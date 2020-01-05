package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._Endereco;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Endereco;

public class RepositorioEndereco implements IRepositorioEndereco {
    @Override
    public void inserir(Endereco endereco) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Endereco.ID, endereco.getId());
        values.put(_Endereco.CIDADE, endereco.getCidade());
        values.put(_Endereco.UF, endereco.getUf());
        values.put(_Endereco.BAIRRO, endereco.getBairro());
        values.put(_Endereco.LOGRADOURO, endereco.getLogradouro());
        values.put(_Endereco.COMPLEMENTO, endereco.getComplemento());
        values.put(_Endereco.NUMERO, endereco.getNumero());
        values.put(_Endereco.CEP, endereco.getCep());
    }

    @Override
    public Endereco buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
