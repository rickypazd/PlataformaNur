package com.example.ricardopazdemiquel.plataformanur.dao;

import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.dto.Materias;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class MateriasDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * Materias
     */
    public abstract List<Materias> seleccionarTodos();
    protected String[] columnas;
    public abstract int insertar(JSONObject json);
    public abstract void truncate();
    public abstract void insercionMasiva(int carreraId, JSONArray jsonArray);
    public abstract void insercionMasiva(int carreraId, JSONArray jsonArray, boolean materiaFaltante);

    public MateriasDAO(String... columnas) {
		this.columnas = columnas;
    }

}