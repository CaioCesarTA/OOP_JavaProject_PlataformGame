package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Auxiliar.Audio;
import Auxiliar.Consts;
import Controler.LoadSave;
import Fases.Fase;

public class Terminal extends Entidade {
    private ArrayList<Controlado> entidadesControladas = new ArrayList<>();
    private transient BufferedImage[] sprites;
    private int indiceImagem = 0;
    
    public Terminal(Fase fase, float xInicial, float yInicial){
        super(fase,xInicial,yInicial);
        transponivel = true;
        mortal = false;
        carregarImagens();
        inicializarHitbox(24, 46);
    }

    @Override
    protected void atualizarPosicao() {
    }

    @Override
    public void atualizarEntidade() {
        if(fase.getPlayer().getHitbox().intersects(hitbox)) {
            if(indiceImagem==0) {
                indiceImagem++;
                fase.getAudio().tocaEfeito(Audio.PLIM);
                if(entidadesControladas.isEmpty()) return;
                for(Controlado c : entidadesControladas){
                    c.mudarEstado();
                }
            }
        }
    }

    public void addControlado(Controlado c){
        entidadesControladas.add(c);
    }

    @Override
    public void desenharEntidade(Graphics g, int cameraOffsetX, int cameraOffsetY) {
        int posXimg = (int)(hitbox.x) - 25 /*+ 74*/;
        int posYimg = (int)(hitbox.y) - 25;
        int larguraImg = 72 /* *-1 */;
        int alturaImg = 72;

        g.drawImage(sprites[indiceImagem], posXimg - cameraOffsetX, posYimg - cameraOffsetY, larguraImg, alturaImg, null);

        if(Consts.DESENHAR_HITBOX){
            g.setColor(Color.RED);
            g.drawRect((int)hitbox.x - cameraOffsetX,(int)hitbox.y - cameraOffsetY,(int)hitbox.width,(int)hitbox.height);
        }  
    }

    @Override
    public final void carregarImagens() {
        sprites = new BufferedImage[2];
        BufferedImage temp = LoadSave.importarImagem("entidades/terminal.png");
        sprites[0] = temp.getSubimage(0, 0, 64, 64);
        sprites[1] = temp.getSubimage(64, 0, 64, 64);
    }
}
