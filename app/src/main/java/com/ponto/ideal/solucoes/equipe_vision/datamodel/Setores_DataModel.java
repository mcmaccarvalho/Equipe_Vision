package com.ponto.ideal.solucoes.equipe_vision.datamodel;

public class Setores_DataModel {


    private final static String TABELA = "setores";

    private final static String id = "id";
    private final static String keysetor = "keysetor";
    private final static String cepinicial= "cepinicial";
    private final static String cepfinal = "cepfinal";
    private final static String nomesetor = "nomesetor";

    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeysetor() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCepinicial() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCepfinal() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNomesetor() + " TEXT ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }




    public static void setQueryCriarTabela(String queryCriarTabela) {
        Setores_DataModel.queryCriarTabela = queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeysetor() {
        return keysetor;
    }

    public static String getCepinicial() {
        return cepinicial;
    }

    public static String getCepfinal() {
        return cepfinal;
    }

    public static String getNomesetor() {
        return nomesetor;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }
}

