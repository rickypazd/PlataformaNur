package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.ricardopazdemiquel.plataformanur.conexion.Conexion;
import com.example.ricardopazdemiquel.plataformanur.conexion.Tablas;
import com.example.ricardopazdemiquel.plataformanur.dto.DTO;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosMaterias;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosOfertados;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * La implementacion DAO para SQLite de la tabla HorariosOfertados
 */
class HorariosMateriasDAO extends com.example.ricardopazdemiquel.plataformanur.dao.HorariosMateriasDAO {

    private static final String ID = "ID";
    private static final String LGRUPOHORARIO_ID = "LGRUPOHORARIO_ID";
    private static final String LGRUPO_ID = "LGRUPO_ID";
    private static final String ENTRADA  = "ENTRADA";
    private static final String SALIDA = "SALIDA";
    private static final String LDIA = "LDIA";
    private static final String SDIA_DSC = "SDIA_DSC";
    private static final String SAULA_DSC = "SAULA_DSC";

    HorariosMateriasDAO() {
		super(LGRUPOHORARIO_ID, LGRUPO_ID, ENTRADA, SALIDA, LDIA, SDIA_DSC, SAULA_DSC);
	}

    @Override
    public DTO seleccionar(Object llave) {
        return new HorariosOfertados();
    }

    @Override
    public void insertar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");
        }

        HorariosMaterias objHorariosMaterias = (HorariosMaterias) obj;

        ContentValues valores = new ContentValues();
        valores.put(LGRUPOHORARIO_ID, objHorariosMaterias.getLGRUPOHORARIO_ID());
		valores.put(LGRUPO_ID, objHorariosMaterias.getLGRUPO_ID());
		valores.put(ENTRADA, objHorariosMaterias.getENTRADA());
		valores.put(SALIDA, objHorariosMaterias.getSALIDA());
		valores.put(LDIA, objHorariosMaterias.getLDIA());
        valores.put(SDIA_DSC, objHorariosMaterias.getSDIA_DSC());
        valores.put(SAULA_DSC, objHorariosMaterias.getSAULA_DSC());

        Conexion con = Conexion.getOrCreate();
        int ID = con.insertar(Tablas.HorariosMaterias, valores);

        // objHorariosOfertados.setLDIA_ID(ID);
    }

    @Override
    public void actualizar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto a actualizar no puede ser nulo");
        }

        HorariosMaterias objHorariosMaterias = (HorariosMaterias) obj;

        ContentValues valores = new ContentValues();
        valores.put(LGRUPOHORARIO_ID, objHorariosMaterias.getLGRUPOHORARIO_ID());
        valores.put(LGRUPO_ID, objHorariosMaterias.getLGRUPO_ID());
        valores.put(ENTRADA, objHorariosMaterias.getENTRADA());
        valores.put(SALIDA, objHorariosMaterias.getSALIDA());
        valores.put(LDIA, objHorariosMaterias.getLDIA());
        valores.put(SDIA_DSC, objHorariosMaterias.getSDIA_DSC());
        valores.put(SAULA_DSC, objHorariosMaterias.getSAULA_DSC());

		String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objHorariosMaterias.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.actualizar(Tablas.HorariosMaterias, valores, where, parametrosWhere);
    }

    @Override
    public void eliminar(DTO obj) {
        if (obj == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }

        HorariosMaterias objHorariosMaterias = (HorariosMaterias) obj;
        if (objHorariosMaterias.getID() < 0) {
            throw new IllegalArgumentException("No se puede eliminar un HorariosOfertados con ID <= 0");
        }

        String where = "ID = ?";
        String[] parametrosWhere = { String.valueOf(objHorariosMaterias.getID()) };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.HorariosMaterias, where, parametrosWhere);
    }

    @Override
    public List<HorariosMaterias> seleccionarTodos() {
        return new ArrayList<>();
    }

    private HorariosMaterias obtenerObjDeCursor(Cursor cursor) {
        HorariosMaterias obj = new HorariosMaterias();

        obj.setID(!cursor.isNull(cursor.getColumnIndex(ID)) ? cursor.getInt(cursor.getColumnIndex(ID)) : 0);
		obj.setLGRUPOHORARIO_ID(cursor.getInt(cursor.getColumnIndex(LGRUPOHORARIO_ID)));
		obj.setLGRUPO_ID(cursor.getInt(cursor.getColumnIndex(LGRUPO_ID)));
		obj.setENTRADA(cursor.getString(cursor.getColumnIndex(ENTRADA)));
		obj.setSALIDA(cursor.getString(cursor.getColumnIndex(SALIDA)));
		obj.setLDIA(cursor.getInt(cursor.getColumnIndex(LDIA)));
        obj.setSDIA_DSC(cursor.getString(cursor.getColumnIndex(SDIA_DSC)));
        obj.setSAULA_DSC(cursor.getString(cursor.getColumnIndex(SAULA_DSC)));

        return obj;
    }

    @Override
    public void insertar(JSONObject json) {
        HorariosMaterias objHorarioMateria = new HorariosMaterias();

        try {
            if (!json.isNull(ID)) objHorarioMateria.setID(json.getInt(ID));
            if (!json.isNull(LGRUPOHORARIO_ID)) objHorarioMateria.setLGRUPOHORARIO_ID(json.getInt(LGRUPOHORARIO_ID));
            if (!json.isNull(LGRUPO_ID)) objHorarioMateria.setLGRUPO_ID(json.getInt(LGRUPO_ID));
            objHorarioMateria.setENTRADA(json.isNull(ENTRADA) ? "" : json.getString(ENTRADA));
            objHorarioMateria.setSALIDA(json.isNull(SALIDA) ? "" : json.getString(SALIDA));
            if (!json.isNull(LDIA)) objHorarioMateria.setLDIA(json.getInt(LDIA));
            objHorarioMateria.setSDIA_DSC(json.isNull(SDIA_DSC) ? "" : json.getString(SDIA_DSC));
            objHorarioMateria.setSAULA_DSC(json.isNull(SAULA_DSC) ? "" : json.getString(SAULA_DSC));
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertar(objHorarioMateria);
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
        con.ejecutarSentencia("delete from " + Tablas.HorariosMaterias);
    }

    @Override
    public List<HorariosMaterias> seleccionar(String grupo) {
        Conexion con = Conexion.getOrCreate();

        String where = "LGRUPO_ID = ?";
        String[] parametrosWhere = { grupo };

        Cursor cursor = con.ejecutarConsulta(Tablas.HorariosMaterias, columnas, where, parametrosWhere);
        List<HorariosMaterias> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            HorariosMaterias obj = obtenerObjDeCursor(cursor);
            lista.add(obj);
        }

        return lista;
    }

    @Override
    public void eliminarDeMateria(String grupo) {
        String where = "LGRUPO_ID = ?";
        String[] parametrosWhere = { grupo };

        Conexion con = Conexion.getOrCreate();
        con.eliminar(Tablas.HorariosMaterias, where, parametrosWhere);
    }

}