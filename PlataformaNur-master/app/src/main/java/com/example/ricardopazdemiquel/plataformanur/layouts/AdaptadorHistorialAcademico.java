package com.example.ricardopazdemiquel.plataformanur.layouts;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.R;
import com.example.ricardopazdemiquel.plataformanur.dto.Materias;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdaptadorHistorialAcademico extends RecyclerView.Adapter<AdaptadorHistorialAcademico.MyViewHolder> {

    private List<Materias> listaMaterias;
    private List<Integer> semestres;
    private Context contexto;

    public AdaptadorHistorialAcademico(Context contexto, List<Materias> lista) {
        this.listaMaterias = lista;
        semestres = new ArrayList<>();
        this.contexto = contexto;

        for (Materias materia: listaMaterias) {
            boolean estaEnLaLista = false;

            for (int semestre: semestres) {
                if (materia.getLSEMESTRE() == semestre) {
                    estaEnLaLista = true;
                    break;
                }
            }

            if (!estaEnLaLista) {
                semestres.add(materia.getLSEMESTRE());
            }
        }

        Collections.sort(semestres);
    }

    @Override
    public AdaptadorHistorialAcademico.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_historial_academico, parent, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public void onBindViewHolder(AdaptadorHistorialAcademico.MyViewHolder holder, int position) {
        final int semestre = semestres.get(position);

        ArrayList<Materias> materiasEnElSemestre = new ArrayList<>();
        for (Materias unaMateria: listaMaterias) {
            if (unaMateria.getLSEMESTRE() == semestre) {
                if (unaMateria.getOBS().trim().equals("Reprobado") || unaMateria.getOBS().trim().equals("Abandono")) {
                    continue;
                }

                materiasEnElSemestre.add(unaMateria);

                if (unaMateria.getOBS().equals("FALTANTE")) {
                    holder.timelineView.setMarker(ContextCompat.getDrawable(contexto, R.drawable.ic_marker_inactive));
                }
            }
        }

            holder.tvSemestre.setText("Semestre " + semestre);

            AdaptadorSubItemHistorialAcademico adaptador = new AdaptadorSubItemHistorialAcademico(contexto, materiasEnElSemestre);
            holder.recyclerView.setAdapter(adaptador);
    }

    @Override
    public int getItemCount() {
        return semestres.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSemestre, tvMaterias;
        public TimelineView timelineView;
        public LinearLayout linearLayoutMaterias, linearLayoutItem;
        public ImageView checkImageView;
        public RecyclerView recyclerView;

        public MyViewHolder(View v, int viewType) {
            super(v);

            tvSemestre = v.findViewById(R.id.text_timeline_date);
            tvMaterias = v.findViewById(R.id.text_timeline_title);
            timelineView = v.findViewById(R.id.timeline);
            timelineView.initLine(viewType);
            linearLayoutMaterias = v.findViewById(R.id.ly_materias);
            linearLayoutItem = v.findViewById(R.id.ly_item);
            checkImageView = v.findViewById(R.id.checkImageView);

            recyclerView = v.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }

    }

}
