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
    protected ArrayList<Personagem> inimigos = new ArrayList<Personagem>();
    //Arrays de "buffer" para evitar alteracoes no array de inimigos enquanto ele é percorrido:
    protected ArrayList<Personagem> inimigosParaRemover = new ArrayList<Personagem>();  
    protected ArrayList<Personagem> inimigosParaAdicionar = new ArrayList<Personagem>();

    protected ArrayList<Projetil> projeteis = new ArrayList<Projetil>();
    //Arrays de "buffer" para evitar alteracoes no array de projeteis enquanto ele é percorrido:
    protected ArrayList<Projetil> projeteisParaRemover = new ArrayList<Projetil>();  
    protected ArrayList<Projetil> projeteisParaAdicionar = new ArrayList<Projetil>();

    protected Hero player;
    protected Portal portal;
    protected int[][] infoCenario;
    protected BufferedImage[] tileset;
    protected BufferedImage background;
    //Controle da camera
    protected int cameraOffsetX = 0;
    protected int bordaCameraEsquerda = (int)(0.5 * Consts.MUNDO_LARGURA * Consts.CELL_SIDE);
    protected int bordaCameraDireita = (int)(0.5 * Consts.MUNDO_LARGURA * Consts.CELL_SIDE);
    protected int larguraFase;
    protected int maxCameraOffsetX;

    protected int cameraOffsetY = 0;
    protected int bordaCameraSuperior = (int)(0.5 * Consts.MUNDO_ALTURA * Consts.CELL_SIDE);
    protected int bordaCameraInferior = (int)(0.5 * Consts.MUNDO_ALTURA * Consts.CELL_SIDE);
    protected int alturaFase;
    protected int maxCameraOffsetY;

    public Fase(int larguraFase, int alturaFase) {
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

    protected abstract void adicionarPersonagens();

    protected abstract void carregarImagens();

    public void atualizarFase() {
        player.atualizarPersonagem();
        portal.atualizarPersonagem();
        checarPlayerNaBorda();
        //Atualiza inimigos
        if(!inimigos.isEmpty()) {
            for(Personagem i : inimigos) {
                i.atualizarPersonagem();
            }
        }
        //Atualiza projeteis
        if(!projeteis.isEmpty()) {
            for(Projetil p : projeteis) {
                p.atualizarPersonagem();
            }
        }
        //adiciona e remove projeteis e inimigos do array
        atualizarArrayProjeteis(); 
        atualizarArrayInimigos();
    }

    public void desenharCenario(Graphics g){
        g.drawImage(background,0,0,Consts.CELL_SIDE*Consts.MUNDO_LARGURA, Consts.CELL_SIDE*Consts.MUNDO_ALTURA,null);
        
        for (int i = 0; i < infoCenario.length; i++) {
            for (int j = 0; j < infoCenario[0].length; j++) {
                g.drawImage(tileset[infoCenario[i][j]], j * Consts.CELL_SIDE - cameraOffsetX, i * Consts.CELL_SIDE - cameraOffsetY, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            }
        }
    }

    public void desenharFase(Graphics g) {
        portal.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
        //Cria uma copia do array antes de desenhar para evitar percorrer o array enquanto o modifica
        ArrayList<Projetil> copiaProjeteis = new ArrayList<>(projeteis);
        if(!copiaProjeteis.isEmpty()) {
            for(Projetil p : copiaProjeteis){
                p.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
            }
        }
        //Cria uma copia do array antes de desenhar para evitar percorrer o array enquanto o modifica
        ArrayList<Personagem> copiaInimigos = new ArrayList<>(inimigos);
        if(!copiaInimigos.isEmpty()) {
            for(Personagem i : copiaInimigos){
                i.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
            }
        }
        player.desenharPersonagem(g, cameraOffsetX, cameraOffsetY);
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
        projeteisParaAdicionar.add(projetil);
    }

    public void removerProjetil(Projetil projetil){
        projeteisParaRemover.add(projetil);
    }

    protected void atualizarArrayProjeteis(){
        projeteis.removeAll(projeteisParaRemover);
        projeteisParaRemover.clear();
        projeteis.addAll(projeteisParaAdicionar);
        projeteisParaAdicionar.clear();
    }

    public void addInimigo(Personagem inimigo){
        inimigosParaAdicionar.add(inimigo);
    }

    public void removerInimigo(Personagem inimigo){
        inimigosParaRemover.add(inimigo);
    }

    protected void atualizarArrayInimigos(){
        inimigos.removeAll(inimigosParaRemover);
        inimigosParaRemover.clear();
        inimigos.addAll(inimigosParaAdicionar);
        inimigosParaAdicionar.clear();
    }
}
