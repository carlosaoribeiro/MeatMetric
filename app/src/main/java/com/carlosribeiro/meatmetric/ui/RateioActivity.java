package com.carlosribeiro.meatmetric.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;

public class RateioActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1001;

    private EditText editTextValorTotal, editTextPagantes;
    private TextView textValorPorPessoa;
    private Button buttonAnexarImagem, buttonCompartilhar;
    private ImageView imagePreview;

    private Uri imagemUriSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateio);

        // View binds
        editTextValorTotal = findViewById(R.id.editTextValorTotal);
        editTextPagantes = findViewById(R.id.editTextPagantes);
        textValorPorPessoa = findViewById(R.id.textValorPorPessoa);
        buttonAnexarImagem = findViewById(R.id.buttonAnexarImagem);
        buttonCompartilhar = findViewById(R.id.buttonCompartilharRateio);
        imagePreview = findViewById(R.id.imagePreview);

        // Atualiza rateio em tempo real
        TextWatcher rateioWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularRateio();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        editTextValorTotal.addTextChangedListener(rateioWatcher);
        editTextPagantes.addTextChangedListener(rateioWatcher);

        // Botão para anexar imagem
        buttonAnexarImagem.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickIntent, REQUEST_IMAGE_PICK);
        });

        // Botão para compartilhar
        buttonCompartilhar.setOnClickListener(v -> {
            String valorStr = editTextValorTotal.getText().toString().trim();
            String pagantesStr = editTextPagantes.getText().toString().trim();

            if (valorStr.isEmpty() || pagantesStr.isEmpty()) {
                Toast.makeText(this, "Informe todos os dados para o rateio", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double valorTotal = Double.parseDouble(valorStr);
                int pagantes = Integer.parseInt(pagantesStr);

                if (valorTotal <= 0 || pagantes <= 0) {
                    Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valorPorPessoa = valorTotal / pagantes;

                String texto = "💰 Rateio do Churrasco com MeatMetric 🔥\n\n" +
                        "🧾 Valor total: R$ " + String.format("%.2f", valorTotal) + "\n" +
                        "👥 Pagantes: " + pagantes + "\n" +
                        "💸 Cada um paga: R$ " + String.format("%.2f", valorPorPessoa) + "\n\n" +
                        "Evite confusão! Compartilhe com MeatMetric 📱";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType(imagemUriSelecionada != null ? "image/*" : "text/plain");

                if (imagemUriSelecionada != null) {
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imagemUriSelecionada);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                shareIntent.putExtra(Intent.EXTRA_TEXT, texto);
                startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Erro ao processar os valores", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calcularRateio() {
        String valorTotalStr = editTextValorTotal.getText().toString().trim();
        String pagantesStr = editTextPagantes.getText().toString().trim();

        if (!valorTotalStr.isEmpty() && !pagantesStr.isEmpty()) {
            try {
                double total = Double.parseDouble(valorTotalStr);
                int pagantes = Integer.parseInt(pagantesStr);

                if (pagantes > 0) {
                    double valorPorPessoa = total / pagantes;
                    textValorPorPessoa.setText(String.format("Valor por pessoa: R$ %.2f", valorPorPessoa));
                } else {
                    textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
                }
            } catch (NumberFormatException e) {
                textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
            }
        } else {
            textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            imagemUriSelecionada = data.getData();
            imagePreview.setImageURI(imagemUriSelecionada);
            imagePreview.setVisibility(ImageView.VISIBLE);
        }
    }
}
