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

public class Alerta_Mes_Vcto extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Resultado Jogo";

    private TextView txtdia1,txtdia2,txtdia3,txtdia4,txtdia5,txtdia6,txtdia7,txtdia8,txtdia9,txtdia10,
            txtdia11,txtdia12;

    public Alerta_Mes_Vcto() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_mes, container, false);
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

        }

        Log.i("ttview",": " + tt);
        HomeViewModel.setMeslistar(tt);
        Home.homeViewModel.setListarmes(true);
        getDialog().dismiss();

    }
}
