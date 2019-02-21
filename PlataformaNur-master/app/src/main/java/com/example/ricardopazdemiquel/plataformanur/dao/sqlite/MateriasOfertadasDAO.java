package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla MateriasOfertadas
 */
class MateriasOfertadasDAO extends com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO {

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

//		if (objMateriasOfertadas.getLGRUPO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LGRUPO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getLCENTRO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LCENTRO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getSCENTRO_DSC().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCENTRO_DSC no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getSMATERIA_DSC().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SMATERIA_DSC no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getLSEMESTRE() <= 0) {
//		    throw new IllegalArgumentException("El LSEMESTRE no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getLCREDITOS() <= 0) {
//		    throw new IllegalArgumentException("El LCREDITOS no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getLLABORATORIO() <= 0) {
//		    throw new IllegalArgumentException("El LLABORATORIO no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getDOCENTE().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El DOCENTE no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getSCODMATERIA().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCODMATERIA no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getCASILLA().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El CASILLA no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getSCODGRUPO().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCODGRUPO no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getSSEMANA().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SSEMANA no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getLESTADOGRUPO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LESTADOGRUPO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getSESTADOGRUPO_DSC().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SESTADOGRUPO_DSC no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getSOBS1().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SOBS1 no puede estar vacio");
//		}
//
//		if (objMateriasOfertadas.getLPERIODO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LPERIODO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objMateriasOfertadas.getLCARRERA_ID() <= 0) {
//		    throw new IllegalArgumentException("El LCARRERA_ID no puede ser menor o igual que cero");
//		}

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
	public int insertar(JSONObject obj) {
		MateriasOfertadas objMateriasOfertadas = new MateriasOfertadas();

		try {
			String LGRUPO_ID = obj.getString("LGRUPO_ID");
			if (!LGRUPO_ID.trim().equals("null")) {
				objMateriasOfertadas.setLGRUPO_ID(obj.getInt("LGRUPO_ID"));
			}
			String LCENTRO_ID = obj.getString("LCENTRO_ID");
			if (!LCENTRO_ID.trim().equals("null")) {
				objMateriasOfertadas.setLCENTRO_ID(obj.getInt("LCENTRO_ID"));
			}
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

			String LESTADOGRUPO_ID = obj.getString("LESTADOGRUPO_ID");
			if (!LESTADOGRUPO_ID.trim().equals("null")) {
				objMateriasOfertadas.setLESTADOGRUPO_ID(obj.getInt("LESTADOGRUPO_ID"));
			}

			objMateriasOfertadas.setSESTADOGRUPO_DSC(obj.getString("SESTADOGRUPO_DSC"));
			objMateriasOfertadas.setSOBS1(obj.getString("SOBS1"));
			objMateriasOfertadas.setLPERIODO_ID(obj.getInt("LPERIODO_ID"));
			objMateriasOfertadas.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

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

}