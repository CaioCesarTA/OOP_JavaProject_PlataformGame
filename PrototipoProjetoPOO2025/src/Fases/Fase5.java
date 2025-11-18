package Fases;

import Auxiliar.Consts;
import Controler.LoadSave;
import Modelo.*;

public class Fase5 extends Fase {

    public Fase5(){
        super(96,32);
        player = new Hero(this, 2*Consts.CELL_SIDE, 27*Consts.CELL_SIDE);
        portal = new Portal(this, 82*Consts.CELL_SIDE, 27*Consts.CELL_SIDE);
        carregarImagensFase();
        adicionarPersonagens();

        LoadSave.salvarEntidade("labubu.zip", new Labubu(this, 0, 0));
        LoadSave.salvarEntidade("chave.zip", new Chave(this, 0, 0,null));
    }

    @Override
    protected void carregarImagensFase() {
        carregarImagens("fases/fase5/tilesetFase5.png","fases/fase5/bgFase5.png");
        carregarInfoNivel("fases/fase5/infoFase5.png");
    }


    protected void adicionarPersonagens(){
        addInimigo(new Labubu(this, 52*Consts.CELL_SIDE, 26*Consts.CELL_SIDE));
    }

    public boolean isSolido(float x, float y){
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];


        int[] transponivel = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,39, 47,48,49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 72, 92};
        for(int i : transponivel){
            if(sprite==i) return false;
        }
        return true;
    }

    @Override
    protected int getSpriteVazio(){
        return 39;
    }
}