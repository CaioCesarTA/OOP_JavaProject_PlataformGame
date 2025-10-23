package Fases;

import Modelo.Personagem;
import Modelo.Player;
import java.util.ArrayList;

public /*abstract*/ class Fase{
    
    private ArrayList<Personagem> inimigos;
    private Player player;
    private int[][] info;
    
    public Fase(){
        inimigos = new ArrayList<Personagem>();
    }
    
    protected void carregarPersonagens(){
        
    }
    
    protected void desenharFase(){
        
    }
    
    protected void carregarInfo(){
        
    }
    
    public void addInimigo(Personagem inimigo){
        inimigos.add(inimigo);
    }
    
    public void removerInimigo(Personagem inimigo){
        inimigos.remove(inimigo);
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int[][] getInfo() {
        return info;
    }
    
    
    
}
