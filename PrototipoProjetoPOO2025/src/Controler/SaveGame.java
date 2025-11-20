package Controler;

import Modelo.Entidade;

import java.io.Serializable;

import Fases.Fase;

public class SaveGame implements Serializable {
    public Fase faseSalva;
    
    public SaveGame(Fase faseSalva){
        this.faseSalva = faseSalva;
    }

    public void recuperarImagens() {
        faseSalva.carregarImagens();
        for(Entidade e : faseSalva.getEntidades()){
            e.carregarImagens();
        }
    }
}
