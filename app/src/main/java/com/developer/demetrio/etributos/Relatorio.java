package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developer.demetrio.adapters.RelatorioAdapter;
import com.developer.demetrio.adapters.utils.ItemRelatorio;;

import java.util.ArrayList;

public class Relatorio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        ListView listView = (ListView) findViewById(R.id.id_list_view_relatorios);
        final ArrayList<ItemRelatorio> itens = popularRelatorios();
        ArrayAdapter adapter = new RelatorioAdapter(this, itens);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


    private ArrayList<ItemRelatorio> popularRelatorios() {
        ArrayList<ItemRelatorio> relatorios = new ArrayList<>();
        ItemRelatorio item = new ItemRelatorio(R.drawable.email_blue, "Total enviados por e-mails");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.whatsapp, "Total enviados por whatsApp");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.imprimindo, "Total impresso");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.atualizado, "Total atualizados");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.positivo, "Total de imóveis visitados");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.casas, "Total de imóveis a visitar");
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.entrega, "Tributos não entregues");
        relatorios.add(item);
        return relatorios;
    }
    /*
    private String dados(int i) {
        String dados = "";
        if (i == 0) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                dados = format.format((imoveis.totalEnviadosPorEmail() * 100) / totalDeRegistro) + "% dos tributos foram enviados por e-mail";
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 1) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                dados = format.format((imoveis.totalEnviadosPorWhatsApp() * 100) / totalDeRegistro) + "% dos tributos foram enviados por whatsApp";
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 2) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                dados = format.format((imoveis.totalImpresso() * 100) / totalDeRegistro) + "% dos tributos foram impressos";
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 3) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                dados = format.format((imoveis.totalDeImoveisVisitados() * 100) / totalDeRegistro) + "% dos imóveis foram visitados";
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 4) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                dados = format.format((imoveis.totalImoveisAVisitar() * 100) / totalDeRegistro) + "% faltam ser visitados";

            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 5) {
            DecimalFormat format = new DecimalFormat("0.#");
            SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(this);
            imoveis = new RepositorioImovel(conexao);
            try {
                totalNaoEntregues = imoveis.totalDeTributosNaoEntregues();
                dados = format.format((totalNaoEntregues * 100) / totalDeRegistro) + "% dos tributos não foram entregues";
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }
       // System.out.println(i+ " -> "+dados);
        return dados;
    }

    public void resumo(View view) {
        startActivity(new Intent(getApplicationContext(), MotivosDaNaoEntrega.class));
    }

    /***
     * format.format((imoveis.totalDeImoveisDemolidos() * 100) / totalDeRegistro) + "% dos tributos não foram entregues;" + "\n" + format.format((imoveis.totalDeImoveisNaoLocalizados() * 100) / totalDeRegistro) + "% dos imóveis não foram localizados;" + "\n" + format.format((imoveis.naoEntreguesPorRecusarReceber() * 100) / totalDeRegistro) + "% dos contribuintes recusaram receber;" + "\n" + format.format((imoveis.naoEntreguesPorRecusarReceber() * 100) / totalDeRegistro) + "% do cadastro são terrenos";
     */
}