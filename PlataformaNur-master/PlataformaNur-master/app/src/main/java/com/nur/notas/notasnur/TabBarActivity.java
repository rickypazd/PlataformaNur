package com.nur.notas.notasnur;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.nur.notas.notasnur.objetos.AlumnoCarrera;
import com.nur.notas.notasnur.utiles.Constantes;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.adaptadores.AdaptadorCarreras;

import java.util.ArrayList;

public class TabBarActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_ofertas:
                    seleccionarFragmento(Constantes.TAB_OFERTAS);
                    return true;
                case R.id.nav_notas:
                    seleccionarFragmento(Constantes.TAB_NOTAS);
                    return true;
                case R.id.nav_ajustes:
                    seleccionarFragmento(Constantes.TAB_PERFIL);
                    return true;
            }
            return false;
        }

    };

    private void seleccionarFragmento(String fragmento) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (fragmento) {
            case Constantes.TAB_OFERTAS:
                fragmentoGenerico = new FragmentoOfertas();
                break;

            case Constantes.TAB_NOTAS:
                fragmentoGenerico = new FragmentoNotas();
                break;

            case Constantes.TAB_PERFIL:
                fragmentoGenerico = new FragmentoPerfil();
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentoContenedor, fragmentoGenerico)
                    .commit();
        }
    }

    Spinner spinnerCarrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        seleccionarFragmento(Constantes.TAB_NOTAS);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerCarrera = findViewById(R.id.spinnerCarrera);

        if (getSupportActionBar() != null) {
            if (estudiaUnaSolaCarrera()) {
                spinnerCarrera.setVisibility(View.GONE);
                getSupportActionBar().setTitle(Preferences.getCarreraSeleccionada(this).getSCARRERA_DSC());
            } else {
                getSupportActionBar().setTitle(null);
            }
        }

        ArrayList<AlumnoCarrera> carreras = Preferences.getAlumnoCarreras(this);
        AdaptadorCarreras adaptador = new AdaptadorCarreras(this, carreras);
        spinnerCarrera.setAdapter(adaptador);
        final View lyt_expand_text = findViewById(R.id.lyt_expand_text);
        lyt_expand_text.setVisibility(View.GONE);

        spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AlumnoCarrera carrera = (AlumnoCarrera) spinnerCarrera.getSelectedItem();
                Preferences.setCarreraSeleccionada(TabBarActivity.this, carrera);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        for (int i = 0; i < carreras.size(); i++) {
            AlumnoCarrera carrera = carreras.get(i);
            AlumnoCarrera carreraSeleccionada = Preferences.getCarreraSeleccionada(this);
            if (carreraSeleccionada.getLCARRERA_ID() == carrera.getLCARRERA_ID()) {
                spinnerCarrera.setSelection(i);
                break;
            }
        }
    }

    public boolean estudiaUnaSolaCarrera() {
        return Preferences.getAlumnoCarreras(this) != null && Preferences.getAlumnoCarreras(this).size() == 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
