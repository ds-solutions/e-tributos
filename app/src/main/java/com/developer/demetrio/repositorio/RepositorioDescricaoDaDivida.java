package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._DescricaoDaDivida;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.DescricaoDaDivida;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDescricaoDaDivida implements IRepositorioDescricaoDaDivida {

    private SQLiteDatabase conexao;

    public RepositorioDescricaoDaDivida(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(DescricaoDaDivida descricao) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_DescricaoDaDivida.CODIGO, descricao.getCodigo());
        values.put(_DescricaoDaDivida.DESCRICAO, descricao.getDescricao());
        values.put(_DescricaoDaDivida.VALOR, descricao.getValor());
        values.put(_DescricaoDaDivida.PONTUALIDADE, descricao.getPontualidade());
        values.put(_DescricaoDaDivida.ISENCAO, descricao.getIsencao());
        values.put(_DescricaoDaDivida.ID_IPTU, descricao.getId_IPTU());

      return this.conexao.insertOrThrow(_DescricaoDaDivida.NOME_DA_TABELA, null, values);
    }

    @Override
    public DescricaoDaDivida buscar(Long id) throws RepositorioException {
        DescricaoDaDivida descricaoDaDivida = new DescricaoDaDivida();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append("");
        sql.append("   SELECT ");
        sql.append(_DescricaoDaDivida.ID);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.CODIGO);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.DESCRICAO);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.VALOR);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.PONTUALIDADE);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.ISENCAO);
        sql.append(", ");
        sql.append(_DescricaoDaDivida.ID_IPTU);
        sql.append(" FROM ");
        sql.append(_DescricaoDaDivida.NOME_DA_TABELA);
        sql.append("    WHERE ");
        sql.append(_DescricaoDaDivida.ID_IPTU);
        sql.append("    = ? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            DescricaoDaDivida descricao = new DescricaoDaDivida();
                descricao.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ID)));
                descricao.setCodigo(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.CODIGO)));
                descricao.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.DESCRICAO)));
                descricao.setValor(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.VALOR)));
                descricao.setPontualidade(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.PONTUALIDADE)));
                descricao.setIsencao(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ISENCAO)));
                descricao.setId_IPTU(resultado.getLong(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ID_IPTU)));
                return descricao;
        }
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_DescricaoDaDivida.NOME_DA_TABELA, _DescricaoDaDivida.ID, parametros);
    }

    @Override
    public List<DescricaoDaDivida> descricoesDaDividaDe(Long iptu_id) throws RepositorioException {
        List<DescricaoDaDivida> descricoes = new ArrayList<DescricaoDaDivida>();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(iptu_id);
        StringBuilder sql = new StringBuilder();
                sql.append("");
                sql.append("   SELECT ");
                sql.append(_DescricaoDaDivida.ID);
                sql.append(", ");
                sql.append(_DescricaoDaDivida.CODIGO);
                sql.append(", ");
                sql.append(_DescricaoDaDivida.DESCRICAO);
                sql.append(", ");
                sql.append(_DescricaoDaDivida.VALOR);
                sql.append(", ");
                sql.append(_DescricaoDaDivida.PONTUALIDADE);
                sql.append(", ");
                sql.append(_DescricaoDaDivida.ISENCAO);
                sql.append(", ");
                 sql.append(_DescricaoDaDivida.ID_IPTU);
                sql.append(" FROM ");
                sql.append(_DescricaoDaDivida.NOME_DA_TABELA);
                sql.append("    WHERE ");
                sql.append(_DescricaoDaDivida.ID_IPTU);
                sql.append("    = ? ");
                sql.append("    ORDER BY ");
                sql.append(_DescricaoDaDivida.CODIGO);
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            do {
                DescricaoDaDivida descricao = new DescricaoDaDivida();
                descricao.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ID)));
                descricao.setCodigo(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.CODIGO)));
                descricao.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.DESCRICAO)));
                descricao.setValor(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.VALOR)));
                descricao.setPontualidade(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.PONTUALIDADE)));
                descricao.setIsencao(resultado.getString(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ISENCAO)));
                descricao.setId_IPTU(resultado.getLong(resultado.getColumnIndexOrThrow(_DescricaoDaDivida.ID_IPTU)));
                descricoes.add(descricao);
            }while (resultado.moveToNext());

        }
    return descricoes;
    }
}
