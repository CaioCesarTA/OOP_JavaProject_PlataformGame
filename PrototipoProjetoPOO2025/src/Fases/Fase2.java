package Fases;

import Auxiliar.Consts;
import Modelo.Hero;
import Modelo.Plataforma;
import Modelo.Porta;
import Modelo.Portal;
import Modelo.Terminal;

public class Fase2 extends Fase {

    public Fase2(){
        super(140,36);
        player = new Hero(this, 16*Consts.CELL_SIDE, 7*Consts.CELL_SIDE);
        portal = new Portal(this, 136*Consts.CELL_SIDE, 7*Consts.CELL_SIDE);
        carregarImagens("fases/fase2/tilesetFase2.png","fases/fase2/bgFase2.png");
        carregarInfoNivel("fases/fase2/infoFase2.png");
        adicionarPersonagens();
    }

    @Override
    protected final void adicionarPersonagens(){
        //adiciona porta e terminal que controla a porta
        Porta porta = new Porta(this,60*Consts.CELL_SIDE, 28*Consts.CELL_SIDE);
        Terminal t1 = new Terminal(this, 27*Consts.CELL_SIDE + 5, 30*Consts.CELL_SIDE-15);
        t1.addControlado(porta);
        addEntidade(porta);
        addEntidade(t1);
        //adiciona 6 plataformas e um terminal que controla todas ao mesmo tempo
        Plataforma[] plataformas = new Plataforma[6];
        for(int i=0;i<3;i++) plataformas[i] = new Plataforma(this, (106+i)*Consts.CELL_SIDE, 9*Consts.CELL_SIDE);
        for(int i=0;i<3;i++) plataformas[i+3] = new Plataforma(this, (113+i)*Consts.CELL_SIDE, 9*Consts.CELL_SIDE);
        Terminal t2 = new Terminal(this, 60*Consts.CELL_SIDE + 5, 8*Consts.CELL_SIDE-15);
        for(Plataforma p : plataformas) {
            t2.addControlado(p);
            addEntidade(p);
        }
        addEntidade(t2);

    }

    @Override
    public boolean isSolido(float x, float y){
        if(x<0 || x>=(larguraFase*Consts.CELL_SIDE)) return true;
        if(y<0 || y>=(alturaFase*Consts.CELL_SIDE)) return true;
        int sprite = infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)];

        int[] transponivel = {0,1,2,9,10,11,18,19,20,27,28,29,26,37,38,45,46,47,53,44,62}; 
        for(int i : transponivel){
            if(sprite==i) return false;
        }
        if (sprite<81) return true;
        return false;
    }
}
