package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class PortaCastelo extends Porta{
    private int flipW = 1;
    private int flipX = 0;

    public PortaCastelo(Fase fase, float xInicial, float yInicial, boolean aberto, int dir){
        super(fase,xInicial,yInicial,aberto);
        mortal = false;
        flipW = dir;
        if(flipW==-1) flipX = 32;
        carregarImagens();
        inicializarHitbox(32, 64);
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) + flipX;
        int posYimg = (int)(hitbox.y);
        int larguraImg = 32 * flipW;
        int alturaImg = 64;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }

    @Override
    public void carregarImagens() {
        BufferedImage temp = LoadSave.importarImagem("fases/fase5/tilesetFase5.png");
        sprites[1] = temp.getSubimage(384, 96, 32, 64);
        sprites[0] = temp.getSubimage(352, 96, 32, 64);
    }
}