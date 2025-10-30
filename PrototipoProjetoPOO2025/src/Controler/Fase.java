package Controler;

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
    protected BufferedImage[] imagens;
    
    public Fase(){
        this.player = new Hero();
    }
    
    public static BufferedImage importarImagem(String nome_da_imagem) { 
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(new java.io.File(".").getCanonicalPath() + Consts.PATH + nome_da_imagem));
        } catch (IOException ex) {
            System.getLogger(Fase.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return imagem;
    }
    
    protected abstract void carregarImagens();

    public void atualizarFase(){
        player.atualizarPersonagem();
    }
    
    protected void desenharFase(Graphics g) {
        for(int i=0;i<infoCenario[0].length;i++){
            for(int j=0;j<infoCenario.length;j++){
                g.drawImage(imagens[infoCenario[i][j]], j*32, i*32, 32, 32, null);
            }
        }
        player.desenharPersonagem(g);
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }
    
    
    
}
