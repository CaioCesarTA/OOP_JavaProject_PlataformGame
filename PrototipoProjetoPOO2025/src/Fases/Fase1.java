package Fases;

import Auxiliar.Consts;
import Modelo.*;

import java.awt.image.BufferedImage;

public class Fase1 extends Fase {
    
    public Fase1(){
        super(140,36);
        player = new Hero(this, 4*Consts.CELL_SIDE, (alturaFase-5)*Consts.CELL_SIDE);
        portal = new Portal(this, 4000, 512);
        carregarImagens();
        carregarInfoNivel("fases/fase1/infoFase1.png");
        adicionarPersonagens();
    }

    protected void adicionarPersonagens(){
        addInimigo(new Biker(this, 14*Consts.CELL_SIDE, (alturaFase-5)*Consts.CELL_SIDE));
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
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        if (sprite != 13 &&
                sprite != 4 &&
                sprite != 5 &&
                sprite != 10 &&
                sprite != 11 && sprite<24) {
            return true;
        }

        return false;
    }
}
