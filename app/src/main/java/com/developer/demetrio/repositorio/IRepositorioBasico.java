package com.developer.demetrio.repositorio;

import com.developer.demetrio.beans.ObjetoBasico;
import com.developer.demetrio.excecoes.RepositorioException;

import java.util.ArrayList;

interface IRepositorioBasico {
    void atualizar(ObjetoBasico objetoBasico) throws RepositorioException;

    long inserir(ObjetoBasico objetoBasico) throws RepositorioException;

    <T extends ObjetoBasico> ArrayList<T> pesquisar(T t) throws RepositorioException;

    <T extends ObjetoBasico> T pesquisarPorId(Integer num, T t) throws RepositorioException;

    void remover(ObjetoBasico objetoBasico) throws RepositorioException;

    boolean verificarExistenciaBancoDeDados();
}
