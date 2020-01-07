package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.IPTU;

public interface IRepositorioIPTU {

    long inserir(IPTU aliquota) throws RepositorioException;

    IPTU buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
