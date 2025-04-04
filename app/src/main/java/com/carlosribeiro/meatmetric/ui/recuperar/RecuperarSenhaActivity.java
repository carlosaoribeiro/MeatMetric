package com.carlosribeiro.meatmetric.ui.recuperar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.carlosribeiro.meatmetric.R;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText editTextEmailRecuperacao;
    private EditText editTextNovaSenha, editTextConfirmarNovaSenha;
    private Button buttonRedefinirSenha;

    private boolean senhaVisivelNova = false;
    private boolean senhaVisivelConfirmar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        // Referências
        editTextEmailRecuperacao = findViewById(R.id.editTextEmailRecuperacao);
        editTextNovaSenha = findViewById(R.id.editTextNovaSenha);
        editTextConfirmarNovaSenha = findViewById(R.id.editTextConfirmarNovaSenha);
        buttonRedefinirSenha = findViewById(R.id.buttonRedefinirSenha);

        // Clique do botão
        buttonRedefinirSenha.setOnClickListener(v -> validarCampos());

        // Olhinho Nova Senha
        editTextNovaSenha.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextNovaSenha.getRight() - editTextNovaSenha.getCompoundDrawables()[2].getBounds().width())) {
                    senhaVisivelNova = !senhaVisivelNova;
                    atualizarVisibilidadeSenha(editTextNovaSenha, senhaVisivelNova);
                    return true;
                }
            }
            return false;
        });

        // Olhinho Confirmar Senha
        editTextConfirmarNovaSenha.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextConfirmarNovaSenha.getRight() - editTextConfirmarNovaSenha.getCompoundDrawables()[2].getBounds().width())) {
                    senhaVisivelConfirmar = !senhaVisivelConfirmar;
                    atualizarVisibilidadeSenha(editTextConfirmarNovaSenha, senhaVisivelConfirmar);
                    return true;
                }
            }
            return false;
        });
    }

    private void atualizarVisibilidadeSenha(EditText campo, boolean visivel) {
        int selection = campo.getSelectionEnd();
        if (visivel) {
            campo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            campo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
        } else {
            campo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            campo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
        }
        campo.setSelection(selection); // mantém o cursor no lugar certo
    }

    private void validarCampos() {
        String email = editTextEmailRecuperacao.getText().toString().trim();
        String novaSenha = editTextNovaSenha.getText().toString();
        String confirmarSenha = editTextConfirmarNovaSenha.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(novaSenha) || TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Preencha todos os campos de senha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (novaSenha.length() < 6) {
            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_LONG).show();
        finish();
    }
}
