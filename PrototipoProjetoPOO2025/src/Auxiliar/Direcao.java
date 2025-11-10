package Auxiliar;

public class Direcao {
    private boolean direita;
    private boolean esquerda;

    public Direcao(){
        direita = false;
        esquerda = false;
    }

    public boolean isDireita(){
        return direita;
    }

    public boolean isEsquerda(){
        return esquerda;
    }

    public void setDireita(boolean direita){
        this.direita = direita;
    }

    public void setEsquerda(boolean esquerda){
        this.esquerda = esquerda;
    }

    public void resetarDirecao(){
        direita = false;
        esquerda = false;
    }

    public void inverterDirecaoAtual(){
        if((direita && esquerda) || (!direita && !esquerda)) return;
        
        if(direita){
            direita = false;
            esquerda = true;
        } 
        else if(esquerda){
            esquerda = false;
            direita = true;
        }
    }

}
