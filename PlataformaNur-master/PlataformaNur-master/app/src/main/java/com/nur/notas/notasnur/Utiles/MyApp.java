package com.nur.notas.notasnur.Utiles;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Usuario on 10/02/2019.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        instancia = this;
    }

    private static MyApp instancia;

    public static MyApp getInstancia() {
        return instancia;
    }


}
