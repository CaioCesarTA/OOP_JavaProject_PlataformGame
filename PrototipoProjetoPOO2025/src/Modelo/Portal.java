package Modelo;

import Fases.Fase;

import java.awt.*;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;

public class Portal extends Personagem {
    //ID das animacoes do Portal
    private static final int PARADO = 0;
    private static final int ABRINDO = 1;
    private static final int FECHANDO = 2;
    //Controle de avancar fase
    private boolean avancarFase = false;
    private int tempoAnimacaoFechando = 0;

    public Portal(Fase fase, float xInicial, float yInicial) {
        super(fase, xInicial, yInicial);
        animation_speed = 15;
        acaoAtual = PARADO;
        mortal = false;
        transponivel = true;
        carregarAnimacoes("portal/portal.png",64);
        inicializarHitbox(32,64);
    }

    @Override
    public int getQtdSprites(int id_acao) {
        if(id_acao == FECHANDO) return 6;
        return 8;
    }

    @Override
    protected void atualizarAcaoAtual() {
        //Vai para a proxima fase se o Player entrar no portal
        if(hitbox.contains(fase.getPlayer().getHitbox())){
            acaoAtual = FECHANDO;
            fase.getPlayer().setVisivel(false);
            tempoAnimacaoFechando++;
            if(tempoAnimacaoFechando >= getQtdSprites(FECHANDO)*animation_speed) avancarFase = true;
        }
    }

    public boolean podeAvancarFase(){
        return avancarFase;
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 48 + 128;
        int posYimg = (int)(hitbox.y) - 48;
        int larguraImg = 128 * -1;
        int alturaImg = 128;

        BufferedImage imagemAtual = imagens[acaoAtual][animation_index];

        g.drawImage(imagemAtual, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }
    }
}
