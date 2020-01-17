package com.developer.demetrio.etributos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

       // ArrayAdapter<ItemRelatorio> adapter = new ArrayAdapter<ItemRelatorio>(getApplicationContext(), itensRelatorio);
    }

    private void popularRelatorios() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            totalDeRegistro = imoveis.getQtdImoveis();
        } catch (RepositorioException e) {
            e.printStackTrace();

        enviadosPorEmail();
        enviadosPorWhatsApp();
        impressos();
        visitados();
        aVisitar();
        totalDeRegistroAlterado();

    }
}

    private void totalDeRegistroAlterado() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeCadastroAlterados());
            this.relatorio.setTitulo("Total de registros alterados");
            this.relatorio.setImg(R.drawable.impressora); // TODO: colocar registro alterado
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.relatorio.getQtd()+" sofreram alteração no cadastro de contribuínte";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
    }

    private void aVisitar() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);

        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalImoveisAVisitar());
            this.relatorio.setTitulo("Total de imóveis à visitar");
            this.relatorio.setImg(R.drawable.impressora); // TODO: colocar casas a visitar
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.relatorio.getQtd()+" faltam ser visitados";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
    }

    private void visitados() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalDeImoveisVisitados());
            this.relatorio.setTitulo("Total de imóveis visitados");
            this.relatorio.setImg(R.drawable.impressora); // TODO: colocar imagem legal
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.relatorio.getQtd()+" já foram visitados";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

    }

    private void impressos() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalImpresso());
            this.relatorio.setImg(R.drawable.impressora);
            this.relatorio.setTitulo("Total de IPTU impressos");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.relatorio.getQtd()+" impressos";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

    }

    private void enviadosPorWhatsApp() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setQtd(imoveis.totalEnviadosPorWhatsApp());
            this.relatorio.setImg(R.drawable.whatsapp);
            this.relatorio.setTitulo("Total de WhatsApp enviados contendo link para downloads");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd())+"/"+totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00)/totalDeRegistro;
            mensagem = "% do total de "+ this.relatorio.getQtd()+" entregues via WhatsApp";
            this.relatorio.setDado(String.valueOf(percentagem)+mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

    }

    private void enviadosPorEmail() {
        RepositorioImovel imoveis = new RepositorioImovel(this.conexao);
        try {
            this.relatorio = new ItemRelatorio();
            this.relatorio.setImg(R.drawable.email);
            this.relatorio.setQtd(imoveis.totalEnviadosPorEmail());
            this.relatorio.setTitulo("Total de e-mails enviadas contendo link para downloads");
            this.relatorio.setComparativo(String.valueOf(this.relatorio.getQtd()) + "/" + totalDeRegistro);
            percentagem = (this.relatorio.getQtd() * 100.00) / totalDeRegistro;
            mensagem = "% do total de " + this.relatorio.getQtd() + " entregues via e-mail";
            this.relatorio.setDado(String.valueOf(percentagem) + mensagem);
            this.relatorios.add(relatorio);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

    }

    }
