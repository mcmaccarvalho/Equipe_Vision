package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class Adapter_Usuários extends ArrayAdapter<Usuarios> {

    public Adapter_Usuários(@NonNull Context context, ArrayList<Usuarios> spusuarios) {
        super(context, 0,spusuarios);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);
    }

    public View iniView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spusu, parent, false);
        }


        TextView txtnome= convertView.findViewById(R.id.txtnome);
        TextView txtemail= convertView.findViewById(R.id.txtemail);
        TextView txttipo= convertView.findViewById(R.id.txttipo);


        Usuarios currentItem = getItem(position);

        if(currentItem!=null) {
            txtnome.setText(currentItem.getNomeusu());
            txtemail.setText(currentItem.getEmailusu());
            String Sadmim = "111111111111111";
            String Soperador = "102201200010100";
            if (currentItem.getUsupermicao().equals(Sadmim))txttipo.setText("Administrador");
            if (currentItem.getUsupermicao().equals(Soperador))txttipo.setText("Operador");
            if (!currentItem.getUsupermicao().equals(Sadmim) && !currentItem.getUsupermicao().equals(Soperador))txttipo.setText("Custom");



        }

        return convertView;

    }
}
