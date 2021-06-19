package com.ponto.ideal.solucoes.equipe_vision.model;

public class Despesas {

    private int id;
    private String keydespesa;
    private String nomedespesa;
    private String tipodespesa;
    private String valordespesa;
    private String descricaodespesa;
    private String linkdespesa;
    private int statusdespesa;
    private long datadesp;
    private long compdesp;

    public Despesas(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeydespesa() {
        return keydespesa;
    }

    public void setKeydespesa(String keydespesa) {
        this.keydespesa = keydespesa;
    }

    public String getNomedespesa() {
        return nomedespesa;
    }

    public void setNomedespesa(String nomedespesa) {
        this.nomedespesa = nomedespesa;
    }

    public String getTipodespesa() {
        return tipodespesa;
    }

    public void setTipodespesa(String tipodespesa) {
        this.tipodespesa = tipodespesa;
    }

    public String getValordespesa() {
        return valordespesa;
    }

    public void setValordespesa(String valordespesa) {
        this.valordespesa = valordespesa;
    }

    public String getDescricaodespesa() {
        return descricaodespesa;
    }

    public void setDescricaodespesa(String descricaodespesa) {
        this.descricaodespesa = descricaodespesa;
    }

    public String getLinkdespesa() {
        return linkdespesa;
    }

    public void setLinkdespesa(String linkdespesa) {
        this.linkdespesa = linkdespesa;
    }

    public int getStatusdespesa() {
        return statusdespesa;
    }

    public void setStatusdespesa(int statusdespesa) {
        this.statusdespesa = statusdespesa;
    }

    public long getDatadesp() {
        return datadesp;
    }

    public void setDatadesp(long datadesp) {
        this.datadesp = datadesp;
    }

    public long getCompdesp() {
        return compdesp;
    }

    public void setCompdesp(long compdesp) {
        this.compdesp = compdesp;
    }
}
