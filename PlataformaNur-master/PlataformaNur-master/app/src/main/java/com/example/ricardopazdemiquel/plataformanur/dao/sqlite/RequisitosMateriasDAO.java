package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.RequisitosMaterias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla RequisitosMaterias
 */
class RequisitosMateriasDAO extends com.example.ricardopazdemiquel.plataformanur.dao.RequisitosMateriasDAO {

    private static final String ID = "ID";
    private static final String SMATERIA_DSC = "SMATERIA_DSC";
    private static final String MATERIA_ID = "MATERIA_ID";

    RequisitosMateriasDAO() {
		super(ID, SMATERIA_DSC, MATERIA_ID);
	}

    @Override
    public DTO seleccionar(Object llave) {
        if (!(llave instanceof Integer)) {
            throw new IllegalArgumentException("La llave debe ser un entero");
        }

        int ID = (int) llave;
        if (ID <= 0) {
            throw new IllegalArgumentException("El ID debe ser un entero positivo");
        }

        Conexion con = Conexion.getOrCreate();

        String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(ID) };

        Cursor cursor = con.ejecutarConsulta(Tablas.RequisitosMaterias, columnas, where, parametrosWhere);
        RequisitosMaterias objRequisitosMaterias = null;

        if (cursor.moveToFirst()) {
            objRequisitosMaterias = obtenerObjDeCursor(cursor);
        }

        return objRequisitosMaterias;
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        RequisitosMaterias objRequisitosMaterias = (RequisitosMaterias) obj;

        ContentValues valores = new ContentValues();
        valores.put(SMATERIA_DSC, objRequisitosMaterias.getSMATERIA_DSC());
		valores.put(MATERIA_ID, objRequisitosMaterias.getMATERIA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.RequisitosMaterias, valores);

        objRequisitosMaterias.setID(ID);
    }

    @Override
    public int insertar(JSONObject json) {
        RequisitosMaterias requisito = new RequisitosMaterias();

        try {
            requisito.setSMATERIA_DSC(json.isNull(SMATERIA_DSC) ? "" : json.getString(SMATERIA_DSC));
            if (!json.isNull(MATERIA_ID)) requisito.setMATERIA_ID(json.getInt(MATERIA_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues valores = new ContentValues();
        valores.put(SMATERIA_DSC, requisito.getSMATERIA_DSC());
        valores.put(MATERIA_ID, requisito.getMATERIA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.RequisitosMaterias, valores);

        return ID;
    }

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        RequisitosMaterias objRequisitosMaterias = (RequisitosMaterias) obj;

        ContentValues valores = new ContentValues();
        valores.put(SMATERIA_DSC, objRequisitosMaterias.getSMATERIA_DSC());
		valores.put(MATERIA_ID, objRequisitosMaterias.getMATERIA_ID());

		String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objRequisitosMaterias.getSMATERIA_DSC()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.RequisitosMaterias, valores, where, parametrosWhere);
    }

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        RequisitosMaterias objRequisitosMaterias = (RequisitosMaterias) obj;

        String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objRequisitosMaterias.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.RequisitosMaterias, where, parametrosWhere);
    }

    @Override
    public List<RequisitosMaterias> seleccionarTodos() {
        Conexion con = Conexion.getOrCreate();

        Cursor cursor = con.ejecutarConsulta(Tablas.RequisitosMaterias, columnas, null, null);
        List<RequisitosMaterias> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            RequisitosMaterias objRequisitosMaterias = obtenerObjDeCursor(cursor);
            lista.add(objRequisitosMaterias);
        }

        return lista;
    }

    private RequisitosMaterias obtenerObjDeCursor(Cursor cursor) {
        RequisitosMaterias objRequisitosMaterias = new RequisitosMaterias();

        objRequisitosMaterias.setID(cursor.getInt(cursor.getColumnIndex(ID)));
		objRequisitosMaterias.setSMATERIA_DSC(cursor.getString(cursor.getColumnIndex(SMATERIA_DSC)));
		objRequisitosMaterias.setMATERIA_ID(cursor.getInt(cursor.getColumnIndex(MATERIA_ID)));

        return objRequisitosMaterias;
    }

    @Override
    public void truncate() {
        Conexion con = Conexion.getOrCreate();
        con.ejecutarSentencia("delete from " + Tablas.RequisitosMaterias);
    }

    @Override
    public void insercionMasiva(JSONArray jsonArray) {
        Conexion conexion = Conexion.getOrCreate();
        SQLiteDatabase bd = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                values.put(SMATERIA_DSC, json.getString(SMATERIA_DSC));
                values.put(MATERIA_ID, json.getInt(MATERIA_ID));


                bd.insertOrThrow(Tablas.RequisitosMaterias.toString(), null, values);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bd.endTransaction();
    }

}