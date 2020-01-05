package com.developer.demetrio.dadabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ETributosDadaBaseOpenHelper extends SQLiteOpenHelper {
    public ETributosDadaBaseOpenHelper(@Nullable Context context) {
        super(context, "DATABASE_TRIBUTOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDLL.getCreateLatLng());
        db.execSQL(ScriptDLL.getCreateTableIPTU());
        db.execSQL(ScriptDLL.getCreateTableDescricaoDaDivida());
        db.execSQL(ScriptDLL.getCreateTableTributos());
        db.execSQL(ScriptDLL.getCreateTableAtualizacaoDoContribuinte());
        db.execSQL(ScriptDLL.getCreateTableDadosCadastradosDoContribuinte());
        db.execSQL(ScriptDLL.getCreateTableContribuinte());
        db.execSQL(ScriptDLL.getCreateTableEndereco());
        db.execSQL(ScriptDLL.getCreateTableCodigoDeCobranca());
        db.execSQL(ScriptDLL.getCreateTableAliquota());
        db.execSQL(ScriptDLL.getCreateTableValoresVenais());
        db.execSQL(ScriptDLL.getCreateTableAreasDoImovel());
        db.execSQL(ScriptDLL.getCreateTableCadastro());
        db.execSQL(ScriptDLL.getCreateTableImovel());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
