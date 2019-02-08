package com.example.ricardopazdemiquel.plataformanur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login2 extends AppCompatActivity {
    private Button btn_login;
    private TextInputEditText et_registro;
    private TextInputEditText et_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btn_login = findViewById(R.id.btn_login);
        et_registro = findViewById(R.id.et_registro);
        et_pin = findViewById(R.id.et_pin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loguearse();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loguearse() throws JSONException {
        boolean valido = true;
        String registro = et_registro.getText().toString();
        String ping = et_pin.getText().toString();
        if (registro.length() <= 0) {
            et_registro.setBackgroundColor(Color.rgb(255, 194, 194));
            valido = false;
        } else {
            et_registro.setBackgroundColor(Color.rgb(255, 255, 255));
        }
        if (ping.length() <= 0) {
            et_pin.setBackgroundColor(Color.rgb(255, 194, 194));
            valido = false;
        } else {
            et_pin.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        if (valido) {
            login(registro, ping);
        }
    }

    public void login(String registro, String ping) throws JSONException {
        String url = getString(R.string.URL_service) + "Login";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            //jsonBody.put("Key", getString(R.string.KeyService));
            jsonBody.put("username", registro);
            jsonBody.put("password", ping);
            final ProgressDialog progreso = new ProgressDialog(this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando respuesta...");
            progreso.setCancelable(false);
            progreso.show();
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progreso.dismiss();
                    Log.i("LOG_VOLLEY", response);
                    if (response == null) {
                        Log.e("Call Service Nur:", "Error desconocido al realizar la peticion.");
                    } else if (response.length() < 1) {
                        Toast.makeText(Login2.this, "Registro o pin incorrecto.", Toast.LENGTH_LONG).show();
                    } else {

//                            JSONObject obj = new JSONObject(response);
                        response = response.replace('"', ' ').trim();
                        Preferences.setTokenAcceso(Login2.this, response);
                        Toast.makeText(Login2.this, "Login exitoso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login2.this, Carga.class);
                        startActivity(intent);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progreso.dismiss();
                    Log.e("LOG_VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
