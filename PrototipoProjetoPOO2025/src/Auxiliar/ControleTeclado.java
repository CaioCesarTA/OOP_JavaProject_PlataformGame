package Auxiliar;

import Main.Tela;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControleTeclado implements KeyListener {
    private final Tela tela;

    public ControleTeclado(Tela tela) {
        this.tela = tela;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                tela.getPlayer().moveUp();
                break;
            case KeyEvent.VK_S:
                tela.getPlayer().moveDown();
                break;
            case KeyEvent.VK_A:
                tela.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                tela.getPlayer().moveRight();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
