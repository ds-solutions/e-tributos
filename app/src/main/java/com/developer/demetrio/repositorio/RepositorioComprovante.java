package com.developer.demetrio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.demetrio.databases.constantes._Comprovante;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Comprovante;

public class RepositorioComprovante implements IRepositorioComprovante {
    private SQLiteDatabase conexao;

    public RepositorioComprovante(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    @Override
    public Comprovante buscarPorId(long id) throws RepositorioException {
        String[] parametros = new String[]{String.valueOf(id)};
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM ");
        sql.append(_Comprovante.NOME_DA_TABELA);
        sql.append(" WHERE ");
        sql.append(_Comprovante.ID);
        sql.append(" =? ");
        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            Comprovante comprovante = getComprovante(resultado);
            resultado.close();
            return comprovante;
        }
        return null;
    }

    private Comprovante getComprovante(Cursor resultado) {
        Comprovante comprovante = new Comprovante();
        comprovante.setId(resultado.getInt(resultado.getColumnIndexOrThrow(_Comprovante.ID)));
        comprovante.setFotoRgFrente(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.RG_FRENTE)));
        comprovante.setFotoRgVerso(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.RG_VERSO)));
        comprovante.setFotoCPF(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.CPF)));
        comprovante.setFotoEscritura(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.ESCRITURA)));
        resultado.close();
        return comprovante;
    }

    @Override
    public long salvar(Comprovante comprovante) throws RepositorioException {
        listarComprovantes();
        ContentValues values = new ContentValues();
        values.put(_Comprovante.RG_FRENTE, comprovante.getFotoRgFrente());
        values.put(_Comprovante.RG_VERSO, comprovante.getFotoRgVerso());
        values.put(_Comprovante.CPF, comprovante.getFotoCPF());
        values.put(_Comprovante.ESCRITURA, comprovante.getFotoEscritura());

      return this.conexao.insertOrThrow(_Comprovante.NOME_DA_TABELA, null, values);

    }

    public void listarComprovantes() throws RepositorioException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM ");
        sql.append(_Comprovante.NOME_DA_TABELA);
        Cursor resultado = conexao.rawQuery(sql.toString(), null);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            do {
                System.out.println(resultado.getLong(resultado.getColumnIndexOrThrow(_Comprovante.ID)));
                System.out.println(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.RG_FRENTE)));
                System.out.println(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.RG_VERSO)));
                System.out.println(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.CPF)));
                System.out.println(resultado.getString(resultado.getColumnIndexOrThrow(_Comprovante.ESCRITURA)));
            } while (resultado.moveToNext());
            resultado.close();
        }
    }

    @Override
    public void atualizar(Comprovante comprovante) throws RepositorioException {
        ContentValues values = new ContentValues();
        listarComprovantes();
        values.put(_Comprovante.RG_FRENTE, comprovante.getFotoRgFrente());
        values.put(_Comprovante.RG_VERSO, comprovante.getFotoRgVerso());
        values.put(_Comprovante.CPF, comprovante.getFotoCPF());
        values.put(_Comprovante.ESCRITURA, comprovante.getFotoEscritura());
       this.conexao.update(_Comprovante.NOME_DA_TABELA, values, _Comprovante.ID + " = " + comprovante.getId(), null);

    }
}
