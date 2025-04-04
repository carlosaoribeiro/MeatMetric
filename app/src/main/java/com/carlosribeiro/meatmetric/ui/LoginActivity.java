package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
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
    private TextView esqueceuSenhaText, textCadastrar;

    private boolean senhaVisivel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        senhaEditText = findViewById(R.id.editTextSenha);
        entrarButton = findViewById(R.id.buttonEntrar);
        esqueceuSenhaText = findViewById(R.id.textEsqueceuSenha);
        textCadastrar = findViewById(R.id.textCadastrar);

        // 🔐 Alternar visibilidade da senha ao clicar no drawable (olhinho)
        senhaEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Verifica se o drawable (ícone) existe
                if (senhaEditText.getCompoundDrawables()[DRAWABLE_END] != null) {
                    int drawableWidth = senhaEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width();
                    if (event.getRawX() >= (senhaEditText.getRight() - drawableWidth)) {
                        senhaVisivel = !senhaVisivel;

                        if (senhaVisivel) {
                            senhaEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            senhaEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                        } else {
                            senhaEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            senhaEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
                        }

                        // Mantém o cursor no final
                        senhaEditText.setSelection(senhaEditText.getText().length());
                        return true;
                    }
                }
            }
            return false;
        });

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
                SessaoManager.salvarUsuarioLogado(
                        this,
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCreatedAt()
                );

                startActivity(new Intent(this, ParametrosActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
            }
        });

        esqueceuSenhaText.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperarSenhaActivity.class));
        });

        textCadastrar.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
