package com.developer.demetrio.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.developer.demetrio.adapters.utils.ItemRelatorio;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.etributos.MotivosDaNaoEntrega;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.utils.QuadrasNaoVisitadas;
import com.developer.demetrio.repositorio.RepositorioImovel;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class RelatorioAdapter extends ArrayAdapter<ItemRelatorio> {
    private final Context context;
    private final ArrayList<ItemRelatorio> relatorios;
    private SQLiteDatabase conexao;
    private RepositorioImovel imoveis;

    public RelatorioAdapter(Context context, ArrayList<ItemRelatorio> relatorios) {
        super(context, R.layout.item_relatorio, relatorios);
        this.context = context;
        this.relatorios = relatorios;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = inflater.inflate(R.layout.item_relatorio, parent, false);

        ImageView img = (ImageView) linha.findViewById(R.id.image_item_relatorio);
        TextView titulo = (TextView) linha.findViewById(R.id.id_titulo_indice_relatorio);
        TextView dados = (TextView) linha.findViewById(R.id.id_text_dados);
        TextView resumo = (TextView) linha.findViewById(R.id.id_text_resumo_dados);
        TextView tituloResumo = (TextView) linha.findViewById(R.id.id_resumo);

        LinearLayout layout = (LinearLayout) linha.findViewById(R.id.id_layout_resumo);


        img.setImageResource(relatorios.get(i).getImg());
        titulo.setText(relatorios.get(i).getTitulo());
        dados.setText(relatorios.get(i).getDado());
        tituloResumo.setText("Resumo");
        String informacao = "Setor  Quadra  Informação";
        switch (i) {
            case 5:
                conexao = new ConexaoDataBase().concectarComBanco(this.context);
                imoveis = new RepositorioImovel(conexao);
                List<QuadrasNaoVisitadas> naoVisitadas = new ArrayList<>();
                try {
                    naoVisitadas = imoveis.setoresEQuadrasNaoEntregues();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                if (!naoVisitadas.isEmpty()) {
                    for (QuadrasNaoVisitadas q : naoVisitadas) {
                        if (StringUtils.isNotBlank(q.getSetor())) {
                            informacao += q.getSetor() + "  " + q.getQuadra() + "   " + q.getTotalQuadra() + " não foram visitados\n";
                        } else {
                            informacao = "Não há imóveis a visitar!";
                        }
                    }
                    resumo.setText(informacao);
                }
            break;
            case 6:
                conexao = new ConexaoDataBase().concectarComBanco(this.context);
                imoveis = new RepositorioImovel(conexao);
                List<String> motivos = new ArrayList<String>();
                tituloResumo.setText("Resumo");
                try {
                    DecimalFormat format = new DecimalFormat("0.#");
                    motivos.add(String.valueOf(format.format((imoveis.totalDeImoveisDemolidos() * 100)/imoveis.getQtdImoveis()))+"% dos imóveis estão demolidos;");
                    format = new DecimalFormat("0.#");
                    motivos.add(String.valueOf(format.format((imoveis.totalDeImoveisNaoLocalizados() * 100)/imoveis.getQtdImoveis()))+"% dos imóveis não foram localizados;");
                    format = new DecimalFormat("0.#");
                    motivos.add(String.valueOf(format.format((imoveis.naoEntreguesPorRecusarReceber() * 100)/imoveis.getQtdImoveis()))+"% dos contribuintes recusaram receber;");
                    format = new DecimalFormat("0.#");
                    motivos.add(String.valueOf(format.format((imoveis.totalNaoEntreguesPorSerTerreno() * 100)/imoveis.getQtdImoveis()))+"% do cadastro é terreno;");

                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                informacao = "";
                if (!motivos.isEmpty()) {
                    for (String motivo : motivos) {
                            informacao += motivo+"\n";
                        }
                    } else {
                    informacao = "Não há incidência de motivo para não entrega!";
                }
                    resumo.setText(informacao);

            break;
            default:
            break;
        }
            return linha;
        }

}
