package com.ponto.ideal.solucoes.equipe_vision.model;

public class Work_Rec {
    private float recvrr;
    private String rectitle;
    private String reccor;
    private int intcor;

    public int getIntcor() {
        return intcor;
    }

    public void setIntcor(int intcor) {
        this.intcor = intcor;
    }

    public Work_Rec(String rectitle, float recvrr, String reccor,int intcor ){
        this.rectitle = rectitle;
        this.recvrr = recvrr;
        this.reccor = reccor;
        this.intcor = intcor;
    }

    public String getReccor() {
        return reccor;
    }

    public void setReccor(String reccor) {
        this.reccor = reccor;
    }

    public float getRecvrr() {
        return recvrr;
    }

    public void setRecvrr(float recvrr) {
        this.recvrr = recvrr;
    }

    public String getRectitle() {
        return rectitle;
    }

    public void setRectitle(String rectitle) {
        this.rectitle = rectitle;
    }
}
