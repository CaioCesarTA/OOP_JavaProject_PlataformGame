package Modelo;

import Fases.Fase;
import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Hero extends Personagem {
    //ID das animacoes do HERO
    public static final int SOCANDO = 0;
    public static final int MORRENDO = 1;
    public static final int PARADO = 2;
    public static final int PULANDO = 3;
    public static final int CORRENDO = 4;
    public static final int ATIRANDO = 5;
    public static final int ANDANDO = 6;

    public Hero(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        carregarAnimacoes();
        inicializarHitbox(22,63);
    }

    @Override
    public void desenharPersonagem(Graphics g, int cameraOffsetX, int cameraOffsetY) {
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
                if(atirando) atirando = false;
                if(socando) socando = false;
                if(pulando) pulando = false;
            }
        }
    }

    @Override
    protected final void carregarAnimacoes() {
        BufferedImage temp = Fase.importarImagem("hero/hero.png");
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
    protected void atualizarPosicaoY(){
        //Verifica se o personagem esta no chao
        if(isPersonagemNoChao()) noAr = false;
        else noAr = true;

        if(noAr) pulando = false;

        if(acaoAtual==ATIRANDO) pulando = false;

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



    @Override
    protected void atualizarPosicao() {
        
        atualizarPosicaoY();

        if(acaoAtual==ATIRANDO || socando) return;

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
            if(podeAtirar){
                acaoAtual = ATIRANDO;
                fase.addProjetil(new Projetil(fase,hitbox.x+30*flipW,hitbox.y+20,flipW));
                podeAtirar = false;
            }
        }

        if(acaoInicial == ATIRANDO && cooldownTiro<getQtdSprites(ATIRANDO)*animation_speed) acaoAtual = ATIRANDO;

        if (socando && !noAr)
            acaoAtual = SOCANDO;

        if (socando && noAr) socando = false;

        if(acaoInicial != acaoAtual)
            resetAniTick();
    }

}
