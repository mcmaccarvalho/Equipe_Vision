package com.ponto.ideal.solucoes.equipe_vision.model;

public class Adesao {

    private int id;
    private String key_adesao;
    private String nome_adesao;
    private String tipo_cob_adesao;
    private String valor_adesao;
    private String descricao_adesao;
    private int status_adesao;
    private int type_cob;

    public Adesao(){

    }

    public int getType_cob() {
        return type_cob;
    }

    public void setType_cob(int type_cob) {
        this.type_cob = type_cob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey_adesao() {
        return key_adesao;
    }

    public void setKey_adesao(String key_adesao) {
        this.key_adesao = key_adesao;
    }

    public String getNome_adesao() {
        return nome_adesao;
    }

    public void setNome_adesao(String nome_adesao) {
        this.nome_adesao = nome_adesao;
    }

    public String getTipo_cob_adesao() {
        return tipo_cob_adesao;
    }

    public void setTipo_cob_adesao(String tipo_cob_adesao) {
        this.tipo_cob_adesao = tipo_cob_adesao;
    }

    public String getValor_adesao() {
        return valor_adesao;
    }

    public void setValor_adesao(String valor_adesao) {
        this.valor_adesao = valor_adesao;
    }

    public String getDescricao_adesao() {
        return descricao_adesao;
    }

    public void setDescricao_adesao(String descricao_adesao) {
        this.descricao_adesao = descricao_adesao;
    }

    public int getStatus_adesao() {
        return status_adesao;
    }

    public void setStatus_adesao(int status_adesao) {
        this.status_adesao = status_adesao;
    }
}
