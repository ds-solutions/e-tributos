package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.developer.demetrio.adapters.ListImoveisAdapter;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioCadastro;
import com.developer.demetrio.repositorio.RepositorioEndereco;
import com.developer.demetrio.repositorio.RepositorioImovel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ConsultarImoveis extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private ConexaoDataBase connectDataBase;

    private String[] parametros;
    private String[] consultarPor = new String[]{"Consultar por", "Enviados por e-mail",
            "Enviados por WhatsApp", "Imóveis impressos", "Imóveis não impressos",
            "Inscrição", "Logradouro", "Matricula", "Não entregues", "Setor/Quadra"};

    private String stringSetor, stringQuadra;

    private int iConsultaPor = 0, iSetor, iQuadra;

    private List<String> setores;
    private List<String> quadras;
    private List<String> logradouros;

    private List<Imovel> listImoveis;
    private Imovel imovel;
    private ListImoveisAdapter adapter;

    private Spinner setor, quadra, buscarPor, logradouro;
    private EditText inscricao, matricula;

    private ImageButton btConsultar;
    private LinearLayout layoutSetorQuadra;


    private RepositorioImovel imoveis;
    private ListView listView;
    private LayoutInflater inflater;
    private OnItemClickListener deviceClickListiner = new ClickListiner();

    private Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_imoveis);
        connectDataBase = new ConexaoDataBase();
        this.conexao = connectDataBase.concectarComBanco(this);

        this.inscricao = findViewById(R.id.id_edt_inscricao);
        this.btConsultar = findViewById(R.id.id_consultar);
        this.listView = (ListView) findViewById(R.id.id_list_view_imoveis);
        this.matricula = (EditText) findViewById(R.id.id_edt_matricula);
        this.context = getApplicationContext();

        limparParametros();

        consultar();

        popularSpinnerLogradouro();
        popularSpinnerSetorQuadra();
        ocultarCampos();

        final ArrayAdapter<String> parametrosParaConsulta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, consultarPor);
        parametrosParaConsulta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.buscarPor = findViewById(R.id.id_item_de_pesquisa);
        this.buscarPor.setAdapter(parametrosParaConsulta);

        this.btConsultar.setOnClickListener(new C_Extra());

        this.logradouro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                parametros = new String[1];
                if (i > 0) {
                    parametros[0] = logradouros.get(i);
                }
                if (i == 0) {
                    parametros[0] = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.setor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                        stringSetor = setores.get(i);
                }
                else if (i == 0) {
                    stringSetor = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.quadra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                         stringQuadra = quadras.get(i);
                } else if (i == 0) {
                    stringQuadra = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.buscarPor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iConsultaPor = i;
                limparParametros();
                switch (i){
                    case 1:
                     ocultarCampos();
                    break;

                    case 2:
                        ocultarCampos();
                    break;

                    case 3:
                        ocultarCampos();
                    break;

                    case 4:
                        ocultarCampos();
                    break;

                    case 5:
                        ocultarCampos();
                        inscricao.setVisibility(View.VISIBLE);
                    break;

                    case 6:
                        ocultarCampos();
                        logradouro.setVisibility(View.VISIBLE);
                    break;

                    case 7:
                        ocultarCampos();
                        matricula.setVisibility(View.VISIBLE);
                    break;

                    case 8:
                        ocultarCampos();
                    break;
                    case 9:
                        ocultarCampos();
                        setor.setVisibility(View.VISIBLE);
                        quadra.setVisibility(View.VISIBLE);
                        break;

                    default:
                        ocultarCampos();
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pupularListView();

        this.listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConsultarImoveis.this.imovel = ConsultarImoveis.this.adapter.getItem(i);
               abrirImovel();
            }
        });

    }

    private void limparParametros() {
        this.parametros = new String[2];
    }

    private void ocultarCampos() {
        this.setor.setVisibility(View.GONE);
        this.quadra.setVisibility(View.GONE);
        this.logradouro.setVisibility(View.GONE);
        this.inscricao.setVisibility(View.GONE);
        this.matricula.setVisibility(View.GONE);
    }

    private void popularSpinnerSetorQuadra() {
        RepositorioCadastro cadastros = new RepositorioCadastro(this.conexao);
        List<Cadastro> list = new ArrayList<>();
        try {
            list = cadastros.registrados();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        this.setores = new ArrayList<String>();
        this.quadras = new ArrayList<String>();
        this.setores.add("Setor");
        this.quadras.add("Quadra");
        if (!list.isEmpty()) {
        for (Cadastro cadastro: list) {
            if (!this.setores.contains(cadastro.getSetor().toString())) {
                this.setores.add(cadastro.getSetor().toString());
            }
            if (!this.quadras.contains(cadastro.getQuadra().toString())) {
                this.quadras.add(cadastro.getQuadra().toString());
            } //            android:textColor="@android:color/black"
        }
        }

        if (!this.setores.isEmpty()) {
            ArrayAdapter<String> setoresAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, this.setores);
            setoresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.setor =  findViewById(R.id.id_spinner_setor);
            this.setor.setAdapter(setoresAdapter);

        }
        if (!this.quadras.isEmpty()) {
            ArrayAdapter<String> quadrasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, this.quadras);
            quadrasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.quadra = findViewById(R.id.id_spinner_quadra);
            this.quadra.setAdapter(quadrasAdapter);
        }
    }

    private void popularSpinnerLogradouro() {
        RepositorioEndereco enderecos = new RepositorioEndereco(this.conexao);
        this.logradouros = new ArrayList<>();
        try {
            this.logradouros = enderecos.nomesLogradouros();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        if (!this.logradouros.isEmpty()) {
            ArrayAdapter<String> logradourosAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, this.logradouros);
            logradourosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           this.logradouro = findViewById(R.id.id_spinner_logradouro);
            this.logradouro.setAdapter(logradourosAdapter);
        }

    }


    private void consultar() {
        this.listImoveis = new ArrayList<>();
        this.imoveis = new RepositorioImovel(this.conexao);
        switch (iConsultaPor) {
            case 1:
                try {
                    listImoveis = imoveis.buscarTodosEnviadosPorEmail();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    listImoveis = imoveis.buscarTodosEnviadosPorWhatsApp();
                } catch (RepositorioException e) {
                    e.printStackTrace();
               }
                break;

            case 3:
                try {
                    listImoveis = imoveis.buscarTodosImoveisImpressos();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 4:
                try {
                    listImoveis = imoveis.buscarTodosImoveisNaoImpressos();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 5:
                try {
                    this.parametros = new String[]{this.inscricao.getText().toString()};
                    listImoveis = imoveis.buscarTodosImoveisPorInscricao(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 6:
               try {
                    if (StringUtils.isBlank(this.parametros[0])){
                        listImoveis = null;
                        return;
                    }
                    listImoveis = imoveis.buscarTodosPorLogradouro(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 7:

               try {
                    this.parametros = new String[]{this.matricula.getText().toString()};
                   if (StringUtils.isBlank(parametros[0])){
                       this.listImoveis = null;
                       return;
                   }
                    listImoveis = imoveis.buscarDaMatricula(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                try {
                    listImoveis = imoveis.buscarImoveisNaoEntregues();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                try {
                    listImoveis = imoveis.buscarPorSetorQuadra(this.stringSetor, this.stringQuadra);
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            default:
                try {
                    listImoveis = imoveis.getImoveis();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;
        }
        parametros = null;
    }

    class ClickListiner implements OnItemClickListener {

        public ClickListiner() {}

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }


    //ESSA CLASSE VAI OUVIR O EVENTO DO BOTÃO CONSULTAR
    class C_Extra implements View.OnClickListener {

        public C_Extra() {
        }

        @Override
        public void onClick(View view) {
            consultar();
            if (ConsultarImoveis.this.listImoveis != null){
                pupularListView();
            }
            if (ConsultarImoveis.this.listImoveis == null){
                limparListView();
            }

        }
    }

    private void limparListView() {
        this.listImoveis = new ArrayList<>();
        adapter = new ListImoveisAdapter(getApplicationContext(), this.listImoveis, inflater);
        listView.setAdapter(adapter);
    }

    private void abrirImovel() {
        if (this.imovel != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", imovel.getId());
            startActivity(new Intent(this.getApplicationContext(),
                    ListaImoveis.class).putExtras(bundle));
        }
    }

    public void buscarTodos() {
        this.imoveis = new RepositorioImovel(this.conexao);
        try {
            this.listImoveis = this.imoveis.getImoveis();
        }catch (RepositorioException ex) {
            ex.printStackTrace();
        }

    }

    public void pupularListView() {
        this.inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (this.listImoveis.size() > 0) {
            this.adapter = new ListImoveisAdapter(this, this.listImoveis, this.inflater);
            this.listView.setAdapter(adapter);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Menu.class));
        finishAffinity();
        return;
    }
}
