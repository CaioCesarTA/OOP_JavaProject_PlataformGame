package Modelo;

import Fases.Fase;
import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Hero extends Personagem {
    //ID das animacoes do HERO
    private static final int SOCANDO = 0;
    private static final int MORRENDO = 1;
    private static final int PARADO = 2;
    private static final int PULANDO = 3;
    private static final int CORRENDO = 4;
    private static final int ATIRANDO = 5;
    private static final int ANDANDO = 6;

    public Hero(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        carregarAnimacoes("hero/hero.png",128);
        inicializarHitbox(22,63);
        //TODO: TIRAR ESSA PORRA DPS
        velocidadeX = 10;
        velocidadePulo = -10;
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        if(!visivel) return;
        int posXimg = (int)(hitbox.x) + flipX - 48;
        int posYimg = (int)(hitbox.y) - 64;
        int larguraImg = 128 * flipW;
        int alturaImg = 128;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);
        
        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.BLUE);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }

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
            if(velocidadeAr<=0.5) animation_index++; //nao atualiza a animacao se estiver caindo
            if(animation_index >= getQtdSprites(acaoAtual)){
                animation_index = 0;
                if(morto) animation_index = getQtdSprites(MORRENDO)-1;
                if(atirando) atirando = false;
                if(socando) socando = false;
                if(pulando) pulando = false;
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
    protected void atualizarPosicaoY(){
        //Verifica se o personagem esta no chao
        if(isEntidadeNoChao()) noAr = false;
        else noAr = true;

        if(noAr) pulando = false;

        if(acaoAtual==ATIRANDO) pulando = false;

        if (pulando && !noAr && !morto) velocidadeAr = velocidadePulo;

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



    @Override
    protected void atualizarPosicao() {
        if(!visivel) return; //se tiver invisivel nao se move mais

        atualizarPosicaoY();

        if(acaoAtual==ATIRANDO || socando || morto) return;

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

        if (noAr || pulando)
            acaoAtual = PULANDO;

        if (atirando && noAr) atirando = false;

        if (atirando && !noAr){
            atirando = false;
            if(podeAtirar && !morto && visivel){
                acaoAtual = ATIRANDO;
                fase.addProjetil(new Projetil(fase,hitbox.x+30*flipW,hitbox.y+20,flipW,dano));
                podeAtirar = false;
            }
        }

        if(acaoInicial == ATIRANDO && cooldownTiro<getQtdSprites(ATIRANDO)*animation_speed) acaoAtual = ATIRANDO;

        if (socando && !noAr)
            acaoAtual = SOCANDO;

        if (socando && noAr) socando = false;

        if(morto) {
            acaoAtual = MORRENDO;
        }

        if(acaoInicial == MORRENDO) {
            acaoAtual = MORRENDO;
        }

        if(acaoInicial != acaoAtual)
            resetAniTick();
    }

}
