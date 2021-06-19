package com.ponto.ideal.solucoes.equipe_vision.model;

public class Work_Desp {
    private float despvrr;
    private String desptitle;


    public Work_Desp(String desptitle, float despvrr){
        this.desptitle = desptitle;
        this.despvrr = despvrr;
    }

    public float getDespvrr() {
        return despvrr;
    }

    public void setDespvrr(float despvrr) {
        this.despvrr = despvrr;
    }

    public String getDesptitle() {
        return desptitle;
    }

    public void setDesptitle(String desptitle) {
        this.desptitle = desptitle;
    }
}
