package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.ValoresVenais;

public interface IRepositorioValoresVenais {

    long inserir(ValoresVenais aliquota) throws RepositorioException;

    ValoresVenais buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
