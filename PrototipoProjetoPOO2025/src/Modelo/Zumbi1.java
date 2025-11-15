package Modelo;

import Fases.Fase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Zumbi1 extends Personagem {
    //ID das animacoes do Zumbi1
    private static final int PARADO = 0;
    private static final int ANDANDO = 1;
    private static final int ATACANDO = 2;
    private static final int LEVANDO_DANO = 3;
    private static final int MORRENDO = 4;
    
    public Zumbi1(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        vidaMaxima = vidaAtual = 3;
        animation_speed = 20;
        velocidadeX = 0.25f;
        direcao.setDireita(true);
        carregarAnimacoes("inimigos/zumbi1.png",128);
        inicializarHitbox(25,63);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        switch (id_acao) {
            case PARADO:
                return 6;
            case ANDANDO:
                return 10;
            case ATACANDO:
                return 5;
            case LEVANDO_DANO:
                return 4;
            case MORRENDO:
                return 5;
            default:
                return 0;
        }
    }

    @Override
    protected void atualizarAcaoAtual() {
        if(morto) {
            acaoAtual = MORRENDO;
            if(animation_index>=getQtdSprites(MORRENDO)-1) fase.removerInimigo(this);
            return;
        }

        int acaoInicial = acaoAtual;
        acaoAtual = PARADO;

        if(acaoInicial == LEVANDO_DANO){
            acaoAtual = LEVANDO_DANO;
            if(animation_index>=getQtdSprites(LEVANDO_DANO)-1) acaoAtual = PARADO;
        }

        else if (direcao.isEsquerda() && !direcao.isDireita())
            acaoAtual = ANDANDO;

        else if (!direcao.isEsquerda() && direcao.isDireita())
            acaoAtual = ANDANDO;           

        if(acaoAtual!=acaoInicial) 
            resetAniTick();
    }

    @Override
    protected void atualizarPosicao() {

        atualizarPosicaoY();

        if(morto || acaoAtual==LEVANDO_DANO) return;

        float vx = velocidadeX;
        if(direcao.isEsquerda()) {
            vx *= -1;
            flipX = 120;
            flipW = -1;
        }
        else{
            flipX = 0;
            flipW = 1;
        }
        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(vx);
        float novaPosicao = hitbox.x;
        
        if(posicaoAnterior==novaPosicao) {
            direcao.inverterDirecaoAtual();
        }
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 48 + flipX;
        int posYimg = (int)(hitbox.y) - 64;
        int larguraImg = 128 * flipW;
        int alturaImg = 128;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }

    }

    @Override
    public void sofrerDano(int dano){
        if(vidaAtual<=0) return;
        vidaAtual -= dano;
        acaoAtual = LEVANDO_DANO;
        resetAniTick();
        if(vidaAtual <= 0) morto = true;
    }

    public void ataca(){
        
    }
    
}
