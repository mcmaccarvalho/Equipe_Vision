package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Setor extends ArrayAdapter<Setores> {

    public Adapter_Setor(@NonNull Context context, ArrayList<Setores> spsetores) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_setores, parent, false);
        }


        TextView txtnome = convertView.findViewById(R.id.txtnome);
        TextView cepini = convertView.findViewById(R.id.cepini);
        TextView cepfim = convertView.findViewById(R.id.cepfim);
        TextView txtcepini = convertView.findViewById(R.id.txtcepini);
        TextView txtcepfim = convertView.findViewById(R.id.txtcepfim);


        Setores currentItem = getItem(position);

        if(currentItem!=null) {
            txtnome.setText(currentItem.getNomesetor());
            cepini.setText(currentItem.getCepinicial());
            cepfim.setText(currentItem.getCepfinal());
            if(currentItem.getNomesetor().equals("Selecione")){
                txtcepfim.setVisibility(View.INVISIBLE);
                txtcepini.setVisibility(View.INVISIBLE);
                cepfim.setVisibility(View.INVISIBLE);
                cepfim.setVisibility(View.INVISIBLE);
            }else{
                txtcepfim.setVisibility(View.VISIBLE);
                txtcepini.setVisibility(View.VISIBLE);
                cepfim.setVisibility(View.VISIBLE);
                cepfim.setVisibility(View.VISIBLE);
            }
        }

        return convertView;

    }
}
