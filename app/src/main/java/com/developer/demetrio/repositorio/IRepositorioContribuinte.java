package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Contribuinte;

public interface IRepositorioContribuinte {

    long inserir(Contribuinte aliquota) throws RepositorioException;

    Contribuinte buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
