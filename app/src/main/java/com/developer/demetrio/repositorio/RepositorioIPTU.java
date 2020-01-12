package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._IPTU;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.IPTU;

public class RepositorioIPTU implements IRepositorioIPTU {

    private SQLiteDatabase conexao;

    public RepositorioIPTU(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(IPTU iptu) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_IPTU.CODIGO_DA_DIVIDA, iptu.getCodigoDaDivida());
        values.put(_IPTU.CODIGO_DE_BAIXA, iptu.getCodigoDeBaixa());
        values.put(_IPTU.EXERCICIO, iptu.getExercicio());
        values.put(_IPTU.MENSAGEM, iptu.getMensagem());
        values.put(_IPTU.VALOR_TOTAL, iptu.getValorTotal());
        values.put(_IPTU.SOMA_DO_VALOR, iptu.getSomaDoValor());
        values.put(_IPTU.SOMA_DO_DESCONTO, iptu.getSomaDoDesconto());
        values.put(_IPTU.SOMA_DA_ISENCAO, iptu.getSomaIsencao());
        values.put(_IPTU.VENCIMENTO, iptu.getVencimento());
        values.put(_IPTU.CODIGO_DE_BARRAS, iptu.getDigitosDoCodigoDeBarras());
        values.put(_IPTU.CAMPO_1, iptu.getCampo1CodigoDeBarras());
        values.put(_IPTU.CAMPO_2, iptu.getCampo2CodigoDeBarras());
        values.put(_IPTU.CAMPO_3, iptu.getCampo3CodigoDeBarras());
        values.put(_IPTU.CAMPO_4, iptu.getCampo4CodigoDeBarras());

        return this.conexao.insertOrThrow(_IPTU.NOME_DA_TABELA, null, values);
    }

    @Override
    public IPTU buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = query();

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            IPTU iptu = getIptu(resultado);
            return iptu;
        }
        return null;
    }

    private IPTU getIptu(Cursor resultado) {
        IPTU iptu = new IPTU();
        iptu.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_IPTU.ID)));
        iptu.setCodigoDaDivida(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CODIGO_DA_DIVIDA)));
        iptu.setCodigoDeBaixa(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CODIGO_DE_BAIXA)));
        iptu.setExercicio(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.EXERCICIO)));
        iptu.setMensagem(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.MENSAGEM)));
        iptu.setValorTotal(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.VALOR_TOTAL)));
        iptu.setSomaDoValor(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.SOMA_DO_VALOR)));
        iptu.setSomaDoDesconto(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.SOMA_DO_DESCONTO)));
        iptu.setSomaIsencao(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.SOMA_DA_ISENCAO)));
        iptu.setVencimento(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.VENCIMENTO)));
        iptu.setDigitosDoCodigoDeBarras(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CODIGO_DE_BARRAS)));
        iptu.setCampo1CodigoDeBarras(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CAMPO_1)));
        iptu.setCampo2CodigoDeBarras(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CAMPO_2)));
        iptu.setCampo3CodigoDeBarras(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CAMPO_3)));
        iptu.setCampo4CodigoDeBarras(resultado.getString(resultado.getColumnIndexOrThrow(_IPTU.CAMPO_4)));
        return iptu;
    }

    private StringBuilder query() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        /*sql.append(_IPTU.ID);
        sql.append(", ");
        sql.append(_IPTU.CODIGO_DA_DIVIDA);
        sql.append(", ");
        sql.append(_IPTU.CODIGO_DE_BAIXA);
        sql.append(", ");
        sql.append(_IPTU.MENSAGEM);
        sql.append(", ");
        sql.append(_IPTU.VALOR_TOTAL);
        sql.append(", ");
        sql.append(_IPTU.SOMA_DO_VALOR);
        sql.append(", ");
        sql.append(_IPTU.SOMA_DO_DESCONTO);
        sql.append(", ");
        sql.append(_IPTU.SOMA_DA_ISENCAO);
        sql.append(", ");
        sql.append(_IPTU.CODIGO_DE_BARRAS);
        sql.append(", ");
        sql.append(_IPTU.CAMPO_1);
        sql.append(", ");
        sql.append(_IPTU.CAMPO_2);
        sql.append(", ");
        sql.append(_IPTU.CAMPO_3);
        sql.append(", ");
        sql.append(_IPTU.CAMPO_4); */
        sql.append(" FROM ");
        sql.append(_IPTU.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_IPTU.ID);
        sql.append(" =? ");
        return sql;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_IPTU.NOME_DA_TABELA, _IPTU.ID, parametros);
    }
}
