package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosOfertados;

import org.json.JSONObject;

/**
 * La implementacion DAO para SQLite de la tabla HorariosOfertados
 */
class HorariosOfertadosDAO extends com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO {

    private static final String LDIA_ID = "LDIA_ID";
    private static final String SDIA_DSC = "SDIA_DSC";
    private static final String DTHRENTRADA = "DTHRENTRADA";
    private static final String DTHRSALIDA = "DTHRSALIDA";
    private static final String MAT_OFERTADA_ID = "MAT_OFERTADA_ID";

    HorariosOfertadosDAO() {
		super(LDIA_ID, SDIA_DSC, DTHRENTRADA, DTHRSALIDA, MAT_OFERTADA_ID);
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

        Cursor cursor = con.ejecutarConsulta(Tablas.HorariosOfertados, columnas, where, parametrosWhere);
        HorariosOfertados objHorariosOfertados = null;

        if (cursor.moveToFirst()) {
            objHorariosOfertados = obtenerObjDeCursor(cursor);
        }

        return objHorariosOfertados;
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        HorariosOfertados objHorariosOfertados = (HorariosOfertados) obj;

        ContentValues valores = new ContentValues();
        valores.put(LDIA_ID, objHorariosOfertados.getLDIA_ID());
		valores.put(SDIA_DSC, objHorariosOfertados.getSDIA_DSC());
		valores.put(DTHRENTRADA, objHorariosOfertados.getDTHRENTRADA());
		valores.put(DTHRSALIDA, objHorariosOfertados.getDTHRSALIDA());
		valores.put(MAT_OFERTADA_ID, objHorariosOfertados.getMAT_OFERTADA_ID());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.HorariosOfertados, valores);

        // objHorariosOfertados.setLDIA_ID(ID);
    }

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        HorariosOfertados objHorariosOfertados = (HorariosOfertados) obj;

        ContentValues valores = new ContentValues();
		valores.put(SDIA_DSC, objHorariosOfertados.getSDIA_DSC());
		valores.put(DTHRENTRADA, objHorariosOfertados.getDTHRENTRADA());
		valores.put(DTHRSALIDA, objHorariosOfertados.getDTHRSALIDA());
		valores.put(MAT_OFERTADA_ID, objHorariosOfertados.getMAT_OFERTADA_ID());

		String where = "codigo_id = ?"; // TODO: thiiiiiis
        String[] parametrosWhere = { String.valueOf(objHorariosOfertados.getLDIA_ID()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.HorariosOfertados, valores, where, parametrosWhere);
    }

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        HorariosOfertados objHorariosOfertados = (HorariosOfertados) obj;
        if (objHorariosOfertados.getLDIA_ID() < 0) {
            throw new IllegalArgumentException("No se puede eliminar un HorariosOfertados con ID <= 0");
        }

        String where = "codigo_id = ?";
        String[] parametrosWhere = { String.valueOf(objHorariosOfertados.getLDIA_ID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.HorariosOfertados, where, parametrosWhere);
    }

    @Override
    public List<HorariosOfertados> seleccionarTodos() {
        Conexion con = Conexion.getOrCreate();

        Cursor cursor = con.ejecutarConsulta(Tablas.HorariosOfertados, columnas, null, null);
        List<HorariosOfertados> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            HorariosOfertados objHorariosOfertados = obtenerObjDeCursor(cursor);
            lista.add(objHorariosOfertados);
        }

        return lista;
    }

    private HorariosOfertados obtenerObjDeCursor(Cursor cursor) {
        HorariosOfertados objHorariosOfertados = new HorariosOfertados();

		objHorariosOfertados.setLDIA_ID(cursor.getInt(cursor.getColumnIndex(LDIA_ID)));
		objHorariosOfertados.setSDIA_DSC(cursor.getString(cursor.getColumnIndex(SDIA_DSC)));
		objHorariosOfertados.setDTHRENTRADA(cursor.getString(cursor.getColumnIndex(DTHRENTRADA)));
		objHorariosOfertados.setDTHRSALIDA(cursor.getString(cursor.getColumnIndex(DTHRSALIDA)));
		objHorariosOfertados.setMAT_OFERTADA_ID(cursor.getInt(cursor.getColumnIndex(MAT_OFERTADA_ID)));

        return objHorariosOfertados;
    }

    @Override
    public void insertar(JSONObject obj) {
        HorariosOfertados objHorariosOfertados = new HorariosOfertados();

        try {
            objHorariosOfertados.setLDIA_ID(obj.getInt("LDIA_ID"));
            objHorariosOfertados.setSDIA_DSC(obj.getString("SDIA_DSC"));
            objHorariosOfertados.setDTHRENTRADA(obj.getString("DTHRENTRADA"));
            objHorariosOfertados.setDTHRSALIDA(obj.getString("DTHRSALIDA"));
            objHorariosOfertados.setMAT_OFERTADA_ID(obj.getInt("MAT_OFERTADA_ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertar(objHorariosOfertados);
    }

    @Override
    public void actualizar(JSONObject obj) {
        HorariosOfertados objHorariosOfertados = new HorariosOfertados();

        try {
            objHorariosOfertados.setLDIA_ID(obj.getInt("LDIA_ID"));
            objHorariosOfertados.setSDIA_DSC(obj.getString("SDIA_DSC"));
            objHorariosOfertados.setDTHRENTRADA(obj.getString("DTHRENTRADA"));
            objHorariosOfertados.setDTHRSALIDA(obj.getString("DTHRSALIDA"));
            objHorariosOfertados.setMAT_OFERTADA_ID(obj.getInt("MAT_OFERTADA_ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        actualizar(objHorariosOfertados);
    }

    @Override
    public void truncate() {
        Conexion con = Conexion.getOrCreate();
        con.ejecutarSentencia("delete from " + Tablas.HorariosOfertados);
    }

    @Override
    public List<HorariosOfertados> seleccionar(int materiaOfertada) {
        Conexion con = Conexion.getOrCreate();

        String where = "MAT_OFERTADA_ID = ?";
        String[] parametrosWhere = { String.valueOf(materiaOfertada) };

        Cursor cursor = con.ejecutarConsulta(Tablas.HorariosOfertados, columnas, where, parametrosWhere);
        List<HorariosOfertados> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            HorariosOfertados obj = obtenerObjDeCursor(cursor);
            lista.add(obj);
        }

        return lista;
    }

    @Override
    public void eliminarDeMateria(int materia) {
        String where = "MAT_OFERTADA_ID = ?";
        String[] parametrosWhere = { String.valueOf(materia) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.HorariosOfertados, where, parametrosWhere);
    }

}