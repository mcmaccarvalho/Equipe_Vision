package com.ponto.ideal.solucoes.equipe_vision.datamodel;

public class Clientes_DataModel {


    private final static String TABELA = "clientes";

    private final static String id = "id";
    private final static String keycli = "keycli";
    private final static String nome = "nome";
    private final static String apelido = "apelido";
    private final static String cpf = "cpf";
    private final static String endereco = "endereco";
    private final static String timestamp = "timestamp";
    private final static String status = "status";
    private final static String emailcli = "emailcli";
    private final static String telefone = "telefone";
    private final static String numero = "numero";
    private final static String complemento = "complemento";
    private final static String cep = "cep";



    private static String queryCriarTabela = "";

    public static String criarTabela() {

        setQueryCriarTabela("CREATE TABLE IF NOT EXISTS " + getTABELA());
        setQueryCriarTabela(getQueryCriarTabela() + "(");
        setQueryCriarTabela(getQueryCriarTabela() + getId() + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getKeycli() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNome() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getApelido() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCpf() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getEndereco() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTimestamp() + " REAL, ");
        setQueryCriarTabela(getQueryCriarTabela() + getStatus() + " INTEGER, ");
        setQueryCriarTabela(getQueryCriarTabela() + getEmailcli() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getComplemento() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getNumero() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getTelefone() + " TEXT, ");
        setQueryCriarTabela(getQueryCriarTabela() + getCep() + " TEXT ");
        setQueryCriarTabela(getQueryCriarTabela() + ")");

        return getQueryCriarTabela();
    }




    public static void setQueryCriarTabela(String queryCriarTabela) {
        Clientes_DataModel.queryCriarTabela = queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getKeycli() {
        return keycli;
    }

    public static String getNome() {
        return nome;
    }

    public static String getComplemento() {
        return complemento;
    }

    public static String getNumero() {
        return numero;
    }

    public static String getApelido() {
        return apelido;
    }

    public static String getCpf() {
        return cpf;
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

    public static String getEmailcli() {
        return emailcli;
    }

    public static String getTelefone() {
        return telefone;
    }

    public static String getCep() {
        return cep;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }
}

