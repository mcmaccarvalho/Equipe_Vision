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
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;

import java.util.ArrayList;

public class Adapter_Instal extends ArrayAdapter<Instalacoes> {

    public Adapter_Instal(@NonNull Context context, ArrayList<Instalacoes> spinstal) {
        super(context, 0,spinstal);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_item, parent, false);
        }


        TextView txtitem = convertView.findViewById(R.id.txtitem);


        Instalacoes currentItem = getItem(position);

        if(currentItem!=null) {
            txtitem.setText(currentItem.getNuminstal());
        }

        return convertView;

    }
}
