package com.developer.demetrio.repositorio;

import android.content.ContentValues;

import com.developer.demetrio.dadabase.constantes._Cadastro;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Cadastro;

public class RepositorioCadastro implements IRepositorioCadastro {
    @Override
    public void inserir(Cadastro cadastro) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Cadastro.ID, cadastro.getId());
        values.put(_Cadastro.NUM_CADASTRO, cadastro.getNumCadastro());
        values.put(_Cadastro.DISTRITO, cadastro.getDistrito());
        values.put(_Cadastro.SETOR, cadastro.getSetor());
        values.put(_Cadastro.QUADRA, cadastro.getQuadra());
        values.put(_Cadastro.LOTE, cadastro.getLote());
        values.put(_Cadastro.ID_AREAS_DO_IMOVEL, cadastro.getAreasDoImovel().getId());
        values.put(_Cadastro.ID_VALORES_VENAIS, cadastro.getValoresVenais().getId());
        values.put(_Cadastro.ID_ALIQUOTA, cadastro.getAliquota().getId());

    }

    @Override
    public Cadastro buscar(Long id) throws RepositorioException {
        return null;
    }

    @Override
    public void excluir(Long id) throws RepositorioException {

    }
}
