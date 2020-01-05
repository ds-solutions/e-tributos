package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.LatLng;

public interface IRepositorioLatLng {

    void inserir(LatLng aliquota) throws RepositorioException;

    LatLng buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
