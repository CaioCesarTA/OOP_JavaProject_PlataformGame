package Controler;

import Modelo.Hero;
import Modelo.Personagem;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveGame implements Serializable {
    public Hero heroi;
    public ArrayList<Personagem> inimigos;
    public int idFaseAtual;

    public SaveGame(int idFaseAtual, Hero heroi, ArrayList<Personagem> inimigos){
        this.idFaseAtual = idFaseAtual;
        this.heroi = heroi;
        this.inimigos = inimigos;
    }

    public void recuperarImagens() {
        if (heroi != null) {
            heroi.carregarImagens();
        }
        if (inimigos != null) {
            for (Personagem p : inimigos) {
                p.carregarImagens();
            }
        }
    }
}
