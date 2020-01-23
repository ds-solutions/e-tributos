package com.developer.demetrio.util;

import android.os.Environment;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConstantesSistemas implements Serializable {
    public static final String CAMINHO_ARQUIVO_IPTU_GERADO = Environment.getExternalStorageDirectory()+"/IPTU_gerado";
    private static final long serialVersionUID = 1L;
    public static final int ALERTA_MENSAGEM = 1;
    public static final int ALERTA_PERGUNTA = 2;
    public static final int ANTERIOR = 1;
    public static final byte BAIXAR_ROTA = (byte) 13;
    public static final int BOTAO_REMOVER_TELEFONE_ID = 1;
    public static final int CAMERA = 8291;
    public static final int PERMISSAO_REQUEST = 1;

    public static final int VALOR_LIMITE_CONTA = 1000;

    public static final String CAMINHO_OFFLINE = (Environment.getExternalStorageDirectory() + "/tributos/carregamento");

   // public static final String CAMINHO_SDCARD = (Environment.getExternalStorageDirectory()+"/e_tributos");
    public static final String CAMINHO_SDCARD = (Environment.getExternalStorageState()+"/e_tributos");


    public static final String CAMINHO_VERSAO = (Environment.getExternalStorageDirectory() + "/tributos/versao");
    public static final String CATEGORIA = "TRIBUTO";
    public static final BigDecimal CEM = new BigDecimal("100.00");
    static final int[] CNPJ = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    static final int[] CPF = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    public static final byte DOWNLOAD_APK = (byte) 11;
    public static final byte DOWNLOAD_ARQUIVO = (byte) 0;
    static final byte ENTER = (byte) 13;
    public static final byte ENVIA_IMOVEL = (byte) 1;
    static final byte EOF = (byte) -1;
    public static final Integer NAO = Integer.valueOf(2);
    public static final String PARAMETRO_APK = "apk=";
    public static final String PARAMETRO_ARQUIVO_ROTEIRO = "arquivoRoteiro=";
    public static final String PARAMETRO_IMOVEIS_PARA_REVISITAR = "imoveis=";
    public static final String PARAMETRO_MENSAGEM = "mensagem=";
    public static final String PARAMETRO_TIPO_ARQUIVO = "tipoArquivo=";
    public static final boolean PERMITE_BAIXAR_MULTIPLAS_ROTAS = false;
    public static final byte PING = (byte) 15;
    public static final String RESPOSTA_OK = "*";
    public static final String SEIKO = "Seiko";
    public static final Integer SIM = Integer.valueOf(1);
    public static boolean SIMULADOR = false;
    public static final char TIPO_ARQUIVO_GZ = 'G';
    public static final byte VERIFICAR_VERSAO = (byte) 12;
    private static final String ZEBRA = "Zebra";
    public static final CharSequence[] IMPRESSORAS = new CharSequence[]{ZEBRA, SEIKO};

}
