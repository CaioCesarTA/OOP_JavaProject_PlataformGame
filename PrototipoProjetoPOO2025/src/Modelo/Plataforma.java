package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class Plataforma extends Entidade implements Controlado{
    private BufferedImage[] sprites = new BufferedImage[2];
    private int indiceImagem = 0;

    public Plataforma(Fase fase, float xInicial, float yInicial){
        super(fase,xInicial,yInicial);
        BufferedImage temp = LoadSave.importarImagem("entidades/plataforma.png");
        mortal = false;
        transponivel = true;
        sprites[0] = temp.getSubimage(0, 0, 32, 32);
        sprites[1] = temp.getSubimage(32, 0, 32, 32);
        inicializarHitbox(32, 32);
    }

    @Override
    public void mudarEstado() {
        indiceImagem++;
        transponivel = false;
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x);
        int posYimg = (int)(hitbox.y);
        int larguraImg = 32;
        int alturaImg = 32;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }
}
