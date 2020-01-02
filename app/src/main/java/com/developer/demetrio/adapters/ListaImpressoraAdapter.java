package com.developer.demetrio.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.demetrio.helpers.Impressora;

import java.util.ArrayList;

public class ListaImpressoraAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Impressora> impressoras;
    private LayoutInflater inflater;
    private TextView nomeImpressora;
    private TextView enderecoImpressora;


    @SuppressLint("WrongConstant")
    public ListaImpressoraAdapter(Context context, ArrayList<Impressora> impressoras, LayoutInflater inflater) {
        this.context = context;
        this.impressoras = impressoras;
        this.inflater = inflater;

    }

    @Override
    public int getCount() {
        return this.impressoras.size();
    }

    @Override
    public Object getItem(int i) {
        return this.impressoras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @SuppressLint("WrongConstant")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //    this.inflater = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));

        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.impressora_inflater, null);
        }
        convertView.findViewById(R.id.impressora);
        System.out.println("DENTRO DO ADAPTER");
        System.out.println(this.impressoras.get(i).getBluetoothEndereco()
                + " - "+ this.impressoras.get(i).getBluetoothNome());
        ((TextView) convertView.findViewById(R.id.nomeImpressora)).setText(this.impressoras.get(i).getBluetoothNome());
        ((TextView) convertView.findViewById(R.id.enderecoBluetooth)).setText(this.impressoras.get(i).getBluetoothEndereco());
        convertView.setTag(getItem(i));
        return convertView;
    }
}
