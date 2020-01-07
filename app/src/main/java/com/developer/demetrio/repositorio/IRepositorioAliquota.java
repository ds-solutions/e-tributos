package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Aliquota;

public interface IRepositorioAliquota {

    long inserir(Aliquota aliquota) throws RepositorioException;

    Aliquota buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
