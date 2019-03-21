package com.example.ricardopazdemiquel.plataformanur.dao;

import java.util.List;

import com.example.ricardopazdemiquel.plataformanur.dto.Notas;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class NotasDAO implements IDAO {

    /**
     * Devuelve una lista con todos los registros de la tabla
     * Notas
     */
    public abstract List<Notas> seleccionarTodos();
    public abstract int insertar(JSONObject obj);
    public abstract void actualizar(JSONObject obj);
    public abstract void truncate();
    public abstract List<Notas> seleccionar(int carrera, int periodo);
    public abstract Notas seleccionar(int carrera, int periodo, String materia);
    public abstract void insercionMasiva(int carreraId, int periodoId, JSONArray jsonArray);
    protected String[] columnas;

    public NotasDAO(String... columnas) {
		this.columnas = columnas;
    }

}