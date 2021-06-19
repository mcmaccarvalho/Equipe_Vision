package com.ponto.ideal.solucoes.equipe_vision.datamodel;

public class Instalacoes_DataModel {


    private final static String TABELA = "instalacoes";

    private final static String id = "id";
    private final static String keyinstalacao = "keyinstalacao";
    private final static String cliente = "cliente";
    private final static String endereco = "endereco";
    private final static String timestamp = "timestamp";
    private final static String status = "status";
    private final static String setor = "setor";
    private final static String numero = "numero";
    private final static String complemento = "complemento";
    private final static String cep = "cep";
    private final static String numinstal = "numinstal";

    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeyinstalacao() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCliente() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getEndereco() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getComplemento() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumero() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCep() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getSetor() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNuminstal() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTimestamp() + " REAL, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatus() + " INTEGER ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }




    public static void setQueryCriarTabela(String queryCriarTabela) {
        Instalacoes_DataModel.queryCriarTabela = queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeyinstalacao() {
        return keyinstalacao;
    }

    public static String getCliente() {
        return cliente;
    }

    public static String getSetor() {
        return setor;
    }

    public static String getEndereco() {
        return endereco;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    public static String getStatus() {
        return status;
    }

    public static String getComplemento() {
        return complemento;
    }

    public static String getNumero() {
        return numero;
    }

    public static String getCep() {
        return cep;
    }

    public static String getNuminstal() {
        return numinstal;
    }


    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }
}

