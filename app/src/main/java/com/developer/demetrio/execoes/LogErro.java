package com.developer.demetrio.execoes;

import com.developer.demetrio.util.ConstantesSistemas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogErro {
    public void criarArquivoDeLog(RepositorioException e, String data){
        String nomeDoArquivo = data+"_"
                + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());

        File dirMida = new File(ConstantesSistemas.CAMINHO_LOG);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
        try {
            FileWriter log = new FileWriter(new File(dirMida, nomeDoArquivo));
            StringBuilder erro = new StringBuilder();
            erro.append(data);
            erro.append("\n");
            erro.append(e.getMessage().toString());
            log.write(erro.toString());
            log.flush();
            log.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void criarArquivoDeLog(IOException e, String data){
        String nomeDoArquivo = data+"_"
                + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());

        File dirMida = new File(ConstantesSistemas.CAMINHO_LOG);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
        try {
            FileWriter log = new FileWriter(new File(dirMida, nomeDoArquivo));
            StringBuilder erro = new StringBuilder();
            erro.append(data);
            erro.append("\n");
            erro.append(e.getMessage().toString());
            log.write(erro.toString());
            log.flush();
            log.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void criarArquivoDeLog(Exception e, String data){
        String nomeDoArquivo = data+"_"
                + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());

        File dirMida = new File(ConstantesSistemas.CAMINHO_LOG);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
        try {
            FileWriter log = new FileWriter(new File(dirMida, nomeDoArquivo));
            StringBuilder erro = new StringBuilder();
            erro.append(data);
            erro.append("\n");
            erro.append(e.getMessage().toString());
            log.write(erro.toString());
            log.flush();
            log.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
