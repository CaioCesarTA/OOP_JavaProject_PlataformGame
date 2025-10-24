package Main;

import Modelo.Personagem;
import Modelo.Player;
import Auxiliar.Consts;
import Auxiliar.ControleMouse;
import Auxiliar.ControleTeclado;
import Auxiliar.Desenho;
import Fases.Fase;
import Auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tela extends javax.swing.JFrame implements Runnable {

    private Fase[] fases = new Fase[5];
    private int faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    
    //private final Set<Integer> teclasPressionadas = new HashSet<>();
    
    public Tela() {
        super();
        initComponents();
        
        Desenho.setCenario(this);
        
        faseAtual = 0;
        fases[faseAtual] = new Fase();

        ControleTeclado controleTeclado = new ControleTeclado(this);
        ControleMouse controleMouse = new ControleMouse(this);
        this.addMouseListener(controleMouse);
        this.addMouseMotionListener(controleMouse);
        this.addKeyListener(controleTeclado);

         /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);
        
        Player player = new Player("Robbo.png", 0, 7);
        fases[faseAtual].setPlayer(player);
        
        this.atualizaCamera();
    }

    @Override
    public void run(){
        double umSegundo = 1000000000; //Um segundo em nanossegundos
        double agora;
        double ultimaAtualizacao = System.nanoTime();
        double tempoPorFrame = umSegundo / Consts.FPS;
        double deltaTempo;
        
        //Contador de frames gerados em um segundo
        double ultimaChecagemFrames = System.nanoTime();
        int frames = 0; 
        
        //Loop de Jogo
        while(true){
            agora = System.nanoTime();
            deltaTempo = (agora - ultimaAtualizacao) / tempoPorFrame;
            
            if(deltaTempo >= 1){
                repaint();
                ultimaAtualizacao = agora;
                frames++;
                deltaTempo--;
            }
            
            if(agora - ultimaChecagemFrames >= umSegundo){
                System.out.println("Frames: " + frames);
                frames = 0;
                ultimaChecagemFrames = agora;
            }
        }
    }

    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public boolean ehPosicaoValida(Posicao p) {
        return cj.ehPosicaoValida(fases[faseAtual].getInimigos(), p);
    }

    public void addPersonagem(Personagem umPersonagem) {
        fases[faseAtual].addInimigo(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        fases[faseAtual].removerInimigo(umPersonagem);
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    @Override
    public void paint(Graphics g) {

        g = this.getBufferStrategy().getDrawGraphics();

        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);

        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                int mapaLinha = cameraLinha + i;
                int mapaColuna = cameraColuna + j;

                if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
                    try {
                        Image newImage = Toolkit.getDefaultToolkit().getImage(
                                new java.io.File(".").getCanonicalPath() + Consts.PATH + "bricks.png");
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                                Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        cj.desenhaTudo(fases[faseAtual]);
        cj.processaTudo(fases[faseAtual]);
        
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
        setTitle("-> Cell: " + (fases[faseAtual].getPlayer().getPosicao().getLinha()) + ", " + (fases[faseAtual].getPlayer().getPosicao().getColuna()));
    }

    private void atualizaCamera() {
        int linha = fases[faseAtual].getPlayer().getPosicao().getLinha();
        int coluna = fases[faseAtual].getPlayer().getPosicao().getColuna();

        cameraLinha = Math.max(0, Math.min(linha - Consts.RES / 2, Consts.MUNDO_ALTURA - Consts.RES));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.RES / 2, Consts.MUNDO_LARGURA - Consts.RES));
    }

    public Player getPlayer() {
        return fases[faseAtual].getPlayer();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
