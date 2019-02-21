package com.example.ricardopazdemiquel.plataformanur.conexion;

/**
 * Una representacion del nombre de las tablas, para tomar como unica referencia a la hora de
 * manipularlas
 */
public enum Tablas {

    Notas("Notas"),
    MateriasOfertadas("MateriasOfertadas"),
    HorariosOfertados("HorariosOfertados");

    private final String texto;

    Tablas(final String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }

}