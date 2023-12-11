package com.example.projetopi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.projetopi.model.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDao {

    private final String TABELA = "veiculo";
    private final String[] CAMPOS = {"id, nome, placa, modeloAno, telefone, email, observacao"};
    private Conexao conexao;
    private SQLiteDatabase banco;

    public VeiculoDao(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    private ContentValues preencherValores(Veiculo veiculo) {
        ContentValues values = new ContentValues();

        values.put("nome", veiculo.getNome());
        values.put("placa", veiculo.getPlaca());
        values.put("modeloAno", veiculo.getModeloAno());
        values.put("telefone", veiculo.getTelefone());
        values.put("email", veiculo.getEmail());
        values.put("observacao", veiculo.getObservacao());

        return values;
    }

    public long inserir(Veiculo veiculo) {
        ContentValues values = preencherValores(veiculo);
        return banco.insert(TABELA, null, values);
    }

    public long alterar(Veiculo veiculo) {
        ContentValues values = preencherValores(veiculo);
        return banco.update(TABELA, values, "id = ?", new String[]{veiculo.getId().toString()});
    }

    public long excluir(Veiculo veiculo) {
        return banco.delete(TABELA, "id = ?", new String[]{veiculo.getId().toString()});
    }

    public List<Veiculo> listar() {
        Cursor c = banco.query(TABELA, CAMPOS,
                null, null, null, null, null);

        List<Veiculo> lista = new ArrayList<>();
        while (c.moveToNext()) {
            Veiculo veiculo = new Veiculo();
            veiculo.setId(c.getLong(0));
            veiculo.setNome(c.getString(1));
            veiculo.setPlaca(c.getString(2));
            veiculo.setModeloAno(c.getString(3));
            veiculo.setTelefone(c.getString(4));
            veiculo.setEmail(c.getString(5));
            veiculo.setObservacao(c.getString(6));
            lista.add(veiculo);
        }
        return lista;
    }
}
