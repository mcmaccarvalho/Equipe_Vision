package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Alerta_Ano extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Resultado Jogo";

    private TextView txtano1,txtano2,txtano3;

    public Alerta_Ano() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_ano, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        txtano1    =v.findViewById(R.id.txtano1 );
        txtano2    =v.findViewById(R.id.txtano2 );
        txtano3    =v.findViewById(R.id.txtano3 );
        txtano1    .setOnClickListener(this);
        txtano2    .setOnClickListener(this);
        txtano3    .setOnClickListener(this);

        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate anoatual = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("yyyy")
                .withResolverStyle(ResolverStyle.STRICT);
        String YEAR = String.format("%04d", new Object[] { anoatual.getYear() });

        LocalDate anoanterior= anoatual.minusYears(1);
        LocalDate anoprox= anoatual.plusYears(1);
            txtano1.setText(anoanterior.format(fmt));
            txtano2.setText(anoatual.format(fmt));
            txtano3.setText(anoprox.format(fmt));
    }


    @Override
    public void onClick(View v) {


        util.vibratePhone(getContext(),(short) 20);
        int idv = v.getId();


        String tt="";

        switch (idv){

            case  (R.id.txtano1 ): tt=txtano1 .getText().toString();break;
            case  (R.id.txtano2 ): tt=txtano2 .getText().toString();break;
            case  (R.id.txtano3 ): tt=txtano3 .getText().toString();break;


        }

        Log.i("ttview",": " + tt);
//        HomeViewModel.setAnolistar(tt);
//        Home.homeViewModel.setListarano(true);
        getDialog().dismiss();

    }
}
