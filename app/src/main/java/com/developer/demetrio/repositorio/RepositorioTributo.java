package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Tributo;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Tributo;

public class RepositorioTributo implements IRepositorioTributo {

    private SQLiteDatabase conexao;

    public RepositorioTributo(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(Tributo tributo) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Tributo.ID_IPTU, tributo.getIptu().getId());

        return this.conexao.insertOrThrow(_Tributo.NOME_DA_TABELA, null, values);
    }

    @Override
    public Tributo buscar(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Tributo.ID);
        sql.append(", ");
        sql.append(_Tributo.ID_IPTU);
        sql.append(" FROM ");
        sql.append(_Tributo.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Tributo.ID);
        sql.append(" = ? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            Tributo tributo = new Tributo();
            tributo.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Tributo.ID)));
            tributo.getIptu().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Tributo.ID_IPTU)));
            return tributo;
        }

        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_Tributo.NOME_DA_TABELA, _Tributo.ID, parametros);
    }
}
