package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;

public interface IRepositorioDadosAtualizadosDoContribuinte {

    void inserir(AtualizacaoDoContribuinte aliquota) throws RepositorioException;

    DadosDeAtualizacaoProprietario buscar(Long id) throws RepositorioException;

    void atualizar(DadosDeAtualizacaoProprietario dados) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
