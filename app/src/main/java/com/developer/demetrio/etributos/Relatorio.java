package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.demetrio.adapters.RelatorioAdapter;
import com.developer.demetrio.adapters.utils.ItemRelatorio;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.utils.QuadrasNaoVisitadas;
import com.developer.demetrio.repositorio.RepositorioImovel;;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Relatorio extends AppCompatActivity {
    private long totalDeRegistro = 0L, totalNaoEntregues = 0L;
    private RepositorioImovel imoveis;
    private LinearLayout layoutResumo;
    private TextView resumo, tituloResumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        ListView listView = (ListView) findViewById(R.id.id_list_view_relatorios);
        this.layoutResumo = (LinearLayout) findViewById(R.id.id_layout_resumo);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras =  intent.getExtras();
            if (extras != null) {
                totalDeRegistro = extras.getLong("total_de_registros");
            }
        }

        final ArrayList<ItemRelatorio> itens = popularRelatorios();
        ArrayAdapter adapter = new RelatorioAdapter(this, itens);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SQLiteDatabase conexao = new ConexaoDataBase().concectarComBanco(getApplicationContext());
                imoveis = new RepositorioImovel(conexao);
                switch (i) {
                    case 5:
                            if (view.findViewById(R.id.id_layout_resumo).getVisibility() == View.GONE) {
                                (view.findViewById(R.id.id_layout_resumo)).setVisibility(View.VISIBLE);
                            } else if ((view.findViewById(R.id.id_layout_resumo)).getVisibility() == View.VISIBLE) {
                                (view.findViewById(R.id.id_layout_resumo)).setVisibility(View.GONE);
                            }

                        break;
                    case 6:
                        if (view.findViewById(R.id.id_layout_resumo).getVisibility() == View.GONE) {
                            (view.findViewById(R.id.id_layout_resumo)).setVisibility(View.VISIBLE);
                        } else if ((view.findViewById(R.id.id_layout_resumo)).getVisibility() == View.VISIBLE) {
                            (view.findViewById(R.id.id_layout_resumo)).setVisibility(View.GONE);
                        }
                    break;
                    default:
                    break;

                }
            }
        });
    }


    private ArrayList<ItemRelatorio> popularRelatorios() {
        ArrayList<ItemRelatorio> relatorios = new ArrayList<>();
        ItemRelatorio item = new ItemRelatorio(R.drawable.email_blue, "Total enviados por e-mails", dados(0));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.whatsapp, "Total enviados por whatsApp", dados(1));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.imprimindo, "Total impresso", dados(2));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.atualizado, "Total atualizados", dados(3));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.positivo, "Total de imóveis visitados", dados(4));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.casas, "Total de imóveis a visitar", dados(5));
        relatorios.add(item);
        item = new ItemRelatorio(R.drawable.entrega, "Tributos não entregues", dados(6));
        relatorios.add(item);
        return relatorios;
    }

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
                dados = format.format((imoveis.totalDeCadastroAlterados() * 100) / totalDeRegistro) + "% dos cadastros foram atualizados";
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
                dados = format.format((imoveis.totalDeImoveisVisitados() * 100) / totalDeRegistro) + "% dos imóveis foram visitados";
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
                dados = format.format((imoveis.totalImoveisAVisitar() * 100) / totalDeRegistro) + "% faltam ser visitados";

            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            conexao.close();
        }

        if (i == 6) {
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
        return dados;
    }

    public void resumo(View view) {
        startActivity(new Intent(getApplicationContext(), MotivosDaNaoEntrega.class));
    }

    /***
     * format.format((imoveis.totalDeImoveisDemolidos() * 100) / totalDeRegistro) + "% dos tributos não foram entregues;" + "\n" + format.format((imoveis.totalDeImoveisNaoLocalizados() * 100) / totalDeRegistro) + "% dos imóveis não foram localizados;" + "\n" + format.format((imoveis.naoEntreguesPorRecusarReceber() * 100) / totalDeRegistro) + "% dos contribuintes recusaram receber;" + "\n" + format.format((imoveis.naoEntreguesPorRecusarReceber() * 100) / totalDeRegistro) + "% do cadastro são terrenos";
     */
}