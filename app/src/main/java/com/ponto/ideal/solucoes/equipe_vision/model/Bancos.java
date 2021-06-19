package com.ponto.ideal.solucoes.equipe_vision.model;

public class Bancos {

    private int id;
    private String bancokey;
    private String banconome;
    private String bancoconta;
    private int bancostatus;

    public Bancos(){

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

    public String getBancokey() {
        return bancokey;
    }

    public void setBancokey(String bancokey) {
        this.bancokey = bancokey;
    }

    public String getBanconome() {
        return banconome;
    }

    public void setBanconome(String banconome) {
        this.banconome = banconome;
    }

    public String getBancoconta() {
        return bancoconta;
    }

    public void setBancoconta(String bancoconta) {
        this.bancoconta = bancoconta;
    }

    public int getBancostatus() {
        return bancostatus;
    }

    public void setBancostatus(int bancostatus) {
        this.bancostatus = bancostatus;
    }
}
