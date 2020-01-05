package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._AreasDoImovel;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AreasDoImovel;

public class RepositorioAreasDoImovel implements IRepositorioAreasDoImovel {
    @Override
    public void inserir(AreasDoImovel areas) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_AreasDoImovel.ID, areas.getId());
        values.put(_AreasDoImovel.TESTADA, areas.getTestada());
        values.put(_AreasDoImovel.AREA_DO_TERRENO, areas.getAreaDoTerreno());
        values.put(_AreasDoImovel.AREA_TOTAL_DO_TERRENO, areas.getAreaTotalDoTerreno());
        values.put(_AreasDoImovel.EDIFICADO, areas.getEdificado());
        values.put(_AreasDoImovel.AREA_TOTAL_EDIFICADO, areas.getAreaTotalEdificado());
        values.put(_AreasDoImovel.EXCEDENTE, areas.getExcedente());
        values.put(_AreasDoImovel.FRACAO, areas.getFracao());
    }

    @Override
    public AreasDoImovel buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
