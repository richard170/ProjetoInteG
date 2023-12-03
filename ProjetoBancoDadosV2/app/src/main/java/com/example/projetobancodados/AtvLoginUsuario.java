package com.example.projetobancodados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetobancodados.dao.UsuarioDao;

public class AtvLoginUsuario extends AppCompatActivity {

    private EditText editTextUsuarioUsername;
    private EditText editTextUsuarioPassword;
    private Button btnUsuarioLogin;
    private Button btnVoltarPrincipalEscolhaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_login_usuario);

        editTextUsuarioUsername = findViewById(R.id.editTextUsuarioUsername);
        editTextUsuarioPassword = findViewById(R.id.editTextUsuarioPassword);
        btnUsuarioLogin = findViewById(R.id.btnUsuarioLogin);
        btnVoltarPrincipalEscolhaUsuario = findViewById(R.id.btnVoltarPrincipalEscolhaUsuario);

        btnUsuarioLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente a lógica de login de usuário aqui
                if (isValidUsuarioLogin()) {
                    // Se o login for válido, redireciona para a StatusUsuarioActivity
                    Intent intent = new Intent(AtvLoginUsuario.this, AtvStatusUsuario.class);
                    startActivity(intent);
                    finish(); // fecha a tela de login para não voltar
                } else {
                    // Caso contrário, exibe uma mensagem de erro
                    Toast.makeText(AtvLoginUsuario.this, "Login de usuário inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVoltarPrincipalEscolhaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Botão para voltar à tela PrincipalEscolha
                Intent intent = new Intent(AtvLoginUsuario.this, AtvPrincipalEscolha.class);
                startActivity(intent);
                finish(); // fecha a tela de login para não voltar
            }
        });
    }

    private boolean isValidUsuarioLogin() {
        // Implemente a lógica de validação do login de usuário conforme necessário
        String usuarioUsername = editTextUsuarioUsername.getText().toString().trim();
        String usuarioPassword = editTextUsuarioPassword.getText().toString().trim();

        // Verifique no banco de dados se o login e a senha correspondem a algum usuário
        UsuarioDao usuarioDao = new UsuarioDao(this);
        boolean isValid = usuarioDao.validarUsuario(usuarioUsername, usuarioPassword);
        usuarioDao.close();

        return isValid;
    }

    // Método de exemplo para obter o objeto Long (substitua por sua lógica real)
    private Long getObjetoLong() {
        // Lógica para obter o objeto Long (substitua por sua lógica real)
        // Certifique-se de que o objeto Long seja inicializado corretamente
        // e não seja nulo
        return 123L; // Substitua pelo valor real ou lógica para obter o objeto Long
    }
}
