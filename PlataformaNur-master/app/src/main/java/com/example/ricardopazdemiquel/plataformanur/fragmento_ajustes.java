package com.example.ricardopazdemiquel.plataformanur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class fragmento_ajustes extends Fragment{

    private TextView textView_carreras;
    private TextView textView_nombre;
    private TextView textView_registro;
    private TextView textView_horas;
    private TextView textView_celular;
    private TextView textView_telefono;
    private TextView textView_email;
    private TextView textView_fecha_nac;

    public fragmento_ajustes() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragmento_ajustes, container, false);

        textView_carreras = (TextView) view.findViewById(R.id.textView_carreras);
        textView_nombre = (TextView) view.findViewById(R.id.textView_nombre);
        textView_registro = (TextView) view.findViewById(R.id.textView_registro);
        textView_horas = (TextView) view.findViewById(R.id.textView_horas);
        textView_celular = (TextView) view.findViewById(R.id.textView_celular);
        textView_telefono = (TextView) view.findViewById(R.id.textView_telefono);
        textView_email = (TextView) view.findViewById(R.id.textView_email);
        textView_fecha_nac = (TextView) view.findViewById(R.id.textView_fecha_nac);

        Alumno alumno = Preferences.getAlumno(fragmento_ajustes.this.getContext());
        if (alumno == null){
            getAlumnoInfo();
            //getAlumnoCarreras();
            //cargarPerfil();
        }else{
            cargarPerfil();
        }

        return view;
        //return inflater.inflate(R.layout.fragment_fragmento_ajustes, container, false);
    }

    public void getAlumnoInfo() {
        String url = getString(R.string.URL_service) + "GetAlumnoInfo";

        RequestQueue requestQueue = Volley.newRequestQueue(fragmento_ajustes.this.getContext());
        JSONObject jsonBody = new JSONObject();
        //jsonBody.put("Key", getString(R.string.KeyService));
        final ProgressDialog progreso = new ProgressDialog(fragmento_ajustes.this.getContext());
        progreso.setIndeterminate(true);
        progreso.setTitle("Esperando respuesta...");
        progreso.setCancelable(false);
        progreso.show();
        final String mRequestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.dismiss();
                //Log.i("LOG_VOLLEY", response);
                if (response == null) {
                    Log.e("Call Service Nur:", "Error desconocido al realizar la peticion.");
                } else if (response.length() < 1) {
                    Toast.makeText(fragmento_ajustes.this.getContext(), "Error desconocido al realizar la peticion.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(response);

                        Alumno alumno = new Alumno();
                        alumno.setId(obj.getJSONObject("Data").getInt("LALUMNO_ID"));
                        alumno.setRegistro(obj.getJSONObject("Data").get("SREGISTRO").toString().trim());
                        alumno.setApellidoPaterno(obj.getJSONObject("Data").get("SAPELLIDOP").toString().trim());
                        alumno.setApellidoMaterno(obj.getJSONObject("Data").get("SAPELLIDOM").toString().trim());
                        alumno.setNombre(obj.getJSONObject("Data").get("SNOMBRES").toString().trim());
                        alumno.setFechaNacimiento(obj.getJSONObject("Data").get("DTFECHNAC").toString().trim());
                        alumno.setSexo(obj.getJSONObject("Data").get("SSEXO_DSC").toString().trim());
                        alumno.setCelular(obj.getJSONObject("Data").get("SCELULAR").toString().trim());
                        alumno.setTelefono(obj.getJSONObject("Data").get("STELEFONO").toString().trim());
                        alumno.setEmail(obj.getJSONObject("Data").get("SEMAIL").toString().trim());
                        alumno.setEstadoCivil(obj.getJSONObject("Data").get("SESTADOCIVIL_DSC").toString().trim());
                        alumno.setTipoSangre(obj.getJSONObject("Data").get("STIPOSANGRE_DSC").toString().trim());

                        int nActivoPasivo = obj.getJSONObject("Data").getInt("BOOLACTIVOPASIVO");
                        if(nActivoPasivo == 1)
                            alumno.setActivoPasivo(true);
                        else
                            alumno.setActivoPasivo(false);

                        alumno.setHorasServicio(obj.getJSONObject("Data").getInt("LHORASERVICIO"));

                        Preferences.setAlumno(fragmento_ajustes.this.getContext(), alumno);

                        getAlumnoCarreras();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                String accesstoken = Preferences.getTokenAcceso(fragmento_ajustes.this.getContext()).trim();

                headers.put("Authorization", "Bearer " + accesstoken);

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAlumnoCarreras() {
        String url = getString(R.string.URL_service) + "GetAlumnoCarreras";

        RequestQueue requestQueue = Volley.newRequestQueue(fragmento_ajustes.this.getContext());
        JSONObject jsonBody = new JSONObject();
        //jsonBody.put("Key", getString(R.string.KeyService));
        final ProgressDialog progreso = new ProgressDialog(fragmento_ajustes.this.getContext());
        progreso.setIndeterminate(true);
        progreso.setTitle("Esperando respuesta...");
        progreso.setCancelable(false);
        progreso.show();
        final String mRequestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.dismiss();
                //Log.i("LOG_VOLLEY", response);
                if (response == null) {
                    Log.e("Call Service Nur:", "Error desconocido al realizar la peticion.");
                } else if (response.length() < 1) {
                    Toast.makeText(fragmento_ajustes.this.getContext(), "Error desconocido al realizar la peticion.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(response);
                        ArrayList<AlumnoCarrera> alumnoCarreras = new ArrayList<>();
                        JSONArray jsonArray = obj.getJSONArray("Data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject objJson = jsonArray.getJSONObject(i);
                            AlumnoCarrera alumnoCarrera = new AlumnoCarrera();
                            alumnoCarrera.setLCARRERA_ID(objJson.getInt("LCARRERA_ID"));
                            alumnoCarrera.setSCODCENTRO(objJson.getString("SCODCENTRO"));
                            alumnoCarrera.setSCENTRO_DSC((objJson.getString("SCENTRO_DSC")));
                            alumnoCarrera.setSCODCARRERA(objJson.getString("SCODCARRERA"));
                            alumnoCarrera.setSCARRERA_DSC(objJson.getString("SCARRERA_DSC"));
                            alumnoCarrera.setLCODPENSUM(objJson.getInt("LCODPENSUM"));
                            alumnoCarrera.setLCREDVENCIDOS(objJson.getInt("LCREDVENCIDOS"));
                            alumnoCarrera.setLPERIODOINICIO(objJson.getString("LPERIODOINICIO"));
                            alumnoCarrera.setLPERIODOACTUAL_ID(objJson.getInt("LPERIODOACTUAL_ID"));
                            alumnoCarrera.setLPERIODOACTUAL(objJson.getString("LPERIODOACTUAL"));
                            alumnoCarrera.setLPERIODOFIN(objJson.getString("LPERIODOFIN"));
                            alumnoCarrera.setDTFECHADMISION(objJson.getString("DTFECHADMISION"));
                            alumnoCarrera.setDTFECHEGRESO(objJson.getString("DTFECHEGRESO"));
                            alumnoCarrera.setDTFECHTITULACION(objJson.getString("DTFECHTITULACION"));
                            alumnoCarrera.setSTIPOGRADUACION_DSC(objJson.getString("STIPOGRADUACION_DSC"));
                            alumnoCarreras.add(alumnoCarrera);
                        }
                        Preferences.setAlumnoCarreras(fragmento_ajustes.this.getContext(),alumnoCarreras);
                        cargarPerfil();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String accesstoken = Preferences.getTokenAcceso(fragmento_ajustes.this.getContext()).trim();
                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void cargarPerfil(){
        Alumno alumno = Preferences.getAlumno(fragmento_ajustes.this.getContext());
        ArrayList<AlumnoCarrera> alumnoCarreras = Preferences.getAlumnoCarreras(fragmento_ajustes.this.getContext());
        String carreras = "";

        for (int i = 0; i < alumnoCarreras.size(); i++) {
            String carrera = alumnoCarreras.get(i).getSCARRERA_DSC();
            carreras = carreras + " " + carrera;
        }
        carreras = carreras.trim();

        textView_carreras.setText(carreras);
        textView_nombre.setText(alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno());
        textView_registro.setText(alumno.getRegistro());
        textView_horas.setText(String.valueOf(alumno.getHorasServicio()));
        textView_celular.setText(alumno.getCelular());
        textView_telefono.setText(alumno.getTelefono());
        textView_email.setText(alumno.getEmail());
        textView_fecha_nac.setText(alumno.getFechaNacimiento());
    }

}
