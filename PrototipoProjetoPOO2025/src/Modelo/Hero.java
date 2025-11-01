package Modelo;

import Auxiliar.Posicao;
import Controler.Fase;
import Controler.Tela;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    public Hero(Fase fase){
        super(fase);
        carregarAnimacoes();
        incializarHitbox();
    }

    protected final void incializarHitbox(){
        hitbox = new Rectangle2D.Float(posicao.getX(),posicao.getY(),22,64);
    }

    @Override
    public void desenharPersonagem(Graphics g) {
        int posXimg = (int)(hitbox.x) + flipX - 48;
        int posYimg = (int)(hitbox.y) - 64;
        int larguraImg = 128 * flipW;
        int alturaImg = 128;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg, posYimg, larguraImg, alturaImg, null);
        g.setColor(Color.BLUE);
        g.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
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
    protected void atualizarTickAnimacao(){
        animation_tick++;
        if(animation_tick >= animation_speed){
            animation_tick = 0;
            animation_index++;
            if(animation_index >= getQtdSprites(acaoAtual)){
                animation_index = 0;
                if(atirando) atirando = false;
                if(socando) socando = false;
                if(pulando) pulando = false;
            }
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
    public void setCorrendo(boolean correndo){
        this.correndo = correndo;
    }

    public void setAtirando(boolean atirando){
        this.atirando = atirando;
    }

    public void setSocando(boolean socando){
        this.socando = socando;
    }

    @Override
    protected void atualizarPosicao() {
        float vx = velocidadeX;
        if(correndo) vx *= 2;

        if(direcao.isEsquerda()) {
            atualizarPosicaoX(-vx);
            flipX = 120;
            flipW = -1;
        }

        if(direcao.isDireita()) {
            atualizarPosicaoX(vx);
            flipX = 0;
            flipW = 1;
        }

        atualizarPosicaoY();
    }

    @Override
    protected void atualizarAcaoAtual() {
        int acaoInicial = acaoAtual;

        acaoAtual = PARADO;
        if (direcao.isEsquerda() && !direcao.isDireita())
            acaoAtual = ANDANDO;

        if (!direcao.isEsquerda() && direcao.isDireita())
            acaoAtual = ANDANDO;

        if (acaoAtual == ANDANDO && correndo)
            acaoAtual = CORRENDO;

        if (pulando)
            acaoAtual = PULANDO;

        if (atirando)
            acaoAtual = ATIRANDO;

        if (socando)
            acaoAtual = SOCANDO;


        if(acaoInicial != acaoAtual)
            resetAniTick();
    }

}
