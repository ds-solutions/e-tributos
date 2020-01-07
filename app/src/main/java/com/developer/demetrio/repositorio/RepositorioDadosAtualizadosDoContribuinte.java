package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._AtualizacaoDoContribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(_AtualizacaoDoContribuinte.DATA_NASC, dateFormat.format(atualizados.getDataNascimento()));
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
        StringBuilder sql = query();

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            AtualizacaoDoContribuinte dados = dadosColetados(resultado);
            return dados;
        }
        return null;
    }

    private StringBuilder query() {
         StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_AtualizacaoDoContribuinte.ID);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.NOME);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.CPF_CNPJ);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.RG);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.ORG_EMISSOR);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.ESTADO_CIVIL);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.SEXO);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.COR);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.NACIONALIDADE);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.NATURALIDADE);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.DATA_NASC);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.TIPO_PESSOA);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.ESCOLARIDADE);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.TELEFONE);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.CELULAR);
        sql.append(", ");
        sql.append(_AtualizacaoDoContribuinte.EMAIL);
        sql.append(" FROM ");
        sql.append(_AtualizacaoDoContribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_AtualizacaoDoContribuinte.ID);
        sql.append(" = ? ");
        return sql;

    }

    private AtualizacaoDoContribuinte dadosColetados(Cursor resultado) {
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
        dados.setDataNascimento(Date.valueOf(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.DATA_NASC))));
        dados.setTipoPessoa(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TIPO_PESSOA)));
       dados.setEscolaridade(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.ESCOLARIDADE)));
       dados.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.TELEFONE)));
        dados.setCelular(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.CELULAR)));
        dados.setEmail(resultado.getString(resultado.getColumnIndexOrThrow(_AtualizacaoDoContribuinte.EMAIL)));
        return dados;
    }

    @Override
    public long atualizar(AtualizacaoDoContribuinte atualizados) throws RepositorioException {

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(atualizados.getId());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(_AtualizacaoDoContribuinte.DATA_NASC, dateFormat.format(atualizados.getDataNascimento()));
        values.put(_AtualizacaoDoContribuinte.TIPO_PESSOA, atualizados.getTipoPessoa());
        values.put(_AtualizacaoDoContribuinte.ESCOLARIDADE, atualizados.getEscolaridade());
        values.put(_AtualizacaoDoContribuinte.TELEFONE, atualizados.getTelefone());
        values.put(_AtualizacaoDoContribuinte.CELULAR, atualizados.getCelular());
        values.put(_AtualizacaoDoContribuinte.EMAIL, atualizados.getEmail());
        return this.conexao.update(_AtualizacaoDoContribuinte.NOME_DA_TABELA, values, _AtualizacaoDoContribuinte.ID, parametros);

    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_AtualizacaoDoContribuinte.NOME_DA_TABELA, _AtualizacaoDoContribuinte.ID, parametros);

    }
}
