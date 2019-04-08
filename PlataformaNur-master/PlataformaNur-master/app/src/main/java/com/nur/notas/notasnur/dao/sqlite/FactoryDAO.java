package com.nur.notas.notasnur.dao.sqlite;

import com.nur.notas.notasnur.dao.HorariosMateriasDAO;
import com.nur.notas.notasnur.dao.HorariosOfertadosDAO;
import com.nur.notas.notasnur.dao.MateriasDAO;
import com.nur.notas.notasnur.dao.MateriasOfertadasDAO;
import com.nur.notas.notasnur.dao.NotasDAO;
import com.nur.notas.notasnur.dao.RequisitosMateriasDAO;

/**
 * Devuelve implementaciones DAO para el SGBD SQLite
 */
public class FactoryDAO extends com.nur.notas.notasnur.dao.FactoryDAO {


    @Override
    public NotasDAO newNotasDAO() {
        return new com.nur.notas.notasnur.dao.sqlite.NotasDAO();
    }

    @Override
    public MateriasOfertadasDAO newMateriasOfertadasDAO() {
		return new com.nur.notas.notasnur.dao.sqlite.MateriasOfertadasDAO();
    }

    @Override
    public HorariosOfertadosDAO newHorariosOfertadosDAO() {
		return new com.nur.notas.notasnur.dao.sqlite.HorariosOfertadosDAO();
    }

    @Override
    public HorariosMateriasDAO newHorariosMateriasDAO() {
        return new com.nur.notas.notasnur.dao.sqlite.HorariosMateriasDAO();
    }

    @Override
    public MateriasDAO newMateriasDAO() {
        return new com.nur.notas.notasnur.dao.sqlite.MateriasDAO();
    }

    @Override
    public RequisitosMateriasDAO newRequisitosMateriasDAO() {
        return new com.nur.notas.notasnur.dao.sqlite.RequisitosMateriasDAO();
    }
}