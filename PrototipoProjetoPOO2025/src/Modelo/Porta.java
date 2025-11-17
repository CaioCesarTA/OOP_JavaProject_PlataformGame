package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Fases.Fase;

public abstract class Porta extends Entidade implements Controlado {
    protected BufferedImage[] sprites = new BufferedImage[2];
    protected int indiceImagem = 0;

    public Porta(Fase fase, float xInicial, float yInicial, boolean aberto){
        super(fase,xInicial,yInicial);
        if(aberto){
            indiceImagem = 1;
            transponivel = true;
        }
        else{
            indiceImagem = 0;
            transponivel = false; 
        }
    }

    @Override
    public void mudarEstado() {
        if(indiceImagem==0){
            indiceImagem++;
            transponivel = true;
        } 
        else if (indiceImagem==1) {
            indiceImagem--;
            transponivel = false;
        }
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
    }
}
