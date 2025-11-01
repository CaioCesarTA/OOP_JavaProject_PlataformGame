package Controler;

import Auxiliar.Consts;

import java.awt.image.BufferedImage;

public class Fase0 extends Fase {
    
    public Fase0(){
        super();
        carregarImagens();
        carregarInfoNivel();
    }
    
    @Override
    protected final void carregarImagens() {
        BufferedImage temp = importarImagem("terreno/level1.png");
        tileset = new BufferedImage[48];
        for(int j=0;j<4;j++){
            for(int i=0;i<12;i++){
                int indice = j*12 + i;
                tileset[indice] = temp.getSubimage(i*32, j*32, 32, 32);
            }
        }
        background = new BufferedImage[5];
        temp = importarImagem("background/level0/1.png");
        background[0] = temp;
        temp = importarImagem("background/level0/2.png");
        background[1] = temp;
        temp = importarImagem("background/level0/3.png");
        background[2] = temp;
        temp = importarImagem("background/level0/4.png");
        background[3] = temp;
        temp = importarImagem("background/level0/5.png");
        background[4] = temp;
    }

    public boolean isSolido(float x, float y){
        if(infoCenario[(int)(y/Consts.CELL_SIDE)][(int)(x/Consts.CELL_SIDE)] != 11) return true;
        else return false;
    }
    
    protected final void carregarInfoNivel(){
        int[][] info = {
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},
            {00,01,01,01,01,01,01,01,01,01,01,01,01,01,01,02},
            {12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14},
            {12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14},
            {12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14},
            {24,25,25,25,25,25,25,25,25,25,25,25,25,25,25,26}
        };
        infoCenario = info;
    }   
    
    
}
