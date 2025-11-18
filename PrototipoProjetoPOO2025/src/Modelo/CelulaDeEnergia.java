package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class CelulaDeEnergia extends Entidade {
    private transient BufferedImage imagemCelula;
    private float velocidadeY = 0.125f;
    private Controlado entidadeControlada;

    public CelulaDeEnergia(Fase fase, float xInicial, float yInicial, Controlado entidadeControlada){
        super(fase,xInicial,yInicial);
        transponivel = true;
        mortal = false;
        this.entidadeControlada = entidadeControlada;
        carregarImagens();
        inicializarHitbox(32, 32);
    }

    @Override
    protected void atualizarPosicao() {
        hitbox.y -= velocidadeY;
        if(Math.abs(hitbox.y - posicaoInicial.getY()) > 5) velocidadeY *= -1;
    }

    @Override
    public void atualizarEntidade() {
        atualizarPosicao();
        if(hitbox.intersects(fase.getPlayer().getHitbox())) {
            if(entidadeControlada!=null) entidadeControlada.mudarEstado();
            fase.removerEntidade(this);
        }
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x);
        int posYimg = (int)(hitbox.y);
        int larguraImg = 32;
        int alturaImg = 32;

        g.drawImage(imagemCelula, posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }

    @Override
    public void carregarImagens() {
        imagemCelula = LoadSave.importarImagem("entidades/celulaEnergia.png");
    }

}
