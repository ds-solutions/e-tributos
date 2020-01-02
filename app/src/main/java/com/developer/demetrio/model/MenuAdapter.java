package com.developer.demetrio.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.demetrio.tributos.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MenuAdapter extends ArrayAdapter<ItemMenu> {

    private final Context context;
    private final ArrayList<ItemMenu> itens;

    public MenuAdapter(Context context, ArrayList<ItemMenu> itens) {
        super(context, R.layout.item_menu, itens);
        this.context = context;
        this.itens = itens;
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = inflater.inflate(R.layout.item_menu, parent, false);

        ImageView img = (ImageView) linha.findViewById(R.id.idImageMenu);
        TextView opcao = (TextView) linha.findViewById(R.id.idOpcaoMenu);

        img.setImageResource(itens.get(posicao).getImg());
        opcao.setText(itens.get(posicao).getDescricao());
        return linha;
    }
}
