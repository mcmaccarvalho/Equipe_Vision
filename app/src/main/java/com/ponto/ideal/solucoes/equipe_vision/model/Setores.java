package com.ponto.ideal.solucoes.equipe_vision.model;

public class Setores {

    private int id;
    private String keysetor;
    private String cepinicial;
    private String cepfinal;
    private String nomesetor;
    private int status;

    public Setores(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNomesetor() {
        return nomesetor;
    }

    public void setNomesetor(String nomesetor) {
        this.nomesetor = nomesetor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeysetor() {
        return keysetor;
    }

    public void setKeysetor(String keysetor) {
        this.keysetor = keysetor;
    }

    public String getCepinicial() {
        return cepinicial;
    }

    public void setCepinicial(String cepinicial) {
        this.cepinicial = cepinicial;
    }

    public String getCepfinal() {
        return cepfinal;
    }

    public void setCepfinal(String cepfinal) {
        this.cepfinal = cepfinal;
    }
}
