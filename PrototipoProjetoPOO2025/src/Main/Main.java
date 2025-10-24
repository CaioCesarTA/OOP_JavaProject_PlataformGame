package Main;

public class Main {
    public static void main(String[] argv) {
        Tela tTela = new Tela();
        tTela.setVisible(true);
        tTela.createBufferStrategy(2);
        tTela.run();
    }
}