package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Endereco;

import java.util.List;

public interface IRepositorioEndereco {

    long inserir(Endereco aliquota) throws RepositorioException;

    Endereco buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;

    List<String> nomesLogradouros() throws RepositorioException;
}
