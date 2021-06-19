package com.ponto.ideal.solucoes.equipe_vision.model;

public class Instalacoes {

    private int id;
    private String keyinstalacao;
    private String cliente;
    private long timestamp;
    private int status;
    private String endereco;
    private String cep;
    private String numero;
    private String complemento;
    private String setor;
    private String numinstal;
    private String link;
    private String plano;
    private String tv;
    private String cobranca;
    private String vcto;
    private String ppoe;
    private long mes_gerado_d1;
    private int num_tit_gerado;
    private String valorAdesao;
    private int statusMensal;
    private String usumaker;
    private String adesao;
    private int megainstal;

    public int getMegainstal() {
        return megainstal;
    }

    public void setMegainstal(int megainstal) {
        this.megainstal = megainstal;
    }

    /*
        ativa = 1
        inativa = 2
        inadimplente = 3
        */
    public Instalacoes(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyinstalacao() {
        return keyinstalacao;
    }

    public void setKeyinstalacao(String keyinstalacao) {
        this.keyinstalacao = keyinstalacao;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNuminstal() {
        return numinstal;
    }

    public void setNuminstal(String numinstal) {
        this.numinstal = numinstal;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getCobranca() {
        return cobranca;
    }

    public void setCobranca(String cobranca) {
        this.cobranca = cobranca;
    }

    public String getVcto() {
        return vcto;
    }

    public void setVcto(String vcto) {
        this.vcto = vcto;
    }

    public String getPpoe() {
        return ppoe;
    }

    public void setPpoe(String ppoe) {
        this.ppoe = ppoe;
    }

    public long getMes_gerado_d1() {
        return mes_gerado_d1;
    }

    public void setMes_gerado_d1(long mes_gerado_d1) {
        this.mes_gerado_d1 = mes_gerado_d1;
    }

    public int getNum_tit_gerado() {
        return num_tit_gerado;
    }

    public void setNum_tit_gerado(int num_tit_gerado) {
        this.num_tit_gerado = num_tit_gerado;
    }

    public String getValorAdesao() {
        return valorAdesao;
    }

    public void setValorAdesao(String valorAdesao) {
        this.valorAdesao = valorAdesao;
    }

    public int getStatusMensal() {
        return statusMensal;
    }

    public void setStatusMensal(int statusMensal) {
        this.statusMensal = statusMensal;
    }

    public String getUsumaker() {
        return usumaker;
    }

    public void setUsumaker(String usumaker) {
        this.usumaker = usumaker;
    }

    public String getAdesao() {
        return adesao;
    }

    public void setAdesao(String adesao) {
        this.adesao = adesao;
    }
}
