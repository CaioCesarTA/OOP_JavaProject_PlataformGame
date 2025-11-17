package Fases;

import Auxiliar.Consts;
import Modelo.*;

public class Fase4 extends Fase {

    public Fase4(){
        super(106,36);
        gravidade = 0.035f;
        player = new Hero(this, 30*Consts.CELL_SIDE,28*Consts.CELL_SIDE);
        portal = new Portal(this, 10*Consts.CELL_SIDE,28*Consts.CELL_SIDE);
        carregarImagens("fases/fase4/tilesetFase4.png","fases/fase4/bgFase4.png");
        carregarInfoNivel("fases/fase4/infoFase4.png");
        adicionarPersonagens();
    }

    protected void adicionarPersonagens(){
        addInimigo(new Alien(this, 40*Consts.CELL_SIDE,28*Consts.CELL_SIDE));
    }


    public boolean isSolido(float x, float y){
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        int[] transponivel = {30,31,32,38,39,40,41,42,43,49,50,51,52,53,54}; 
        for(int i : transponivel){
            if(sprite==i) return false;
        }
        return true;
    }
}