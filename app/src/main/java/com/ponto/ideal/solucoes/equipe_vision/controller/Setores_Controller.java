package com.ponto.ideal.solucoes.equipe_vision.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.equipe_vision.datamodel.Setores_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datasource.DataSource;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class Setores_Controller extends DataSource {

    ContentValues dados;

    public Setores_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvarSetor(Setores obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(Setores_DataModel.getKeysetor(),obj.getKeysetor());
        dados.put(Setores_DataModel.getCepinicial(),obj.getCepinicial());
        dados.put(Setores_DataModel.getCepfinal(),obj.getCepfinal());
        dados.put(Setores_DataModel.getNomesetor(),obj.getNomesetor());

        sucesso = insert(Setores_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public boolean deletar(Setores obj){

        boolean sucesso = true;

        sucesso = deletar(Setores_DataModel.getTABELA(), obj.getId());

        return sucesso;
    }

    public ArrayList<Setores> listar_Setores(){
        return getAll_Setores();
    }



}


