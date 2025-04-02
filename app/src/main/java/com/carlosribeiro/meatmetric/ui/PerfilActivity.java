package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.util.SessaoManager;

public class PerfilActivity extends AppCompatActivity {

    private TextView textEmailUsuario;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        textEmailUsuario = findViewById(R.id.textEmailUsuario);
        buttonLogout = findViewById(R.id.buttonLogout);

        String email = SessaoManager.getUsuarioLogado(this);

        if (email != null) {
            textEmailUsuario.setText("Email:" + email);
        } else {
            textEmailUsuario.setText("Usuário não identificado");
        }

        buttonLogout.setOnClickListener(v -> {
            SessaoManager.logout(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
