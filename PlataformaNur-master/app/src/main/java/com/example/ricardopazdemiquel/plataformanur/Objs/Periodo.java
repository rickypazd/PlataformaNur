package com.nur.notas.notasnur.Objs;

public class Periodo {

    private int LPERIODO_ID;
    private String SPERIODO_DSC;

    public Periodo() {
    }

    public Periodo(int LPERIODO_ID, String SPERIODO_DSC) {
        this.LPERIODO_ID = LPERIODO_ID;
        this.SPERIODO_DSC = SPERIODO_DSC;
    }

    public int getLPERIODO_ID() {
        return LPERIODO_ID;
    }

    public void setLPERIODO_ID(int LPERIODO_ID) {
        this.LPERIODO_ID = LPERIODO_ID;
    }

    public String getSPERIODO_DSC() {
        return SPERIODO_DSC;
    }

    public void setSPERIODO_DSC(String SPERIODO_DSC) {
        this.SPERIODO_DSC = SPERIODO_DSC;
    }

}
