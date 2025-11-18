package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class Bau extends Entidade implements Controlado{
    private int indiceImagem = 0;
    private transient BufferedImage[] sprites = new BufferedImage[2];
    private boolean aberto = false;

    public Bau(Fase fase, float xInicial, float yInicial){
        super(fase,xInicial,yInicial);
        carregarImagens();
        inicializarHitbox(32, 32);
        transponivel = true;
    }

    @Override
    public void mudarEstado() {
        aberto = true;
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x);
        int posYimg = (int)(hitbox.y);
        int larguraImg = 32;
        int alturaImg = 32;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
        if(aberto){
            indiceImagem = 1;
        }
        else indiceImagem = 0;
    }

    @Override
    protected void carregarImagens() {
        BufferedImage temp = LoadSave.importarImagem("fases/fase5/tilesetFase5.png");
        mortal = false;
        sprites[0] = temp.getSubimage(160, 96, 32, 32);
        sprites[1] = temp.getSubimage(192, 96, 32, 32);
    }
}