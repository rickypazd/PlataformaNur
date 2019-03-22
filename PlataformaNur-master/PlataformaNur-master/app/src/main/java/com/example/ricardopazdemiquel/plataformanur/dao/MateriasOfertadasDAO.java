package com.example.ricardopazdemiquel.plataformanur.dao;

import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class MateriasOfertadasDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * MateriasOfertadas
     */
    public abstract List<MateriasOfertadas> seleccionarTodos();
    public abstract int insertar(JSONObject obj);
    public abstract void actualizar(JSONObject obj);
    public abstract void truncate();
    public abstract List<MateriasOfertadas> seleccionar(int carrera, int periodo);
    public abstract MateriasOfertadas seleccionar(int carrera, int periodo, String materia);
    public abstract void insercionMasiva(int carreraId, int periodoId, JSONArray jsonArray);
    protected String[] columnas;

    public MateriasOfertadasDAO(String... columnas) {
		this.columnas = columnas;
    }

}