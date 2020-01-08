package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._AreasDoImovel;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AreasDoImovel;

public class RepositorioAreasDoImovel implements IRepositorioAreasDoImovel {

    private SQLiteDatabase conexao;

    public RepositorioAreasDoImovel(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(AreasDoImovel areas) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_AreasDoImovel.TESTADA, areas.getTestada());
        values.put(_AreasDoImovel.AREA_DO_TERRENO, areas.getAreaDoTerreno());
        values.put(_AreasDoImovel.AREA_TOTAL_DO_TERRENO, areas.getAreaTotalDoTerreno());
        values.put(_AreasDoImovel.EDIFICADO, areas.getEdificado());
        values.put(_AreasDoImovel.AREA_TOTAL_EDIFICADO, areas.getAreaTotalEdificado());
        values.put(_AreasDoImovel.EXCEDENTE, areas.getExcedente());
        values.put(_AreasDoImovel.FRACAO, areas.getFracao());

        return this.conexao.insertOrThrow(_AreasDoImovel.NOME_DA_TABELA, null, values);
    }

    @Override
    public AreasDoImovel buscar(long id) throws RepositorioException {
        AreasDoImovel areas = new AreasDoImovel();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_AreasDoImovel.ID);
        sql.append(", ");
        sql.append(_AreasDoImovel.TESTADA);
        sql.append(", ");
        sql.append(_AreasDoImovel.AREA_DO_TERRENO);
        sql.append(", ");
        sql.append(_AreasDoImovel.AREA_TOTAL_DO_TERRENO);
        sql.append(", ");
        sql.append(_AreasDoImovel.EDIFICADO);
        sql.append(", ");
        sql.append(_AreasDoImovel.AREA_TOTAL_EDIFICADO);
        sql.append(", ");
        sql.append(_AreasDoImovel.EXCEDENTE);
        sql.append(", ");
        sql.append(_AreasDoImovel.FRACAO);
        sql.append(" FROM ");
        sql.append(_AreasDoImovel.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_AreasDoImovel.ID);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            areas.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_AreasDoImovel.ID)));
            areas.setAreaDoTerreno(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.AREA_DO_TERRENO)));
            areas.setAreaTotalDoTerreno(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.AREA_TOTAL_DO_TERRENO)));
            areas.setEdificado(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.EDIFICADO)));
            areas.setAreaTotalEdificado(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.AREA_TOTAL_EDIFICADO)));
            areas.setExcedente(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.EXCEDENTE)));
            areas.setFracao(resultado.getString(resultado.getColumnIndexOrThrow(_AreasDoImovel.FRACAO)));
            return areas;
        }
        return null;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_AreasDoImovel.NOME_DA_TABELA, _AreasDoImovel.ID, parametros);
    }
}
