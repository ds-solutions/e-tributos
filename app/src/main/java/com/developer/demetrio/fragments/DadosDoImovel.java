package com.developer.demetrio.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;

import com.developer.demetrio.controladores.ControladorImovel;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.etributos.ListaImoveis;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.Tributo;
import com.developer.demetrio.model.ValoresVenais;
import com.developer.demetrio.repositorio.RepositorioAliquota;
import com.developer.demetrio.repositorio.RepositorioAreasDoImovel;
import com.developer.demetrio.repositorio.RepositorioCadastro;
import com.developer.demetrio.repositorio.RepositorioContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosAtualizadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioDescricaoDaDivida;
import com.developer.demetrio.repositorio.RepositorioEndereco;
import com.developer.demetrio.repositorio.RepositorioIPTU;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.repositorio.RepositorioTributo;
import com.developer.demetrio.repositorio.RepositorioValoresVenais;
import com.developer.demetrio.service.Mail;
import com.developer.demetrio.service.Zap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class DadosDoImovel extends Fragment {
    private long idDescricao = 0;
    private long id;
    private SQLiteDatabase conexao;
    private ConexaoDataBase conexaoDataBase;
    private Spinner motivoNaoEntrega;
    private String[] motivos = new String[] {"Motivo da não entrega", "Demolido", "Imóvel não localizado", "Recusou receber",  "Terreno"};

    private Imovel imovel = new Imovel();
    private AtualizacaoDoContribuinte atualizacaoDoProprietario;
    private Context context;
    private Activity activity;

    private Button btImprimir, btImovelAnterior, btProximoImovel;
    private Fachada fachada = Fachada.getInstance();
    private Location location;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;

    private Mail mail;
    private Zap zap;

    private boolean has = false;

    private TextView matricula, inscricao, cidade, bairro, logradouro, num, complemento,
    zoneamento, valorTributo, contribuinte;

    private ImageView printer, email, whatsApp;
    private ControladorImovel controladorImovel;
    private List<Imovel> imoveis = new ArrayList<Imovel>();
    private long in = 0;
    private long lastIndex;

    public DadosDoImovel(Context context, Activity activity, long id) {
        this.context = context;
        this.activity = activity;
        if (id != 0) {
            this.id = id;
        }
        Fachada.setContext(this.context);
        conexaoDataBase = new ConexaoDataBase();
        this.conexao = conexaoDataBase.concectarComBanco(this.context);
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.imovel = imoveis.buscarImovelPorId(id);
            preencherView();
        }  catch (RepositorioException e) {
            e.printStackTrace();
        }
        lastIndex = this.imovel.getId();
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
        this.btImprimir = (Button) viewGroup.findViewById(R.id.bt_imprimir);
        this.btImovelAnterior = (Button) viewGroup.findViewById(R.id.bt_anterior2);
        this.btProximoImovel = (Button) viewGroup.findViewById(R.id.bt_proximo2);

        preencherView();
        in = id +1;

        ArrayAdapter<String> listMotivoNaoEntregaAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, this.motivos);
        listMotivoNaoEntregaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.motivoNaoEntrega = viewGroup.findViewById(R.id.id_motivo_nao_entrega);
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
        this.btImprimir.setOnClickListener(new C_Imprimir(this.imovel));
        onCreate(savedInstanceState);
        this.btImovelAnterior.setOnClickListener(new ImovelAnterior(this.context, id, lastIndex));
        this.btProximoImovel.setOnClickListener(new ProximoImovel(this.context, id, lastIndex));
        return viewGroup;
    }

    private void preencherView() {
        this.imovel.setCadastro(buscarCadastroDoImovel(this.imovel.getCadastro().getId()));

        this.imovel.setEndereco(buscarEndereco(this.imovel.getEndereco().getId()));

        this.imovel.setContribuinte(buscarContribuinte(this.imovel.getContribuinte().getId()));

        this.imovel.setTributo(buscarTributos(this.imovel.getTributo().getId()));
        System.out.println("MATRICULA DOA COISA -> " +
        this.imovel.getCadastro().getNumCadastro());
       // this.matricula.setText(this.imovel.getCadastro().getNumCadastro().toString());
        this.inscricao.setText(this.imovel.getCadastro().getInscricao().toString());
        this.cidade.setText(this.imovel.getEndereco().getCidade().toString());
        this.bairro.setText(this.imovel.getEndereco().getBairro().toString());
        this.logradouro.setText(this.imovel.getEndereco().getLogradouro().toString());
        this.num.setText(this.imovel.getEndereco().getNumero().toString());
        this.complemento.setText(this.imovel.getEndereco().getComplemento().toString());
        this.zoneamento.setText(this.imovel.getCadastro().getAliquota().getZoneamento().toString());
        this.valorTributo.setText(this.imovel.getTributo().getIptu().getValorTotal().toString());
        this.contribuinte.setText(this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null ?
                this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getNome().toString() : this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNome().toString());

        addImagemNosStatusDoImovel();
    }

    private Tributo buscarTributos(Long id) {

        System.out.println("id do parametro = "+id);

        Tributo tributo = new Tributo();
        tributo.setIptu(new IPTU());
        RepositorioTributo tributos = new RepositorioTributo(this.conexao);
        try {
            tributo = tributos.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        System.out.println("id do tributo = "+tributo.getId());
       tributo.setIptu(buscarIptu(tributo.getIptu().getId()));

        return tributo;
    }

    private IPTU buscarIptu(Long id) {
        IPTU iptu = new IPTU();
        RepositorioIPTU iptus = new RepositorioIPTU(this.conexao);
        try {
            iptu = iptus.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        iptu.setListDescricao(buscarListasDaDescricao(iptu.getId()));
        return iptu;
    }

    private List<DescricaoDaDivida> buscarListasDaDescricao(Long id) {
        List<DescricaoDaDivida> descricaoDaDividas = new ArrayList<>();
        RepositorioDescricaoDaDivida descricoes = new RepositorioDescricaoDaDivida(this.conexao);
        try {
            descricaoDaDividas = descricoes.descricoesDaDividaDe(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return descricaoDaDividas;
    }

    private Contribuinte buscarContribuinte(Long id) {
        Contribuinte contribuinte = new Contribuinte();
        RepositorioContribuinte contribuintes = new RepositorioContribuinte(this.conexao);
        try {
            System.out.println("O id do contribuinte é: "+id);

            contribuinte = contribuintes.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        contribuinte.setDadosCadastradosDoContribuinte(buscarDadosDoContribuinte(contribuinte.getId()));

        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            contribuinte.setAtualizacaoDoContribuinte(buscarAtualizacaoDoContribuinte(contribuinte.getAtualizacaoDoContribuinte().getId()));
        }

        return contribuinte;
    }

    private AtualizacaoDoContribuinte buscarAtualizacaoDoContribuinte(Long id) {
        AtualizacaoDoContribuinte contribuinte = new AtualizacaoDoContribuinte();
        RepositorioDadosAtualizadosDoContribuinte dados = new RepositorioDadosAtualizadosDoContribuinte(this.conexao);
        try {
            contribuinte = dados.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return contribuinte;
    }

    private DadosCadastradosDoContribuinte buscarDadosDoContribuinte(Long id)
    {
        DadosCadastradosDoContribuinte contribuinte = new DadosCadastradosDoContribuinte();
        RepositorioDadosDoContribuinte dados = new RepositorioDadosDoContribuinte(this.conexao);
        try {
            contribuinte = dados.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return contribuinte;
    }

    private Endereco buscarEndereco(Long id) {
        Endereco endereco = new Endereco();
        RepositorioEndereco enderecos = new RepositorioEndereco(this.conexao);
        try {
            endereco = enderecos.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return endereco;
    }

    private Cadastro buscarCadastroDoImovel(long id) {
        Cadastro cadastro = new Cadastro();
        RepositorioCadastro cadastros = new RepositorioCadastro(this.conexao);
        try {
            cadastro = cadastros.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        cadastro.setValoresVenais(buscarValoresVenais(cadastro.getValoresVenais().getId()));
        cadastro.setAreasDoImovel(buscarAreas(cadastro.getAreasDoImovel().getId()));
        cadastro.setAliquota(buscarAliquotas(cadastro.getAliquota().getId()));
       return cadastro;
    }

    private Aliquota buscarAliquotas(Long id) {
        Aliquota aliquota = new Aliquota();
        RepositorioAliquota aliquotas = new RepositorioAliquota(this.conexao);
        try {
            aliquota = aliquotas.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return aliquota;
    }

    private AreasDoImovel buscarAreas(Long id) {
        AreasDoImovel areasDoImovel = new AreasDoImovel();
        RepositorioAreasDoImovel areas = new RepositorioAreasDoImovel(this.conexao);
        try {
            areasDoImovel = areas.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        return areasDoImovel;
    }

    private ValoresVenais buscarValoresVenais(Long id) {
        ValoresVenais valoresVenais = new ValoresVenais();
        RepositorioValoresVenais valores = new RepositorioValoresVenais(this.conexao);
        try {
            valoresVenais = valores.buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
         return valoresVenais;
    }

    private void addImagemNosStatusDoImovel() {
        this.printer.setImageResource(this.imovel.getIndcEmissaoConta() == 1 ? R.drawable.print : R.drawable.un_send_impressora);
        this.whatsApp.setImageResource(this.imovel.getIndcEmissaoConta() == 1 ? R.drawable.whatsapp : R.drawable.un_send_whatsapp1);
        this.email.setImageResource(this.imovel.getIndcEnvioEmail() == 1 ? R.drawable.email : R.drawable.un_send_email);

    }

    class ImovelAnterior implements View.OnClickListener {

        private long index, lastIndex;
        private Context context;

        public ImovelAnterior(Context context, long index, long lastIndex) {
            this.context = context;
            this.index = index;
            this.lastIndex = lastIndex;
        }

        @Override
        public void onClick(View view) {
            if (this.index != 0) {
                this.index--;
            } else if (this.index == 0) {
                index = lastIndex;
            }

            Bundle parametros = new Bundle();
            parametros.putLong("id", index);
            Intent activity = new Intent(this.context, ListaImoveis.class);
            activity.putExtras(parametros);
            startActivity(activity);
        }
    }

    class ProximoImovel implements View.OnClickListener {

        private long index, lastIndex;
        private Context context;

        public ProximoImovel(Context context, long index, long lastIndex) {
            this.context = context;
            this.index = index;
            this.lastIndex = lastIndex;
        }

        @Override
        public void onClick(View view) {
           /* if (this.id < this.lastIndex) {
                this.id++;
            } else if (this.id == this.lastIndex) {
                id = 0;
            }*/
           index++;

            Bundle parametros = new Bundle();
            parametros.putLong("id", index);
            Intent activity = new Intent(this.context, ListaImoveis.class);
            activity.putExtras(parametros);
            startActivity(activity);
        }
    }

    class C_Imprimir implements View.OnClickListener {
        private Imovel imovel;
        public C_Imprimir(Imovel imovel){
            this.imovel = imovel;
        }

        @Override
        public void onClick(View view) {
            if (this.imovel == null) {
                mensagemToast("Não há imóvel para ser impresso");
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
                                try {
                                    sendForImpressora();
                                } catch (ControladorException e) {
                                    e.printStackTrace();
                                }
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
                                try {
                                    sendForImpressora();
                                } catch (ControladorException e) {
                                    e.printStackTrace();
                                }
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
                                try {
                                    sendForImpressora();
                                } catch (ControladorException e) {
                                    e.printStackTrace();
                                }
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
            try {
                sendForImpressora();
            } catch (ControladorException e) {
                e.printStackTrace();
            }
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
                        try {
                            sendForImpressora();
                        } catch (ControladorException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        addImagemNosStatusDoImovel();
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
        this.id++;
       // new DadosDoImovel(this.context, this.activity, this.id);
        Bundle parametros = new Bundle();
        parametros.putLong("id", id);
        Intent activity = new Intent(this.context, ListaImoveis.class);
        activity.putExtras(parametros);
        startActivity(activity);
    }


    private void sendForImpressora() throws ControladorException {
        if (this.fachada.verificarImpressaoConta(this.imovel, this.context.getApplicationContext()).isPeloMenosUmaImpressao()) {
            this.imovel.setIndcEmissaoConta(1);
            /*** TODO: método para inserir a alteração do status do imóvel
             *   método para buscar próximo imóvel
             */
            addImagemNosStatusDoImovel();
            proximoImovel();
        } else {
            mensagemToast("Erro ao tentar imprimir!");
            return;
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
        addImagemNosStatusDoImovel();
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
        addImagemNosStatusDoImovel();
        return;
    }




    @Override
    public void onResume() {
        super.onResume();
    }


}
