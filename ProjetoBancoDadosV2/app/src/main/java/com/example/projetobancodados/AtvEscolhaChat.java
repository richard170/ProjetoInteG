package com.example.projetobancodados;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetobancodados.util.ChatManager;

public class AtvEscolhaChat extends AppCompatActivity {

    private EditText editTextChat;
    private Button btnIniciarChat;
    private LinearLayout chatContainer;
    private EditText editTextMensagem;
    private Button btnEnviarMensagem;
    private ChatManager chatManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_escolha_chat);

        editTextChat = findViewById(R.id.editTextChat);
        btnIniciarChat = findViewById(R.id.btnIniciarChat);
        chatContainer = findViewById(R.id.chatContainer);
        editTextMensagem = findViewById(R.id.editTextMensagem);
        btnEnviarMensagem = findViewById(R.id.btnEnviarMensagem);
        chatManager = new ChatManager(getApplicationContext());

        btnIniciarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarChat();
            }
        });

        btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagem();
            }
        });
    }

    private void iniciarChat() {
        String userId = editTextChat.getText().toString().trim();

        if (!userId.isEmpty()) {
            String mensagensDoChat = obterMensagensDoChat(userId);
            exibirMensagensNoChat(mensagensDoChat);
        } else {
            // Trate o caso em que o ID do usuário está vazio
        }
    }

    private String obterMensagensDoChat(String userId) {
        // Obtenha as mensagens do chat usando o ChatManager
        Cursor cursor = chatManager.obterMensagensDoChat(userId);

        // Verifique se o Cursor é não nulo antes de tentar acessar os dados
        if (cursor != null) {
            StringBuilder mensagens = new StringBuilder();

            // Itere sobre as mensagens no Cursor
            while (cursor.moveToNext()) {
                // Obtenha o índice da coluna de mensagem no Cursor
                int indexMensagem = cursor.getColumnIndex("message");

                // Extraia a mensagem do Cursor usando o índice
                String mensagem = cursor.getString(indexMensagem);

                // Adicione a mensagem ao StringBuilder
                mensagens.append(mensagem).append("\n");
            }

            // Feche o Cursor após usá-lo
            cursor.close();

            // Retorne as mensagens como uma String
            return mensagens.toString();
        }

        // Se o Cursor for nulo, retorne uma String vazia
        return "";
    }

    private void exibirMensagensNoChat(String mensagens) {
        // Exiba as mensagens no layout do chat
        TextView textViewChat = new TextView(this);
        textViewChat.setText(mensagens);

        // Adicione a TextView ao contêiner de chat
        chatContainer.addView(textViewChat);

        // Role o ScrollView para a parte inferior para exibir as mensagens mais recentes
        final ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void enviarMensagem() {
        String mensagem = editTextMensagem.getText().toString().trim();

        if (!mensagem.isEmpty()) {
            // Adicione a mensagem ao layout de mensagens
            String mensagemFormatada = "admin: " + mensagem;

            // Exibe a mensagem formatada no chat
            exibirMensagemNoChat(mensagemFormatada);

            // Salva a mensagem formatada no banco de dados
            chatManager.salvarMensagem(String.valueOf(editTextChat.getText()), mensagemFormatada);

            // Limpa o campo de mensagem após o envio
            editTextMensagem.setText("");
        }
    }

    private void exibirMensagemNoChat(String mensagem) {
        // Exiba a mensagem no layout do chat
        TextView textViewMensagem = new TextView(this);
        textViewMensagem.setText(mensagem);

        // Adicione a TextView ao contêiner de chat
        chatContainer.addView(textViewMensagem);

        // Role o ScrollView para a parte inferior para exibir as mensagens mais recentes
        final ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
