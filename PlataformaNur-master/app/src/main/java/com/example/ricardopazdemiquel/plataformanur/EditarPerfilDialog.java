package com.nur.notas.notasnur;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.nur.notas.notasnur.objetos.Alumno;
import com.nur.notas.notasnur.utiles.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfilDialog extends AppCompatDialogFragment {

    private TextInputEditText et_celular;
    private TextInputEditText et_telefono;
    private TextInputEditText et_correo;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_perfil, null);

        et_celular = view.findViewById(R.id.et_celular);
        et_telefono = view.findViewById(R.id.et_telefono);
        et_correo = view.findViewById(R.id.et_correo);

        Alumno alumno = Preferences.getAlumno(this.getContext());

        et_celular.setText(alumno.getCelular());
        et_telefono.setText(alumno.getTelefono());
        et_correo.setText(alumno.getEmail());
        builder.setView(view)
                .setTitle("Editar Información")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            editInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        return builder.create();
    }

    public void editInfo() throws JSONException{
        String url = getString(R.string.URL_service) + "UpdateEmailTelefono";
        final String celular = et_celular.getText().toString();
        final String telefono = et_telefono.getText().toString();
        final String correo = et_correo.getText().toString();
        // context = this.getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pEmail", correo);
        jsonBody.put("pTelefono", telefono);
        jsonBody.put("pCelular", celular);

        final ProgressDialog progreso = new ProgressDialog(this.getContext());
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
                    Toast.makeText(EditarPerfilDialog.this.getContext(), "Error desconocido al realizar la peticion.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Boolean status = obj.getBoolean("Status");
                        if(status){
                            Alumno alumno = Preferences.getAlumno(context);
                            alumno.setCelular(celular);
                            alumno.setTelefono(telefono);
                            alumno.setEmail(correo);
                            Preferences.setAlumno(context, alumno);

                            TextView tvCelular = ((Activity) context).findViewById(R.id.textView_celular);
                            TextView tvTelefono = ((Activity) context).findViewById(R.id.textView_telefono);
                            TextView tvEmail = ((Activity) context).findViewById(R.id.textView_email);

                            tvCelular.setText(celular);
                            tvTelefono.setText(telefono);
                            tvEmail.setText(correo);
                        }else{
                            //TODO hacer login de nuevo
                        }

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

                String accesstoken = Preferences.getTokenAcceso(EditarPerfilDialog.this.getContext()).trim();

                headers.put("Authorization", "Bearer " + accesstoken);

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

}
