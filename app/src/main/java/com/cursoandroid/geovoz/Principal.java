package com.cursoandroid.geovoz;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Principal extends AppCompatActivity {

    private ImageView imageCapital;
    private Random random;
    private TextView txtResultado;
    private Button botao;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int RECOGNIZER_REQUEST_CODE = 100;
    private long lastClickTime = 0;
    private boolean isListening = false;
    private int index = 0;
    private final List<String> capitais = Arrays.asList(
            "Rio Branco", "Maceió", "Macapá", "Manaus", "Salvador", "Fortaleza",
             "Vitória", "Goiânia", "São Luís", "Cuiabá", "Campo Grande",
            "Belo Horizonte", "Belém", "João Pessoa", "Curitiba", "Recife", "Teresina",
            "Rio de Janeiro", "Natal", "Porto Alegre", "Porto Velho", "Boa Vista",
            "Florianópolis", "São Paulo", "Aracaju", "Palmas"
    );
    private final List<String> estados = new ArrayList<>(
            Arrays.asList(
                    "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Espírito Santo",
                    "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais",
                    "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
                    "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima",
                    "Santa Catarina", "São Paulo", "Sergipe", "Tocantins"
            )
    );
    private final List<Integer> imagens = Arrays.asList(
            R.drawable.acre, R.drawable.alagoas, R.drawable.amapa, R.drawable.amazonas,
            R.drawable.bahia, R.drawable.ceara,
            R.drawable.espirito_santo, R.drawable.goias, R.drawable.maranhao,
            R.drawable.mato_grosso, R.drawable.mato_grosso_do_sul,
            R.drawable.minas_gerais, R.drawable.para, R.drawable.paraiba,
            R.drawable.parana, R.drawable.pernambuco, R.drawable.piaui,
            R.drawable.rio_de_janeiro, R.drawable.rio_grande_do_norte,
            R.drawable.rio_grande_do_sul, R.drawable.rondonia, R.drawable.roraima,
            R.drawable.santa_catarina, R.drawable.sao_paulo, R.drawable.sergipe,
            R.drawable.tocantins
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtResultado = findViewById(R.id.textEstado);
        botao = findViewById(R.id.btnMicrofone);
        imageCapital = findViewById(R.id.imageCapital);
        random = new Random();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }

        carregarNovaCapital();
        botao.setOnClickListener(v -> verificarCliqueDuplo());
    }

    private void verificarCliqueDuplo() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 500) {
            pararReconhecimento();
        } else {
            iniciarReconhecimento();
        }
        lastClickTime = currentTime;
    }

    private void iniciarReconhecimento() {
        if (!isListening) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");


            try {
                startActivityForResult(intent, RECOGNIZER_REQUEST_CODE);
                isListening = true;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Reconhecimento de fala não suportado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pararReconhecimento() {
        if (isListening) {
            isListening = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECOGNIZER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                if(result.get(0).equalsIgnoreCase(capitais.get(index))) {
                    carregarNovaCapital();
                }
            }
            isListening = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void carregarNovaCapital() {
        index = random.nextInt(capitais.size());
        txtResultado.setText(estados.get(index));
        imageCapital.setImageDrawable(getDrawable(imagens.get(index)));
    }
}
