package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._DadosCadastradosDoContribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;
import com.developer.demetrio.model.ValoresVenais;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositorioDadosDoContribuinte implements IRepositorioDadosDoContribuinte {
    @Override
    public void inserir(DadosCadastradosDoContribuinte dados) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_DadosCadastradosDoContribuinte.ID, dados.getId());
        values.put(_DadosCadastradosDoContribuinte.NOME, dados.getNome());
        values.put(_DadosCadastradosDoContribuinte.CPF, dados.getCpf());
        values.put(_DadosCadastradosDoContribuinte.RG, dados.getRg());
        values.put(_DadosCadastradosDoContribuinte.ORG_EMISSOR, dados.getOrgEmissor());
        String data ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(_DadosCadastradosDoContribuinte.DATA_NASC, dateFormat.format(dados.getDataNasc()));
        values.put(_DadosCadastradosDoContribuinte.NACIONALIDADE, dados.getNacionalidade());
        values.put(_DadosCadastradosDoContribuinte.NATURALIDADE, dados.getNaturalidade());
        values.put(_DadosCadastradosDoContribuinte.RACA, dados.getRaca());
        values.put(_DadosCadastradosDoContribuinte.SEXO, dados.getSexo());
        values.put(_DadosCadastradosDoContribuinte.EMAIL, dados.getEmail());
        values.put(_DadosCadastradosDoContribuinte.CELULAR, dados.getNumeroCelular());
    }

    @Override
    public DadosCadastradosDoContribuinte buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
