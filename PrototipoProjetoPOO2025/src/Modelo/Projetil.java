package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Projetil extends Personagem{
    protected float velocidadeProjetil = 4.0f;
    BufferedImage imagemProjetil;

    public Projetil(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        carregarAnimacoes();
        inicializarHitbox();
    }

    @Override
    protected void inicializarHitbox() {
        hitbox = new Rectangle2D.Float(posicaoInicial.getX(),posicaoInicial.getY(),6,5);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        return 0;
    }

    @Override
    protected void carregarAnimacoes() {
        imagemProjetil = Fase.importarImagem("projeteis/bullet.png");
    }

    @Override
    protected void atualizarAcaoAtual() {
    }

    @Override
    protected void atualizarPosicao() {
        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(velocidadeProjetil);
        float novaPosicao = hitbox.x;
        if(posicaoAnterior == novaPosicao) fase.removerProjetil(this);
    }

    @Override
    public void desenharPersonagem(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 12;
        int posYimg = (int)(hitbox.y) - 12;
        int larguraImg = 32;
        int alturaImg = 32;

        g.drawImage(imagemProjetil, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
    }
}
