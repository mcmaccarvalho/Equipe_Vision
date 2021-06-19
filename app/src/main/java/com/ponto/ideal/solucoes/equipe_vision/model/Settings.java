package com.ponto.ideal.solucoes.equipe_vision.model;

public class Settings {

    private String max_dias_atraso;
    private String valor_min_boleto;
    private String pzo_vcto_adesao;

    public Settings(){

    }

    public String getMax_dias_atraso() {
        return max_dias_atraso;
    }

    public void setMax_dias_atraso(String max_dias_atraso) {
        this.max_dias_atraso = max_dias_atraso;
    }

    public String getValor_min_boleto() {
        return valor_min_boleto;
    }

    public void setValor_min_boleto(String valor_min_boleto) {
        this.valor_min_boleto = valor_min_boleto;
    }

    public String getPzo_vcto_adesao() {
        return pzo_vcto_adesao;
    }

    public void setPzo_vcto_adesao(String pzo_vcto_adesao) {
        this.pzo_vcto_adesao = pzo_vcto_adesao;
    }
}
