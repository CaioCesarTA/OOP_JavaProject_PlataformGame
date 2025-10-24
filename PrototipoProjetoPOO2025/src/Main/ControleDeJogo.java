package Main;

import Fases.Fase;
import Modelo.Personagem;
import Auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    
    public void desenhaTudo(Fase fase) {
        for (int i = 0; i < fase.getInimigos().size(); i++)
            fase.getInimigos().get(i).autoDesenho();

        fase.getPlayer().autoDesenho();
    }
    
    public void processaTudo(Fase fase) {
        Personagem pIesimoPersonagem;
        
        for (int i = 0; i < fase.getInimigos().size(); i++) {
            pIesimoPersonagem = fase.getInimigos().get(i);
            
            if (fase.getPlayer().getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) /*TO-DO: verificar se o personagem eh mortal antes de retirar*/ {
                    if (pIesimoPersonagem.isbMortal())
                    fase.removerInimigo(pIesimoPersonagem);
                }
            }
        }
            
        /*
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            
            if (pIesimoPersonagem instanceof Chaser) {
                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
            }
            
        }
        */
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 0; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}
