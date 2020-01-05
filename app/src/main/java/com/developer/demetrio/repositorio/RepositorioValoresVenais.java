package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._ValoresVenais;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.ValoresVenais;

public class RepositorioValoresVenais implements IRepositorioValoresVenais {
    @Override
    public void inserir(ValoresVenais valores) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_ValoresVenais.ID, valores.getId());
        values.put(_ValoresVenais.TERRENO, valores.getTerreno());
        values.put(_ValoresVenais.EDIFICADA, valores.getEdificada());
        values.put(_ValoresVenais.EXCEDENTE, valores.getExcedente());
        values.put(_ValoresVenais.TOTAL, valores.getTotal());

    }

    @Override
    public ValoresVenais buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
