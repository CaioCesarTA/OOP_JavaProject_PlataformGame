package Controler;

import Auxiliar.Consts;
import java.awt.Graphics;

public class ControleDeJogo implements Runnable {
    
    private Janela janela;
    private Tela tela;

    private Thread threadJogo;

    public ControleDeJogo(){
        tela = new Tela(this);
        janela = new Janela(tela);
        janela.setVisible(true);
        tela.requestFocus();
    }

    public void ComecarLoopJogo(){
        this.threadJogo = new Thread(this);
        this.threadJogo.start();
    }

    public void processaTudo(){
        tela.getFaseAtual().atualizarFase();
    }

    public void desenhaTudo(Graphics g){
        tela.getFaseAtual().desenharCenario(g);
        tela.getFaseAtual().desenharFase(g);
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
}
