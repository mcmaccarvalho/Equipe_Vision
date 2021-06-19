package com.ponto.ideal.solucoes.equipe_vision.datamodel;

public class Usuarios_DataModel {


    private final static String TABELA = "usuarios";

    private final static String id = "id";
    private final static String keyusu = "keyusu";
    private final static String nomeusu = "nomeusu";
    private final static String apelidousu = "apelidousu";
    private final static String emailusu = "emailusu";
    private final static String imagemusuario = "imagemusuario";
    private final static String timestamp = "timestamp";
    private final static String status = "status";
    private final static String token = "token";
    private final static String online = "online";



    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeyusu() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNomeusu() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getApelidousu() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getEmailusu() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getImagemusuario() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTimestamp() + " REAL, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatus() + " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getToken() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getOnline() + " INTEGER ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }




    public static void setQueryCriarTabela(String queryCriarTabela) {
        Usuarios_DataModel.queryCriarTabela = queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeyusu() {
        return keyusu;
    }

    public static String getNomeusu() {
        return nomeusu;
    }

    public static String getApelidousu() {
        return apelidousu;
    }

    public static String getEmailusu() {
        return emailusu;
    }

    public static String getImagemusuario() {
        return imagemusuario;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    public static String getStatus() {
        return status;
    }

    public static String getToken() {
        return token;
    }

    public static String getOnline() {
        return online;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }
}

