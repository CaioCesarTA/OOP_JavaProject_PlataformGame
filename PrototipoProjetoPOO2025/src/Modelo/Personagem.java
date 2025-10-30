package Modelo;

import Auxiliar.Consts;
import Auxiliar.Posicao;
import Controler.Fase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {
    protected BufferedImage[][] imagens;
    protected Posicao pPosicao;
    protected boolean bTransponivel; 
    protected boolean bMortal; 
    //Controle da posicao

    protected Fase fase;
    //Controle de animacoes
    protected int animation_tick = 0;
    protected int animation_index = 0;
    protected int animation_speed = 15; //8 animacoes por segundo e 120 FPS => 15 frames por animacao
    //Controle das acoes do personagem
    int acaoAtual; 
    boolean atacando;

    public abstract int getQtdSprites(int id_acao);

    protected abstract void carregarAnimacoes();
    
    protected abstract void atualizarPosicao();

    public abstract void desenharPersonagem(Graphics g);
    
    protected abstract void atualizarAcaoAtual();

    protected void atualizarTickAnimacao(){
        animation_tick++;
        if(animation_tick >= animation_speed){
            animation_tick = 0;
            animation_index++;
            if(animation_index >= getQtdSprites(acaoAtual)){
                animation_index = 0;
                atacando = false;
            }
        }
    }

    public void atualizarPersonagem(){
        atualizarAcaoAtual();
        atualizarPosicao();
        atualizarTickAnimacao();
    }
    
}
