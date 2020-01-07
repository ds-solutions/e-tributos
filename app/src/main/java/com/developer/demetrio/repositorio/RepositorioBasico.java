package com.developer.demetrio.repositorio;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.developer.demetrio.beans.ObjetoBasico;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.util.ConstantesSistemas;

import java.util.ArrayList;

class RepositorioBasico implements IRepositorioBasico{
    //com.developer.demetrio.databases
    public static final String CAMINHO_BANCO = "data/data/com.developer.demetrio/databases";
    public static final String NOME_BANCO = "tributos_banco";
    protected static Context context;
    protected static SQLiteDatabase db;
    private static SQLiteOpenHelper dbHelper;
    private static RepositorioBasico instancia;


    public static RepositorioBasico getInstance() {
        if (instancia == null) {
            instancia = new RepositorioBasico();
        }
        return instancia;
    }


    @Override
    public void atualizar(ObjetoBasico objetoBasico) throws RepositorioException {

    }

    @Override
    public long inserir(ObjetoBasico objetoBasico) throws RepositorioException {
        return 0;
    }

    @Override
    public <T extends ObjetoBasico> ArrayList<T> pesquisar(T t) throws RepositorioException {
        return null;
    }

    @Override
    public <T extends ObjetoBasico> T pesquisarPorId(Integer num, T t) throws RepositorioException {
        return null;
    }

    @Override
    public void remover(ObjetoBasico objetoBasico) throws RepositorioException {

    }

    @Override
    public boolean verificarExistenciaBancoDeDados() {
        return false;
    }

    public void fecharBanco()throws RepositorioException {
        if (db != null) {
            try {
                db.close();
            }catch (SQLException e) {
                e.printStackTrace();
                Log.e(ConstantesSistemas.CATEGORIA, e.getMessage());
                throw new RepositorioException(context.getResources().getString(R.string.db_erro));
            }
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    protected RepositorioBasico() {
        if (db == null || !(db == null || db.isOpen())) {
            abrirBanco();
        }
    }

    private void abrirBanco() {
       /* try {
            fecharBanco();
            if (!registrarBanco() || db == null || (db != null && !db.isOpen())) {
                BDScr
            }
        }*/
    }
}

