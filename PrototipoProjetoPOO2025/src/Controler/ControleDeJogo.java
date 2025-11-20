package Controler;

import Auxiliar.Audio;
import Auxiliar.Consts;
import Fases.*;
import Modelo.Entidade;
import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Portal;

import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.PortUnreachableException;
import java.util.ArrayList;

public class ControleDeJogo implements Runnable, KeyListener, MouseListener, DropTargetListener {
    private Janela janela;
    private Tela tela;
    private Thread threadJogo;
    private Fase[] fases;
    private int IDfaseAtual;
    private Audio audio;

    public ControleDeJogo(){
        IDfaseAtual = 0;
        audio = new Audio();
        fases = new Fase[7];
        fases[0] = new FaseInicial();
        fases[1] = new Fase1();
        fases[2] = new Fase2();
        fases[3] = new Fase3();
        fases[4] = new Fase4();
        fases[5] = new Fase5();
        fases[6] = new FaseFinal();
        for(int i = 0; i < fases.length; i++) {
            if(fases[i] != null) {
                fases[i].setAudio(audio);
            }
        }
        tela = new Tela(this);
        janela = new Janela(tela);
        janela.setVisible(true);
        tela.requestFocus();
    }

    public void ComecarLoopJogo(){
        threadJogo = new Thread(this);
        threadJogo.start();
    }

    public void processaTudo(){
        getFaseAtual().atualizarFase();
        
        //Vai para a proxima fase se o Player entrar no portal
        if(getFaseAtual().getPortal() != null && getFaseAtual().getPortal().podeAvancarFase()) {
            avancarFase();
        }

        //Atualiza o titulo da janela
        if(IDfaseAtual==0) janela.setTitle("AVENTURA INTERDIMENSIONAL");
        if(IDfaseAtual>0 && IDfaseAtual<6) janela.setTitle("FASE " + IDfaseAtual);
        if(IDfaseAtual==6) janela.setTitle("FIM");
    }

    public void desenhaTudo(Graphics g){
        getFaseAtual().desenharCenario(g);
        getFaseAtual().desenharFase(g);
    }

    @Override
    public void run() {
        double umSegundo = 1000000000.0; //um segundo em nanossegundos
        double tempoPorFrame = umSegundo / Consts.FPS; // nanossegundos por frame
        
        //Inicializa contadores de tempo
        long ultimaAtualizacao = System.nanoTime();
        long agora;
        long ultimaChecagem = System.nanoTime(); //usado para verificar quantos quadros foram gerados em um segundo
        
        //Contadores de frame
        int frames = 0;
        double deltaTempo = 0;
        
        while(true){
            agora = System.nanoTime();

            deltaTempo += (agora - ultimaAtualizacao)/tempoPorFrame;
            ultimaAtualizacao = agora;
            
            if(deltaTempo >= 1){
                processaTudo();
                tela.repaint();
                frames++;
                deltaTempo--;
            }
            
            //Contador de FPS
            if(agora - ultimaChecagem >= umSegundo) {
                ultimaChecagem = agora;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    private Fase getFaseAtual() {
        return fases[IDfaseAtual];
    }

    public Audio getAudio() {
        return audio;
    }

    private void avancarFase() {
        if(IDfaseAtual<(fases.length-1)) {
            IDfaseAtual++;
            getFaseAtual().resetarFase();
        }
    }

    private void voltarFase(){
        if(IDfaseAtual>0) {
            IDfaseAtual--;
            getFaseAtual().resetarFase();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        getFaseAtual().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(getFaseAtual() instanceof FaseInicial) {
            FaseInicial menu = (FaseInicial) getFaseAtual();

            if(menu.getEstadoFase() == FaseInicial.CENA_CREDITOS) menu.setEstadoFase(FaseInicial.MENU2);
            
            else if (menu.getEstadoFase() == FaseInicial.MENU2){
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) menu.setEstadoFase(FaseInicial.MENU1);
                else if(e.getKeyCode() == KeyEvent.VK_ENTER) menu.setEstadoFase(FaseInicial.CENA_CREDITOS);
            }
            else if (menu.getEstadoFase() == FaseInicial.MENU1){
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) menu.setEstadoFase(FaseInicial.MENU2);
                else if(e.getKeyCode() == KeyEvent.VK_ENTER) menu.setEstadoFase(FaseInicial.CENA_INICIO);
            }

            else if (menu.getEstadoFase() == FaseInicial.CENA_INICIO){
                menu.setEstadoFase(FaseInicial.MENU1);
                avancarFase();
            }
        }
        else if(getFaseAtual() instanceof FaseFinal) System.exit(0);

        else if(e.getKeyCode() == KeyEvent.VK_S){
            getFaseAtual().isSalvando = true;
            Hero heroiAtual = getFaseAtual().getPlayer();
            Portal portalAtual = getFaseAtual().getPortal();
            ArrayList<Personagem> inimigosAtuais = getFaseAtual().getInimigos();
            SaveGame save = new SaveGame(IDfaseAtual, heroiAtual, portalAtual, inimigosAtuais);
            LoadSave.salvarJogo("SAVE/save.zip", save);
            getFaseAtual().isSalvando = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_L){
            SaveGame save = LoadSave.carregarSave("SAVE/save.zip");
            if(save != null) {
                this.IDfaseAtual = save.idFaseAtual;
                Fase faseCarregada = getFaseAtual();
                if(faseCarregada.getInimigos() != null) {
                    faseCarregada.getInimigos().clear();
                }

                save.heroi.setFase(faseCarregada);
                save.portal.setFase(faseCarregada);
                faseCarregada.setPlayer(save.heroi);
                faseCarregada.setPortal(save.portal);

                ArrayList<Personagem> listaInimigosSalvos = save.inimigos;
                if(listaInimigosSalvos != null) {
                    for(Personagem p : listaInimigosSalvos) {
                        p.setFase(faseCarregada);
                        faseCarregada.addInimigo(p);
                    }
                }

                System.out.println("Jogo carregado na fase " + IDfaseAtual);
                tela.repaint();
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_N) avancarFase();
        else if(e.getKeyCode() == KeyEvent.VK_B) voltarFase();
        else getFaseAtual().keyReleased(e);
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);

            Object dado = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

            @SuppressWarnings("unchecked")
            java.util.List<File> lista = (java.util.List<File>) dado;

            File zip = lista.get(0);

            float x = dtde.getLocation().x + getFaseAtual().getCameraOffsetX();
            float y = dtde.getLocation().y + getFaseAtual().getCameraOffsetY();

            // Carrega entidade a partir do ZIP
            Entidade novaEntidade = LoadSave.carregarEntidade(zip.getAbsolutePath());

            if (novaEntidade != null) {
                novaEntidade.setFase(getFaseAtual());
                novaEntidade.setPosicaoHitbox(x, y);
                novaEntidade.getPosicaoInicial().setPosicao(x,y);

                if(novaEntidade instanceof Personagem){
                    Personagem inimigo = (Personagem) novaEntidade;
                    getFaseAtual().addInimigo(inimigo);
                } else {
                    getFaseAtual().addEntidade(novaEntidade);
                }
                
                System.out.println("Entidade adicionada");
            }

        } catch (Exception ex) {
            System.out.println("Erro ao carregar entidade.");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

}
