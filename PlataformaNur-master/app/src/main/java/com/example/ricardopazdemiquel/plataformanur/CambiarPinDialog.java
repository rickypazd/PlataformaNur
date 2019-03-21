package com.example.ricardopazdemiquel.plataformanur;

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
import java.util.HashMap;
import java.util.Map;

public class CambiarPinDialog extends AppCompatDialogFragment {
    private TextInputEditText et_pin_actual;
    private TextInputEditText et_nuevo_pin;
    private TextInputEditText et_nuevo_pin_r;
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_cambiar_pin, null);

        et_pin_actual = view.findViewById(R.id.et_pin_actual);
        et_nuevo_pin = view.findViewById(R.id.et_nuevo_pin);
        et_nuevo_pin_r = view.findViewById(R.id.et_nuevo_pin_r);

        builder.setView(view)
                .setTitle("Cambiar Pin")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("nur", "khe");
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinActual = et_pin_actual.getText().toString();
                String pinNuevo = et_nuevo_pin.getText().toString();
                String pinNuevoR = et_nuevo_pin_r.getText().toString();
                if(pinActual.trim().isEmpty() || pinNuevo.trim().isEmpty() || pinNuevoR.trim().isEmpty()){
                    Toast.makeText(CambiarPinDialog.this.getContext(), "Por favor llene todos los campos.", Toast.LENGTH_LONG).show();
                }else{
                    if(!pinNuevo.trim().equals(pinNuevoR)){
                        Toast.makeText(CambiarPinDialog.this.getContext(), "Los nuevos pines no coinciden", Toast.LENGTH_LONG).show();
                    }else{
                        try {
                            cambiarPin(dialog, pinActual, pinNuevo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return dialog;
    }

    public void cambiarPin(final AlertDialog dialog, String pinActual, String pinNuevo) throws JSONException{
        String url = getString(R.string.URL_service) + "UpdatePin";
        context = this.getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pPinActual", pinActual);
        jsonBody.put("pPinNuevo", pinNuevo);

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
                    Toast.makeText(CambiarPinDialog.this.getContext(), "Error desconocido al realizar la peticion.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Boolean status = obj.getBoolean("Status");
                        if(status){
                            Toast.makeText(CambiarPinDialog.this.getContext(), "Pin actualizado con éxito.", Toast.LENGTH_LONG).show();
                            // TODO: update the shared preferences
                            dialog.dismiss();
                        }else{
                            Toast.makeText(CambiarPinDialog.this.getContext(), "Pin incorrecto", Toast.LENGTH_LONG).show();
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

                String accesstoken = Preferences.getTokenAcceso(context).trim();

                headers.put("Authorization", "Bearer " + accesstoken);

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

}
