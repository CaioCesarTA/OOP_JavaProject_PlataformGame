package Controler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Auxiliar.Consts;

//Metodos estaticos relacionados ao carregamento de saves, obejetos serializados e imagens
public class LoadSave {
    
    public static BufferedImage importarImagem(String nome_da_imagem) {
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(new java.io.File(".").getCanonicalPath() + Consts.PATH + nome_da_imagem));
        } catch (IOException ex) {
            System.err.println("Erro ao importar imagem: " + nome_da_imagem);
            ex.printStackTrace();
        }
        return imagem;
    }
}
