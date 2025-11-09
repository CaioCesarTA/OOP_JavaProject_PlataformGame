package Modelo;

import Fases.Fase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Biker extends Personagem {
    //ID das animacoes do Biker
    public static final int PARADO = 0;
    public static final int ATACANDO = 1;
    public static final int ANDANDO = 2;
    
    public Biker(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        animation_speed = 15;
        acaoAtual = PARADO;
        direcao.setDireita(true);
        carregarAnimacoes();
        inicializarHitbox(25,63);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        if(id_acao == PARADO) return 4;
        if(id_acao == ATACANDO) return 6;
        if(id_acao == ANDANDO) return 6;
        return 0;
    }

    @Override
    protected final void carregarAnimacoes() {
        BufferedImage temp = Fase.importarImagem("inimigos/biker.png");
        this.imagens = new BufferedImage[3][6];
        for(int i=0;i<3;i++){
            for(int j=0;j<6;j++){
                imagens[i][j] = temp.getSubimage(j*48, i*48, 48, 48);
            }
        }
    }

    @Override
    protected void atualizarAcaoAtual() {
    }

    @Override
    protected void atualizarPosicao() {
        acaoAtual = ANDANDO;
        
        atualizarPosicaoY();

        float vx = velocidadeX;
        if(direcao.isEsquerda()) {
            vx *= -1;
            flipX = 40;
            flipW = -1;
        }
        else{
            flipX = 0;
            flipW = 1;
        }
        float posicaoAnterior = hitbox.x;
        atualizarPosicaoX(vx);
        float novaPosicao = hitbox.x;
        
        if(posicaoAnterior==novaPosicao) {
            direcao.inverterDirecaoAtual();
            acaoAtual = PARADO;
        }
    }

    @Override
    public void desenharPersonagem(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 10 + flipX;
        int posYimg = (int)(hitbox.y) - 25;
        int larguraImg = 88 * flipW;
        int alturaImg = 88;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
    }
    
}
