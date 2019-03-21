package com.example.ricardopazdemiquel.plataformanur;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorOfertas;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorPeriodos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class fragmento_ofertas extends Fragment {

    private SwipeRefreshLayout contenedorSwipeRefreshLayout;
    private Spinner spinnerPeriodos;
    private RecyclerView ofertasRecyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public fragmento_ofertas() {
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
        layoutManager = new LinearLayoutManager(getActivity());
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
                int periodoId = ((Periodo) spinnerPeriodos.getSelectedItem()).getLPERIODO_ID();
                int carreraId = Preferences.getCarreraSeleccionada(getContext()).getLCARRERA_ID();

                obtenerMateriasOfertadas(periodoId, carreraId);
            }
        });

        obtenerPeriodosOfertados();

        return view;
    }

    public void obtenerPeriodosOfertados() {
        ArrayList<Periodo> periodos = Preferences.getPeriodosOferta(getContext());
        spinnerPeriodos.setAdapter(new AdaptadorPeriodos(getContext(), periodos));

        spinnerPeriodos.setSelection(periodos.size() - 1);
    }

    public void obtenerMateriasOfertadas(int periodoId) {
        ArrayList<AlumnoCarrera> carreras = Preferences.getAlumnoCarreras(getContext());

        AlumnoCarrera carrera = estudiaUnaSolaCarrera() ? carreras.get(0) :
                Preferences.getCarreraSeleccionada(getContext());

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

    public void obtenerMateriasOfertadas(final int periodoId, final int carreraId) {
        String url = getString(R.string.URL_service) + "GetAlumnoOferta";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("pCarreraId", carreraId);
            jsonBody.put("pPeriodoId", periodoId);

            final ProgressDialog progreso = new ProgressDialog(getContext());
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando respuesta...");
            progreso.setCancelable(false);
            progreso.show();

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

                        } else {
                            Log.i("nur", "Status false en get notas");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    contenedorSwipeRefreshLayout.setRefreshing(false);
                    progreso.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showLoginDialog(periodoId, carreraId);
                    progreso.dismiss();
                    Log.e("LOG_VOLLEY", error.toString());
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

    public boolean estudiaUnaSolaCarrera() {
        return Preferences.getAlumnoCarreras(getContext()).size() == 1;
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
                        obtenerMateriasOfertadas(periodoId, carreraId);
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

    public void verMateria(MateriasOfertadas obj){
        int idCarrera = obj.getLCARRERA_ID();
        int idPeriodo = obj.getLPERIODO_ID();
        String idMateria = obj.getSCODMATERIA();
        OfertaDialog ofertaDialog = new OfertaDialog();
        ofertaDialog.show(fragmento_ofertas.this.getFragmentManager(), "Oferta");

        Bundle args = new Bundle();
        args.putInt("idCarrera", idCarrera);
        args.putInt("idPeriodo", idPeriodo);
        args.putString("idMateria", idMateria);

        ofertaDialog.setArguments(args);
    }

}
