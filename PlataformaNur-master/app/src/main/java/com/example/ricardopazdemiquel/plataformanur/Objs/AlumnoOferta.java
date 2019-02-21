package com.example.ricardopazdemiquel.plataformanur.Objs;

import java.util.ArrayList;

public class AlumnoOferta {

    private int LGRUPO_ID;
    private int LCENTRO_ID;
    private String SCENTRO_DSC;
    private String SMATERIA_DSC;
    private int LSEMESTRE;
    private int LCREDITOS;
    private int LLABORATORIO;
    private String DOCENTE;
    private String SCODMATERIA;
    private String CASILLA;
    private String SCODGRUPO;
    private String SSEMANA;
    private int LESTADOGRUPO_ID;
    private String SESTADOGRUPO_DSC;
    private String SOBS1;
    private ArrayList<HorarioOferta> HORARIO;

    public AlumnoOferta() {
    }

    public AlumnoOferta(int LGRUPO_ID, int LCENTRO_ID, String SCENTRO_DSC, String SMATERIA_DSC, int LSEMESTRE, int LCREDITOS, int LLABORATORIO, String DOCENTE, String SCODMATERIA, String CASILLA, String SCODGRUPO, String SSEMANA, int LESTADOGRUPO_ID, String SESTADOGRUPO_DSC, String SOBS1, ArrayList<HorarioOferta> HORARIO) {
        this.LGRUPO_ID = LGRUPO_ID;
        this.LCENTRO_ID = LCENTRO_ID;
        this.SCENTRO_DSC = SCENTRO_DSC;
        this.SMATERIA_DSC = SMATERIA_DSC;
        this.LSEMESTRE = LSEMESTRE;
        this.LCREDITOS = LCREDITOS;
        this.LLABORATORIO = LLABORATORIO;
        this.DOCENTE = DOCENTE;
        this.SCODMATERIA = SCODMATERIA;
        this.CASILLA = CASILLA;
        this.SCODGRUPO = SCODGRUPO;
        this.SSEMANA = SSEMANA;
        this.LESTADOGRUPO_ID = LESTADOGRUPO_ID;
        this.SESTADOGRUPO_DSC = SESTADOGRUPO_DSC;
        this.SOBS1 = SOBS1;
        this.HORARIO = HORARIO;
    }

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

    public String getSCENTRO_DSC() {
        return SCENTRO_DSC;
    }

    public void setSCENTRO_DSC(String SCENTRO_DSC) {
        this.SCENTRO_DSC = SCENTRO_DSC;
    }

    public String getSMATERIA_DSC() {
        return SMATERIA_DSC;
    }

    public void setSMATERIA_DSC(String SMATERIA_DSC) {
        this.SMATERIA_DSC = SMATERIA_DSC;
    }

    public int getLSEMESTRE() {
        return LSEMESTRE;
    }

    public void setLSEMESTRE(int LSEMESTRE) {
        this.LSEMESTRE = LSEMESTRE;
    }

    public int getLCREDITOS() {
        return LCREDITOS;
    }

    public void setLCREDITOS(int LCREDITOS) {
        this.LCREDITOS = LCREDITOS;
    }

    public int getLLABORATORIO() {
        return LLABORATORIO;
    }

    public void setLLABORATORIO(int LLABORATORIO) {
        this.LLABORATORIO = LLABORATORIO;
    }

    public String getDOCENTE() {
        return DOCENTE;
    }

    public void setDOCENTE(String DOCENTE) {
        this.DOCENTE = DOCENTE;
    }

    public String getSCODMATERIA() {
        return SCODMATERIA;
    }

    public void setSCODMATERIA(String SCODMATERIA) {
        this.SCODMATERIA = SCODMATERIA;
    }

    public String getCASILLA() {
        return CASILLA;
    }

    public void setCASILLA(String CASILLA) {
        this.CASILLA = CASILLA;
    }

    public String getSCODGRUPO() {
        return SCODGRUPO;
    }

    public void setSCODGRUPO(String SCODGRUPO) {
        this.SCODGRUPO = SCODGRUPO;
    }

    public String getSSEMANA() {
        return SSEMANA;
    }

    public void setSSEMANA(String SSEMANA) {
        this.SSEMANA = SSEMANA;
    }

    public int getLESTADOGRUPO_ID() {
        return LESTADOGRUPO_ID;
    }

    public void setLESTADOGRUPO_ID(int LESTADOGRUPO_ID) {
        this.LESTADOGRUPO_ID = LESTADOGRUPO_ID;
    }

    public String getSESTADOGRUPO_DSC() {
        return SESTADOGRUPO_DSC;
    }

    public void setSESTADOGRUPO_DSC(String SESTADOGRUPO_DSC) {
        this.SESTADOGRUPO_DSC = SESTADOGRUPO_DSC;
    }

    public String getSOBS1() {
        return SOBS1;
    }

    public void setSOBS1(String SOBS1) {
        this.SOBS1 = SOBS1;
    }

    public ArrayList<HorarioOferta> getHORARIO() {
        return HORARIO;
    }

    public void setHORARIO(ArrayList<HorarioOferta> HORARIO) {
        this.HORARIO = HORARIO;
    }
}
