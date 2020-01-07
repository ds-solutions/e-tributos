package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.LatLng;

public interface IRepositorioLatLng {

    long inserir(LatLng aliquota) throws RepositorioException;

    LatLng buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;
}
