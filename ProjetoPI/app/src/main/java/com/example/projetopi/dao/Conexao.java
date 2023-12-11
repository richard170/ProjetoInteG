package com.example.projetopi.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String NAME = "banco.db";
    private static final int VERSION = 1; // Aumente a versão para acionar o método onUpgrade

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
        db.execSQL("CREATE TABLE IF NOT EXISTS chat_xxx (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "message TEXT, " +
                "timestamp INTEGER);");

        // Cria a tabela "usuario" apenas se ela não existir
        criarTabelaUsuarioSeNaoExistir(db);

        // Adicione a coluna "telefone" se não existir
        if (!verificarSeColunaExiste(db, "veiculo", "telefone")) {
            db.execSQL("ALTER TABLE veiculo ADD COLUMN telefone VARCHAR(20);");
        }
    }

    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Adicione código de atualização conforme necessário
        if (oldVersion < 3) {
            // Adicione as alterações de esquema necessárias para a versão 3
            db.execSQL(SQL_CREATE_USUARIO);
        }
    }

    private boolean verificarSeColunaExiste(SQLiteDatabase db, String tabela, String coluna) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tabela + ")", null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String columnName = cursor.getString(cursor.getColumnIndex("name"));
                    if (coluna.equals(columnName)) {
                        return true;
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return false;
    }

    private void criarTabelaUsuarioSeNaoExistir(SQLiteDatabase db) {
        // Verifica se a tabela já existe
        String tabelaUsuario = "usuario";
        String sqlCreateUsuario = "CREATE TABLE " + tabelaUsuario + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "login VARCHAR(10), " +
                "senha VARCHAR(4));";

        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name=?", new String[]{tabelaUsuario});

        try {
            // Se a tabela não existe, cria a tabela "usuario"
            if (cursor.getCount() == 0) {
                Log.d("Conexao", "criarTabelaUsuarioSeNaoExistir: Creating usuario table");
                db.execSQL(sqlCreateUsuario);
            } else {
                Log.d("Conexao", "criarTabelaUsuarioSeNaoExistir: usuario table already exists");
            }
        } finally {
            cursor.close();
        }
    }

}
