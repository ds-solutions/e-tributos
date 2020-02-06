package com.developer.demetrio.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

public abstract class ImageUtil {

    public static final int MIDIA_FOTO = 0;
    private static final String PREFERENCIA_MIDIA = "midia_prefs";
    private static final String[] CHAVE_PREF = new String[]{".jpg", ".png", ".3gp"};

    public static Bitmap carregarImagem(File foto, int largura, int altura) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(foto.getAbsolutePath(), bmOptions);
        int fotoW = bmOptions.outWidth;
        int fotoH = bmOptions.outHeight;
        System.out.println("Caminho: "+foto.toString());
        System.out.println("largura: "+largura+" altura: "+altura + " fotoW: "+ fotoW+" fotoH: "+fotoH);

        int scala = Math.min(fotoW/largura, fotoH/altura);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scala;
        Bitmap bitmap = BitmapFactory.decodeFile(foto.getAbsolutePath(), bmOptions);
        return bitmap;
    }


    public static void salvarUltimaMida(Activity activity, int midiaFoto, String midia) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFERENCIA_MIDIA, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(CHAVE_PREF[midiaFoto], midia)
                .commit();
        Intent midiaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.parse(midia);
        midiaScanIntent.setData(uri);
        activity.sendBroadcast(midiaScanIntent);
    }
}
