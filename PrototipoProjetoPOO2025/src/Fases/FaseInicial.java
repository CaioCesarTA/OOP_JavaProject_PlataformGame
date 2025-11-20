package Fases;

import Auxiliar.Consts;
import Controler.LoadSave;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.*;

public class FaseInicial extends Fase{
    public static final int MENU1 = 0;
    public static final int MENU2 = 1;
    public static final int CENA_INICIO = 2;
    public static final int CENA_CREDITOS = 3;
    private transient BufferedImage[] menu;
    private int estadoFase = MENU1;

    public FaseInicial() {
        super(Consts.MUNDO_LARGURA, Consts.MUNDO_ALTURA);
        carregarImagens();
    }

    @Override
    public final void carregarImagens(){
        menu = new BufferedImage[4];
        menu[0] = LoadSave.importarImagem("fases/cenas/menu1.png");
        menu[1] = LoadSave.importarImagem("fases/cenas/menu2.png");
        menu[2] = LoadSave.importarImagem("fases/cenas/msgInicio.png");
        menu[3] = LoadSave.importarImagem("fases/cenas/creditos.png");
    }

    @Override
    public boolean isSolido(float x, float y) {
        return false;
    }

    public int getEstadoFase(){
        return estadoFase;
    }

    public void setEstadoFase(int estadoFase){
        this.estadoFase = estadoFase;
    }

    @Override
    public void desenharCenario(Graphics g) {
        g.drawImage(menu[estadoFase],  0, 0, Consts.CELL_SIDE * Consts.MUNDO_LARGURA, Consts.CELL_SIDE * Consts.MUNDO_ALTURA, null);
    }

    @Override
    public void resetarFase() {
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
        return 0;
    }

}
