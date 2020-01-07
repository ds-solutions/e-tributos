package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.CodigoDeCobranca;

public interface IRepositorioCodigoDeCobranca {
    long inserir(CodigoDeCobranca aliquota) throws RepositorioException;

    CodigoDeCobranca buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
