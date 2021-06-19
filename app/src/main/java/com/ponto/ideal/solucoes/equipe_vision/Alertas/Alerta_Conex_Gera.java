package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

public class Alerta_Conex_Gera extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private Button btcancel,btcad;
    private HomeViewModel homeViewModel;



    public Alerta_Conex_Gera() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_conex, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel=Home.homeViewModel;
        btcad    =v.findViewById(R.id.btcad );
        btcancel    =v.findViewById(R.id.btcancel );

        this.setCancelable(false);

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setNumerobol(25);
                getDialog().dismiss();
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setNumerobol(15);
                getDialog().dismiss();
            }
        });





    }


}
