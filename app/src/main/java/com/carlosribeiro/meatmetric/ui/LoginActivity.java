package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.db.AppDatabase;
import com.carlosribeiro.meatmetric.model.Usuario;
import com.carlosribeiro.meatmetric.ui.recuperar.RecuperarSenhaActivity;
import com.carlosribeiro.meatmetric.util.SessaoManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, senhaEditText;
    private Button entrarButton;
    private TextView esqueceuSenhaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        senhaEditText = findViewById(R.id.editTextSenha);
        entrarButton = findViewById(R.id.buttonEntrar);
        esqueceuSenhaText = findViewById(R.id.textEsqueceuSenha);

        entrarButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(senha)) {
                Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Usuario usuario = db.usuarioDao().buscarUsuario(email, senha);

            if (usuario != null) {
                SessaoManager.salvarDadosUsuario(this, email);
                startActivity(new Intent(this, ParametrosActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
            }
        });

        esqueceuSenhaText.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperarSenhaActivity.class));
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
