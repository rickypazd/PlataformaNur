package com.nur.notas.notasnur.layouts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nur.notas.notasnur.Objs.AlumnoCarrera;
import com.nur.notas.notasnur.R;

import java.util.List;


public class AdaptadorCarreras extends BaseAdapter {

    private Context context;
    private List<AlumnoCarrera> carreras;

    public AdaptadorCarreras(Context context, List<AlumnoCarrera> carreras) {
        this.context = context;
        this.carreras = carreras;
    }

    @Override
    public int getCount() {
        return carreras.size();
    }

    @Override
    public Object getItem(int i) {
        return carreras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner_carrera, viewGroup, false);
            // view.setBackgroundColor(context.getResources().getColor(R.color.mdtp_white));
        }

        TextView textView = view.findViewById(R.id.textview);
        // textView.setTextSize(22);
        // textView.setTextColor(Color.WHITE);
        textView.setText(carreras.get(i).getSCARRERA_DSC());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);

        TextView textView = view.findViewById(R.id.textview);
        textView.setPadding(15, 16, 16, 16);
        // view.setBackgroundColor(context.getResources().getColor(R.color.mdtp_white));

        return view;
    }
}
