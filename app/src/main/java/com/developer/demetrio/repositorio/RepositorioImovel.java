package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Imovel;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.DescricaoDaDivida;
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

import java.util.ArrayList;
import java.util.List;

public class RepositorioImovel implements IRepositorioImovel {

    private static RepositorioImovel instancia;
    private Imovel objeto, imovel;
    private Long id = 0L;
    private Integer lote = 10;
    private Integer preCpf = 10;
    private Integer midleCpf = 750;
    private Integer lastCpf = 114;
    private Integer digitCpf = 77;
    private List<Imovel> imoveis;

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

    public long atualizarIndicadorEmissao(long id, int indicador) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_EMISSAO_CONTA, indicador);
        return this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID, parametros);
    }

    public long atualizarIndicadorEnvioEmail(long id, int indicador) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_ENVIO_EMAIL, indicador);
        return this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID, parametros);
   }

    public long atualizarIndicadorEnvioWhatsAap(long id, int indicador) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        ContentValues values = new ContentValues();

        values.put(_Imovel.INDIC_ENVIO_WHATSAAP, indicador);
        return this.conexao.update(_Imovel.NOME_DA_TABELA, values, _Imovel.ID, parametros);
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
        sql.append(" SELECT ");
        sql.append(_Imovel.ID);
        sql.append(", ");
        sql.append(_Imovel.INDIC_EMISSAO_CONTA);
        sql.append(", ");
        sql.append(_Imovel.INDIC_ENVIO_EMAIL);
        sql.append(", ");
        sql.append(_Imovel.INDIC_ENVIO_WHATSAAP);
        sql.append(", ");
        sql.append(_Imovel.ID_CADASTRO);
        sql.append(", ");
        sql.append(_Imovel.ID_CONTRIBUINTE);
        sql.append(", ");
        sql.append(_Imovel.ID_ENDERECO);
        sql.append(", ");
        sql.append(_Imovel.ID_TRIBUTO);
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
             System.out.println("ID DO TRIBUTO DENTRO DO REPOSITORIO DE IMOVEL "+resultado.getLong(resultado.getColumnIndexOrThrow(_Imovel.ID_TRIBUTO)));
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
        sql.append(" SELECT * FROM ");
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


    public List<Imovel> getImoveis() {
        System.out.println("getImoveis com "+ this.imoveis.size());
        return this.imoveis;
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

}
