package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.databases.DataBasesUtil;
import com.developer.demetrio.databases.constantes._DataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.adapters.utils.ItemMenu;
import com.developer.demetrio.adapters.MenuAdapter;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.util.ConstantesSistemas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent activity;
                switch (itens.get(i).getDescricao()) {
                    case "Lista de Imóveis":
                        try {
                            conexao = new ConexaoDataBase().concectarComBanco(getApplicationContext());
                            RepositorioImovel imoveis = new RepositorioImovel(conexao);
                           id = imoveis.primeiraPosicaoNaoEmitida();
                           if (id == 0) {
                               id = imoveis.primeiraPosicao();
                           }
                        } catch (RepositorioException e) {
                            e.printStackTrace();
                        }
                        conexao.close();
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
                        SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(getApplicationContext());
                        RepositorioImovel imoveis = new RepositorioImovel(conexao);
                        long totalDeRegistro = 0L;
                       try {
                            totalDeRegistro = imoveis.getQtdImoveis();
                        } catch (RepositorioException e) {
                            e.printStackTrace();
                        }
                       conexao.close();
                       Bundle bundle = new Bundle();
                       bundle.putLong("total_de_registros", totalDeRegistro);
                        activity = new Intent(getApplicationContext(), Relatorio.class);
                        activity.putExtras(bundle);
                        startActivity(activity);
                        break;

                    case "Finalizar":

                    /*  getApplicationContext().deleteDatabase(_DataBase.NOME_DO_BANCO);*/
                        try {
                            Thread.sleep(5);
                            exportarDb();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    //    onBackPressed();
                        break;

                    case "Sair":
                        onBackPressed();
                      //  onDestroy();
                            break;

                }
            }
        });

    }

    private void exportarDb() {
        File dir = new File(ConstantesSistemas.CAMINHO_SDCARD);
        DataBasesUtil utils = new DataBasesUtil();
        System.out.println("ANTES DE CHAMAR O MÉTODO "+ConstantesSistemas.PATH_DB);
       utils.exportDataBase(this, this  ,_DataBase.NOME_DO_BANCO, ".db", true);


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
