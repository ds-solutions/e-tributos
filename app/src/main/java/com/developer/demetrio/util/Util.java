package com.developer.demetrio.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.developer.demetrio.excecoes.FachadaException;
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.tributos.ListaImoveis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void chamaProximo(Context context, Imovel imovel, boolean enviar) {
      /*  if (enviar) {
            Fachada.getInstance().enviarEmBackground(imovel);
        }
        Intent it = new Intent(context, TabsActivity.class);
        it.putExtra("imovel", Fachada.getInstance().buscarImovelContaPosicao(imovel.getPosicao(), Boolean.valueOf(true)));
        context.startActivity(it);
   */ }


    public static void enviarEmBackGround(Imovel imovel, Context context) {
        try {
            Fachada.setContext(context);
            Fachada.getInstance().enviarEmBackground(imovel);
        }catch (FachadaException f) {
            Log.e(ConstantesSistemas.CATEGORIA, f.getMessage());
            f.printStackTrace();
        }
    }

    public static double arredondar(double valor, int i) {
        return 12.00;
    }

    public static String convertDateToDateStr(Date date) {
        return dateFormat.format(date);
    }
}
