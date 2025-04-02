package com.carlosribeiro.meatmetric.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.carlosribeiro.meatmetric.R;

import java.util.Locale;

public class RateioActivity extends AppCompatActivity {

    private EditText editTextValorTotal, editTextPagantes;
    private TextView textValorPorPessoa;
    private Button buttonAnexarImagem, buttonCompartilhar;
    private ImageView imagePreview;

    private Uri imagemUriSelecionada = null;
    private Uri cameraImageUri;

    private boolean isUpdating = false;
    private int totalPessoas = 100; // valor padrão, pode ser sobrescrito via Intent

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                    imagemUriSelecionada = cameraImageUri;
                    imagePreview.setImageURI(imagemUriSelecionada);
                    imagePreview.setVisibility(ImageView.VISIBLE);
                }
            });

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imagemUriSelecionada = result.getData().getData();
                    imagePreview.setImageURI(imagemUriSelecionada);
                    imagePreview.setVisibility(ImageView.VISIBLE);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateio);

        solicitarPermissoes();

        totalPessoas = getIntent().getIntExtra("numeroPessoas", 100);

        editTextValorTotal = findViewById(R.id.editTextValorTotal);
        editTextPagantes = findViewById(R.id.editTextPagantes);
        textValorPorPessoa = findViewById(R.id.textValorPorPessoa);
        buttonAnexarImagem = findViewById(R.id.buttonAnexarImagem);
        buttonCompartilhar = findViewById(R.id.buttonCompartilharRateio);
        imagePreview = findViewById(R.id.imagePreview);

        aplicarMascaraMonetaria(editTextValorTotal);

        editTextPagantes.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularRateio();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        buttonAnexarImagem.setOnClickListener(v -> mostrarDialogEscolhaImagem());
        buttonCompartilhar.setOnClickListener(v -> compartilharRateio());
    }

    private void solicitarPermissoes() {
        String[] permissoes = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        };

        for (String permissao : permissoes) {
            if (ContextCompat.checkSelfPermission(this, permissao) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissoes, 1);
                break;
            }
        }
    }

    private void aplicarMascaraMonetaria(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private String valorAnterior = "";

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (isUpdating) return;

                isUpdating = true;

                String textoAtual = editable.toString();

                if (textoAtual.equals(valorAnterior)) {
                    isUpdating = false;
                    return;
                }

                String somenteNumeros = textoAtual.replaceAll("[^\\d]", "");

                if (!somenteNumeros.isEmpty()) {
                    long valorEmCentavos = Long.parseLong(somenteNumeros);

                    if (valorEmCentavos > 500000) {
                        Toast.makeText(RateioActivity.this, "Valor máximo permitido: R$ 5.000,00", Toast.LENGTH_SHORT).show();
                        editText.setText(valorAnterior);
                        editText.setSelection(valorAnterior.length());
                        isUpdating = false;
                        return;
                    }

                    double valor = valorEmCentavos / 100.0;
                    String valorFormatado = String.format(Locale.getDefault(), "R$ %,.2f", valor);

                    valorAnterior = valorFormatado;
                    editText.setText(valorFormatado);
                    editText.setSelection(valorFormatado.length());
                }

                isUpdating = false;
                calcularRateio();
            }
        });
    }

    private void calcularRateio() {
        String valorTotalStr = editTextValorTotal.getText().toString().replaceAll("[^\\d]", "");
        String pagantesStr = editTextPagantes.getText().toString().trim();

        if (!valorTotalStr.isEmpty() && !pagantesStr.isEmpty()) {
            try {
                double total = Double.parseDouble(valorTotalStr) / 100.0;

                if (total > 5000) {
                    Toast.makeText(this, "Valor máximo permitido é R$ 5.000,00", Toast.LENGTH_SHORT).show();
                    textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
                    return;
                }

                int pagantes = Integer.parseInt(pagantesStr);
                if (pagantes <= 0 || pagantes > 100) {
                    Toast.makeText(this, "O número de pagantes deve ser entre 1 e 100", Toast.LENGTH_SHORT).show();
                    textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
                    return;
                }

                double valorPorPessoa = total / pagantes;
                textValorPorPessoa.setText(String.format("Valor por pessoa: R$ %.2f", valorPorPessoa));
            } catch (NumberFormatException e) {
                textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
            }
        } else {
            textValorPorPessoa.setText("Valor por pessoa: R$ 0,00");
        }
    }

    private void compartilharRateio() {
        String valorStr = editTextValorTotal.getText().toString().replaceAll("[^\\d]", "");
        String pagantesStr = editTextPagantes.getText().toString().trim();

        if (valorStr.isEmpty() || pagantesStr.isEmpty()) {
            Toast.makeText(this, "Informe todos os dados para o rateio", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valorTotal = Double.parseDouble(valorStr) / 100.0;
            int pagantes = Integer.parseInt(pagantesStr);
            if (valorTotal <= 0 || pagantes <= 0 || pagantes > totalPessoas) {
                Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
                return;
            }

            double valorPorPessoa = valorTotal / pagantes;

            String texto = "\uD83D\uDCB0 Rateio do Churrasco com MeatMetric \uD83D\uDD25\n\n" +
                    "\uD83D\uDCDD Valor total: R$ " + String.format("%.2f", valorTotal) + "\n" +
                    "\uD83D\uDC65 Pagantes: " + pagantes + "\n" +
                    "\uD83D\uDCB8 Cada um paga: R$ " + String.format("%.2f", valorPorPessoa) + "\n\n" +
                    "Evite confusão! Compartilhe com MeatMetric \uD83D\uDCF1";

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
    }

    private void mostrarDialogEscolhaImagem() {
        String[] opcoes = {"Tirar Foto", "Escolher da Galeria"};

        new AlertDialog.Builder(this)
                .setTitle("Anexar imagem do ticket")
                .setItems(opcoes, (dialog, which) -> {
                    if (which == 0) {
                        abrirCamera();
                    } else {
                        abrirGaleria();
                    }
                })
                .show();
    }

    private void abrirCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Ticket MeatMetric");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem capturada pela câmera");
        cameraImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        cameraLauncher.launch(cameraIntent);
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }
}

