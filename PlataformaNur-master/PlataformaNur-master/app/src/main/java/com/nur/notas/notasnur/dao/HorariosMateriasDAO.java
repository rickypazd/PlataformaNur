package com.nur.notas.notasnur.dao;

import com.nur.notas.notasnur.dto.HorariosMaterias;

import org.json.JSONObject;

import java.util.List;

public abstract class HorariosMateriasDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * HorariosMaterias
     */
    public abstract List<HorariosMaterias> seleccionarTodos();
    public abstract void insertar(JSONObject obj);
    public abstract void actualizar(JSONObject obj);
    public abstract void truncate();
    public abstract List<HorariosMaterias> seleccionar(String grupo);
    public abstract void eliminarDeMateria(String materia);
    protected String[] columnas;

    public HorariosMateriasDAO(String... columnas) {
		this.columnas = columnas;
    }

}