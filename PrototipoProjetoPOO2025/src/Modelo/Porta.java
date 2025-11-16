package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Fases.Fase;

public class Porta extends Entidade implements Controlado {
    private BufferedImage[] sprites = new BufferedImage[2];
    private int indiceImagem = 0;

    public Porta(Fase fase, float xInicial, float yInicial){
        super(fase,xInicial,yInicial);
        BufferedImage temp = Fase.importarImagem("entidades/porta.png");
        mortal = false;
        sprites[0] = temp.getSubimage(0, 0, 64, 64);
        sprites[1] = temp.getSubimage(64, 0, 64, 64);
        inicializarHitbox(44, 96);
    }

    @Override
    public void mudarEstado() {
        indiceImagem++;
        transponivel = true;
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x)-25;
        int posYimg = (int)(hitbox.y);
        int larguraImg = 96;
        int alturaImg = 96;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }
}
