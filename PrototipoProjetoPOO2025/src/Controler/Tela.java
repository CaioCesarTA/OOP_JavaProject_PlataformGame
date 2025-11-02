package Controler;

import Auxiliar.Consts;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Tela extends JPanel implements MouseListener, KeyListener {

    private ControleDeJogo cj;   
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private Fase[] fases;
    private int faseAtual;
    
    public Tela(ControleDeJogo cj) {
        this.cj = cj;
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(Consts.RES); //Configura o tamanho do painel
        addKeyListener(this);
        addMouseListener(this);
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                fases[faseAtual].getPlayer().getDirecao().setEsquerda(true);
                break;
            case KeyEvent.VK_D:
                fases[faseAtual].getPlayer().getDirecao().setDireita(true);
                break;
            case KeyEvent.VK_W:
                fases[faseAtual].getPlayer().setPulando(true);
                break;
            case KeyEvent.VK_SHIFT:
                fases[faseAtual].getPlayer().setCorrendo(true);
                break;
            case KeyEvent.VK_F:
                fases[faseAtual].getPlayer().setSocando(true);
                break;
            case KeyEvent.VK_SPACE:
                fases[faseAtual].getPlayer().setAtirando(true);
                break;
            case KeyEvent.VK_R:
                //TODO: quando tiver inimigos e etc, isso vira fases[faseAtual].resetarFase()
                fases[faseAtual].getPlayer().resetarPosicao();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                fases[faseAtual].getPlayer().getDirecao().setEsquerda(false);
                break;
            case KeyEvent.VK_D:
                fases[faseAtual].getPlayer().getDirecao().setDireita(false);
                break;
            case KeyEvent.VK_SHIFT:
                fases[faseAtual].getPlayer().setCorrendo(false);
                break;
        }
    }

}
