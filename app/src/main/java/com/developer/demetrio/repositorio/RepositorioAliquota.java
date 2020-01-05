package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._Aliquota;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Aliquota;

public class RepositorioAliquota implements IRepositorioAliquota {
    @Override
    public void inserir(Aliquota aliquota) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Aliquota.ID, aliquota.getId());
        values.put(_Aliquota.TERRENO, aliquota.getTerreno());
        values.put(_Aliquota.EDIFICADO, aliquota.getEdificado());
        values.put(_Aliquota.ZONEAMENTO, aliquota.getZoneamento());
        values.put(_Aliquota.TIPO_CONSTRUCAO, aliquota.getTipoConstrucao());
        values.put(_Aliquota.ID_CODIGO_DE_COBRANCA, aliquota.getCodigoDeCobranca().getId());

    }

    @Override
    public Aliquota buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
