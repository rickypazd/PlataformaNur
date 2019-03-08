package com.example.ricardopazdemiquel.plataformanur.dto;

public class Notas extends DTO {

	/* LA LLAVE PRIMARIA ES LCARRERA_ID, LPERIODO_ID, SCODMATERIA*/
	private int LGRUPO_ID;
	private int LCENTRO_ID;
	private String SCODCENTRO;
	private String SCENTRO_DSC;
	private String SCODMATERIA;
	private String SSIGLA;
	private String SMATERIA_DSC;
	private String SCODGRUPO;
	private String DOCENTE;
	private String PAR1;
	private String PAR2;
	private String EXFINAL;
	private String FINAL;
	private int FMES1;
	private int FMES2;
	private int FMES3;
	private int FMES4;
	private int FMES5;
	private int FMES6;
	private int FMES7;
	private int FMES8;
	private int FMES9;
	private int FMES10;
	private int FMES11;
	private int FMES12;

	private int LPERIODO_ID;
	private int LCARRERA_ID;

	public Notas() {
	}

	public Notas(int LGRUPO_ID, int LCENTRO_ID, String SCODCENTRO, String SCENTRO_DSC, String SCODMATERIA, String SSIGLA, String SMATERIA_DSC, String SCODGRUPO, String DOCENTE, String PAR1, String PAR2, String EXFINAL, String FINAL, int FMES1, int FMES2, int FMES3, int FMES4, int FMES5, int FMES6, int FMES7, int FMES8, int FMES9, int FMES10, int FMES11, int FMES12, int LPERIODO_ID, int LCARRERA_ID) {
		this.LGRUPO_ID = LGRUPO_ID;
		this.LCENTRO_ID = LCENTRO_ID;
		this.SCODCENTRO = SCODCENTRO;
		this.SCENTRO_DSC = SCENTRO_DSC;
		this.SCODMATERIA = SCODMATERIA;
		this.SSIGLA = SSIGLA;
		this.SMATERIA_DSC = SMATERIA_DSC;
		this.SCODGRUPO = SCODGRUPO;
		this.DOCENTE = DOCENTE;
		this.PAR1 = PAR1;
		this.PAR2 = PAR2;
		this.EXFINAL = EXFINAL;
		this.FINAL = FINAL;
		this.FMES1 = FMES1;
		this.FMES2 = FMES2;
		this.FMES3 = FMES3;
		this.FMES4 = FMES4;
		this.FMES5 = FMES5;
		this.FMES6 = FMES6;
		this.FMES7 = FMES7;
		this.FMES8 = FMES8;
		this.FMES9 = FMES9;
		this.FMES10 = FMES10;
		this.FMES11 = FMES11;
		this.FMES12 = FMES12;
		this.LPERIODO_ID = LPERIODO_ID;
		this.LCARRERA_ID = LCARRERA_ID;
	}

	// <editor-fold defaultstate="collapsed" desc="getters y setters">

	public int getLGRUPO_ID() {
		return LGRUPO_ID;
	}

	public void setLGRUPO_ID(int LGRUPO_ID) {
		this.LGRUPO_ID = LGRUPO_ID;
	}

	public int getLCENTRO_ID() {
		return LCENTRO_ID;
	}

	public void setLCENTRO_ID(int LCENTRO_ID) {
		this.LCENTRO_ID = LCENTRO_ID;
	}

	public String getSCODCENTRO() {
		return SCODCENTRO;
	}

	public void setSCODCENTRO(String SCODCENTRO) {
		this.SCODCENTRO = SCODCENTRO;
	}

	public String getSCENTRO_DSC() {
		return SCENTRO_DSC;
	}

	public void setSCENTRO_DSC(String SCENTRO_DSC) {
		this.SCENTRO_DSC = SCENTRO_DSC;
	}

	public String getSCODMATERIA() {
		return SCODMATERIA;
	}

	public void setSCODMATERIA(String SCODMATERIA) {
		this.SCODMATERIA = SCODMATERIA;
	}

	public String getSSIGLA() {
		return SSIGLA;
	}

	public void setSSIGLA(String SSIGLA) {
		this.SSIGLA = SSIGLA;
	}

	public String getSMATERIA_DSC() {
		return SMATERIA_DSC;
	}

	public void setSMATERIA_DSC(String SMATERIA_DSC) {
		this.SMATERIA_DSC = SMATERIA_DSC;
	}

	public String getSCODGRUPO() {
		return SCODGRUPO;
	}

	public void setSCODGRUPO(String SCODGRUPO) {
		this.SCODGRUPO = SCODGRUPO;
	}

	public String getDOCENTE() {
		return DOCENTE;
	}

	public void setDOCENTE(String DOCENTE) {
		this.DOCENTE = DOCENTE;
	}

	public String getPAR1() {
		return PAR1;
	}

	public void setPAR1(String PAR1) {
		this.PAR1 = PAR1;
	}

	public String getPAR2() {
		return PAR2;
	}

	public void setPAR2(String PAR2) {
		this.PAR2 = PAR2;
	}

	public String getEXFINAL() {
		return EXFINAL;
	}

	public void setEXFINAL(String EXFINAL) {
		this.EXFINAL = EXFINAL;
	}

	public String getFINAL() {
		return FINAL;
	}

	public void setFINAL(String FINAL) {
		this.FINAL = FINAL;
	}

	public int getFMES1() {
		return FMES1;
	}

	public void setFMES1(int FMES1) {
		this.FMES1 = FMES1;
	}

	public int getFMES2() {
		return FMES2;
	}

	public void setFMES2(int FMES2) {
		this.FMES2 = FMES2;
	}

	public int getFMES3() {
		return FMES3;
	}

	public void setFMES3(int FMES3) {
		this.FMES3 = FMES3;
	}

	public int getFMES4() {
		return FMES4;
	}

	public void setFMES4(int FMES4) {
		this.FMES4 = FMES4;
	}

	public int getFMES5() {
		return FMES5;
	}

	public void setFMES5(int FMES5) {
		this.FMES5 = FMES5;
	}

	public int getFMES6() {
		return FMES6;
	}

	public void setFMES6(int FMES6) {
		this.FMES6 = FMES6;
	}

	public int getFMES7() {
		return FMES7;
	}

	public void setFMES7(int FMES7) {
		this.FMES7 = FMES7;
	}

	public int getFMES8() {
		return FMES8;
	}

	public void setFMES8(int FMES8) {
		this.FMES8 = FMES8;
	}

	public int getFMES9() {
		return FMES9;
	}

	public void setFMES9(int FMES9) {
		this.FMES9 = FMES9;
	}

	public int getFMES10() {
		return FMES10;
	}

	public void setFMES10(int FMES10) {
		this.FMES10 = FMES10;
	}

	public int getFMES11() {
		return FMES11;
	}

	public void setFMES11(int FMES11) {
		this.FMES11 = FMES11;
	}

	public int getFMES12() {
		return FMES12;
	}

	public void setFMES12(int FMES12) {
		this.FMES12 = FMES12;
	}

	public int getLPERIODO_ID() {
		return LPERIODO_ID;
	}

	public void setLPERIODO_ID(int LPERIODO_ID) {
		this.LPERIODO_ID = LPERIODO_ID;
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
		return "Notas{" +
				"LGRUPO_ID=" + LGRUPO_ID +
				",LCENTRO_ID=" + LCENTRO_ID +
				",SCODCENTRO=" + SCODCENTRO +
				",SCENTRO_DSC=" + SCENTRO_DSC +
				",SCODMATERIA=" + SCODMATERIA +
				",SSIGLA=" + SSIGLA +
				",SMATERIA_DSC=" + SMATERIA_DSC +
				",SCODGRUPO=" + SCODGRUPO +
				",DOCENTE=" + DOCENTE +
				",PAR1=" + PAR1 +
				",PAR2=" + PAR2 +
				",EXFINAL=" + EXFINAL +
				",FINAL=" + FINAL +
				",FMES1=" + FMES1 +
				",FMES2=" + FMES2 +
				",FMES3=" + FMES3 +
				",FMES4=" + FMES4 +
				",FMES5=" + FMES5 +
				",FMES6=" + FMES6 +
				",FMES7=" + FMES7 +
				",FMES8=" + FMES8 +
				",FMES9=" + FMES9 +
				",FMES10=" + FMES10 +
				",FMES11=" + FMES11 +
				",FMES12=" + FMES12 +
				'}';
     }

}