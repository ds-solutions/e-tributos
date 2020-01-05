package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Endereco;

public interface IRepositorioEndereco {

    void inserir(Endereco aliquota) throws RepositorioException;

    Endereco buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
