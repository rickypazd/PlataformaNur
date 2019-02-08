package com.example.ricardopazdemiquel.plataformanur.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Preferences {

    public static JSONObject getUsr_log(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);
                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public static void setUsr_log(Context context,JSONObject usrLog) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        editor2.putString("usr_log", usrLog.toString());
        editor2.commit();
    }

    public static String getTokenAcceso(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = preferencias.getString("Token_Acceso", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            return usr;
        }
    }
    public static void setTokenAcceso(Context context,String usrLog) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        editor2.putString("Token_Acceso", usrLog.toString());
        editor2.commit();
    }

    public static Alumno getAlumno(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = preferencias.getString("alumno_info", "");
        if (alumnoJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            Alumno alumno = (Alumno) gson.fromJson(alumnoJson, Alumno.class);
            return alumno;
        }
    }

    public static void setAlumno(Context context, Alumno obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        editor2.putString("alumno_info", gson.toJson(obj));
        editor2.commit();
    }

    public static ArrayList<AlumnoCarrera> getAlumnoCarreras(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = preferencias.getString("alumno_carreras", "");
        if (alumnoJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<AlumnoCarrera> obj =  gson.fromJson(alumnoJson, new TypeToken<ArrayList<AlumnoCarrera>>(){}.getType());
            //ArrayList<AlumnoCarrera> obj = (ArrayList<AlumnoCarrera>) gson.fromJson(alumnoJson, ArrayList.class);
            return obj;
        }
    }

    public static void setAlumnoCarreras(Context context, ArrayList<AlumnoCarrera> obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        editor2.putString("alumno_carreras", gson.toJson(obj));
        editor2.commit();
    }
}
