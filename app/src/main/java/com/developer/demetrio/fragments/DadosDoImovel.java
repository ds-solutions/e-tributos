package com.developer.demetrio.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.AtualizacaoDoProprietario;
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
import com.developer.demetrio.tributos.ListaImoveis;
import com.developer.demetrio.tributos.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class DadosDoImovel extends Fragment {

    private Spinner motivoNaoEntrega;
    private String[] motivos = new String[] {"Motivo da não entrega", "Demolido", "Imóvel não localizado", "Recusou receber",  "Terreno"};

    private Imovel imovel, ultimoImovel;
    private AtualizacaoDoProprietario atualizacaoDoProprietario;
    private Context context;
    private Activity activity;

    private Button btImprimir;
    private Fachada fachada = Fachada.getInstance();
    //  private OnClickListener imprimir = new C_Imprimir();
    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 10, FASTEST_INTRVAL = 10;

    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissonsRejected  = new ArrayList<>();
    private ArrayList<String> permissions  = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    private Mail mail;
    private Zap zap;

    private LatLng latLng;
    private int ultimaDistancia = 12;

    private boolean has = false;

    private TextView matricula, inscricao, cidade, bairro, logradouro, num, complemento,
    zoneamento, valorTributo, contribuinte;

    private ImageView printer, email, whatsApp;

    public DadosDoImovel(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        Fachada.setContext(this.context);

        this.imovel = popularImovelParaImpressao();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dados_do_imovel, container, false);
        this.matricula = (TextView) viewGroup.findViewById(R.id.id_matricula);
        this.inscricao = (TextView) viewGroup.findViewById(R.id.id_inscricao);
        this.cidade = (TextView) viewGroup.findViewById(R.id.id_cidade);
        this.bairro = (TextView) viewGroup.findViewById(R.id.id_bairro);
        this.logradouro = (TextView) viewGroup.findViewById(R.id.id_logradouro);
        this.num = (TextView) viewGroup.findViewById(R.id.id_numero);
        this.complemento = (TextView) viewGroup.findViewById(R.id.id_complemento);
        this.zoneamento = (TextView) viewGroup.findViewById(R.id.zonemanento);
        this.valorTributo = (TextView) viewGroup.findViewById(R.id.valor_tributo);
        this.contribuinte = (TextView) viewGroup.findViewById(R.id.id_contribuinte);
        this.printer = viewGroup.findViewById(R.id.status_impressora);
        this.email =  viewGroup.findViewById(R.id.status_email);
        this.whatsApp =  viewGroup.findViewById(R.id.status_whatsapp);

        this.printer.setVisibility(this.imovel.getIndcEmissaoConta() == 1 ? View.VISIBLE : View.INVISIBLE);
        this.email.setVisibility(this.imovel.getIndcEnvioEmail() == 1 ? View.VISIBLE : View.INVISIBLE);
        this.whatsApp.setVisibility(this.imovel.getIndcEnvioZap() == 1 ? View.VISIBLE : View.INVISIBLE);


        preencherView();

        this.btImprimir = (Button) viewGroup.findViewById(R.id.bt_imprimir);

        ArrayAdapter<String> listMotivoNaoEntregaAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, this.motivos);
        listMotivoNaoEntregaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.motivoNaoEntrega = (Spinner) viewGroup.findViewById(R.id.id_motivo_nao_entrega);
        this.motivoNaoEntrega.setAdapter(listMotivoNaoEntregaAdapter);
        this.motivoNaoEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(motivos[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.btImprimir.setOnClickListener(new C_Imprimir());
        onCreate(savedInstanceState);
       // init();

        return viewGroup;
    }

    private void preencherView() {
        this.matricula.setText(this.imovel.getCadastro().getNumCadastro());
        this.inscricao.setText(this.imovel.getCadastro().getInscricao());
        this.cidade.setText(this.imovel.getEndereco().getCidade());
        this.bairro.setText(this.imovel.getEndereco().getBairro());
        this.logradouro.setText(this.imovel.getEndereco().getLogradouro());
        this.num.setText(this.imovel.getEndereco().getNumero());
        this.complemento.setText(this.imovel.getEndereco().getComplemento());
        this.zoneamento.setText(this.imovel.getCadastro().getAliquota().getZoneamento());
        this.valorTributo.setText(this.imovel.getTributo().getIptu().getValorTotal());
        this.contribuinte.setText(imovel.getAtualizacaoDoProprietario() != null ?
                this.imovel.getAtualizacaoDoProprietario().getNome() : this.imovel.getContribuinte().getNome());

    }



    class C_Imprimir implements View.OnClickListener {
        private Imovel imovel;
        public C_Imprimir(){}

        @Override
        public void onClick(View view) {
            if (this.imovel == null) {
                this.imovel = popularImovelParaImpressao();
            }
            if (this.imovel != null) {
                imprimirTributo();
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
                new AlertDialog.Builder(this.context)
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
                new AlertDialog.Builder(this.context)
                        .setMessage("Enviar o IPTU " +this.imovel.getTributo().getIptu().getExercicio()
                                +" via e-mail?").setPositiveButton("Sim", dialogEmail)
                        .setNegativeButton("Não", dialogEmail).show();
            }

            if (isHasCellPhone() && isHasEmail()) {
                AlertDialog.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ultimoImovel = imovel;
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
                new AlertDialog.Builder(this.context)
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

        new AlertDialog.Builder(this.context).setMessage("Deseja imprimir")
                .setPositiveButton("Sim", dialog)
                .setNeutralButton("Não", dialog).show();
    }

    private void proximoImovel() {
        System.out.println("Dentro da class DadosDoImovel");
        Intent intent = new Intent(this.context, ListaImoveis.class);
        startActivity(intent);
    }


    private void sendForIpressora() {
        if (this.fachada.verificarImpressaoConta(this.imovel, this.context.getApplicationContext()).isPeloMenosUmaImpressao()) {
            this.imovel.setIndcEmissaoConta(1);
           setarLocalizacao();

            /*** TODO: método para salvar a alteração do status do imóvel
             *   método para buscar próximo imóvel
             */
            proximoImovel();
        } else {
            mensagemToast("Erro ao tentar imprimir!");
            return;
        }
    }

    public void setarLocalizacao() {
        System.out.println("Método setarLocalizacao");
        if (this.latLng != null) {
            this.imovel.setLatLng(new LatLng(this.latLng.getLatitude(), this.latLng.getLongitude()));
        } else {
         //   setarLocalizacao();
        }
    }

    /*
        private void nextImovel(String mensagem) {
            if (ListaImoveis.this.imovel != null) {
                AlertDialog.OnClickListener alertaDialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case -1:
                                Intent activity = new Intent(getApplicationContext(), ConsultarImoveis.class);
                                startActivity(activity);
                                break;
                            default:
                            break;
                        }
                    }
                };
                new AlertDialog.Builder(this).setMessage(mensagem)
                        .setPositiveButton("Ok", alertaDialog).show();
            }
        }
    */
    public void mensagemToast(String text) {
        Toast.makeText(this.context, text, LENGTH_LONG).show();
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
        if (intentSend.resolveActivity(this.context.getPackageManager()) != null) {
            startActivity(intentSend);
        } else {
            onPause();
            Toast.makeText(this.context, "Erro ao tentar enviar mensagem via WhatsApp, tente novamente!", LENGTH_LONG).show();
        }
        this.zap.rejetar();
        return;
    }

    private ArrayList<String> permissionsToTequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();
        for (String permission: wantedPermissions) {
            if (!hasPermission(permission)) {
                result.add(permission);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this.context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private boolean checkePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this.context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this.activity, resultCode, PLAY_SERVICE_RESOLUTION_REQUEST);
            } else {
                this.activity.finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Método onStart");
        if (this.googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Método onResume");
        if (!checkePlayServices()) {
            Toast.makeText(this.context, "É necessário instalar o Google Play Services neste dispositivo!!!", LENGTH_LONG).show();
        }


    }

    public Imovel popularImovelParaImpressao() {
        this.imovel = new Imovel();
        Aliquota aliquota = new Aliquota();
        CodigoDeCobranca codigo = new CodigoDeCobranca();
        codigo.setId(1L);
        this.imovel.setIndcEmissaoConta(1);
        this.imovel.setIndcEnvioZap(1);

        codigo.setTipo("1-NORMAL");
        codigo.setTaxaTestada("6,00");
        aliquota.setId(1L);
        aliquota.setCodigoDeCobranca(codigo);
        aliquota.setTipoConstrucao("EDIFICADO");
        aliquota.setEdificado("1,00");
        aliquota.setTerreno("1,00");
        aliquota.setZoneamento("3");

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

    private List<DescricaoDaDivida> listDescricao() {
        List<DescricaoDaDivida> list = new ArrayList<>();
        DescricaoDaDivida d1, d2, d3;
        d1 = new DescricaoDaDivida(1L, "01", "IPTU", "67,73", "13,55", "0,00");
        d2 = new DescricaoDaDivida(2L, "02", "TAXA DE EXPEDIENTE", "30,00", "6,00", "0,00");
        d3 = new DescricaoDaDivida(3L, "03", "COLETA DE LIXO", "20,00", "4,00", "0,00");
        list.add(d1);
        list.add(d2);
        list.add(d3);

        return list;
    }


}
