package com.ponto.ideal.solucoes.equipe_vision.model;

public class Cobrancas {

    private int id_cobranca;
    private String key_cobranca;
    private String nome_cobranca;
    private String conta_cobranca;
    private int statu_cobranca;
    private String typecob_cobranca;
    private int type_cob;


    public Cobrancas(){

    }

    public int getType_cob() {
        return type_cob;
    }

    public void setType_cob(int type_cob) {
        this.type_cob = type_cob;
    }

    public int getId_cobranca() {
        return id_cobranca;
    }

    public void setId_cobranca(int id_cobranca) {
        this.id_cobranca = id_cobranca;
    }

    public String getKey_cobranca() {
        return key_cobranca;
    }

    public void setKey_cobranca(String key_cobranca) {
        this.key_cobranca = key_cobranca;
    }

    public String getNome_cobranca() {
        return nome_cobranca;
    }

    public void setNome_cobranca(String nome_cobranca) {
        this.nome_cobranca = nome_cobranca;
    }

    public String getConta_cobranca() {
        return conta_cobranca;
    }

    public void setConta_cobranca(String conta_cobranca) {
        this.conta_cobranca = conta_cobranca;
    }

    public int getStatu_cobranca() {
        return statu_cobranca;
    }

    public void setStatu_cobranca(int statu_cobranca) {
        this.statu_cobranca = statu_cobranca;
    }

    public String getTypecob_cobranca() {
        return typecob_cobranca;
    }

    public void setTypecob_cobranca(String typecob_cobranca) {
        this.typecob_cobranca = typecob_cobranca;
    }
}
