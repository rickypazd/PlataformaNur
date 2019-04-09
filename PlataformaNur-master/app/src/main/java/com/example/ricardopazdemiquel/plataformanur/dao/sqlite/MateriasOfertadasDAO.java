package com.nur.notas.notasnur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.nur.notas.notasnur.conexion.Conexion;
import com.nur.notas.notasnur.conexion.Tablas;
import com.nur.notas.notasnur.dto.DTO;
import com.nur.notas.notasnur.dto.MateriasOfertadas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla MateriasOfertadas
 */
class MateriasOfertadasDAO extends com.nur.notas.notasnur.dao.MateriasOfertadasDAO {

    private static final String ID = "ID";
    private static final String LGRUPO_ID = "LGRUPO_ID";
    private static final String LCENTRO_ID = "LCENTRO_ID";
    private static final String SCENTRO_DSC = "SCENTRO_DSC";
    private static final String SMATERIA_DSC = "SMATERIA_DSC";
    private static final String LSEMESTRE = "LSEMESTRE";
    private static final String LCREDITOS = "LCREDITOS";
    private static final String LLABORATORIO = "LLABORATORIO";
    private static final String DOCENTE = "DOCENTE";
    private static final String SCODMATERIA = "SCODMATERIA";
    private static final String CASILLA = "CASILLA";
    private static final String SCODGRUPO = "SCODGRUPO";
    private static final String SSEMANA = "SSEMANA";
    private static final String LESTADOGRUPO_ID = "LESTADOGRUPO_ID";
    private static final String SESTADOGRUPO_DSC = "SESTADOGRUPO_DSC";
    private static final String SOBS1 = "SOBS1";
    private static final String LPERIODO_ID = "LPERIODO_ID";
    private static final String LCARRERA_ID = "LCARRERA_ID";

    MateriasOfertadasDAO() {
		super(ID, LGRUPO_ID, LCENTRO_ID, SCENTRO_DSC, SMATERIA_DSC, LSEMESTRE, LCREDITOS, LLABORATORIO, DOCENTE, SCODMATERIA, CASILLA, SCODGRUPO, SSEMANA, LESTADOGRUPO_ID, SESTADOGRUPO_DSC, SOBS1, LPERIODO_ID, LCARRERA_ID);
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

        String where = "codigo_id = ?";
        String[] parametrosWhere = { String.valueOf(ID) };

        Cursor cursor = con.ejecutarConsulta(Tablas.MateriasOfertadas, columnas, where, parametrosWhere);
        MateriasOfertadas objMateriasOfertadas = null;

        if (cursor.moveToFirst()) {
            objMateriasOfertadas = obtenerObjDeCursor(cursor);
        }

        return objMateriasOfertadas;
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        MateriasOfertadas objMateriasOfertadas = (MateriasOfertadas) obj;

        ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, objMateriasOfertadas.getLGRUPO_ID());
		valores.put(LCENTRO_ID, objMateriasOfertadas.getLCENTRO_ID());
		valores.put(SCENTRO_DSC, objMateriasOfertadas.getSCENTRO_DSC());
		valores.put(SMATERIA_DSC, objMateriasOfertadas.getSMATERIA_DSC());
		valores.put(LSEMESTRE, objMateriasOfertadas.getLSEMESTRE());
		valores.put(LCREDITOS, objMateriasOfertadas.getLCREDITOS());
		valores.put(LLABORATORIO, objMateriasOfertadas.getLLABORATORIO());
		valores.put(DOCENTE, objMateriasOfertadas.getDOCENTE());
		valores.put(SCODMATERIA, objMateriasOfertadas.getSCODMATERIA());
		valores.put(CASILLA, objMateriasOfertadas.getCASILLA());
		valores.put(SCODGRUPO, objMateriasOfertadas.getSCODGRUPO());
		valores.put(SSEMANA, objMateriasOfertadas.getSSEMANA());
		valores.put(LESTADOGRUPO_ID, objMateriasOfertadas.getLESTADOGRUPO_ID());
		valores.put(SESTADOGRUPO_DSC, objMateriasOfertadas.getSESTADOGRUPO_DSC());
		valores.put(SOBS1, objMateriasOfertadas.getSOBS1());
		valores.put(LPERIODO_ID, objMateriasOfertadas.getLPERIODO_ID());
		valores.put(LCARRERA_ID, objMateriasOfertadas.getLCARRERA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.MateriasOfertadas, valores);

        // objMateriasOfertadas.setID(ID);
    }

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        MateriasOfertadas objMateriasOfertadas = (MateriasOfertadas) obj;

        ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, objMateriasOfertadas.getLGRUPO_ID());
		valores.put(LCENTRO_ID, objMateriasOfertadas.getLCENTRO_ID());
		valores.put(SCENTRO_DSC, objMateriasOfertadas.getSCENTRO_DSC());
		valores.put(SMATERIA_DSC, objMateriasOfertadas.getSMATERIA_DSC());
		valores.put(LSEMESTRE, objMateriasOfertadas.getLSEMESTRE());
		valores.put(LCREDITOS, objMateriasOfertadas.getLCREDITOS());
		valores.put(LLABORATORIO, objMateriasOfertadas.getLLABORATORIO());
		valores.put(DOCENTE, objMateriasOfertadas.getDOCENTE());
		valores.put(SCODMATERIA, objMateriasOfertadas.getSCODMATERIA());
		valores.put(CASILLA, objMateriasOfertadas.getCASILLA());
		valores.put(SCODGRUPO, objMateriasOfertadas.getSCODGRUPO());
		valores.put(SSEMANA, objMateriasOfertadas.getSSEMANA());
		valores.put(LESTADOGRUPO_ID, objMateriasOfertadas.getLESTADOGRUPO_ID());
		valores.put(SESTADOGRUPO_DSC, objMateriasOfertadas.getSESTADOGRUPO_DSC());
		valores.put(SOBS1, objMateriasOfertadas.getSOBS1());
		valores.put(LPERIODO_ID, objMateriasOfertadas.getLPERIODO_ID());
		valores.put(LCARRERA_ID, objMateriasOfertadas.getLCARRERA_ID());

		String where = "LPERIODO_ID = ? and LCARRERA_ID = ? and SCODMATERIA = ?";
		String[] parametrosWhere = { String.valueOf(objMateriasOfertadas.getLPERIODO_ID()),
				String.valueOf(objMateriasOfertadas.getLCARRERA_ID()),
				String.valueOf(objMateriasOfertadas.getSCODMATERIA()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.MateriasOfertadas, valores, where, parametrosWhere);
    }

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        MateriasOfertadas objMateriasOfertadas = (MateriasOfertadas) obj;
        if (objMateriasOfertadas.getID() < 0) {
            throw new IllegalArgumentException("No se puede eliminar un MateriasOfertadas con ID <= 0");
        }

        String where = "codigo_id = ?";
        String[] parametrosWhere = { String.valueOf(objMateriasOfertadas.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.MateriasOfertadas, where, parametrosWhere);
    }

    @Override
    public List<MateriasOfertadas> seleccionarTodos() {
        Conexion con = Conexion.getOrCreate();

        Cursor cursor = con.ejecutarConsulta(Tablas.MateriasOfertadas, columnas, null, null);
        List<MateriasOfertadas> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            MateriasOfertadas objMateriasOfertadas = obtenerObjDeCursor(cursor);
            lista.add(objMateriasOfertadas);
        }

        return lista;
    }

    private MateriasOfertadas obtenerObjDeCursor(Cursor cursor) {
        MateriasOfertadas objMateriasOfertadas = new MateriasOfertadas();

		objMateriasOfertadas.setID(cursor.getInt(cursor.getColumnIndex(ID)));
		objMateriasOfertadas.setLGRUPO_ID(cursor.getInt(cursor.getColumnIndex(LGRUPO_ID)));
		objMateriasOfertadas.setLCENTRO_ID(cursor.getInt(cursor.getColumnIndex(LCENTRO_ID)));
		objMateriasOfertadas.setSCENTRO_DSC(cursor.getString(cursor.getColumnIndex(SCENTRO_DSC)));
		objMateriasOfertadas.setSMATERIA_DSC(cursor.getString(cursor.getColumnIndex(SMATERIA_DSC)));
		objMateriasOfertadas.setLSEMESTRE(cursor.getInt(cursor.getColumnIndex(LSEMESTRE)));
		objMateriasOfertadas.setLCREDITOS(cursor.getInt(cursor.getColumnIndex(LCREDITOS)));
		objMateriasOfertadas.setLLABORATORIO(cursor.getInt(cursor.getColumnIndex(LLABORATORIO)));
		objMateriasOfertadas.setDOCENTE(cursor.getString(cursor.getColumnIndex(DOCENTE)));
		objMateriasOfertadas.setSCODMATERIA(cursor.getString(cursor.getColumnIndex(SCODMATERIA)));
		objMateriasOfertadas.setCASILLA(cursor.getString(cursor.getColumnIndex(CASILLA)));
		objMateriasOfertadas.setSCODGRUPO(cursor.getString(cursor.getColumnIndex(SCODGRUPO)));
		objMateriasOfertadas.setSSEMANA(cursor.getString(cursor.getColumnIndex(SSEMANA)));
		objMateriasOfertadas.setLESTADOGRUPO_ID(cursor.getInt(cursor.getColumnIndex(LESTADOGRUPO_ID)));
		objMateriasOfertadas.setSESTADOGRUPO_DSC(cursor.getString(cursor.getColumnIndex(SESTADOGRUPO_DSC)));
		objMateriasOfertadas.setSOBS1(cursor.getString(cursor.getColumnIndex(SOBS1)));
		objMateriasOfertadas.setLPERIODO_ID(cursor.getInt(cursor.getColumnIndex(LPERIODO_ID)));
		objMateriasOfertadas.setLCARRERA_ID(cursor.getInt(cursor.getColumnIndex(LCARRERA_ID)));

        return objMateriasOfertadas;
    }

	@Override
	public int insertar(JSONObject json) {
		MateriasOfertadas materiaOfertada = new MateriasOfertadas();

		try {
			if (!json.isNull("LGRUPO_ID")) materiaOfertada.setLGRUPO_ID(json.getInt("LGRUPO_ID"));
			if (!json.isNull("LCENTRO_ID")) materiaOfertada.setLCENTRO_ID(json.getInt("LCENTRO_ID"));
			materiaOfertada.setSCENTRO_DSC(json.isNull("SCENTRO_DSC") ? "" : json.getString("SCENTRO_DSC"));
			materiaOfertada.setSMATERIA_DSC(json.isNull("SMATERIA_DSC") ? "" : json.getString("SMATERIA_DSC"));
			if (!json.isNull("LSEMESTRE")) materiaOfertada.setLSEMESTRE(json.getInt("LSEMESTRE"));
			if (!json.isNull("LCREDITOS")) materiaOfertada.setLCREDITOS(json.getInt("LCREDITOS"));
			if (!json.isNull("LLABORATORIO")) materiaOfertada.setLLABORATORIO(json.getInt("LLABORATORIO"));
			materiaOfertada.setDOCENTE(json.isNull("DOCENTE") ? "" : json.getString("DOCENTE"));
			materiaOfertada.setSCODMATERIA(json.isNull("SCODMATERIA") ? "" : json.getString("SCODMATERIA"));
			materiaOfertada.setCASILLA(json.isNull("CASILLA") ? "" : json.getString("CASILLA"));
			materiaOfertada.setSCODGRUPO(json.isNull("SCODGRUPO") ? "" : json.getString("SCODGRUPO"));
			materiaOfertada.setSSEMANA(json.isNull("SSEMANA") ? "" : json.getString("SSEMANA"));
			if (!json.isNull("LESTADOGRUPO_ID")) materiaOfertada.setLESTADOGRUPO_ID(json.getInt("LESTADOGRUPO_ID"));
			materiaOfertada.setSESTADOGRUPO_DSC(json.isNull("SESTADOGRUPO_DSC") ? "" : json.getString("SESTADOGRUPO_DSC"));
			materiaOfertada.setSOBS1(json.isNull("SOBS1") ? "" : json.getString("SOBS1"));
			if (!json.isNull("LPERIODO_ID")) materiaOfertada.setLPERIODO_ID(json.getInt("LPERIODO_ID"));
			if (!json.isNull("LCARRERA_ID")) materiaOfertada.setLCARRERA_ID(json.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, materiaOfertada.getLGRUPO_ID());
		valores.put(LCENTRO_ID, materiaOfertada.getLCENTRO_ID());
		valores.put(SCENTRO_DSC, materiaOfertada.getSCENTRO_DSC());
		valores.put(SMATERIA_DSC, materiaOfertada.getSMATERIA_DSC());
		valores.put(LSEMESTRE, materiaOfertada.getLSEMESTRE());
		valores.put(LCREDITOS, materiaOfertada.getLCREDITOS());
		valores.put(LLABORATORIO, materiaOfertada.getLLABORATORIO());
		valores.put(DOCENTE, materiaOfertada.getDOCENTE());
		valores.put(SCODMATERIA, materiaOfertada.getSCODMATERIA());
		valores.put(CASILLA, materiaOfertada.getCASILLA());
		valores.put(SCODGRUPO, materiaOfertada.getSCODGRUPO());
		valores.put(SSEMANA, materiaOfertada.getSSEMANA());
		valores.put(LESTADOGRUPO_ID, materiaOfertada.getLESTADOGRUPO_ID());
		valores.put(SESTADOGRUPO_DSC, materiaOfertada.getSESTADOGRUPO_DSC());
		valores.put(SOBS1, materiaOfertada.getSOBS1());
		valores.put(LPERIODO_ID, materiaOfertada.getLPERIODO_ID());
		valores.put(LCARRERA_ID, materiaOfertada.getLCARRERA_ID());

		Conexion con = Conexion.getOrCreate();
		int ID = con.insertar(Tablas.MateriasOfertadas, valores);

		return ID;
	}

	@Override
	public void actualizar(JSONObject obj) {
		MateriasOfertadas objMateriasOfertadas = new MateriasOfertadas();

		try {
			objMateriasOfertadas.setLGRUPO_ID(obj.getInt("LGRUPO_ID"));
			objMateriasOfertadas.setLCENTRO_ID(obj.getInt("LCENTRO_ID"));
			objMateriasOfertadas.setSCENTRO_DSC(obj.getString("SCENTRO_DSC"));
			objMateriasOfertadas.setSMATERIA_DSC(obj.getString("SMATERIA_DSC"));
			objMateriasOfertadas.setLSEMESTRE(obj.getInt("LSEMESTRE"));
			objMateriasOfertadas.setLCREDITOS(obj.getInt("LCREDITOS"));
			objMateriasOfertadas.setLLABORATORIO(obj.getInt("LLABORATORIO"));
			objMateriasOfertadas.setDOCENTE(obj.getString("DOCENTE"));
			objMateriasOfertadas.setSCODMATERIA(obj.getString("SCODMATERIA"));
			objMateriasOfertadas.setCASILLA(obj.getString("CASILLA"));
			objMateriasOfertadas.setSCODGRUPO(obj.getString("SCODGRUPO"));
			objMateriasOfertadas.setSSEMANA(obj.getString("SSEMANA"));
			objMateriasOfertadas.setLESTADOGRUPO_ID(obj.getInt("LESTADOGRUPO_ID"));
			objMateriasOfertadas.setSESTADOGRUPO_DSC(obj.getString("SESTADOGRUPO_DSC"));
			objMateriasOfertadas.setSOBS1(obj.getString("SOBS1"));
			objMateriasOfertadas.setLPERIODO_ID(obj.getInt("LPERIODO_ID"));
			objMateriasOfertadas.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		actualizar(objMateriasOfertadas);
	}

	@Override
	public void truncate() {
		Conexion con = Conexion.getOrCreate();
		con.ejecutarSentencia("delete from " + Tablas.MateriasOfertadas);
	}

	@Override
	public List<MateriasOfertadas> seleccionar(int carrera, int periodo) {
		Conexion con = Conexion.getOrCreate();

		String where = "LCARRERA_ID = ? AND LPERIODO_ID = ?";
		String[] parametrosWhere = { String.valueOf(carrera), String.valueOf(periodo) };

		Cursor cursor = con.ejecutarConsulta(Tablas.MateriasOfertadas, columnas, where, parametrosWhere);
		List<MateriasOfertadas> lista = new ArrayList<>();

		while (cursor.moveToNext()) {
			MateriasOfertadas obj = obtenerObjDeCursor(cursor);
			lista.add(obj);
		}

		return lista;
	}

	public MateriasOfertadas seleccionar(int carrera, int periodo, String materia) {
		Conexion con = Conexion.getOrCreate();

		String where = "LPERIODO_ID = ? and LCARRERA_ID = ? and SCODMATERIA = ?";
		String[] parametrosWhere = { String.valueOf(periodo),
				String.valueOf(carrera), String.valueOf(materia) };

		Cursor cursor = con.ejecutarConsulta(Tablas.MateriasOfertadas, columnas, where, parametrosWhere);

		MateriasOfertadas objMateriasOfertadas = null;

		if (cursor.moveToFirst()) {
			objMateriasOfertadas = obtenerObjDeCursor(cursor);
		}

		return objMateriasOfertadas;
	}

	@Override
	public void insercionMasiva(int carreraId, int periodoId, JSONArray jsonArray) {
		Conexion conexion = Conexion.getOrCreate();
		SQLiteDatabase bd = conexion.getWritableDatabase();

		ContentValues values;
		ContentValues horariosValues;

		bd.beginTransaction();

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);

				values = new ContentValues();
				values.put(LGRUPO_ID, json.isNull(LGRUPO_ID) ? 0 : json.getInt(LGRUPO_ID));
				values.put(LCENTRO_ID, json.isNull(LCENTRO_ID) ? 0 : json.getInt(LCENTRO_ID));
				values.put(SCENTRO_DSC, json.isNull(SCENTRO_DSC) ? "" : json.getString(SCENTRO_DSC));
				values.put(SMATERIA_DSC, json.isNull(SMATERIA_DSC) ? "" : json.getString(SMATERIA_DSC));
				values.put(SCODGRUPO, json.isNull(SCODGRUPO) ? "" : json.getString(SCODGRUPO));
				values.put(DOCENTE, json.isNull(DOCENTE) ? "" : json.getString(DOCENTE));
				values.put(LSEMESTRE, json.isNull(LSEMESTRE) ? 0 : json.getInt(LSEMESTRE));
				values.put(LCREDITOS, json.isNull(LCREDITOS) ? 0 : json.getInt(LCREDITOS));
				values.put(LLABORATORIO, json.isNull(LLABORATORIO) ? 0 : json.getInt(LLABORATORIO));
				values.put(SCODMATERIA, json.isNull(SCODMATERIA) ? "" : json.getString(SCODMATERIA));
				values.put(CASILLA, json.isNull(CASILLA) ? "" : json.getString(CASILLA));
				values.put(SCODGRUPO, json.isNull(SCODGRUPO) ? "" : json.getString(SCODGRUPO));
				values.put(SSEMANA, json.isNull(SSEMANA) ? "" : json.getString(SSEMANA));
				values.put(LESTADOGRUPO_ID, json.isNull(LESTADOGRUPO_ID) ? 0 : json.getInt(LESTADOGRUPO_ID));
				values.put(SESTADOGRUPO_DSC, json.isNull(SESTADOGRUPO_DSC) ? "" : json.getString(SESTADOGRUPO_DSC));
				values.put(SOBS1, json.isNull(SOBS1) ? "" : json.getString(SOBS1));
				values.put(LPERIODO_ID, periodoId);
				values.put(LCARRERA_ID, carreraId);

				int ID = (int) bd.insertOrThrow(Tablas.MateriasOfertadas.toString(), null, values);

				if (!json.isNull("HORARIO")) {
					JSONArray horarios = json.getJSONArray("HORARIO");

					for (int j = 0; j < horarios.length(); j++) {
						JSONObject horario = horarios.getJSONObject(j);

						horariosValues = new ContentValues();
						horariosValues.put("LDIA_ID", horario.isNull("LDIA_ID") ? 0 : horario.getInt("LDIA_ID"));
						horariosValues.put("SDIA_DSC", horario.isNull("SDIA_DSC") ? "" : horario.getString("SDIA_DSC"));
						horariosValues.put("DTHRENTRADA", horario.isNull("DTHRENTRADA") ? "" : horario.getString("DTHRENTRADA"));
						horariosValues.put("DTHRSALIDA", horario.isNull("DTHRSALIDA") ? "" : horario.getString("DTHRSALIDA"));
						horariosValues.put("MAT_OFERTADA_ID", ID);

						bd.insertOrThrow(Tablas.HorariosOfertados.toString(), null, horariosValues);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		bd.setTransactionSuccessful();
		bd.endTransaction();
	}

}