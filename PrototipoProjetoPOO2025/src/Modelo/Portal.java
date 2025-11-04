package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Portal extends Personagem {
    //ID das animacoes do Portal
    public static final int PARADO = 0;
    public static final int ABRINDO = 1;
    public static final int FECHANDO = 2;

    public Portal(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        animation_speed = 15;
        acaoAtual = PARADO;
        mortal = false;
        transponivel = true;
        carregarAnimacoes();
        inicializarHitbox();
    }

    @Override
    protected void inicializarHitbox() {
        hitbox = new Rectangle2D.Float(posicaoInicial.getX(),posicaoInicial.getY(),32,64);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        if(id_acao == FECHANDO) return 6;
        return 8;
    }

    @Override
    protected void carregarAnimacoes() {
        BufferedImage temp = Fase.importarImagem("portal/portal.png");
        this.imagens = new BufferedImage[3][8];
        for(int i=0;i<3;i++){
            for(int j=0;j<8;j++){
                imagens[i][j] = temp.getSubimage(j*64, i*64, 64, 64);
            }
        }

    }

    @Override
    protected void atualizarAcaoAtual() {
        acaoAtual = PARADO;
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void desenharPersonagem(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 48 + 128;
        int posYimg = (int)(hitbox.y) - 48;
        int larguraImg = 128 * -1;
        int alturaImg = 128;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
    }
}
