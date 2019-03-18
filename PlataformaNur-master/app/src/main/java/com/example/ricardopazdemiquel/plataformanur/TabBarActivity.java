package com.example.ricardopazdemiquel.plataformanur;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Tools;
import com.example.ricardopazdemiquel.plataformanur.Utiles.ViewAnimation;
import com.example.ricardopazdemiquel.plataformanur.layouts.AdaptadorCarreras;

import java.util.ArrayList;

public class TabBarActivity extends AppCompatActivity {
    int ii = 1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_ofertas:
                    seleccionarFragmento("nav_ofertas");
                    return true;
                case R.id.nav_notas:
                    seleccionarFragmento("nav_notas");
                    return true;
                case R.id.nav_ajustes:
                    seleccionarFragmento("nav_ajustes");
                    return true;
            }
            return false;
        }

    };

    private void seleccionarFragmento(String fragmento) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (fragmento) {
            case "nav_ofertas":
                fragmentoGenerico = new fragmento_ofertas();
                break;

            case "nav_notas":
                fragmentoGenerico = new fragmento_notas();
                break;

            case "nav_ajustes":
                fragmentoGenerico = new fragmento_ajustes();
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

        if (Preferences.isFirstTime(this) && !estudiaUnaSolaCarrera()) {
            /* Por defecto la primera carrera será seleccionada */
            final ArrayList<AlumnoCarrera> carreras = Preferences.getAlumnoCarreras(this);

            final CharSequence[] items = new CharSequence[carreras.size()];

            for (int i = 0; i < carreras.size(); i++) {
                AlumnoCarrera carrera = carreras.get(i);
                items[i] = carrera.getSCARRERA_DSC();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una carrera")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AlumnoCarrera carreraSeleccionada = carreras.get(which);
                            Preferences.setCarreraSeleccionada(TabBarActivity.this, carreraSeleccionada);
                        }
                    });

            builder.show();
        }

        seleccionarFragmento("nav_notas");

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
        final NestedScrollView nested_scroll_view = findViewById(R.id.nested_scroll_view);

        spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("nur", "onItemSelected");

                AlumnoCarrera carrera = (AlumnoCarrera) spinnerCarrera.getSelectedItem();
                Preferences.setCarreraSeleccionada(TabBarActivity.this, carrera);
//                if (ii == 1) {
//                    ii++;
//                    return;
//                }
//                ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
//                    @Override
//                    public void onFinish() {
//                        Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
//                    }
//                });
//

                // ViewAnimation.collapse(lyt_expand_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("nur", "onNothingSelected");
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
