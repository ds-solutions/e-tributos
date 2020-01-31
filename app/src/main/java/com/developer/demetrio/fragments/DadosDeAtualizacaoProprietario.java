package com.developer.demetrio.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.etributos.ListaImoveis;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.utils.MaskEditUtil;
import com.developer.demetrio.repositorio.RepositorioContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosAtualizadosDoContribuinte;

import org.apache.commons.lang3.StringUtils;


public class DadosDeAtualizacaoProprietario extends Fragment {
    private EditText nome, cpf, cnpj, rg, orgaoEmissor, dataNac, nacionalidade,
    naturalidade, escolaridade, telefone, celular, email;
    private Spinner sexo, cor, tipoPessoa, estadoCivil;
    private String[] arraySexo = new String[] {"Sexo", "F", "M"};
    private String[] arrayCor = new String[] {"Cor", "Amarela", "Branca", "Parda", "Preta"};
    private String[] arrayTipoPessoa = new String[] {"Pessoa","Fisíca", "Jurídica"};
    private String[] arrayEstadoCivil = new String[]{"Estado Cívil","Casado(a)", "Solteiro(a)", "União Estável", "Viúvo(a)"};
    private Context context;
    private long index;
    private String genero, raca, pessoaTipo, condicaoCivil;
    private SQLiteDatabase conexao;
    private ConexaoDataBase conexaoDataBase;

    private AtualizacaoDoContribuinte dados;

    private Button salvar, cancelar;

    public static final Integer FRAGMENTO_ATUALIZAR_DADOS = 2;

    private Imovel imovel;

   private Button btSwitchDocument;
   private TextView labelCpfOrCnpj;

   private boolean isCnpj;

    public DadosDeAtualizacaoProprietario(Context context, Imovel imovel) {
        if (imovel != null) {
            this.imovel = imovel;
            this.index = imovel.getId();
            if (this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null) {
                this.dados = this.imovel.getContribuinte().getAtualizacaoDoContribuinte();
            }
        }
        this.isCnpj = false;
        this.context = context;
        conexaoDataBase = new ConexaoDataBase();
        this.conexao = conexaoDataBase.concectarComBanco(this.context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dados_proprietario, container, false);
        this.nome = (EditText) viewGroup.findViewById(R.id.novo_propr);
        this.cpf = (EditText) viewGroup.findViewById(R.id.id_cpf_atualizado);
        this.cnpj = (EditText) viewGroup.findViewById(R.id.id_cnpj_atualizado);
        this.rg = (EditText) viewGroup.findViewById(R.id.rg);
        this.orgaoEmissor = (EditText) viewGroup.findViewById(R.id.orgao_emissor);
        this.dataNac = (EditText) viewGroup.findViewById(R.id.date_nascimento);
        this.nacionalidade = (EditText) viewGroup.findViewById(R.id.nacionalidade);
        this.naturalidade = (EditText) viewGroup.findViewById(R.id.naturalidade);
        this.escolaridade = (EditText) viewGroup.findViewById(R.id.escolaridade);
        this.telefone = (EditText) viewGroup.findViewById(R.id.telefone);
        this.celular = (EditText) viewGroup.findViewById(R.id.celular);
        this.email = (EditText) viewGroup.findViewById(R.id.email);
        this.salvar = (Button) viewGroup.findViewById(R.id.bt_salvar_atualizacao);
        this.cancelar = (Button) viewGroup.findViewById(R.id.bt_cancelar_atualizacao);
        ArrayAdapter<String> listCoresAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, arrayCor);
        listCoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.cor = (Spinner) viewGroup.findViewById(R.id.cor);
        this.btSwitchDocument = (Button) viewGroup.findViewById(R.id.id_bt_switch_document);
        this.labelCpfOrCnpj = (TextView) viewGroup.findViewById(R.id.textView8);

        this.cnpj.setVisibility(View.GONE);
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
        this.sexo = (Spinner) viewGroup.findViewById(R.id.sexo);
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
        this.estadoCivil = (Spinner) viewGroup.findViewById(R.id.estado_civil);
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
        this.tipoPessoa = (Spinner) viewGroup.findViewById(R.id.tipo_pessoa);
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

        if (this.dados != null && this.dados.getId() != 0)  {
            preencherView();
        }

       this.salvar.setOnClickListener(new C_Salvar());
        cpfOrCnpj();
        this.btSwitchDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCnpj = !isCnpj;
                cpfOrCnpj();
                cpf.setText("");
                cnpj.setText("");
            }
        });

        this.cpf.addTextChangedListener(MaskEditUtil.mask(this.cpf, MaskEditUtil.FORMAT_CPF));
        this.cnpj.addTextChangedListener(MaskEditUtil.mask(this.cnpj, MaskEditUtil.FORMAT_CNPJ));
        this.dataNac.addTextChangedListener(MaskEditUtil.mask(this.dataNac, MaskEditUtil.FORMAT_DATE));
        this.telefone.addTextChangedListener(MaskEditUtil.mask(this.telefone, MaskEditUtil.FORMAT_PHOONE));
        this.celular.addTextChangedListener(MaskEditUtil.mask(this.celular, MaskEditUtil.FORMAT_CELLPHOONE));

        return viewGroup;
    }


    public void cpfOrCnpj() {
        if (isCnpj) {
            labelCpfOrCnpj.setText(R.string.cnpj);
            this.cnpj.setVisibility(View.VISIBLE);
            this.cpf.setVisibility(View.GONE);
            this.btSwitchDocument.setText(R.string.cpf);
        }
        if (!isCnpj) {
            labelCpfOrCnpj.setText(R.string.cpf);
            this.cnpj.setVisibility(View.GONE);
            this.cpf.setVisibility(View.VISIBLE);
            this.btSwitchDocument.setText(R.string.cnpj);
         }
    }

    public void preencherView() {
        this.nome.setText(this.dados.getNome().toUpperCase());
        if (StringUtils.isNotBlank(dados.getCpfCnpj()) && dados.getCpfCnpj().length() > 14) {
            this.cnpj.setText(dados.getCpfCnpj());
            this.cnpj.setVisibility(View.VISIBLE);
            this.cpf.setVisibility(View.GONE);
            this.isCnpj = true;
            this.btSwitchDocument.setText(R.string.cpf);
        }
        if (StringUtils.isNotBlank(dados.getCpfCnpj()) && dados.getCpfCnpj().length() > 14) {
            this.cpf.setText(this.dados.getCpfCnpj());
            this.cnpj.setVisibility(View.GONE);
            this.cpf.setVisibility(View.VISIBLE);
            this.isCnpj = false;
            this.btSwitchDocument.setText(R.string.cnpj);
        }

        this.rg.setText(this.dados.getRg());
        this.orgaoEmissor.setText(this.dados.getOrgaoEmissor().toUpperCase());
        this.dataNac.setText(this.dados.getDataNascimento());
        this.nacionalidade.setText(this.dados.getNacionalidade().toUpperCase());
        this.naturalidade.setText(this.dados.getNaturalidade().toUpperCase());
        this.escolaridade.setText(this.dados.getEscolaridade().toUpperCase());
        this.telefone.setText(this.dados.getTelefone());
        this.celular.setText(this.dados.getCelular());
        this.email.setText(this.dados.getEmail());
        int i = 0;
        for (String o : arraySexo) {
            if (o == this.dados.getSexo()) {
                this.sexo.setSelection(i);
                i = 0;
            }
            i++;
        }

        for (String o : arrayCor) {
            if (o == this.dados.getCor()) {
                this.cor.setSelection(i);
                i = 0;
            }
            i++;
        }
        for (String o : arrayEstadoCivil) {
            if (o == this.dados.getEstadoCivil()) {
                this.estadoCivil.setSelection(i);
                i = 0;
            }
            i++;
        }
        for (String o : arrayTipoPessoa) {
            if (o == this.dados.getTipoPessoa()) {
                this.tipoPessoa.setSelection(i);
                i = 0;
            }
            i++;
        }


    }



    public AtualizacaoDoContribuinte novoCadastro() {

        AtualizacaoDoContribuinte contribuinte = new AtualizacaoDoContribuinte();
        if (this.dados != null && this.dados.getId() != 0) {
            contribuinte.setId(this.dados.getId());
        }
        contribuinte.setNome(this.nome.getText().toString().toUpperCase());
        if (StringUtils.isNotBlank(this.cpf.getText().toString())) {
            contribuinte.setCpfCnpj(this.cpf.getText().toString());
        }
        if (StringUtils.isNotBlank(this.cnpj.getText().toString())) {
            contribuinte.setCpfCnpj(this.cnpj.getText().toString());
        }
        contribuinte.setRg(this.rg.getText().toString());
        contribuinte.setOrgaoEmissor(this.orgaoEmissor.getText().toString().toUpperCase());
        contribuinte.setNacionalidade(this.nacionalidade.getText().toString().toUpperCase());
        contribuinte.setNaturalidade(this.naturalidade.getText().toString().toUpperCase());
        contribuinte.setTipoPessoa(this.tipoPessoa.getSelectedItem().toString());
        contribuinte.setEscolaridade(this.escolaridade.getText().toString().toUpperCase());
        contribuinte.setEstadoCivil(this.estadoCivil.getSelectedItem().toString());
        contribuinte.setTelefone(this.telefone.getText().toString());
        contribuinte.setCelular(this.celular.getText().toString());
        contribuinte.setCor(this.cor.getSelectedItem().toString());
        contribuinte.setDataNascimento(this.dataNac.getText().toString());
        contribuinte.setEmail(this.email.getText().toString());
        contribuinte.setSexo(this.sexo.getSelectedItem().toString());
        return contribuinte;
    }

    class C_Salvar implements View.OnClickListener {
        public C_Salvar() {

        }

        @Override
        public void onClick(View view) {
           inserir(novoCadastro());
           abrirTelaDadosDoImovel();
        }
    }

    public void abrirTelaDadosDoImovel() {
       Bundle bundle = new Bundle();
        bundle.putLong("id",this.index);
        Intent activity = new Intent(this.context, new ListaImoveis().getClass());
        activity.putExtras(bundle);
        startActivity(activity);
    }

    public void inserir(AtualizacaoDoContribuinte contribuinte) {
        RepositorioDadosAtualizadosDoContribuinte contribuintes =
                new RepositorioDadosAtualizadosDoContribuinte(this.conexao);
        try {
            if (contribuinte.getId() != null && contribuinte.getId() != 0) {
               contribuintes.atualizar(contribuinte);
            }
            if (contribuinte.getId() == null) {
                contribuinte.setId(contribuintes.inserir(contribuinte));
            }
        }catch (RepositorioException ex) {
            ex.printStackTrace();
        }
        atualizarContribuinte(contribuinte);

    }

    public void atualizarContribuinte(AtualizacaoDoContribuinte dados) {
        RepositorioContribuinte contribuintes = new RepositorioContribuinte(this.conexao);
        Contribuinte contribuinte = this.imovel.getContribuinte();
        contribuinte.setAtualizacaoDoContribuinte(dados);
        try {
            contribuintes.atualizar(contribuinte);
        }catch (RepositorioException ex) {
            ex.printStackTrace();
        }
    }

}
