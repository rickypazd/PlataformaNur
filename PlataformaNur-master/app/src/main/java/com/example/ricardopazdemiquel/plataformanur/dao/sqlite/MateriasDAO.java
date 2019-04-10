package com.nur.notas.notasnur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.nur.notas.notasnur.objetos.AlumnoCarrera;
import com.nur.notas.notasnur.utiles.MyApp;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.conexion.Conexion;
import com.nur.notas.notasnur.conexion.Tablas;
import com.nur.notas.notasnur.dto.DTO;
import com.nur.notas.notasnur.dto.Materias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla Materias
 */
class MateriasDAO extends com.nur.notas.notasnur.dao.MateriasDAO {

	private static final String ID = "ID";
    private static final String SSIGLACODIGO = "SSIGLACODIGO";
    private static final String SCENTRO = "SCENTRO";
    private static final String SMATERIA_DSC = "SMATERIA_DSC";
    private static final String LCREDITOS = "LCREDITOS";
    private static final String LNOTA = "LNOTA";
    private static final String SPERIODO_DSC = "SPERIODO_DSC";
    private static final String OBS = "OBS";
    private static final String LPOSICION = "LPOSICION";
    private static final String SNROCARRERA = "SNROCARRERA";
    private static final String LPENSUM = "LPENSUM";
    private static final String CODMATERIA = "CODMATERIA";
    private static final String LSEMESTRE = "LSEMESTRE";
    private static final String LMATERIA_ID = "LMATERIA_ID";
    private static final String LCARRERA_ID = "LCARRERA_ID";

    MateriasDAO() {
		super(ID, SSIGLACODIGO, SCENTRO, SMATERIA_DSC, LCREDITOS, LNOTA, SPERIODO_DSC, OBS, LPOSICION, SNROCARRERA, LPENSUM, CODMATERIA, LSEMESTRE, LMATERIA_ID, LCARRERA_ID);
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

        Cursor cursor = con.ejecutarConsulta(Tablas.Materias, columnas, where, parametrosWhere);
        Materias objMaterias = null;

        if (cursor.moveToFirst()) {
            objMaterias = obtenerObjDeCursor(cursor);
        }

        return objMaterias;
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        Materias objMaterias = (Materias) obj;

        ContentValues valores = new ContentValues();
		valores.put(SSIGLACODIGO, objMaterias.getSSIGLACODIGO());
		valores.put(SCENTRO, objMaterias.getSCENTRO());
		valores.put(SMATERIA_DSC, objMaterias.getSMATERIA_DSC());
		valores.put(LCREDITOS, objMaterias.getLCREDITOS());
		valores.put(LNOTA, objMaterias.getLNOTA());
		valores.put(SPERIODO_DSC, objMaterias.getSPERIODO_DSC());
		valores.put(OBS, objMaterias.getOBS());
		valores.put(LPOSICION, objMaterias.getLPOSICION());
		valores.put(SNROCARRERA, objMaterias.getSNROCARRERA());
		valores.put(LPENSUM, objMaterias.getLPENSUM());
		valores.put(CODMATERIA, objMaterias.getCODMATERIA());
		valores.put(LSEMESTRE, objMaterias.getLSEMESTRE());
		valores.put(LMATERIA_ID, objMaterias.getLMATERIA_ID());
        valores.put(LCARRERA_ID, objMaterias.getLCARRERA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.Materias, valores);

        objMaterias.setID(ID);
    }

    @Override
    public int insertar(JSONObject json) {
        Materias materia = new Materias();

        try {
            materia.setSSIGLACODIGO(json.isNull(SSIGLACODIGO) ? "" : json.getString(SSIGLACODIGO));
            materia.setSCENTRO(json.isNull(SCENTRO) ? "" : json.getString(SCENTRO));
            materia.setSMATERIA_DSC(json.isNull(SMATERIA_DSC) ? "" : json.getString(SMATERIA_DSC));
            if (!json.isNull(LCREDITOS)) materia.setLCREDITOS(json.getInt(LCREDITOS));
            if (!json.isNull(LNOTA)) materia.setLNOTA(json.getInt(LNOTA));
            materia.setSPERIODO_DSC(json.isNull(LCREDITOS) ? "" : json.getString(SPERIODO_DSC));
            materia.setOBS(json.isNull(LNOTA) ? "" : json.getString(OBS));
            if (!json.isNull(LPOSICION)) materia.setLPOSICION(json.getInt(LPOSICION));
            materia.setSNROCARRERA(json.isNull(SPERIODO_DSC) ? "" : json.getString(SNROCARRERA));
            if (!json.isNull(LPENSUM)) materia.setLPENSUM(json.getInt(LPENSUM));
            materia.setCODMATERIA(json.isNull(OBS) ? "" : json.getString(CODMATERIA));
            if (!json.isNull(LSEMESTRE)) materia.setLSEMESTRE(json.getInt(LSEMESTRE));
            if (!json.isNull(LMATERIA_ID)) materia.setLMATERIA_ID(json.getInt(LMATERIA_ID));
            if (!json.isNull(LCARRERA_ID)) materia.setLCARRERA_ID(json.getInt(LCARRERA_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues valores = new ContentValues();
        valores.put(SSIGLACODIGO, materia.getSSIGLACODIGO());
        valores.put(SCENTRO, materia.getSCENTRO());
        valores.put(SMATERIA_DSC, materia.getSMATERIA_DSC());
        valores.put(LCREDITOS, materia.getLCREDITOS());
        valores.put(LNOTA, materia.getLNOTA());
        valores.put(SPERIODO_DSC, materia.getSPERIODO_DSC());
        valores.put(OBS, materia.getOBS());
        valores.put(LPOSICION, materia.getLPOSICION());
        valores.put(SNROCARRERA, materia.getSNROCARRERA());
        valores.put(LPENSUM, materia.getLPENSUM());
        valores.put(CODMATERIA, materia.getCODMATERIA());
        valores.put(LSEMESTRE, materia.getLSEMESTRE());
        valores.put(LMATERIA_ID, materia.getLMATERIA_ID());
        valores.put(LCARRERA_ID, materia.getLCARRERA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.Materias, valores);

        return ID;
    }

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        Materias objMaterias = (Materias) obj;

        ContentValues valores = new ContentValues();
		valores.put(SSIGLACODIGO, objMaterias.getSSIGLACODIGO());
		valores.put(SCENTRO, objMaterias.getSCENTRO());
		valores.put(SMATERIA_DSC, objMaterias.getSMATERIA_DSC());
		valores.put(LCREDITOS, objMaterias.getLCREDITOS());
		valores.put(LNOTA, objMaterias.getLNOTA());
		valores.put(SPERIODO_DSC, objMaterias.getSPERIODO_DSC());
		valores.put(OBS, objMaterias.getOBS());
		valores.put(LPOSICION, objMaterias.getLPOSICION());
		valores.put(SNROCARRERA, objMaterias.getSNROCARRERA());
		valores.put(LPENSUM, objMaterias.getLPENSUM());
		valores.put(CODMATERIA, objMaterias.getCODMATERIA());
		valores.put(LSEMESTRE, objMaterias.getLSEMESTRE());
		valores.put(LMATERIA_ID, objMaterias.getLMATERIA_ID());
        valores.put(LCARRERA_ID, objMaterias.getLCARRERA_ID());

		String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objMaterias.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.Materias, valores, where, parametrosWhere);
    }

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        Materias objMaterias = (Materias) obj;

        String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objMaterias.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.Materias, where, parametrosWhere);
    }

    @Override
    public List<Materias> seleccionarTodos() {
        Conexion con = Conexion.getOrCreate();

        AlumnoCarrera carrera = Preferences.getCarreraSeleccionada(MyApp.getInstancia());
        String where = "LCARRERA_ID = ?";
        String[] parametrosWhere = { String.valueOf(carrera.getLCARRERA_ID()) };

        Cursor cursor = con.ejecutarConsulta(Tablas.Materias, columnas, where, parametrosWhere);
        List<Materias> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Materias objMaterias = obtenerObjDeCursor(cursor);
            lista.add(objMaterias);
        }

        return lista;
    }

    private Materias obtenerObjDeCursor(Cursor cursor) {
        Materias objMaterias = new Materias();

		objMaterias.setID(cursor.isNull(cursor.getColumnIndex(ID)) ? 0 : cursor.getInt(cursor.getColumnIndex(ID)));
		objMaterias.setSSIGLACODIGO(cursor.isNull(cursor.getColumnIndex(SSIGLACODIGO)) ? "" : cursor.getString(cursor.getColumnIndex(SSIGLACODIGO)));
		objMaterias.setSCENTRO(cursor.isNull(cursor.getColumnIndex(SCENTRO)) ? "" : cursor.getString(cursor.getColumnIndex(SCENTRO)));
		objMaterias.setSMATERIA_DSC(cursor.isNull(cursor.getColumnIndex(SMATERIA_DSC)) ? "" : cursor.getString(cursor.getColumnIndex(SMATERIA_DSC)));
		objMaterias.setLCREDITOS(cursor.isNull(cursor.getColumnIndex(LCREDITOS)) ? 0 : cursor.getInt(cursor.getColumnIndex(LCREDITOS)));
		objMaterias.setLNOTA(cursor.isNull(cursor.getColumnIndex(LNOTA)) ? 0 : cursor.getInt(cursor.getColumnIndex(LNOTA)));
		objMaterias.setSPERIODO_DSC(cursor.isNull(cursor.getColumnIndex(SPERIODO_DSC)) ? "" : cursor.getString(cursor.getColumnIndex(SPERIODO_DSC)));
		objMaterias.setOBS(cursor.isNull(cursor.getColumnIndex(OBS)) ? "" : cursor.getString(cursor.getColumnIndex(OBS)));
		objMaterias.setLPOSICION(cursor.isNull(cursor.getColumnIndex(LPOSICION)) ? 0 : cursor.getInt(cursor.getColumnIndex(LPOSICION)));
		objMaterias.setSNROCARRERA(cursor.isNull(cursor.getColumnIndex(SNROCARRERA)) ? "" : cursor.getString(cursor.getColumnIndex(SNROCARRERA)));
		objMaterias.setLPENSUM(cursor.isNull(cursor.getColumnIndex(LPENSUM)) ? 0 : cursor.getInt(cursor.getColumnIndex(LPENSUM)));
		objMaterias.setCODMATERIA(cursor.isNull(cursor.getColumnIndex(CODMATERIA)) ? "" : cursor.getString(cursor.getColumnIndex(CODMATERIA)));
		objMaterias.setLSEMESTRE(cursor.isNull(cursor.getColumnIndex(LSEMESTRE)) ? 0 : cursor.getInt(cursor.getColumnIndex(LSEMESTRE)));
		objMaterias.setLMATERIA_ID(cursor.isNull(cursor.getColumnIndex(LMATERIA_ID)) ? 0 : cursor.getInt(cursor.getColumnIndex(LMATERIA_ID)));
        objMaterias.setLCARRERA_ID(cursor.isNull(cursor.getColumnIndex(LCARRERA_ID)) ? 0 : cursor.getInt(cursor.getColumnIndex(LCARRERA_ID)));

        return objMaterias;
    }

    @Override
    public void truncate() {
        Conexion con = Conexion.getOrCreate();
        con.ejecutarSentencia("delete from " + Tablas.Materias);
    }

    @Override
    public void insercionMasiva(int carreraId, JSONArray jsonArray) { /* [{MATERIAS:JSONObject, REQUISITOS:JSONArray}] */ // JSONArray jsonArray, JSONArray requisitosArray) {
        insercionMasiva(carreraId, jsonArray, false);
    }

    public void insercionMasiva(int carreraId, JSONArray jsonArray, boolean materiaFaltante) {
        Conexion conexion = Conexion.getOrCreate();
        SQLiteDatabase bd = conexion.getWritableDatabase();

        ContentValues values;
        ContentValues requisitosValues;

        bd.beginTransaction();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                JSONObject materiaJson = json.getJSONObject("MATERIAS");
                JSONArray requisitosJson = json.getJSONArray("REQUISITOS");

                values = new ContentValues();
                values.put(SSIGLACODIGO, materiaJson.isNull(SSIGLACODIGO) ? "" : materiaJson.getString(SSIGLACODIGO));
                values.put(SCENTRO, materiaJson.isNull(SCENTRO) ? "" : materiaJson.getString(SCENTRO));
                values.put(SMATERIA_DSC, materiaJson.isNull(SMATERIA_DSC) ? "" : materiaJson.getString(SMATERIA_DSC));
                values.put(LCREDITOS, materiaJson.isNull(LCREDITOS) ? 0 : materiaJson.getInt(LCREDITOS));
                values.put(LNOTA, materiaJson.isNull(LNOTA) ? 0 : materiaJson.getInt(LNOTA));
                values.put(SPERIODO_DSC, materiaJson.isNull(SPERIODO_DSC) ? "" : materiaJson.getString(SPERIODO_DSC));
                values.put(OBS, materiaJson.isNull(OBS) ? "" : materiaFaltante ? "FALTANTE" : materiaJson.getString(OBS));
                values.put(LPOSICION, materiaJson.isNull(LPOSICION) ? 0 : materiaJson.getInt(LPOSICION));
                values.put(SNROCARRERA, materiaJson.isNull(SNROCARRERA) ? "" : materiaJson.getString(SNROCARRERA));
                values.put(LPENSUM, materiaJson.isNull(LPENSUM) ? 0 : materiaJson.getInt(LPENSUM));
                values.put(CODMATERIA, materiaJson.isNull(CODMATERIA) ? "" : materiaJson.getString(CODMATERIA));
                values.put(LSEMESTRE, materiaJson.isNull(LSEMESTRE) ? 0 : materiaJson.getInt(LSEMESTRE));
                values.put(LMATERIA_ID, materiaJson.isNull(LMATERIA_ID) ? 0 : materiaJson.getInt(LMATERIA_ID));
                values.put(LCARRERA_ID, carreraId);

                int ID = (int) bd.insertOrThrow(Tablas.Materias.toString(), null, values);

                for (int j = 0; j < requisitosJson.length(); j++) {
                    JSONObject requisitoJson = requisitosJson.getJSONObject(j);

                    requisitosValues = new ContentValues();
                    requisitosValues.put("SMATERIA_DSC", requisitoJson.isNull("SMATERIA_DSC") ? "" : requisitoJson.getString("SMATERIA_DSC"));
                    requisitosValues.put("MATERIA_ID", ID);

                    bd.insertOrThrow(Tablas.RequisitosMaterias.toString(), null, requisitosValues);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bd.setTransactionSuccessful();
        bd.endTransaction();
    }

}
