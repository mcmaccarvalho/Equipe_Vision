package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Consulta_Cli;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Frag_Mostra_Install extends Fragment {


    public static final String ARG_OBJECT = "object";

    private TextView txtendI,txtnumI,txtcomplI,txtcepI,txtinst,txtsetor,
            txtlink,txtppoe,txtplano,txttv,txtcob,txtvcto,txtstat,txtdata,txtedtinst,txtmensal,txtminstal;

    private ImageView imgedtinst;
    private LinearLayout llstat;

    private BigDecimal zero;
    private BigDecimal bigtv;
    private BigDecimal bigplano;
    private BigDecimal bigtotal;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");


    Instalacoes instalacoes = new Instalacoes();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mostra_install, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        instalacoes= Frag_Consulta_Cli.install.get(args.getInt(ARG_OBJECT));

        View v = view;
        txtstat = v.findViewById(R.id.txtstat);
        llstat = v.findViewById(R.id.llstat);
        txtendI = v.findViewById(R.id.txtendI);
        txtnumI = v.findViewById(R.id.txtnumI);
        txtcomplI = v.findViewById(R.id.txtcomplI);
        txtcepI = v.findViewById(R.id.txtcepI);
        llstat = v.findViewById(R.id.llstat);
        txtlink = v.findViewById(R.id.txtlink);
        txtppoe = v.findViewById(R.id.txtppoe);
        txtplano = v.findViewById(R.id.txtplano);
        txtmensal = v.findViewById(R.id.txtmensal);
        txttv = v.findViewById(R.id.txttv);
        txtcob = v.findViewById(R.id.txtcob);
        txtvcto = v.findViewById(R.id.txtvcto);
        imgedtinst = v.findViewById(R.id.imgedtinst);
        txtminstal = v.findViewById(R.id.txtminstal);
        txtdata = v.findViewById(R.id.txtdata);

        txtsetor = v.findViewById(R.id.txtsetor);

        txtedtinst = v.findViewById(R.id.txtedtinst);
        txtinst = v.findViewById(R.id.txtinst);

        //util.vibratePhone(getContext(), (short) 20);
        Instant it = Instant.ofEpochMilli(instalacoes.getTimestamp());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        String scomp = dataenvio.format(fmt);
        txtdata.setText(scomp);

        String nset= "";
        for(int i=0;i<Home.baseSetores.size();i++){
            if(instalacoes.getSetor().equals(Home.baseSetores.get(i).getKeysetor())){
                nset=Home.baseSetores.get(i).getNomesetor();
                break;
            }
        }

        txtedtinst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeViewModel.setInstall(instalacoes);
                MainActivity.navController.navigate(R.id.action_frag_Consulta_Cli_to_frag_Editar_Install);
            }
        });

        imgedtinst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtedtinst.callOnClick();
            }
        });

        txtminstal.setText(String.valueOf(instalacoes.getMegainstal()));
        txtinst.setText(instalacoes.getNuminstal());
        txtsetor.setText(nset);
        txtendI.setText(instalacoes.getEndereco());
        txtnumI.setText(instalacoes.getNumero());
        txtcomplI.setText(instalacoes.getComplemento());
        txtcepI.setText(instalacoes.getCep());
        txtppoe.setText(instalacoes.getPpoe());
        for(int i=0;i<Home.baseLinks.size();i++){
            if(instalacoes.getLink().equals(Home.baseLinks.get(i).getKeylink())){
                txtlink.setText(Home.baseLinks.get(i).getNomelink());
            }
        }
        for(int i=0;i<Home.baseProdutos.size();i++){
            if(instalacoes.getPlano().equals(Home.baseProdutos.get(i).getKeyproduto())){
                txtplano.setText(Home.baseProdutos.get(i).getNomeproduto());
                bigplano=new BigDecimal(util.S_to_Big(Home.baseProdutos.get(i).getValorproduto()));
            }
        }
        for(int i=0;i<Home.baseProdutos.size();i++){
            if(instalacoes.getTv().equals(Home.baseProdutos.get(i).getKeyproduto())){
                txttv.setText(Home.baseProdutos.get(i).getNomeproduto());
                bigtv=new BigDecimal(util.S_to_Big(Home.baseProdutos.get(i).getValorproduto()));
            }
        }

        bigtotal=bigplano.add(bigtv);

        txtmensal.setText(util.Big_to_S(bigtotal));


        for(int i = 0; i< Home.baseCobrancas.size(); i++){
            if(instalacoes.getCobranca().equals(Home.baseCobrancas.get(i).getKey_cobranca())){
                txtcob.setText(Home.baseCobrancas.get(i).getNome_cobranca());
            }
        }

        txtvcto.setText(instalacoes.getVcto());

        switch (instalacoes.getStatus()) {
            case 1:
                llstat.setBackgroundColor(Color.GREEN);
                txtstat.setText("Ativa");
                break;
            case 2:
                llstat.setBackgroundColor(Color.GRAY);
                txtstat.setText("Inativa");
                break;
            case 3:
                llstat.setBackgroundColor(Color.RED);
                txtstat.setText("Inadimplente");
                break;
            default:
                llstat.setBackgroundColor(Color.GRAY);
                txtstat.setText("Inativa");
        }


    }



    public Frag_Mostra_Install() {
        // Required empty public constructor
    }


}