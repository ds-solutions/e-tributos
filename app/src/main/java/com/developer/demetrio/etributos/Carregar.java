package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.demetrio.controladores.ControladorTributo;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

public class Carregar extends AppCompatActivity {
    Boolean carregado = false;
    ProgressBar looping;
    long time = 0;
    Thread thread;
    TextView status;
    private List<Imovel> imoveis = new ArrayList<>();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);
        looping = (ProgressBar) findViewById(R.id.idProgressBar);
        status = (TextView) findViewById(R.id.id_status);
        final long l = 2;
        handler = new Handler();

       new Thread(new Runnable() {
           @Override
           public void run() {
               do {
                   try {
                       Thread.sleep(l);

                      carregado = verificarCarregamento();
                   } catch (InterruptedException e) {
                       Toast toast = new Toast(getApplicationContext());
                       toast.setText(e.getMessage().toString());
                       System.out.println(e.getMessage().toString());
                       e.printStackTrace();
                   }

               } while (!carregado);

               Intent menu = new Intent(getApplicationContext(), Menu.class);
               startActivity(menu);
           }

       }).start();

      System.out.println("Terminar sessão");
    }

    public Boolean verificarCarregamento(){
       time++;

       return time == 2000;
    }
}
