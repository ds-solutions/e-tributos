package com.developer.demetrio.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.demetrio.databases.constantes._DataBase;

public class ConexaoDataBase {

    private ETributosDadaBaseOpenHelper getInstance;
    private ConexaoDataBase instance;
    private Context context;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;


    public ConexaoDataBase() {
    }

    public ConexaoDataBase(Context context) {
        this.context = context;
     //   this.context.deleteDatabase(_DataBase.NOME_DO_BANCO);
       getInstance = new ETributosDadaBaseOpenHelper(this.context);
    }

    public SQLiteDatabase concectarComBanco(Context context)throws SQLException {
        this.context = context;
        sqLiteOpenHelper = new ETributosDadaBaseOpenHelper(this.context);
        return database = sqLiteOpenHelper.getWritableDatabase();
    }



}
