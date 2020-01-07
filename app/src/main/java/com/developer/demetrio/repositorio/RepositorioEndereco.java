package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Endereco;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Endereco;

public class RepositorioEndereco implements IRepositorioEndereco {

    private SQLiteDatabase conexao;

    public RepositorioEndereco(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(Endereco endereco) throws RepositorioException {

        ContentValues values = new ContentValues();
        values.put(_Endereco.CIDADE, endereco.getCidade());
        values.put(_Endereco.UF, endereco.getUf());
        values.put(_Endereco.BAIRRO, endereco.getBairro());
        values.put(_Endereco.LOGRADOURO, endereco.getLogradouro());
        values.put(_Endereco.COMPLEMENTO, endereco.getComplemento());
        values.put(_Endereco.NUMERO, endereco.getNumero());
        values.put(_Endereco.CEP, endereco.getCep());
        return this.conexao.insertOrThrow(_Endereco.NOME_DA_TABELA, null, values);
    }

    @Override
    public Endereco buscar(long id) throws RepositorioException {

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        StringBuilder sql = query();
        Cursor resultado  = this.conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Endereco endereco = getEndereco(resultado);
            return endereco;
        }
        return null;
    }

    private Endereco getEndereco(Cursor resultado) {
        Endereco endereco = new Endereco();
        endereco.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Endereco.ID)));
        endereco.setCidade(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.CIDADE)));
        endereco.setUf(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.UF)));
        endereco.setBairro(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.BAIRRO)));
        endereco.setLogradouro(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.LOGRADOURO)));
        endereco.setComplemento(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.COMPLEMENTO)));
        endereco.setNumero(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.NUMERO)));
        endereco.setCep(resultado.getString(resultado.getColumnIndexOrThrow(_Endereco.CEP)));
        return endereco;
    }

    private StringBuilder query() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Endereco.ID);
        sql.append(", ");
        sql.append(_Endereco.CIDADE);
        sql.append(", ");
        sql.append(_Endereco.UF);
        sql.append(", ");
        sql.append(_Endereco.BAIRRO);
        sql.append(", ");
        sql.append(_Endereco.LOGRADOURO);
        sql.append(", ");
        sql.append(_Endereco.COMPLEMENTO);
        sql.append(", ");
        sql.append(_Endereco.NUMERO);
        sql.append(", ");
        sql.append(_Endereco.CEP);
        sql.append(" FROM ");
        sql.append(_Endereco.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Endereco.ID);
        sql.append(" = ? ");
        return sql;
    }

    @Override
    public void excluir(long id) throws RepositorioException {

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_Endereco.NOME_DA_TABELA, _Endereco.ID, parametros);

    }
}
