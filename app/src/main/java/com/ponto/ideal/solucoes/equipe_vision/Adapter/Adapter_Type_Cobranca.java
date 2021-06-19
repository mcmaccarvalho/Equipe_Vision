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
import com.ponto.ideal.solucoes.equipe_vision.model.Tipo_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.model.Type_Cobranca;

import java.util.ArrayList;

public class Adapter_Type_Cobranca extends ArrayAdapter<Type_Cobranca> {

    public Adapter_Type_Cobranca(@NonNull Context context, ArrayList<Type_Cobranca> spcob) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_item, parent, false);
        }
        TextView txtitem = convertView.findViewById(R.id.txtitem);
        Type_Cobranca currentItem = getItem(position);
        if(currentItem!=null) {
            txtitem.setText(currentItem.getNome_type_cob());
        }
        return convertView;
    }
}
