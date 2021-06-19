package com.ponto.ideal.solucoes.equipe_vision.model;

public class Tipo_Cobranca {

    private int idcobranca;
    private String keycobranca;
    private String nomecobranca;
    private String contacobranca;
    private int statuscobranca;
    private int tipocob;


    public Tipo_Cobranca(){

    }

    /*
   tipo cobrança
   1 = boleto
   2= carteira
   3 = cartão
   4 = adesão boleto
   5 = adesão carteira
   6 = adesão cartão
   7 = adesão cortesia
     */

    public String getContacobranca() {
        return contacobranca;
    }

    public void setContacobranca(String contacobranca) {
        this.contacobranca = contacobranca;
    }

    public int getTipocob() {
        return tipocob;
    }

    public void setTipocob(int tipocob) {
        this.tipocob = tipocob;
    }

    public int getStatuscobranca() {
        return statuscobranca;
    }

    public void setStatuscobranca(int statuscobranca) {
        this.statuscobranca = statuscobranca;
    }

    public int getIdcobranca() {
        return idcobranca;
    }

    public void setIdcobranca(int idcobranca) {
        this.idcobranca = idcobranca;
    }

    public String getKeycobranca() {
        return keycobranca;
    }

    public void setKeycobranca(String keycobranca) {
        this.keycobranca = keycobranca;
    }

    public String getNomecobranca() {
        return nomecobranca;
    }

    public void setNomecobranca(String nomecobranca) {
        this.nomecobranca = nomecobranca;
    }
}
