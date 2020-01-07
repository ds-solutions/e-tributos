package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.DadosCadastradosDoContribuinte;

public interface IRepositorioDadosDoContribuinte {

    long inserir(DadosCadastradosDoContribuinte aliquota) throws RepositorioException;

    DadosCadastradosDoContribuinte buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
