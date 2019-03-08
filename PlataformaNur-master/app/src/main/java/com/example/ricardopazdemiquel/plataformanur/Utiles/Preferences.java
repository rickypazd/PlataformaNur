package com.example.ricardopazdemiquel.plataformanur.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoOferta;
import com.example.ricardopazdemiquel.plataformanur.Objs.Periodo;
import com.example.ricardopazdemiquel.plataformanur.Objs.PeriodoOferta;
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

    public static void setAlumno(Context context, JSONObject obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        Alumno alumno = new Alumno();

        try {
            alumno.setId(obj.getInt("LALUMNO_ID"));
            alumno.setRegistro(obj.get("SREGISTRO").toString().trim());
            alumno.setApellidoPaterno(obj.get("SAPELLIDOP").toString().trim());
            alumno.setApellidoMaterno(obj.get("SAPELLIDOM").toString().trim());
            alumno.setNombre(obj.get("SNOMBRES").toString().trim());
            alumno.setFechaNacimiento(obj.get("DTFECHNAC").toString().trim());
            alumno.setSexo(obj.get("SSEXO_DSC").toString().trim());
            alumno.setCelular(obj.get("SCELULAR").toString().trim());
            alumno.setTelefono(obj.get("STELEFONO").toString().trim());
            alumno.setEmail(obj.get("SEMAIL").toString().trim());
            alumno.setEstadoCivil(obj.get("SESTADOCIVIL_DSC").toString().trim());
            alumno.setTipoSangre(obj.get("STIPOSANGRE_DSC").toString().trim());
            alumno.setActivoPasivo(obj.getInt("BOOLACTIVOPASIVO") == 1);
            alumno.setHorasServicio(obj.getJSONObject("Data").getInt("LHORASERVICIO"));
        } catch (Exception e) {

        }

        editor2.putString("alumno_info", gson.toJson(alumno));
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

    public static void setAlumnoCarreras(Context context, JSONArray carreras) {
        SharedPreferences myPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = myPref.edit();

        ArrayList<AlumnoCarrera> alumnoCarreras = new ArrayList<>();

        try {
            for(int i = 0; i < carreras.length(); i++){
                JSONObject objJson = carreras.getJSONObject(i);

                AlumnoCarrera alumnoCarrera = new AlumnoCarrera();
                alumnoCarrera.setLCARRERA_ID(objJson.getInt("LCARRERA_ID"));
                alumnoCarrera.setSCODCENTRO(objJson.getString("SCODCENTRO"));
                alumnoCarrera.setSCENTRO_DSC((objJson.getString("SCENTRO_DSC")));
                alumnoCarrera.setSCODCARRERA(objJson.getString("SCODCARRERA"));
                alumnoCarrera.setSCARRERA_DSC(objJson.getString("SCARRERA_DSC"));
                alumnoCarrera.setLCODPENSUM(objJson.getInt("LCODPENSUM"));
                alumnoCarrera.setLCREDVENCIDOS(objJson.getInt("LCREDVENCIDOS"));
                alumnoCarrera.setLPERIODOINICIO(objJson.getString("LPERIODOINICIO"));
                alumnoCarrera.setLPERIODOACTUAL_ID(objJson.getInt("LPERIODOACTUAL_ID"));
                alumnoCarrera.setLPERIODOACTUAL(objJson.getString("LPERIODOACTUAL"));
                alumnoCarrera.setLPERIODOFIN(objJson.getString("LPERIODOFIN"));
                alumnoCarrera.setDTFECHADMISION(objJson.getString("DTFECHADMISION"));
                alumnoCarrera.setDTFECHEGRESO(objJson.getString("DTFECHEGRESO"));
                alumnoCarrera.setDTFECHTITULACION(objJson.getString("DTFECHTITULACION"));
                alumnoCarrera.setSTIPOGRADUACION_DSC(objJson.getString("STIPOGRADUACION_DSC"));

                alumnoCarreras.add(alumnoCarrera);
            }
        } catch (Exception e) {

        }

        Gson gson = new Gson();

        editor2.putString("alumno_carreras", gson.toJson(alumnoCarreras));
        editor2.commit();
    }

    public static ArrayList<Periodo> getPeriodos(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String periodosJson = preferencias.getString("periodos", "");

        if (periodosJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<Periodo> periodos =  gson.fromJson(periodosJson, new TypeToken<ArrayList<Periodo>>(){}.getType());

            return periodos;
        }
    }

    public static void setPeriodos(Context context, JSONArray periodosJson) {
        SharedPreferences myPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = myPref.edit();

        ArrayList<Periodo> periodos = new ArrayList<>();

        try {
            for(int i = 0; i < periodosJson.length(); i++){
                JSONObject objJson = periodosJson.getJSONObject(i);

                Periodo periodo = new Periodo();
                periodo.setLPERIODO_ID(objJson.getInt("LPERIODO_ID"));
                periodo.setSPERIODO_DSC(objJson.getString("SPERIODO_DSC"));

                periodos.add(periodo);
            }
        } catch (Exception e) {

        }

        Gson gson = new Gson();

        editor2.putString("periodos", gson.toJson(periodos));
        editor2.commit();
    }


    /**
     * Si se tiene más de una carrera, hay que tener una como preferencia para la app
     * @param context
     * @return
     */
    public static AlumnoCarrera getCarreraSeleccionada(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String alumnoJson = preferencias.getString("carrera_seleccionada", "");
        if (alumnoJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            AlumnoCarrera alumno = (AlumnoCarrera) gson.fromJson(alumnoJson, AlumnoCarrera.class);
            return alumno;
        }
    }

    public static void setCarreraSeleccionada(Context context, AlumnoCarrera obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        editor2.putString("carrera_seleccionada", gson.toJson(obj));
        editor2.commit();
    }

    public static void setCarreraSeleccionada(Context context, JSONObject obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        AlumnoCarrera carrera = new AlumnoCarrera();

        try {
            carrera.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));
            carrera.setSCODCENTRO(obj.getString("SCODCENTRO"));
            carrera.setSCENTRO_DSC((obj.getString("SCENTRO_DSC")));
            carrera.setSCODCARRERA(obj.getString("SCODCARRERA"));
            carrera.setSCARRERA_DSC(obj.getString("SCARRERA_DSC"));
            carrera.setLCODPENSUM(obj.getInt("LCODPENSUM"));
            carrera.setLCREDVENCIDOS(obj.getInt("LCREDVENCIDOS"));
            carrera.setLPERIODOINICIO(obj.getString("LPERIODOINICIO"));
            carrera.setLPERIODOACTUAL_ID(obj.getInt("LPERIODOACTUAL_ID"));
            carrera.setLPERIODOACTUAL(obj.getString("LPERIODOACTUAL"));
            carrera.setLPERIODOFIN(!obj.isNull("LPERIODOFIN") ? obj.getString("LPERIODOFIN") : "");
            carrera.setDTFECHADMISION(obj.getString("DTFECHADMISION"));
            carrera.setDTFECHEGRESO(!obj.isNull("DTFECHEGRESO") ? obj.getString("DTFECHEGRESO") : "");
            carrera.setDTFECHTITULACION(!obj.isNull("DTFECHTITULACION") ? obj.getString("DTFECHTITULACION") : "");
            carrera.setSTIPOGRADUACION_DSC(!obj.isNull("STIPOGRADUACION_DSC") ? obj.getString("STIPOGRADUACION_DSC") : "");
        } catch (Exception e) {

        }

        editor2.putString("carrera_seleccionada", gson.toJson(carrera));
        editor2.commit();
    }

    public static boolean isFirstTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);

        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }

        return !ranBefore;
    }

    public static void setIsFirstTime(Context context, boolean isFirstTime) {
        SharedPreferences preferences = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("RanBefore", true);
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

    public static void setPeriodosOferta(Context context, ArrayList<Periodo> obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        editor2.putString("periodos_oferta", gson.toJson(obj));
        editor2.commit();
    }

    public static void setPeriodosOferta(Context context, JSONArray periodosJson) {
        SharedPreferences myPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = myPref.edit();

        ArrayList<PeriodoOferta> periodos = new ArrayList<>();

        try {
            for(int i = 0; i < periodosJson.length(); i++){
                JSONObject objJson = periodosJson.getJSONObject(i);

                PeriodoOferta periodo = new PeriodoOferta();
                periodo.setLPERIODO_ID(objJson.getInt("LPERIODO_ID"));
                periodo.setSPERIODO_DSC(objJson.getString("SPERIODO_DSC"));

                periodos.add(periodo);
            }
        } catch (Exception e) {

        }

        Gson gson = new Gson();

        editor2.putString("periodos_oferta", gson.toJson(periodos));
        editor2.commit();
    }

    public static ArrayList<AlumnoOferta> getAlumnoOferta(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String objJson = preferencias.getString("alumno_oferta", "");
        if (objJson.length() <= 0) {
            return null;
        } else {
            Gson gson = new Gson();
            ArrayList<AlumnoOferta> obj =  gson.fromJson(objJson, new TypeToken<ArrayList<AlumnoOferta>>(){}.getType());
            return obj;
        }
    }

    public static void setAlumnoOferta(Context context, ArrayList<AlumnoOferta> obj) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        Gson gson = new Gson();

        editor2.putString("alumno_oferta", gson.toJson(obj));
        editor2.commit();
    }

    public static String getRegistro(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
        String usr = preferencias.getString("registro", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            return usr;
        }
    }

    public static void setRegistro(Context context, String pin) {
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        editor2.putString("registro", pin.toString());
        editor2.commit();
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
        SharedPreferences preferencias2 = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        editor2.putString("pin", clave.toString());
        editor2.commit();
    }

}
