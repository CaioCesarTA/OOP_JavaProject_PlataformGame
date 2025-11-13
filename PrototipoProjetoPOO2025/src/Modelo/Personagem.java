package Modelo;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Auxiliar.Direcao;
import Auxiliar.Posicao;
import Fases.Fase;

public abstract class Personagem extends Entidade {
//Controle das acoes do personagem
    protected int acaoAtual = 0;
    protected boolean atirando = false;
    protected boolean correndo = false;
    protected boolean pulando = false;
    protected boolean socando = false;
    protected boolean morto = false;
    //Controle da vida
    protected int vidaMaxima = 5;
    protected int vidaAtual = vidaMaxima;
    protected int dano = 1;
    //Controle de animacoes
    protected BufferedImage[][] imagens;
    protected int animation_tick = 0;
    protected int animation_index = 0;
    protected int animation_speed = 10;
    protected int flipX = 0;
    protected int flipW = 1;
    //Tempo entre tiros (cooldown)
    protected boolean podeAtirar = true;
    protected int tempoEntreTiros =  Consts.FPS / 2; // um tiro a cada 60 quadros == 0.5 segundo
    protected int cooldownTiro = 0;


    public Personagem(Fase fase, float xInicial, float yInicial) {
        super(fase,xInicial,yInicial);
    }

    public abstract int getQtdSprites(int id_acao);

    protected abstract void atualizarAcaoAtual();

    protected final void carregarAnimacoes(String pathSpritesheet, int tamSprite) {
        BufferedImage temp = Fase.importarImagem(pathSpritesheet);
        int alturaImg = temp.getHeight() / tamSprite;
        int larguraImg = temp.getWidth() / tamSprite;
        this.imagens = new BufferedImage[alturaImg][larguraImg];
        for(int i=0;i<alturaImg;i++){
            for(int j=0;j<larguraImg;j++){
                imagens[i][j] = temp.getSubimage(j*tamSprite, i*tamSprite, tamSprite, tamSprite);
            }
        }
    }

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

    @Override
    public void atualizarEntidade(){
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

    @Override
    protected void atualizarPosicaoY(){
        //Verifica se o personagem esta no chao
        if(isEntidadeNoChao()) noAr = false;
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

    public void sofrerDano(int dano){
        if(vidaAtual<=0) return;
        vidaAtual -= dano;
        if(vidaAtual <= 0) morto = true;
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
        morto = false;
        vidaAtual = vidaMaxima;
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

    public boolean isMortal(){
        return mortal;
    }

    public boolean isMorto(){
        return morto;
    }

    public void setMorto(boolean morto){
        this.morto = morto;
    }

    public int getDano(){
        return dano;
    }

    public int getVidaAtual(){
        return vidaAtual;
    }
}
