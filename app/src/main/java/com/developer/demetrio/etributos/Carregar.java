package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeoutException;

public class Carregar extends AppCompatActivity {
    Boolean carregado = false;
    ProgressBar looping;
    long time = 0;
    Thread thread;
    TextView status;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);
        looping = (ProgressBar) findViewById(R.id.idProgressBar);
        status = (TextView) findViewById(R.id.idStatus);
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
