package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._TRIBUTOS;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Tributo;

public class RepositorioTributo implements IRepositorioTributo {
    @Override
    public void inserir(Tributo tributo) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_TRIBUTOS.ID, tributo.getId());
        values.put(_TRIBUTOS.ID_IPTU, tributo.getIptu().getId());

    }

    @Override
    public Tributo buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
