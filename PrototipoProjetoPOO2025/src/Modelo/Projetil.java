package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Controler.LoadSave;

public class Projetil extends Entidade {
    protected float velocidadeProjetil = 4.0f;
    protected transient BufferedImage imagemProjetil;
    protected int dano;
    protected int flipW;
    protected String pathImagem;

    public Projetil(Fase fase, float xInicial, float yInicial, int dir, int dano, String pathImagem) {
        super(fase, xInicial, yInicial);
        flipW = dir;
        this.pathImagem = pathImagem;
        carregarImagens();
        inicializarHitbox(6,5);
        transponivel = true;
        this.dano = dano;
    }

    public void atualizarEntidade(){
        detectarColisao();
        atualizarPosicao();
    }

    protected void detectarColisao() {
        //Detecta colisao com personagens e da dano
        for(Entidade e : fase.getEntidades()){
            if(hitbox.intersects(e.getHitbox())) {
                if(e.equals(this)) continue;
                if(e.isMortal()) e.sofrerDano(dano);
                if(!e.isTransponivel()) fase.removerEntidade(this);
                return;
            }
        }
    }

    @Override
    protected void atualizarPosicao() {

        float vx = velocidadeProjetil;
        vx *= flipW;

        //Detecta colisao com paredes
        float posicaoAnterior = hitbox.x;

        atualizarPosicaoX(vx);
        
        float novaPosicao = hitbox.x;
        
        if(posicaoAnterior == novaPosicao) fase.removerEntidade(this);
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
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

    @Override
    protected void carregarImagens() {
        imagemProjetil = LoadSave.importarImagem(pathImagem);
    }
}
