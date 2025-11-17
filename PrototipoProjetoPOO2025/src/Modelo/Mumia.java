package Modelo;

import Fases.Fase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Mumia extends Personagem {
    //ID das animacoes da Mumia
    private static final int PARADO = 0;
    private static final int ANDANDO = 1;
    private static final int ATACANDO = 2;
    private static final int LEVANDO_DANO = 4;
    private static final int MORRENDO = 3;

    public Mumia(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        vidaMaxima = vidaAtual = 5;
        dano = 2;
        animation_speed = 20;
        velocidadeX = 0.5f;
        direcao.setDireita(true);
        carregarAnimacoes("inimigos/mumia.png",48);
        inicializarHitbox(25,63);
        inicializarataquePerto(40,20);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        switch (id_acao) {
            case PARADO:
                return 4;
            case ANDANDO:
                return 6;
            case ATACANDO:
                return 6;
            case LEVANDO_DANO:
                return 2;
            case MORRENDO:
                return 6;
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

        if (intersecta(fase.getPlayer().hitbox, ataquePerto)) {
            acaoAtual = ATACANDO;
            if (fase.getPlayer().isMorto())
                acaoAtual = ANDANDO;
            if(animation_index == 0)
                jaAtacou = false;
            if (animation_index == 3 && !jaAtacou){
                ataca(fase.getPlayer().hitbox, ataquePerto);
            }
        }

        if(acaoAtual!=acaoInicial)
            resetAniTick();
    }

    @Override
    protected void atualizarPosicao() {

        if(morto || acaoAtual==LEVANDO_DANO || acaoAtual==ATACANDO) return;

        float vx = velocidadeX;
        if(direcao.isEsquerda()) {
            vx *= -1;
            flipX = 60;
            flipW = -1;
        }
        else{
            flipX = 0;
            flipW = 1;
        }

        if(!fase.isSolido(hitbox.x+vx, hitbox.y+hitbox.height+1)) vx=0;
        atualizarPosicaoY();


        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(vx);
        float novaPosicao = hitbox.x;

        if(posicaoAnterior==novaPosicao) {
            direcao.inverterDirecaoAtual();
        }
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) -15 + flipX;
        int posYimg = (int)(hitbox.y) - 38;
        int larguraImg = 100 * flipW;
        int alturaImg = 100;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.BLUE);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
            g.setColor(Color.RED);
            g.drawRect((int) ataquePerto.x - cameraOffsetX, (int) ataquePerto.y - cameraOffsetY, (int) ataquePerto.width, (int) ataquePerto.height);
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
}
