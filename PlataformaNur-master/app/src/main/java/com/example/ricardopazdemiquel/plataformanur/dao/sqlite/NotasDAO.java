package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;

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
    public void insertar(JSONObject obj) {
    	Notas nota = new Notas();

		try {
			nota.setLGRUPO_ID(obj.getInt("LGRUPO_ID"));
			nota.setLCENTRO_ID(obj.getInt("LCENTRO_ID"));
			nota.setSCODCENTRO(obj.getString("SCODCENTRO"));
			nota.setSCENTRO_DSC(obj.getString("SCENTRO_DSC"));
			nota.setSCODMATERIA(obj.getString("SCODMATERIA"));
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

			nota.setLPERIODO_ID(obj.getInt("LPERIODO_ID"));
			nota.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		insertar(nota);
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
}