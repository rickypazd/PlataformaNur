package com.example.ricardopazdemiquel.plataformanur;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Objs.Link;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class LinksDialog extends AppCompatDialogFragment {

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
        View view = inflater.inflate(R.layout.dialog_links, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lLLinks);

        ArrayList<Link> links = Preferences.getLinksNur(this.getContext());
        for (int i = 0; i < links.size(); i++) {
            TextView tvTitulo = new TextView(this.getContext());
            TextView tvLink = new TextView(this.getContext());
            tvTitulo.setText(links.get(i).getTITULO());

            String link = links.get(i).getLINK();
            String html = "<a href=\"" + link + "\">" + link + "</a>";
            tvLink.setText(Html.fromHtml(html));
            tvLink.setAutoLinkMask(Linkify.WEB_URLS);
            tvLink.setLinksClickable(true);
            linearLayout.addView(tvTitulo);
            linearLayout.addView(tvLink);



        }

        builder.setView(view)
                .setTitle("Páginas informativas");
        return builder.create();
    }
}
