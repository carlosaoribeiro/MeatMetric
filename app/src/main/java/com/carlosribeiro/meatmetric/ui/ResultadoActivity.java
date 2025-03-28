package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.ui.ParametrosActivity;
import com.carlosribeiro.meatmetric.ui.RateioActivity;

public class ResultadoActivity extends AppCompatActivity {

    private TextView textTotalKg, textBovino, textFrango, textLinguica, textPorco;
    private Button buttonNovoCalculo, buttonCompartilhar, buttonRateio;

    private double totalKg, bovino, frango, linguica, porco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Bind views
        textTotalKg = findViewById(R.id.textTotalKg);
        textBovino = findViewById(R.id.textBovino);
        textFrango = findViewById(R.id.textFrango);
        textLinguica = findViewById(R.id.textLinguica);
        textPorco = findViewById(R.id.textPorco);
        buttonNovoCalculo = findViewById(R.id.buttonNovoCalculo);
        buttonCompartilhar = findViewById(R.id.buttonCompartilhar);
        buttonRateio = findViewById(R.id.buttonRateio);

        // Recebe dados da Intent
        totalKg = getIntent().getDoubleExtra("totalKg", 0.0);
        bovino = getIntent().getDoubleExtra("bovino", 0.0);
        frango = getIntent().getDoubleExtra("frango", 0.0);
        linguica = getIntent().getDoubleExtra("linguica", 0.0);
        porco = getIntent().getDoubleExtra("porco", 0.0);

        // Exibe na tela
        textTotalKg.setText(String.format("Total: %.2f kg", totalKg));
        textBovino.setText(String.format("Bovino: %.2f kg", bovino));
        textFrango.setText(String.format("Frango: %.2f kg", frango));
        textLinguica.setText(String.format("Linguiça: %.2f kg", linguica));
        textPorco.setText(String.format("Porco: %.2f kg", porco));

        // Botão de novo cálculo
        buttonNovoCalculo.setOnClickListener(v -> {
            Intent intent = new Intent(ResultadoActivity.this, ParametrosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Botão de compartilhar
        buttonCompartilhar.setOnClickListener(v -> {
            String texto = "🔥🍖 MEATMETRIC — CHURRASCO NA MEDIDA CERTA 🍖🔥\n\n" +
                    "📊 Resultado do Planejamento:\n\n" +
                    "━━━━━━━━━━━━━━━━━━━\n" +
                    String.format("🔹 Total de carnes:     %.2f kg\n", totalKg) +
                    String.format("🐄 Bovino:              %.2f kg\n", bovino) +
                    String.format("🐔 Frango:              %.2f kg\n", frango) +
                    String.format("🌭 Linguiça:            %.2f kg\n", linguica) +
                    String.format("🐖 Porco:               %.2f kg\n", porco) +
                    "━━━━━━━━━━━━━━━━━━━\n\n" +
                    "✅ Evite desperdícios.\n" +
                    "✅ Calcule com inteligência.\n" +
                    "✅ Organize um churrasco impecável!\n\n" +
                    "📱 *Feito com MeatMetric*";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, texto);
            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
        });

        // Botão de rateio
        buttonRateio.setOnClickListener(v -> {
            Intent intent = new Intent(ResultadoActivity.this, RateioActivity.class);
            intent.putExtra("totalKg", totalKg); // Envia o total para rateio
            startActivity(intent);
        });
    }
}
