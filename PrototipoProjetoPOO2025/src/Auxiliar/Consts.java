package Auxiliar;

import java.awt.Dimension;
import java.io.File;

public class Consts {
    public static final int CELL_SIDE = 32; // pixels por lado de celula
    public static final int MUNDO_LARGURA = 40; // celulas na largura visivel na tela
    public static final int MUNDO_ALTURA = 22; // celulas na altura visivel na tela
    public static final boolean DESENHAR_HITBOX = true; // controla se as hitboxes serao desenhadas ou nao

    //Dimensao da tela do jogo
    public static final Dimension RES = new Dimension((int)(CELL_SIDE*MUNDO_LARGURA),(int)(CELL_SIDE*MUNDO_ALTURA));
    //Frames por segundo do jogo
    public static final int FPS = 120;
    //Path para as imagens
    public static final String PATH =File.separator+"imgs"+File.separator;
}