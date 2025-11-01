package Auxiliar;

import Controler.Fase;
import com.sun.source.doctree.HiddenTree;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Posicao implements Serializable {
    //Posicao
    private float x;
    private float y;
    private Fase fase;

    public Posicao(float x, float y, Fase fase) {
        this.fase = fase;
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
