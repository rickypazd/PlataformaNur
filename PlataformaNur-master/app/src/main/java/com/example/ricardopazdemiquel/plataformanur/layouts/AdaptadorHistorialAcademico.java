package com.example.ricardopazdemiquel.plataformanur.layouts;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.R;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class AdaptadorHistorialAcademico extends RecyclerView.Adapter<AdaptadorHistorialAcademico.MyViewHolder> {

    private List<Notas> listaNotas;
    private Context contexto;

    public AdaptadorHistorialAcademico(Context contexto, List<Notas> lista) {
        this.listaNotas = lista;
        this.contexto = contexto;
    }

    @Override
    public AdaptadorHistorialAcademico.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_historial_academico, parent, false);
        MyViewHolder vh = new MyViewHolder(view, viewType);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public void onBindViewHolder(AdaptadorHistorialAcademico.MyViewHolder holder, int position) {
        final Notas obj = listaNotas.get(position);

        holder.tvSemestre.setText("5");
        holder.tvMaterias.setText(obj.getSMATERIA_DSC());

        holder.timelineView.setMarker(ContextCompat.getDrawable(contexto, R.drawable.ic_marker_inactive));
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSemestre;
        public TextView tvMaterias;
        public TimelineView timelineView;

        public MyViewHolder(View v, int viewType) {
            super(v);

            tvSemestre = v.findViewById(R.id.text_timeline_date);
            tvMaterias = v.findViewById(R.id.text_timeline_title);
            timelineView = v.findViewById(R.id.timeline);
            timelineView.initLine(viewType);
        }

    }

}
