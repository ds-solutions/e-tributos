package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AreasDoImovel;

public interface IRepositorioAreasDoImovel {

    long inserir(AreasDoImovel aliquota) throws RepositorioException;

    AreasDoImovel buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
