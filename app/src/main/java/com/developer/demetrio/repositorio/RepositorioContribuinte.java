package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._Contribuinte;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Contribuinte;

public class RepositorioContribuinte implements IRepositorioContribuinte {
    @Override
    public void inserir(Contribuinte contribuinte) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Contribuinte.ID, contribuinte.getId());
        values.put(_Contribuinte.ID_DADOS_DO_CONTRIBUINTE, contribuinte.getDadosCadastradosDoContribuinte().getId());
        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            values.put(_Contribuinte.ID_AUTALIZACAO_DO_CONTRIBUINTE, contribuinte.getAtualizacaoDoContribuinte().getId());
        }
    }

    @Override
    public Contribuinte buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
