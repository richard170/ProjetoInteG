package com.example.projetobancodados.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String NAME = "banco.db";
    private static final int VERSION = 3; // Aumente a versão para acionar o método onUpgrade

    private static final String SQL_CREATE_VEICULO = "CREATE TABLE veiculo (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(50), " +
            "fone VARCHAR(20), " +
            "email VARCHAR(50), " +
            "placa VARCHAR(10), " +
            "modeloAno VARCHAR(20), " +
            "observacao TEXT);";

    private static final String SQL_CREATE_USUARIO = "CREATE TABLE usuario (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "login VARCHAR(10), " +
            "senha VARCHAR(4));";

    public Conexao(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VEICULO);
        db.execSQL(SQL_CREATE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Adicione código de atualização conforme necessário
        if (oldVersion < 3) {
            // Adicione as alterações de esquema necessárias para a versão 3
            db.execSQL(SQL_CREATE_USUARIO);
        }
    }
}
