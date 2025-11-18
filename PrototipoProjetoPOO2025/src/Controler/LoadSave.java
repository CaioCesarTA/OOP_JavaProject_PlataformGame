package Controler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import Auxiliar.Consts;
import Modelo.Entidade;

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

    public static void salvarEntidade(String nomeArqDestino, Entidade e) {
        File tanque = new File(nomeArqDestino);
        try {
            tanque.createNewFile();
            FileOutputStream canoOut = new FileOutputStream(tanque);
            ZipOutputStream zipOut = new ZipOutputStream(canoOut);

            ZipEntry entry = new ZipEntry("entidade");
            zipOut.putNextEntry(entry);

            ObjectOutputStream serializador = new ObjectOutputStream(zipOut);
            serializador.writeObject(e);
            
            serializador.flush();
            zipOut.closeEntry();
            serializador.close();

            System.out.println("Entidade salva: " + nomeArqDestino);

        } catch (IOException error) {
            System.out.println("Erro ao salvar entidade: " + error.getMessage());
        }
    }

    public static void salvarJogo(String nomeArqDestino, SaveGame s) {
        File tanque = new File(nomeArqDestino);
        try {
            tanque.createNewFile();
            FileOutputStream canoOut = new FileOutputStream(tanque);
            ZipOutputStream zipOut = new ZipOutputStream(canoOut);

            ZipEntry entry = new ZipEntry("SaveJogo");
            zipOut.putNextEntry(entry);

            ObjectOutputStream serializador = new ObjectOutputStream(zipOut);
            serializador.writeObject(s);

            serializador.flush();
            zipOut.closeEntry();
            serializador.close();

            System.out.println("Estado atual do jogo salvo com sucesso em: " + nomeArqDestino);

        } catch (IOException error) {
            System.out.println("Erro ao salvar jogo: " + error.getMessage());
        }
    }

    public static SaveGame carregarSave(String nomeArqOrigem){
        File arquivo = new File(nomeArqOrigem);
        SaveGame save = null;

        try {
            FileInputStream fis = new FileInputStream(arquivo);
            ZipInputStream zipIn = new ZipInputStream(fis);
            ZipEntry entrada = zipIn.getNextEntry();

            if (entrada != null) {
                ObjectInputStream ois = new ObjectInputStream(zipIn);
                save = (SaveGame) ois.readObject();
                zipIn.closeEntry();
                save.recuperarImagens();
            }

            zipIn.close();

        } catch (Exception e) {
            System.out.println("Erro ao carregar save: " + e.getMessage());
        }

        return save;
    }

    public static Entidade carregarEntidade(String nomeArqOrigem) {
        File arquivo = new File(nomeArqOrigem);
        Entidade entidade = null;

        try {
            FileInputStream fis = new FileInputStream(arquivo);
            ZipInputStream zipIn = new ZipInputStream(fis);
            ZipEntry entrada = zipIn.getNextEntry();

            if (entrada != null) {
                ObjectInputStream ois = new ObjectInputStream(zipIn);
                entidade = (Entidade) ois.readObject();
                zipIn.closeEntry();
                entidade.carregarImagens();
            }

            zipIn.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar entidade: " + e.getMessage());
        }

        return entidade;
    }
}
