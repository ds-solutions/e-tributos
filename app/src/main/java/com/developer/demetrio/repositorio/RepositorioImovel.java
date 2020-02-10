package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._AtualizacaoDoContribuinte;
import com.developer.demetrio.databases.constantes._Cadastro;
import com.developer.demetrio.databases.constantes._Contribuinte;
import com.developer.demetrio.databases.constantes._DadosCadastradosDoContribuinte;
import com.developer.demetrio.databases.constantes._Endereco;
import com.developer.demetrio.databases.constantes._Imovel;
import com.developer.demetrio.execoes.RepositorioException;
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
import com.developer.demetrio.model.utils.QuadrasNaoVisitadas;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RepositorioImovel implements IRepositorioImovel {

    private Imovel objeto;
    private static RepositorioImovel instancia;
    private SQLiteDatabase conexao;
    public void resetarInstancia() {
        instancia = null;
    }


    public static RepositorioImovel getInstance() {
        if (instancia == null) {
            instancia = new RepositorioImovel();
            instancia.objeto = new Imovel();
        }
        return instancia;
    }

    public RepositorioImovel() {

    }

    public RepositorioImovel(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public List<Imovel> buscarTodosEnviadosPorEmail() throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = "1";
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarTodosEnviadosPorWhatsApp() throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = "1";
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarTodosImoveisImpressos() throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = "1";
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarTodosImoveisNaoImpressos() throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = "0";
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarTodosImoveisPorInscricao(String[] parametros) throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.ID);
        sql.append(" = ");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(" WHERE ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.INSCRICAO);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarTodosPorLogradouro(String[] parametros) throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Endereco.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Endereco.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.ID);
        sql.append(" = ");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(" WHERE ");
        sql.append(_Endereco.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Endereco.LOGRADOURO);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarImoveisNaoEntregues() throws RepositorioException {
        String[] parametros = new String[3];
        parametros[0] = "0";
        parametros[1] = "0";
        parametros[2] = "0";
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public List<Imovel> buscarPorSetorQuadra(String setor, String quadra) throws RepositorioException {
       String[] parametros = null;
       if (StringUtils.isNotBlank(setor) && StringUtils.isNotBlank(quadra)) {
            parametros = new String[2];
            parametros = new String[]{setor, quadra};
        }
        if ((StringUtils.isNotBlank(setor)) && (StringUtils.isBlank(quadra))) {
            parametros = new String[1];
            parametros = new String[]{setor};
        }
        if ((StringUtils.isBlank(setor)) && (StringUtils.isNotBlank(quadra))) {
            parametros = new String[1];
            parametros = new String[]{quadra};
        }
        if ((StringUtils.isBlank(setor)) && (StringUtils.isBlank(quadra))) {
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.ID);
        sql.append(" = ");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(" WHERE ");
        if (StringUtils.isNotBlank(setor)) {
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
            sql.append(_Cadastro.SETOR);
            sql.append(" =? ");
        }

        if ((StringUtils.isNotBlank(setor)) && (StringUtils.isNotBlank(quadra))) {
            sql.append(" AND ");
        }

        if (StringUtils.isNotBlank(quadra)) {
            sql.append(_Cadastro.NOME_DA_TABELA);
            sql.append(".");
            sql.append(_Cadastro.QUADRA);
            sql.append(" =? ");
        }

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public long inserir(Imovel imovel) throws RepositorioException {
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_EMISSAO_CONTA, imovel.getIndcEmissaoConta());
        values.put(_Imovel.INDIC_ENVIO_EMAIL, imovel.getIndcEnvioEmail());
        values.put(_Imovel.INDIC_ENVIO_WHATSAAP, imovel.getIndcEnvioZap());
        values.put(_Imovel.MOTIVO_NAO_ENTREGA, imovel.getMotivoDaNaoEntrega());
        values.put(_Imovel.ID_CADASTRO, imovel.getCadastro().getId());
        values.put(_Imovel.ID_CONTRIBUINTE, imovel.getContribuinte().getId());
        values.put(_Imovel.ID_ENDERECO, imovel.getEndereco().getId());
        values.put(_Imovel.ID_TRIBUTO, imovel.getTributo().getId());

       return this.conexao.insertOrThrow(_Imovel.NOME_DA_TABELA, null, values);
    }

    public long atualizarIndicadorDeEnvio(long id, Imovel i) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_EMISSAO_CONTA, i.getIndcEmissaoConta());
        values.put(_Imovel.INDIC_ENVIO_WHATSAAP, i.getIndcEnvioZap());
        values.put(_Imovel.INDIC_ENVIO_EMAIL, i.getIndcEnvioEmail());
        values.put(_Imovel.MOTIVO_NAO_ENTREGA, i.getMotivoDaNaoEntrega());
        return this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID+"="+id, null);
    }

    @Override
    public Imovel buscarImovelPorId(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.ID);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Imovel imovel = new Imovel();
             imovel.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID)));
             imovel.setIndcEmissaoConta(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_EMISSAO_CONTA)));
             imovel.setIndcEnvioEmail(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_ENVIO_EMAIL)));
             imovel.setIndcEnvioZap(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_ENVIO_WHATSAAP)));
             imovel.setMotivoDaNaoEntrega(resultado.getString(resultado.getColumnIndexOrThrow(_Imovel.MOTIVO_NAO_ENTREGA)));
             imovel.setCadastro(new Cadastro());
             imovel.getCadastro().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CADASTRO)));
             imovel.setContribuinte(new Contribuinte());
             imovel.getContribuinte().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CONTRIBUINTE)));
             imovel.setEndereco(new Endereco());
             imovel.getEndereco().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_ENDERECO)));
             imovel.setTributo(new Tributo());
             imovel.getTributo().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_TRIBUTO)));
             imovel.getComprovante().setId(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.ID_COMPROVANTE)));
             resultado.close();
             return imovel;
        }
        return null;
    }

    @Override
    public Integer getQtdImoveis() throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" ");
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
       Cursor resultado =  this.conexao.rawQuery(sql.toString(), null);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Integer qtd = resultado.getCount();
            resultado.close();
            return qtd;
        }
        return 0;
    }



    @Override
    public List<Imovel> getImoveis()throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);

        Cursor resultado = this.conexao.rawQuery(sql.toString(), null);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }


    @Override
    public long primeiraPosicaoNaoEmitida() throws RepositorioException {
        String[] parametros = new String[3];
        parametros[0] = String.valueOf(0);
        parametros[1] = String.valueOf(0);
        parametros[2] = String.valueOf(0);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =?");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            long id = resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID));
            resultado.close();
            return id;
        }
        return 0;
    }

    @Override
    public boolean rotaFinalizada(long totalRegistro)throws RepositorioException  {
        String[] parametros = new String[4];
        parametros[0] = String.valueOf(1);
        parametros[1] = String.valueOf(1);
        parametros[2] = String.valueOf(1);
        parametros[3] = "Motivo da não entrega";
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? OR ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" !=? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0
                && resultado.getCount() == totalRegistro) {
            resultado.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Imovel> buscarDaMatricula(String[] parametros) throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.ID);
        sql.append(" = ");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(" WHERE ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.NUM_CADASTRO);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            List<Imovel> imovels = getResultado(resultado);
            resultado.close();
            return imovels;
        }
        return null;
    }

    @Override
    public long primeiraPosicao() throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);

        Cursor resultado = this.conexao.rawQuery(sql.toString(), null);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            long id = resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID));
            resultado.close();
            return id;
        }
        return 0;
    }

    @Override
    public long totalDeImoveisVisitados() throws RepositorioException {
        String[] parametros = new String[]{"1", "1", "1"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalEnviadosPorEmail() throws RepositorioException {
        String[] parametros = new String[]{"1"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            return total;
        }
        return 0;
    }

    @Override
    public long totalEnviadosPorWhatsApp() throws RepositorioException {
        String[] parametros = new String[]{"1"};
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalImpresso() throws RepositorioException {
        String[] parametros = new String[]{"1"};
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalImoveisAVisitar() throws RepositorioException {
        String[] parametros = new String[]{"0", "0", "0", "Motivo da não entrega"};
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? AND ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalDeCadastroAlterados() throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT COUNT(");
        sql.append(_Imovel.ID_CONTRIBUINTE);
        sql.append(")");
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Contribuinte.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Contribuinte.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Contribuinte.ID);
        sql.append(" = ");
        sql.append(_Imovel.ID_CONTRIBUINTE);
        sql.append(" WHERE ");
        sql.append(_Contribuinte.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE);
        Cursor resultado = this.conexao.rawQuery(sql.toString() + " != "+null, null);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalDeImoveisDemolidos() throws RepositorioException {
        String[] parametros = new String[]{"Demolido"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalDeImoveisNaoLocalizados() throws RepositorioException {
        String[] parametros = new String[]{"Imóvel não localizado"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalNaoEntreguesPorSerTerreno() throws RepositorioException {
        String[] parametros = new String[]{"Terreno"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long naoEntreguesPorRecusarReceber() throws RepositorioException {
        String[] parametros = new String[]{"Recusou receber"};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public long totalDeTributosNaoEntregues() throws RepositorioException {
        String[] parametros = new String[]{"Motivo da não entrega"};
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(_Imovel.ID);
        sql.append(" FROM ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
      /*  sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? OR ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? AND ");  */
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" != ?");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }

    @Override
    public void atualizarMotivoDaNaoEntrega(Imovel imovel) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(imovel.getId());
        ContentValues values = new ContentValues();

        values.put(_Imovel.MOTIVO_NAO_ENTREGA, imovel.getMotivoDaNaoEntrega());
        this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID+"="+imovel.getId(), null);

    }

    @Override
    public List<QuadrasNaoVisitadas> setoresEQuadrasNaoEntregues() throws RepositorioException {
       String[] parametros = new String[]{"0", "0", "0", "", "Motivo da não entrega"};
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(");
        sql.append(_Cadastro.QUADRA);
        sql.append("), ");
        sql.append(_Cadastro.SETOR);
        sql.append(", ");
        sql.append(_Cadastro.QUADRA);
        sql.append(" FROM ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" INNER JOIN ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(" ON ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Cadastro.ID);
        sql.append(" = ");
        sql.append(_Imovel.NOME_DA_TABELA);
        sql.append(".");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(" WHERE ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(" =? AND ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(" =? AND ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? OR ");
        sql.append(_Imovel.MOTIVO_NAO_ENTREGA);
        sql.append(" =? ");
        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            List<QuadrasNaoVisitadas> naoVisitados = resultadoDaConsulta(resultado);
            resultado.close();
            return naoVisitados;
        }

        return null;
    }

    @Override
    public void atualizarComprovante(Imovel imovel) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Imovel.ID_COMPROVANTE, imovel.getComprovante().getId());
        this.conexao.update(_Imovel.NOME_DA_TABELA, values,_Imovel.ID + " = " + imovel.getId(), null);
    }

    private List<QuadrasNaoVisitadas> resultadoDaConsulta(Cursor resultado) {
        List<QuadrasNaoVisitadas> respostas = new ArrayList<>();
        do{
            QuadrasNaoVisitadas naoEntregue = new QuadrasNaoVisitadas();
            naoEntregue.setTotalQuadra(resultado.getLong(resultado.getCount()));
            naoEntregue.setSetor(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.SETOR)));
            naoEntregue.setQuadra(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.QUADRA)));
            respostas.add(naoEntregue);
        }while (resultado.moveToNext());
        resultado.close();
       return respostas;
    }

    public List<Imovel> getResultado(Cursor resultado) {
        List<Imovel> imoveis = new ArrayList<>();

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            do {
                Imovel imovel = new Imovel();
                imovel.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID)));
                imovel.setIndcEmissaoConta(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_EMISSAO_CONTA)));
                imovel.setIndcEnvioEmail(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_ENVIO_EMAIL)));
                imovel.setIndcEnvioZap(resultado.getInt(resultado.getColumnIndexOrThrow(_Imovel.INDIC_ENVIO_WHATSAAP)));
                imovel.setMotivoDaNaoEntrega(resultado.getString(resultado.getColumnIndexOrThrow(_Imovel.MOTIVO_NAO_ENTREGA)));

                imovel.setCadastro(getCadastro(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CADASTRO))));

                imovel.setContribuinte(getContribuinte(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CONTRIBUINTE))));

                imovel.setEndereco(getEndereco(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_ENDERECO))));

                imovel.setTributo(new Tributo());
                imovel.getTributo().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_TRIBUTO)));
                imoveis.add(imovel);
            }while (resultado.moveToNext());
                resultado.close();
            return imoveis;
        }
        return null;
    }

    public Endereco getEndereco(long id) {
        Endereco endereco = new Endereco();
        String[] parametros = new String[]{String.valueOf(id)};
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT * FROM ");
        sql.append(_Endereco.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Endereco.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            endereco.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Endereco.ID)));
            endereco.setCidade(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.CIDADE)));
            endereco.setUf(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.UF)));
            endereco.setBairro(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.BAIRRO)));
            endereco.setLogradouro(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.LOGRADOURO)));
            endereco.setComplemento(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.COMPLEMENTO)));
            endereco.setNumero(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.NUMERO)));
            endereco.setCep(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.CEP)));
            resultado.close();
            return endereco;
        }
        return null;
    }

    public Contribuinte getContribuinte(long id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("*");
        sql.append(" FROM ");
        sql.append(_Contribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Contribuinte.ID);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Contribuinte contribuinte = new Contribuinte();

            contribuinte.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID)));

            contribuinte.setAtualizacaoDoContribuinte(getContribuinteAtualizado(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE))));

            contribuinte.setDadosCadastradosDoContribuinte(getDadosDoContribuinte(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID_DADOS_DO_CONTRIBUINTE))));
            resultado.close();
            return contribuinte;
        }

        return null;
    }

    public AtualizacaoDoContribuinte getContribuinteAtualizado(long id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM ");
        sql.append(_AtualizacaoDoContribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_AtualizacaoDoContribuinte.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            AtualizacaoDoContribuinte dados = new AtualizacaoDoContribuinte();
            dados.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ID)));
            dados.setNome(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NOME)));
            dados.setCpfCnpj(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.CPF_CNPJ)));
            dados.setRg(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.RG)));
            dados.setOrgaoEmissor(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ORG_EMISSOR)));
            dados.setEstadoCivil(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ESTADO_CIVIL)));
            dados.setSexo(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.SEXO)));
            dados.setCor(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.COR)));
            dados.setNacionalidade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NACIONALIDADE)));
            dados.setNaturalidade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NATURALIDADE)));
            dados.setDataNascimento(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.DATA_NASC)));
            dados.setTipoPessoa(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TIPO_PESSOA)));
            dados.setEscolaridade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ESCOLARIDADE)));
            dados.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TELEFONE)));
            dados.setCelular(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.CELULAR)));
            dados.setEmail(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.EMAIL)));
            resultado.close();
            return dados;
        }
        return  null;
    }

    public DadosCadastradosDoContribuinte getDadosDoContribuinte(long id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM ");
        sql.append(_DadosCadastradosDoContribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_DadosCadastradosDoContribuinte.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            DadosCadastradosDoContribuinte dados = new DadosCadastradosDoContribuinte();
            dados.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ID)));
            dados.setNome(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NOME)));
            dados.setCpf(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.CPF)));
            dados.setRg(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.RG)));
            dados.setOrgEmissor(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ORG_EMISSOR)));
            dados.setDataNasc(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.DATA_NASC)));
            dados.setEstadoCivil(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ESTADO_CIVIL)));
            dados.setNacionalidade(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NACIONALIDADE)));
            dados.setNaturalidade(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NATURALIDADE)));
            dados.setRaca(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.COR)));
            dados.setSexo(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.SEXO)));
            dados.setEmail(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.EMAIL)));
            dados.setNumeroCelular(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.CELULAR)));
            resultado.close();
            return dados;
        }
        return null;
    }

    public Cadastro getCadastro(long id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Cadastro.ID);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Cadastro cadastro = new Cadastro();
            cadastro.setAliquota(new Aliquota());
            cadastro.setAreasDoImovel(new AreasDoImovel());
            cadastro.setValoresVenais(new ValoresVenais());
            cadastro.getAliquota().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_ALIQUOTA)));
            cadastro.getAreasDoImovel().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_AREAS_DO_IMOVEL)));
            cadastro.getValoresVenais().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_VALORES_VENAIS)));
            cadastro.setInscricao(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.INSCRICAO)));
            cadastro.setNumCadastro(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.NUM_CADASTRO)));
            cadastro.setDistrito(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.DISTRITO)));
            cadastro.setSetor(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.SETOR)));
            cadastro.setQuadra(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.QUADRA)));
            cadastro.setLote(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.LOTE)));
            cadastro.setUnidade(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.UNIDADE)));
            resultado.close();
            return cadastro;
         }

        return null;
    }

}
