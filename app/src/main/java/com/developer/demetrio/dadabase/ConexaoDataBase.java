package com.developer.demetrio.dadabase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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

       getInstance = new ETributosDadaBaseOpenHelper(this.context);
    }

    public SQLiteDatabase concectarComBanco(Context context)throws SQLException {
        this.context = context;
        sqLiteOpenHelper = new ETributosDadaBaseOpenHelper(this.context);
        return database = sqLiteOpenHelper.getWritableDatabase();
    }
}
