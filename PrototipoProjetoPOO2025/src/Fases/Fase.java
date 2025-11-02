package Fases;

import Auxiliar.Consts;
import Modelo.Hero;
import Modelo.Personagem;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public abstract class Fase {
    protected ArrayList<Personagem> inimigos;
    protected Hero player;
    protected int[][] infoCenario;
    protected BufferedImage[] tileset;
    protected BufferedImage[] background;

    public Fase() {
        this.player = new Hero(this);
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
    }

    public void desenharFase(Graphics g) {
        for(int i=0; i<background.length;i++){
            g.drawImage(background[i],0,0,Consts.CELL_SIDE*Consts.MUNDO_LARGURA, Consts.CELL_SIDE*Consts.MUNDO_ALTURA,null);
        }
        for (int i = 0; i < infoCenario.length; i++) {
            for (int j = 0; j < infoCenario[0].length; j++) {
                g.drawImage(tileset[infoCenario[i][j]], j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            }
        }
        player.desenharPersonagem(g);
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }

    public Hero getPlayer() {
        return player;
    }
}
