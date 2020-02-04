package com.developer.demetrio.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CameraUtil {

    public static final int MIDIA_FOTO = 0;
    public static final int REQUESTCODE_FOTO = 1 ;

    private static final String FOTO_RG_FRENTE = "rg_frente";
    private static final String FOTO_RG_VERSO = "rg_frente";
    private static final String FOTO_CPF = "rg_frente";
    private static final String FOTO_ESCRITURA = "rg_frente";

    private static final String[] EXTENSOES = new String[]{".jpg"};
    private static final String PREFENRECIA_MIDIA = "midia_prefs";
    private String fotoPath;

    private static final String[] CHAVE_REFERENCIA =
            new String[]{FOTO_RG_FRENTE, FOTO_RG_VERSO, FOTO_CPF, FOTO_ESCRITURA};

    public File novaMidia(int tipo, int chave, String prefix) {
        String nomeDaMidia = prefix +"_"+ CHAVE_REFERENCIA[chave]+ "_" + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());

        File dirMida = new File(ConstantesSistemas.CAMINHO_FOTOS);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
       File image = new File(dirMida, nomeDaMidia+EXTENSOES[tipo]);
        fotoPath = image.getAbsolutePath();
        return image;
    }

    public void salvarFoto(Context context, int tipo, String midia) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFENRECIA_MIDIA, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(CHAVE_REFERENCIA[tipo], midia)
                .commit();
      //  Intent midiaScanIntent = new Intent(Intent.ACTION_MEDIA_S)
    }


}
