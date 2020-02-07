package com.developer.demetrio.etributos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.util.ConstantesSistemas;

public class MainActivity extends AppCompatActivity {
    private Button btEntrar;
    private EditText login;
    private EditText senha;

    private SQLiteDatabase conexao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.idLogin);
        senha = (EditText) findViewById(R.id.idSenha);
        btEntrar = (Button) findViewById(R.id.id_button_logar);
      //  new ConexaoDataBase(this);
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEntrar();
            }
        });
    }

    public void onEntrar() {

      /*  if (this.login.getText().toString().equals(ConstantesSistemas.LOGIN)
                && this.senha.getText().toString().equals(ConstantesSistemas.SENHA)) {
          */

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conectarAoBanco();
            try {
                if (new RepositorioImovel(this.conexao).getQtdImoveis() > 0) {
                    Intent activity = new Intent(getApplicationContext(), Menu.class);
                    desconectarBanco();
                    startActivity(activity);
                } else {
                    Intent carregar = new Intent(getApplicationContext(), Carregar.class);
                    desconectarBanco();
                    startActivity(carregar);
                }
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
  /*      } else {
            Toast.makeText(this, "login e/ou senha incorretos!", Toast.LENGTH_LONG).show();
            this.senha.setText("");
            this.login.setText("");
        }
*/

  this.conexao.close();
    }

    private void desconectarBanco() {
        this.conexao.close();
    }

    private void conectarAoBanco(){
        this.conexao = new ConexaoDataBase().concectarComBanco(this);
    }

}
