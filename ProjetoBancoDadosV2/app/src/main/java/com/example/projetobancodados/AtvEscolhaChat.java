package com.example.projetobancodados;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetobancodados.dao.Conexao;
import com.example.projetobancodados.util.ChatManager;

public class AtvEscolhaChat extends AppCompatActivity {

    private EditText editTextChat;
    private Button btnIniciarChat;
    private LinearLayout chatContainer;
    private EditText editTextMensagem;
    private Button btnEnviarMensagem;
    private ChatManager chatManager;
    private Spinner statusSpinner; // Mova a declaração para o nível de classe

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
        Button btnEnviarStatus = findViewById(R.id.btnEnviarStatus);
        statusSpinner = findViewById(R.id.statusSpinner);
        Button btnVoltar = findViewById(R.id.btnVoltar);


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
        btnEnviarStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarStatus();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtvEscolhaChat.this, AtvPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void iniciarChat() {
        String userId = editTextChat.getText().toString().trim();

        if (!userId.isEmpty()) {

            String tabelaChat = "chat_" + userId;
            // Adicione um log para rastrear a tabela que o chat do usuário está abrindo
            Log.d("AtvEscolhaChat", "Chat do admin abriu a tabela: " + tabelaChat);

            // Verifique se a tabela de chat existe antes de tentar acessá-la
            if (verificarSeTabelaChatExiste(userId)) {
                String mensagensDoChat = obterMensagensDoChat(userId);
                exibirMensagensNoChat(mensagensDoChat);
            } else {
                exibirMensagem("Nenhum usuário cadastrado com este ID");
            }
        } else {
            // Trate o caso em que o ID do usuário está vazio
        }
    }

    private boolean verificarSeTabelaChatExiste(String userId) {
        // Obtenha a instância do banco de dados
        Conexao conexao = new Conexao(this);
        SQLiteDatabase db = conexao.getReadableDatabase();

        // Verifique se a tabela "chat_<userId>" existe
        String tabelaChat = "chat_" + userId;
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tabelaChat});

        boolean tabelaExiste = cursor.getCount() > 0;

        // Feche o Cursor e a conexão com o banco de dados
        cursor.close();
        conexao.close();

        return tabelaExiste;
    }

    private void exibirMensagem(String mensagem) {
        // Aqui você pode exibir a mensagem da forma desejada, por exemplo, usando um AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.create().show();
    }


    private String obterMensagensDoChat(String userId) {
        String tabelaChat = "chat_" + userId;
        // Obtenha as mensagens do chat usando o ChatManager
        Cursor cursor = chatManager.obterMensagensDoChat(tabelaChat);

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
        // Limpe todas as mensagens existentes no layout do chat
        chatContainer.removeAllViews();

        // Exiba as novas mensagens no layout do chat
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

            String tabelaChatAdmin = String.valueOf(editTextChat.getText());
            Log.d("AtvEscolhaChat", "Mensagem do admin salva na tabela: " + tabelaChatAdmin);
            // Adicione a mensagem ao layout de mensagens
            String mensagemFormatada = "admin: " + mensagem;

            // Exibe a mensagem formatada no chat
            exibirMensagemNoChat(mensagemFormatada);

            // Salva a mensagem formatada no banco de dados
            chatManager.salvarMensagem("chat_"+String.valueOf(editTextChat.getText()), mensagemFormatada);

            // Limpa o campo de mensagem após o envio
            editTextMensagem.setText("");
        }
    }

    private void enviarStatus() {
        // Obter o item selecionado do Spinner
        String statusSelecionado = (String) statusSpinner.getSelectedItem();

        if (statusSelecionado != null && !statusSelecionado.isEmpty()) {
            // Adicionar a mensagem ao layout de mensagens
            String mensagemFormatada = "STATUS: " + statusSelecionado;
            exibirMensagemNoChat(mensagemFormatada);

            // Salvar a mensagem no banco de dados
            String tabelaChatAdmin = String.valueOf(editTextChat.getText());
            chatManager.salvarMensagem("chat_" + tabelaChatAdmin, mensagemFormatada);

            // Limpar o campo de mensagem após o envio
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
