package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Projetil extends Entidade {
    protected float velocidadeProjetil = 4.0f;
    BufferedImage imagemProjetil;
    int dano;
    int flipW;

    public Projetil(Fase fase, float xInicial, float yInicial, int dir, int dano) {
        super(fase, xInicial, yInicial);
        flipW = dir;
        imagemProjetil = Fase.importarImagem("projeteis/bullet.png");
        inicializarHitbox(6,5);
        transponivel = true;
        this.dano = dano;
    }

    public void atualizarEntidade(){
        detectarColisao();
        atualizarPosicao();
    }

    protected void detectarColisao() {
        //TODO: projetil tem que ter o personagem que atirou ele, ou o dano do personagem que atirou ele
        //Detecta colisao com personagens e da dano
        for(Personagem i : fase.getPersonagens()){
            if(i.isMortal() && hitbox.intersects(i.getHitbox())) {
                System.out.println("dano");
                i.sofrerDano(dano);
                fase.removerProjetil(this);
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
        
        if(posicaoAnterior == novaPosicao) fase.removerProjetil(this);
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
}
