package com.example.ricardopazdemiquel.plataformanur.dto;

public class Materias extends DTO {

	private int ID;
	private String SSIGLACODIGO;
	private String SCENTRO;
	private String SMATERIA_DSC;
	private int LCREDITOS;
	private int LNOTA;
	private String SPERIODO_DSC;
	private String OBS;
	private int LPOSICION;
	private String SNROCARRERA;
	private int LPENSUM;
	private String CODMATERIA;
	private int LSEMESTRE;
	private int LMATERIA_ID;
	private int LCARRERA_ID;

	public Materias() {
	}

	// <editor-fold defaultstate="collapsed" desc="getters y setters">

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getSSIGLACODIGO() {
		return SSIGLACODIGO;
	}

	public void setSSIGLACODIGO(String SSIGLACODIGO) {
		this.SSIGLACODIGO = SSIGLACODIGO;
	}

	public String getSCENTRO() {
		return SCENTRO;
	}

	public void setSCENTRO(String SCENTRO) {
		this.SCENTRO = SCENTRO;
	}

	public String getSMATERIA_DSC() {
		return SMATERIA_DSC;
	}

	public void setSMATERIA_DSC(String SMATERIA_DSC) {
		this.SMATERIA_DSC = SMATERIA_DSC;
	}

	public int getLCREDITOS() {
		return LCREDITOS;
	}

	public void setLCREDITOS(int LCREDITOS) {
		this.LCREDITOS = LCREDITOS;
	}

	public int getLNOTA() {
		return LNOTA;
	}

	public void setLNOTA(int LNOTA) {
		this.LNOTA = LNOTA;
	}

	public String getSPERIODO_DSC() {
		return SPERIODO_DSC;
	}

	public void setSPERIODO_DSC(String SPERIODO_DSC) {
		this.SPERIODO_DSC = SPERIODO_DSC;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String OBS) {
		this.OBS = OBS;
	}

	public int getLPOSICION() {
		return LPOSICION;
	}

	public void setLPOSICION(int LPOSICION) {
		this.LPOSICION = LPOSICION;
	}

	public String getSNROCARRERA() {
		return SNROCARRERA;
	}

	public void setSNROCARRERA(String SNROCARRERA) {
		this.SNROCARRERA = SNROCARRERA;
	}

	public int getLPENSUM() {
		return LPENSUM;
	}

	public void setLPENSUM(int LPENSUM) {
		this.LPENSUM = LPENSUM;
	}

	public String getCODMATERIA() {
		return CODMATERIA;
	}

	public void setCODMATERIA(String CODMATERIA) {
		this.CODMATERIA = CODMATERIA;
	}

	public int getLSEMESTRE() {
		return LSEMESTRE;
	}

	public void setLSEMESTRE(int LSEMESTRE) {
		this.LSEMESTRE = LSEMESTRE;
	}

	public int getLMATERIA_ID() {
		return LMATERIA_ID;
	}

	public void setLMATERIA_ID(int LMATERIA_ID) {
		this.LMATERIA_ID = LMATERIA_ID;
	}

	public int getLCARRERA_ID() {
		return LCARRERA_ID;
	}

	public void setLCARRERA_ID(int LCARRERA_ID) {
		this.LCARRERA_ID = LCARRERA_ID;
	}

// </editor-fold>

    @Override
    public String toString() {
		return "Materias{" +
				"SSIGLACODIGO=" + SSIGLACODIGO +
				",SCENTRO=" + SCENTRO +
				",SMATERIA_DSC=" + SMATERIA_DSC +
				",LCREDITOS=" + LCREDITOS +
				",LNOTA=" + LNOTA +
				",SPERIODO_DSC=" + SPERIODO_DSC +
				",OBS=" + OBS +
				",LPOSICION=" + LPOSICION +
				",SNROCARRERA=" + SNROCARRERA +
				",LPENSUM=" + LPENSUM +
				",CODMATERIA=" + CODMATERIA +
				",LSEMESTRE=" + LSEMESTRE +
				",LMATERIA_ID=" + LMATERIA_ID +
				'}';
     }

}