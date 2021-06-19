package com.ponto.ideal.solucoes.equipe_vision.model;

public class Tabela_Produtos {

    private int id;
    private String keyproduto;
    private String nomeproduto;
    private String tipoproduto;
    private String valorproduto;
    private String descricaoproduto;
    private int statusproduto;
    private int qtdeunidade;

    public int getQtdeunidade() {
        return qtdeunidade;
    }

    public void setQtdeunidade(int qtdeunidade) {
        this.qtdeunidade = qtdeunidade;
    }

    public Tabela_Produtos(){

    }

    public String getDescricaoproduto() {
        return descricaoproduto;
    }

    public void setDescricaoproduto(String descricaoproduto) {
        this.descricaoproduto = descricaoproduto;
    }

    public int getStatusproduto() {
        return statusproduto;
    }

    public void setStatusproduto(int statusproduto) {
        this.statusproduto = statusproduto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyproduto() {
        return keyproduto;
    }

    public void setKeyproduto(String keyproduto) {
        this.keyproduto = keyproduto;
    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public String getTipoproduto() {
        return tipoproduto;
    }

    public void setTipoproduto(String tipoproduto) {
        this.tipoproduto = tipoproduto;
    }

    public String getValorproduto() {
        return valorproduto;
    }

    public void setValorproduto(String valorproduto) {
        this.valorproduto = valorproduto;
    }
}
