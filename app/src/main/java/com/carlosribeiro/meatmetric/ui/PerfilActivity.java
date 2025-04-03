package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.util.SessaoManager;

public class PerfilActivity extends AppCompatActivity {

    private TextView textNomeUsuario;
    private TextView textEmailUsuario;
    private TextView textMembroDesde;
    private ImageView imageAvatar;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializa os componentes da UI
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        textEmailUsuario = findViewById(R.id.textEmailUsuario);
        textMembroDesde = findViewById(R.id.textMembroDesde);
        imageAvatar = findViewById(R.id.imageAvatar);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Recupera dados da sessão
        String email = SessaoManager.getUsuarioLogado(this);
        String nome = SessaoManager.getNomeUsuario(this);
        String dataCriacao = SessaoManager.getDataCriacao(this);

        // Define os textos na tela
        if (nome != null) {
            textNomeUsuario.setText("Nome: " + nome);
        } else {
            textNomeUsuario.setText("Nome não disponível");
        }

        if (email != null) {
            textEmailUsuario.setText(email);
        } else {
            textEmailUsuario.setText("Email não disponível");
        }

        if (dataCriacao != null) {
            textMembroDesde.setText("Membro desde: " + dataCriacao);
        } else {
            textMembroDesde.setText("Membro desde: --/--/----");
        }

        // Botão de logout
        buttonLogout.setOnClickListener(v -> {
            SessaoManager.logout(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
