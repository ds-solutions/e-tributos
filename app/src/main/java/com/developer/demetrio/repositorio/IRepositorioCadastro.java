package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Cadastro;

import java.util.List;

public interface IRepositorioCadastro {

    long inserir(Cadastro aliquota) throws RepositorioException;

    Cadastro buscar(long id) throws RepositorioException;

    void excluir(long id) throws RepositorioException;

    List<Cadastro> registrados() throws RepositorioException;
}
