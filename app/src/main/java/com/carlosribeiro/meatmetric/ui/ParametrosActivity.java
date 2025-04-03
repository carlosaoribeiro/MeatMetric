package com.carlosribeiro.meatmetric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlosribeiro.meatmetric.R;
import com.carlosribeiro.meatmetric.model.Churrasco;
import com.carlosribeiro.meatmetric.util.CalculoUtil;

public class ParametrosActivity extends AppCompatActivity {

    private RadioGroup tipoRadioGroup;
    private EditText pessoasEditText;
    private Button calcularButton, buttonPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);

        tipoRadioGroup = findViewById(R.id.radioGroupTipo);
        pessoasEditText = findViewById(R.id.editTextPessoas);
        calcularButton = findViewById(R.id.buttonCalcular);
        buttonPerfil = findViewById(R.id.buttonPerfil);

        buttonPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, PerfilActivity.class));
        });

        calcularButton.setOnClickListener(v -> {
            int selectedId = tipoRadioGroup.getCheckedRadioButtonId();
            String pessoasStr = pessoasEditText.getText().toString().trim();

            if (selectedId == -1) {
                Toast.makeText(this, "Selecione o tipo de churrasco", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pessoasStr)) {
                Toast.makeText(this, "Informe o número de pessoas", Toast.LENGTH_SHORT).show();
                return;
            }

            int numeroPessoas;
            try {
                numeroPessoas = Integer.parseInt(pessoasStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numeroPessoas <= 0) {
                Toast.makeText(this, "Informe um número válido de pessoas", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numeroPessoas > 100) {
                Toast.makeText(this, "Número máximo permitido é 100 pessoas", Toast.LENGTH_SHORT).show();
                return;
            }

            Churrasco.TipoChurrasco tipo = (selectedId == R.id.radioSomenteCarnes)
                    ? Churrasco.TipoChurrasco.SOMENTE_CARNES
                    : Churrasco.TipoChurrasco.CARNES_E_GUARNICOES;

            Churrasco churrasco = CalculoUtil.calcularChurrasco(tipo, numeroPessoas);

            Intent intent = new Intent(this, ResultadoActivity.class);
            intent.putExtra("totalKg", churrasco.getTotalCarneKg());
            intent.putExtra("bovino", churrasco.getBovino());
            intent.putExtra("frango", churrasco.getFrango());
            intent.putExtra("linguica", churrasco.getLinguica());
            intent.putExtra("suíno", churrasco.getSuino());
            startActivity(intent);
        });
    }
}
