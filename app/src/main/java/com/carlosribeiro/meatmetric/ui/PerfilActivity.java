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
        String nome = SessaoManager.getNomeUsuario(this);
        String email = SessaoManager.getUsuarioLogado(this);
        String dataCriacao = SessaoManager.getDataCriacaoFormatada(this);

        // Aplica os dados
        textNomeUsuario.setText(nome != null ? "👤 " + nome : "Nome não disponível");
        textEmailUsuario.setText(email != null ? "📧 " + email : "Email não disponível");
        textMembroDesde.setText(dataCriacao != null ? "📅 Membro desde: " + dataCriacao : "📅 Membro desde: --/--/----");

        // Logout
        buttonLogout.setOnClickListener(v -> {
            SessaoManager.logout(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
