package com.example.ricardopazdemiquel.plataformanur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorHistorialAcademico;

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

        AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(this);

        if (carrera != null) {
            NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();

            List<Notas> notas = dao.seleccionar(carrera.getLCARRERA_ID(), 126);
            AdaptadorHistorialAcademico adaptador = new AdaptadorHistorialAcademico(this, notas);
            recyclerView.setAdapter(adaptador);
        }
    }
}
