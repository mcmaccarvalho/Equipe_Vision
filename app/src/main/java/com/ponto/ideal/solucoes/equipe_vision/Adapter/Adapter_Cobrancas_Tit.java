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
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;

import java.util.ArrayList;

public class Adapter_Cobrancas_Tit extends ArrayAdapter<Cobrancas> {

    public Adapter_Cobrancas_Tit(@NonNull Context context, ArrayList<Cobrancas> spcob) {
        super(context, 0,spcob);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_item_peq, parent, false);
        }
        TextView txtitem = convertView.findViewById(R.id.txtitem);
        Cobrancas currentItem = getItem(position);
        if(currentItem!=null) {
            txtitem.setText(currentItem.getNome_cobranca());
        }
        return convertView;
    }
}
