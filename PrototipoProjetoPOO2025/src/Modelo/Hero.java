package Modelo;

import Controler.Tela;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Hero extends Personagem implements Serializable{
    //ID das animacoes do HERO
    public static final int SOCANDO = 0;
    public static final int MORRENDO = 1;
    public static final int PARADO = 2;
    public static final int PULANDO = 3;
    public static final int CORRENDO = 4;
    public static final int ATIRANDO = 5;
    public static final int ANDANDO = 6;

    public Hero(){
        carregarAnimacoes();
        acaoAtual = PARADO;
    }

    @Override
    public int getQtdSprites(int id_acao) {
        switch (id_acao) {
            case SOCANDO:
                return 3;
            case ATIRANDO:
                return 4;
            case MORRENDO:
                return 5;
            case PARADO:
                return 6;       
            case PULANDO:
            case CORRENDO:
            case ANDANDO:
                return 10;
            default:
                return 0;
        }
    }

    @Override
    protected void carregarAnimacoes() {
        BufferedImage temp = fase.importarImagem("agent/agent.png");
        this.imagens = new BufferedImage[7][10];
        for(int i=0;i<7;i++){
            for(int j=0;j<10;j++){
                imagens[i][j] = temp.getSubimage(j*128, i*128, 128, 128);
            }
        }
    }

    @Override
    protected void atualizarPosicao() {

    }

    @Override
    public void desenharPersonagem(Graphics g) {
        g.drawImage(imagens[acaoAtual][animation_index], 50, 50, null);
    }

    @Override
    protected void atualizarAcaoAtual() {
    }

}
