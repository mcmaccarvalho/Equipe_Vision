package com.ponto.ideal.solucoes.equipe_vision.model;


    /*
    status titulo
    1 = aberto
    2 = baixado
    3 = cancelado

    Situação titulo
    0 = normal
    1 = inadimplente

    Bancotit
    0 = gerar banco
    1 = gerado banco
    */

public class Titulos {
    private int id;
    private String keytit;
    private String nomeclientetit;
    private String keyclientetit;
    private String installtit;
    private int tipocobtit;
    private String keycobtit;
    private String nomecobtit;
    private String contacobtit;
    private String barrastit;
    private String numerotit;
    private String desctit;
    private String produtostit;
    private String keyusubaixa;
    private String keyusucancel;
    private String valortit;
    private long vencimentotit;
    private long datapgtotit;
    private long comptit;
    private int statustit;
    private String keylinktit;
    private String keysetortit;
    private String keyplanotit;
    private String keytvtit;
    private String keygeraltit;
    private String valorplano;
    private String valortv;
    private String valorgeral;
    private int situacaotit;
    private int geradobanco;

    /*
    status titulo
    1 = aberto
    2 = baixado
    3 = cancelado

    Situação titulo
    0 = normal
    1 = inadimplente

    Bancotit
    0 = gerar banco
    1 = gerado banco
    */

    public Titulos(){

    }

    public String getKeyplanotit() {
        return keyplanotit;
    }

    public void setKeyplanotit(String keyplanotit) {
        this.keyplanotit = keyplanotit;
    }

    public String getKeytvtit() {
        return keytvtit;
    }

    public void setKeytvtit(String keytvtit) {
        this.keytvtit = keytvtit;
    }

    public String getKeygeraltit() {
        return keygeraltit;
    }

    public void setKeygeraltit(String keygeraltit) {
        this.keygeraltit = keygeraltit;
    }

    public String getValorplano() {
        return valorplano;
    }

    public void setValorplano(String valorplano) {
        this.valorplano = valorplano;
    }

    public String getValortv() {
        return valortv;
    }

    public void setValortv(String valortv) {
        this.valortv = valortv;
    }

    public String getValorgeral() {
        return valorgeral;
    }

    public void setValorgeral(String valorgeral) {
        this.valorgeral = valorgeral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeytit() {
        return keytit;
    }

    public void setKeytit(String keytit) {
        this.keytit = keytit;
    }

    public String getNomeclientetit() {
        return nomeclientetit;
    }

    public void setNomeclientetit(String nomeclientetit) {
        this.nomeclientetit = nomeclientetit;
    }

    public String getKeyclientetit() {
        return keyclientetit;
    }

    public void setKeyclientetit(String keyclientetit) {
        this.keyclientetit = keyclientetit;
    }

    public String getInstalltit() {
        return installtit;
    }

    public void setInstalltit(String installtit) {
        this.installtit = installtit;
    }

    public int getTipocobtit() {
        return tipocobtit;
    }

    public void setTipocobtit(int tipocobtit) {
        this.tipocobtit = tipocobtit;
    }

    public String getKeycobtit() {
        return keycobtit;
    }

    public void setKeycobtit(String keycobtit) {
        this.keycobtit = keycobtit;
    }

    public String getNomecobtit() {
        return nomecobtit;
    }

    public void setNomecobtit(String nomecobtit) {
        this.nomecobtit = nomecobtit;
    }

    public String getContacobtit() {
        return contacobtit;
    }

    public void setContacobtit(String contacobtit) {
        this.contacobtit = contacobtit;
    }

    public String getBarrastit() {
        return barrastit;
    }

    public void setBarrastit(String barrastit) {
        this.barrastit = barrastit;
    }

    public String getNumerotit() {
        return numerotit;
    }

    public void setNumerotit(String numerotit) {
        this.numerotit = numerotit;
    }

    public String getDesctit() {
        return desctit;
    }

    public void setDesctit(String desctit) {
        this.desctit = desctit;
    }

    public String getProdutostit() {
        return produtostit;
    }

    public void setProdutostit(String produtostit) {
        this.produtostit = produtostit;
    }

    public String getKeyusubaixa() {
        return keyusubaixa;
    }

    public void setKeyusubaixa(String keyusubaixa) {
        this.keyusubaixa = keyusubaixa;
    }

    public String getKeyusucancel() {
        return keyusucancel;
    }

    public void setKeyusucancel(String keyusucancel) {
        this.keyusucancel = keyusucancel;
    }

    public String getValortit() {
        return valortit;
    }

    public void setValortit(String valortit) {
        this.valortit = valortit;
    }

    public long getVencimentotit() {
        return vencimentotit;
    }

    public void setVencimentotit(long vencimentotit) {
        this.vencimentotit = vencimentotit;
    }

    public long getDatapgtotit() {
        return datapgtotit;
    }

    public void setDatapgtotit(long datapgtotit) {
        this.datapgtotit = datapgtotit;
    }

    public long getComptit() {
        return comptit;
    }

    public void setComptit(long comptit) {
        this.comptit = comptit;
    }

    public int getStatustit() {
        return statustit;
    }

    public void setStatustit(int statustit) {
        this.statustit = statustit;
    }

    public String getKeylinktit() {
        return keylinktit;
    }

    public void setKeylinktit(String keylinktit) {
        this.keylinktit = keylinktit;
    }

    public String getKeysetortit() {
        return keysetortit;
    }

    public void setKeysetortit(String keysetortit) {
        this.keysetortit = keysetortit;
    }

    public int getSituacaotit() {
        return situacaotit;
    }

    public void setSituacaotit(int situacaotit) {
        this.situacaotit = situacaotit;
    }

    public int getGeradobanco() {
        return geradobanco;
    }

    public void setGeradobanco(int geradobanco) {
        this.geradobanco = geradobanco;
    }
}
