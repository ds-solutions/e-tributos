package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.ValoresVenais;

public interface IRepositorioValoresVenais {

    long inserir(ValoresVenais aliquota) throws RepositorioException;

    ValoresVenais buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
