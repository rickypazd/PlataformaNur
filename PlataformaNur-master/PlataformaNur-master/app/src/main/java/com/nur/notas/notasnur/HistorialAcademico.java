package com.nur.notas.notasnur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.MateriasDAO;
import com.nur.notas.notasnur.dto.Materias;
import com.nur.notas.notasnur.adaptadores.AdaptadorHistorialAcademico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorialAcademico extends AppCompatActivity {

    private SwipeRefreshLayout contenedorSwipeRefreshLayout, swipeRefreshLayoutEmptyState;
    private RecyclerView recyclerView;
    LinearLayout emptyStateContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_academico);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        contenedorSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayoutEmptyState = findViewById(R.id.swipeRefreshLayoutEmptyState);

        ImageView imgEmptyState = findViewById(R.id.imgEmptyState);
        Glide.with(this).load(R.drawable.empty_state_pensum).into(imgEmptyState);

        contenedorSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                confirmarActualizarMateriasCursadas();
            }
        });

        swipeRefreshLayoutEmptyState.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                confirmarActualizarMateriasCursadas();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pensum");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        cargarMaterias();
    }

    private void cargarMaterias() {
        MateriasDAO materiaDao = FactoryDAO.getOrCreate().newMateriasDAO();
        List<Materias> materias = materiaDao.seleccionarTodos();

        AdaptadorHistorialAcademico adaptador = new AdaptadorHistorialAcademico(this, materias);
        recyclerView.setAdapter(adaptador);

        contenedorSwipeRefreshLayout.setVisibility(materias.isEmpty() ? View.GONE : View.VISIBLE);
        swipeRefreshLayoutEmptyState.setVisibility(materias.isEmpty() ? View.VISIBLE: View.GONE);
    }

    private void confirmarActualizarMateriasCursadas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Actualizar pensum?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                obtenerHistorialMateriasCursadas();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contenedorSwipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayoutEmptyState.setRefreshing(false);
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                contenedorSwipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayoutEmptyState.setRefreshing(false);
            }
        });

        builder.show();
    }

    public void obtenerHistorialMateriasCursadas() {
        String url = getString(R.string.url_historial_academico);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            final int carreraId = Preferences.getCarreraSeleccionada(this).getLCARRERA_ID();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("pCarreraId", carreraId);

            final ProgressDialog progreso = new ProgressDialog(this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Actualizando pensum...");
            progreso.setCancelable(false);
            progreso.show();

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

                            materiaDao.eliminarMaterias(carreraId);
                            materiaDao.insercionMasiva(carreraId, materiasCursadas);
                            materiaDao.insercionMasiva(carreraId, materiasFaltantes, true);
                        } else {
                            Log.i("nur", "Status false en get alumno historial " + carreraId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cargarMaterias();

                    contenedorSwipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayoutEmptyState.setRefreshing(false);

                    progreso.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progreso.dismiss();

                    swipeRefreshLayoutEmptyState.setRefreshing(false);
                    contenedorSwipeRefreshLayout.setRefreshing(false);

                    if (volleyError instanceof NetworkError) {
                        showNoNetworkDialog();
                    } else if (volleyError instanceof AuthFailureError) {
                        loginAgain();
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(HistorialAcademico.this));

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

    private void showNoNetworkDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_no_internet);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void loginAgain() {
        String url = getString(R.string.url_login);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String registro = Preferences.getRegistro(this);
            String pin = Preferences.getPin(this);

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("username", registro);
            jsonBody.put("password", pin);
            jsonBody.put("bloqueo", false); // TODO: sacar el bloqueo

            final ProgressDialog progreso = new ProgressDialog(this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Un momento...");
            progreso.setCancelable(false);
            progreso.show();

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progreso.dismiss();

                    response = response.replace('"', ' ').trim();

                    if (response.equals("Bloqueo. Tiene deuda pendiente.")) {
                        Toast.makeText(HistorialAcademico.this, "Bloqueo. Tiene deuda pendiente.", Toast.LENGTH_SHORT).show();
                    } else {
                        Preferences.setTokenAcceso(HistorialAcademico.this, response);
                        obtenerHistorialMateriasCursadas();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    /* acá el error mas probable son credenciales incorrectos, debido a un cambio
                    en el pin, que no fue realizado desde la app */
                    if (volleyError instanceof AuthFailureError) {
                        String error = "Hubo un error al obtener el pensum. Si cambió su pin, cierre sesión y vuelva ingresar.";
                        Toast.makeText(HistorialAcademico.this, error, Toast.LENGTH_LONG).show();
                    }

                    progreso.dismiss();
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
