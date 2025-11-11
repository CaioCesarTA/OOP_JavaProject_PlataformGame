package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Projetil extends Personagem {
    protected float velocidadeProjetil = 4.0f;
    BufferedImage imagemProjetil;

    public Projetil(Fase fase, float xInicial, float yInicial, int dir) {
        super(fase, xInicial, yInicial);
        flipW = dir;
        carregarAnimacoes();
        inicializarHitbox(6,5);
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

        float vx = velocidadeProjetil;
        vx *= flipW;

        //Detecta colisao com inimigos
        for(Personagem i : fase.getInimigos()){
            if(i.isMortal()){
                if(i.getHitbox().contains(hitbox.x+hitbox.width+vx,hitbox.y)
                    || i.getHitbox().contains(hitbox.x+vx,hitbox.y))
                {
                    fase.removerInimigo(i);
                    fase.removerProjetil(this);
                    return;
                }
            }
        }
        //Detecta colisao com paredes
        float posicaoAnterior = hitbox.x;

        atualizarPosicaoX(vx);
        
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

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }
    }
}
