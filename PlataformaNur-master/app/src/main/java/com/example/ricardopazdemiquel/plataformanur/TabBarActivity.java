package com.example.ricardopazdemiquel.plataformanur;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;

import java.util.ArrayList;

public class TabBarActivity extends AppCompatActivity {

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
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentoContenedor, fragmentoGenerico)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* Por defecto la primera carrera será seleccionada */
        final ArrayList<AlumnoCarrera> carreras = Preferences.getAlumnoCarreras(this);
        AlumnoCarrera primeraCarrera = carreras.get(0);
        Preferences.setCarreraSeleccionada(this, primeraCarrera);

        if (Preferences.isFirstTime(this) && !estudiaUnaSolaCarrera()) {
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
    }

    public boolean estudiaUnaSolaCarrera() {
        return Preferences.getAlumnoCarreras(this).size() == 1;
    }

}
