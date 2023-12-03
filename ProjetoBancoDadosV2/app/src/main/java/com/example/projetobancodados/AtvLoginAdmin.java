package com.example.projetobancodados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AtvLoginAdmin extends AppCompatActivity {

    private EditText editTextAdminUsername;
    private EditText editTextAdminPassword;
    private Button btnAdminLogin;
    private Button btnVoltarPrincipalEscolha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_login_admin);

        editTextAdminUsername = findViewById(R.id.editTextAdminUsername);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnVoltarPrincipalEscolha = findViewById(R.id.btnVoltarPrincipalEscolha);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente a lógica de login admin aqui
                if (isValidAdminLogin()) {
                    // Se o login for válido, redireciona para a AtvPrincipal
                    Intent intent = new Intent(AtvLoginAdmin.this, AtvPrincipal.class);
                    startActivity(intent);
                    finish(); // fecha a tela de login para não voltar
                } else {
                    // Caso contrário, exibe uma mensagem de erro
                    Toast.makeText(AtvLoginAdmin.this, "Login de admin inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVoltarPrincipalEscolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Botão para voltar à tela PrincipalEscolha
                Intent intent = new Intent(AtvLoginAdmin.this, AtvPrincipalEscolha.class);
                startActivity(intent);
                finish(); // fecha a tela de login para não voltar
            }
        });
    }

    private boolean isValidAdminLogin() {
        // Implemente a lógica de validação do login de admin conforme necessário
        String adminUsername = editTextAdminUsername.getText().toString().trim();
        String adminPassword = editTextAdminPassword.getText().toString().trim();

        // Exemplo: verificar se o nome de usuário e a senha correspondem aos dados de admin
        // Aqui, você pode usar uma lógica mais avançada, como verificar em um banco de dados

        // Este é um exemplo simples, substitua pela lógica real
        return adminUsername.equals("admin") && adminPassword.equals("admin");
    }
}
