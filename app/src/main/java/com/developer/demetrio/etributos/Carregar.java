package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.demetrio.databases.ConexaoDataBase;
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

public class Carregar extends AppCompatActivity {
    Boolean carregado = false;
    ProgressBar looping;
    long time = 0;
    Thread thread;
    TextView status;
    private List<Imovel> imoveis = new ArrayList<>();
    private Handler handler;

    private String campo1, campo2, campo3, campo4, codigoDeBarras;

    private SQLiteDatabase conexao;

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
                       e.printStackTrace();
                   }

               } while (!carregado);

               Intent menu = new Intent(getApplicationContext(), Menu.class);
               startActivity(menu);
           }

       }).start();
    }

    private void conectarAoBanco() {
        ConexaoDataBase conexaoDataBase;
        conexaoDataBase = new ConexaoDataBase(getApplicationContext());

           this.conexao = conexaoDataBase.concectarComBanco(this);
            int i = 0;
            RepositorioImovel imoveis =  new RepositorioImovel(this.conexao);
            try {
                if (imoveis.getQtdImoveis() < 7) {
                    while (i < 7){
                        popularImovelParaImpressao();
                        i++;
                        Toast.makeText(this, i + "º Imovel cadastrado com cadastro "+this.imovel.getCadastro().getNumCadastro()+" com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (RepositorioException e) {
                e.printStackTrace();
            }

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

        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.imovel.setId(imoveis.inserir(this.imovel));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.imovel;
    }

    private Tributo popularTributo() {
        Tributo tributo = new Tributo();
        tributo.setIptu(pupularIPTU());
        RepositorioTributo tributos = new RepositorioTributo(this.conexao);
        try {
            tributo.setId(tributos.inserir(tributo));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
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
        RepositorioIPTU iptus = new RepositorioIPTU(this.conexao);
        try {
            iptu.setId(iptus.inserir(iptu));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
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
            RepositorioDescricaoDaDivida dividas = new RepositorioDescricaoDaDivida(this.conexao);
            try {
                divida.setId_IPTU(dividas.inserir(divida));
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            descricoes.add(divida);
        }
        return descricoes;
    }

    private void gerarCodigo() {
        String c1 = String.valueOf(new Random().nextLong());
        String c2 = String.valueOf(new Random().nextLong());
        String c3 = String.valueOf(new Random().nextLong());
        String c4 = String.valueOf(new Random().nextLong());

        campo1 = c1.substring(1, 11)+"-"+c1.substring(6, 6);
        campo2 = c2.substring(1, 11)+"-"+c1.substring(6, 6);
        campo3 = c3.substring(1, 11)+"-"+c1.substring(6, 6);
        campo4 = c3.substring(1, 11)+"-"+c1.substring(6, 6);
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
        RepositorioEndereco enderecos = new RepositorioEndereco(this.conexao);
        try {
            endereco.setId(enderecos.inserir(endereco));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return endereco;
    }

    private Contribuinte popularContribuinte() {
        Contribuinte contribuinte = new Contribuinte();

        contribuinte.setDadosCadastradosDoContribuinte(new DadosCadastradosDoContribuinte());
        contribuinte.setDadosCadastradosDoContribuinte(popularDadosDoContribuinte());

        RepositorioContribuinte contribuintes = new RepositorioContribuinte(this.conexao);
        try {
            contribuinte.setId(contribuintes.inserir(contribuinte));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
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
        RepositorioDadosDoContribuinte dados = new RepositorioDadosDoContribuinte(this.conexao);
        try {
            contribuinte.setId(dados.inserir(contribuinte));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
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
        return "81 9"+ String.valueOf(new Random().nextInt(9999))+"-"+String.valueOf(new Random().nextInt(9999));
    }

    private Cadastro popularCadastro() {
        Cadastro cadastro = new Cadastro();
        cadastro.setDistrito("01");
        cadastro.setSetor("02");
        cadastro.setQuadra("015");
        cadastro.setLote(lote());
        cadastro.setUnidade("000");
        cadastro.setInscricao(cadastro.getDistrito() + "." + cadastro.getSetor() +"."+
                cadastro.getQuadra() +"."+ cadastro.getLote()+"."+ cadastro.getUnidade());
        cadastro.setNumCadastro(campo1.substring(1, 6));
        cadastro.setAliquota(popularAliquota());
        cadastro.setAreasDoImovel(pupularAreasDoImovel());
        cadastro.setValoresVenais(popularValoresVenais());

        RepositorioCadastro cadastros = new RepositorioCadastro(this.conexao);
        try {
            cadastro.setId(cadastros.inserir(cadastro));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        return cadastro;
    }

    private ValoresVenais popularValoresVenais() {
        ValoresVenais valoresVenais = new ValoresVenais();
        valoresVenais.setTotal("6.057,00");
        valoresVenais.setExcedente("0,00");
        valoresVenais.setEdificada("3.257,00");
        valoresVenais.setTerreno("2.800,00");

        RepositorioValoresVenais valores = new RepositorioValoresVenais(this.conexao);
        try {
            valoresVenais.setId(valores.inserir(valoresVenais));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

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

        RepositorioAreasDoImovel areas = new RepositorioAreasDoImovel(this.conexao);
        try {
            area.setId(areas.inserir(area));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return area;
    }

    private Aliquota popularAliquota() {
        Aliquota aliquota = new Aliquota();
        aliquota.setTipoConstrucao("EDIFICADO");
        aliquota.setZoneamento("1");
        aliquota.setEdificado("1,00");
        aliquota.setTerreno("1,00");
        aliquota.setCodigoDeCobranca(popularCodigo());

        RepositorioAliquota aliquotas = new RepositorioAliquota(this.conexao);
        try {
            aliquota.setId(aliquotas.inserir(aliquota));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        return aliquota;
    }

    private CodigoDeCobranca popularCodigo() {
        CodigoDeCobranca codigo =  new CodigoDeCobranca();
        codigo.setTipo("1-NORMAL");
        codigo.setTaxaTestada("5,00");

        RepositorioCodigoDeCobranca codigos = new RepositorioCodigoDeCobranca(this.conexao);
        try {
            codigo.setId(codigos.inserir(codigo));
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
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
        return "00"+String.valueOf(lote+=10);
    }

    private Long id() {
        return id++;
    }

    public Boolean verificarCarregamento(){
       time++;
       return time == 2000;
    }


}
