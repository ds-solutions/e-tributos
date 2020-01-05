package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.IPTU;

public interface IRepositorioIPTU {

    void inserir(IPTU aliquota) throws RepositorioException;

    IPTU buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
