package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.google.common.util.concurrent.SettableFuture;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Bancos;
import com.ponto.ideal.solucoes.equipe_vision.model.Cartao;
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

public class Alerta_Boleto extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtbanco, txtconta, txtcliente, txtinstall,txtdesc,txtprodutos,txtvalor,txtvcto,ttbanco,txtcompetencia;
    private Button btcad,btcancel;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clboleto;
    private Titulos titulo;
    String dias, plano,planoprop,planoacres, vcto,proxvcto;
    int tipocob;

    public Alerta_Boleto(Titulos titulo) {
        this.titulo = titulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_boleto, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        tipocob=titulo.getTipocobtit();

        homeViewModel = Home.homeViewModel;
        txtbanco=v.findViewById(R.id.txtbanco);
        txtconta=v.findViewById(R.id.txtconta);
        txtcliente=v.findViewById(R.id.txtcliente);
        txtinstall=v.findViewById(R.id.txtinstall);
        txtdesc=v.findViewById(R.id.txtdesc);
        txtprodutos=v.findViewById(R.id.txtprodutos);
        txtvalor=v.findViewById(R.id.txtvalor);
        txtvcto=v.findViewById(R.id.txtvcto);
        ttbanco=v.findViewById(R.id.ttbanco);
        txtcompetencia=v.findViewById(R.id.txtcompetencia);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        clboleto=v.findViewById(R.id.clboleto);

        this.setCancelable(false);

        switch (tipocob){
            case 1:
                ttbanco.setText("Banco:");
                break;
            case 2:
                ttbanco.setText("Carteira:");
                break;
            case 3:
                ttbanco.setText("Cartão:");
                break;
            case 10:
                ttbanco.setText("Cortesia:");
                break;
        }

        txtbanco.setText(titulo.getNomecobtit());
        txtconta.setText(titulo.getContacobtit());


        txtcliente.setText(titulo.getNomeclientetit());
        txtinstall.setText(titulo.getInstalltit());
        txtdesc.setText(titulo.getDesctit());

        String[] listprod;
        String stt = titulo.getProdutostit();
        listprod = stt.split("#");
        String [] nomeprod = new String[listprod.length];
        Log.i("aleretaboleto","listprod.length: "  + listprod.length);
        for(int i=0;i<listprod.length;i++){
            Log.i("aleretaboleto","listprod.length: "  + listprod[i]);
            for( int j=0;j<Home.baseProdutos.size();j++){
                if(Home.baseProdutos.get(j).getKeyproduto().equals(listprod[i])){
                    Log.i("aleretaboleto","baseProdutos "  + Home.baseProdutos.get(j).getNomeproduto() + " : " +
                            Home.baseProdutos.get(j).getKeyproduto());
                    nomeprod[i]=Home.baseProdutos.get(j).getNomeproduto();
                    break;
                }
            }
        }
        String produtos= "";
        produtos=nomeprod[0];
        for(int i=1;i<nomeprod.length;i++){
            produtos=produtos+"\n"+nomeprod[i];
        }

        if(titulo.getContacobtit().equals("Adesão"))produtos=titulo.getProdutostit();

        txtprodutos.setText(produtos);
        txtvalor.setText(titulo.getValortit());

        Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        String svcto = dataenvio.format(fmt);

        txtvcto.setText(svcto);

        it = Instant.ofEpochMilli(titulo.getComptit());
        zdt = it.atZone(ZoneId.systemDefault());
        dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt2 = DateTimeFormatter
                .ofPattern("MM/uu")
                .withResolverStyle(ResolverStyle.STRICT);

        String scomp = dataenvio.format(fmt2);

        if(titulo.getComptit()==0)scomp="Adesão";

        txtcompetencia.setText(scomp);

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    homeViewModel.setNumerobol(1);
                getDialog().dismiss();
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setNumerobol(5);
                getDialog().dismiss();
            }
        });










    }


}
