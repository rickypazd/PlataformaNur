package com.example.ricardopazdemiquel.plataformanur.dao;

import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.dto.RequisitosMaterias;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class RequisitosMateriasDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * RequisitosMaterias
     */
    public abstract List<RequisitosMaterias> seleccionarTodos();
    protected String[] columnas;
    public abstract int insertar(JSONObject json);
    public abstract void truncate();
    public abstract void insercionMasiva(JSONArray array);

    public RequisitosMateriasDAO(String... columnas) {
		this.columnas = columnas;
    }

}