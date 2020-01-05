package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._AtualizacaoDoContribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;

import java.text.SimpleDateFormat;

public class RepositorioDadosAtualizadosDoContribuinte implements IRepositorioDadosAtualizadosDoContribuinte {
    @Override
    public void inserir(AtualizacaoDoContribuinte atualizados) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_AtualizacaoDoContribuinte.ID, atualizados.getId());
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



    }

    @Override
    public DadosDeAtualizacaoProprietario buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void atualizar(DadosDeAtualizacaoProprietario dados) throws RepositorioException {

    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
