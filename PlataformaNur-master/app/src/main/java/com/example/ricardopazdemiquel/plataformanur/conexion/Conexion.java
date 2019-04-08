package com.nur.notas.notasnur.conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.StringTokenizer;

import com.nur.notas.notasnur.R;
import com.nur.notas.notasnur.Utiles.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Conexion extends SQLiteOpenHelper {

    private final static String NOMBRE = "notas.nur";
    private final static int VERSION = 1;

    private static Conexion instancia;
    private Context contexto;

    public synchronized static Conexion getOrCreate() {
        if (instancia == null) {
            instancia = new Conexion(MyApp.getInstancia());
        }

        return instancia;
    }

    private Conexion(Context contexto) {
        super(contexto, NOMBRE, null, VERSION);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = contexto.getResources().getString(R.string.db_v1);

        StringTokenizer st = new StringTokenizer(sql, ";");
        while (st.hasMoreTokens()) {
            String sentencia = st.nextToken();
            db.execSQL(sentencia);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static boolean existe() {
        File baseDeDatos = MyApp.getInstancia().getDatabasePath(NOMBRE);

        return baseDeDatos.exists();
    }

    public int insertar(Tablas tabla, ContentValues valores) {
        SQLiteDatabase bd = getWritableDatabase();
        long ID = bd.insertOrThrow(tabla.toString(), null, valores);
        return (int) ID;
    }

    public void actualizar(Tablas tabla, ContentValues valores, String expresionWhere,
                           String[] parametrosWhere) {
        SQLiteDatabase bd = getWritableDatabase();
        bd.update(tabla.toString(), valores, expresionWhere, parametrosWhere);
    }

    public void eliminar(Tablas tabla, String expresionWhere, String[] parametrosWhere) {
        SQLiteDatabase bd = getWritableDatabase();
        bd.delete(tabla.toString(), expresionWhere, parametrosWhere);
    }

    public Cursor ejecutarConsulta(int consultas, String[] parametrosWhere) {
        SQLiteDatabase bd = getReadableDatabase();

        String consultaStr = contexto.getResources().getString(consultas);
        Cursor cursor = bd.rawQuery(consultaStr, parametrosWhere);
        return cursor;
    }

    public Cursor ejecutarConsulta(Tablas tabla, String[] columnas, String expresionWhere,
                                   String[] parametrosWhere) {
        SQLiteDatabase bd = getReadableDatabase();

        Cursor cursor = bd.query(tabla.toString(), columnas, expresionWhere, parametrosWhere, null,
                null, null);
        return cursor;
    }

    public void ejecutarSentencia(String sentencias) {
        SQLiteDatabase bd = getWritableDatabase();
        ejecutarSentencia(sentencias, bd);
    }

    public void ejecutarSentencia(int sentencias, SQLiteDatabase bd) {
        String sentencia = contexto.getResources().getString(sentencias);
        ejecutarSentencia(sentencia, bd);
    }

    public void ejecutarSentencia(String sentencias, SQLiteDatabase bd) {
        StringTokenizer st = new StringTokenizer(sentencias, ";");

        while (st.hasMoreTokens()) {
            String sentencia = st.nextToken();
            bd.execSQL(sentencia);
        }
    }

//    public void insertarMateri(Tablas tabla, ContentValues values, int cantidadColumnas) {
//        SQLiteDatabase bd = getWritableDatabase();
//
//        bd.beginTransaction();
//
//        for (int i = 0; i < values.size(); i += cantidadColumnas) {
//            bd.insertOrThrow(tabla.toString(), null, valores);
//        }
//
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject json = jsonArray.getJSONObject(i);
//
//                ContentValues values = new ContentValues();
//                for (String columna: columnas) {
//                    values.put(columna, json.get(columna));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // StringTokenizer sentencias = new StringTokenizer(lote, ";");
////        while (sentencias.hasMoreTokens()) {
////            String sentencia = sentencias.nextToken();
////            bd.insert()
////        }
//    }

//    public void ejecutarLote(Tablas tabla, String[] columnas, JSONArray jsonArray) {
//        SQLiteDatabase bd = getWritableDatabase();
//
//        bd.beginTransaction();
//
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject json = jsonArray.getJSONObject(i);
//
//                ContentValues values = new ContentValues();
//                for (String columna: columnas) {
//                    values.put(columna, json.get(columna));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // StringTokenizer sentencias = new StringTokenizer(lote, ";");
////        while (sentencias.hasMoreTokens()) {
////            String sentencia = sentencias.nextToken();
////            bd.insert()
////        }
//    }

}