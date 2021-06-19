package com.ponto.ideal.solucoes.equipe_vision.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datasource.DataSource;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

public class Usuarios_Controller extends DataSource {

    ContentValues dados;

    public Usuarios_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvarusuario(Usuarios obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(Usuarios_DataModel.getKeyusu(),obj.getKeyusu());
        dados.put(Usuarios_DataModel.getNomeusu(),obj.getNomeusu());
        dados.put(Usuarios_DataModel.getApelidousu(),obj.getApelidousu());
        dados.put(Usuarios_DataModel.getEmailusu(),obj.getEmailusu());
        dados.put(Usuarios_DataModel.getImagemusuario(),obj.getImagemusuario());
        dados.put(Usuarios_DataModel.getTimestamp(),obj.getTimestamp());
        dados.put(Usuarios_DataModel.getStatus(),obj.getStatus());
        dados.put(Usuarios_DataModel.getToken(),obj.getToken());
        dados.put(Usuarios_DataModel.getOnline(),obj.getOnline());

        sucesso = insert(Usuarios_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public boolean deletar(Usuarios obj){

        boolean sucesso = true;

        sucesso = deletar(Usuarios_DataModel.getTABELA(), obj.getId());

        return sucesso;
    }


// public List<Usuarios> listaruaurios(){
//        return getAllUsuarios();
//    }

    public Usuarios usuatual(String keyusu){
        return usuariotual(Usuarios_DataModel.getTABELA(), keyusu);
    }



    public boolean alterarApelidoUsuario(String apelido, String uid) {
        boolean sucesso = true;
        dados = new ContentValues();
        dados.put(Usuarios_DataModel.getApelidousu(),apelido);
        sucesso = updateapelido(Usuarios_DataModel.getTABELA(), uid, dados);
        return sucesso;
    }


}


