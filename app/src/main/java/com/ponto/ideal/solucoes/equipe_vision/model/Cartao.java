package com.ponto.ideal.solucoes.equipe_vision.model;

public class Cartao {


    private int id;
    private String keycartao;
    private String nomecartao;
    private String contacartao;
    private int statuscartao;

    public Cartao(){

    }

    /*
     * status
     * 0 = inativo
     * 1 = ativo
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeycartao() {
        return keycartao;
    }

    public void setKeycartao(String keycartao) {
        this.keycartao = keycartao;
    }

    public String getNomecartao() {
        return nomecartao;
    }

    public void setNomecartao(String nomecartao) {
        this.nomecartao = nomecartao;
    }

    public String getContacartao() {
        return contacartao;
    }

    public void setContacartao(String contacartao) {
        this.contacartao = contacartao;
    }

    public int getStatuscartao() {
        return statuscartao;
    }

    public void setStatuscartao(int statuscartao) {
        this.statuscartao = statuscartao;
    }
}
