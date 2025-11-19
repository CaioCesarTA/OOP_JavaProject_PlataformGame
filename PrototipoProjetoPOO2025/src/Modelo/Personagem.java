package Modelo;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Auxiliar.Direcao;
import Auxiliar.Posicao;
import Controler.LoadSave;
import Fases.Fase;

public abstract class Personagem extends Entidade {
    //Controle das acoes do personagem
    protected int acaoAtual = 0;
    protected boolean atirando = false;
    protected boolean correndo = false;
    protected boolean pulando = false;
    protected boolean socando = false;
    protected boolean jaAtacou = false;
    //Controle de animacoes
    protected transient BufferedImage[][] imagens;
    protected int animation_tick = 0;
    protected int animation_index = 0;
    protected int animation_speed = 10;
    protected int flipX = 0;
    protected int flipW = 1;
    //Tempo entre tiros (cooldown)
    protected boolean podeAtirar = true;
    protected int tempoEntreTiros =  Consts.FPS / 2; // um tiro a cada 60 quadros == 0.5 segundo
    protected int cooldownTiro = 0;
    //Tempo entre socos
    protected boolean podeSocar = true;
    protected int tempoEntreSocos =  Consts.FPS; // um soco a cada 120 quadros == 1 segundo
    protected int cooldownSoco = 0;
    //Paths das imagens
    protected String pathSpritesheet;
    protected int tamSprite;


    public Personagem(Fase fase, float xInicial, float yInicial) {
        super(fase,xInicial,yInicial);
        acaoAtual = getAcaoIdle();
    }

    public abstract int getQtdSprites(int id_acao);

    protected abstract void atualizarAcaoAtual();

    @Override
    public void carregarImagens() {
        carregarAnimacoes();
    }

    protected final void carregarAnimacoes() {
        BufferedImage temp = LoadSave.importarImagem(pathSpritesheet);
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
        atualizarAtaqueDePerto();
    }

    protected final void atualizarAtaqueDePerto(){
        if(ataquePerto==null) return;
        ataquePerto.x = hitbox.x + hitbox.width;
        if(flipW == -1) ataquePerto.x = hitbox.x - ataquePerto.width;  
        ataquePerto.y = hitbox.y + 10;
    }

    protected void atualizarCooldowns(){
        if(!podeAtirar){
            cooldownTiro++;
            if(cooldownTiro >= tempoEntreTiros) {
                podeAtirar = true;
                cooldownTiro = 0;
            }
        }
        if(!podeSocar){
            cooldownSoco++;
            if(cooldownSoco >= tempoEntreSocos) {
                podeSocar = true;
                cooldownSoco = 0;
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

    public abstract int getAcaoIdle();

    public void resetarPersonagem(){
        morto = false;
        visivel = true;
        vidaAtual = vidaMaxima;
        resetAniTick();
        resetarPosicao();
        acaoAtual = getAcaoIdle();
        pulando = false;
        atirando = false;
        socando = false;
        correndo = false;
        flipX = 0;
        flipW = 1;
        direcao.resetarDirecao();
        atualizarAtaqueDePerto();
    }

    public void ataca(Rectangle2D.Float hitbox,Rectangle2D.Float ataquePerto){
        if (intersecta(fase.getPlayer().hitbox, ataquePerto)){
            fase.getPlayer().sofrerDano(dano);
            jaAtacou = true;
        }
    }


}
