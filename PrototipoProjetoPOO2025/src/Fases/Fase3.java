package Fases;

import Auxiliar.Consts;
import Controler.LoadSave;
import Modelo.*;

public class Fase3 extends Fase {

    public Fase3(){
        super(140,32);
        player = new Hero(this, Consts.CELL_SIDE, 4*Consts.CELL_SIDE);
        portal = new Portal(this, 4444, 128);
        carregarImagensFase();
        adicionarPersonagens();

        LoadSave.salvarEntidade("ENTIDADES/mumia.zip", new Mumia(this, 0, 0));
        LoadSave.salvarEntidade("ENTIDADES/escorpiao.zip", new Escorpiao(this, 0, 0));
    }

    @Override
    protected void carregarImagensFase() {
        carregarImagens("fases/fase3/tilesetFase3.png","fases/fase3/bgFase3.png");
        carregarInfoNivel("fases/fase3/infoFase3.png");
    }

    protected void adicionarPersonagens(){
        addInimigo(new Mumia(this, 52*Consts.CELL_SIDE, 29*Consts.CELL_SIDE));
        addInimigo(new Mumia(this, 10*Consts.CELL_SIDE, 28*Consts.CELL_SIDE));
        addInimigo(new Escorpiao(this, 12*Consts.CELL_SIDE, 22*Consts.CELL_SIDE));
        addInimigo(new Mumia(this, 28*Consts.CELL_SIDE, 19*Consts.CELL_SIDE));
        addInimigo(new Escorpiao(this, 50*Consts.CELL_SIDE, 18*Consts.CELL_SIDE));
        addInimigo(new Mumia(this, 83*Consts.CELL_SIDE, 19*Consts.CELL_SIDE));
        addInimigo(new Escorpiao(this, 116*Consts.CELL_SIDE, 18*Consts.CELL_SIDE));
        addInimigo(new Escorpiao(this, 120*Consts.CELL_SIDE, 26*Consts.CELL_SIDE));
        addInimigo(new Mumia(this, 133*Consts.CELL_SIDE, 15*Consts.CELL_SIDE));
    }


    public boolean isSolido(float x, float y){
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        if (sprite != 4 &&
                sprite != 12 &&
                sprite != 18 &&
                sprite != 19 &&
                sprite != 20 &&
                sprite != 34 &&
                sprite != 35 &&
                sprite != 36 &&
                sprite != 37 &&
                sprite != 38 &&
                sprite != 39 &&
                sprite != 42 &&
                sprite != 43 &&
                sprite != 46 &&
                sprite != 47 &&
                sprite != 63) {


            return true;
        }

        return false;
    }

    @Override
    protected int getSpriteVazio(){
        return 63;
    }
}