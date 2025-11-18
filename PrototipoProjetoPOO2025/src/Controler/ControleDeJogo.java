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
        janela.setTitle("FASE " + (IDfaseAtual+1));
    }

    public void desenhaTudo(Graphics g){
        getFaseAtual().desenharCenario(g);
        getFaseAtual().desenharFase(g);
    }

    @Override
    public void run() {
        double umSegundo = 1000000000.0; //um segundo em nanossegundos
        double tempoPorFrame = umSegundo / Consts.FPS; // nanossegundos por frame
        
        //Inicializa contadores de tempo
        long ultimaAtualizacao = System.nanoTime();
        long agora;
        long ultimaChecagem = System.nanoTime(); //usado para verificar quantos quadros foram gerados em um segundo
        
        //Contadores de frame
        int frames = 0;
        double deltaTempo = 0;
        
        while(true){
            agora = System.nanoTime();

            deltaTempo += (agora - ultimaAtualizacao)/tempoPorFrame;
            ultimaAtualizacao = agora;
            
            if(deltaTempo >= 1){
                processaTudo();
                tela.repaint();
                frames++;
                deltaTempo--;
            }
            
            //Contador de FPS
            if(agora - ultimaChecagem >= umSegundo) {
                ultimaChecagem = agora;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
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
        if(e.getKeyCode() == KeyEvent.VK_P) avancarFase();
        if(e.getKeyCode() == KeyEvent.VK_O) voltarFase();
        getFaseAtual().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        getFaseAtual().keyReleased(e);
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
