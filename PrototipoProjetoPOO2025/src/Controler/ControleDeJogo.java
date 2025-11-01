package Controler;

import Auxiliar.Consts;
import Auxiliar.Posicao;
import Modelo.Personagem;
import java.awt.Graphics;
import java.util.ArrayList;

public class ControleDeJogo implements Runnable {
    
    private Janela janela;
    private Tela tela;

    private Thread threadJogo;

    public ControleDeJogo(){
        inicializarClasses();
        //Cria janela e painel
        tela = new Tela(this);
        janela = new Janela(tela);
        tela.requestFocus();
        //Comeca o loop de jogo
        ComecarLoopJogo();
    }

    private void inicializarClasses(){
    }

    private void ComecarLoopJogo(){
        this.threadJogo = new Thread(this);
        this.threadJogo.start();
    }

    public void processaTudo(){
        tela.getFaseAtual().atualizarFase();
    }

    public void desenhaTudo(Graphics g){
        tela.getFaseAtual().desenharFase(g);
    }

    @Override
    public void run() {
        double umSegundo = 1000000000.0; //um segundo em nanossegundos
        double tempoPorFrame = umSegundo / Consts.FPS; // nanossegundos por frame
        
        //Inicializa contadores de tempo
        long ultimaAtualizacao = System.nanoTime();
        long agora;
        long ultimaChecagem = System.currentTimeMillis();
        
        //Contadores de frame
        int frames = 0;
        double deltaFrame = 0;
        
        while(true){
            agora = System.nanoTime();

            deltaFrame += (agora - ultimaAtualizacao)/tempoPorFrame;
            ultimaAtualizacao = agora;
            
            
            if(deltaFrame >= 1){
                processaTudo();
                tela.repaint();
                frames++;
                deltaFrame--;
            }
            
            //Contador de FPS
            if(System.currentTimeMillis() - ultimaChecagem >= 1000) {
                ultimaChecagem = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }      
        }
    }
}
