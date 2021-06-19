package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.cadastro.Itens_Cad;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.util.ArrayList;

public class Alerta_Altera_Mega_Install extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtplano;
    private EditText edtcap;
    private Button btsim,btnao;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clinativar;
    String plano;

    public Alerta_Altera_Mega_Install(String plano) {
        this.plano = plano;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_altera_mega_intall, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel = Home.homeViewModel;
        txtplano=v.findViewById(R.id.txtplano);
        edtcap=v.findViewById(R.id.edtcap);
        btsim=v.findViewById(R.id.btsim);
        btnao=v.findViewById(R.id.btnao);
        clinativar=v.findViewById(R.id.clinativar);


        this.setCancelable(false);

        txtplano.setText(plano);


        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newmmm = edtcap.getText().toString().trim();
                if (newmmm.equals("")) {
                    util.showSnackError(clinativar, "informe Capacidade de Megas instalada.");
                    return;
                }else{
                    Itens_Cad.megaalt= Integer.parseInt(edtcap.getText().toString().trim());
                    homeViewModel.setAlteracap(1);
                    getDialog().dismiss();
                }

            }
        });

        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Itens_Cad.megaalt=0;
                homeViewModel.setAlteracap(2);
                getDialog().dismiss();
            }
        });

    }


}
