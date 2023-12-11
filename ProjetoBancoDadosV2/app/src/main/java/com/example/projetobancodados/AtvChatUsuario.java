package com.example.projetobancodados;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projetobancodados.util.ChatManager;

public class AtvChatUsuario extends AppCompatActivity {

    private EditText editTextMensagemUsuario;
    private Button btnEnviarMensagemUsuario;
    private LinearLayout chatContainerUsuario;
    private ChatManager chatManager;
    private Long userId; // Variável para armazenar o ID do usuário
    private Button btnDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_chat_usuario);

        editTextMensagemUsuario = findViewById(R.id.editTextMensagemUsuario);
        btnEnviarMensagemUsuario = findViewById(R.id.btnEnviarMensagemUsuario);
        chatContainerUsuario = findViewById(R.id.chatContainerUsuario);
        chatManager = new ChatManager(getApplicationContext());
        btnDeslogar = findViewById(R.id.btnDeslogar);

        // Obtenha as informações do usuário do Intent
        userId = getIntent().getLongExtra("userId", 0L);

        // Adicione um log para verificar o valor do userId
        Log.d("AtvChatUsuario", "userId recuperado: " + userId);

        // Exemplo: exibir o ID do usuário na ActionBar
        setTitle("Chat do Usuário: " + userId);

        // Carrega as mensagens do chat do usuário
        carregarMensagensDoUsuario();

        btnEnviarMensagemUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagemUsuario();
            }
        });


        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para deslogar
                Intent intent = new Intent(AtvChatUsuario.this, AtvPrincipalEscolha.class);
                startActivity(intent);
                finish();  // Encerra a atividade atual
            }
        });
    }

    private void carregarMensagensDoUsuario() {
        String tabelaChatUsuario = "chat_" + userId;
        Log.d("AtvChatUsuario", "Abrindo tabela do usuário: " + tabelaChatUsuario);

        // Obtenha as mensagens do chat usando o ChatManager
        Cursor cursor = chatManager.obterMensagensDoChat(tabelaChatUsuario);

        // Verifique se o Cursor é não nulo antes de tentar acessar os dados
        if (cursor != null) {
            // Itere sobre as mensagens no Cursor
            while (cursor.moveToNext()) {
                // Obtenha o índice da coluna de mensagem no Cursor
                int indexMensagem = cursor.getColumnIndex("message");

                // Extraia a mensagem do Cursor usando o índice
                String mensagem = cursor.getString(indexMensagem);

                // Adicione a mensagem ao layout de mensagens do usuário
                exibirMensagemNoChatUsuario(mensagem);
            }

            // Feche o Cursor após usá-lo
            cursor.close();
        }
    }

    private void enviarMensagemUsuario() {
        String mensagemUsuario = "Usuario " + userId + ": " + editTextMensagemUsuario.getText().toString().trim();

        if (!mensagemUsuario.isEmpty()) {
            // Adicione a mensagem do usuário ao layout de mensagens
            exibirMensagemNoChatUsuario(mensagemUsuario);

            // Salva a mensagem do usuário no banco de dados
            String tabelaChatUsuario = "chat_"+String.valueOf(userId);
            chatManager.salvarMensagem(tabelaChatUsuario, mensagemUsuario);

            // Adicione logs para rastrear a mensagem sendo salva e a tabela utilizada
            Log.d("AtvChatUsuario", "Mensagem do usuário salva: " + mensagemUsuario);
            Log.d("AtvChatUsuario", "Tabela do usuário utilizada: " + tabelaChatUsuario);

            // Limpa o campo de mensagem após o envio
            editTextMensagemUsuario.setText("");
        }
    }

    private void exibirMensagemNoChatUsuario(String mensagem) {
        // Exiba a mensagem no layout do chat do usuário
        TextView textViewMensagem = new TextView(this);
        textViewMensagem.setText(mensagem);

        // Adicione a TextView ao contêiner de chat do usuário
        chatContainerUsuario.addView(textViewMensagem);

        // Role o ScrollView para a parte inferior para exibir as mensagens mais recentes
        final ScrollView scrollView = findViewById(R.id.scrollViewUsuario);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
