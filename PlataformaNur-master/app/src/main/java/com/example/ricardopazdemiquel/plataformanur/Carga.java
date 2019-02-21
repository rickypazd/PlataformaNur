package com.example.ricardopazdemiquel.plataformanur;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Carga extends AppCompatActivity {

    private JSONObject usr_log;
    private String Token_Acceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        usr_log = Preferences.getUsr_log(this);
        Token_Acceso = Preferences.getTokenAcceso(this);
        ejecutar();
    }

    public void ejecutar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Token_Acceso == null) {
                    Intent intent = new Intent(Carga.this, Login2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Carga.this, TabBarActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //usr logueado
                }

            }
        }, 3000);
    }
}