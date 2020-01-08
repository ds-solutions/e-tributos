package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.databases.constantes._Cadastro;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.ValoresVenais;

public class RepositorioCadastro implements IRepositorioCadastro {

    private SQLiteDatabase conexao;
    private Cadastro objeto;
    private static RepositorioCadastro instancia;

    public RepositorioCadastro() {

    }

    public RepositorioCadastro(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public long inserir(Cadastro cadastro) throws RepositorioException {
        ContentValues values = new ContentValues();
        values.put(_Cadastro.INSCRICAO, cadastro.getInscricao());
        values.put(_Cadastro.NUM_CADASTRO, cadastro.getNumCadastro());
        values.put(_Cadastro.DISTRITO, cadastro.getDistrito());
        values.put(_Cadastro.SETOR, cadastro.getSetor());
        values.put(_Cadastro.QUADRA, cadastro.getQuadra());
        values.put(_Cadastro.LOTE, cadastro.getLote());
        values.put(_Cadastro.UNIDADE, cadastro.getUnidade());
        values.put(_Cadastro.ID_AREAS_DO_IMOVEL, cadastro.getAreasDoImovel().getId());
        values.put(_Cadastro.ID_VALORES_VENAIS, cadastro.getValoresVenais().getId());
        values.put(_Cadastro.ID_ALIQUOTA, cadastro.getAliquota().getId());
        return this.conexao.insertOrThrow(_Cadastro.NOME_DA_TABELA, null, values);
    }

    @Override
    public Cadastro buscar(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(_Cadastro.ID);
        sql.append(", ");
        sql.append(_Cadastro.DISTRITO);
        sql.append(", ");
        sql.append(_Cadastro.SETOR);
        sql.append(", ");
        sql.append(_Cadastro.QUADRA);
        sql.append(", ");
        sql.append(_Cadastro.LOTE);
        sql.append(", ");
        sql.append(_Cadastro.UNIDADE);
        sql.append(", ");
        sql.append(_Cadastro.INSCRICAO);
        sql.append(", ");
        sql.append(_Cadastro.NUM_CADASTRO);
        sql.append(", ");
        sql.append(_Cadastro.ID_VALORES_VENAIS);
        sql.append(", ");
        sql.append(_Cadastro.ID_ALIQUOTA);
        sql.append(", ");
        sql.append(_Cadastro.ID_AREAS_DO_IMOVEL);
        sql.append(" FROM ");
        sql.append(_Cadastro.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Cadastro.ID);
        sql.append(" =? ");
        Cursor resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Cadastro cadastro = new Cadastro();
            cadastro.setAliquota(new Aliquota());
            cadastro.setAreasDoImovel(new AreasDoImovel());
            cadastro.setValoresVenais(new ValoresVenais());
            cadastro.getAliquota().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_ALIQUOTA)));
            cadastro.getAreasDoImovel().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_AREAS_DO_IMOVEL)));
            cadastro.getValoresVenais().setId(resultado.getLong(resultado.getColumnIndexOrThrow(_Cadastro.ID_VALORES_VENAIS)));
            cadastro.setInscricao(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.INSCRICAO)));
            cadastro.setNumCadastro(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.NUM_CADASTRO)));
            cadastro.setDistrito(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.DISTRITO)));
            cadastro.setSetor(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.SETOR)));
            cadastro.setQuadra(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.QUADRA)));
            cadastro.setLote(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.LOTE)));
            cadastro.setUnidade(resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.UNIDADE)));
            System.out.println("NUMERO DO CADASTRO - > " + resultado.getString(resultado.getColumnIndexOrThrow(_Cadastro.NUM_CADASTRO)));
            return cadastro;

        }
        return null;
    }

    @Override
    public void excluir(long id) throws RepositorioException {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        this.conexao.delete(_Cadastro.NOME_DA_TABELA, _Cadastro.ID, parametros);
    }
}
