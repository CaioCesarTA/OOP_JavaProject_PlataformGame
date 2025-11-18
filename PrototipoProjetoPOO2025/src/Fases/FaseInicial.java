package Fases;

import Auxiliar.Consts;
import java.awt.event.KeyEvent;

import java.awt.*;

public class FaseInicial extends Fase{
private boolean avancarFase = false;
    public FaseInicial() {
        super(Consts.MUNDO_LARGURA, Consts.MUNDO_ALTURA);
        this.background = Fase.importarImagem("fases/cenas/msgInicio.png");
    }

    @Override
    public boolean isSolido(float x, float y) {
        return false;
    }

    @Override
    public void resetarFase() {
        avancarFase = false;
    }

    @Override
    protected void adicionarPersonagens() {

    }

    @Override
    protected int getSpriteVazio() {
        return 0;
    }

    @Override
    public void atualizarFase() {
    }

    @Override
    public void desenharCenario(Graphics g) {
        g.drawImage(background, 0, 0, Consts.CELL_SIDE * Consts.MUNDO_LARGURA, Consts.CELL_SIDE * Consts.MUNDO_ALTURA, null);

    }

    public boolean proxFase(){
        return avancarFase;
    }
    @Override
    public void desenharFase(Graphics g) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        avancarFase = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
