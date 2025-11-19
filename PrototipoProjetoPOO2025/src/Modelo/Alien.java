package Modelo;

import Auxiliar.Audio;
import Fases.Fase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Alien extends Personagem {
    //ID das animacoes do Alien
    private static final int PARADO = 0;
    private static final int ANDANDO = 1;
    private static final int ATIRANDO = 2;
    private static final int LEVANDO_DANO = 3;
    private static final int MORRENDO = 4;
    //Area de visao
    private Rectangle2D.Float areaVisao;
    
    public Alien(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        vidaMaxima = vidaAtual = 3;
        animation_speed = 20;
        velocidadeX = 0.25f;
        direcao.setEsquerda(true);
        pathSpritesheet = "inimigos/alien.png";
        tamSprite = 128;
        carregarAnimacoes();
        inicializarHitbox(25,63);
        areaVisao = new Rectangle2D.Float(hitbox.x,hitbox.y,550,50);
        tempoEntreTiros = Consts.FPS; //um tiro por segundo
    }

    @Override
    public int getQtdSprites(int id_acao) {
        switch (id_acao) {
            case PARADO:
                return 6;
            case ANDANDO:
                return 10;
            case ATIRANDO:
                return 4;
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


        if(vendoPlayer()) {
            if(podeAtirar && !morto){
                acaoAtual = ATIRANDO;
                if(fase.getAudio() != null && !fase.getPlayer().isMorto()) {
                    fase.getAudio().playEffect(Audio.TIROINIMIGO);
                }
                fase.addEntidade(new Projetil(fase,hitbox.x+30*flipW,hitbox.y+10,flipW,dano,"projeteis/bullet2.png"));
                podeAtirar = false;
                resetAniTick();
            }
            else acaoAtual = PARADO;
        }
        if(acaoInicial == ATIRANDO) {
            acaoAtual = ATIRANDO;
            if(animation_index>=getQtdSprites(ATIRANDO)-1) acaoAtual = PARADO;
        }

        if(acaoAtual!=acaoInicial) 
            resetAniTick();
    }

    public boolean vendoPlayer(){
        return fase.getPlayer().getHitbox().intersects(areaVisao) && !fase.getPlayer().isMorto();
    }

    @Override
    protected void atualizarPosicao() {
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

        if(!fase.isSolido(hitbox.x+vx, hitbox.y+hitbox.height+1)) vx=0;
        atualizarPosicaoY();

        if(morto || acaoAtual==LEVANDO_DANO || acaoAtual==ATIRANDO || vendoPlayer()) return;

        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(vx);
        float novaPosicao = hitbox.x;
        
        if(posicaoAnterior==novaPosicao) {
            direcao.inverterDirecaoAtual();
        }

        areaVisao.x = hitbox.x;
        if(flipW==-1) areaVisao.x -= areaVisao.width - hitbox.width;
        areaVisao.y = hitbox.y;
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
            g.setColor(Color.BLUE);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
            g.setColor(Color.RED);
            g.drawRect((int)areaVisao.x- cameraOffsetX, (int)areaVisao.y- cameraOffsetY, (int)areaVisao.width, (int)areaVisao.height);
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

    @Override
    public int getAcaoIdle(){
        return PARADO;
    }
}