package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MotivosDaNaoEntrega extends AppCompatActivity {

    private TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivos_da_nao_entrega);
        titulo = findViewById(R.id.id_title_relatorio);

    }
}
