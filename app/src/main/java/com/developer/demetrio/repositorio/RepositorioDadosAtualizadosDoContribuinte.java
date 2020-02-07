package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._AtualizacaoDoContribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;

public class RepositorioDadosAtualizadosDoContribuinte implements IRepositorioDadosAtualizadosDoContribuinte {

    private SQLiteDatabase conexao;

    public RepositorioDadosAtualizadosDoContribuinte(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(AtualizacaoDoContribuinte atualizados) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_AtualizacaoDoContribuinte.NOME, atualizados.getNome());
        values.put(_AtualizacaoDoContribuinte.CPF_CNPJ, atualizados.getCpfCnpj());
        values.put(_AtualizacaoDoContribuinte.RG, atualizados.getRg());
        values.put(_AtualizacaoDoContribuinte.ORG_EMISSOR, atualizados.getOrgaoEmissor());
        values.put(_AtualizacaoDoContribuinte.ESTADO_CIVIL, atualizados.getEstadoCivil());
        values.put(_AtualizacaoDoContribuinte.SEXO, atualizados.getSexo());
        values.put(_AtualizacaoDoContribuinte.COR, atualizados.getCor());
        values.put(_AtualizacaoDoContribuinte.NACIONALIDADE, atualizados.getNacionalidade());
        values.put(_AtualizacaoDoContribuinte.NATURALIDADE, atualizados.getNaturalidade());
        values.put(_AtualizacaoDoContribuinte.DATA_NASC, atualizados.getDataNascimento());
        values.put(_AtualizacaoDoContribuinte.TIPO_PESSOA, atualizados.getTipoPessoa());
        values.put(_AtualizacaoDoContribuinte.ESCOLARIDADE, atualizados.getEscolaridade());
        values.put(_AtualizacaoDoContribuinte.TELEFONE, atualizados.getTelefone());
        values.put(_AtualizacaoDoContribuinte.CELULAR, atualizados.getCelular());
        values.put(_AtualizacaoDoContribuinte.EMAIL, atualizados.getEmail());

        return this.conexao.insertOrThrow(_AtualizacaoDoContribuinte.NOME_DA_TABELA, null, values);
    }

    @Override
    public AtualizacaoDoContribuinte buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM ");
        sql.append(_AtualizacaoDoContribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_AtualizacaoDoContribuinte.ID);
        sql.append(" =? ");

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            AtualizacaoDoContribuinte dados = new AtualizacaoDoContribuinte();
            dados.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ID)));
            dados.setNome(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NOME)));
            dados.setCpfCnpj(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.CPF_CNPJ)));
            dados.setRg(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.RG)));
            dados.setOrgaoEmissor(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ORG_EMISSOR)));
            dados.setEstadoCivil(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ESTADO_CIVIL)));
            dados.setSexo(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.SEXO)));
            dados.setCor(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.COR)));
            dados.setNacionalidade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NACIONALIDADE)));
            dados.setNaturalidade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.NATURALIDADE)));
            dados.setDataNascimento(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.DATA_NASC)));
            dados.setTipoPessoa(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TIPO_PESSOA)));
            dados.setEscolaridade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ESCOLARIDADE)));
            dados.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TELEFONE)));
            dados.setCelular(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.CELULAR)));
            dados.setEmail(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.EMAIL)));
            return dados;
        }
        return null;
    }



    @Override
    public void atualizar(AtualizacaoDoContribuinte atualizado) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_AtualizacaoDoContribuinte.NOME, atualizado.getNome());
        values.put(_AtualizacaoDoContribuinte.CPF_CNPJ, atualizado.getCpfCnpj());
        values.put(_AtualizacaoDoContribuinte.RG, atualizado.getRg());
        values.put(_AtualizacaoDoContribuinte.ORG_EMISSOR, atualizado.getOrgaoEmissor());
        values.put(_AtualizacaoDoContribuinte.ESTADO_CIVIL, atualizado.getEstadoCivil());
        values.put(_AtualizacaoDoContribuinte.SEXO, atualizado.getSexo());
        values.put(_AtualizacaoDoContribuinte.COR, atualizado.getCor());
        values.put(_AtualizacaoDoContribuinte.NACIONALIDADE, atualizado.getNacionalidade());
        values.put(_AtualizacaoDoContribuinte.NATURALIDADE, atualizado.getNaturalidade());
        values.put(_AtualizacaoDoContribuinte.DATA_NASC, atualizado.getDataNascimento());
        values.put(_AtualizacaoDoContribuinte.TIPO_PESSOA, atualizado.getTipoPessoa());
        values.put(_AtualizacaoDoContribuinte.ESCOLARIDADE, atualizado.getEscolaridade());
        values.put(_AtualizacaoDoContribuinte.TELEFONE, atualizado.getTelefone());
        values.put(_AtualizacaoDoContribuinte.CELULAR, atualizado.getCelular());
        values.put(_AtualizacaoDoContribuinte.EMAIL, atualizado.getEmail());
        this.conexao.update(_AtualizacaoDoContribuinte.NOME_DA_TABELA, values, _AtualizacaoDoContribuinte.ID+" = "+atualizado.getId(), null);

    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_AtualizacaoDoContribuinte.NOME_DA_TABELA, _AtualizacaoDoContribuinte.ID, parametros);

    }

    @Override
    public long totalDeCadastroAlterados() throws RepositorioException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID ");
        sql.append(" FROM ");
        sql.append(_AtualizacaoDoContribuinte.NOME_DA_TABELA);

        Cursor resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            long total = resultado.getCount();
            resultado.close();
            return total;
        }
        return 0;
    }
}
