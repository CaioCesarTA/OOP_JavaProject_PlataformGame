package Fases;

import Auxiliar.Consts;
import Controler.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;

public class FaseFinal extends Fase{

    public FaseFinal() {
        super(Consts.MUNDO_LARGURA, Consts.MUNDO_ALTURA);
        carregarImagens();
    }

    @Override
    public final void carregarImagens(){
        background = LoadSave.importarImagem("fases/cenas/msgFinal.png");
    }

    @Override
    public boolean isSolido(float x, float y) {
        return false;
    }

    @Override
    public void resetarFase() {
    }

    @Override
    public void desenharCenario(Graphics g) {
        g.drawImage(background, 0, 0, Consts.CELL_SIDE * Consts.MUNDO_LARGURA, Consts.CELL_SIDE * Consts.MUNDO_ALTURA, null);

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
    public void desenharFase(Graphics g) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public final int getIDFase(){
        return 6;
    }

}
