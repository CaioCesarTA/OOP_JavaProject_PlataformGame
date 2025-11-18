package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class PortaFuturo extends Porta {

    public PortaFuturo(Fase fase, float xInicial, float yInicial,boolean aberto){
        super(fase,xInicial,yInicial,aberto);
        BufferedImage temp = LoadSave.importarImagem("entidades/porta.png");
        mortal = false;
        sprites[0] = temp.getSubimage(0, 0, 64, 64);
        sprites[1] = temp.getSubimage(64, 0, 64, 64);
        inicializarHitbox(44, 96);
    }
    

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 25 + 96;
        int posYimg = (int)(hitbox.y);
        int larguraImg = 96 * -1;
        int alturaImg = 96;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }
    }
}