package com.ponto.ideal.solucoes.equipe_vision.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.equipe_vision.datamodel.Clientes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Instalacoes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datasource.DataSource;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class Instalacoes_Controller extends DataSource {

    ContentValues dados;

    public Instalacoes_Controller(@Nullable Context context) {
        super(context);
    }



    public boolean salvarinstalacao(Instalacoes obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(Instalacoes_DataModel.getKeyinstalacao(),obj.getKeyinstalacao());
        dados.put(Instalacoes_DataModel.getCliente(),obj.getCliente());
        dados.put(Instalacoes_DataModel.getEndereco(),obj.getEndereco());
        dados.put(Instalacoes_DataModel.getTimestamp(),obj.getTimestamp());
        dados.put(Instalacoes_DataModel.getStatus(),obj.getStatus());
        dados.put(Instalacoes_DataModel.getComplemento(),obj.getComplemento());
        dados.put(Instalacoes_DataModel.getNumero(),obj.getNumero());
        dados.put(Instalacoes_DataModel.getCep(),obj.getCep());
        dados.put(Instalacoes_DataModel.getNuminstal(),obj.getNuminstal());
        dados.put(Instalacoes_DataModel.getSetor(),obj.getSetor());

        sucesso = insert(Instalacoes_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public boolean deletar(Instalacoes obj){

        boolean sucesso = true;

        sucesso = deletar(Instalacoes_DataModel.getTABELA(), obj.getId());

        return sucesso;
    }

    public ArrayList<Instalacoes> listar_instal_Cliente(String idcli){
        return getinstalCli(idcli);
    }

    public ArrayList<Instalacoes> listar_Instalacoes(){
        return getAll_Instalacoes();
    }



}


