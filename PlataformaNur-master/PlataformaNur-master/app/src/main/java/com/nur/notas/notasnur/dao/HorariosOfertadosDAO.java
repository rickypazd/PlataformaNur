package com.nur.notas.notasnur.dao;

import java.util.List;

import com.nur.notas.notasnur.dto.HorariosOfertados;

import org.json.JSONObject;

public abstract class HorariosOfertadosDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * HorariosOfertados
     */
    public abstract List<HorariosOfertados> seleccionarTodos();
    public abstract void insertar(JSONObject obj);
    public abstract void actualizar(JSONObject obj);
    public abstract void truncate();
    public abstract List<HorariosOfertados> seleccionar(int materiaOfertada);
    public abstract void eliminarDeMateria(int materia);
    protected String[] columnas;

    public HorariosOfertadosDAO(String... columnas) {
		this.columnas = columnas;
    }

}