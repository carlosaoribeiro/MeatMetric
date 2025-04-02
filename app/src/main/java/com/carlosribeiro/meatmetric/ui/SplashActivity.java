package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.util.SessaoManager;

public class SplashActivity extends AppCompatActivity {

    private static final int TEMPO_SPLASH = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (SessaoManager.isUsuarioLogado(SplashActivity.this)) {
                // Vai direto para a tela principal
                startActivity(new Intent(SplashActivity.this, ParametrosActivity.class));
            } else {
                // Vai para login
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, TEMPO_SPLASH);
    }
}
