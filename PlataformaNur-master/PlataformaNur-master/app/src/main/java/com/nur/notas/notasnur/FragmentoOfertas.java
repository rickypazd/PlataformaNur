package com.nur.notas.notasnur;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import com.nur.notas.notasnur.dao.HorariosOfertadosDAO;
import com.nur.notas.notasnur.dao.MateriasOfertadasDAO;
import com.nur.notas.notasnur.dto.MateriasOfertadas;
import com.nur.notas.notasnur.adaptadores.AdaptadorOfertas;
import com.nur.notas.notasnur.adaptadores.AdaptadorPeriodos;
import com.nur.notas.notasnur.utiles.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentoOfertas extends Fragment {

    private SwipeRefreshLayout contenedorSwipeRefreshLayout;
    private Spinner spinnerPeriodos;
    private RecyclerView ofertasRecyclerView;

    public FragmentoOfertas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TabBarActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_ofertas, container, false);

        contenedorSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutOferta);
        spinnerPeriodos = view.findViewById(R.id.spinnerPeriodoOferta);
        ofertasRecyclerView = view.findViewById(R.id.ofertasRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ofertasRecyclerView.setLayoutManager(layoutManager);

        spinnerPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerMateriasOfertadas(((Periodo) adapterView.getItemAtPosition(i)).getLPERIODO_ID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("nur", "nothing selected");
            }
        });

        contenedorSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                obtenerMateriasOfertadas();
            }
        });

        obtenerPeriodosOfertados();

        return view;
    }

    public void obtenerPeriodosOfertados() {
        MateriasOfertadasDAO dao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        List<Periodo> periodos = dao.seleccionarSemestresOfertados();

        if (periodos != null) {
            spinnerPeriodos.setAdapter(new AdaptadorPeriodos(getContext(), periodos));
            spinnerPeriodos.setSelection(periodos.size() - 1);
        }
    }

    public void obtenerMateriasOfertadas(int periodoId) {
        AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(getContext());

        MateriasOfertadasDAO dao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        List<MateriasOfertadas> ofertas = dao.seleccionar(carrera.getLCARRERA_ID(), periodoId);

        AdaptadorOfertas adaptador = new AdaptadorOfertas(getContext(), ofertas);
        ofertasRecyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new AdaptadorOfertas.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MateriasOfertadas obj) {
                verMateria(obj);
            }
        });
    }

    /* de los servicios */
    public void obtenerMateriasOfertadas() {
        String url = getString(R.string.url_materias_ofertadas);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            final int periodoId = ((Periodo) spinnerPeriodos.getSelectedItem()).getLPERIODO_ID();
            final int carreraId = Preferences.getCarreraSeleccionada(getContext()).getLCARRERA_ID();

            jsonBody.put("pCarreraId", carreraId);
            jsonBody.put("pPeriodoId", periodoId);

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuesta = new JSONObject(response);

                        if (respuesta.getBoolean("Status")) {
                            JSONArray ofertasJson = respuesta.getJSONArray("Data");

                            MateriasOfertadasDAO ofertasDao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
                            HorariosOfertadosDAO horarioDao = FactoryDAO.getOrCreate().newHorariosOfertadosDAO();

                            for (int i = 0; i < ofertasJson.length(); i++) {
                                JSONObject ofertaJson = ofertasJson.getJSONObject(i);
                                ofertaJson.put("LPERIODO_ID", periodoId);
                                ofertaJson.put("LCARRERA_ID", carreraId);

                                ofertasDao.actualizar(ofertaJson);
                                MateriasOfertadas oferta = ofertasDao.seleccionar(carreraId,
                                        periodoId, ofertaJson.getString("SCODMATERIA"));

                                horarioDao.eliminarDeMateria(oferta.getID());

                                if (!ofertaJson.isNull("HORARIO")) {
                                    JSONArray horarios = ofertaJson.getJSONArray("HORARIO");

                                    for (int j = 0; j < horarios.length(); j++) {
                                        JSONObject horario = horarios.getJSONObject(j);
                                        horario.put("MAT_OFERTADA_ID", oferta.getID());

                                        horarioDao.insertar(horario);
                                    }
                                }
                            }

                            obtenerMateriasOfertadas(periodoId);
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
                public void onErrorResponse(VolleyError volleyError) {
                    contenedorSwipeRefreshLayout.setRefreshing(false);

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
                    response = response.replace('"', ' ').trim();

                    if (response.equals("Bloqueo. Tiene deuda pendiente.")) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    } else {
                        Preferences.setTokenAcceso(getContext(), response);
                        obtenerMateriasOfertadas();
                    }

                    progreso.dismiss();
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

    public void verMateria(MateriasOfertadas obj){
        int idCarrera = obj.getLCARRERA_ID();
        int idPeriodo = obj.getLPERIODO_ID();
        int idMateria = obj.getID();
        OfertaDialog ofertaDialog = new OfertaDialog();
        ofertaDialog.show(FragmentoOfertas.this.getFragmentManager(), "Oferta");

        Bundle args = new Bundle();
        args.putInt("idCarrera", idCarrera);
        args.putInt("idPeriodo", idPeriodo);
        args.putInt("idMateria", idMateria);

        ofertaDialog.setArguments(args);
    }

}
