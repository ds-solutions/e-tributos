package com.developer.demetrio.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.etributos.R;


public class DadosDeAtualizacaoProprietario extends Fragment {
    private AtualizacaoDoContribuinte dados;
    private TextView nome, cpfCnpj, rg, orgaoEmissor, dataNac, nacionalidade,
    naturalidade, escolaridade, telefone, celular, email;
    private Spinner sexo, cor, tipoPessoa, estadoCivil;
    private String[] arraySexo = new String[] {"Sexo", "F", "M"};
    private String[] arrayCor = new String[] {"Cor", "Amarela", "Branca", "Parda", "Preta"};
    private String[] arrayTipoPessoa = new String[] {"Pessoa","Fisíca", "Jurídica"};
    private String[] arrayEstadoCivil = new String[]{"Estado Cívil","Casado(a)", "Solteiro(a)", "União Estável", "Viúvo(a)"};
    private Context context;
    private Integer index;
    public static final Integer FRAGMENTO_ATUALIZAR_DADOS = 2;

    public DadosDeAtualizacaoProprietario(Context context, Integer index) {
        this.index = index;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dados_proprietario, container, false);
        this.nome = viewGroup.findViewById(R.id.novo_propr);
        this.cpfCnpj = viewGroup.findViewById(R.id.cpf_cnpj_novo_prop);
        this.rg = viewGroup.findViewById(R.id.rg);
        this.orgaoEmissor = viewGroup.findViewById(R.id.orgao_emissor);
        this.dataNac = viewGroup.findViewById(R.id.date_nascimento);
        this.nacionalidade = viewGroup.findViewById(R.id.nacionalidade);
        this.naturalidade = viewGroup.findViewById(R.id.naturalidade);
        this.escolaridade = viewGroup.findViewById(R.id.escolaridade);
        this.telefone = viewGroup.findViewById(R.id.telefone);
        this.celular = viewGroup.findViewById(R.id.celular);
        this.email = viewGroup.findViewById(R.id.email);

        ArrayAdapter<String> listCoresAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, arrayCor);
        listCoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.cor = viewGroup.findViewById(R.id.cor);
        this.cor.setAdapter(listCoresAdapter);
        this.cor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pegou = arrayCor[i];
           }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> listSexoAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, arraySexo);
        listSexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sexo = viewGroup.findViewById(R.id.sexo);
        this.sexo.setAdapter(listSexoAdapter);
        this.sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pegou = arraySexo[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> listEstadoCivil = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, arrayEstadoCivil);
        listEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.estadoCivil = viewGroup.findViewById(R.id.estado_civil);
        this.estadoCivil.setAdapter(listEstadoCivil);
        this.estadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pegou = arrayEstadoCivil[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> listTipoPessoaAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, arrayTipoPessoa);
        listTipoPessoaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.tipoPessoa = viewGroup.findViewById(R.id.tipo_pessoa);
        this.tipoPessoa.setAdapter(listTipoPessoaAdapter);
        this.tipoPessoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pegou = arrayTipoPessoa[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return viewGroup;
    }


}
