package com.nur.notas.notasnur.dto;

public class HorariosOfertados extends DTO {

	private int LDIA_ID;
	private String SDIA_DSC;
	private String DTHRENTRADA;
	private String DTHRSALIDA;
	private int MAT_OFERTADA_ID;

	public HorariosOfertados() {
	}

	public HorariosOfertados(int LDIA_ID, String SDIA_DSC, String DTHRENTRADA, String DTHRSALIDA, int MAT_OFERTADA_ID) {
		this.LDIA_ID = LDIA_ID;
		this.SDIA_DSC = SDIA_DSC;
		this.DTHRENTRADA = DTHRENTRADA;
		this.DTHRSALIDA = DTHRSALIDA;
		this.MAT_OFERTADA_ID = MAT_OFERTADA_ID;
	}

	// <editor-fold defaultstate="collapsed" desc="getters y setters">

	public int getLDIA_ID() {
		return LDIA_ID;
	}

	public void setLDIA_ID(int LDIA_ID) {
		this.LDIA_ID = LDIA_ID;
	}

	public String getSDIA_DSC() {
		return SDIA_DSC;
	}

	public void setSDIA_DSC(String SDIA_DSC) {
		this.SDIA_DSC = SDIA_DSC;
	}

	public String getDTHRENTRADA() {
		return DTHRENTRADA;
	}

	public void setDTHRENTRADA(String DTHRENTRADA) {
		this.DTHRENTRADA = DTHRENTRADA;
	}

	public String getDTHRSALIDA() {
		return DTHRSALIDA;
	}

	public void setDTHRSALIDA(String DTHRSALIDA) {
		this.DTHRSALIDA = DTHRSALIDA;
	}

	public int getMAT_OFERTADA_ID() {
		return MAT_OFERTADA_ID;
	}

	public void setMAT_OFERTADA_ID(int MAT_OFERTADA_ID) {
		this.MAT_OFERTADA_ID = MAT_OFERTADA_ID;
	}

    // </editor-fold>

    @Override
    public String toString() {
		return "HorariosOfertados{" +
				"LDIA_ID=" + LDIA_ID +
				",SDIA_DSC=" + SDIA_DSC +
				",DTHRENTRADA=" + DTHRENTRADA +
				",DTHRSALIDA=" + DTHRSALIDA +
				",MAT_OFERTADA_ID=" + MAT_OFERTADA_ID +
				'}';
     }

}