package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;

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

        criarContaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString();
                String confirmarSenha = confirmarSenhaEditText.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(CadastroActivity.this, "Digite um email válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(senha)) {
                    Toast.makeText(CadastroActivity.this, "Digite uma senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!senha.equals(confirmarSenha)) {
                    Toast.makeText(CadastroActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aqui você pode salvar o usuário com DAO, Firebase, etc
                Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Vai para tela de login
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
