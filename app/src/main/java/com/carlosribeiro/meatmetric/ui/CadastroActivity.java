package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.db.AppDatabase;
import com.carlosribeiro.meatmetric.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText emailEditText, senhaEditText, confirmarSenhaEditText;
    private Button criarContaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        emailEditText = findViewById(R.id.editTextEmailCadastro);
        senhaEditText = findViewById(R.id.editTextSenhaCadastro);
        confirmarSenhaEditText = findViewById(R.id.editTextConfirmarSenha);
        criarContaButton = findViewById(R.id.buttonCriarConta);

        criarContaButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String confirmarSenha = confirmarSenhaEditText.getText().toString().trim();

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Digite um email válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (senha.length() < 6) {
                Toast.makeText(this, "A senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(getApplicationContext());

            if (db.usuarioDao().emailJaCadastrado(email) > 0) {
                Toast.makeText(this, "Este e-mail já está cadastrado", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario novoUsuario = new Usuario(email, senha);
            db.usuarioDao().inserirUsuario(novoUsuario);

            Toast.makeText(this, "Conta criada com sucesso! Faça login para continuar.", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
