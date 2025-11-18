package Modelo;

import Fases.Fase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.sound.sampled.ReverbType;

import Auxiliar.Consts;

public class Labubu extends Personagem {
    //ID das animacoes do Labubu
    private static final int PARADO = 0;
    private static final int ANDANDO = 1;
    private static final int ATACANDO = 2;
    private static final int PULANDO = 3;
    private static final int LEVANDO_DANO = 4;
    private static final int MORRENDO = 5;
    private static final int ATIRANDO = 6;
    //Controle dos ataques do Labubu
    private Rectangle2D.Float areaVisao;
    private Rectangle2D.Float virar;
    private boolean comecouBossfight = false;
    //Controle do cenario
    private Rectangle2D.Float[] plataformas;
    private Bau bau;
    private PortaCastelo portaAbre;
    private PortaCastelo portaFecha;
    private Chave chave;
    
    public Labubu(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        mortal = false;
        vidaMaxima = vidaAtual = 1;
        dano = 2;
        tempoEntreTiros = Consts.FPS; //um tiro por segundo
        animation_speed = 15;
        velocidadeX = 1.75f;
        direcao.setEsquerda(true);
        flipW = -1;
        flipX = 116;
        pathSpritesheet = "inimigos/Labubu.png";
        tamSprite = 32;
        carregarAnimacoes();
        inicializarHitbox(50,70);
        inicializarAtaquePerto(50,60);

        areaVisao = new Rectangle2D.Float(hitbox.x,hitbox.y,200,50);
        atualizarAreaVisao();
        virar = new Rectangle2D.Float(hitbox.x,hitbox.y,30,60);
        atualizarvirar();

        plataformas = new Rectangle2D.Float[4];
        //chao
        plataformas[0] = new Rectangle2D.Float(32*Consts.CELL_SIDE,25*Consts.CELL_SIDE,960,128);
        //plataforma esquerda
        plataformas[1] = new Rectangle2D.Float(32*Consts.CELL_SIDE,19*Consts.CELL_SIDE,256,160);
        //plataforma direita
        plataformas[2] = new Rectangle2D.Float(54*Consts.CELL_SIDE,19*Consts.CELL_SIDE,256,160);
        //plataforma central
        plataformas[3] = new Rectangle2D.Float(42*Consts.CELL_SIDE,14*Consts.CELL_SIDE,320,160);
        
        bau = new Bau(fase, 47*Consts.CELL_SIDE, 18*Consts.CELL_SIDE);
        portaAbre = new PortaCastelo(fase, 62*Consts.CELL_SIDE, 27*Consts.CELL_SIDE, false, 1);
        portaFecha = new PortaCastelo(fase, 31*Consts.CELL_SIDE, 27*Consts.CELL_SIDE,true, -1);
        chave = new Chave(fase,bau.getHitbox().x,bau.getHitbox().y-32,portaAbre);
        chave.setVisivel(false);

        fase.addEntidade(bau);
        fase.addEntidade(portaAbre);
        fase.addEntidade(portaFecha);
        fase.addEntidade(chave);
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
            case PULANDO:
                return 8;
            case LEVANDO_DANO:
                return 4;
            case MORRENDO:
                return 8;
            case ATIRANDO:
                return 4;
            default:
                return 0;
        }
    }

    @Override
    protected void atualizarAcaoAtual() {
        if(morto) {
            acaoAtual = MORRENDO;
            if(animation_index>=getQtdSprites(MORRENDO)-1) {
                transponivel = true;
                visivel = false;
                bau.mudarEstado();
                chave.mudarEstado();
            }
            return;
        }
        if(!comecouBossfight && areaVisao.intersects(fase.getPlayer().getHitbox())){
            comecouBossfight = true;
            portaFecha.mudarEstado();
            mortal = true;
        }
        if(!comecouBossfight){
            acaoAtual = PARADO;
            return;
        }

        int acaoInicial = acaoAtual;
        acaoAtual = PARADO;

        if(vendoPlayer() && podeAtirar && !morto){
            acaoAtual = ATIRANDO;
            
            podeAtirar = false;
            resetAniTick();
            return;
        }

        if(acaoInicial == LEVANDO_DANO){
            acaoAtual = LEVANDO_DANO;
            if(animation_index>=getQtdSprites(LEVANDO_DANO)-1) acaoAtual = PARADO;
            return;
        }
        if(acaoInicial == ATIRANDO) {
            acaoAtual = ATIRANDO;
            if(animation_index>=getQtdSprites(ATIRANDO)-1) {
                acaoAtual = PARADO;
                fase.addEntidade(new Projetil(fase,hitbox.x+70*flipW,hitbox.y+10,flipW,1,"projeteis/bullet3.png"));
            }
            return;
        }

        if (direcao.isEsquerda() && !direcao.isDireita())
            acaoAtual = ANDANDO;

        if (!direcao.isEsquerda() && direcao.isDireita())
            acaoAtual = ANDANDO;

        if (intersecta(fase.getPlayer().hitbox, ataquePerto)) {
            acaoAtual = ATACANDO;
            if (fase.getPlayer().isMorto())
                acaoAtual = ANDANDO;
            if(animation_index == 0)
                jaAtacou = false;
            if ( (animation_index == 3 ||animation_index == 5)  && !jaAtacou){
                ataca(fase.getPlayer().hitbox, ataquePerto);
            }
        }

        if(acaoAtual!=acaoInicial)
            resetAniTick();
    }

    public boolean vendoPlayer(){
        return fase.getPlayer().getHitbox().intersects(areaVisao) && !fase.getPlayer().isMorto();
    }

    @Override
    protected void atualizarPosicao() {
        atualizarPosicaoY();

        if(morto 
        || acaoAtual == LEVANDO_DANO 
        || acaoAtual == ATACANDO 
        || acaoAtual == ATIRANDO
        || acaoAtual == PARADO
        ) return;
        if (virar.intersects(fase.getPlayer().getHitbox())) {
            direcao.inverterDirecaoAtual();
        }
        //Teleporta para as plataformas
        if(plataformas[0].contains(fase.getPlayer().getHitbox()) && !plataformas[0].intersects(hitbox)) {
            hitbox.x = (float) plataformas[0].getCenterX();
            hitbox.y = (float) plataformas[0].getMaxY() - hitbox.height - 1;
        }
        else if(plataformas[3].contains(fase.getPlayer().getHitbox()) && !plataformas[3].intersects(hitbox)) {
            hitbox.x = (float) plataformas[3].getCenterX();
            hitbox.y = (float) plataformas[3].getMaxY() - hitbox.height - 1;
        }
        else if(plataformas[1].contains(fase.getPlayer().getHitbox()) && !plataformas[1].intersects(hitbox)) {
            hitbox.x = (float) plataformas[1].getX();
            hitbox.y = (float) plataformas[1].getMaxY() - hitbox.height - 1;
        }
        else if(plataformas[2].contains(fase.getPlayer().getHitbox()) && !plataformas[2].intersects(hitbox)) {
            hitbox.x = (float) plataformas[2].getMaxX() - hitbox.width;
            hitbox.y = (float) plataformas[2].getMaxY() - hitbox.height - 1;
        }

        float vx = velocidadeX;
        if(direcao.isEsquerda()) {
            vx *= -1;
            flipX = 116;
            flipW = -1;
        }
        else{
            flipX = 0;
            flipW = 1;
        }

        if(!fase.isSolido(hitbox.x+vx, hitbox.y+hitbox.height+1)) vx=0;

        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(vx);
        float novaPosicao = hitbox.x;

        if(posicaoAnterior==novaPosicao) {
            direcao.inverterDirecaoAtual();
        }
    }

    @Override
    public void atualizarEntidade(){
        super.atualizarEntidade();
        atualizarAreaVisao();
        atualizarvirar();
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        if(!visivel) return;

        int posXimg = (int)(hitbox.x) - 36 + flipX;
        int posYimg = (int)(hitbox.y) - 42;
        int larguraImg = 116 * flipW;
        int alturaImg = 116;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.BLUE);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
            g.setColor(Color.RED);
            g.drawRect((int) ataquePerto.x - cameraOffsetX, (int) ataquePerto.y - cameraOffsetY, (int) ataquePerto.width, (int) ataquePerto.height);
            g.drawRect((int)areaVisao.x - cameraOffsetX, (int)areaVisao.y- cameraOffsetY, (int)areaVisao.width, (int)areaVisao.height);
            g.setColor(Color.BLUE);
            for(Rectangle2D.Float r : plataformas) g.drawRect((int)r.x - cameraOffsetX,(int)r.y - cameraOffsetY, (int)r.width, (int)r.height);
            g.setColor(Color.GREEN);
            g.drawRect((int) virar.x - cameraOffsetX, (int) virar.y - cameraOffsetY, (int) virar.width, (int) virar.height);
        }
    }

    private void atualizarAreaVisao(){
        areaVisao.x = hitbox.x + 200;
        if(flipW==-1) areaVisao.x -= areaVisao.width - hitbox.width + 400;
        areaVisao.y = hitbox.y;
    }

    private void atualizarvirar(){
        virar.x = hitbox.x - 30;
        if(flipW==-1) virar.x -= virar.width - hitbox.width - 60;
        virar.y = hitbox.y;
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
