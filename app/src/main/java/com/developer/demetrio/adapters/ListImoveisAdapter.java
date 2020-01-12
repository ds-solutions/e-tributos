package com.developer.demetrio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.demetrio.etributos.R;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

public class ListImoveisAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Imovel> imoveis;


    public ListImoveisAdapter(Context context, List<Imovel> listImoveis, LayoutInflater inflater) {
        this.context = context;
        this.imoveis = listImoveis;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return this.imoveis.size();
    }

    @Override
    public Object getItem(int i) {
        return this.imoveis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.imovel_adapter, null);
        }
        ((TextView) view.findViewById(R.id.id_view_matricula)).setText(this.imoveis.get(i).getCadastro().getNumCadastro());
        ((TextView) view.findViewById(R.id.id_view_insc)).setText(this.imoveis.get(i).getCadastro().getInscricao());
        ((TextView) view.findViewById(R.id.id_view_nome)).setText(getContribuinte(this.imoveis.get(i)));
        ((TextView) view.findViewById(R.id.id_view_logradouro)).setText(getLogradouro(this.imoveis.get(i)));
        ((TextView) view.findViewById(R.id.id_view_numero)).setText(getNumero(this.imoveis.get(i)));
        ((ImageView) view.findViewById(R.id.id_status_impressora)).setImageResource(getStatusImpressora(this.imoveis.get(i)));
        ((ImageView) view.findViewById(R.id.id_status_impressora)).setBackgroundColor(getBackgroundColor(this.imoveis.get(i)));
        ((ImageView) view.findViewById(R.id.id_status_email)).setImageResource(getStatusEmail(this.imoveis.get(i)));
        ((ImageView) view.findViewById(R.id.id_status_whatsaap)).setImageResource(getStatusWhatsApp(this.imoveis.get(i)));


        return view;
    }

    private int getBackgroundColor(Imovel imovel) {
        return imovel.getIndcEmissaoConta() != 1 ? android.R.color.transparent : R.color.colorAccent;
    }

    private int getStatusWhatsApp(Imovel imovel) {
        return imovel.getIndcEnvioZap() != 1 ? R.drawable.un_send_whatsapp1 : R.drawable.whatsapp;
    }

    private int getStatusEmail(Imovel imovel) {
        return imovel.getIndcEnvioEmail() != 1 ? R.drawable.un_send_email : R.drawable.email;
    }

    private int getStatusImpressora(Imovel imovel) {
        return imovel.getIndcEmissaoConta() != 1 ? R.drawable.un_send_impressora : R.drawable.print;
    }

    private String getNumero(Imovel imovel) {
        return imovel.getEndereco().getNumero();
    }

    private String getLogradouro(Imovel imovel) {
        return imovel.getEndereco().getLogradouro();
    }

    private String getContribuinte(Imovel imovel) {
        String nome="";

        nome = imovel.getContribuinte().getAtualizacaoDoContribuinte() != null ?
                imovel.getContribuinte().getAtualizacaoDoContribuinte().getNome() :
                imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNome();
        return nome;
    }
}
