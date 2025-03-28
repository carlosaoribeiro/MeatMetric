package com.carlosribeiro.meatmetric.ui.recuperar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button enviarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        emailEditText = findViewById(R.id.editTextEmailRecuperacao);
        enviarButton = findViewById(R.id.buttonEnviarRecuperacao);

        enviarButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aqui você poderia enviar o email (Firebase/Auth/etc.)
            Toast.makeText(this, "Link de recuperação enviado!", Toast.LENGTH_LONG).show();
        });
    }
}
