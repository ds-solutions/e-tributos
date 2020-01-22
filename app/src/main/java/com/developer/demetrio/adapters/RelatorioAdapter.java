package com.developer.demetrio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.developer.demetrio.adapters.utils.ItemRelatorio;
import com.developer.demetrio.etributos.R;

import java.util.ArrayList;


public class RelatorioAdapter extends ArrayAdapter<ItemRelatorio> {
    private final Context context;
    private final ArrayList<ItemRelatorio> relatorios;

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

        img.setImageResource(relatorios.get(i).getImg());
        titulo.setText(relatorios.get(i).getTitulo());
        return linha;
    }
}
