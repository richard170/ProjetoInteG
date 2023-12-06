//test
package com.example.projetobancodados.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatManager extends SQLiteOpenHelper {

    private static final String NAME = "chat.db";
    private static final int VERSION = 1;

    public ChatManager(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crie as tabelas necessárias para armazenar os chats
        String createTable = "CREATE TABLE IF NOT EXISTS chat_messages (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "chat_id TEXT, " +
                "message TEXT, " +
                "timestamp INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Adicione código de atualização conforme necessário
        // Neste exemplo, estamos descartando e recriando a tabela
        db.execSQL("DROP TABLE IF EXISTS chat_messages");
        onCreate(db);
    }

    public void salvarMensagem(String chatId, String mensagem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("chat_id", chatId);
        values.put("message", mensagem);
        values.put("timestamp", System.currentTimeMillis());

        db.insert("chat_messages", null, values);
    }

    public Cursor obterMensagensDoChat(String chatId) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"_id", "message", "timestamp"};
        String selection = "chat_id = ?";
        String[] selectionArgs = {chatId};
        String sortOrder = "timestamp ASC";

        return db.query("chat_messages", projection, selection, selectionArgs, null, null, sortOrder);
    }

    // Outros métodos necessários...

}
