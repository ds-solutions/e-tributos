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

import java.util.ArrayList;
import java.util.List;

public class ConsultarImoveis extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private ConexaoDataBase connectDataBase;

    private String[] parametros;
    private String[] consultarPor = new String[]{"Consultar por", "Enviados por e-mail",
            "Enviados por WhatsApp", "Imóveis impressos", "Imóveis não impressos",
            "Inscrição", "Logradouro", "Matricula", "Não entregues", "Setor/Quadra"};

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
        this.btConsultar = findViewById(R.id.id_consultar);

        this.inscricao = findViewById(R.id.id_edt_inscricao);
        this.layoutSetorQuadra = findViewById(R.id.layour_setor_quadra);
        this.btConsultar = findViewById(R.id.id_consultar);
        this.listView = (ListView) findViewById(R.id.id_list_view_imoveis);
        this.context = getApplicationContext();

        consultar();

        popularSpinnerLogradouro();
        popularSpinnerSetorQuadra();

        ArrayAdapter<String> parametrosParaConsulta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, consultarPor);
        parametrosParaConsulta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.buscarPor = findViewById(R.id.id_item_de_pesquisa);
        this.buscarPor.setAdapter(parametrosParaConsulta);

        this.btConsultar.setOnClickListener(new C_Extra());

        this.logradouro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.setor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.quadra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.buscarPor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iConsultaPor = i;
                switch (i){
                    case 1:
                     inscricao.setVisibility(View.GONE);
                     logradouro.setVisibility(View.GONE);
                     layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 2:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 3:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 4:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 5:
                        inscricao.setVisibility(View.VISIBLE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 6:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.VISIBLE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 7:
                       // MATRICULA popularSpinnerSetorQuadra();
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.VISIBLE);
                    break;

                    case 8:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;
                    case 9:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.VISIBLE);
                        break;

                    default:
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
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

    private void popularSpinnerSetorQuadra() {
        System.out.println("Entrou no método chamado pelo item ");
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
                System.out.println("1º CASO");
                try {
                    listImoveis = imoveis.buscarTodosEnviadosPorEmail();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                System.out.println("2º CASO");
                try {
                    listImoveis = imoveis.buscarTodosEnviadosPorWhatsApp();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                System.out.println("3º CASO");
                try {
                    listImoveis = imoveis.buscarTodosImoveisImpressos();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 4:
                System.out.println("4º CASO");
                try {
                    listImoveis = imoveis.buscarTodosImoveisNaoImpressos();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 5:
                try {
                    System.out.println("5º CASO");
                    this.parametros = new String[]{this.inscricao.getText().toString()};
                    listImoveis = imoveis.buscarTodosImoveisPorInscricao(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 6:
                System.out.println("6º CASO");
                try {
                    this.parametros = new String[]{this.inscricao.getText().toString()};
                    listImoveis = imoveis.buscarTodosPorLogradouro(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 7:
                System.out.println("7º CASO");
                try {
                    this.parametros = new String[1];
                    listImoveis = imoveis.buscarTodosPorLogradouro(this.parametros);
                    this.parametros = new String[]{};
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                System.out.println("8º CASO");
                try {
                    listImoveis = imoveis.buscarImoveisNaoEntregues();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 9:
                System.out.println("9º CASO");
                try {
                    listImoveis = imoveis.buscarPorSetorQuadra(this.parametros);
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("DEFAULT");
                try {
                    listImoveis = imoveis.getImoveis();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;
        }

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
