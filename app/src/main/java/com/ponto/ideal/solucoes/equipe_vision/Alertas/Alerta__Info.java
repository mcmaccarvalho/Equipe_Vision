package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

public class Alerta__Info extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private Button btcad;

    public Alerta__Info() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_info, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {



        btcad    =v.findViewById(R.id.btcad );

        this.setCancelable(false);


        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



    }


}
