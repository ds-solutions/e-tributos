package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Comprovante;

public interface IRepositorioComprovante {

    Comprovante buscarPorId(long id) throws RepositorioException;
    long salvar(Comprovante comprovante)throws RepositorioException;
    void atualizar(Comprovante comprovante) throws RepositorioException;
    void listarComprovantes() throws RepositorioException;
}
