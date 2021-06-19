package com.ponto.ideal.solucoes.equipe_vision.model;

public class Type_Cobranca {

    private int id_cobranca;
    private String key_type_cob;
    private String nome_type_cob;
    private int tipo_type_cob;

    public Type_Cobranca(){

    }

   /*
   type cobrança
   1 = boleto
   2 = carteira
   3 = cartão
   */

    public int getId_cobranca() {
        return id_cobranca;
    }

    public void setId_cobranca(int id_cobranca) {
        this.id_cobranca = id_cobranca;
    }

    public String getKey_type_cob() {
        return key_type_cob;
    }

    public void setKey_type_cob(String key_type_cob) {
        this.key_type_cob = key_type_cob;
    }

    public String getNome_type_cob() {
        return nome_type_cob;
    }

    public void setNome_type_cob(String nome_type_cob) {
        this.nome_type_cob = nome_type_cob;
    }

    public int getTipo_type_cob() {
        return tipo_type_cob;
    }

    public void setTipo_type_cob(int tipo_type_cob) {
        this.tipo_type_cob = tipo_type_cob;
    }
}
