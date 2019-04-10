//package com.example.ricardopazdemiquel.plataformanur.Objs;
package com.nur.notas.notasnur.objetos;

public class Link {
    private String TITULO;
    private String LINK;

    public Link() {
    }

    public Link(String TITULO, String LINK) {
        this.TITULO = TITULO;
        this.LINK = LINK;
    }

    public String getTITULO() {
        return TITULO;
    }

    public void setTITULO(String TITULO) {
        this.TITULO = TITULO;
    }

    public String getLINK() {
        return LINK;
    }

    public void setLINK(String LINK) {
        this.LINK = LINK;
    }
}
