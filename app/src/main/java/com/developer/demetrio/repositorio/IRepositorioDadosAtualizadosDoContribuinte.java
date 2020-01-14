package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;

public interface IRepositorioDadosAtualizadosDoContribuinte {

    long inserir(AtualizacaoDoContribuinte aliquota) throws RepositorioException;

    AtualizacaoDoContribuinte buscar(long id) throws RepositorioException;

    void atualizar(AtualizacaoDoContribuinte dados) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
