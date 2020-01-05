package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.iptu.DescricaoDaDivida;

import java.util.List;

public class RepositorioDescricaoDaDivida implements IRepositorioDescricaoDaDivida {
    @Override
    public void inserir(DescricaoDaDivida aliquota) throws RepositorioException {

    }

    @Override
    public DescricaoDaDivida buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }

    @Override
    public List<DescricaoDaDivida> descricoesDaDividaDe(Long iptu_id) throws RepositorioException {
        return null;
    }
}
