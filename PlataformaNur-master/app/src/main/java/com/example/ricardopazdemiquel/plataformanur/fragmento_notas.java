package com.example.ricardopazdemiquel.plataformanur;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Objs.Periodo;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorNotas;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorPeriodos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class fragmento_notas extends Fragment {

    private SwipeRefreshLayout contenedorSwipeRefreshLayout;
    private Spinner spinnerPeriodos;
    private RecyclerView notasRecyclerView;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    public fragmento_notas() {Log.i("nur", "fragmento_notas constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);Log.i("nur", "fragmento_notas onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_notas, container, false);

        contenedorSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        spinnerPeriodos = view.findViewById(R.id.spinnerPeriodo);
        notasRecyclerView = view.findViewById(R.id.notasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notasRecyclerView.setLayoutManager(layoutManager);
        bottom_sheet = view.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        /*
        if (getContext() != null) {
            Typeface fontSegoePrint = Typeface.createFromAsset(getContext().getAssets(), "segoeprb.ttf");
            TextView tvMisNotas = view.findViewById(R.id.tvMisNotas);
            tvMisNotas.setTypeface(fontSegoePrint);
        }
        */
        spinnerPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerNotas(((Periodo) adapterView.getItemAtPosition(i)).getLPERIODO_ID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        contenedorSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {
                AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(getContext());

                if (carrera != null) {
                    int periodoId = ((Periodo) spinnerPeriodos.getSelectedItem()).getLPERIODO_ID();
                    int carreraId = carrera.getLCARRERA_ID();

                    obtenerNotas(periodoId, carreraId);
                }
            }

        });

        obtenerPeriodosCursados();

        return view;
    }

    public void obtenerPeriodosCursados() {
        ArrayList<Periodo> periodos = Preferences.getPeriodos(getContext());

        if (periodos != null) {
            spinnerPeriodos.setAdapter(new AdaptadorPeriodos(getContext(), periodos));
            spinnerPeriodos.setSelection(periodos.size() - 1);
        }
    }

    public void obtenerNotas(int periodoId) {
        AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(getContext());

        if (carrera != null) {
            NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();

            List<Notas> notas = dao.seleccionar(carrera.getLCARRERA_ID(), periodoId);
            AdaptadorNotas adaptador = new AdaptadorNotas(getContext(), notas);
            notasRecyclerView.setAdapter(adaptador);
            adaptador.setOnItemClickListener(new AdaptadorNotas.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Notas obj, int pos) {
                    showBottomSheetDialog(obj);
                }
            });
        }
    }

    private void showBottomSheetDialog(final Notas nota) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.sheet_basic, null);
        // ((TextView) view.findViewById(R.id.tvPrimerParcial)).setText(Html.fromHtml(getString(R.string.primer_parcial)));
        ((TextView) view.findViewById(R.id.tvNotaPrimerParcial)).setText(nota.getPAR1());
        ((TextView) view.findViewById(R.id.tvNotaSegundoParcial)).setText(nota.getPAR2());
        ((TextView) view.findViewById(R.id.tvNotaExamenFinal)).setText(nota.getEXFINAL());
        ((TextView) view.findViewById(R.id.tvNotaFinal)).setText(nota.getFINAL());

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    // de los servicios
    public void obtenerNotas(final int periodoId, final int carreraId) {
        String url = getString(R.string.URL_service) + "GetNotasFaltas";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("pCarreraId", carreraId);
            jsonBody.put("pPeriodoId", periodoId);

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray notas = respuesta.getJSONArray("Data");

                            NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();

                            for (int i = 0; i < notas.length(); i++) {
                                JSONObject nota = notas.getJSONObject(i);

                                nota.put("LPERIODO_ID", periodoId);
                                nota.put("LCARRERA_ID", carreraId);

                                /* Mi id son: LPERIODO_ID, LCARRERA_ID y SCODMATERIA */
                                dao.actualizar(nota);
                            }

                            AdaptadorNotas adaptador = new AdaptadorNotas(getContext(), notas);
                            notasRecyclerView.setAdapter(adaptador);
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    contenedorSwipeRefreshLayout.setRefreshing(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());

                    if (error instanceof NetworkError) {
                        Toast.makeText(getContext(), "NetworkError", Toast.LENGTH_SHORT).show();
                        // NO HAY INTERNET
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Credenciales incorrectos.", Toast.LENGTH_SHORT).show();
                    }

                    showLoginDialog(periodoId, carreraId);
                    contenedorSwipeRefreshLayout.setRefreshing(false);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(getContext()) + "xyz");

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

    public void showLoginDialog(final int periodoId, final int carreraId) {
        final View inflatedView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_login, (ViewGroup) getView(), false);
        final EditText etRegistro = inflatedView.findViewById(R.id.registro);
        final EditText etPin = inflatedView.findViewById(R.id.pin);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflatedView);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String registro = etRegistro.getText().toString();
                String pin = etPin.getText().toString();

                if (!registro.trim().isEmpty() && !pin.trim().isEmpty()) {
                    loginAgain(registro, pin, periodoId, carreraId);
                }
            }
        });

        builder.show();
    }

    public void loginAgain(String registro, String pin, final int periodoId, final int carreraId) {
        String url = getString(R.string.URL_service) + "Login";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("username", registro);
            jsonBody.put("password", pin);
            jsonBody.put("bloqueo", false); // TODO: sacar el bloqueo

            final ProgressDialog progreso = new ProgressDialog(getContext());
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando respuesta...");
            progreso.setCancelable(false);
            progreso.show();

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progreso.dismiss();

                    response = response.replace('"', ' ').trim();

                    if (response.equals("Bloqueo. Tiene deuda pendiente.")) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    } else {
                        Preferences.setTokenAcceso(getContext(), response);
                        obtenerNotas(periodoId, carreraId);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError instanceof NetworkError) {
                        Toast.makeText(getContext(), "NetworkError", Toast.LENGTH_SHORT).show();
                    } else if (volleyError instanceof NoConnectionError) {
                        Toast.makeText(getContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Credenciales incorrectos.", Toast.LENGTH_SHORT).show();
                    }

                    progreso.dismiss();
                    Log.e("LOG_VOLLEY", volleyError.toString());
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
