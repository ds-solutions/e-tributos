package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.CodigoDeCobranca;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.ValoresVenais;

import java.util.ArrayList;
import java.util.List;

public class Carregar extends AppCompatActivity {
    Boolean carregado = false;
    ProgressBar looping;
    long time = 0;
    Thread thread;
    TextView status;
    private List<Imovel> imoveis = new ArrayList<>();
    private Handler handler;

    private SQLiteDatabase database;

    private Imovel imovel;
    private Long id = 0L;
    private Integer lote = 10;
    private Integer preCpf = 10;
    private Integer midleCpf = 750;
    private Integer lastCpf = 114;
    private Integer digitCpf = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);
        looping = (ProgressBar) findViewById(R.id.idProgressBar);
        status = (TextView) findViewById(R.id.id_status);
        final long l = 2;
        handler = new Handler();
        conectarAoBanco();
       new Thread(new Runnable() {
           @Override
           public void run() {

               do {
                   try {
                       Thread.sleep(l);

                      carregado = verificarCarregamento();
                   } catch (InterruptedException e) {
                       Toast toast = new Toast(getApplicationContext());
                       toast.setText(e.getMessage().toString());
                       System.out.println(e.getMessage().toString());
                       e.printStackTrace();
                   }

               } while (!carregado);

               Intent menu = new Intent(getApplicationContext(), Menu.class);
               startActivity(menu);
           }

       }).start();

      System.out.println("Terminar sessão");
    }

    private void conectarAoBanco() {
        ConexaoDataBase conexaoDataBase;
        conexaoDataBase = new ConexaoDataBase();

        try {
           this.database = conexaoDataBase.concectarComBanco(this);
            this.id = 0L;
            this.lote = 10;
            this.preCpf = 10;
            this.midleCpf = 750;
            this.lastCpf = 114;
            this.digitCpf = 77;
            int i = 0;
            while (i < 7){
                popularImovelParaImpressao(i);
                 i++;
                Toast.makeText(this, i +"º Imovel cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Imovel popularImovelParaImpressao(int i) {
        this.imovel = new Imovel();
        Aliquota aliquota = new Aliquota();
        CodigoDeCobranca codigo = new CodigoDeCobranca();
        codigo.setId(id());
        this.imovel.setIndcEmissaoConta(0);
        this.imovel.setIndcEnvioZap(0);
        this.imovel.setIndcEnvioEmail(0);

        codigo.setTipo("1-NORMAL");
        codigo.setTaxaTestada("6,00");
        aliquota.setId(codigo.getId());
        aliquota.setCodigoDeCobranca(codigo);
        aliquota.setTipoConstrucao("EDIFICADO");
        aliquota.setEdificado("1,00");
        aliquota.setTerreno("1,00");
        aliquota.setZoneamento("3");

        Cadastro cadastro = new Cadastro();
        cadastro.setAliquota(aliquota);
        AreasDoImovel areasDoImovel = new AreasDoImovel();
        areasDoImovel.setAreaDoTerreno("132,00");
        areasDoImovel.setEdificado("50,00");
        areasDoImovel.setAreaTotalEdificado("50,00");
        areasDoImovel.setAreaDoTerreno("132,00");
        areasDoImovel.setExcedente("0,00");
        areasDoImovel.setTestada("6,00");
        areasDoImovel.setFracao("1.000000");

        cadastro.setAreasDoImovel(areasDoImovel);
        cadastro.setDistrito("01");
        cadastro.setId(codigo.getId());
        cadastro.setLote(lote());
        cadastro.setNumCadastro("000004");
        cadastro.setQuadra("001");
        cadastro.setSetor("01");
        cadastro.setUnidade("000");
        cadastro.setInscricao("01.01.001."+cadastro.getLote()+".000");
        ValoresVenais valores = new ValoresVenais();
        valores.setId(codigo.getId());
        valores.setEdificada("1.229,00");
        valores.setExcedente("0,00");
        valores.setTerreno("5.544,00");
        valores.setTotal("6.773,00");
        cadastro.setValoresVenais(valores);

        this.imovel.setCadastro(cadastro);

        Endereco endereco = new Endereco();
        endereco.setUf("PE");
        endereco.setNumero(cadastro.getLote());
        endereco.setLogradouro("JOSÉ TAVARES DO REGO");
        endereco.setComplemento("CASA");
        endereco.setCidade("LAGOA DE ITAENGA");
        endereco.setCep("55840-000");
        endereco.setBairro("INDEPENDENCIA");
        endereco.setId(codigo.getId());

        this.imovel.setEndereco(endereco);

        Contribuinte contribuinte = new Contribuinte();
        DadosCadastradosDoContribuinte doContribuinte = new DadosCadastradosDoContribuinte();
        switch (i) {
            case 0:
                doContribuinte.setNome("DEMÉTRIO ANTONIO DE SANTANA");
            case 1:
                doContribuinte.setNome("DOUGLAS PIEDRO DE MORAIS");
            case 2:
                doContribuinte.setNome("AMAURI RODRIGUES DE VASCONCELOS");
            case 3:
                doContribuinte.setNome("RITA MARIA DE ALBUQUERQUE");
            case 4:
                doContribuinte.setNome("DAMIANA FARIAS DA SILVA");
            default:
                doContribuinte.setNome("FAVOR INFORMAR SEU NOME JUNTO AO CADASTRO");
        }
        doContribuinte.setNome("DEMÉTRIO ANTONIO DE SANTANA");
        doContribuinte.setCpf("010.750.114-77");
        doContribuinte.setEstadoCivil("CASADO");
        doContribuinte.setNacionalidade("BRASILEIRA");
        contribuinte.setDadosCadastradosDoContribuinte(doContribuinte);

        this.imovel.setContribuinte(contribuinte);

        this.imovel.getTributo().getIptu().setCampo1CodigoDeBarras("81680000000-1");
        this.imovel.getTributo().getIptu().setCampo2CodigoDeBarras("94182367201-4");
        this.imovel.getTributo().getIptu().setCampo3CodigoDeBarras("91230010120-7");
        this.imovel.getTributo().getIptu().setCampo4CodigoDeBarras("00125799000-0");
        this.imovel.getTributo().getIptu().setCodigoDaDivida("125799");
        this.imovel.getTributo().getIptu().setDigitosDoCodigoDeBarras("81680000000941823672019123001012000125799000");
        this.imovel.getTributo().getIptu().setCodigoDeBaixa("2-125799-1-0");
        this.imovel.getTributo().getIptu().setExercicio("2020");
        this.imovel.getTributo().getIptu().setValorTotal("94,18");
        this.imovel.getTributo().getIptu().setMensagem("Cota Única com desconto, após o vencimento procure o setor tributário do município ou acesse o nosso portal: http://portal.itaenga.pe.gov.br:8070/servicosweb");
        this.imovel.getTributo().getIptu().setMensagem1(null);
        this.imovel.getTributo().getIptu().setMensagem2(null);
        this.imovel.getTributo().getIptu().setSomaDoDesconto("23,55");
        this.imovel.getTributo().getIptu().setSomaDoValor("117,73");
        this.imovel.getTributo().getIptu().setSomaIsencao("0,00");
        this.imovel.getTributo().getIptu().setVencimento("25/09/2020");
        this.imovel.getTributo().getIptu().setListDescricao(listDescricao());
        return this.imovel;
    }

    private String cpf() {
        String cpf = "0"+preCpf+"."+midleCpf+"."+lastCpf+"-"+digitCpf;
        preCpf += 15;
        midleCpf += 17;
        lastCpf += 8;
        digitCpf += 13;
        return cpf;
    }

    private String lote() {
        return "00"+String.valueOf(lote+=10);
    }

    private Long id() {
        return id++;
    }

    private List<DescricaoDaDivida> listDescricao() {
        List<DescricaoDaDivida> list = new ArrayList<>();
        DescricaoDaDivida d1, d2, d3;
        d1 = new DescricaoDaDivida(id(), "01", "IPTU", "67,73", "13,55", "0,00");
        d2 = new DescricaoDaDivida(id(), "02", "TAXA DE EXPEDIENTE", "30,00", "6,00", "0,00");
        d3 = new DescricaoDaDivida(id(), "03", "COLETA DE LIXO", "20,00", "4,00", "0,00");
        list.add(d1);
        list.add(d2);
        list.add(d3);

        return list;
    }

    public Boolean verificarCarregamento(){
       time++;
       return time == 2000;
    }
}
