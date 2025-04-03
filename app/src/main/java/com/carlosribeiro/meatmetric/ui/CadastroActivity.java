package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.db.AppDatabase;
import com.carlosribeiro.meatmetric.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, senhaEditText, confirmarSenhaEditText;
    private Button criarContaButton;

    private boolean senhaVisivel = false;
    private boolean confirmarVisivel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = findViewById(R.id.editTextNome);
        emailEditText = findViewById(R.id.editTextEmailCadastro);
        senhaEditText = findViewById(R.id.editTextSenhaCadastro);
        confirmarSenhaEditText = findViewById(R.id.editTextConfirmarSenha);
        criarContaButton = findViewById(R.id.buttonCriarConta);

        // 👁️ Mostrar/ocultar senha
        setupTogglePasswordVisibility(senhaEditText, true);
        setupTogglePasswordVisibility(confirmarSenhaEditText, false);

        criarContaButton.setOnClickListener(v -> {
            String nome = nomeEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String confirmarSenha = confirmarSenhaEditText.getText().toString().trim();

            if (TextUtils.isEmpty(nome)) {
                Toast.makeText(this, "Digite seu nome completo", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
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

            Usuario novoUsuario = new Usuario(nome, email, senha);
            db.usuarioDao().inserirUsuario(novoUsuario);

            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void setupTogglePasswordVisibility(EditText editText, boolean isSenhaPrincipal) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    if (isSenhaPrincipal) {
                        senhaVisivel = !senhaVisivel;
                        editText.setInputType(senhaVisivel ?
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                senhaVisivel ? R.drawable.ic_eye_open : R.drawable.ic_eye_closed, 0);
                    } else {
                        confirmarVisivel = !confirmarVisivel;
                        editText.setInputType(confirmarVisivel ?
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                confirmarVisivel ? R.drawable.ic_eye_open : R.drawable.ic_eye_closed, 0);
                    }

                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
