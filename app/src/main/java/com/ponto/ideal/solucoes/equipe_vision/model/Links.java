package com.ponto.ideal.solucoes.equipe_vision.model;

public class Links {

    private int idlink;
    private String keylink;
    private String nomelink;
    private String capacidadelink;
    private String valorlink;
    private long timestamp;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Links(){

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdlink() {
        return idlink;
    }

    public void setIdlink(int idlink) {
        this.idlink = idlink;
    }

    public String getKeylink() {
        return keylink;
    }

    public void setKeylink(String keylink) {
        this.keylink = keylink;
    }

    public String getNomelink() {
        return nomelink;
    }

    public void setNomelink(String nomelink) {
        this.nomelink = nomelink;
    }

    public String getCapacidadelink() {
        return capacidadelink;
    }

    public void setCapacidadelink(String capacidadelink) {
        this.capacidadelink = capacidadelink;
    }

    public String getValorlink() {
        return valorlink;
    }

    public void setValorlink(String valorlink) {
        this.valorlink = valorlink;
    }
}
