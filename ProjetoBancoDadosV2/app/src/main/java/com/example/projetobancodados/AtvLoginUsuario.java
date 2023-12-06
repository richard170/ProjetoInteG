package com.example.projetobancodados;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private UsuarioDao usuarioDao;
    private long idDoUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_login_usuario);

        usuarioDao = new UsuarioDao(this);

        editTextUsuarioUsername = findViewById(R.id.editTextUsuarioUsername);
        editTextUsuarioPassword = findViewById(R.id.editTextUsuarioPassword);
        btnUsuarioLogin = findViewById(R.id.btnUsuarioLogin);
        btnVoltarPrincipalEscolhaUsuario = findViewById(R.id.btnVoltarPrincipalEscolhaUsuario);

        btnUsuarioLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioUsername = editTextUsuarioUsername.getText().toString().trim();
                String usuarioPassword = editTextUsuarioPassword.getText().toString().trim();

                if (usuarioDao != null) {
                    idDoUsuarioLogado = usuarioDao.obterIdDoUsuarioLogado(usuarioUsername, usuarioPassword);

                    if (isValidUsuarioLogin()) {
                        Intent intent = new Intent(AtvLoginUsuario.this, AtvChatUsuario.class);
                        intent.putExtra("userId", idDoUsuarioLogado);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AtvLoginUsuario.this, "Login de usuário inválido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("AtvLoginUsuario", "usuarioDao is null");
                }
            }
        });

        btnVoltarPrincipalEscolhaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtvLoginUsuario.this, AtvPrincipalEscolha.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isValidUsuarioLogin() {
        String usuarioUsername = editTextUsuarioUsername.getText().toString().trim();
        String usuarioPassword = editTextUsuarioPassword.getText().toString().trim();

        if (usuarioDao != null) {
            return usuarioDao.validarUsuario(usuarioUsername, usuarioPassword);
        } else {
            Log.e("AtvLoginUsuario", "usuarioDao is null");
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        if (usuarioDao != null) {
            usuarioDao.close();
        }
        super.onDestroy();
    }
}
