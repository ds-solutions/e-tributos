package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Cadastro;

public interface IRepositorioCadastro {

    long inserir(Cadastro aliquota) throws RepositorioException;

    Cadastro buscar(Long id) throws RepositorioException;

    void excluir(Long id) throws RepositorioException;
}
