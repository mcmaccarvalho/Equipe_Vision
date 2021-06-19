package com.ponto.ideal.solucoes.equipe_vision.model;

public class Lista_Mensalidades {

    private String installmens;

    public String getInstallmens() {
        return installmens;
    }

    public void setInstallmens(String installmens) {
        this.installmens = installmens;
    }

    private long longmes;
    private boolean bstatus;


    public Lista_Mensalidades() {

    }



    public long getLongmes() {
        return longmes;
    }

    public void setLongmes(long longmes) {
        this.longmes = longmes;
    }

    public boolean isBstatus() {
        return bstatus;
    }

    public void setBstatus(boolean bstatus) {
        this.bstatus = bstatus;
    }
}
