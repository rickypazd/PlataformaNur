package com.example.ricardopazdemiquel.plataformanur.Objs;

public class HorarioOferta {

    private int LDIA_ID;
    private String SDIA_DSC;
    private  String DTHRENTRADA;
    private  String DTHRSALIDA;

    public HorarioOferta() {
    }

    public HorarioOferta(int LDIA_ID, String SDIA_DSC, String DTHRENTRADA, String DTHRSALIDA) {
        this.LDIA_ID = LDIA_ID;
        this.SDIA_DSC = SDIA_DSC;
        this.DTHRENTRADA = DTHRENTRADA;
        this.DTHRSALIDA = DTHRSALIDA;
    }

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
}
