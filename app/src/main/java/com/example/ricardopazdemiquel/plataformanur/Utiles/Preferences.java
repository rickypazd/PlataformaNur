package com.example.ricardopazdemiquel.plataformanur.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
