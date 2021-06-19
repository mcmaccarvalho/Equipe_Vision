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
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;

import java.util.ArrayList;

public class Adapter_Setor_Chart extends ArrayAdapter<Setores> {

    public Adapter_Setor_Chart(@NonNull Context context, ArrayList<Setores> spsetores) {
        super(context, 0,spsetores);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_setor_chart, parent, false);
        }


        TextView txtnome = convertView.findViewById(R.id.txtnome);



        Setores currentItem = getItem(position);

        if(currentItem!=null) {
            txtnome.setText(currentItem.getNomesetor());
        }

        return convertView;

    }
}
