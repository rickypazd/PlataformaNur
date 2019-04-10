package com.nur.notas.notasnur;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.nur.notas.notasnur.objetos.Link;
import com.nur.notas.notasnur.utiles.Preferences;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

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
            String titulo = links.get(i).getTITULO();
            String link = links.get(i).getLINK();
            String html = "<a href=\"" + link + "\">" + titulo + "</a>";
            tvLink.setMovementMethod(LinkMovementMethod.getInstance());
            tvLink.setText(Html.fromHtml(html));
            tvLink.setLinkTextColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
            tvLink.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvLink.setGravity(Gravity.CENTER);

            //linearLayout.addView(tvTitulo);
            linearLayout.addView(tvLink);
        }

        TextView title = new TextView(view.getContext());
        title.setText("Páginas informativas");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
        title.setTextSize(20);
        builder.setView(view)
                .setCustomTitle(title);
        return builder.create();
    }
}
