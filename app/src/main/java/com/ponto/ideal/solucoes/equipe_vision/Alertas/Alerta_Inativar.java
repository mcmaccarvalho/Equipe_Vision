package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.util.ArrayList;

public class Alerta_Inativar extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txttipo, txtnome;
    private Button btcad,btcancel;
    private Spinner sptipo;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clinativar;
    String tipo, nome;
    ArrayList<String> lista;
    int pos =0;

    public Alerta_Inativar(String tipo, String nome, ArrayList<String> lista) {
        this.tipo = tipo;
        this.nome = nome;
        this.lista = lista;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_inativar, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel = Home.homeViewModel;
        txttipo=v.findViewById(R.id.txttipo);
        txtnome=v.findViewById(R.id.txtnome);
        sptipo=v.findViewById(R.id.sptipo);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        clinativar=v.findViewById(R.id.clinativar);


        this.setCancelable(false);

        txttipo.setText(tipo);
        txtnome.setText(nome);

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos==0){
                    util.showSnackOk(clinativar,"Selecione " + tipo + " para direcionar.");
                    return;
                }else{
                    homeViewModel.setRetinativar(pos);
                    getDialog().dismiss();
                }

            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setRetinativar(pos);
                getDialog().dismiss();
            }
        });

        final ArrayAdapter<String> adaptertipo = new ArrayAdapter(getContext(),
                R.layout.sp_prod, lista);
        adaptertipo.setDropDownViewResource(R.layout.sp_prod);
        sptipo.setAdapter(adaptertipo);

        sptipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



    }


}
