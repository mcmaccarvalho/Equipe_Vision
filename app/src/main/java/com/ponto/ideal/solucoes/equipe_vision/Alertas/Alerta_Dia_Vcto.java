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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Vcto_Dia;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Dias_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Alerta_Dia_Vcto extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Resultado Jogo";

    private TextView txtdia1,txtdia2,txtdia3,txtdia4,txtdia5,txtdia6,txtdia7,txtdia8,txtdia9,txtdia10,
            txtdia11,txtdia12,txtdia13,txtdia14,txtdia15,txtdia16,txtdia17,txtdia18,txtdia19,txtdia20,
            txtdia21,txtdia22,txtdia23,txtdia24,txtdia25,txtdia26,txtdia27,txtdia28,txtdia29,txtdia30;

    public Alerta_Dia_Vcto() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_dia, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        txtdia1    =v.findViewById(R.id.txtdia1 );
        txtdia2    =v.findViewById(R.id.txtdia2 );
        txtdia3    =v.findViewById(R.id.txtdia3 );
        txtdia4    =v.findViewById(R.id.txtdia4 );
        txtdia5    =v.findViewById(R.id.txtdia5 );
        txtdia6    =v.findViewById(R.id.txtdia6 );
        txtdia7    =v.findViewById(R.id.txtdia7 );
        txtdia8    =v.findViewById(R.id.txtdia8 );
        txtdia9    =v.findViewById(R.id.txtdia9 );
        txtdia10   =v.findViewById(R.id.txtdia10);
        txtdia11   =v.findViewById(R.id.txtdia11);
        txtdia12   =v.findViewById(R.id.txtdia12);
        txtdia13   =v.findViewById(R.id.txtdia13);
        txtdia14   =v.findViewById(R.id.txtdia14);
        txtdia15   =v.findViewById(R.id.txtdia15);
        txtdia16   =v.findViewById(R.id.txtdia16);
        txtdia17   =v.findViewById(R.id.txtdia17);
        txtdia18   =v.findViewById(R.id.txtdia18);
        txtdia19   =v.findViewById(R.id.txtdia19);
        txtdia20   =v.findViewById(R.id.txtdia20);
        txtdia21   =v.findViewById(R.id.txtdia21);
        txtdia22   =v.findViewById(R.id.txtdia22);
        txtdia23   =v.findViewById(R.id.txtdia23);
        txtdia24   =v.findViewById(R.id.txtdia24);
        txtdia25   =v.findViewById(R.id.txtdia25);
        txtdia26   =v.findViewById(R.id.txtdia26);
        txtdia27   =v.findViewById(R.id.txtdia27);
        txtdia28   =v.findViewById(R.id.txtdia28);
        txtdia29   =v.findViewById(R.id.txtdia29);
        txtdia30   =v.findViewById(R.id.txtdia30);

        txtdia1   .setOnClickListener(this);
        txtdia2   .setOnClickListener(this);
        txtdia3   .setOnClickListener(this);
        txtdia4   .setOnClickListener(this);
        txtdia5   .setOnClickListener(this);
        txtdia6   .setOnClickListener(this);
        txtdia7   .setOnClickListener(this);
        txtdia8   .setOnClickListener(this);
        txtdia9   .setOnClickListener(this);
        txtdia10  .setOnClickListener(this);
        txtdia11  .setOnClickListener(this);
        txtdia12  .setOnClickListener(this);
        txtdia13  .setOnClickListener(this);
        txtdia14  .setOnClickListener(this);
        txtdia15  .setOnClickListener(this);
        txtdia16  .setOnClickListener(this);
        txtdia17  .setOnClickListener(this);
        txtdia18  .setOnClickListener(this);
        txtdia19  .setOnClickListener(this);
        txtdia20  .setOnClickListener(this);
        txtdia21  .setOnClickListener(this);
        txtdia22  .setOnClickListener(this);
        txtdia23  .setOnClickListener(this);
        txtdia24  .setOnClickListener(this);
        txtdia25  .setOnClickListener(this);
        txtdia26  .setOnClickListener(this);
        txtdia27  .setOnClickListener(this);
        txtdia28  .setOnClickListener(this);
        txtdia29  .setOnClickListener(this);
        txtdia30  .setOnClickListener(this);

     //   this.setCancelable(false);

//        btcancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeViewModel.setNumerobol(5);
//                getDialog().dismiss();
//            }
//        });

    }


    @Override
    public void onClick(View v) {


        util.vibratePhone(getContext(),(short) 20);
        int idv = v.getId();


        String tt="";

        switch (idv){

            case  (R.id.txtdia1 ): tt=txtdia1 .getText().toString();break;
            case  (R.id.txtdia2 ): tt=txtdia2 .getText().toString();break;
            case  (R.id.txtdia3 ): tt=txtdia3 .getText().toString();break;
            case  (R.id.txtdia4 ): tt=txtdia4 .getText().toString();break;
            case  (R.id.txtdia5 ): tt=txtdia5 .getText().toString();break;
            case  (R.id.txtdia6 ): tt=txtdia6 .getText().toString();break;
            case  (R.id.txtdia7 ): tt=txtdia7 .getText().toString();break;
            case  (R.id.txtdia8 ): tt=txtdia8 .getText().toString();break;
            case  (R.id.txtdia9 ): tt=txtdia9 .getText().toString();break;
            case  (R.id.txtdia10): tt=txtdia10.getText().toString();break;
            case  (R.id.txtdia11): tt=txtdia11.getText().toString();break;
            case  (R.id.txtdia12): tt=txtdia12.getText().toString();break;
            case  (R.id.txtdia13): tt=txtdia13.getText().toString();break;
            case  (R.id.txtdia14): tt=txtdia14.getText().toString();break;
            case  (R.id.txtdia15): tt=txtdia15.getText().toString();break;
            case  (R.id.txtdia16): tt=txtdia16.getText().toString();break;
            case  (R.id.txtdia17): tt=txtdia17.getText().toString();break;
            case  (R.id.txtdia18): tt=txtdia18.getText().toString();break;
            case  (R.id.txtdia19): tt=txtdia19.getText().toString();break;
            case  (R.id.txtdia20): tt=txtdia20.getText().toString();break;
            case  (R.id.txtdia21): tt=txtdia21.getText().toString();break;
            case  (R.id.txtdia22): tt=txtdia22.getText().toString();break;
            case  (R.id.txtdia23): tt=txtdia23.getText().toString();break;
            case  (R.id.txtdia24): tt=txtdia24.getText().toString();break;
            case  (R.id.txtdia25): tt=txtdia25.getText().toString();break;
            case  (R.id.txtdia26): tt=txtdia26.getText().toString();break;
            case  (R.id.txtdia27): tt=txtdia27.getText().toString();break;
            case  (R.id.txtdia28): tt=txtdia28.getText().toString();break;
            case  (R.id.txtdia29): tt=txtdia29.getText().toString();break;
            case  (R.id.txtdia30): tt=txtdia30.getText().toString();break;
        }

        Log.i("ttview",": " + tt);
        HomeViewModel.setDiavcto(tt);
        Home.homeViewModel.setVctobol(true);
        getDialog().dismiss();

    }
}
