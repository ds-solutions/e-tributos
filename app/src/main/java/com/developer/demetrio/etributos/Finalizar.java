package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.databases.ScriptDLL;
import com.developer.demetrio.databases.constantes._DataBase;

public class Finalizar extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private ConexaoDataBase conexaoDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);
        this.deleteDatabase(_DataBase.NOME_DO_BANCO);
        sair();
    }

    private void sair() {
       onBackPressed();

    }
}
