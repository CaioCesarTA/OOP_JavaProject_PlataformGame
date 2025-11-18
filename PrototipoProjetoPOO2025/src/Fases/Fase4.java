package Fases;

import Auxiliar.Consts;
import Modelo.*;

public class Fase4 extends Fase {

    public Fase4(){
        super(106,36);
        gravidade = 0.07f/2;
        player = new Hero(this, 30*Consts.CELL_SIDE,28*Consts.CELL_SIDE);
        portal = new Portal(this, 20*Consts.CELL_SIDE,10*Consts.CELL_SIDE);
        carregarImagens("fases/fase4/tilesetFase4.png","fases/fase4/bgFase4.png");
        carregarInfoNivel("fases/fase4/infoFase4.png");
        adicionarPersonagens();
    }

    protected void adicionarPersonagens(){
        addInimigo(new Alien(this, 60*Consts.CELL_SIDE,28*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 77*Consts.CELL_SIDE,28*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 98*Consts.CELL_SIDE,28*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 56*Consts.CELL_SIDE,19*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 94*Consts.CELL_SIDE,19*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 32*Consts.CELL_SIDE,10*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 69*Consts.CELL_SIDE,10*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 91*Consts.CELL_SIDE,10*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 5*Consts.CELL_SIDE,28*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 10*Consts.CELL_SIDE,19*Consts.CELL_SIDE));
        addInimigo(new Alien(this, 20*Consts.CELL_SIDE,10*Consts.CELL_SIDE));
        
        PortaAlien p = new PortaAlien(this, 26*Consts.CELL_SIDE,27*Consts.CELL_SIDE);
        addEntidade(p);
        addEntidade(new CelulaDeEnergia(this, 40*Consts.CELL_SIDE,28*Consts.CELL_SIDE, p));
        addEntidade(new CelulaDeEnergia(this, 84*Consts.CELL_SIDE,28*Consts.CELL_SIDE, p));
        addEntidade(new CelulaDeEnergia(this, 52*Consts.CELL_SIDE,19*Consts.CELL_SIDE, p));
        addEntidade(new CelulaDeEnergia(this, 69*Consts.CELL_SIDE,19*Consts.CELL_SIDE, p));
        addEntidade(new CelulaDeEnergia(this, 35*Consts.CELL_SIDE,10*Consts.CELL_SIDE, p));
        addEntidade(new CelulaDeEnergia(this, 86*Consts.CELL_SIDE,10*Consts.CELL_SIDE, p));

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