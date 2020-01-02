package com.developer.demetrio.beans;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public abstract class ObjetoBasico {
    public abstract String[] getColunas();

    public abstract Integer getId();

    public abstract String getNomeTabela();

    public abstract <T extends ObjetoBasico> ArrayList<T> preencherObjetos(Cursor cursor);

    public abstract ContentValues preencherValues();

    public String getNameId() {
        return getColunas()[0];
    }
}
