package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

public class Alerta_Gera_Boleto extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtoption1, txtoption2, txtoption3, txtprop;
    private CardView cvoption1, cvoption2, cvoption3;
    private Button btcad,btcancel;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clboleto;
    String dias, plano,planoprop,planoacres, vcto,proxvcto;
    int option=0;

    public Alerta_Gera_Boleto(String dias, String plano, String planoprop, String planoacres, String vcto, String proxvcto) {
        this.dias = dias;
        this.plano = plano;
        this.planoprop = planoprop;
        this.planoacres = planoacres;
        this.proxvcto = proxvcto;
        this.vcto = vcto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_gera_boleto, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel = Home.homeViewModel;
        txtoption1=v.findViewById(R.id.txtoption1);
        txtoption2=v.findViewById(R.id.txtoption2);
        txtoption3=v.findViewById(R.id.txtoption3);
        txtprop=v.findViewById(R.id.txtprop);
        cvoption1=v.findViewById(R.id.cvopt1);
        cvoption2=v.findViewById(R.id.cvopt2);
        cvoption3=v.findViewById(R.id.cvopt3);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        clboleto=v.findViewById(R.id.clboleto);


        this.setCancelable(false);


        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option==0){
                    util.showSnackError(clboleto,"Selecione Opção");
                }else{
                    homeViewModel.setResultbol(option);
                    getDialog().dismiss();
                }

            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        String nd = " dias.";
        if(dias.equals("1"))nd=" dia.";
        txtprop.setText("Valor proporcional:\nR$ "+ planoprop+"  -  " + dias + nd);
        String opt1="Descartar valor proporcional:\n"+
                "Gerar Boleto:\nValor R$ "+plano +
                 " Vcto "+proxvcto;
        txtoption1.setText(opt1 );

        String opt2="Cobrar valor proporcional:\n"+
                "Gerar 2 Boletos:\nValor R$ "+planoprop +
                " Vcto "+vcto+
                "\nValor R$ "+plano+
                " Vcto "+proxvcto;
        txtoption2.setText(opt2 );

        String opt3="Cobrar junto com a parcela:\n"+
                "Gerar Boleto:\nValor R$ "+planoacres +
                " Vcto "+proxvcto;
        txtoption3.setText(opt3 );

        txtoption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option=1;
                cvoption1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cvoption2.setCardBackgroundColor(Color.WHITE);
                cvoption3.setCardBackgroundColor(Color.WHITE);
            }
        });
        txtoption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option=2;
                cvoption2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cvoption1.setCardBackgroundColor(Color.WHITE);
                cvoption3.setCardBackgroundColor(Color.WHITE);
            }
        });
        txtoption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option=3;
                cvoption3.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cvoption2.setCardBackgroundColor(Color.WHITE);
                cvoption1.setCardBackgroundColor(Color.WHITE);
            }
        });











    }


}
