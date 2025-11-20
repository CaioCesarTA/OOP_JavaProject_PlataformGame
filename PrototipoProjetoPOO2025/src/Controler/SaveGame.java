package Controler;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Portal;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveGame implements Serializable {
    public Hero heroi;
    public Portal portal;
    public ArrayList<Personagem> inimigos;
    public int idFaseAtual;

    public SaveGame(int idFaseAtual, Hero heroi, Portal portal, ArrayList<Personagem> inimigos){
        this.idFaseAtual = idFaseAtual;
        this.heroi = heroi;
        this.inimigos = inimigos;
        this.portal = portal;
    }

    public void recuperarImagens() {
        if (heroi != null) {
            heroi.carregarImagens();
        }
        if (portal != null) {
            portal.carregarImagens();
        }
        if (inimigos != null) {
            for (Personagem p : inimigos) {
                p.carregarImagens();
            }
        }
    }
}
