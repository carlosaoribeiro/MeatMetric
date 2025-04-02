package com.carlosribeiro.meatmetric.ui.recuperar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.db.AppDatabase;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextNovaSenha, editTextConfirmarSenha;
    private Button buttonRedefinirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        editTextEmail = findViewById(R.id.editTextEmailRecuperacao);
        editTextNovaSenha = findViewById(R.id.editTextNovaSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarNovaSenha);
        buttonRedefinirSenha = findViewById(R.id.buttonRedefinirSenha);

        buttonRedefinirSenha.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String novaSenha = editTextNovaSenha.getText().toString();
            String confirmarSenha = editTextConfirmarSenha.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(novaSenha) || TextUtils.isEmpty(confirmarSenha)) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!novaSenha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            if (db.usuarioDao().emailJaCadastrado(email) == 0) {
                Toast.makeText(this, "E-mail não encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            db.usuarioDao().atualizarSenha(email, novaSenha);
            Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
