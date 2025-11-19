package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class PortaAlien extends Porta {
    int qtdEnergia = 0;
    
    public PortaAlien(Fase fase, float xInicial, float yInicial){
        super(fase, xInicial, yInicial, false);
        indiceImagem = 0;
        transponivel = false;
        carregarImagens();
        inicializarHitbox(32, 96);
    }

    @Override
    public void atualizarEntidade() {
        if(qtdEnergia>=6){
            transponivel = true;
        }
    }

    @Override
    public void mudarEstado(){
        qtdEnergia++;
        if(indiceImagem<6) indiceImagem++;
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x);
        int posYimg = (int)(hitbox.y);
        int larguraImg = 32;
        int alturaImg = 96;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        } 
    }

    @Override
    public final void carregarImagens() {
        sprites = new BufferedImage[7];
        BufferedImage temp = LoadSave.importarImagem("entidades/portaAlien.png");
        for(int i=0;i<7;i++){
            sprites[i] = temp.getSubimage(32*i, 0, 32, 96);
        }
    }
}
