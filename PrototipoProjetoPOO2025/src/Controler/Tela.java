package Controler;

import Auxiliar.Consts;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Tela extends JPanel {
    private ControleDeJogo cj;
    
    public Tela(ControleDeJogo cj) {
        this.cj = cj;
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(Consts.RES); //Configura o tamanho do painel
        addKeyListener(cj);
        addMouseListener(cj);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        cj.desenhaTudo(g);
    }

}
