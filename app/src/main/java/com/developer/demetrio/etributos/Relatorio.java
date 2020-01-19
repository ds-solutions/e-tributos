package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developer.demetrio.adapters.RelatorioAdapter;
import com.developer.demetrio.adapters.utils.ItemRelatorio;
import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.repositorio.RepositorioImovel;

import java.util.ArrayList;
import java.util.List;


public class Relatorio extends AppCompatActivity {

    private List<ItemRelatorio> relatorios;
    private ListView listViewRelatorio;
    private ConexaoDataBase conexaoDataBase;
    private SQLiteDatabase conexao;

    private long totalDeRegistro = 0L;

    private ItemRelatorio relatorio;
    private Double percentagem = 0.0;
    private String mensagem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        this.listViewRelatorio = findViewById(R.id.id_list_view_relatorios);
        conexaoDataBase = new ConexaoDataBase();
        this.relatorios = new ArrayList<>();
        conexao = conexaoDataBase.concectarComBanco(this);

       popularRelatorios();

       final ArrayList<ItemRelatorio> itensRelatorio = (ArrayList<ItemRelatorio>) relatorios;

        ArrayAdapter adapter = new RelatorioAdapter(this, itensRelatorio);

        this.listViewRelatorio.setAdapter(adapter);
    }

    private List<ItemRelatorio> popularRelatorios() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            totalDeRegistro = imoveis.getQtdImoveis();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        this.relatorios.add(enviadosPorEmail());
        this.relatorios.add(enviadosPorWhatsApp());
        this.relatorios.add(impressos());
        this.relatorios.add(visitados());
        this.relatorios.add(aVisitar());
        this.relatorios.add(totalDeRegistroAlterado());
        this.relatorios.add(naoEntreguePorDemolicao());
        this.relatorios.add(naoEntreguePorNaoLocalizar());
        this.relatorios.add(naoEntreguePorRecusarReceber());
        this.relatorios.add(naoEntreguePorSerTerreno());
        return this.relatorios;
    }

    private ItemRelatorio naoEntreguePorSerTerreno() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalNaoEntreguesPorSerTerreno());
            this.relatorio.setTitulo("Total não entregue por ser terreno");
            this.relatorio.setImg(R.drawable.terreno); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" IPTUs não entregues por se tratar de terreno";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio naoEntreguePorRecusarReceber() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.naoEntreguesPorRecusarReceber());
            this.relatorio.setTitulo("Total recusados");
            this.relatorio.setImg(R.drawable.recusou); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" IPTUs foram recusados";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio naoEntreguePorNaoLocalizar() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeImoveisNaoLocalizados());
            this.relatorio.setTitulo("Total não localizados");
            this.relatorio.setImg(R.drawable.nao_localizado); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" imoveis sofreram alterações no cadastro de contribuínte";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio naoEntreguePorDemolicao() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeImoveisDemolidos());
            this.relatorio.setTitulo("Total não entregue por demição");
            this.relatorio.setImg(R.drawable.demolido); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" imoveis sofreram alterações no cadastro de contribuínte";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio totalDeRegistroAlterado() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeCadastroAlterados());
            this.relatorio.setTitulo("Total de atualizações");
            this.relatorio.setImg(R.drawable.cadastro_atualizado); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" imoveis sofreram alterações no cadastro de contribuínte";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio aVisitar() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);

        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalImoveisAVisitar());
            this.relatorio.setTitulo("Total de imóveis à visitar");
            this.relatorio.setImg(R.drawable.casas); // TODO: colocar casas a visitar
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" imoveis faltam ser visitados";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        return this.relatorio;
    }

    private ItemRelatorio visitados() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeImoveisVisitados());
            this.relatorio.setTitulo("Total de imóveis visitados");
            this.relatorio.setImg(R.drawable.positivo); // TODO: colocar imagem legal
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" imóveis já foram visitados";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio impressos() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalImpresso());
            this.relatorio.setImg(R.drawable.print);
            this.relatorio.setTitulo("Total de IPTU impresso");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" IPTUs foram foram impressos";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio enviadosPorWhatsApp() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalEnviadosPorWhatsApp());
            this.relatorio.setImg(R.drawable.whatsapp);
            this.relatorio.setTitulo("Total enviados por WhatsApp");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.totalDeRegistro+" IPTUs foram entregues via WhatsApp";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);

        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    private ItemRelatorio enviadosPorEmail() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setImg(R.drawable.email);
            this.relatorio.setQtd(imoveis.totalEnviadosPorEmail());
            this.relatorio.setTitulo("Total enviados por e-mails");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd()) + "/" + totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00) / totalDeRegistro;
            mensagem = "% do total de " + this.totalDeRegistro + " IPTUs foram entregues via e-mail";
            this.relatorio.setDado(String.valueOf(percentagem) + mensagem);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return this.relatorio;
    }

    }
