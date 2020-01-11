package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Imovel;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.Tributo;

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
    public long inserir(Imovel imovel) throws RepositorioException {
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_EMISSAO_CONTA, imovel.getIndcEmissaoConta());
        values.put(_Imovel.INDIC_ENVIO_EMAIL, imovel.getIndcEnvioEmail());
        values.put(_Imovel.INDIC_ENVIO_WHATSAAP, imovel.getIndcEnvioZap());
        values.put(_Imovel.ID_CADASTRO, imovel.getCadastro().getId());
        values.put(_Imovel.ID_CONTRIBUINTE, imovel.getContribuinte().getId());
        values.put(_Imovel.ID_ENDERECO, imovel.getEndereco().getId());
        values.put(_Imovel.ID_TRIBUTO, imovel.getTributo().getId());
        values.put(_Imovel.ID_LATLNG, imovel.getLatLng().getId());

       return this.conexao.insertOrThrow(_Imovel.NOME_DA_TABELA, null, values);
    }

    public long atualizarIndicadorDeEnvio(long id, Imovel i) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_EMISSAO_CONTA, i.getIndcEmissaoConta());
        values.put(_Imovel.INDIC_ENVIO_WHATSAAP, i.getIndcEnvioZap());
        values.put(_Imovel.INDIC_ENVIO_EMAIL, i.getIndcEnvioEmail());
        return this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID+"="+id, null);
    }

    @Override
    public void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws RepositorioException {

    }


    @Override
    public ArrayList<Integer> buscarIdsImoveisCadastroNaoEnviados() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContaPorQuadra(String setor, String quadra) throws RepositorioException {
        return null;
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
             imovel.setCadastro(new Cadastro());
             imovel.getCadastro().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CADASTRO)));
             imovel.setContribuinte(new Contribuinte());
             imovel.getContribuinte().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_CONTRIBUINTE)));
             imovel.setEndereco(new Endereco());
             imovel.getEndereco().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_ENDERECO)));
             imovel.setTributo(new Tributo());
             imovel.getTributo().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_TRIBUTO)));
             return imovel;
        }
        return null;
    }



    @Override
    public ArrayList<Imovel> buscarImovelContas() throws RepositorioException {

        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContasLidos() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarQuadras() throws RepositorioException {
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
            return resultado.getCount();
        }
        return 0;
    }

    @Override
    public void inverterRoteiroImoveis() throws RepositorioException {

    }

    @Override
    public Integer obterQuantidadeDeRegistro() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterPosicaoImovel(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisPorQuadra(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws RepositorioException {
        return null;
    }

    @Override
    public List<Imovel> getImoveis() {
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
            return resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID));
        }
        return 0;
    }

    @Override
    public boolean rotaFinalizada()  {
        String[] parametros = new String[3];
        parametros[0] = String.valueOf(1);
        parametros[1] = String.valueOf(1);
        parametros[2] = String.valueOf(1);
        Integer cont = 0;
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
        sql.append(" =? ");

        try {
            cont = getQtdImoveis();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0
                && resultado.getCount() == cont) {
            return true;
        }
        return false;
    }

}
