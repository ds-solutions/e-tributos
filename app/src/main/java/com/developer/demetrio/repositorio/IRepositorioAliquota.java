package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Aliquota;

public interface IRepositorioAliquota {

    void inserir(Aliquota aliquota) throws RepositorioException;

    Aliquota buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
