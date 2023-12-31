package com.example.projetopi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDao extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "banco.db";
    private static final int DATABASE_VERSION = 1;

    public UsuarioDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Nenhuma alteração necessária aqui, pois a tabela usuario já será criada pelo Conexao.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Adicione código de atualização conforme necessário
        if (oldVersion < 3) {
            // Adicione as alterações de esquema necessárias para a versão 3
            db.execSQL("CREATE TABLE usuario (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "login VARCHAR(10), " +
                    "senha VARCHAR(4));");
        }
    }

    public long obterIdDoUsuarioLogado(String usuarioUsername, String usuarioPassword) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"id"};
        String selection = "login = ? AND senha = ?";
        String[] selectionArgs = {usuarioUsername, usuarioPassword};

        Cursor cursor = db.query("usuario", projection, selection, selectionArgs, null, null, null);

        long idDoUsuario = -1; // Valor padrão se não encontrar um usuário

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("id");
            idDoUsuario = cursor.getLong(columnIndex);
        }

        cursor.close();

        return idDoUsuario;
    }


    public long inserirUsuario(String login, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", login);
        values.put("senha", senha);

        long newRowId = db.insert("usuario", null, values);
        db.close();
        return newRowId;
    }

    public boolean validarUsuario(String login, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"login", "senha"};

        String selection = "login = ? AND senha = ?";
        String[] selectionArgs = {login, senha};

        Cursor cursor = db.query("usuario", projection, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.getCount() > 0;

        cursor.close();

        return isValid;
    }
}
