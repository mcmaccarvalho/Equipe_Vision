package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

public class Alerta_Gera_Individual extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private Button btcancel,btcad;
    private HomeViewModel homeViewModel;
    private TextView txtinstall,txtmes;

    private String install;
    private String mes;


    public Alerta_Gera_Individual(String install, String mes) {
        this.install=install;
        this.mes=mes;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_gera_individual, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel=Home.homeViewModel;
        btcad    =v.findViewById(R.id.btcad );
        btcancel    =v.findViewById(R.id.btcancel );
        txtinstall    =v.findViewById(R.id.txtinstall );
        txtmes    =v.findViewById(R.id.txtmes );

        this.setCancelable(false);

        txtinstall.setText(install);
        txtmes.setText(mes);

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setNumerobol(45);
                getDialog().dismiss();
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setNumerobol(55);
                getDialog().dismiss();
            }
        });





    }


}
