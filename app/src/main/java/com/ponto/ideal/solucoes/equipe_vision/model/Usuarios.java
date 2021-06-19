package com.ponto.ideal.solucoes.equipe_vision.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuarios  {

    private int id;
    private String keyusu;
    private String nomeusu;
    private String apelidousu;
    private String emailusu;
    private String imagemusuario;
    private long timestamp;
    private int status;
    private String token;
    private int online;
    private String usupermicao;

    /*
    Admin sem perfil = status = 10
    Administrador comprfil = status = 100
    Operador sem perfil = status = 20
    Operador com perfil = status = 200
     */

    public Usuarios(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyusu() {
        return keyusu;
    }

    public void setKeyusu(String keyusu) {
        this.keyusu = keyusu;
    }

    public String getNomeusu() {
        return nomeusu;
    }

    public void setNomeusu(String nomeusu) {
        this.nomeusu = nomeusu;
    }

    public String getApelidousu() {
        return apelidousu;
    }

    public void setApelidousu(String apelidousu) {
        this.apelidousu = apelidousu;
    }

    public String getEmailusu() {
        return emailusu;
    }

    public void setEmailusu(String emailusu) {
        this.emailusu = emailusu;
    }

    public String getImagemusuario() {
        return imagemusuario;
    }

    public void setImagemusuario(String imagemusuario) {
        this.imagemusuario = imagemusuario;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getUsupermicao() {
        return usupermicao;
    }

    public void setUsupermicao(String usupermicao) {
        this.usupermicao = usupermicao;
    }
}