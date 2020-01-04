package com.developer.demetrio.etributos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btEntrar;
    private EditText login;
    private EditText senha;
    long tentativa = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.idLogin);
        senha = (EditText) findViewById(R.id.idSenha);
        btEntrar = (Button) findViewById(R.id.id_button_logar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEntrar();
            }
        });
    }

    public void onEntrar() {
        Toast.makeText(this, "Logando...", Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent carregar = new Intent(getApplicationContext(), Carregar.class);
        startActivity(carregar);
    }
}
