package Controler;

import Fases.Fase1;
import Fases.Fase;
import Auxiliar.Consts;
import Modelo.Projetil;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Tela extends JPanel implements MouseListener, KeyListener {

    private ControleDeJogo cj;
    private Fase[] fases;
    private int IDfaseAtual;
    
    public Tela(ControleDeJogo cj) {
        this.cj = cj;
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(Consts.RES); //Configura o tamanho do painel
        addKeyListener(this);
        addMouseListener(this);
        IDfaseAtual = 0;
        fases = new Fase[10];
        fases[0] = new Fase1();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        cj.desenhaTudo(g);
    }

    public Fase getFaseAtual() {
        return fases[IDfaseAtual];
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                fases[IDfaseAtual].getPlayer().getDirecao().setEsquerda(true);
                break;
            case KeyEvent.VK_D:
                fases[IDfaseAtual].getPlayer().getDirecao().setDireita(true);
                break;
            case KeyEvent.VK_W:
                fases[IDfaseAtual].getPlayer().setPulando(true);
                break;
            case KeyEvent.VK_SHIFT:
                fases[IDfaseAtual].getPlayer().setCorrendo(true);
                break;
            case KeyEvent.VK_F:
                fases[IDfaseAtual].getPlayer().setSocando(true);
                break;
            case KeyEvent.VK_SPACE:
                fases[IDfaseAtual].getPlayer().setAtirando(true);
                getFaseAtual().addProjetil(new Projetil(getFaseAtual(),getFaseAtual().getPlayer().getHitbox().x+40,getFaseAtual().getPlayer().getHitbox().y+20));
                break;
            case KeyEvent.VK_R:
                fases[IDfaseAtual].resetarFase();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                fases[IDfaseAtual].getPlayer().getDirecao().setEsquerda(false);
                break;
            case KeyEvent.VK_D:
                fases[IDfaseAtual].getPlayer().getDirecao().setDireita(false);
                break;
            case KeyEvent.VK_SHIFT:
                fases[IDfaseAtual].getPlayer().setCorrendo(false);
                break;
        }
    }

}
