package Modelo;

import Auxiliar.Direcao;
import Auxiliar.Posicao;
import Fases.Fase;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Personagem implements Serializable {
    protected boolean bTransponivel;
    protected boolean bMortal;
    //Controle da posicao
    protected Posicao posicaoInicial;
    protected Direcao direcao;
    protected Fase fase;
    protected float velocidadeX = 0.75f;
    protected float velocidadeAr = 0;
    protected float gravidade = 0.07f;
    protected float velocidadePulo = -4.75f;
    protected float velocidadeQuedaPosColisao = 0.5f;
    //Controle de animacoes
    protected BufferedImage[][] imagens;
    protected int animation_tick = 0;
    protected int animation_index = 0;
    protected int animation_speed = 10;
    protected int flipX = 0;
    protected int flipW = 1;
    //Controle das acoes do personagem
    protected int acaoAtual = 0;
    protected boolean atirando = false;
    protected boolean correndo = false;
    protected boolean pulando = false;
    protected boolean socando = false;
    protected boolean noAr = false;
    //Hitbox
    protected Rectangle2D.Float hitbox;

    public Personagem(Fase fase) {
        this.fase = fase;
        direcao = new Direcao();
        posicaoInicial = new Posicao(100,100);
    }

    protected abstract void inicializarHitbox();

    public abstract int getQtdSprites(int id_acao);

    protected abstract void carregarAnimacoes();

    protected abstract void atualizarAcaoAtual();

    protected abstract void atualizarPosicao();

    public abstract void desenharPersonagem(Graphics g);

    protected void atualizarTickAnimacao(){
        animation_tick++;
        if(animation_tick >= animation_speed){
            animation_tick = 0;
            animation_index++;
            if(animation_index >= getQtdSprites(acaoAtual)){
                animation_index = 0;
                atirando = false;
                socando = false;
            }
        }
    }

    protected void resetAniTick() {
        animation_tick=0;
        animation_index=0;
    }

    public void atualizarPersonagem(){
        atualizarAcaoAtual();
        atualizarPosicao();
        atualizarTickAnimacao();
    }

    protected void atualizarPosicaoX(float vx) {
        if(isPosValida(hitbox.x+vx,hitbox.y)){
            hitbox.x += vx;
        }
    }

    protected void atualizarPosicaoY(){
        //Verifica se o personagem esta no chao
        if(isPersonagemNoChao()) noAr = false;
        else noAr = true;

        if(noAr) pulando = false;

        if (pulando && !noAr) velocidadeAr = velocidadePulo;

        if(isPosValida(hitbox.x,hitbox.y+velocidadeAr)){
            hitbox.y += velocidadeAr;
            velocidadeAr += gravidade;
        }
        else{
            if(velocidadeAr > 0){
                noAr = false;
                velocidadeAr = 0;
            }
            else velocidadeAr = velocidadeQuedaPosColisao;
        }
    }

    protected boolean isPersonagemNoChao(){
        //Checar pixel inferior esquerdo e inferior direito
        if(!fase.isSolido(hitbox.x,hitbox.y+hitbox.height+1)
           && !fase.isSolido(hitbox.x+hitbox.width,hitbox.y+hitbox.height+1)){
                return false;
        }
        return true;
    }

    public boolean isPosValida(float x, float y){
        return     !fase.isSolido(x, y)
                && !fase.isSolido(x + hitbox.width, y + hitbox.height)
                && !fase.isSolido(x + hitbox.width, y)
                && !fase.isSolido(x, y + hitbox.height)
                && !fase.isSolido(x, y + hitbox.height / 2)
                && !fase.isSolido(x + hitbox.width, y + hitbox.height / 2);
    }

    public Posicao getPosicaoInicial(){
        return posicaoInicial;
    }

    public Direcao getDirecao(){
        return direcao;
    }

    public void setPulando(boolean pulando){
        this.pulando = pulando;
    }

    public void resetarPosicao(){
        hitbox.x = posicaoInicial.getX();
        hitbox.y = posicaoInicial.getY();
    }

    public void resetarPersonagem(){
        resetAniTick();
        resetarPosicao();
        acaoAtual = 0;
        pulando = false;
        atirando = false;
        socando = false;
        correndo = false;
    }
}
