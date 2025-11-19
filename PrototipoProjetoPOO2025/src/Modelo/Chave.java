package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class Chave extends Entidade implements Controlado {
    private Porta portaControlada;
    private transient BufferedImage imagemChave;
    private boolean acompanharPlayer;
    private float velocidadeY = 0.125f;
    private int flipW = 1, flipX = 0;

    public Chave(Fase fase, float xInicial, float yInicial, Porta portaControlada){
        super(fase,xInicial,yInicial);
        this.portaControlada = portaControlada;
        mortal = false;
        transponivel = true;
        acompanharPlayer = false;
        inicializarHitbox(30, 8);
        carregarImagens();
    }

    @Override
    protected void atualizarPosicao() {
        if(acompanharPlayer){
            flipW = fase.getPlayer().getFlipW();
            flipX = 0;
            hitbox.x = fase.getPlayer().getHitbox().x +fase.getPlayer().getHitbox().width;
            if(flipW == -1) {
                hitbox.x = fase.getPlayer().getHitbox().x - hitbox.width;
                flipX = 32;
            }
            hitbox.y = fase.getPlayer().getHitbox().y + 20;
        }
        else{
            hitbox.y -= velocidadeY;
            if(Math.abs(hitbox.y - posicaoInicial.getY()) > 5) velocidadeY *= -1;
        }
    }

    @Override
    public void atualizarEntidade() {
        if(!visivel) return;
        atualizarPosicao();
        if(hitbox.intersects(fase.getPlayer().getHitbox()) && !acompanharPlayer) {
            acompanharPlayer = true;
        }
        if(portaControlada==null) return;
        if(acompanharPlayer && hitbox.intersects(portaControlada.getHitbox())) {
            portaControlada.mudarEstado();
            fase.removerEntidade(this);
        }
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        if(!visivel) return;
        int posXimg = (int)(hitbox.x) + flipX;
        int posYimg = (int)(hitbox.y) - 15;
        int larguraImg = 32 * flipW;
        int alturaImg = 32;

        g.drawImage(imagemChave, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }

    @Override
    public void mudarEstado() {
        visivel = true;
    }

    @Override
    public final void carregarImagens() {
        BufferedImage temp = LoadSave.importarImagem("fases/fase5/tilesetFase5.png");
        imagemChave = temp.getSubimage(224,96,32,32);
    }

}
