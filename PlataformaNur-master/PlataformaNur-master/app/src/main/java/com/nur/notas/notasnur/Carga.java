package com.nur.notas.notasnur;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.nur.notas.notasnur.utiles.Preferences;

public class Carga extends AppCompatActivity {

    private String Token_Acceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        Token_Acceso = Preferences.getTokenAcceso(this);
        ejecutar();
    }

    public void ejecutar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (Token_Acceso == null) {
                    intent = new Intent(Carga.this, Login.class);
                } else {
                    intent = new Intent(Carga.this, TabBarActivity.class);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
            }
        }, 0);
    }
}