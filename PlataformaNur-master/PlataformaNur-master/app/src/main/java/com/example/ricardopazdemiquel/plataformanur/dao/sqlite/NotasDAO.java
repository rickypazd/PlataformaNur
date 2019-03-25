package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Objs.Periodo;
import com.example.ricardopazdemiquel.plataformanur.Utiles.MyApp;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla Notas
 */
class NotasDAO extends com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO {

    private static final String LGRUPO_ID = "LGRUPO_ID";
    private static final String LCENTRO_ID = "LCENTRO_ID";
    private static final String SCODCENTRO = "SCODCENTRO";
    private static final String SCENTRO_DSC = "SCENTRO_DSC";
    private static final String SCODMATERIA = "SCODMATERIA";
    private static final String SSIGLA = "SSIGLA";
    private static final String SMATERIA_DSC = "SMATERIA_DSC";
    private static final String SCODGRUPO = "SCODGRUPO";
    private static final String DOCENTE = "DOCENTE";
    private static final String PAR1 = "PAR1";
    private static final String PAR2 = "PAR2";
    private static final String EXFINAL = "EXFINAL";
    private static final String FINAL = "FINAL";
    private static final String FMES1 = "FMES1";
    private static final String FMES2 = "FMES2";
    private static final String FMES3 = "FMES3";
    private static final String FMES4 = "FMES4";
    private static final String FMES5 = "FMES5";
    private static final String FMES6 = "FMES6";
    private static final String FMES7 = "FMES7";
    private static final String FMES8 = "FMES8";
    private static final String FMES9 = "FMES9";
    private static final String FMES10 = "FMES10";
    private static final String FMES11 = "FMES11";
    private static final String FMES12 = "FMES12";

	private static final String LPERIODO_ID = "LPERIODO_ID";
	private static final String LCARRERA_ID = "LCARRERA_ID";

    NotasDAO() {
		super(LGRUPO_ID, LCENTRO_ID, SCODCENTRO, SCENTRO_DSC, SCODMATERIA, SSIGLA, SMATERIA_DSC, SCODGRUPO, DOCENTE, PAR1, PAR2, EXFINAL, FINAL, FMES1, FMES2, FMES3, FMES4, FMES5, FMES6, FMES7, FMES8, FMES9, FMES10, FMES11, FMES12, LPERIODO_ID, LCARRERA_ID);
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

        Cursor cursor = con.ejecutarConsulta(Tablas.Notas, columnas, where, parametrosWhere);
        Notas objNotas = null;

        if (cursor.moveToFirst()) {
            objNotas = obtenerObjDeCursor(cursor);
        }

        return objNotas;
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        Notas objNotas = (Notas) obj;

        ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, objNotas.getLGRUPO_ID());
		valores.put(LCENTRO_ID, objNotas.getLCENTRO_ID());
		valores.put(SCODCENTRO, objNotas.getSCODCENTRO());
		valores.put(SCENTRO_DSC, objNotas.getSCENTRO_DSC());
		valores.put(SCODMATERIA, objNotas.getSCODMATERIA());
		valores.put(SSIGLA, objNotas.getSSIGLA());
		valores.put(SMATERIA_DSC, objNotas.getSMATERIA_DSC());
		valores.put(SCODGRUPO, objNotas.getSCODGRUPO());
		valores.put(DOCENTE, objNotas.getDOCENTE());
		valores.put(PAR1, objNotas.getPAR1());
		valores.put(PAR2, objNotas.getPAR2());
		valores.put(EXFINAL, objNotas.getEXFINAL());
		valores.put(FINAL, objNotas.getFINAL());
		valores.put(FMES1, objNotas.getFMES1());
		valores.put(FMES2, objNotas.getFMES2());
		valores.put(FMES3, objNotas.getFMES3());
		valores.put(FMES4, objNotas.getFMES4());
		valores.put(FMES5, objNotas.getFMES5());
		valores.put(FMES6, objNotas.getFMES6());
		valores.put(FMES7, objNotas.getFMES7());
		valores.put(FMES8, objNotas.getFMES8());
		valores.put(FMES9, objNotas.getFMES9());
		valores.put(FMES10, objNotas.getFMES10());
		valores.put(FMES11, objNotas.getFMES11());
		valores.put(FMES12, objNotas.getFMES12());

		valores.put(LPERIODO_ID, objNotas.getLPERIODO_ID());
		valores.put(LCARRERA_ID, objNotas.getLCARRERA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.Notas, valores);

        // objNotas.setLGRUPO_ID(ID);
    }

	@Override
    public int insertar(JSONObject json) {
    	Notas nota = new Notas();

		try {
			if (!json.isNull("LGRUPO_ID")) nota.setLGRUPO_ID(json.getInt("LGRUPO_ID"));
			if (!json.isNull("LCENTRO_ID")) nota.setLCENTRO_ID(json.getInt("LCENTRO_ID"));
			nota.setSCODCENTRO(json.isNull("SCODCENTRO") ? "" : json.getString("SCODCENTRO"));
			nota.setSCENTRO_DSC(json.isNull("SCENTRO_DSC") ? "" : json.getString("SCENTRO_DSC"));
			nota.setSCODMATERIA(json.isNull("SCODMATERIA") ? "" : json.getString("SCODMATERIA"));
			nota.setSSIGLA(json.isNull("SSIGLA") ? "" : json.getString("SSIGLA"));
			nota.setSMATERIA_DSC(json.isNull("SMATERIA_DSC") ? "" : json.getString("SMATERIA_DSC"));
			nota.setSCODGRUPO(json.isNull("SCODGRUPO") ? "" : json.getString("SCODGRUPO"));
			nota.setDOCENTE(json.isNull("DOCENTE") ? "" : json.getString("DOCENTE"));
			nota.setPAR1(json.isNull("PAR1") ? "" : json.getString("PAR1"));
			nota.setPAR2(json.isNull("PAR2") ? "" : json.getString("PAR2"));
			nota.setEXFINAL(json.isNull("EXFINAL") ? "" : json.getString("EXFINAL"));
			nota.setFINAL(json.isNull("FINAL") ? "" : json.getString("FINAL"));
			if (!json.isNull("FMES1")) nota.setFMES1(json.getInt("FMES1"));
			if (!json.isNull("FMES2")) nota.setFMES2(json.getInt("FMES2"));
			if (!json.isNull("FMES3")) nota.setFMES3(json.getInt("FMES3"));
			if (!json.isNull("FMES4")) nota.setFMES4(json.getInt("FMES4"));
			if (!json.isNull("FMES5")) nota.setFMES5(json.getInt("FMES5"));
			if (!json.isNull("FMES6")) nota.setFMES6(json.getInt("FMES6"));
			if (!json.isNull("FMES7")) nota.setFMES7(json.getInt("FMES7"));
			if (!json.isNull("FMES8")) nota.setFMES8(json.getInt("FMES8"));
			if (!json.isNull("FMES9")) nota.setFMES9(json.getInt("FMES9"));
			if (!json.isNull("FMES10")) nota.setFMES10(json.getInt("FMES10"));
			if (!json.isNull("FMES11")) nota.setFMES11(json.getInt("FMES11"));
			if (!json.isNull("FMES12")) nota.setFMES12(json.getInt("FMES12"));

			if (!json.isNull("LPERIODO_ID")) nota.setLPERIODO_ID(json.getInt("LPERIODO_ID"));
			if (!json.isNull("LCARRERA_ID")) nota.setLCARRERA_ID(json.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, nota.getLGRUPO_ID());
		valores.put(LCENTRO_ID, nota.getLCENTRO_ID());
		valores.put(SCODCENTRO, nota.getSCODCENTRO());
		valores.put(SCENTRO_DSC, nota.getSCENTRO_DSC());
		valores.put(SSIGLA, nota.getSSIGLA());
		valores.put(SMATERIA_DSC, nota.getSMATERIA_DSC());
		valores.put(SCODGRUPO, nota.getSCODGRUPO());
		valores.put(DOCENTE, nota.getDOCENTE());
		valores.put(PAR1, nota.getPAR1());
		valores.put(PAR2, nota.getPAR2());
		valores.put(EXFINAL, nota.getEXFINAL());
		valores.put(FINAL, nota.getFINAL());
		valores.put(FMES1, nota.getFMES1());
		valores.put(FMES2, nota.getFMES2());
		valores.put(FMES3, nota.getFMES3());
		valores.put(FMES4, nota.getFMES4());
		valores.put(FMES5, nota.getFMES5());
		valores.put(FMES6, nota.getFMES6());
		valores.put(FMES7, nota.getFMES7());
		valores.put(FMES8, nota.getFMES8());
		valores.put(FMES9, nota.getFMES9());
		valores.put(FMES10, nota.getFMES10());
		valores.put(FMES11, nota.getFMES11());
		valores.put(FMES12, nota.getFMES12());
		valores.put(LPERIODO_ID, nota.getLPERIODO_ID());
		valores.put(LCARRERA_ID, nota.getLCARRERA_ID());

		Conexion con = Conexion.getOrCreate();
		int ID = con.insertar(Tablas.Notas, valores);

		return ID;
	}

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        Notas objNotas = (Notas) obj;

//		if (objNotas.getLGRUPO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LGRUPO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getLCENTRO_ID() <= 0) {
//		    throw new IllegalArgumentException("El LCENTRO_ID no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getSCODCENTRO().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCODCENTRO no puede estar vacio");
//		}
//
//		if (objNotas.getSCENTRO_DSC().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCENTRO_DSC no puede estar vacio");
//		}
//
//		if (objNotas.getSCODMATERIA().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCODMATERIA no puede estar vacio");
//		}
//
//		if (objNotas.getSSIGLA().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SSIGLA no puede estar vacio");
//		}
//
//		if (objNotas.getSMATERIA_DSC().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SMATERIA_DSC no puede estar vacio");
//		}
//
//		if (objNotas.getSCODGRUPO().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El SCODGRUPO no puede estar vacio");
//		}
//
//		if (objNotas.getDOCENTE().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El DOCENTE no puede estar vacio");
//		}
//
//		if (objNotas.getPAR1().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El PAR1 no puede estar vacio");
//		}
//
//		if (objNotas.getPAR2().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El PAR2 no puede estar vacio");
//		}
//
//		if (objNotas.getEXFINAL().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El EXFINAL no puede estar vacio");
//		}
//
//		if (objNotas.getFINAL().trim().isEmpty()) {
//		    throw new IllegalArgumentException("El FINAL no puede estar vacio");
//		}
//
//		if (objNotas.getFMES1() <= 0) {
//		    throw new IllegalArgumentException("El FMES1 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES2() <= 0) {
//		    throw new IllegalArgumentException("El FMES2 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES3() <= 0) {
//		    throw new IllegalArgumentException("El FMES3 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES4() <= 0) {
//		    throw new IllegalArgumentException("El FMES4 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES5() <= 0) {
//		    throw new IllegalArgumentException("El FMES5 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES6() <= 0) {
//		    throw new IllegalArgumentException("El FMES6 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES7() <= 0) {
//		    throw new IllegalArgumentException("El FMES7 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES8() <= 0) {
//		    throw new IllegalArgumentException("El FMES8 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES9() <= 0) {
//		    throw new IllegalArgumentException("El FMES9 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES10() <= 0) {
//		    throw new IllegalArgumentException("El FMES10 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES11() <= 0) {
//		    throw new IllegalArgumentException("El FMES11 no puede ser menor o igual que cero");
//		}
//
//		if (objNotas.getFMES12() <= 0) {
//		    throw new IllegalArgumentException("El FMES12 no puede ser menor o igual que cero");
//		}

        ContentValues valores = new ContentValues();
		valores.put(LGRUPO_ID, objNotas.getLGRUPO_ID());
		valores.put(LCENTRO_ID, objNotas.getLCENTRO_ID());
		valores.put(SCODCENTRO, objNotas.getSCODCENTRO());
		valores.put(SCENTRO_DSC, objNotas.getSCENTRO_DSC());
		valores.put(SSIGLA, objNotas.getSSIGLA());
		valores.put(SMATERIA_DSC, objNotas.getSMATERIA_DSC());
		valores.put(SCODGRUPO, objNotas.getSCODGRUPO());
		valores.put(DOCENTE, objNotas.getDOCENTE());
		valores.put(PAR1, objNotas.getPAR1());
		valores.put(PAR2, objNotas.getPAR2());
		valores.put(EXFINAL, objNotas.getEXFINAL());
		valores.put(FINAL, objNotas.getFINAL());
		valores.put(FMES1, objNotas.getFMES1());
		valores.put(FMES2, objNotas.getFMES2());
		valores.put(FMES3, objNotas.getFMES3());
		valores.put(FMES4, objNotas.getFMES4());
		valores.put(FMES5, objNotas.getFMES5());
		valores.put(FMES6, objNotas.getFMES6());
		valores.put(FMES7, objNotas.getFMES7());
		valores.put(FMES8, objNotas.getFMES8());
		valores.put(FMES9, objNotas.getFMES9());
		valores.put(FMES10, objNotas.getFMES10());
		valores.put(FMES11, objNotas.getFMES11());
		valores.put(FMES12, objNotas.getFMES12());

		String where = "LPERIODO_ID = ? and LCARRERA_ID = ? and SCODMATERIA = ?";
        String[] parametrosWhere = { String.valueOf(objNotas.getLPERIODO_ID()), String.valueOf(objNotas.getLCARRERA_ID()), String.valueOf(objNotas.getSCODMATERIA()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.Notas, valores, where, parametrosWhere);
    }

	@Override
	public void actualizar(JSONObject obj) {
		Notas nota = new Notas();

		try {
			nota.setLGRUPO_ID(obj.getInt("LGRUPO_ID"));
			nota.setLCENTRO_ID(obj.getInt("LCENTRO_ID"));
			nota.setSCODCENTRO(obj.getString("SCODCENTRO"));
			nota.setSCENTRO_DSC(obj.getString("SCENTRO_DSC"));
			nota.setSSIGLA(obj.getString("SSIGLA"));
			nota.setSMATERIA_DSC(obj.getString("SMATERIA_DSC"));
			nota.setSCODGRUPO(obj.getString("SCODGRUPO"));
			nota.setDOCENTE(obj.getString("DOCENTE"));
			nota.setPAR1(obj.getString("PAR1"));
			nota.setPAR2(obj.getString("PAR2"));
			nota.setEXFINAL(obj.getString("EXFINAL"));
			nota.setFINAL(obj.getString("FINAL"));
			nota.setFMES1(obj.getInt("FMES1"));
			nota.setFMES2(obj.getInt("FMES2"));
			nota.setFMES3(obj.getInt("FMES3"));
			nota.setFMES4(obj.getInt("FMES4"));
			nota.setFMES5(obj.getInt("FMES5"));
			nota.setFMES6(obj.getInt("FMES6"));
			nota.setFMES7(obj.getInt("FMES7"));
			nota.setFMES8(obj.getInt("FMES8"));
			nota.setFMES9(obj.getInt("FMES9"));
			nota.setFMES10(obj.getInt("FMES10"));
			nota.setFMES11(obj.getInt("FMES11"));
			nota.setFMES12(obj.getInt("FMES12"));

			nota.setSCODMATERIA(obj.getString("SCODMATERIA"));
			nota.setLPERIODO_ID(obj.getInt("LPERIODO_ID"));
			nota.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		actualizar(nota);
	}

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        Notas objNotas = (Notas) obj;
        if (objNotas.getLGRUPO_ID() < 0) {
            throw new IllegalArgumentException("No se puede eliminar un Notas con ID <= 0");
        }

        String where = "LGRUPO_ID = ?";
        String[] parametrosWhere = { String.valueOf(objNotas.getLGRUPO_ID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.Notas, where, parametrosWhere);
    }

    @Override
    public List<Notas> seleccionarTodos() {
        Conexion con = Conexion.getOrCreate();

        Cursor cursor = con.ejecutarConsulta(Tablas.Notas, columnas, null, null);
        List<Notas> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Notas objNotas = obtenerObjDeCursor(cursor);
            lista.add(objNotas);
        }

        return lista;
    }

	@Override
	public List<Notas> seleccionar(int carrera, int periodo) {
		Conexion con = Conexion.getOrCreate();

		String where = "LCARRERA_ID = ? AND LPERIODO_ID = ?";
		String[] parametrosWhere = { String.valueOf(carrera), String.valueOf(periodo) };

		Cursor cursor = con.ejecutarConsulta(Tablas.Notas, columnas, where, parametrosWhere);
		List<Notas> lista = new ArrayList<>();

		while (cursor.moveToNext()) {
			Notas objNotas = obtenerObjDeCursor(cursor);
			lista.add(objNotas);
		}

		return lista;
	}

	@Override
    public void truncate() {
    	Conexion con = Conexion.getOrCreate();
    	con.ejecutarSentencia("delete from " + Tablas.Notas);
	}

    private Notas obtenerObjDeCursor(Cursor cursor) {
        Notas objNotas = new Notas();

		objNotas.setLGRUPO_ID(cursor.getInt(cursor.getColumnIndex(LGRUPO_ID)));
		objNotas.setLCENTRO_ID(cursor.getInt(cursor.getColumnIndex(LCENTRO_ID)));
		objNotas.setSCODCENTRO(cursor.getString(cursor.getColumnIndex(SCODCENTRO)));
		objNotas.setSCENTRO_DSC(cursor.getString(cursor.getColumnIndex(SCENTRO_DSC)));
		objNotas.setSCODMATERIA(cursor.getString(cursor.getColumnIndex(SCODMATERIA)));
		objNotas.setSSIGLA(cursor.getString(cursor.getColumnIndex(SSIGLA)));
		objNotas.setSMATERIA_DSC(cursor.getString(cursor.getColumnIndex(SMATERIA_DSC)));
		objNotas.setSCODGRUPO(cursor.getString(cursor.getColumnIndex(SCODGRUPO)));
		objNotas.setDOCENTE(cursor.getString(cursor.getColumnIndex(DOCENTE)));
		objNotas.setPAR1(cursor.getString(cursor.getColumnIndex(PAR1)));
		objNotas.setPAR2(cursor.getString(cursor.getColumnIndex(PAR2)));
		objNotas.setEXFINAL(cursor.getString(cursor.getColumnIndex(EXFINAL)));
		objNotas.setFINAL(cursor.getString(cursor.getColumnIndex(FINAL)));
		objNotas.setFMES1(cursor.getInt(cursor.getColumnIndex(FMES1)));
		objNotas.setFMES2(cursor.getInt(cursor.getColumnIndex(FMES2)));
		objNotas.setFMES3(cursor.getInt(cursor.getColumnIndex(FMES3)));
		objNotas.setFMES4(cursor.getInt(cursor.getColumnIndex(FMES4)));
		objNotas.setFMES5(cursor.getInt(cursor.getColumnIndex(FMES5)));
		objNotas.setFMES6(cursor.getInt(cursor.getColumnIndex(FMES6)));
		objNotas.setFMES7(cursor.getInt(cursor.getColumnIndex(FMES7)));
		objNotas.setFMES8(cursor.getInt(cursor.getColumnIndex(FMES8)));
		objNotas.setFMES9(cursor.getInt(cursor.getColumnIndex(FMES9)));
		objNotas.setFMES10(cursor.getInt(cursor.getColumnIndex(FMES10)));
		objNotas.setFMES11(cursor.getInt(cursor.getColumnIndex(FMES11)));
		objNotas.setFMES12(cursor.getInt(cursor.getColumnIndex(FMES12)));
		objNotas.setLPERIODO_ID(cursor.getInt(cursor.getColumnIndex(LPERIODO_ID)));
		objNotas.setLCARRERA_ID(cursor.getInt(cursor.getColumnIndex(LCARRERA_ID)));

        return objNotas;
    }

	@Override
	public Notas seleccionar(int carrera, int periodo, String materia) {
		Conexion con = Conexion.getOrCreate();

		String where = "LCARRERA_ID = ? AND LPERIODO_ID = ? AND SCODMATERIA = ?";
		String[] parametrosWhere = { String.valueOf(carrera), String.valueOf(periodo), String.valueOf(materia) };

		Cursor cursor = con.ejecutarConsulta(Tablas.Notas, columnas, where, parametrosWhere);
		List<Notas> lista = new ArrayList<>();

		Notas objNotas = null;

		if (cursor.moveToFirst()) {
			objNotas = obtenerObjDeCursor(cursor);
		}

		return objNotas;
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
				values.put(SCODCENTRO, json.isNull(SCODCENTRO) ? "" : json.getString(SCODCENTRO));
				values.put(SCENTRO_DSC, json.isNull(SCENTRO_DSC) ? "" : json.getString(SCENTRO_DSC));
				values.put(SSIGLA, json.isNull(SSIGLA) ? "" : json.getString(SSIGLA));
				values.put(SMATERIA_DSC, json.isNull(SMATERIA_DSC) ? "" : json.getString(SMATERIA_DSC));
				values.put(SCODGRUPO, json.isNull(SCODGRUPO) ? "" : json.getString(SCODGRUPO));
				values.put(DOCENTE, json.isNull(DOCENTE) ? "" : json.getString(DOCENTE));
				values.put(PAR1, json.isNull(PAR1) ? "" : json.getString(PAR1));
				values.put(PAR2, json.isNull(PAR2) ? "" : json.getString(PAR2));
				values.put(EXFINAL, json.isNull(EXFINAL) ? "" : json.getString(EXFINAL));
				values.put(FINAL, json.isNull(FINAL) ? "" : json.getString(FINAL));
				values.put(FMES1, json.isNull(FMES1) ? 0 : json.getInt(FMES1));
				values.put(FMES2, json.isNull(FMES2) ? 0 : json.getInt(FMES2));
				values.put(FMES3, json.isNull(FMES3) ? 0 : json.getInt(FMES3));
				values.put(FMES4, json.isNull(FMES4) ? 0 : json.getInt(FMES4));
				values.put(FMES5, json.isNull(FMES5) ? 0 : json.getInt(FMES5));
				values.put(FMES6, json.isNull(FMES6) ? 0 : json.getInt(FMES6));
				values.put(FMES7, json.isNull(FMES7) ? 0 : json.getInt(FMES7));
				values.put(FMES8, json.isNull(FMES8) ? 0 : json.getInt(FMES8));
				values.put(FMES9, json.isNull(FMES9) ? 0 : json.getInt(FMES9));
				values.put(FMES10, json.isNull(FMES10) ? 0 : json.getInt(FMES10));
				values.put(FMES11, json.isNull(FMES11) ? 0 : json.getInt(FMES11));
				values.put(FMES12, json.isNull(FMES12) ? 0 : json.getInt(FMES12));
				values.put(LPERIODO_ID, periodoId);
				values.put(LCARRERA_ID, carreraId);

				bd.insertOrThrow(Tablas.Notas.toString(), null, values);

				if (!json.isNull("HORARIO")) {
					JSONArray horarios = json.getJSONArray("HORARIO");

					for (int j = 0; j < horarios.length(); j++) {
						JSONObject horario = horarios.getJSONObject(j);

						horariosValues = new ContentValues();
						horariosValues.put("LGRUPOHORARIO_ID", horario.isNull("LGRUPOHORARIO_ID") ? 0 : horario.getInt("LGRUPOHORARIO_ID"));
						horariosValues.put("LGRUPO_ID", horario.isNull("LGRUPO_ID") ? 0 : horario.getInt("LGRUPO_ID"));
						horariosValues.put("ENTRADA", horario.isNull("ENTRADA") ? "" : horario.getString("ENTRADA"));
						horariosValues.put("SALIDA", horario.isNull("SALIDA") ? "" : horario.getString("SALIDA"));
						horariosValues.put("LDIA", horario.isNull("LDIA") ? 0 : horario.getInt("LDIA"));
						horariosValues.put("SDIA_DSC", horario.isNull("SDIA_DSC") ? "" : horario.getString("SDIA_DSC"));
						horariosValues.put("SAULA_DSC", horario.isNull("SAULA_DSC") ? "" : horario.getString("SAULA_DSC"));

						bd.insertOrThrow(Tablas.HorariosMaterias.toString(), null, horariosValues);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		bd.setTransactionSuccessful();
		bd.endTransaction();
	}

	@Override
	public List<Periodo> seleccionarSemestresCursados() {
		Conexion con = Conexion.getOrCreate();

		AlumnoCarrera carreraSeleccionada = Preferences.getCarreraSeleccionada(MyApp.getInstancia());
		ArrayList<Periodo> todosLosPeriodos = Preferences.getPeriodos(MyApp.getInstancia());
		ArrayList<Periodo> periodosCursados = new ArrayList<>();

		String where = "LCARRERA_ID = ?";
		String[] parametrosWhere = { String.valueOf(carreraSeleccionada.getLCARRERA_ID()) };

		Cursor cursor = con.ejecutarConsulta(Tablas.Notas, columnas, where, parametrosWhere);

		while (cursor.moveToNext()) {
			Notas objNotas = obtenerObjDeCursor(cursor);
			int periodoId = objNotas.getLPERIODO_ID();

			// recorro todos los peridos
			for (int i = 0; i < todosLosPeriodos.size(); i++) {
				Periodo objPeriodo = todosLosPeriodos.get(i);

				// veo si esta materia se llevó en el peridoo
				if (objPeriodo.getLPERIODO_ID() == periodoId) {
					if (periodosCursados.size() > 0) { // para evitar tener periodos repetidos

						boolean yaEstaEnLaLista = false;
						for (int j = 0; j < periodosCursados.size(); j++) {
							Periodo periodoCursado = periodosCursados.get(j);

							if (periodoCursado.getLPERIODO_ID() == periodoId) {
								yaEstaEnLaLista = true;
								break;
							}
						}

						if (!yaEstaEnLaLista) {
							periodosCursados.add(objPeriodo);
						}

					} else { // agrego el periodo a la lista filtrada
						periodosCursados.add(objPeriodo);
					}

					break;
				}
			}
		}

		return periodosCursados;
	}
}