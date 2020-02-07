package com.developer.demetrio.etributos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.developer.demetrio.adapters.PageViewAdapter;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.fragments.DadosDoImovel;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.CodigoDeCobranca;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.LatLng;
import com.developer.demetrio.model.Tributo;
import com.developer.demetrio.model.ValoresVenais;
import com.developer.demetrio.repositorio.RepositorioAliquota;
import com.developer.demetrio.repositorio.RepositorioAreasDoImovel;
import com.developer.demetrio.repositorio.RepositorioCadastro;
import com.developer.demetrio.repositorio.RepositorioCodigoDeCobranca;
import com.developer.demetrio.repositorio.RepositorioContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosAtualizadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioDescricaoDaDivida;
import com.developer.demetrio.repositorio.RepositorioEndereco;
import com.developer.demetrio.repositorio.RepositorioIPTU;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.repositorio.RepositorioTributo;
import com.developer.demetrio.repositorio.RepositorioValoresVenais;
import com.developer.demetrio.service.GPS_Service;
import com.developer.demetrio.service.Mail;
import com.developer.demetrio.service.Zap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class ListaImoveis extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Fachada fachada = Fachada.getInstance();
    private Location location;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private long UPDATE_INTERVAL = 10, FASTEST_INTRVAL = 10;

    private static final int ALL_PERMISSIONS_RESULT = 1011;
    public static final Integer FRAGMENTO_DADOS_DO_IMOVEL = 1;
    private long idImovel;
    private Imovel imovel;

    private SQLiteDatabase conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imoveis);

        List<Fragment> fragments = new ArrayList<>();
        Intent intent = getIntent();

        if (intent != null) {
            Bundle parametros = intent.getExtras();
            if (parametros != null) {
                this.idImovel = parametros.getLong("id");
                carregarImovel(this.idImovel);
            }
        }

        fragments.add(new DadosDoImovel(this, this, this.imovel));
        fragments.add(new DadosDeAtualizacaoProprietario(this, this.imovel));

        this.viewPager = (ViewPager) findViewById(R.id.view_page);
        this.pagerAdapter = new PageViewAdapter(getSupportFragmentManager(), fragments);
        this.viewPager.setAdapter(this.pagerAdapter);

        Fachada.setContext(this);
    }

    private void conectarBanco() {
        this.conexao = new ConexaoDataBase().concectarComBanco(getApplicationContext());
    }
    private void desconectarBanco(){
        this.conexao.close();
    }

    private void carregarImovel(long idImovel) {
        conectarBanco();
        try {
            this.imovel = new RepositorioImovel(this.conexao).buscarImovelPorId(idImovel);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        this.imovel.setCadastro(buscarCadastroDoImovel(this.imovel.getCadastro().getId()));

        this.imovel.setEndereco(buscarEndereco(this.imovel.getEndereco().getId()));

        this.imovel.setContribuinte(buscarContribuinte(this.imovel.getContribuinte().getId()));

        this.imovel.setTributo(buscarTributos(this.imovel.getTributo().getId()));
    }

    private Tributo buscarTributos(Long id) {
        conectarBanco();
        Tributo tributo = new Tributo();
        tributo.setIptu(new IPTU());
        try {
            tributo = new RepositorioTributo(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        tributo.setIptu(buscarIptu(tributo.getIptu().getId()));

        return tributo;
    }

    private IPTU buscarIptu(Long id) {
        conectarBanco();
        IPTU iptu = new IPTU();
         try {
            iptu = new RepositorioIPTU(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
         desconectarBanco();
        iptu.setListDescricao(buscarListasDaDescricao(iptu.getId()));
        return iptu;
    }

    private List<DescricaoDaDivida> buscarListasDaDescricao(Long id) {
        conectarBanco();
        List<DescricaoDaDivida> descricaoDaDividas = new ArrayList<>();
         try {
            descricaoDaDividas = new RepositorioDescricaoDaDivida(this.conexao).descricoesDaDividaDe(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
         desconectarBanco();
        return descricaoDaDividas;
    }

    private Contribuinte buscarContribuinte(Long id) {
        conectarBanco();
        Contribuinte contribuinte = new Contribuinte();
         try {
            contribuinte = new RepositorioContribuinte(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        contribuinte.setDadosCadastradosDoContribuinte(buscarDadosDoContribuinte(contribuinte.getId()));

        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            if (contribuinte.getAtualizacaoDoContribuinte().getId() != null &&
                    contribuinte.getAtualizacaoDoContribuinte().getId() != 0) {
               contribuinte.setAtualizacaoDoContribuinte(buscarAtualizacaoDoContribuinte(contribuinte.getAtualizacaoDoContribuinte().getId()));
            }
        }

        return contribuinte;
    }



    private AtualizacaoDoContribuinte buscarAtualizacaoDoContribuinte(Long id) {
        conectarBanco();
        AtualizacaoDoContribuinte contribuinte = null;
         try {
            contribuinte = new RepositorioDadosAtualizadosDoContribuinte(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
         desconectarBanco();
        return contribuinte;
    }

    private DadosCadastradosDoContribuinte buscarDadosDoContribuinte(Long id)
    {
        conectarBanco();
        DadosCadastradosDoContribuinte contribuinte = new DadosCadastradosDoContribuinte();
        try {
            contribuinte = new RepositorioDadosDoContribuinte(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        return contribuinte;
    }

    private Endereco buscarEndereco(Long id) {
        conectarBanco();
        Endereco endereco = new Endereco();
        try {
            endereco = new RepositorioEndereco(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        return endereco;
    }

    private Cadastro buscarCadastroDoImovel(long id) {
        conectarBanco();
        Cadastro cadastro = new Cadastro();
        try {
            cadastro = new RepositorioCadastro(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        cadastro.setValoresVenais(buscarValoresVenais(cadastro.getValoresVenais().getId()));
        cadastro.setAreasDoImovel(buscarAreas(cadastro.getAreasDoImovel().getId()));
        cadastro.setAliquota(buscarAliquotas(cadastro.getAliquota().getId()));
        return cadastro;
    }

    private Aliquota buscarAliquotas(Long id) {
        conectarBanco();
        Aliquota aliquota = new Aliquota();
        try {
            aliquota.setCodigoDeCobranca(new CodigoDeCobranca());

            aliquota = new RepositorioAliquota(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        aliquota.setCodigoDeCobranca(buscarCodigoDeCobranca(aliquota.getCodigoDeCobranca().getId()));
        return aliquota;
    }

    private CodigoDeCobranca buscarCodigoDeCobranca(Long id) {
        conectarBanco();
        CodigoDeCobranca codigo = new CodigoDeCobranca();
         try {
            codigo = new RepositorioCodigoDeCobranca(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        return codigo;
    }

    private AreasDoImovel buscarAreas(Long id) {
        conectarBanco();
        AreasDoImovel areasDoImovel = new AreasDoImovel();
        try {
            areasDoImovel = new RepositorioAreasDoImovel(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        return areasDoImovel;
    }

    private ValoresVenais buscarValoresVenais(Long id) {
        conectarBanco();
        ValoresVenais valoresVenais = new ValoresVenais();
        try {
            valoresVenais = new RepositorioValoresVenais(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        return valoresVenais;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ConsultarImoveis.class));
        finishAffinity();
        return;
    }


}
