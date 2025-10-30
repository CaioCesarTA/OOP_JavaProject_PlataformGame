package Controler;

import Auxiliar.Posicao;
import Modelo.Personagem;
import java.awt.Graphics;
import java.util.ArrayList;

public class ControleDeJogo implements Runnable {
    
    private Janela janela;
    private Tela tela;
    
    private Thread threadJogo;
    //Frames por segundo do jogo
    public static final int FPS = 120;
    
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
        //faseatual.atualizar
    }
    
    public void desenhaTudo(Graphics g){
        tela.getFaseAtual().desenharFase(g);
    }

    @Override
    public void run() {
        //Nanossegundos por frame
        double tempoPorFrame = 1000000000.0 / FPS;
        
        //Inicializa contadores de tempo
        long ultimoTempo = System.nanoTime();
        long tempoAtual;
        long ultimaChecagem = System.currentTimeMillis();
        
        //Contadores de frame
        int frames = 0;
        double deltaFrame = 0;
        
        while(true){
            tempoAtual = System.nanoTime();

            deltaFrame += (tempoAtual - ultimoTempo)/tempoPorFrame;
            ultimoTempo = tempoAtual;
            
            
            if(deltaFrame >= 1){
                processaTudo();
                tela.repaint();
                frames++;
                deltaFrame--;
            }
            
            //Contador de FPS
            if(System.currentTimeMillis() - ultimaChecagem >= 1000) {
                ultimaChecagem = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames = 0;
            }      
        }
    }
    
    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}
