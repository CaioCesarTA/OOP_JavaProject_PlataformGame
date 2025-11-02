package Controler;

import javax.swing.JFrame;

public class Janela extends JFrame {
    public Janela(Tela tela){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(tela);
        setResizable(false);
        pack();
        setLocationRelativeTo(null); //Faz a janela aparecer no meio da tela
    }
}
