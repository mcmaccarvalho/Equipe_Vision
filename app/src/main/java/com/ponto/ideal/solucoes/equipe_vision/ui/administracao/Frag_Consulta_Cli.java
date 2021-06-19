package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Instal;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.InstallPagerAdapter;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.controller.Instalacoes_Controller;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Instalacoes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;

import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;
import com.xwray.groupie.GroupAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;


public class Frag_Consulta_Cli extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView txtnome,txtend,txtnum,txtcompl,txtcep,txtfone,txtstat,txtcpf,txtapelido,txtemail,txtendI,txtnumI,txtedtcli,
           txtaddins,txtdata,txtqtdeins;
    private Clientes cliente;
    private ImageView imgedtcli,imgaddins;
    private LinearLayout llstat;


    public static int origemInstall;

    public static ArrayList<Instalacoes> install = new ArrayList<>();

    InstallPagerAdapter installPagerAdapter;
    ViewPager viewPager;
    private int qins =0;
    Context context;

    // private Spinner spinstal;
    // private Adapter_Instal adapter_instal;
    // public static int nnins;
    // Instalacoes newinstall;
//    ArrayList<Instalacoes> instalacoes = new ArrayList<>();
//    int k=0;


    public Frag_Consulta_Cli() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frag__consulta__cli, container, false);
        initVies(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context=getContext();
        homeViewModel = Home.homeViewModel;
        install=homeViewModel.getArrayinstall();
        qins=install.size();
        installPagerAdapter = new InstallPagerAdapter(getChildFragmentManager(),qins,context);
        installPagerAdapter.notifyDataSetChanged();
        viewPager = view.findViewById(R.id.vpclass);
        viewPager.setAdapter(installPagerAdapter);
    }

    private void initVies(View v) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        cliente=HomeViewModel.getClientes();

        txtnome = v.findViewById(R.id.txtnome);
        txtend = v.findViewById(R.id.txtend);
        txtnum = v.findViewById(R.id.txtnum);
        txtcompl = v.findViewById(R.id.txtcompl);
        txtcep = v.findViewById(R.id.txtcep);
        txtfone = v.findViewById(R.id.txtfone);
        txtcpf = v.findViewById(R.id.txtcpf);
        txtapelido = v.findViewById(R.id.txtapelido);
        txtemail = v.findViewById(R.id.txtemail);
        txtqtdeins = v.findViewById(R.id.txtqtdeins);

        txtstat = v.findViewById(R.id.txtstat);
        llstat = v.findViewById(R.id.llstat);

        txtendI = v.findViewById(R.id.txtendI);
        txtnumI = v.findViewById(R.id.txtnumI);

        txtdata = v.findViewById(R.id.txtdata);

        txtedtcli = v.findViewById(R.id.txtedtcli);
        txtaddins = v.findViewById(R.id.txtaddins);
        imgedtcli = v.findViewById(R.id.imgedtcli);
        imgaddins = v.findViewById(R.id.imgaddins);


        txtedtcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                HomeViewModel.setClientes(cliente);
                HomeViewModel.setCpfcod(cliente.getCpf());
                Home.origemCli=2;
               MainActivity.navController.navigate(R.id.action_frag_Consulta_Cli_to_frag_Editar_Cliente);
            }
        });

        txtaddins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origemInstall=2;
                util.vibratePhone(getContext(), (short) 20);
                MainActivity.navController.navigate(R.id.action_frag_Consulta_Cli_to_frag_Add_Install);

            }
        });



        imgedtcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtedtcli.callOnClick();
            }
        });

        imgaddins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtaddins.callOnClick();

            }
        });

        if(cliente!=null) {

            Instant it = Instant.ofEpochMilli(cliente.getTimestamp());
            ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
            LocalDate dataenvio = zdt.toLocalDate();

            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            String scomp = dataenvio.format(fmt);
            txtdata.setText(scomp);

            txtnome.setText(cliente.getNome());
            txtend.setText(cliente.getEndereco());
            txtnum.setText(cliente.getNumero());
            txtcompl.setText(cliente.getComplemento());
            txtcep.setText(cliente.getCep());
            txtfone.setText(cliente.getTelefone());
            txtcpf.setText(cliente.getCpf());
            txtapelido.setText(cliente.getApelido());
            txtemail.setText(cliente.getEmailcli());

            switch (cliente.getStatus()) {
                case 1:
                    llstat.setBackgroundColor(Color.GREEN);
                    txtstat.setText("Ativo");
                    break;
                case 2:
                    llstat.setBackgroundColor(Color.GRAY);
                    txtstat.setText("Inativo");
                    break;
                case 3:
                    llstat.setBackgroundColor(Color.RED);
                    txtstat.setText("Inadimplente");
                    break;
                default:
                    llstat.setBackgroundColor(Color.GRAY);
                    txtstat.setText("Inativo");
            }





            txtqtdeins.setText("(" + HomeViewModel.getArrayinstall().size() +")");



        }

    }



}