package com.developer.demetrio.repositorio;

import android.database.Cursor;

import com.developer.demetrio.beans.Parametros;
import com.developer.demetrio.excecoes.RepositorioException;

public class RepositorioParametros extends RepositorioBasico /* implements IRepositorioParamentros*/ {
    private static RepositorioParametros instancia;
    private Parametros parametros;

    public void resetar() {
        instancia = null;
    }

    public static RepositorioParametros getInstance() {
        if (instancia == null) {
            instancia = new RepositorioParametros();
            instancia.parametros = new Parametros();
        }
        return instancia;
    }
    public Parametros buscarParametros() throws RepositorioException {
        Cursor cursor = null;
/*
        try {
            cursor = db
        }*/
return null;
    }
}
