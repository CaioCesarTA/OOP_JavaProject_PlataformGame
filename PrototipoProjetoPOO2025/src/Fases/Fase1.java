package Fases;

import Auxiliar.Consts;
import Modelo.Hero;
import Modelo.Portal;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fase1 extends Fase {
    
    public Fase1(){
        super(140,32);
        player = new Hero(this, 4*Consts.CELL_SIDE, (32-5)*Consts.CELL_SIDE);
        portal = new Portal(this, 2400, 96);
        carregarImagens();
        carregarInfoNivel();
    }

    @Override
    protected final void carregarImagens() {
        BufferedImage temp = importarImagem("fases/fase1/tilesetFase1.png");
        tileset = new BufferedImage[54];
        for(int j=0;j<9;j++){
            for(int i=0;i<6;i++){
                int indice = j*6 + i;
                tileset[indice] = temp.getSubimage(i*Consts.CELL_SIDE, j*Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE);
            }
        }
        background = importarImagem("fases/fase1/bgFase1.png");
    }

    public boolean isSolido(float x, float y){
        if(x<0 || x>(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        if (sprite != 13 &&
                sprite != 4 &&
                sprite != 5 &&
                sprite != 10 &&
                sprite != 11 && sprite<24) {
            return true;
        }
        //if (sprite<24 && sp) return true;
        return false;
    }
    
    protected final void carregarInfoNivel(){
        BufferedImage temp = importarImagem("fases/fase1/infoFase1.png");
        infoCenario = new int[alturaFase][larguraFase];
        for(int i=0;i<temp.getHeight();i++){
            for(int j=0;j<temp.getWidth();j++){
                Color cor = new Color(temp.getRGB(j,i));
                int valor = cor.getRed();
                infoCenario[i][j] = valor;
            }
        }
    }
}
