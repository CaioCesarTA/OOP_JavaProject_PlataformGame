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

    private Clip[] effects;

    private float volume = 0.85f;


    public Audio() {
        loadEffects();
    }

    private void loadEffects() {
        String[] effectNames = { "tiro", "tapa", "tiroInimigo", "portal", "pedra", "machucado", "espada", "coronhada" };
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++)
            effects[i] = getClip(effectNames[i]);

        updateEffectsVolume();

    }

    private Clip getClip(String name) {
        try {

            String caminhoCompleto = new java.io.File(".").getCanonicalPath() + "/" + "sounds/" + name + ".wav";

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
            System.err.println("Erro ao carregar audio: " + name);
            e.printStackTrace();
        }
        return null;
    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }


    private void updateEffectsVolume() {
        for (Clip c : effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}