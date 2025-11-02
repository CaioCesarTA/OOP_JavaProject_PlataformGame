package Auxiliar;

import java.io.Serializable;

public class Posicao implements Serializable {
    private float x;
    private float y;

    public Posicao(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosicao(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
