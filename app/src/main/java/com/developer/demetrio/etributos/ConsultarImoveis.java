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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.developer.demetrio.adapters.ListImoveisAdapter;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
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
            "Inscrição", "Logradouro", "Não entregues", "Setor/Quadra"};

    private int iConsultaPor = 0, iSetor, iQuadra;

    private List<String> setores;
    private List<String> quadras;
    private List<String> logradouros;

    private List<Imovel> listImoveis;
    private Imovel imovel;
    private ListImoveisAdapter adapter;

    private Spinner setor, quadra, buscarPor;
    private EditText inscricao, logradouro;

    private ImageButton btConsultar;
    private LinearLayout layoutSetorQuadra;


    private RepositorioImovel imoveis;
    private RepositorioEndereco enderecos;
    private RepositorioCadastro cadastros;

    private ListView listView;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener deviceClickListiner = new ClickListiner();

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_imoveis);
        connectDataBase = new ConexaoDataBase();
        this.conexao = connectDataBase.concectarComBanco(this);
        this.btConsultar = findViewById(R.id.id_consultar);
        this.logradouro = findViewById(R.id.id_edt_logradouro);
        this.inscricao = findViewById(R.id.id_edt_inscricao);
        this.layoutSetorQuadra = findViewById(R.id.layour_setor_quadra);
        this.btConsultar = findViewById(R.id.id_consultar);
        this.layoutSetorQuadra.setVisibility(View.GONE);
        this.logradouro.setVisibility(View.GONE);
        this.listView = (ListView) findViewById(R.id.id_list_view_imoveis);
        this.context = getApplicationContext();
        consultar();
        ArrayAdapter<String> parametrosParaConsulta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, consultarPor);
        parametrosParaConsulta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.buscarPor = findViewById(R.id.id_item_de_pesquisa);
        this.buscarPor.setAdapter(parametrosParaConsulta);

        this.btConsultar.setOnClickListener(new C_Extra());

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
                        inscricao.setVisibility(View.GONE);
                        logradouro.setVisibility(View.GONE);
                        layoutSetorQuadra.setVisibility(View.GONE);
                    break;

                    case 8:
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
                    listImoveis = imoveis.buscarTodosImoveisPorInscricao(this.parametros);
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 6:
                System.out.println("6º CASO");
                try {
                    listImoveis = imoveis.buscarTodosPorLogradouro(this.parametros);
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 7:
                System.out.println("7º CASO");
                try {
                    listImoveis = imoveis.buscarImoveisNaoEntregues();
                } catch (RepositorioException e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                System.out.println("8º CASO");
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

    class ClickListiner implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ConsultarImoveis.this.imovel = (Imovel) view.getTag();
            abrirImovel();
        }
    }


    //ESSA CLASSE VAI OUVIR O EVENTO DO BOTÃO CONSULTAR
    class C_Extra implements View.OnClickListener {

        public C_Extra() {
        }

        @Override
        public void onClick(View view) {
             consultar();
            pupularListView();
        }
    }

    private void abrirImovel() {
        if (this.imovel != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", this.imovel.getId());
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
        System.out.println("NO MÉTODO POPULARLISTVIEW A LISTA DE IMÓVEIS TEM: " +this.listImoveis.size());
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
