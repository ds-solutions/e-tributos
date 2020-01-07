package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._CodigoDeCobranca;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.CodigoDeCobranca;

public class RepositorioCodigoDeCobranca implements IRepositorioCodigoDeCobranca {

    private SQLiteDatabase conexao;

    public RepositorioCodigoDeCobranca(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(CodigoDeCobranca codigo) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_CodigoDeCobranca.TIPO, codigo.getTipo());
        values.put(_CodigoDeCobranca.TAXA_TESTADA, codigo.getTaxaTestada());

        return this.conexao.insertOrThrow(_CodigoDeCobranca.NOME_DA_TABELA, null, values);
    }

    @Override
    public CodigoDeCobranca buscar(Long id) throws RepositorioException {
        CodigoDeCobranca codigo = new CodigoDeCobranca();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_CodigoDeCobranca.ID);
        sql.append(", ");
        sql.append(_CodigoDeCobranca.TAXA_TESTADA);
        sql.append(", ");
        sql.append(_CodigoDeCobranca.TIPO);
        sql.append(" FROM ");
        sql.append(_CodigoDeCobranca.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_CodigoDeCobranca.ID);
        sql.append(" = ?");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            codigo.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_CodigoDeCobranca.ID)));
            codigo.setTaxaTestada(resultado.getString(resultado.getColumnIndexOrThrow(_CodigoDeCobranca.TAXA_TESTADA)));
            codigo.setTipo(resultado.getString(resultado.getColumnIndexOrThrow(_CodigoDeCobranca.TIPO)));

            return codigo;
        }

        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_CodigoDeCobranca.NOME_DA_TABELA, _CodigoDeCobranca.ID, parametros);

    }
}
