package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.databases.constantes._DataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.adapters.utils.ItemMenu;
import com.developer.demetrio.adapters.MenuAdapter;
import com.developer.demetrio.repositorio.RepositorioImovel;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private String conveterId;
    private long id;
    SQLiteDatabase conexao;
    ConexaoDataBase conexaoDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ListView listView = (ListView) findViewById(R.id.idListViewMenu);
        final ArrayList<ItemMenu> itens = preencherMenu();
        ArrayAdapter adapter = new MenuAdapter(this, itens);
        listView.setAdapter(adapter);

       // this.conexao = this.conexaoDataBase.concectarComBanco(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent activity;
                switch (itens.get(i).getDescricao()) {
                    case "Lista de Imóveis":
                        try {
                            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(getApplicationContext());
                            RepositorioImovel imoveis = new RepositorioImovel(conexao);
                           id = imoveis.primeiraPosicaoNaoEmitida();
                           if (id == 0) {
                               id = imoveis.primeiraPosicao();
                           }
                        } catch (RepositorioException e) {
                            e.printStackTrace();
                        }
                        activity = new Intent(getApplicationContext(), ListaImoveis.class);
                        if (id > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("id", id);
                            activity.putExtras(bundle);
                        }
                        startActivity(activity);
                        break;

                    case "Consultar Imóveis":
                        activity = new Intent(getApplicationContext(), ConsultarImoveis.class);
                        startActivity(activity);
                        break;

                    case "Selecionar Impressora":
                        activity = new Intent(getApplicationContext(), SelecionarImpressora.class);
                        startActivity(activity);
                        break;

                    case "Relatório":
                        activity = new Intent(getApplicationContext(), Relatorio.class);
                        startActivity(activity);
                        break;

                    case "Finalizar":
                      /*  activity = new Intent(getApplicationContext(), Finalizar.class);
                        startActivity(activity);*/
                      getApplicationContext().deleteDatabase(_DataBase.NOME_DO_BANCO);
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onBackPressed();
                        break;

                    case "Sair":
                        onBackPressed();
                      //  onDestroy();
                            break;

                }
            }
        });

    }

    private ArrayList<ItemMenu> preencherMenu(){
        ArrayList<ItemMenu> itens = new ArrayList<>();

        ItemMenu item = new ItemMenu(R.drawable.imovel, "Lista de Imóveis");
        itens.add(item);

        item = new ItemMenu(R.drawable.buscar_imoveis, "Consultar Imóveis");
        itens.add(item);

        item = new ItemMenu(R.drawable.impressora, "Selecionar Impressora");
        itens.add(item);

        item = new ItemMenu(R.drawable.relatorio, "Relatório");
        itens.add(item);

        item = new ItemMenu(R.drawable.finalizar, "Finalizar");
        itens.add(item);

        item = new ItemMenu(R.drawable.sair, "Sair");
        itens.add(item);
        return itens;
    }
}
