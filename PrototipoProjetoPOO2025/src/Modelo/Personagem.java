package Modelo;

import Auxiliar.Consts;
import Auxiliar.Direcao;
import Auxiliar.Posicao;
import Fases.Fase;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Personagem implements Serializable {
    protected boolean transponivel = false;
    protected boolean mortal = true;
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
    //Tempo entre tiros (cooldown)
    protected boolean podeAtirar = true;
    public static final int tempoEntreTiros =  Consts.FPS / 2; // um tiro a cada 60 quadros == 0.5 segundo
    public int cooldownTiro = 0;

    public Personagem(Fase fase, float xInicial, float yInicial) {
        posicaoInicial = new Posicao(xInicial,yInicial);
        this.fase = fase;
        direcao = new Direcao();
    }

    protected final void inicializarHitbox(int largura, int altura){
        hitbox = new Rectangle2D.Float(posicaoInicial.getX(),posicaoInicial.getY(),largura,altura);
    }

    public abstract int getQtdSprites(int id_acao);

    protected abstract void carregarAnimacoes();

    protected abstract void atualizarAcaoAtual();

    protected abstract void atualizarPosicao();

    public abstract void desenharPersonagem(Graphics g, int cameraOffsetX, int cameraOffsetY);

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
        atualizarCooldowns();
        atualizarTickAnimacao();
    }

    protected void atualizarCooldowns(){
        if(!podeAtirar){
            cooldownTiro++;
            if(cooldownTiro >= tempoEntreTiros) {
                podeAtirar = true;
                cooldownTiro = 0;
            }
        }
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
        //Checa colisao com o cenario
        if(    fase.isSolido(x, y)
            || fase.isSolido(x + hitbox.width, y + hitbox.height)
            || fase.isSolido(x + hitbox.width, y)
            || fase.isSolido(x, y + hitbox.height)
            || fase.isSolido(x, y + hitbox.height / 2)
            || fase.isSolido(x + hitbox.width, y + hitbox.height / 2)) return false;

        Rectangle2D.Float hitboxFutura = new Rectangle2D.Float(x,y,hitbox.width,hitbox.height);

        //Checa colisao com player, se o personagem nao for Hero
        if(!this.equals(fase.getPlayer()) && hitboxFutura.intersects(fase.getPlayer().getHitbox())) return false;

        //Checa colisao com outros personagens da fase
        for(Personagem i : fase.getInimigos()){
            if(!i.isTransponivel()){
                if(!this.equals(i) && hitboxFutura.intersects(i.getHitbox())) return false;
            }
        }

        //Se passou em todos os testes, a posicao eh valida
        return true;
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
        flipX = 0;
        flipW = 1;
        direcao.resetarDirecao();
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public boolean isTransponivel(){
        return transponivel;
    }

    public boolean isMortal(){
        return mortal;
    }

}
