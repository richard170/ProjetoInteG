package com.example.projetopi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AtvPrincipalEscolha extends AppCompatActivity {

    private Button btnLoginAdmin;
    private Button btnLoginUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_principal_escolha);

        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        btnLoginUsuario = findViewById(R.id.btnLoginUsuario);

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a tela de login específica para administração
                Intent intent = new Intent(AtvPrincipalEscolha.this, AtvLoginAdmin.class);
                startActivity(intent);
            }
        });

        btnLoginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a tela de login para usuário
                Intent intent = new Intent(AtvPrincipalEscolha.this, AtvLoginUsuario.class);
                startActivity(intent);
            }
        });
    }
}
