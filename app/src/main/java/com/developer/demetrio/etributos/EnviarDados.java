package com.developer.demetrio.etributos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class EnviarDados extends AppCompatActivity {
    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressBarAnimador;
    private TextView textStatus;
    private int cont = 1;
    private Context context;
    int max = 101;
    private boolean envados = false;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_dados);
        this.handler = new Handler();
        init();

     }

    private void init() {
        progressBarAnimation = (ProgressBar) findViewById(R.id.id_progressBar);
        progressBarAnimador = ObjectAnimator.ofInt(progressBarAnimation, "progress", 60);
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                      progressBarAnimation.setProgress(cont++);
                      handler.post(new Runnable() {
                          @Override
                          public void run() {
                              if (cont < max) {
                                  textStatus(String.valueOf(cont + "/" + (max - 1)) + " dos imóveis enviados...");
                              }
                              if (cont == max -1) {
                                  toastStatus();
                                  envados = true;
                                  abrirDialog();
                              }
                          }
                      });

                if (cont == max) {
                      timer.cancel();
                }
            }

        };
        timer.schedule(timerTask, 800, max);

   }

    private void textStatus(String s) {
        textStatus = findViewById(R.id.status_do_envio);
        textStatus.setText(s);

    }
    private void toastStatus() {
        Toast.makeText(getApplicationContext(), "Imóveis enviados com sucesso!", Toast.LENGTH_LONG)
                .show();

    }

    public void abrirDialog() {
        if (envados) {
            AlertDialog.OnClickListener dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case -1:
                            finishAffinity();
                            break;
                        default:
                            return;
                    }
                }
            };

            new AlertDialog.Builder(this).setMessage("A aplicação será fechada!").setPositiveButton("Ok", dialog).setTitle("Rota finalizada").show();
        }
    }


}
