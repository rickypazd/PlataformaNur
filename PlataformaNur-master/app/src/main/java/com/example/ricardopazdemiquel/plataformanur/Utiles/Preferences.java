package com.nur.notas.notasnur.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

import com.nur.notas.notasnur.Objs.Alumno;
import com.nur.notas.notasnur.Objs.AlumnoCarrera;
import com.nur.notas.notasnur.Objs.Periodo;
import com.nur.notas.notasnur.Objs.PeriodoOferta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Preferences {

    public static String getTokenAcceso(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = pref.getString("Token_Acceso", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            return usr;
        }
    }

    public static void setTokenAcceso(Context context,String usrLog) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("Token_Acceso", usrLog.toString());
        edit.commit();
    }

    public static Alumno getAlumno(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = pref.getString("alumno_info", "");
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

    public static void setAlumno(Context context, JSONObject json) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        Alumno alumno = new Alumno();

        try {
            if (!json.isNull("LALUMNO_ID")) alumno.setId(json.getInt("LALUMNO_ID"));
            alumno.setRegistro(json.isNull("SREGISTRO") ? "" : json.get("SREGISTRO").toString().trim());
            alumno.setApellidoPaterno(json.isNull("SAPELLIDOP") ? "" : json.get("SAPELLIDOP").toString().trim());
            alumno.setApellidoMaterno(json.isNull("SAPELLIDOM") ? "" : json.get("SAPELLIDOM").toString().trim());
            alumno.setNombre(json.isNull("SNOMBRES") ? "" : json.get("SNOMBRES").toString().trim());
            alumno.setFechaNacimiento(json.isNull("DTFECHNAC") ? "" : json.get("DTFECHNAC").toString().trim());
            alumno.setSexo(json.isNull("SSEXO_DSC") ? "" : json.get("SSEXO_DSC").toString().trim());
            alumno.setCelular(json.isNull("SCELULAR") ? "" : json.get("SCELULAR").toString().trim());
            alumno.setTelefono(json.isNull("STELEFONO") ? "" : json.get("STELEFONO").toString().trim());
            alumno.setEmail(json.isNull("SEMAIL") ? "" : json.get("SEMAIL").toString().trim());
            alumno.setEstadoCivil(json.isNull("SESTADOCIVIL_DSC") ? "" : json.get("SESTADOCIVIL_DSC").toString().trim());
            alumno.setTipoSangre(json.isNull("STIPOSANGRE_DSC") ? "" : json.get("STIPOSANGRE_DSC").toString().trim());
            if (!json.isNull("BOOLACTIVOPASIVO")) alumno.setActivoPasivo(json.getInt("BOOLACTIVOPASIVO") == 1);
            if (!json.isNull("Data")) alumno.setHorasServicio(json.getInt("LHORASERVICIO"));
        } catch (Exception ignored) {

        }

        editor.putString("alumno_info", gson.toJson(alumno));
        editor.commit();
    }

    public static void setAlumnoImagenStr(Context context, String imagenStr) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("imagen_perfil", imagenStr);
        editor.commit();
    }

    public static String getAlumnoImagenStr(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = pref.getString("imagen_perfil", "");

        return usr;
    }

    public static ArrayList<AlumnoCarrera> getAlumnoCarreras(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = pref.getString("alumno_carreras", "");
        if (alumnoJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<AlumnoCarrera> obj =  gson.fromJson(alumnoJson, new TypeToken<ArrayList<AlumnoCarrera>>(){}.getType());

            return obj;
        }
    }

    public static void setAlumnoCarreras(Context context, JSONArray carreras) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ArrayList<AlumnoCarrera> alumnoCarreras = new ArrayList<>();

        try {
            for(int i = 0; i < carreras.length(); i++){
                JSONObject objJson = carreras.getJSONObject(i);

                AlumnoCarrera alumnoCarrera = new AlumnoCarrera();
                if (!objJson.isNull("LCARRERA_ID")) alumnoCarrera.setLCARRERA_ID(objJson.getInt("LCARRERA_ID"));
                alumnoCarrera.setSCODCENTRO(objJson.isNull("SCODCENTRO") ? "" : objJson.getString("SCODCENTRO"));
                alumnoCarrera.setSCENTRO_DSC(objJson.isNull("SCENTRO_DSC") ? "" : objJson.getString("SCENTRO_DSC"));
                alumnoCarrera.setSCODCARRERA(objJson.isNull("SCODCARRERA") ? "" : objJson.getString("SCODCARRERA"));
                alumnoCarrera.setSCARRERA_DSC(objJson.isNull("SCARRERA_DSC") ? "" : objJson.getString("SCARRERA_DSC"));
                if (!objJson.isNull("LCODPENSUM")) alumnoCarrera.setLCODPENSUM(objJson.getInt("LCODPENSUM"));
                if (!objJson.isNull("LCREDVENCIDOS")) alumnoCarrera.setLCREDVENCIDOS(objJson.getInt("LCREDVENCIDOS"));
                if (!objJson.isNull("LPERIODOINICIO")) alumnoCarrera.setLPERIODOINICIO(objJson.getString("LPERIODOINICIO"));
                if (!objJson.isNull("LPERIODOACTUAL_ID")) alumnoCarrera.setLPERIODOACTUAL_ID(objJson.getInt("LPERIODOACTUAL_ID"));
                if (!objJson.isNull("LPERIODOACTUAL")) alumnoCarrera.setLPERIODOACTUAL(objJson.getString("LPERIODOACTUAL"));
                if (!objJson.isNull("LPERIODOFIN")) alumnoCarrera.setLPERIODOFIN(objJson.getString("LPERIODOFIN"));
                alumnoCarrera.setDTFECHADMISION(objJson.isNull("DTFECHADMISION") ? "" : objJson.getString("DTFECHADMISION"));
                alumnoCarrera.setDTFECHEGRESO(objJson.isNull("DTFECHEGRESO") ? "" : objJson.getString("DTFECHEGRESO"));
                alumnoCarrera.setDTFECHTITULACION(objJson.isNull("DTFECHTITULACION") ? "" : objJson.getString("DTFECHTITULACION"));
                alumnoCarrera.setSTIPOGRADUACION_DSC(objJson.isNull("STIPOGRADUACION_DSC") ? "" : objJson.getString("STIPOGRADUACION_DSC"));

                alumnoCarreras.add(alumnoCarrera);
            }
        } catch (Exception ignored) {

        }

        Gson gson = new Gson();

        editor.putString("alumno_carreras", gson.toJson(alumnoCarreras));
        editor.commit();
    }

    public static ArrayList<Periodo> getPeriodos(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String periodosJson = pref.getString("periodos", "");

        if (periodosJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<Periodo> periodos =  gson.fromJson(periodosJson, new TypeToken<ArrayList<Periodo>>(){}.getType());

            return periodos;
        }
    }

    public static void setPeriodos(Context context, JSONArray periodosJson) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ArrayList<Periodo> periodos = new ArrayList<>();

        try {
            for(int i = 0; i < periodosJson.length(); i++){
                JSONObject objJson = periodosJson.getJSONObject(i);

                Periodo periodo = new Periodo();
                if (!objJson.isNull("LPERIODO_ID")) periodo.setLPERIODO_ID(objJson.getInt("LPERIODO_ID"));
                periodo.setSPERIODO_DSC(objJson.isNull("SPERIODO_DSC") ? "" : objJson.getString("SPERIODO_DSC"));

                periodos.add(periodo);
            }
        } catch (Exception ignored) {

        }

        Gson gson = new Gson();

        editor.putString("periodos", gson.toJson(periodos));
        editor.commit();
    }

    public static AlumnoCarrera getCarreraSeleccionada(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = pref.getString("carrera_seleccionada", "");
        if (alumnoJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            AlumnoCarrera alumno = (AlumnoCarrera) gson.fromJson(alumnoJson, AlumnoCarrera.class);
            return alumno;
        }
    }

    public static void setCarreraSeleccionada(Context context, AlumnoCarrera obj) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        editor.putString("carrera_seleccionada", gson.toJson(obj));
        editor.commit();
    }

    public static void setCarreraSeleccionada(Context context, JSONObject json) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        AlumnoCarrera carrera = new AlumnoCarrera();

        try {
            if (!json.isNull("LCARRERA_ID")) carrera.setLCARRERA_ID(json.getInt("LCARRERA_ID"));
            carrera.setSCODCENTRO(json.isNull("SCODCENTRO") ? "" : json.getString("SCODCENTRO"));
            carrera.setSCENTRO_DSC(json.isNull("SCENTRO_DSC") ? "" : json.getString("SCENTRO_DSC"));
            carrera.setSCODCARRERA(json.isNull("SCODCARRERA") ? "" : json.getString("SCODCARRERA"));
            carrera.setSCARRERA_DSC(json.isNull("SCARRERA_DSC") ? "" : json.getString("SCARRERA_DSC"));
            if (!json.isNull("LCODPENSUM")) carrera.setLCODPENSUM(json.getInt("LCODPENSUM"));
            if (!json.isNull("LCREDVENCIDOS")) carrera.setLCREDVENCIDOS(json.getInt("LCREDVENCIDOS"));
            if (!json.isNull("LPERIODOINICIO")) carrera.setLPERIODOINICIO(json.getString("LPERIODOINICIO"));
            if (!json.isNull("LPERIODOACTUAL_ID")) carrera.setLPERIODOACTUAL_ID(json.getInt("LPERIODOACTUAL_ID"));
            if (!json.isNull("LPERIODOACTUAL")) carrera.setLPERIODOACTUAL(json.getString("LPERIODOACTUAL"));
            if (!json.isNull("LPERIODOFIN")) carrera.setLPERIODOFIN(json.getString("LPERIODOFIN"));
            carrera.setDTFECHADMISION(json.isNull("DTFECHADMISION") ? "" : json.getString("DTFECHADMISION"));
            carrera.setDTFECHEGRESO(json.isNull("DTFECHEGRESO") ? "" : json.getString("DTFECHEGRESO"));
            carrera.setDTFECHTITULACION(json.isNull("DTFECHTITULACION") ? "" : json.getString("DTFECHTITULACION"));
            carrera.setSTIPOGRADUACION_DSC(json.isNull("STIPOGRADUACION_DSC") ? "" : json.getString("STIPOGRADUACION_DSC"));
        } catch (Exception ignored) {

        }

        editor.putString("carrera_seleccionada", gson.toJson(carrera));
        editor.commit();
    }

    public static boolean isFirstTime(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        boolean ranBefore = pref.getBoolean("RanBefore", false);

        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }

        return !ranBefore;
    }

    public static void setIsFirstTime(Context context, boolean isFirstTime) {
        SharedPreferences preferences = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("RanBefore", isFirstTime);
        editor.commit();
    }

    public static ArrayList<Periodo> getPeriodosOferta(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String objJson = preferencias.getString("periodos_oferta", "");
        if (objJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<Periodo> obj =  gson.fromJson(objJson, new TypeToken<ArrayList<Periodo>>(){}.getType());
            return obj;
        }
    }

    public static void setPeriodosOferta(Context context, JSONArray periodosJson) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ArrayList<PeriodoOferta> periodos = new ArrayList<>();

        try {
            for(int i = 0; i < periodosJson.length(); i++){
                JSONObject json = periodosJson.getJSONObject(i);

                PeriodoOferta periodo = new PeriodoOferta();
                if (!json.isNull("LPERIODO_ID")) periodo.setLPERIODO_ID(json.getInt("LPERIODO_ID"));
                periodo.setSPERIODO_DSC(json.isNull("SPERIODO_DSC") ? "" : json.getString("SPERIODO_DSC"));

                periodos.add(periodo);
            }
        } catch (Exception ignored) {

        }

        Gson gson = new Gson();

        editor.putString("periodos_oferta", gson.toJson(periodos));
        editor.commit();
    }

    public static String getRegistro(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = pref.getString("registro", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            return usr;
        }
    }

    public static void setRegistro(Context context, String pin) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("registro", pin.toString());
        editor.commit();
    }

    public static String getPin(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = preferencias.getString("pin", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            return usr;
        }
    }

    public static void setPin(Context context, String clave) {
        SharedPreferences pref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("pin", clave.toString());
        editor.commit();
    }

}
