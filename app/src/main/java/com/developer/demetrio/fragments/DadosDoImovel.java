package com.developer.demetrio.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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


import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.etributos.ListaImoveis;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fachada.Fachada;

import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.service.Mail;
import com.developer.demetrio.service.Zap;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;

import static android.widget.Toast.LENGTH_LONG;

public class DadosDoImovel extends Fragment {
    private static final String[] SEND_FOR = new String[]{"EMAIL", "WHATSAPP"};
    private long id;
    private SQLiteDatabase conexao;
    RepositorioImovel imoveis = null;
    private Spinner motivoNaoEntrega;
    private String[] motivos = new String[]{"Motivo da não entrega", "Demolido", "Imóvel não localizado", "Recusou receber", "Terreno"};

    private Integer totalImoveis;

    private Imovel imovel = new Imovel();
    private Context context;
    private Activity activity;

    private Button btImprimir, btImovelAnterior, btProximoImovel;
    private Fachada fachada = Fachada.getInstance();
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;

    private Mail mail;
    private Zap zap;
    private TextView matricula, inscricao, cidade, bairro, logradouro, num, complemento,
            zoneamento, valorTributo, contribuinte, msgUsuario, statusQtdImoveis;

    private ImageView printer, email, whatsApp;
    private boolean concluiu = false;
    private boolean cadastroAtualizado = false;


    public DadosDoImovel(Context context, Activity activity, Imovel imovel) {
        this.context = context;
        this.activity = activity;
        if (imovel != null) {
            this.imovel = imovel;
            this.id = imovel.getId();
        }
        Fachada.setContext(this.context);
        conectarBanco();
        try {
            this.totalImoveis = imoveis.getQtdImoveis();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        conectarBanco();
        try {
            this.concluiu = imoveis.rotaFinalizada(this.totalImoveis);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
    }

    private void conectarBanco() {
        this.conexao = new ConexaoDataBase().concectarComBanco(this.context);
        this.imoveis = new RepositorioImovel(this.conexao);
    }
    private void desconectarBanco() {
        this.conexao.close();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);
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
        this.statusQtdImoveis = (TextView) viewGroup.findViewById(R.id.id_qtd_imoveis);
        this.msgUsuario = (TextView) viewGroup.findViewById(R.id.id_msg_usuario);
        this.printer = (ImageView) viewGroup.findViewById(R.id.status_impressora);
        this.email = (ImageView) viewGroup.findViewById(R.id.status_email);
        this.whatsApp = (ImageView) viewGroup.findViewById(R.id.status_whatsapp);
        this.btImprimir = (Button) viewGroup.findViewById(R.id.bt_imprimir);
        this.btImovelAnterior = (Button) viewGroup.findViewById(R.id.bt_anterior2);
        this.btProximoImovel = (Button) viewGroup.findViewById(R.id.bt_proximo2);


        addImagemNosStatusDoImovel();
        ArrayAdapter<String> listMotivoNaoEntregaAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, this.motivos);
        listMotivoNaoEntregaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.motivoNaoEntrega = viewGroup.findViewById(R.id.id_motivo_nao_entrega);
        this.motivoNaoEntrega.setAdapter(listMotivoNaoEntregaAdapter);
        this.motivoNaoEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imovel.setMotivoDaNaoEntrega(motivos[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.btImprimir.setOnClickListener(new C_Imprimir(this.imovel));

        this.btImovelAnterior.setOnClickListener(new ImovelAnterior(this.context, id, totalImoveis));
        this.btProximoImovel.setOnClickListener(new ProximoImovel(this.context, id, totalImoveis));

        preencherView();

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
        this.contribuinte.setText(getNome());
        this.statusQtdImoveis.setText(String.valueOf(this.id) + "/" + String.valueOf(this.totalImoveis));
        this.msgUsuario.setText(getmessage());
        if (StringUtils.isNotBlank(this.imovel.getMotivoDaNaoEntrega())) {
            int i = 0;
            for (String motivo : motivos) {
                if (motivo.equals(this.imovel.getMotivoDaNaoEntrega())) {
                    motivoNaoEntrega.setSelection(i);
                }
                i++;
            }
        }
    }

    private int getmessage() {
        int mensagem = R.string.nulo;
        int i = (!StringUtils.isNotBlank(this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getCpf()) ||
                !StringUtils.isNotBlank(this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getRg()) ||
                !StringUtils.isNotBlank(this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNumeroCelular()) ||
                !StringUtils.isNotBlank(this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getEmail())) ? 1 : 0;
        if (i == 1 && !cadastroAtualizado()) {
          //  if (cadastroAtualizado())
            this.msgUsuario.setBackgroundResource(android.R.color.holo_red_dark);
            mensagem = R.string.atualizar_dados;
        }
        if (this.concluiu) {
            mensagem = R.string.rota_finalizada;
        }
        return mensagem;
    }

    private boolean cadastroAtualizado() {
        return this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null &&
                StringUtils.isNotBlank(this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getCpfCnpj());
    }

    private String getNome() {
        String nome = this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null && this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getId() != 0 ?
                this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getNome() : this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNome();
        this.cadastroAtualizado = this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null ? true : false;
        return nome;
    }

    private void addImagemNosStatusDoImovel() {
        this.printer.setImageResource(this.imovel.getIndcEmissaoConta() == 1 ? R.drawable.print : R.drawable.un_send_impressora);
        this.whatsApp.setImageResource(this.imovel.getIndcEnvioZap() == 1 ? R.drawable.whatsapp : R.drawable.un_send_whatsapp1);
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
            if (this.index != 1) {
                this.index--;
            } else if (this.index == 1) {
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
            if (this.index < this.lastIndex) {
                this.index++;
            } else if (this.index == this.lastIndex) {
                index = 1;
            }
            Bundle parametros = new Bundle();
            parametros.putLong("id", index);
            Intent activity = new Intent(this.context, ListaImoveis.class);
            activity.putExtras(parametros);
            startActivity(activity);
        }
    }

    class C_Imprimir implements View.OnClickListener {
        private Imovel imovel;

        public C_Imprimir(Imovel imovel) {
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

    public void imprimirTributo() {
        if (isHasCellPhone(this.imovel.getContribuinte()) || isHasEmail(this.imovel.getContribuinte())) {
            if (!isHasEmail(this.imovel.getContribuinte()) && isHasCellPhone(this.imovel.getContribuinte())) {
                AlertDialog.OnClickListener dialogZap = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case -1:
                                try {
                                    sendForWhatsApp();
                                    Thread.sleep(1500);

                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por WhatsApp!");
                                    e.printStackTrace();
                                }
                                enviado(SEND_FOR[1]);
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
                        .setMessage("Enviar o IPTU " + this.imovel.getTributo().getIptu().getExercicio()
                                + " via whatsapp?").setPositiveButton("Sim", dialogZap)
                        .setNegativeButton("Não", dialogZap).show();
            }

            if (!isHasCellPhone(this.imovel.getContribuinte()) && isHasEmail(this.imovel.getContribuinte())) {
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
                                enviado(SEND_FOR[0]);
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
                        .setMessage("Enviar o IPTU " + this.imovel.getTributo().getIptu().getExercicio()
                                + " via e-mail?").setPositiveButton("Sim", dialogEmail)
                        .setNegativeButton("Não", dialogEmail).show();
            }

            if (isHasCellPhone(this.imovel.getContribuinte()) && isHasEmail(this.imovel.getContribuinte())) {
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
                                enviado(SEND_FOR[0]);
                                break;
                            case -2:
                                sendForWhatsApp();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    mensagemToast("Erro ao tentar enviar por WhatsApp!");
                                    e.printStackTrace();
                                }
                                enviado(SEND_FOR[1]);
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
                        .setMessage("Enviar o IPTU " + this.imovel.getTributo().getIptu().getExercicio()
                                + " via?").setNeutralButton("(Imprimir)", dialog)
                        .setPositiveButton("e-mail", dialog)
                        .setNegativeButton("WhatsApp", dialog).show();
            }

        } else {
            try {
                sendForImpressora();
            } catch (ControladorException e) {
                Toast.makeText(this.context, "Erro de conexão com a impressora!", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }
    }

    private void enviado(final String s) {
        AlertDialog.OnClickListener dialog = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case -1:
                        if (s == SEND_FOR[0]) {
                            enviadoPeloEmail();
                        } else if (s == SEND_FOR[1]) {
                            enviadoPeloZap();
                        }
                        break;
                    default:
                        break;
                }
                isImprimir();
            }
        };
        new AlertDialog.Builder(this.context).setMessage("A mensagem foi enviada com êxito?")
                .setPositiveButton("Sim", dialog).setNegativeButton("Não", dialog).show();
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

        new AlertDialog.Builder(this.context).setMessage("Deseja imprimir?")
                .setPositiveButton("Sim", dialog)
                .setNeutralButton("Não", dialog).show();
    }

    private void proximoImovel() {
        if (this.imovel.getIndcEnvioZap() == 1 ||
                this.imovel.getIndcEnvioEmail() == 1 ||
                this.imovel.getIndcEmissaoConta() == 1) {
            autalizarStatusDeEnvio();
        }
        if (this.id < this.totalImoveis) {
            this.id++;
        } else if (this.id == this.totalImoveis) {
            this.id = 1;
        }
        Bundle parametros = new Bundle();
        parametros.putLong("id", id);
        Intent activity = new Intent(this.context, ListaImoveis.class);
        activity.putExtras(parametros);
        startActivity(activity);
    }

    private void autalizarStatusDeEnvio() {
        this.conexao = new ConexaoDataBase().concectarComBanco(this.context);
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            imoveis.atualizarIndicadorDeEnvio(this.imovel.getId(), this.imovel);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        this.conexao.close();
    }

    private void sendForImpressora() throws ControladorException {
        if (this.motivoNaoEntrega.getSelectedItem() != motivos[0]) {
            this.conexao = new ConexaoDataBase().concectarComBanco(this.context);
            RepositorioImovel imoveis = new RepositorioImovel(conexao);
            try {
                imoveis.atualizarMotivoDaNaoEntrega(this.imovel);
            } catch (RepositorioException e) {
                e.printStackTrace();
            }
            this.conexao.close();
            proximoImovel();
        }

        if (this.motivoNaoEntrega.getSelectedItem() == this.motivos[0]) {
            if (this.fachada.verificarImpressaoConta(this.imovel, this.context.getApplicationContext()).isPeloMenosUmaImpressao()) {
                this.imovel.setIndcEmissaoConta(1);
                addImagemNosStatusDoImovel();
                proximoImovel();
            } else {
                mensagemToast("Erro ao tentar imprimir!");
            }
        }
    }

    public void mensagemToast(String text) {
        Toast.makeText(this.context, text, LENGTH_LONG).show();
    }

    private boolean isHasCellPhone(Contribuinte contribuinte) {
        boolean retorno = false;

        if (StringUtils.isNotBlank(contribuinte.getDadosCadastradosDoContribuinte().getNumeroCelular())) {
            retorno = true;
        }

        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            if (StringUtils.isNotBlank(contribuinte.getAtualizacaoDoContribuinte().getCelular())) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }


    private boolean isHasEmail(Contribuinte contribuinte) {
        boolean retorno = false;

        if (StringUtils.isNotBlank(contribuinte.getDadosCadastradosDoContribuinte().getEmail())) {
            retorno = true;
        }

        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            if (StringUtils.isNotBlank(contribuinte.getAtualizacaoDoContribuinte().getEmail())) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }


    private void sendForEmail() {
        this.mail = new Mail();
        this.mail.prepararEmail(this.imovel, 0, getImei());

        Intent intentSend = new Intent(Intent.ACTION_SEND);
        intentSend.putExtra(Intent.EXTRA_EMAIL, mail.getEmails());
        intentSend.putExtra(Intent.EXTRA_SUBJECT, mail.getTituloDoEmail());
        intentSend.putExtra(Intent.EXTRA_TEXT, mail.getMensagem());
        intentSend.setType("message/rfc822");
        startActivity(Intent.createChooser(intentSend, "Escolha um app para enviar o email!"));

        this.mail.rejetar();
        addImagemNosStatusDoImovel();

    }

    private String getImei() {
        final TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        final String imei = manager.getDeviceId();
        return imei;
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
