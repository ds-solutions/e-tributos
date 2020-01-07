package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Tributo;

public interface IRepositorioTributo {

    long inserir(Tributo aliquota) throws RepositorioException;

    Tributo buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
