package com.ponto.ideal.solucoes.equipe_vision.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.equipe_vision.datamodel.Clientes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datasource.DataSource;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class Clientes_Controller extends DataSource {

    ContentValues dados;

    public Clientes_Controller(@Nullable Context context) {
        super(context);
    }

    public boolean salvarcliente(Clientes obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(Clientes_DataModel.getKeycli(),obj.getKeycli());
        dados.put(Clientes_DataModel.getNome(),obj.getNome());
        dados.put(Clientes_DataModel.getApelido(),obj.getApelido());
        dados.put(Clientes_DataModel.getEmailcli(),obj.getEmailcli());
        dados.put(Clientes_DataModel.getCpf(),obj.getCpf());
        dados.put(Clientes_DataModel.getTimestamp(),obj.getTimestamp());
        dados.put(Clientes_DataModel.getStatus(),obj.getStatus());
        dados.put(Clientes_DataModel.getEndereco(),obj.getEndereco());
        dados.put(Clientes_DataModel.getTelefone(),obj.getTelefone());
        dados.put(Clientes_DataModel.getComplemento(),obj.getComplemento());
        dados.put(Clientes_DataModel.getNumero(),obj.getNumero());
        dados.put(Clientes_DataModel.getCep(),obj.getCep());

        sucesso = insert(Clientes_DataModel.getTABELA(), dados);

        return sucesso;

    }

    public boolean deletar(Clientes obj){

        boolean sucesso = true;

        sucesso = deletar(Clientes_DataModel.getTABELA(), obj.getId());

        return sucesso;
    }


    public ArrayList<Clientes> listar_Clientes(){
        return getAll_Clientes();
    }



}


