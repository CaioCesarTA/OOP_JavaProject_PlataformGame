package Fases;

import Auxiliar.Consts;
import Modelo.*;

public class Fase2 extends Fase {

    public Fase2(){
        super(140,36);
        player = new Hero(this, 6*Consts.CELL_SIDE, 27*Consts.CELL_SIDE);
        portal = new Portal(this, 131*Consts.CELL_SIDE, 22*Consts.CELL_SIDE);
        carregarImagens("fases/fase2/tilesetFase2.png","fases/fase2/bgFase2.png");
        carregarInfoNivel("fases/fase2/infoFase2.png");
        adicionarPersonagens();
    }

    @Override
    protected final void adicionarPersonagens(){
        PortaFuturo porta1 = new PortaFuturo(this, 46*Consts.CELL_SIDE, 26*Consts.CELL_SIDE,false);
        Terminal terminal1 = new Terminal(this,  44*Consts.CELL_SIDE+5, 8*Consts.CELL_SIDE-15);
        terminal1.addControlado(porta1);
        addEntidade(terminal1);
        addEntidade(porta1);
        Plataforma[] plataformas = new Plataforma[20];
        Terminal terminal2 = new Terminal(this, 77*Consts.CELL_SIDE+5, 13*Consts.CELL_SIDE-15);
        addEntidade(terminal2);
        for(int i=0;i<20;i++) {
            plataformas[i] = new Plataforma(this, (86+i)*Consts.CELL_SIDE, 29*Consts.CELL_SIDE);
            terminal2.addControlado(plataformas[i]);
            addEntidade(plataformas[i]);
        }
        addInimigo(new Robo(this, 40*Consts.CELL_SIDE, 17*Consts.CELL_SIDE));
        addInimigo(new Robo(this, 79*Consts.CELL_SIDE, 21*Consts.CELL_SIDE));
        addInimigo(new Robo(this, 63*Consts.CELL_SIDE, 16*Consts.CELL_SIDE));
        addInimigo(new Robo(this, 76*Consts.CELL_SIDE, 11*Consts.CELL_SIDE));
        addInimigo(new Robo(this, 131*Consts.CELL_SIDE, 21*Consts.CELL_SIDE));
        addInimigo(new Robo(this, 114*Consts.CELL_SIDE, 26*Consts.CELL_SIDE));
    }

    @Override
    public boolean isSolido(float x, float y){
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        int[] transponivel = {0,1,2,9,10,11,18,19,20,27,28,29,26,37,38,45,46,47,53,44,62,60}; 
        for(int i : transponivel){
            if(sprite==i) return false;
        }
        if (sprite<81) return true;
        return false;
    }
    @Override
    protected int getSpriteVazio(){
        return 81;
    }
}
