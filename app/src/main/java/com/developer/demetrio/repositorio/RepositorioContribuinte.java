package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Contribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;

public class RepositorioContribuinte implements IRepositorioContribuinte {

    private SQLiteDatabase conexao;

    public RepositorioContribuinte(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(Contribuinte contribuinte) throws RepositorioException {
        ContentValues values = new ContentValues();
       values.put(_Contribuinte.ID_DADOS_DO_CONTRIBUINTE, contribuinte.getDadosCadastradosDoContribuinte().getId());
        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            values.put(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE, contribuinte.getAtualizacaoDoContribuinte().getId());
        }
        return this.conexao.insertOrThrow(_Contribuinte.NOME_DA_TABELA, null, values);
    }

    @Override
    public Contribuinte buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("*");
      /*  sql.append(_Contribuinte.ID);
        sql.append(", ");
        sql.append(_Contribuinte.ID_DADOS_DO_CONTRIBUINTE);
        sql.append(", ");
        sql.append(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE);*/
        sql.append(" FROM ");
        sql.append(_Contribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Contribuinte.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Contribuinte contribuinte = new Contribuinte();
            contribuinte.setAtualizacaoDoContribuinte(new AtualizacaoDoContribuinte());
            contribuinte.setDadosCadastradosDoContribuinte(new DadosCadastradosDoContribuinte());
            contribuinte.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID)));
            contribuinte.getAtualizacaoDoContribuinte().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE)));
            contribuinte.getDadosCadastradosDoContribuinte().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE)));
            return contribuinte;
        }

        return null;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(
                _Contribuinte.NOME_DA_TABELA, _Contribuinte.ID, parametros
        );
    }

    @Override
    public void atualizar(Contribuinte contribuinte) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(contribuinte.getId());
        ContentValues values = new ContentValues();
       if (contribuinte.getAtualizacaoDoContribuinte() != null) {
           System.out.println("o id que veio "+ contribuinte.getAtualizacaoDoContribuinte().getId());
           values.put(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE,
                   contribuinte.getAtualizacaoDoContribuinte().getId());
           this.conexao.update(_Contribuinte.NOME_DA_TABELA, values, _Contribuinte.ID+"="+ contribuinte.getId(), null);
       }
    }
}
