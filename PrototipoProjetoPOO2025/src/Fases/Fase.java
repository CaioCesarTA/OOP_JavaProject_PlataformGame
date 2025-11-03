package Fases;

import Auxiliar.Consts;
import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Portal;
import Modelo.Projetil;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public abstract class Fase {
    protected ArrayList<Personagem> inimigos;
    protected ArrayList<Projetil> projeteis;
    protected Hero player;
    protected Portal portal;
    protected int[][] infoCenario;
    protected BufferedImage[] tileset;
    protected BufferedImage[] background;
    //Controle da camera
    protected int cameraOffsetX = 0;
    protected int bordaCameraEsquerda = (int)(0.5 * Consts.MUNDO_LARGURA * Consts.CELL_SIDE);
    protected int bordaCameraDireita = (int)(0.5 * Consts.MUNDO_LARGURA * Consts.CELL_SIDE);
    protected int larguraFase;
    protected int maxCameraOffsetX;

    protected int cameraOffsetY = 0;
    protected int bordaCameraSuperior = (int)(0.2 * Consts.MUNDO_ALTURA * Consts.CELL_SIDE);
    protected int bordaCameraInferior = (int)(0.8 * Consts.MUNDO_ALTURA * Consts.CELL_SIDE);
    protected int alturaFase;
    protected int maxCameraOffsetY;

    public Fase(int larguraFase, int alturaFase) {
        projeteis = new ArrayList<Projetil>();
        inimigos = new ArrayList<Personagem>();
        this.larguraFase = larguraFase;
        this.alturaFase = alturaFase;
        maxCameraOffsetX = (larguraFase - Consts.MUNDO_LARGURA) * Consts.CELL_SIDE;
        maxCameraOffsetY = (alturaFase - Consts.MUNDO_ALTURA) * Consts.CELL_SIDE;
    }

    public abstract boolean isSolido(float x, float y);

    public static BufferedImage importarImagem(String nome_da_imagem) {
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(new java.io.File(".").getCanonicalPath() + Consts.PATH + nome_da_imagem));
        } catch (IOException ex) {
            System.err.println("Erro ao importar imagem: " + nome_da_imagem);
            ex.printStackTrace();
        }
        return imagem;
    }

    protected abstract void carregarImagens();

    public void atualizarFase() {
        player.atualizarPersonagem();
        portal.atualizarPersonagem();
        checarPlayerNaBorda();
        if(!projeteis.isEmpty()) {
            for(Projetil p : projeteis) {
                p.atualizarPersonagem();
            }
        }
    }

    public void desenharCenario(Graphics g){
        for(int i=0; i<background.length;i++){
            g.drawImage(background[i],0,0,Consts.CELL_SIDE*Consts.MUNDO_LARGURA, Consts.CELL_SIDE*Consts.MUNDO_ALTURA,null);
        }
        for (int i = 0; i < infoCenario.length; i++) {
            for (int j = 0; j < infoCenario[0].length; j++) {
                g.drawImage(tileset[infoCenario[i][j]], j * Consts.CELL_SIDE - cameraOffsetX, i * Consts.CELL_SIDE - cameraOffsetY, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            }
        }
    }

    public void desenharFase(Graphics g) {
        portal.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
        player.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
        if(!projeteis.isEmpty()) {
            for(Projetil p : projeteis){
                p.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
            }
        }

    }

    public void resetarFase(){
        player.resetarPersonagem();
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }

    public Hero getPlayer() {
        return player;
    }

    protected void checarPlayerNaBordaY(){
        int playerY = (int)player.getHitbox().y;
        int diferencaY = playerY - cameraOffsetY;

        if(diferencaY > bordaCameraInferior){
            cameraOffsetY += diferencaY - bordaCameraInferior;
        }
        else if(diferencaY < bordaCameraSuperior){
            cameraOffsetY += diferencaY - bordaCameraSuperior;
        }
        if(cameraOffsetY>maxCameraOffsetY){
            cameraOffsetY = maxCameraOffsetY;
        }
        else if(cameraOffsetY<0){
            cameraOffsetY = 0;
        }
    }

    protected void checarPlayerNaBordaX(){
        int playerX = (int)player.getHitbox().x;
        int diferencaX = playerX - cameraOffsetX;

        if(diferencaX > bordaCameraDireita){
            cameraOffsetX += diferencaX - bordaCameraDireita;
        }
        else if(diferencaX < bordaCameraEsquerda){
            cameraOffsetX += diferencaX - bordaCameraEsquerda;
        }
        if(cameraOffsetX>maxCameraOffsetX){
            cameraOffsetX = maxCameraOffsetX;
        }
        else if(cameraOffsetX<0){
            cameraOffsetX = 0;
        }
    }

    protected void checarPlayerNaBorda(){
        checarPlayerNaBordaX();
        checarPlayerNaBordaY();
    }

    public void addProjetil(Projetil projetil){
        projeteis.add(projetil);
    }

    public void removerProjetil(){
        projeteis.remove(0);
    }

    public void addInimigo(Personagem inimigo){
        inimigos.add(inimigo);
    }
}
