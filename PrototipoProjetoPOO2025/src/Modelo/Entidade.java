package Modelo;

import Auxiliar.Consts;
import Auxiliar.Direcao;
import Auxiliar.Posicao;
import Fases.Fase;

import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entidade implements Serializable{
    protected boolean transponivel = false;
    protected boolean mortal = true;
    protected boolean visivel = true;
    //Controle da posicao
    protected boolean noAr = false;
    protected Posicao posicaoInicial;
    protected Direcao direcao;
    protected Fase fase;
    protected float velocidadeX = 0.75f;
    protected float velocidadeAr = 0;
    protected float gravidade = 0.07f;
    protected float velocidadePulo = -4.75f;
    protected float velocidadeQuedaPosColisao = 0.5f;
    //Hitbox
    protected Rectangle2D.Float hitbox;

    public Entidade(Fase fase, float xInicial, float yInicial){
        posicaoInicial = new Posicao(xInicial,yInicial);
        this.fase = fase;
        direcao = new Direcao();
    }

    protected abstract void atualizarPosicao();

    public abstract void atualizarEntidade();

    public abstract void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY);

    protected final void inicializarHitbox(int largura, int altura){
        hitbox = new Rectangle2D.Float(posicaoInicial.getX(),posicaoInicial.getY(),largura,altura);
    } 
    
        protected void atualizarPosicaoX(float vx) {
        if(isPosValida(hitbox.x+vx,hitbox.y)){
            hitbox.x += vx;
        }
    }

    protected void atualizarPosicaoY(){
        //Verifica se o personagem esta no chao
        if(isEntidadeNoChao()) noAr = false;
        else noAr = true;

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

    protected boolean isEntidadeNoChao(){
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

        //se esse personagem eh transponivel, ele nao tem colisao com outros personagens
        if(this.isTransponivel()) return true; 
        
        //caso contrario, checa colisao com outros personagens da fase
        Rectangle2D.Float hitboxFutura = new Rectangle2D.Float(x,y,hitbox.width,hitbox.height);

        for(Personagem i : fase.getPersonagens()){
            if(!i.isTransponivel()){
                if(!this.equals(i) && hitboxFutura.intersects(i.getHitbox())) return false;
            }
        }

        //Se passou em todos os testes, a posicao eh valida
        return true;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public boolean isTransponivel(){
        return transponivel;
    }

    public void setVisivel(boolean visivel){
        this.visivel = visivel;
    }
}