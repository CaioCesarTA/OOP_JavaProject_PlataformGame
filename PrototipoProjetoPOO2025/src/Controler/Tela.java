package Controler;

import Auxiliar.Consts;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import Auxiliar.Posicao;

public class Tela extends JPanel implements MouseListener, KeyListener {

    private ControleDeJogo cj;   
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private Fase[] fases;
    private int faseAtual;
    
    public Tela(ControleDeJogo cj) {
        this.cj = cj;
        //Configura o tamanho do painel
        this.setPreferredSize(new Dimension(32*Consts.RES,32*Consts.RES));
        faseAtual = 0;
        fases = new Fase[10];
        fases[0] = new Fase0();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        cj.desenhaTudo(g);
    }
    
    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public Fase getFaseAtual() {
        return fases[faseAtual];
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
