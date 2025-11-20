package Auxiliar;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {

    public static int TIRO = 0;
    public static int TAPA = 1;
    public static int TIROINIMIGO = 2;
    public static int PORTAL = 3;
    public static int PEDRA = 4;
    public static int MACHUCADO = 5;
    public static int ESPADA = 6;
    public static int CORONHADA = 7;
    public static int PICADA = 8;
    public static int JOGO_SALVO = 9;

    private Clip[] efeito;

    private float volume = 0.85f;


    public Audio() {
        carregaEfeitoSonoro();
    }

    private void carregaEfeitoSonoro() {
        String[] nomesEfeitos = { "tiro", "tapa", "tiroInimigo", "portal", "pedra", "machucado", "espada", "coronhada", "picada", "save" };
        efeito = new Clip[nomesEfeitos.length];
        for (int i = 0; i < efeito.length; i++)
            efeito[i] = getClip(nomesEfeitos[i]);

        AtualizaVolume();

    }

    private Clip getClip(String nome) {
        try {
            String caminhoCompleto = new java.io.File(".").getCanonicalPath() + "/" + "sounds/" + nome + ".wav";
            File arquivoSom = new File(caminhoCompleto);
            System.out.println("Carregando som de: " + arquivoSom.getAbsolutePath());
            if (!arquivoSom.exists()) {
                System.err.println("ERRO: Arquivo nao encontrado: " + caminhoCompleto);
                return null;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(arquivoSom);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (Exception e) {
            System.err.println("Erro ao carregar audio: " + nome);
            e.printStackTrace();
        }
        return null;
    }

    public void tocaEfeito(int efeitos) {
        efeito[efeitos].setMicrosecondPosition(0);
        efeito[efeitos].start();
    }

    private void AtualizaVolume() {
        for (Clip c : efeito) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float alcance = gainControl.getMaximum() - gainControl.getMinimum();
            float ganho = (alcance * volume) + gainControl.getMinimum();
            gainControl.setValue(ganho);
        }
    }
}