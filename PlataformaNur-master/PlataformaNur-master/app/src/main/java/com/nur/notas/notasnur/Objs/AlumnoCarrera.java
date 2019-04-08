package com.nur.notas.notasnur.Objs;

public class AlumnoCarrera {

    /*
            "LCARRERA_ID": 1,
            "SCODCENTRO": "000",
            "SCENTRO_DSC": "PRESENCIAL",
            "SCODCARRERA": "0001",
            "SCARRERA_DSC": "Ingeniería de Sistemas",
            "LCODPENSUM": 3,
            "LCREDVENCIDOS": 61,
            "LPERIODOINICIO": "2016-1",
            "LPERIODOACTUAL_ID": 127,
            "LPERIODOACTUAL": "2017-2",
            "LPERIODOFIN": null,
            "DTFECHADMISION": "2016-01-12",
            "DTFECHEGRESO": null,
            "DTFECHTITULACION": null,
            "STIPOGRADUACION_DSC": null
     */

    private int LCARRERA_ID;
    private String SCODCENTRO;
    private String SCENTRO_DSC;
    private String SCODCARRERA;
    private String SCARRERA_DSC;
    private int LCODPENSUM;
    private int LCREDVENCIDOS;
    private String LPERIODOINICIO;
    private int LPERIODOACTUAL_ID;
    private String LPERIODOACTUAL;
    private String LPERIODOFIN;
    private String DTFECHADMISION;
    private String DTFECHEGRESO;
    private String DTFECHTITULACION;
    private String STIPOGRADUACION_DSC;

    public AlumnoCarrera() {
    }

    public AlumnoCarrera(int LCARRERA_ID, String SCODCENTRO, String SCENTRO_DSC, String SCODCARRERA, String SCARRERA_DSC, int LCODPENSUM, int LCREDVENCIDOS, String LPERIODOINICIO, int LPERIODOACTUAL_ID, String LPERIODOACTUAL, String LPERIODOFIN, String DTFECHADMISION, String DTFECHEGRESO, String DTFECHTITULACION, String STIPOGRADUACION_DSC) {
        this.LCARRERA_ID = LCARRERA_ID;
        this.SCODCENTRO = SCODCENTRO;
        this.SCENTRO_DSC = SCENTRO_DSC;
        this.SCODCARRERA = SCODCARRERA;
        this.SCARRERA_DSC = SCARRERA_DSC;
        this.LCODPENSUM = LCODPENSUM;
        this.LCREDVENCIDOS = LCREDVENCIDOS;
        this.LPERIODOINICIO = LPERIODOINICIO;
        this.LPERIODOACTUAL_ID = LPERIODOACTUAL_ID;
        this.LPERIODOACTUAL = LPERIODOACTUAL;
        this.LPERIODOFIN = LPERIODOFIN;
        this.DTFECHADMISION = DTFECHADMISION;
        this.DTFECHEGRESO = DTFECHEGRESO;
        this.DTFECHTITULACION = DTFECHTITULACION;
        this.STIPOGRADUACION_DSC = STIPOGRADUACION_DSC;
    }

    public int getLCARRERA_ID() {
        return LCARRERA_ID;
    }

    public void setLCARRERA_ID(int LCARRERA_ID) {
        this.LCARRERA_ID = LCARRERA_ID;
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

    public String getSCODCARRERA() {
        return SCODCARRERA;
    }

    public void setSCODCARRERA(String SCODCARRERA) {
        this.SCODCARRERA = SCODCARRERA;
    }

    public String getSCARRERA_DSC() {
        return SCARRERA_DSC;
    }

    public void setSCARRERA_DSC(String SCARRERA_DSC) {
        this.SCARRERA_DSC = SCARRERA_DSC;
    }

    public int getLCODPENSUM() {
        return LCODPENSUM;
    }

    public void setLCODPENSUM(int LCODPENSUM) {
        this.LCODPENSUM = LCODPENSUM;
    }

    public int getLCREDVENCIDOS() {
        return LCREDVENCIDOS;
    }

    public void setLCREDVENCIDOS(int LCREDVENCIDOS) {
        this.LCREDVENCIDOS = LCREDVENCIDOS;
    }

    public String getLPERIODOINICIO() {
        return LPERIODOINICIO;
    }

    public void setLPERIODOINICIO(String LPERIODOINICIO) {
        this.LPERIODOINICIO = LPERIODOINICIO;
    }

    public int getLPERIODOACTUAL_ID() {
        return LPERIODOACTUAL_ID;
    }

    public void setLPERIODOACTUAL_ID(int LPERIODOACTUAL_ID) {
        this.LPERIODOACTUAL_ID = LPERIODOACTUAL_ID;
    }

    public String getLPERIODOACTUAL() {
        return LPERIODOACTUAL;
    }

    public void setLPERIODOACTUAL(String LPERIODOACTUAL) {
        this.LPERIODOACTUAL = LPERIODOACTUAL;
    }

    public String getLPERIODOFIN() {
        return LPERIODOFIN;
    }

    public void setLPERIODOFIN(String LPERIODOFIN) {
        this.LPERIODOFIN = LPERIODOFIN;
    }

    public String getDTFECHADMISION() {
        return DTFECHADMISION;
    }

    public void setDTFECHADMISION(String DTFECHADMISION) {
        this.DTFECHADMISION = DTFECHADMISION;
    }

    public String getDTFECHEGRESO() {
        return DTFECHEGRESO;
    }

    public void setDTFECHEGRESO(String DTFECHEGRESO) {
        this.DTFECHEGRESO = DTFECHEGRESO;
    }

    public String getDTFECHTITULACION() {
        return DTFECHTITULACION;
    }

    public void setDTFECHTITULACION(String DTFECHTITULACION) {
        this.DTFECHTITULACION = DTFECHTITULACION;
    }

    public String getSTIPOGRADUACION_DSC() {
        return STIPOGRADUACION_DSC;
    }

    public void setSTIPOGRADUACION_DSC(String STIPOGRADUACION_DSC) {
        this.STIPOGRADUACION_DSC = STIPOGRADUACION_DSC;
    }
}
