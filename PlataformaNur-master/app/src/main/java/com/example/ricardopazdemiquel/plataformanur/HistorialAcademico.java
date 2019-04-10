package com.nur.notas.notasnur;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.MateriasDAO;
import com.nur.notas.notasnur.dto.Materias;
import com.nur.notas.notasnur.adaptadores.AdaptadorHistorialAcademico;

import java.util.List;

public class HistorialAcademico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_academico);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pensum");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        MateriasDAO materiaDao = FactoryDAO.getOrCreate().newMateriasDAO();
        List<Materias> materias = materiaDao.seleccionarTodos();

        AdaptadorHistorialAcademico adaptador = new AdaptadorHistorialAcademico(this, materias);
        recyclerView.setAdapter(adaptador);
    }
}
