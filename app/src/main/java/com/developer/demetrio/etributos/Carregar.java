package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.LogErro;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.CodigoDeCobranca;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.Tributo;
import com.developer.demetrio.model.ValoresVenais;
import com.developer.demetrio.repositorio.RepositorioAliquota;
import com.developer.demetrio.repositorio.RepositorioAreasDoImovel;
import com.developer.demetrio.repositorio.RepositorioCadastro;
import com.developer.demetrio.repositorio.RepositorioCodigoDeCobranca;
import com.developer.demetrio.repositorio.RepositorioContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioDescricaoDaDivida;
import com.developer.demetrio.repositorio.RepositorioEndereco;
import com.developer.demetrio.repositorio.RepositorioIPTU;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.repositorio.RepositorioTributo;
import com.developer.demetrio.repositorio.RepositorioValoresVenais;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Carregar extends AppCompatActivity {
    private Boolean carregado = false;
    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressBarAnimador;
    private long time = 0;
    private Thread thread;
    private TextView status;
    private List<Imovel> listImoveis = new ArrayList<>();

    private String campo1, campo2, campo3, campo4, codigoDeBarras;

    private SQLiteDatabase conexao;
    private RepositorioImovel imoveis;

    private Imovel imovel;
    private Long id = 0L;
    private Integer lote = 10;
    private Integer preCpf = 10;
    private Integer midleCpf = 750;
    private Integer lastCpf = 114;
    private Integer digitCpf = 77;
    private Handler handler;
    private long qtd = 100;
    private int salvo = 0;
    private int contImoveis = 0;
    private int quadra = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);
        status = (TextView) findViewById(R.id.id_status);
        final long l = 2;
        handler = new Handler();
        init();
    }

    private void init() {
        progressBarAnimation = (ProgressBar) findViewById(R.id.id_progress_carregar);
        progressBarAnimador = ObjectAnimator.ofInt(progressBarAnimation, "progress", 0,(int)qtd);
        progressBarAnimation.setMax((int)qtd);

        final Timer timer = new Timer();
        final int cont = 0;
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                 if (salvo < qtd) {
                      popularImovelParaImpressao();
                      salvo++;
                 }
               progressBarAnimation.setProgress(salvo);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Carregando....");
                    }
                });
                if (salvo == qtd) {
                    timer.cancel();
                    Intent menu = new Intent(getApplicationContext(), Menu.class);
                    startActivity(menu);
                }

            }
        };
        timer.schedule(task, 300, qtd);
    }

    private void conectarAoBanco() {
        this.conexao = new ConexaoDataBase().concectarComBanco(this);
    }

    private void desconectarBanco() {
        this.conexao.close();
    }


    public Imovel popularImovelParaImpressao() {

        this.imovel = new Imovel();
        this.imovel.setIndcEnvioEmail(0);
        this.imovel.setIndcEnvioZap(0);
        this.imovel.setIndcEmissaoConta(0);

        this.imovel.setContribuinte(popularContribuinte());

        this.imovel.setEndereco(popularEndereco());

        this.imovel.setTributo(popularTributo());
        this.imovel.setCadastro(popularCadastro());
        conectarAoBanco();
        try {
            this.imovel.setId(new RepositorioImovel(this.conexao).inserir(this.imovel));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar imóvel");
            e.printStackTrace();
        }
        desconectarBanco();
        return this.imovel;
    }

    private Tributo popularTributo() {
        Tributo tributo = new Tributo();
        tributo.setIptu(pupularIPTU());
        conectarAoBanco();
        RepositorioTributo tributos = new RepositorioTributo(this.conexao);
        try {
            tributo.setId(tributos.inserir(tributo));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar tributo");
            e.printStackTrace();
        }
        desconectarBanco();
        return tributo;
    }

    private IPTU pupularIPTU() {
        IPTU iptu = new IPTU();
        gerarCodigo();
        iptu.setDigitosDoCodigoDeBarras(codigoDeBarras);
        iptu.setCampo1CodigoDeBarras(campo1);
        iptu.setCampo2CodigoDeBarras(campo2);
        iptu.setCampo3CodigoDeBarras(campo3);
        iptu.setCampo4CodigoDeBarras(campo4);
        iptu.setExercicio("2020");
        iptu.setCodigoDeBaixa("2-"+campo1.substring(1,5)+"1-0");
        iptu.setCodigoDaDivida(campo1.substring(1,5));
        iptu.setVencimento("30/12/2020");
        iptu.setSomaDoValor("100,57");
        iptu.setSomaIsencao("0,00");
        iptu.setSomaDoDesconto("20,11");
        iptu.setValorTotal("80,46");
        iptu.setMensagem("Cota Única com desconto, após o vencimento procure o setor tributário do município ou acesse o nosso portal: http://portal.itaenga.pe.gov.br:8070/servicosweb");
        conectarAoBanco();
        try {
            iptu.setId(new RepositorioIPTU(this.conexao).inserir(iptu));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar iptu");
            e.printStackTrace();
        }
        desconectarBanco();
        iptu.setListDescricao(gerarDescricao(iptu.getId()));
        return iptu;
    }

    private List<DescricaoDaDivida> gerarDescricao(Long id) {
        int i = 0;
        int c = 1;
        List<DescricaoDaDivida> descricoes = new ArrayList<>();
        while (i < 4) {
            DescricaoDaDivida divida = new DescricaoDaDivida();
            divida.setId_IPTU(id);
            divida.setIsencao("0,00");
            divida.setCodigo("0"+String.valueOf(c));
            if (i == 0) {
                divida.setDescricao("IPTU");
                divida.setValor("28,00");
                divida.setPontualidade("5,60");
            }
            if (i == 1) {
                divida.setDescricao("IMP. PREDIAL URBANO");
                divida.setValor("32,57");
                divida.setPontualidade("6,51");
            }
            if (i == 2) {
                divida.setDescricao("TAXA DE EXPEDIENTE");
                divida.setValor("30,00");
                divida.setPontualidade("6,00");
            }
            if (i == 3) {
                divida.setDescricao("TAXA DE COLETA DE LIXO");
                divida.setValor("10,00");
                divida.setPontualidade("2,00");
            }
            c++;
            i++;
            conectarAoBanco();
           try {
                divida.setId_IPTU(new RepositorioDescricaoDaDivida(this.conexao).inserir(divida));
            } catch (RepositorioException e) {
                new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar descrição da divida");
                e.printStackTrace();
            }
            descricoes.add(divida);
            desconectarBanco();
        }
        return descricoes;
    }

    private void gerarCodigo() {
        String c1 = String.valueOf(new Random().nextLong());
        String c2 = String.valueOf(new Random().nextLong());
        String c3 = String.valueOf(new Random().nextLong());
        String c4 = String.valueOf(new Random().nextLong());

        campo1 = c1.substring(1, 11)+"-"+c1.substring(6, 7);
        campo2 = c2.substring(1, 11)+"-"+c2.substring(6, 7);
        campo3 = c3.substring(1, 11)+"-"+c3.substring(6, 7);
        campo4 = c4.substring(1, 11)+"-"+c4.substring(6, 7);
        codigoDeBarras = c1.substring(1, 11) +
                c2.substring(1, 11) +
                c3.substring(1, 11) +
                c4.substring(1, 11);
    }

    private Endereco popularEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("55840-000");
        endereco.setNumero(lote());
        endereco.setComplemento("");
        endereco.setBairro("INDEPENDÊNCIA");
        endereco.setLogradouro("JOSÉ TAVARES DO REGO");
        endereco.setCidade("LAGOA DE ITAENGA");
        endereco.setUf("PE");
        conectarAoBanco();
        try {
            endereco.setId(new RepositorioEndereco(this.conexao).inserir(endereco));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar descrição da divida");
            e.printStackTrace();
        }
        desconectarBanco();
        return endereco;
    }

    private Contribuinte popularContribuinte() {
        Contribuinte contribuinte = new Contribuinte();

        contribuinte.setDadosCadastradosDoContribuinte(new DadosCadastradosDoContribuinte());
        contribuinte.setDadosCadastradosDoContribuinte(popularDadosDoContribuinte());
        conectarAoBanco();
        try {
            contribuinte.setId(new RepositorioContribuinte(this.conexao).inserir(contribuinte));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar contribuinte");
            e.printStackTrace();
        }
        desconectarBanco();
        return contribuinte;
    }

    private DadosCadastradosDoContribuinte popularDadosDoContribuinte() {
        DadosCadastradosDoContribuinte contribuinte = new DadosCadastradosDoContribuinte();
        contribuinte.setNumeroCelular(getCelfone());
        contribuinte.setEmail(getEmail());
        contribuinte.setSexo("MASCULINO");
        contribuinte.setRaca(pegarCor());
        contribuinte.setNaturalidade("LAGOA DE ITAENGA");
        contribuinte.setNacionalidade("BRASILEIRA");
        contribuinte.setEstadoCivil("SOLTEIRO");
        contribuinte.setDataNasc((new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());
        contribuinte.setOrgEmissor("SDS/PE");
        contribuinte.setRg(getRg());
        contribuinte.setCpf(getCpf());
        contribuinte.setNome(getNome());
        conectarAoBanco();
        try {
            contribuinte.setId(new RepositorioDadosDoContribuinte(this.conexao).inserir(contribuinte));
        } catch (RepositorioException e) {
            e.printStackTrace();
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar dados do contribuinte");
        }
        desconectarBanco();
        return contribuinte;
    }

    private String pegarCor() {
         int i = new Random().nextInt(4);
        switch (i) {
            case 0:
                return "AMARELA";
            case 1:
                return "BRANCA";
            case 2:
                return "PARDA";
            case 3:
                return "PRETA";
            default:
                return "INDIGENA";
        }
    }

    private String getRg() {
        if (contImoveis > 16 && contImoveis < 25 ) {
            return "";
        }
        return String.valueOf(new Random().nextInt(9999999));
    }

    private String getNome() {
        String[] nomes = new String[]{"PEDRO", "DANIEL", "SAULO", "ANTONIEL", "DEMÉTRIO"};
        String[] midleNome = new String[]{" FERNANDES DA ", " ANTONIO DE ", "R ODRIGUES "};
        String[] sobreNome = new String[]{"SILVA", "SANTANA"};
        int n, m, s;
        n = (new Random().nextInt(3));
        m = (new Random().nextInt(2));
        s = (new Random().nextInt(1));

        String nome = nomes[n] + midleNome[m] + sobreNome[s];
       return nome;
    }

    private String getCpf() {
        if (contImoveis > 8 && contImoveis < 11 ) {
            return "";
        }
        return String.valueOf(new Random().nextInt(9)) +
                String.valueOf(new Random().nextInt(9)) +
                String.valueOf(new Random().nextInt(9))+"."+
                String.valueOf(new Random().nextInt(9))+
                String.valueOf(new Random().nextInt(9))+
                String.valueOf(new Random().nextInt(9))+"."+
                String.valueOf(new Random().nextInt(9))+
                String.valueOf(new Random().nextInt(9))+
                String.valueOf(new Random().nextInt(9))+"-"+
                String.valueOf(new Random().nextInt(9))+
                String.valueOf(new Random().nextInt(9));
    }

    private String getEmail() {
        Random c = new Random();
        int i = 0;
        int t = c.nextInt(9);
        String email = "";
        char[] alfabeto = {'a','b','c','d','e','f','g','h','i','j','l','k','m','n','n','o','p','q','r','s','t','u','v','w','x','y','z',};
        do {
            c = new Random();
            email+= alfabeto[c.nextInt(9)];
            i++;
        }while (i < t);

        return email+="@gmail.com";
    }

    private String getCelfone() {
        if (contImoveis < 3) {
            return "";
        }
        return "81 9"+ String.valueOf(new Random().nextInt(9999))+"-"+String.valueOf(new Random().nextInt(9999));
    }

    private Cadastro popularCadastro() {
        Cadastro cadastro = new Cadastro();
        cadastro.setDistrito("01");
        cadastro.setSetor("02");
        cadastro.setLote(lote());
        cadastro.setQuadra(quadra());
        cadastro.setUnidade("000");
        cadastro.setInscricao(cadastro.getDistrito() + "." + cadastro.getSetor() +"."+
                cadastro.getQuadra() +"."+ cadastro.getLote()+"."+ cadastro.getUnidade());
        cadastro.setNumCadastro(campo1.substring(1, 6));
        cadastro.setAliquota(popularAliquota());
        cadastro.setAreasDoImovel(pupularAreasDoImovel());
        cadastro.setValoresVenais(popularValoresVenais());
        conectarAoBanco();
        try {
            cadastro.setId(new RepositorioCadastro(this.conexao).inserir(cadastro));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar cadastro");
            e.printStackTrace();
        }
        desconectarBanco();
        return cadastro;
    }

    private String quadra() {
        String q = "00" + quadra;
        if (q.length() > 3) {
            q = q.substring(1, 4);
        }
        return q;
    }

    private ValoresVenais popularValoresVenais() {
        ValoresVenais valoresVenais = new ValoresVenais();
        valoresVenais.setTotal("6.057,00");
        valoresVenais.setExcedente("0,00");
        valoresVenais.setEdificada("3.257,00");
        valoresVenais.setTerreno("2.800,00");
        conectarAoBanco();
        try {
            valoresVenais.setId(new RepositorioValoresVenais(this.conexao).inserir(valoresVenais));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar valores venais");
            e.printStackTrace();
        }
        desconectarBanco();
        return valoresVenais;
    }

    private AreasDoImovel pupularAreasDoImovel() {
        AreasDoImovel area = new AreasDoImovel();
        area.setFracao("1,000000");
        area.setTestada("5,00");
        area.setExcedente("0,00");
        area.setAreaTotalEdificado("135,00");
        area.setEdificado("125,00");
        area.setAreaDoTerreno("100,00");
        conectarAoBanco();
        try {
            area.setId(new RepositorioAreasDoImovel(this.conexao).inserir(area));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar areas do imóvel");
            e.printStackTrace();
        }
        desconectarBanco();
        return area;
    }

    private Aliquota popularAliquota() {
        Aliquota aliquota = new Aliquota();
        aliquota.setTipoConstrucao("EDIFICADO");
        aliquota.setZoneamento("1");
        aliquota.setEdificado("1,00");
        aliquota.setTerreno("1,00");
        aliquota.setCodigoDeCobranca(popularCodigo());
        conectarAoBanco();
        try {
            aliquota.setId(new RepositorioAliquota(this.conexao).inserir(aliquota));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar alicota");
            e.printStackTrace();
        }
        desconectarBanco();
        return aliquota;
    }

    private CodigoDeCobranca popularCodigo() {
        CodigoDeCobranca codigo =  new CodigoDeCobranca();
        codigo.setTipo("1-NORMAL");
        codigo.setTaxaTestada("5,00");
        conectarAoBanco();
        try {
            codigo.setId(new RepositorioCodigoDeCobranca(this.conexao).inserir(codigo));
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Carregar ao tentar salvar código de cobrança");
            e.printStackTrace();
        }
        desconectarBanco();
        return codigo;
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
        String l ="00"+String.valueOf(lote);
        if (lote < 999) {
            if (l.length() > 4) {
                l = l.substring(1, 5);
            }
        } else {
            l = l.substring(2, 6);
        }

        lote+=10;
        contImoveis ++;
        if (contImoveis == 30) {
            quadra++;
            lote = 10;
            contImoveis = 0;
        }
        return l;
    }

    private Long id() {
        return id++;
    }

    public Boolean verificarCarregamento(){
       time++;
       return time == 2000;
    }


}
