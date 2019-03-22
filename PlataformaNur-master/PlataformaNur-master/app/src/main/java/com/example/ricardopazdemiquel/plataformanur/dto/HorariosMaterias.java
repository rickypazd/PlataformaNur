package com.example.ricardopazdemiquel.plataformanur.dto;

public class HorariosMaterias extends DTO {

    private int ID;
    private int LGRUPOHORARIO_ID; // : 58495,
    private int LGRUPO_ID; // : 58871,
    private String ENTRADA; // : "19:00",
    private String SALIDA; // : "20:30",
    private int LDIA; // : 1,
    private String SDIA_DSC; // : "Lunes",
    private String SAULA_DSC; // : "307"

    public HorariosMaterias() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLGRUPOHORARIO_ID() {
        return LGRUPOHORARIO_ID;
    }

    public void setLGRUPOHORARIO_ID(int LGRUPOHORARIO_ID) {
        this.LGRUPOHORARIO_ID = LGRUPOHORARIO_ID;
    }

    public int getLGRUPO_ID() {
        return LGRUPO_ID;
    }

    public void setLGRUPO_ID(int LGRUPO_ID) {
        this.LGRUPO_ID = LGRUPO_ID;
    }

    public String getENTRADA() {
        return ENTRADA;
    }

    public void setENTRADA(String ENTRADA) {
        this.ENTRADA = ENTRADA;
    }

    public String getSALIDA() {
        return SALIDA;
    }

    public void setSALIDA(String SALIDA) {
        this.SALIDA = SALIDA;
    }

    public int getLDIA() {
        return LDIA;
    }

    public void setLDIA(int LDIA) {
        this.LDIA = LDIA;
    }

    public String getSDIA_DSC() {
        return SDIA_DSC;
    }

    public void setSDIA_DSC(String SDIA_DSC) {
        this.SDIA_DSC = SDIA_DSC;
    }

    public String getSAULA_DSC() {
        return SAULA_DSC;
    }

    public void setSAULA_DSC(String SAULA_DSC) {
        this.SAULA_DSC = SAULA_DSC;
    }
}
