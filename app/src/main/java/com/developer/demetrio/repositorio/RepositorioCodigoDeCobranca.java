package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._CodigoDeCobranca;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.CodigoDeCobranca;

public class RepositorioCodigoDeCobranca implements IRepositorioCodigoDeCobranca {
    @Override
    public void inserir(CodigoDeCobranca codigo) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_CodigoDeCobranca.ID, codigo.getId());
        values.put(_CodigoDeCobranca.TIPO, codigo.getTipo());
        values.put(_CodigoDeCobranca.TAXA_TESTADA, codigo.getTaxaTestada());
    }

    @Override
    public CodigoDeCobranca buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
