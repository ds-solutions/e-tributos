package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._DadosCadastradosDoContribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class RepositorioDadosDoContribuinte implements IRepositorioDadosDoContribuinte {

    private SQLiteDatabase conexao;

    public RepositorioDadosDoContribuinte(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(DadosCadastradosDoContribuinte dados) throws RepositorioException {
        ContentValues values = new ContentValues();

        values.put(_DadosCadastradosDoContribuinte.NOME, dados.getNome());
        values.put(_DadosCadastradosDoContribuinte.CPF, dados.getCpf());
        values.put(_DadosCadastradosDoContribuinte.RG, dados.getRg());
        values.put(_DadosCadastradosDoContribuinte.ORG_EMISSOR, dados.getOrgEmissor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(_DadosCadastradosDoContribuinte.DATA_NASC, dateFormat.format(dados.getDataNasc()));
        values.put(_DadosCadastradosDoContribuinte.ESTADO_CIVIL, dados.getEstadoCivil());
        values.put(_DadosCadastradosDoContribuinte.NACIONALIDADE, dados.getNacionalidade());
        values.put(_DadosCadastradosDoContribuinte.NATURALIDADE, dados.getNaturalidade());
        values.put(_DadosCadastradosDoContribuinte.COR, dados.getRaca());
        values.put(_DadosCadastradosDoContribuinte.SEXO, dados.getSexo());
        values.put(_DadosCadastradosDoContribuinte.EMAIL, dados.getEmail());
        values.put(_DadosCadastradosDoContribuinte.CELULAR, dados.getNumeroCelular());

        return this.conexao.insertOrThrow(_DadosCadastradosDoContribuinte.NOME_DA_TABELA, null, values);
    }

    @Override
    public DadosCadastradosDoContribuinte buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = query();

        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            DadosCadastradosDoContribuinte dados = dadosColetados(resultado);
             return dados;
        }
        return null;
    }

    private DadosCadastradosDoContribuinte dadosColetados(Cursor resultado) {

        DadosCadastradosDoContribuinte dados = new DadosCadastradosDoContribuinte();

        dados.setId(resultado.getLong(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ID)));
        dados.setCpf(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NOME)));
        dados.setCpf(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.CPF)));
        dados.setRg(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.RG)));
        dados.setOrgEmissor(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ORG_EMISSOR)));
        java.util.Date date = new java.util.Date(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.DATA_NASC)));
        dados.setDataNasc(date);
        dados.setEstadoCivil(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.ESTADO_CIVIL)));
        dados.setNacionalidade(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NACIONALIDADE)));
        dados.setNaturalidade(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.NATURALIDADE)));
        dados.setRaca(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.COR)));
        dados.setSexo(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.SEXO)));
        dados.setEmail(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.EMAIL)));
        dados.setNumeroCelular(resultado.getString(resultado.getColumnIndexOrThrow(_DadosCadastradosDoContribuinte.CELULAR)));

        return dados;
    }

    private StringBuilder query() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_DadosCadastradosDoContribuinte.ID);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.NOME);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.CPF);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.RG);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.ORG_EMISSOR);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.DATA_NASC);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.ESTADO_CIVIL);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.NACIONALIDADE);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.NATURALIDADE);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.COR);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.SEXO);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.EMAIL);
        sql.append(", ");
        sql.append(_DadosCadastradosDoContribuinte.CELULAR);
        sql.append(" FROM ");
        sql.append(_DadosCadastradosDoContribuinte.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_DadosCadastradosDoContribuinte.ID);
        sql.append(" = ? ");
        return sql;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_DadosCadastradosDoContribuinte.NOME_DA_TABELA, _DadosCadastradosDoContribuinte.ID, parametros);
    }
}
