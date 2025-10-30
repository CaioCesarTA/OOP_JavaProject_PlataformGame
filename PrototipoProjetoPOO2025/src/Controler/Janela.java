package Controler;

import javax.swing.JFrame;

public class Janela extends JFrame {
    public Janela(Tela tela){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(tela);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null); //Faz a janela aparecer no meio da tela
        this.setVisible(true);
    }
}
