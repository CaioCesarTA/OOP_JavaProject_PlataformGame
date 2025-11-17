package Controler;

import Auxiliar.Consts;
import Fases.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControleDeJogo implements Runnable, KeyListener, MouseListener {
    private Janela janela;
    private Tela tela;
    private Thread threadJogo;
    private Fase[] fases;
    private int IDfaseAtual;

    public ControleDeJogo(){
        IDfaseAtual = 0;
        fases = new Fase[5];
        //TODO: Trocar cada uma dessas pela fase certa
        fases[0] = new Fase1();
        fases[1] = new Fase2();
        fases[2] = new Fase3();
        fases[3] = new Fase4();
        fases[4] = new Fase5();
        tela = new Tela(this);
        janela = new Janela(tela);
        janela.setVisible(true);
        tela.requestFocus();
    }

    public void ComecarLoopJogo(){
        threadJogo = new Thread(this);
        threadJogo.start();
    }

    public void processaTudo(){
        getFaseAtual().atualizarFase();

        //Vai para a proxima fase se o Player entrar no portal
        if(getFaseAtual().getPortal().podeAvancarFase()) {
            avancarFase();
        }

        //Atualiza o titulo da janela
        janela.setTitle("FASE " + (IDfaseAtual+1) +
                        " (x: " + (int)getFaseAtual().getPlayer().getHitbox().x + 
                        ", y: " + (int)getFaseAtual().getPlayer().getHitbox().y + ")" +
                        " Vida: " + getFaseAtual().getPlayer().getVidaAtual() + "/5");
    }

    public void desenhaTudo(Graphics g){
        getFaseAtual().desenharCenario(g);
        getFaseAtual().desenharFase(g);
    }

    @Override
    public void run() {
        int delay = 1000 / Consts.FPS; // intervalo em ms para atingir o FPS desejado

        new javax.swing.Timer(delay, e -> {
            processaTudo();
            tela.repaint();
        }).start();
    }

    private Fase getFaseAtual() {
        return fases[IDfaseAtual];
    }

    private void avancarFase() {
        if(IDfaseAtual<4) {
            IDfaseAtual++;
            getFaseAtual().resetarFase();
        }
    }

    private void voltarFase(){
        if(IDfaseAtual>0) {
            IDfaseAtual--;
            getFaseAtual().resetarFase();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                getFaseAtual().getPlayer().getDirecao().setEsquerda(true);
                break;
            case KeyEvent.VK_D:
                getFaseAtual().getPlayer().getDirecao().setDireita(true);
                break;
            case KeyEvent.VK_W:
                getFaseAtual().getPlayer().setPulando(true);
                break;
            case KeyEvent.VK_SHIFT:
                getFaseAtual().getPlayer().setCorrendo(true);
                break;
            case KeyEvent.VK_F:
                getFaseAtual().getPlayer().setSocando(true);
                break;
            case KeyEvent.VK_SPACE:
                getFaseAtual().getPlayer().setAtirando(true);
                break;
            case KeyEvent.VK_R:
                getFaseAtual().resetarFase();
                break;
            case KeyEvent.VK_P:
                avancarFase();
                break;
            case KeyEvent.VK_O:
                voltarFase();
                break;
            case KeyEvent.VK_M:
                getFaseAtual().getPlayer().sofrerDano(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                getFaseAtual().getPlayer().getDirecao().setEsquerda(false);
                break;
            case KeyEvent.VK_D:
                getFaseAtual().getPlayer().getDirecao().setDireita(false);
                break;
            case KeyEvent.VK_SHIFT:
                getFaseAtual().getPlayer().setCorrendo(false);
                break;
        }
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
}
