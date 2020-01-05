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
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.fragments.DadosDoImovel;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.CodigoDeCobranca;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.LatLng;
import com.developer.demetrio.model.ValoresVenais;
import com.developer.demetrio.service.GPS_Service;
import com.developer.demetrio.service.Mail;
import com.developer.demetrio.service.Zap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class ListaImoveis extends AppCompatActivity {

    private Imovel imovel, ultimoImovel;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Button btImprimir;
    private Fachada fachada = Fachada.getInstance();
    //  private OnClickListener imprimir = new C_Imprimir();
    private Location location;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private long UPDATE_INTERVAL = 10, FASTEST_INTRVAL = 10;

    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissonsRejected  = new ArrayList<>();
    private ArrayList<String> permissions  = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    public static final Integer FRAGMENTO_DADOS_DO_IMOVEL = 1;

    private Mail mail;
    private Zap zap;

    private LatLng latLng;
    private int ultimaDistancia = 12;
    private Integer index;
    private boolean has = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imoveis);
        List<Fragment> fragments = new ArrayList<>();
        Intent intent = getIntent();

        if (intent != null) {
            System.out.println("intent não nulo");
            Bundle parametros = intent.getExtras();
            if (parametros != null) {
                System.out.println("paramentros não nulo");
                this.index = parametros.getInt("index");
            } else if (parametros == null) {
                System.out.println("paramentros está nulo");
            }
        } else if (intent == null) {
            System.out.println("intent está nulo");
        }

        fragments.add(new DadosDoImovel(this, this, this.index));
        fragments.add(new DadosDeAtualizacaoProprietario(this, this.index));

        this.viewPager = (ViewPager) findViewById(R.id.view_page);
        this.pagerAdapter = new PageViewAdapter(getSupportFragmentManager(), fragments);
        this.viewPager.setAdapter(this.pagerAdapter);

        Fachada.setContext(this);
     //   this.btImprimir = (Button) findViewById(R.id.bt_imprimir);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Menu.class));
        finishAffinity();
        return;
    }

    /*

    class C_Imprimir implements OnClickListener {
       private Imovel imovel;
       public C_Imprimir(){}

        @Override
        public void onClick(View view) {
            if (this.imovel == null) {
              this.imovel = ListaImoveis.this.popularImovelParaImpressao();
            }
            if (this.imovel != null) {
                ListaImoveis.this.imprimirTributo();
            }
        }
    }

    public void imprimirTributo(){
        if (isHasCellPhone() || isHasEmail()) {
            if (!isHasEmail() && isHasCellPhone()) {
                AlertDialog.OnClickListener dialogZap = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case -1:
                            sendForWhatsApp();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por WhatsApp!");
                                    e.printStackTrace();
                                }
                                enviadoPeloZap();
                                isImprimir();
                                break;
                            default:
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendForIpressora();
                              break;
                        }
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage("Enviar o IPTU " +this.imovel.getTributo().getIptu().getExercicio()
                                +" via whatsapp?").setPositiveButton("Sim", dialogZap)
                        .setNegativeButton("Não", dialogZap).show();
          }

            if (!isHasCellPhone() && isHasEmail()) {
                AlertDialog.OnClickListener dialogEmail = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case -1:
                                sendForEmail();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por e-mail!");
                                    e.printStackTrace();
                                }
                                enviadoPeloEmail();
                                isImprimir();
                                break;
                            default:
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendForIpressora();
                                break;
                        }
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage("Enviar o IPTU " +this.imovel.getTributo().getIptu().getExercicio()
                         +" via e-mail?").setPositiveButton("Sim", dialogEmail)
                        .setNegativeButton("Não", dialogEmail).show();
            }

            if (isHasCellPhone() && isHasEmail()) {
                AlertDialog.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ListaImoveis.this.ultimoImovel = ListaImoveis.this.imovel;
                        switch (i) {
                            case -1:
                                sendForEmail();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por e-mail!");
                                    e.printStackTrace();
                                }
                                enviadoPeloEmail();
                                isImprimir();
                            break;
                            case -2:
                               sendForWhatsApp();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por WhatsApp!");
                                    e.printStackTrace();
                                }
                                enviadoPeloZap();
                                isImprimir();
                                break;
                            default:
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendForIpressora();
                            break;
                        }
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage("Enviar o IPTU " +this.imovel.getTributo().getIptu().getExercicio()
                                +" via?").setNeutralButton("cancelar", dialog)
                        .setPositiveButton("e-mail", dialog)
                        .setNegativeButton("WhatsApp", dialog).show();
            }

        } else {
            sendForIpressora();
            }
    }

    private void enviadoPeloZap() {
        this.imovel.setIndcEnvioZap(1);
    }

    private void enviadoPeloEmail() {
        this.imovel.setIndcEnvioEmail(1);
    }

    private void isImprimir() {
        AlertDialog.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case -1:
                       sendForIpressora();
                    break;
                    default:
                    proximoImovel();
                    break;
                }
            }
        };

        new AlertDialog.Builder(this).setMessage("Deseja imprimir")
                .setPositiveButton("Sim", dialog)
                .setNeutralButton("Não", dialog).show();
    }

    public void proximoImovel() {
        System.out.println("Dentro da class ListaImoveis");
        Intent activity = new Intent(getApplicationContext(), ListaImoveis.class);
        startActivity(activity);
    }

    public void sendForIpressora() {
  //      if (this.fachada.verificarImpressaoConta(this.imovel, getApplicationContext()).isPeloMenosUmaImpressao()) {
            this.imovel.setIndcEmissaoConta(1);


            /*** TODO: método para inserir a alteração do status do imóvel
             *   método para buscar próximo imóvel
             */
      //      proximoImovel();
       /* } else {
            mensagemToast("Erro ao tentar imprimir!");
            return;
        }*/
  //  }


/*

    public void mensagemToast(String text) {
        Toast.makeText(this, text, LENGTH_LONG).show();
    }

    private boolean isHasCellPhone() {
        return true; //  this.imovel.getContribuinte().getNumeroCelular() != null;
    }



    private boolean isHasEmail() {
        return true; // this.imovel.getContribuinte().getEmail() != null;
    }


    private void sendForEmail() {
        //TODO: ESSA FUNCINALIDADE DEU CERTO, IUPIIIIII!
        this.mail = new Mail();
        this.mail.prepararEmail(this.imovel, 0);

        Intent intentSend = new Intent(Intent.ACTION_SEND);
        intentSend.putExtra(Intent.EXTRA_EMAIL, mail.getEmails());
        intentSend.putExtra(Intent.EXTRA_SUBJECT, mail.getTituloDoEmail());
        intentSend.putExtra(Intent.EXTRA_TEXT, mail.getMensagem());
        intentSend.setType("message/rfc822");
        startActivity(Intent.createChooser(intentSend,"Escolha um app para enviar o email!"));

        this.mail.rejetar();
        return;
    }

    private void sendForWhatsApp() {
        this.zap = new Zap();
        this.zap.prepararZap(this.imovel, 0);
        String contato = "+55"+ zap.getDestino();
        Intent intentSend = new Intent("android.intent.action.MAIN");
        intentSend.setAction(Intent.ACTION_VIEW);
        intentSend.setPackage("com.whatsapp");
        String url = null;
        try {
             url = "https://api.whatsapp.com/send?phone="+ contato+ "&text=" + URLEncoder.encode(zap.getMensagem(), "UTF-8");
        }  catch (IOException ioe) {
            ioe.printStackTrace();
        }
     intentSend.setData(Uri.parse(url));
     if (intentSend.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intentSend);
        } else {
         onPause();
         Toast.makeText(this, "Erro ao tentar enviar mensagem via WhatsApp, tente novamente!", LENGTH_LONG).show();
        }
        this.zap.rejetar();
        return;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public Imovel popularImovelParaImpressao() {
        this.imovel = new Imovel();
        Aliquota aliquota = new Aliquota();
        CodigoDeCobranca codigo = new CodigoDeCobranca();
        codigo.setId(1L);
        codigo.setTipo("1-NORMAL");
        codigo.setTaxaTestada("6,00");
        aliquota.setId(1L);
        aliquota.setCodigoDeCobranca(codigo);
        aliquota.setTipoConstrucao("EDIFICADO");
        aliquota.setEdificado("1,00");
        aliquota.setTerreno("1,00");
        aliquota.setZoneamento("1");

        Cadastro cadastro = new Cadastro();
        cadastro.setAliquota(aliquota);
        AreasDoImovel areasDoImovel = new AreasDoImovel();
        areasDoImovel.setAliquota(aliquota);
        areasDoImovel.setAreaDoTerreno("132,00");
        areasDoImovel.setEdificado("50,00");
        areasDoImovel.setAreaTotalEdificado("50,00");
        areasDoImovel.setAreaDoTerreno("132,00");
        areasDoImovel.setExcedente("0,00");
        areasDoImovel.setTestada("6,00");
        areasDoImovel.setFracao("1.000000");

        cadastro.setAreasDoImovel(areasDoImovel);
        cadastro.setDistrito("01");
        cadastro.setId(1L);
        cadastro.setInscricao("01.01.001.0028.000");
        cadastro.setLote("0028");
        cadastro.setNumCadastro("000004");
        cadastro.setQuadra("001");
        cadastro.setSetor("01");
        cadastro.setUnidade("000");
        ValoresVenais valores = new ValoresVenais();
        valores.setId(1L);
        valores.setEdificada("1.229,00");
        valores.setExcedente("0,00");
        valores.setTerreno("5.544,00");
        valores.setTotal("6.773,00");
        cadastro.setValoresVenais(valores);

        this.imovel.setCadastro(cadastro);

        Endereco endereco = new Endereco();
        endereco.setUf("PE");
        endereco.setNumero("129");
        endereco.setLogradouro("JOSÉ TAVARES DO REGO");
        endereco.setComplemento("CASA");
        endereco.setCidade("LAGOA DE ITAENGA");
        endereco.setCep("55840-000");
        endereco.setBairro("INDEPENDENCIA");
        endereco.setId(1L);

        this.imovel.setEndereco(endereco);

        Contribuinte contribuinte = new Contribuinte();
        contribuinte.setNome("DEMÉTRIO ANTONIO DE SANTANA");
        contribuinte.setCpf("010.750.114-77");
        contribuinte.setEstadoCivil("CASADO");
        contribuinte.setNacionalidade("BRASILEIRA");


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
        this.imovel.getTributo().getIptu().setMensagem("Testando a aplicação inserindo a 1ª linha com mensagem.");
        this.imovel.getTributo().getIptu().setMensagem1("Continuando os testes inserindo a 2ª linha ou mensagem, com mais carctere para ver o que acontece, vamos ver!");
        this.imovel.getTributo().getIptu().setMensagem2("Da mesma forma estamos testando a terceira mensagem, se houver inserindo-a na 3ª e ultima mensagem enviado via arquivo pelo sistema da contadora.");
        this.imovel.getTributo().getIptu().setSomaDoDesconto("23,55");
        this.imovel.getTributo().getIptu().setSomaDoValor("117,73");
        this.imovel.getTributo().getIptu().setSomaIsencao("0,00");
        this.imovel.getTributo().getIptu().setVencimento("25/09/2020");
        this.imovel.getTributo().getIptu().setListDescricao(listDescricao());

       /* impressaoIPTU = new FormatarIPTU();
        impressaoIPTU.gerarIPTU(this.imovel);*/
/*
    return this.imovel;
    }



    private List<DescricaoDaDivida> listDescricao() {
        List<DescricaoDaDivida> list = new ArrayList<>();
        DescricaoDaDivida d1, d2, d3;
        d1 = new DescricaoDaDivida(1L, "01", "IPTU", "67,73", "13,55", "0,00");
        d2 = new DescricaoDaDivida(2L, "02", "TAXA DE EXPEDIENTE", "30,00", "6,00", "0,00");
        d3 = new DescricaoDaDivida(2L, "03", "COLETA DE LIXO", "20,00", "4,00", "0,00");
        list.add(d1);
        list.add(d2);
        list.add(d3);

        return list;
    }
*/
    /*
    public void atualizar(View view) {
        Toast t = new Toast(this);
    }
    */
}
