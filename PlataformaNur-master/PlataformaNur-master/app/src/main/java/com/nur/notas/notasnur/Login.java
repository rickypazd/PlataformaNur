package com.nur.notas.notasnur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nur.notas.notasnur.objetos.AlumnoCarrera;
import com.nur.notas.notasnur.objetos.Periodo;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.MateriasDAO;
import com.nur.notas.notasnur.dao.MateriasOfertadasDAO;
import com.nur.notas.notasnur.dao.NotasDAO;
import com.nur.notas.notasnur.utiles.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity { // 915 -

    private TextInputEditText et_registro, et_pin;
    private ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = findViewById(R.id.btn_login);
        et_registro = findViewById(R.id.et_registro);
        et_pin = findViewById(R.id.et_pin);
        TextView tvIngresar = findViewById(R.id.tvIngresar);

        LinearLayout formContainer = findViewById(R.id.formContainer);

        Animation animFadein, animslideup;

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animslideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

        final AnimationSet s = new AnimationSet(true);
        s.setInterpolator(new AccelerateInterpolator());

        s.addAnimation(animslideup);
        s.addAnimation(animFadein);
        formContainer.startAnimation(s);

        Typeface fontSegoePrint = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        tvIngresar.setTypeface(fontSegoePrint);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearse();
            }
        });
    }

    public void loguearse() {
        boolean valido = true;
        String registro = et_registro.getText().toString().trim();
        String ping = et_pin.getText().toString().trim();
        if (registro.length() <= 0) {
            et_registro.setError("Debe introducir su número de registro");
            valido = false;
        }

        if (ping.length() <= 0) {
            et_pin.setError("Debe introducir su clave");
            valido = false;
        }

        if (valido) {
            login(registro, ping);
        }
    }

    public void login(final String registro, final String ping) {
        String url = getString(R.string.url_login);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", registro);
            jsonBody.put("password", ping);
            jsonBody.put("bloqueo", false); // TODO: quitar esto

            progreso = new ProgressDialog(this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Iniciando sesion...");
            progreso.setCancelable(false);
            progreso.show();

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.replace('"', ' ').trim();

                    if (response.equals("Bloqueo. Tiene deuda pendiente.")) {
                        Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();
                    } else {
                        Preferences.setTokenAcceso(Login.this, response);
                        Preferences.setRegistro(Login.this, registro);
                        Preferences.setPin(Login.this, ping);

                        obtenerPeriodos();
                        obtenerCarreras();
                        obtenerPeriodosOfertados();
                        obtenerPerfil();
                        obtenerFotoDePerfil();
                        obtenerLinks();
                        obtenerTokenFCM();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progreso.dismiss();

                    if (error instanceof NetworkError) {
                        Util.mostrarDialogoSinInternet(Login.this);
                    } else if (error instanceof TimeoutError) {

                    } else if (error instanceof ServerError) {
                        Toast.makeText(Login.this, "Crendenciales incorrectos", Toast.LENGTH_SHORT).show();
                    }

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

    /* SERVICIOS BASE */

    public void obtenerPeriodos() {
        String url = getString(R.string.url_periodos_cursados);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo periodos cursados...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray periodosJson = respuesta.getJSONArray("Data");

                            Preferences.setPeriodos(Login.this, periodosJson);

                            pasosCompletadosBase += 1;
                            verificarCompletadoBase();
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerCarreras() {
        String url = getString(R.string.url_carreras);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo carreras...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray listaCarreras = respuesta.getJSONArray("Data");
                            Preferences.setAlumnoCarreras(Login.this, listaCarreras);
                            /* la primera carrera seleccionada por defecto */
                            Preferences.setCarreraSeleccionada(Login.this, listaCarreras.getJSONObject(0));
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                        pasosCompletadosBase += 1;
                        verificarCompletadoBase();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerPerfil() {
        String url = getString(R.string.url_alumno_info);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo perfil...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONObject perfil = respuesta.getJSONObject("Data");

                            Preferences.setAlumno(Login.this, perfil);

                            pasosCompletadosBase += 1;
                            verificarCompletadoBase();
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                    Log.e("LOG_VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerFotoDePerfil() {
        String url = getString(R.string.url_foto_perfil);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo perfil...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            String imagenB64Str = respuesta.getString("Data");

                            Preferences.setAlumnoImagenStr(Login.this, imagenB64Str);

                            pasosCompletadosBase += 1;
                            verificarCompletadoBase();
                        } else {
                            Log.i("nur", "Status false en get alumno imagen");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                    Log.e("LOG_VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerPeriodosOfertados() {
        String url = getString(R.string.url_periodos_ofertados);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo ofertas...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray periodosJson = respuesta.getJSONArray("Data");

                            Preferences.setPeriodosOferta(Login.this, periodosJson);

                            pasosCompletadosBase += 1;
                            verificarCompletadoBase();

                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerTokenFCM() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("com.nur.notas.notasnur", "El token es: " + token);

        String url = getString(R.string.url_actualizar_token_FCM);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("SLLAVE", token);

            progreso.setTitle("Preparando tu dispositivo para obtener notificaciones...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {

                            pasosCompletadosBase += 1;
                            verificarCompletadoBase();
                        } else {
                            Log.i("nur", "Status false al actualizar el token");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    /* */

    final int PASOS_NECESARIOS_BASE = 7; /* carreras, periodos, perfil, imagen de perfil, periodos ofertados, enlaces, token de firebase */
    int pasosCompletadosBase = 0;

    int PASOS_NESARIOS_NOTAS = 0; /* carreras x periodos */
    int pasosCompletadosNotas = 0;

    int PASOS_NECESARIOS_HISTORIAL_ACADEMICO = 0;
    int pasosCompletadosHistorialAcademico = 0;

    public void verificarCompletadoBase() {
        if (pasosCompletadosBase == PASOS_NECESARIOS_BASE) {
            ArrayList<AlumnoCarrera> carreras = Preferences.getAlumnoCarreras(this);
            ArrayList<Periodo> periodos = Preferences.getPeriodos(this);
            ArrayList<Periodo> periodosOfertados = Preferences.getPeriodosOferta(this);

            PASOS_NESARIOS_NOTAS = carreras.size() * periodos.size();
            PASOS_NECESARIOS_OFERTAS = carreras.size() * periodosOfertados.size();
            PASOS_NECESARIOS_HISTORIAL_ACADEMICO = carreras.size();

            obtenerHistorialMateriasCursadas(carreras);
            obtenerNotasYOfertas(carreras, periodos, periodosOfertados);

//            for (int i = 0; i < carreras.size(); i++) {
//                AlumnoCarrera carrera = carreras.get(i);

                // obtenerHistorialMateriasCursadas(carrera.getLCARRERA_ID());
                // Log.i("nur", "obtenerHistorialMateriasCursadas(" + carrera.getLCARRERA_ID() +");");

                /* obtener notas */
//                for (int j = 0; j < periodos.size(); j++) {
//                    Periodo periodo = periodos.get(j);
//                    obtenerNotas(carrera.getLCARRERA_ID(), periodo.getLPERIODO_ID());
//                }

                /* obtener periodos ofertados*/
//                for (int j = 0; j < periodosOfertados.size(); j++) {
//                    Periodo periodo = periodosOfertados.get(j);
//                    obtenerMateriasOfertadas(carrera.getLCARRERA_ID(), periodo.getLPERIODO_ID());
//                }
//            }

            pasosCompletadosBase = 0;
        }
    }

    public void obtenerHistorialMateriasCursadas(final int carreraId) {
        String url = getString(R.string.url_historial_academico);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("pCarreraId", carreraId);

            progreso.setTitle("Obteniendo historial académico...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("com.example.ricardopaz", response);
                    try {
                        pensums.put(carreraId, new JSONObject(response));

                        pasosCompletadosHistorialAcademico += 1;
                        verificarCompletadoHistorialAcademico();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosHistorialAcademico += 1;
                    verificarCompletadoHistorialAcademico();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerHistorialMateriasCursadas(ArrayList<AlumnoCarrera> carreras) {
        String url = getString(R.string.url_historial_academico);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            for (AlumnoCarrera carrera: carreras) {
                JSONObject jsonBody = new JSONObject();

                final int carreraId = carrera.getLCARRERA_ID();
                jsonBody.put("pCarreraId", carreraId);

                progreso.setTitle("Obteniendo historial académico...");

                final String mRequestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);

                            if (respuesta.getBoolean("Status")) {
                                JSONObject datos = respuesta.getJSONObject("Data");

                                MateriasDAO materiaDao = FactoryDAO.getOrCreate().newMateriasDAO();

                                JSONArray materiasCursadas = datos.getJSONArray("CURSADAS");
                                JSONArray materiasFaltantes = datos.getJSONArray("FALTANTES");

                                materiaDao.insercionMasiva(carreraId, materiasCursadas);
                                materiaDao.insercionMasiva(carreraId, materiasFaltantes, true);
                            } else {
                                Log.i("nur", "Status false en get alumno historial " + carreraId);
                            }

                            pasosCompletadosHistorialAcademico += 1;
                            verificarCompletadoHistorialAcademico();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pasosCompletadosHistorialAcademico += 1;
                        verificarCompletadoHistorialAcademico();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                        return headers;
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    HashMap<Integer, JSONObject> pensums = new HashMap<>();

    public void verificarCompletadoHistorialAcademico() {
        if (pasosCompletadosHistorialAcademico == PASOS_NECESARIOS_HISTORIAL_ACADEMICO) {
//            try {
//                for (Map.Entry<Integer, JSONObject> entry : pensums.entrySet()) {
//                    int carreraId = entry.getKey();
//                    JSONObject respuesta = entry.getValue();
//
//                    if (respuesta.getBoolean("Status")) {
//                        JSONObject datos = respuesta.getJSONObject("Data");
//
//                        MateriasDAO materiaDao = FactoryDAO.getOrCreate().newMateriasDAO();
//
//                        JSONArray materiasCursadas = datos.getJSONArray("CURSADAS");
//                        JSONArray materiasFaltantes = datos.getJSONArray("FALTANTES");
//
//                        materiaDao.insercionMasiva(carreraId, materiasCursadas);
//                        materiaDao.insercionMasiva(carreraId, materiasFaltantes, true);
//                    } else {
//                        Log.i("nur", "Status false en get alumno historial " + carreraId);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            PASOS_NECESARIOS_HISTORIAL_ACADEMICO = 0;
            pasosCompletadosHistorialAcademico = 0;

            serviciosCompletados++;
            listo();
        }
    }

    public void obtenerNotas(final int carreraId, final int periodoId) {
        String url = getString(R.string.url_notas);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("pCarreraId", carreraId);
            jsonBody.put("pPeriodoId", periodoId);

            progreso.setTitle("Obteniendo notas...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray notasJson = respuesta.getJSONArray("Data");

                            NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();
                            dao.insercionMasiva(carreraId, periodoId, notasJson);
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                        pasosCompletadosNotas += 1;
                        verificarCompletadoNotas();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosNotas += 1;
                    verificarCompletadoNotas();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerNotasYOfertas(ArrayList<AlumnoCarrera> carreras, ArrayList<Periodo> periodos, ArrayList<Periodo> periodosOfertados) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            for (AlumnoCarrera carrera : carreras) {
                final int carreraId = carrera.getLCARRERA_ID();

                for (Periodo periodoOfertado : periodosOfertados) {
                    final int periodoId = periodoOfertado.getLPERIODO_ID();

                    String url = getString(R.string.url_materias_ofertadas);

                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("pCarreraId", carreraId);
                    jsonBody.put("pPeriodoId", periodoId);

                    progreso.setTitle("Obteniendo materias ofertadas...");

                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);

                                if (respuesta.getBoolean("Status")) {
                                    JSONArray ofertasJson = respuesta.getJSONArray("Data");

                                    MateriasOfertadasDAO ofertasDao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
                                    ofertasDao.insercionMasiva(carreraId, periodoId, ofertasJson);
                                } else {
                                    Log.i("nur", "Status false en get notas");
                                }

                                pasosCompletadosOfertas += 1;
                                verificarCompletadoOfertas();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());

                            pasosCompletadosOfertas += 1;
                            verificarCompletadoOfertas();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                            return headers;
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
                }

                for (Periodo periodo : periodos) {
                    final int periodoId = periodo.getLPERIODO_ID();

                    String url = getString(R.string.url_notas);

                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("pCarreraId", carreraId);
                    jsonBody.put("pPeriodoId", periodoId);

                    progreso.setTitle("Obteniendo notas...");

                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respuesta = new JSONObject(response);

                                if (respuesta.getBoolean("Status")) {
                                    JSONArray notasJson = respuesta.getJSONArray("Data");

                                    NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();
                                    dao.insercionMasiva(carreraId, periodoId, notasJson);
                                } else {
                                    Log.i("nur", "Status false en get notas");
                                }

                                pasosCompletadosNotas += 1;
                                verificarCompletadoNotas();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pasosCompletadosNotas += 1;
                            verificarCompletadoNotas();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                            return headers;
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
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void verificarCompletadoNotas() {
        if (pasosCompletadosNotas == PASOS_NESARIOS_NOTAS) {
            pasosCompletadosNotas = 0;
            PASOS_NESARIOS_NOTAS = 0;

            serviciosCompletados++;

            listo();
        }
    }

    public void listo() {
        if (serviciosCompletados == SERVICIOS_REQUERIDOS) {
            serviciosCompletados = 0;

            progreso.dismiss();
            finish();

            Intent intent = new Intent(Login.this, Carga.class);
            startActivity(intent);
        }
    }

    public void obtenerMateriasOfertadas(final int carreraId, final int periodoId) {
        String url = getString(R.string.url_materias_ofertadas);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("pCarreraId", carreraId);
            jsonBody.put("pPeriodoId", periodoId);

            progreso.setTitle("Obteniendo materias ofertadas...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray ofertasJson = respuesta.getJSONArray("Data");

                            MateriasOfertadasDAO ofertasDao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
                            ofertasDao.insercionMasiva(carreraId, periodoId, ofertasJson);
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                        pasosCompletadosOfertas += 1;
                        verificarCompletadoOfertas();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());

                    pasosCompletadosOfertas += 1;
                    verificarCompletadoOfertas();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(Login.this));

                    return headers;
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

    public void obtenerLinks() {
        String url = getString(R.string.url_enlaces);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("", "");

            progreso.setTitle("Obteniendo links...");

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray listaLinks = respuesta.getJSONArray("Data");
                            Preferences.setLinksNur(Login.this, listaLinks);
                        } else {
                            Log.i("nur", "Status false en get links");
                        }

                        pasosCompletadosBase += 1;
                        verificarCompletadoBase();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pasosCompletadosBase += 1;
                    verificarCompletadoBase();
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

    int PASOS_NECESARIOS_OFERTAS;
    int pasosCompletadosOfertas;

    public void verificarCompletadoOfertas() {
        if (pasosCompletadosOfertas == PASOS_NECESARIOS_OFERTAS) {
            PASOS_NECESARIOS_OFERTAS = 0;
            pasosCompletadosOfertas = 0;

            serviciosCompletados++;
            listo();
        }
    }
    
    final int SERVICIOS_REQUERIDOS = 3; /* NOTAS, OFERTAS E HISTORIAL ACADEMICO */
    int serviciosCompletados;

}
