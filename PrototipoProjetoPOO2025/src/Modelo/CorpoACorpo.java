package Modelo;

import java.awt.Graphics;

import Fases.Fase;

public class CorpoACorpo extends Entidade{

    public CorpoACorpo(Fase fase, float xInicial, float yInicial){
        super(fase, xInicial, yInicial);
        transponivel = true;
        inicializarHitbox(0, 0);
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
    }

}