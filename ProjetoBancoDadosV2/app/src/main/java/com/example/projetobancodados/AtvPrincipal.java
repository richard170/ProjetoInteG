package com.example.projetobancodados;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AtvPrincipal extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnVeiculo, btnProduto, btnCategoria, btnDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_principal);

        btnVeiculo = findViewById(R.id.btnVeiculo);
        btnVeiculo.setOnClickListener(this);
        btnDeslogar = findViewById(R.id.btnDeslogar);
        btnDeslogar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnVeiculo) {
            Intent atv = new Intent(this, AtvListaVeiculo.class);
            startActivity(atv);
        } else if (v == btnDeslogar) {
            showLogoutConfirmationDialog();
        }
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deslogar");
        builder.setMessage("Tem certeza de que deseja deslogar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Implemente a lógica de deslogar aqui (por exemplo, limpar dados de sessão)
                redirectToPrincipalEscolha();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Não fazer nada, apenas fechar o diálogo
            }
        });
        builder.show();
    }

    private void redirectToPrincipalEscolha() {
        Intent intent = new Intent(this, AtvPrincipalEscolha.class);
        startActivity(intent);
        finish(); // fecha a tela atual para não voltar
    }
}
