package com.nur.notas.notasnur.layouts;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nur.notas.notasnur.R;
import com.nur.notas.notasnur.dto.Materias;

import java.util.List;

public class AdaptadorSubItemHistorialAcademico extends RecyclerView.Adapter<AdaptadorSubItemHistorialAcademico.MyViewHolder> {

    private List<Materias> listaMaterias;
    private Context contexto;

    public AdaptadorSubItemHistorialAcademico(Context contexto, List<Materias> lista) {
        this.listaMaterias = lista;
        this.contexto = contexto;
    }

    @Override
    public AdaptadorSubItemHistorialAcademico.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.layout_sub_item_historial_academico, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdaptadorSubItemHistorialAcademico.MyViewHolder holder, int position) {
        final Materias materia = listaMaterias.get(position);

        String upperString = aMayusculas(materia.getSMATERIA_DSC());

        upperString = upperString.replaceAll("\\b" + "i" + "\\b", "I");
        upperString = upperString.replaceAll("\\b" + "ii" + "\\b", "II");
        upperString = upperString.replaceAll("\\b" + "iii" + "\\b", "III");
        upperString = upperString.replaceAll("\\b" + "iv" + "\\b", "IV");
        upperString = upperString.replaceAll("\\b" + "v" + "\\b", "V");

        holder.tvMaterias.setText(upperString);
        if (materia.getOBS().equals("FALTANTE")) {
            holder.checkImageView.setVisibility(View.GONE);
        }
    }

    private String aMayusculas(String texto) {
        if (texto.isEmpty()) {
            return "";
        }

        return texto.substring(0,1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    @Override
    public int getItemCount() {
        return listaMaterias.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMaterias;
        public ImageView checkImageView;

        public MyViewHolder(View v) {
            super(v);

            tvMaterias = v.findViewById(R.id.materiaTextView);
            checkImageView = v.findViewById(R.id.checkImageView);
        }

    }

}

