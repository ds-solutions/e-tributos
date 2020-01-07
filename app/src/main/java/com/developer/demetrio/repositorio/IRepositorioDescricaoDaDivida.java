package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.DescricaoDaDivida;

import java.util.List;

public interface IRepositorioDescricaoDaDivida {

    long inserir(DescricaoDaDivida aliquota) throws RepositorioException;

    DescricaoDaDivida buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;

    List<DescricaoDaDivida> descricoesDaDividaDe(long iptu_id) throws RepositorioException;
}
