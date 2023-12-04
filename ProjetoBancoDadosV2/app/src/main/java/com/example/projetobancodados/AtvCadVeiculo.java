package com.example.projetobancodados;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetobancodados.dao.Conexao;
import com.example.projetobancodados.dao.UsuarioDao;
import com.example.projetobancodados.dao.VeiculoDao;
import com.example.projetobancodados.model.Veiculo;

public class AtvCadVeiculo extends AppCompatActivity implements View.OnClickListener {

    Button btnGravar, btnExcluir, btnVoltar;
    EditText edtCodigo, edtNome, edtPlaca, edtModeloAno, edtObs, edtTelefone, edtEmail;

    Veiculo veiculo;
    VeiculoDao veiculoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_cad_veiculo);

        btnGravar = findViewById(R.id.btnGravar);
        btnGravar.setOnClickListener(this);

        btnExcluir = findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(this);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        edtCodigo = findViewById(R.id.edtCodigo);
        edtNome = findViewById(R.id.edtNome);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtModeloAno = findViewById(R.id.edtModeloAno);
        edtObs = findViewById(R.id.edtObs);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);

        veiculoDao = new VeiculoDao(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            veiculo = (Veiculo) extras.getSerializable("obj");
        }

        if (veiculo == null) {
            // Se o objeto veiculo for nulo, crie um novo objeto
            veiculo = new Veiculo();
        }

        if (veiculo.getId() != null) {
            edtCodigo.setText(String.valueOf(veiculo.getId().longValue()));
        } else {
            edtCodigo.setText(""); // Defina o campo como vazio se o ID for nulo
        }

        edtNome.setText(veiculo.getNome());
        edtPlaca.setText(veiculo.getPlaca());
        edtModeloAno.setText(veiculo.getModeloAno());
        edtTelefone.setText(veiculo.getTelefone());
        edtEmail.setText(veiculo.getEmail());
        edtObs.setText(veiculo.getObservacao());
    }

    @Override
    public void onClick(View v) {
        if (v == btnVoltar) {
            finish();
        } else if (v == btnExcluir) {
            if (veiculo != null && veiculo.getId() != null) {
                long id = veiculoDao.excluir(veiculo);
                Toast.makeText(this, "Veículo excluído com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Tratar o caso em que veiculo ou veiculo.getId() é nulo
                Toast.makeText(this, "Erro ao excluir veículo", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnGravar) {
            if (veiculo == null) {
                veiculo = new Veiculo();
            }

            veiculo.setNome(edtNome.getText().toString());
            veiculo.setPlaca(edtPlaca.getText().toString());
            veiculo.setModeloAno(edtModeloAno.getText().toString());
            veiculo.setTelefone(edtTelefone.getText().toString());
            veiculo.setEmail(edtEmail.getText().toString());
            veiculo.setObservacao(edtObs.getText().toString());

            if (veiculo.getId() != null && veiculo.getId() != 0L) {
                // Alterar veículo
                long idVeiculo = veiculoDao.alterar(veiculo);
                Toast.makeText(this, "Veículo alterado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                // Inserir veículo
                long idVeiculo = veiculoDao.inserir(veiculo);
                Toast.makeText(this, "Veículo inserido com o id = " + idVeiculo, Toast.LENGTH_SHORT).show();
                // Após inserir o veículo, salva também o usuário
                salvarUsuario();
            }
            finish();
        }
    }

    private void salvarUsuario() {
        String login = veiculo.getPlaca();  // Use a placa como login
        String telefone = veiculo.getTelefone();  // Supondo que o telefone seja uma String
        String senha = telefone.substring(Math.max(0, telefone.length() - 4));  // Últimos 4 dígitos do telefone como senha

        UsuarioDao usuarioDao = new UsuarioDao(this);
        long idUsuario = usuarioDao.inserirUsuario(login, senha);
        usuarioDao.close();

        // Agora, crie a tabela de chat associada ao usuário
        criarTabelaChat(idUsuario);
    }

    private void criarTabelaChat(long idUsuario) {
        String nomeTabelaChat = "chat_" + idUsuario;
        String sqlCreateChat = "CREATE TABLE " + nomeTabelaChat + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "message TEXT, " +
                "timestamp INTEGER);";

        // Obtém a instância do banco de dados
        Conexao conexao = new Conexao(this);
        SQLiteDatabase db = conexao.getWritableDatabase();

        // Executa o SQL para criar a tabela de chat
        db.execSQL(sqlCreateChat);

        // Fecha a conexão com o banco de dados
        conexao.close();
    }
}
