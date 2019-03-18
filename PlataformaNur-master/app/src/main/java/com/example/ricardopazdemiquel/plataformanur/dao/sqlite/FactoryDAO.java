package com.example.ricardopazdemiquel.plataformanur.dao.sqlite;

import com.example.ricardopazdemiquel.plataformanur.dao.*;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosMateriasDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO;

/**
 * Devuelve implementaciones DAO para el SGBD SQLite
 */
public class FactoryDAO extends com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO {


    @Override
    public NotasDAO newNotasDAO() {
        return new com.example.ricardopazdemiquel.plataformanur.dao.sqlite.NotasDAO();
    }

    @Override
    public MateriasOfertadasDAO newMateriasOfertadasDAO() {
		return new com.example.ricardopazdemiquel.plataformanur.dao.sqlite.MateriasOfertadasDAO();
    }

    @Override
    public HorariosOfertadosDAO newHorariosOfertadosDAO() {
		return new com.example.ricardopazdemiquel.plataformanur.dao.sqlite.HorariosOfertadosDAO();
    }

    @Override
    public HorariosMateriasDAO newHorariosMateriasDAO() {
        return new com.example.ricardopazdemiquel.plataformanur.dao.sqlite.HorariosMateriasDAO();
    }
}