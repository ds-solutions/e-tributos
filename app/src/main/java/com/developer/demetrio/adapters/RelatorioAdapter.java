package com.developer.demetrio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.demetrio.adapters.utils.ItemRelatorio;
import com.developer.demetrio.etributos.R;

import java.util.List;


public class RelatorioAdapter extends ArrayAdapter<ItemRelatorio> {
    private Context context;
    private final List<ItemRelatorio> relatorios;

    public RelatorioAdapter(Context context, List<ItemRelatorio> relatorios) {
        super(context, R.layout.item_relatorio, relatorios);
        this.context = context;
        this.relatorios = relatorios;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = inflater.inflate(R.layout.item_relatorio, parent, false);

        ImageView img = (ImageView) linha.findViewById(R.id.image_item_relatorio);
        TextView titulo = (TextView) linha.findViewById(R.id.id_titulo_indice_relatorio);
        TextView dados = (TextView) linha.findViewById(R.id.id_text_dados);

        img.setImageResource(relatorios.get(i).getImg());
        titulo.setText(relatorios.get(i).getTitulo());
        dados.setText(relatorios.get(i).getDado());


        return linha;
    }
}
