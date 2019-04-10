package com.nur.notas.notasnur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.nur.notas.notasnur.objetos.AlumnoCarrera;
import com.nur.notas.notasnur.objetos.Periodo;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.NotasDAO;
import com.nur.notas.notasnur.dto.Notas;
import com.nur.notas.notasnur.adaptadores.AdaptadorNotas;
import com.nur.notas.notasnur.adaptadores.AdaptadorPeriodos;
import com.nur.notas.notasnur.utiles.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentoNotas extends Fragment {

    private SwipeRefreshLayout contenedorSwipeRefreshLayout, swipeRefreshLayoutEmptyState;
    private Spinner spinnerPeriodos;
    private RecyclerView notasRecyclerView;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    public FragmentoNotas() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (((TabBarActivity)getActivity()).getSupportActionBar() != null) {
            ((TabBarActivity)getActivity()).getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_notas, container, false);

        contenedorSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayoutEmptyState = view.findViewById(R.id.swipeRefreshLayoutEmptyState);
        spinnerPeriodos = view.findViewById(R.id.spinnerPeriodo);
        notasRecyclerView = view.findViewById(R.id.notasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notasRecyclerView.setLayoutManager(layoutManager);
        View bottom_sheet = view.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        ImageView imgEmptyState = view.findViewById(R.id.imgEmptyState);
        Glide.with(getContext()).load(R.drawable.empty_state_pensum).into(imgEmptyState);

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
                confirmarActualizarNotas();
            }

        });

        swipeRefreshLayoutEmptyState.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                confirmarActualizarNotas();
            }
        });

        obtenerPeriodosCursados();

        return view;
    }

    public void obtenerPeriodosCursados() {
        NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();
        List<Periodo> periodos = dao.seleccionarSemestresCursados();

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

            contenedorSwipeRefreshLayout.setVisibility(notas.isEmpty() ? View.GONE : View.VISIBLE);
            swipeRefreshLayoutEmptyState.setVisibility(notas.isEmpty() ? View.VISIBLE: View.GONE);
        }
    }

    private void showBottomSheetDialog(final Notas nota) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.sheet_basic, null);

        ((TextView) view.findViewById(R.id.tvNombreMateria)).setText(nota.getSMATERIA_DSC());
        ((TextView) view.findViewById(R.id.tvNotaPrimerParcial)).setText(nota.getPAR1());
        ((TextView) view.findViewById(R.id.tvNotaSegundoParcial)).setText(nota.getPAR2());
        ((TextView) view.findViewById(R.id.tvNotaExamenFinal)).setText(nota.getEXFINAL());
        ((TextView) view.findViewById(R.id.tvNotaFinal)).setText(nota.getFINAL());

        TextView notaPrimerParcial = view.findViewById(R.id.notaPrimerParcial);
        TextView notaSegundoParcial = view.findViewById(R.id.notaSegundoParcial);
        TextView notaExamenFinal = view.findViewById(R.id.notaExamenFinal);
        TextView notaFinal = view.findViewById(R.id.notaFinal);

        if (nota.getPAR2().equals("0/0")) {
            view.findViewById(R.id.contenedorSegundoParcial).setVisibility(View.GONE);
        }

        // TODO: optimize this
        // PRIMER PARCIAL
        String notaPrimerParcialStr = nota.getPAR1(); /* "27/30" */
        notaPrimerParcialStr = notaPrimerParcialStr.replace(",", ".");
        String[] notaPrimerParcialArr = notaPrimerParcialStr.split("/"); /* ["27", "30"] */
        int residuoPrimerParcial = (int) (Double.parseDouble(notaPrimerParcialArr[0]) % 1);
        int primerParcialSobre = (int) Double.parseDouble(notaPrimerParcialArr[1]);
        animateTextViewV2(Double.parseDouble(notaPrimerParcialArr[0]), primerParcialSobre, notaPrimerParcial, residuoPrimerParcial);

        // SEGUNDO PARCIAL
        String notaSegundoParcialStr = nota.getPAR2(); /* "27/30" */
        notaSegundoParcialStr = notaSegundoParcialStr.replace(",", ".");
        String[] notaSegundoParcialArr = notaSegundoParcialStr.split("/"); /* ["27", "30"] */
        int residuoSegundoParcial = (int) (Double.parseDouble(notaSegundoParcialArr[0]) % 1);
        int segundoParcialSobre = (int) Double.parseDouble(notaSegundoParcialArr[1]);
        animateTextViewV2(Double.parseDouble(notaSegundoParcialArr[0]), segundoParcialSobre, notaSegundoParcial, residuoSegundoParcial);

        // EXAMEN FINAL
        String notaExamenFinalStr = nota.getEXFINAL(); /* "27/30" */
        notaExamenFinalStr = notaExamenFinalStr.replace(",", ".");
        String[] notaExamenFinalArr = notaExamenFinalStr.split("/"); /* ["27", "30"] */
        int residuoExamenFinal = (int) (Double.parseDouble(notaExamenFinalArr[0]) % 1);
        int examenFinalSobre = (int) Double.parseDouble(notaExamenFinalArr[1]);
        animateTextViewV2(Double.parseDouble(notaExamenFinalArr[0]), examenFinalSobre, notaExamenFinal, residuoExamenFinal);

        // NOTA FINAL
        String notaFinalStr = nota.getFINAL(); /* "27/30" */
        notaFinalStr = notaFinalStr.replace(",", ".");
        String[] notaFinalArr = notaFinalStr.split("/"); /* ["27", "30"] */
        int residuoNotaFinal = (int) (Double.parseDouble(notaFinalArr[0]) % 1);
        int notaFinalSobre = (int) Double.parseDouble(notaFinalArr[1]);
        animateTextViewV2(Double.parseDouble(notaFinalArr[0]), notaFinalSobre, notaFinal, residuoNotaFinal);

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    private void confirmarActualizarNotas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Actualizar las notas?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                obtenerNotas();
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

    // de los servicios
    private void obtenerNotas() {
        String url = getString(R.string.url_notas);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(getContext());

            if (carrera == null) {
                return;
            }

            final int periodoId = ((Periodo) spinnerPeriodos.getSelectedItem()).getLPERIODO_ID();
            final int carreraId = carrera.getLCARRERA_ID();

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
                            dao.eliminar(carreraId, periodoId);
                            dao.insercionMasiva(carreraId, periodoId, notas);

                            obtenerNotas(periodoId);
                        } else {
                            Log.i("nur", "Status false en get notas");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    contenedorSwipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayoutEmptyState.setRefreshing(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    contenedorSwipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayoutEmptyState.setRefreshing(false);

                    if (volleyError instanceof NetworkError) {
                        Util.mostrarDialogoSinInternet(getContext());
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
                    headers.put("Authorization", "Bearer " + Preferences.getTokenAcceso(getContext()));

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

    public void loginAgain() {
        String url = getString(R.string.url_login);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            String registro = Preferences.getRegistro(getContext());
            String pin = Preferences.getPin(getContext());

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
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Preferences.setTokenAcceso(getContext(), response);
                        obtenerNotas();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    /* acá el error mas probable son credenciales incorrectos, debido a un cambio
                    en el pin, que no fue realizado desde la app */
                    if (volleyError instanceof AuthFailureError) {
                        String error = "Hubo un error al obtener el pensum. Si cambió su pin, cierre sesión y vuelva ingresar.";
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
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

    public void animateTextViewV2(final double nota, final int sobre, final TextView textview, final int puntoDecimal) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.2f);

        Handler handler = new Handler();

        for (int count = 0; count <= (int) nota; count++) {
            float input = ((float) count) / (int) nota;
            // int time = Math.round(decelerateInterpolator.getInterpolation(input) * 100) * count;
            int time = Math.round(input * 100) * 10;
            final int finalCount = count;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (finalCount == (int) nota && nota % 1 != 0) { /* ultima iteracion */
                        textview.setText(nota + "/" + sobre);
                        return;
                    }

                    textview.setText(finalCount + "/" + sobre);
                }
            }, time);
        }
    }

}
