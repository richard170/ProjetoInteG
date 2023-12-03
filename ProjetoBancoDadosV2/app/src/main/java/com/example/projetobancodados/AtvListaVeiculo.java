package com.example.projetobancodados;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.projetobancodados.dao.VeiculoDao;
import com.example.projetobancodados.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class AtvListaVeiculo extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView lstVeiculo;
    Button btnCad, btnVoltar;
    List<Veiculo> listaVeiculos = new ArrayList<>();
    ListAdapter listAdapter;
    int indice;
    VeiculoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_lista_veiculo);

        btnCad = findViewById(R.id.btnCad);
        btnCad.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        lstVeiculo = findViewById(R.id.lstView);
        lstVeiculo.setOnItemClickListener(this);

        dao = new VeiculoDao(this);
        atualizarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        listaVeiculos = dao.listar();
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaVeiculos);
        lstVeiculo.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCad) {
            abrirCadastro("Inserir", null);
        } else if (v == btnVoltar) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Veiculo veiculo = (Veiculo) lstVeiculo.getAdapter().getItem(position);
        abrirCadastro("Editar", veiculo);
    }

    private void abrirCadastro(String acao, Veiculo obj) {
        Intent telaCad = new Intent(this, AtvCadVeiculo.class);
        Bundle extras = new Bundle();
        extras.putString("acao", acao);
        extras.putSerializable("obj", obj);
        telaCad.putExtras(extras);
        startActivity(telaCad);
    }
}
