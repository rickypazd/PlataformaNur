package com.example.ricardopazdemiquel.plataformanur;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class TabBarActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

      //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
