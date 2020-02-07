package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._LatLng;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.LatLng;

public class RepositorioLatLng implements IRepositorioLatLng {

    private SQLiteDatabase conexao;

    public RepositorioLatLng(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(LatLng latLng) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_LatLng.LATITUDE, String.valueOf(latLng.getLatitude()));
        values.put(_LatLng.LONGITUDE, String.valueOf(latLng.getLongitude()));
        return this.conexao.insertOrThrow(_LatLng.NOME_DA_TABELA, null, values);

    }

    @Override
    public LatLng buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_LatLng.ID);
        sql.append(", ");
        sql.append(_LatLng.LATITUDE);
        sql.append(", ");
        sql.append(_LatLng.LONGITUDE);
        sql.append(" FROM ");
        sql.append(_LatLng.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_LatLng.ID);
        sql.append(" = ? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            LatLng latLng = new LatLng();
            latLng.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_LatLng.ID)));
            latLng.setLatitude(resultado.getDouble(resultado.getColumnIndexOrThrow(_LatLng.LATITUDE)));
            latLng.setLongitude(resultado.getDouble(resultado.getColumnIndexOrThrow(_LatLng.LONGITUDE)));
            resultado.close();
            return latLng;
        }
        return null;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        this.conexao.delete(_LatLng.NOME_DA_TABELA, _LatLng.ID, parametros);

    }
}
