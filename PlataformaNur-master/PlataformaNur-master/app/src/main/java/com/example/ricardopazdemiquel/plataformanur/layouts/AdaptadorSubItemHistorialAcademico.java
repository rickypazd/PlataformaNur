package com.example.ricardopazdemiquel.plataformanur.layouts;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.R;
import com.example.ricardopazdemiquel.plataformanur.dto.Materias;

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

        String upperString = materia.getSMATERIA_DSC().substring(0,1).toUpperCase() + materia.getSMATERIA_DSC().substring(1).toLowerCase();
        holder.tvMaterias.setText(upperString);
        if (materia.getOBS().equals("FALTANTE")) {
            holder.checkImageView.setVisibility(View.GONE);
        }
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

