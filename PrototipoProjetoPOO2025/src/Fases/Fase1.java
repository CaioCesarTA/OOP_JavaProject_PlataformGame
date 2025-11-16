package Fases;

import Auxiliar.Consts;
import Modelo.*;

public class Fase1 extends Fase {
    
    public Fase1(){
        super(140,36);
        player = new Hero(this, 4*Consts.CELL_SIDE, (alturaFase-5)*Consts.CELL_SIDE);
        portal = new Portal(this, 4000, 512);
        carregarImagens("fases/fase1/tilesetFase1.png","fases/fase1/bgFase1.png");
        carregarInfoNivel("fases/fase1/infoFase1.png");
        adicionarPersonagens();
    }

    protected void adicionarPersonagens(){
        addInimigo(new Zumbi1(this, 1600, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 1772, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 2000, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 2400, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 2300, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 4362, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 4200, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 3210, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 2916, (alturaFase-5)*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 129*Consts.CELL_SIDE, 16*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 103*Consts.CELL_SIDE, 12*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 140*Consts.CELL_SIDE, 6*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 53*Consts.CELL_SIDE, 11*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 59*Consts.CELL_SIDE, 27*Consts.CELL_SIDE));
        addInimigo(new Zumbi1(this, 31*Consts.CELL_SIDE, 23*Consts.CELL_SIDE));
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
