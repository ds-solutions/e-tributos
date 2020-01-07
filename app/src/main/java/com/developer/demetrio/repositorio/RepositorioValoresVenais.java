package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._ValoresVenais;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.ValoresVenais;

public class RepositorioValoresVenais implements IRepositorioValoresVenais {

    private SQLiteDatabase conexao;

    public RepositorioValoresVenais(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(ValoresVenais valores) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_ValoresVenais.TERRENO, valores.getTerreno());
        values.put(_ValoresVenais.EDIFICADA, valores.getEdificada());
        values.put(_ValoresVenais.EXCEDENTE, valores.getExcedente());
        values.put(_ValoresVenais.TOTAL, valores.getTotal());
        return this.conexao.insertOrThrow(_ValoresVenais.NOME_DA_TABELA, null, values);

    }

    @Override
    public ValoresVenais buscar(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_ValoresVenais.ID);
        sql.append(", ");
        sql.append(_ValoresVenais.TERRENO);
        sql.append(", ");
        sql.append(_ValoresVenais.EDIFICADA);
        sql.append(", ");
        sql.append(_ValoresVenais.EXCEDENTE);
        sql.append(", ");
        sql.append(_ValoresVenais.TOTAL);
        sql.append(" FROM ");
        sql.append(_ValoresVenais.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_ValoresVenais.ID);
        sql.append(" = ? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            ValoresVenais valores = new ValoresVenais();
            valores.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_ValoresVenais.ID)));
            valores.setTerreno(resultado.getString(resultado.getColumnIndexOrThrow(_ValoresVenais.TERRENO)));
            valores.setEdificada(resultado.getString(resultado.getColumnIndexOrThrow(_ValoresVenais.EDIFICADA)));
            valores.setExcedente(resultado.getString(resultado.getColumnIndexOrThrow(_ValoresVenais.EXCEDENTE)));
            valores.setTotal(resultado.getString(resultado.getColumnIndexOrThrow(_ValoresVenais.TOTAL)));
            return valores;
        }

        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_ValoresVenais.NOME_DA_TABELA, _ValoresVenais.ID, parametros);

    }
}
