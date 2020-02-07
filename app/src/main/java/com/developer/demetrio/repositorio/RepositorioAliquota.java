package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Aliquota;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.CodigoDeCobranca;

import java.util.AbstractList;

public class RepositorioAliquota implements IRepositorioAliquota {
    private SQLiteDatabase conexao;

    public RepositorioAliquota(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(Aliquota aliquota) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Aliquota.TERRENO, aliquota.getTerreno());
        values.put(_Aliquota.EDIFICADO, aliquota.getEdificado());
        values.put(_Aliquota.ZONEAMENTO, aliquota.getZoneamento());
        values.put(_Aliquota.TIPO_CONSTRUCAO, aliquota.getTipoConstrucao());
        values.put(_Aliquota.ID_CODIGO_DE_COBRANCA, aliquota.getCodigoDeCobranca().getId());

       return this.conexao.insertOrThrow(_Aliquota.NOME_DA_TABELA, null, values);
    }

    @Override
    public Aliquota buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT * ");
        sql.append(" FROM ");
        sql.append(_Aliquota.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Aliquota.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Aliquota aliquota = new Aliquota();
            aliquota.setCodigoDeCobranca(new CodigoDeCobranca());
            aliquota.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Aliquota.ID)));
            aliquota.setTerreno(resultado.getString(resultado.getColumnIndexOrThrow(_Aliquota.TERRENO)));
            aliquota.setEdificado(resultado.getString(resultado.getColumnIndexOrThrow(_Aliquota.EDIFICADO)));
            aliquota.setZoneamento(resultado.getString(resultado.getColumnIndexOrThrow(_Aliquota.ZONEAMENTO)));
            aliquota.setTipoConstrucao(resultado.getString(resultado.getColumnIndexOrThrow(_Aliquota.TIPO_CONSTRUCAO)));
            aliquota.getCodigoDeCobranca().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Aliquota.ID_CODIGO_DE_COBRANCA)));
            resultado.close();
            return aliquota;
        }
        return null;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
    String[] parametros = new String[1];
    parametros[0] = String.valueOf(id);
    this.conexao.delete(_Aliquota.NOME_DA_TABELA, _Aliquota.ID+" = ? ", parametros);
    }
}
