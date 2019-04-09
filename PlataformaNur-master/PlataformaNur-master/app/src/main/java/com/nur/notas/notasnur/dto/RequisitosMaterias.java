package com.nur.notas.notasnur.dto;

public class RequisitosMaterias extends DTO {

	private int ID;
	private String SMATERIA_DSC;
	private int MATERIA_ID;

	public RequisitosMaterias() {
	}

	public RequisitosMaterias(String SMATERIA_DSC, int MATERIA_ID) {
		this.SMATERIA_DSC = SMATERIA_DSC;
		this.MATERIA_ID = MATERIA_ID;
	}

	// <editor-fold defaultstate="collapsed" desc="getters y setters">

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getSMATERIA_DSC() {
		return SMATERIA_DSC;
	}

	public void setSMATERIA_DSC(String SMATERIA_DSC) {
		this.SMATERIA_DSC = SMATERIA_DSC;
	}

	public int getMATERIA_ID() {
		return MATERIA_ID;
	}

	public void setMATERIA_ID(int MATERIA_ID) {
		this.MATERIA_ID = MATERIA_ID;
	}

    // </editor-fold>

    @Override
    public String toString() {
		return "RequisitosMaterias{" +
				"SMATERIA_DSC=" + SMATERIA_DSC +
				",MATERIA_ID=" + MATERIA_ID +
				'}';
     }

}