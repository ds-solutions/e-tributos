package com.developer.demetrio.etributos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class EnviarDados extends AppCompatActivity {
    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressBarAnimador;
    private TextView textStatus;
    private int cont = 0;
    int max = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_dados);
        this.textStatus = (TextView) this.findViewById(R.id.status_do_envio);
        init();
        textStatus.setText("");

    }

    private void init() {
        progressBarAnimation = (ProgressBar) findViewById(R.id.id_progressBar);
        progressBarAnimador = ObjectAnimator.ofInt(progressBarAnimation, "progress", 60);

             new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                do {
                                Thread.sleep(600);
                                cont++;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        progressBarAnimation.setProgress(cont, true);
                                    }
                                    System.out.println(cont);
                                   // exibirStatus();
                                    if (cont == 99) {
                                        return;
                                    }
                                } while (cont < 99);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();

    }

    private void exibirStatus() {
        textStatus.setText(String.valueOf(cont+"/"+max)+" imóveis enviados...");
    }
}
