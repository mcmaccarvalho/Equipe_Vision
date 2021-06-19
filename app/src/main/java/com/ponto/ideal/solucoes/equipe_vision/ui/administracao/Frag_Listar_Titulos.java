package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Cobrancas_Tit;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Baixa_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Editar_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Ver_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Frag_Listar_Titulos extends Fragment {

    private ConstraintLayout cltit;
    private RecyclerView rvtitulos;
    private TextView tttitle,txtdata,txttitle,txttodos,txtlimpar, txtano, txtmes;


    private TextView txtbase;
    private ImageView imgleft,imgright;
    private LocalDate hoje;
    private LocalDate mesbase;
    private long longhoje;
    private long longmesbase;



    private GroupAdapter adapter;

    private Spinner sptipocob,spstat;

    private HomeViewModel homeViewModel;

    private ArrayAdapter<String> adaptcob;
    private Adapter_Cobrancas_Tit adapter_cobrancas;
    private ArrayList<Cobrancas> bcobrancas = new ArrayList<>();

    public static ArrayList<Titulos> arrayTitulos = new ArrayList<>();
    public static ArrayList<Titulos> arrayTemp = new ArrayList<>();
    private Titulos titAltera = new Titulos();


    private String keycob="";

    public Frag_Listar_Titulos() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_listar__titulo, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        homeViewModel= Home.homeViewModel;
        tttitle=v.findViewById(R.id.tttitle);
        txttitle=v.findViewById(R.id.txttitle);

        txtmes=v.findViewById(R.id.txtmes);
        cltit=v.findViewById(R.id.cltit);
        rvtitulos=v.findViewById(R.id.rvtitulos);
        sptipocob=v.findViewById(R.id.sptipocob);


        txtbase = v.findViewById(R.id.txtbase);
        imgright = v.findViewById(R.id.imgright);
        imgleft = v.findViewById(R.id.imgleft);

        mesbase=util.Dia_01_Mes(System.currentTimeMillis());
        longmesbase=util.longmesbase(mesbase);
        txtbase.setText(util.fma(mesbase));

        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesbase=mesbase.minusMonths(1);
                txtbase.setText(util.fma(mesbase));
                longmesbase=util.longmesbase(mesbase);
                itemSpinner();
            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesbase=mesbase.plusMonths(1);
                txtbase.setText(util.fma(mesbase));
                longmesbase=util.longmesbase(mesbase);
                itemSpinner();
            }
        });


        spstat=v.findViewById(R.id.spstat);

        cltit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();

            }
        });

        final ArrayList<String> scob = new ArrayList<>();
        scob.clear();
        scob.add("Todos");
        scob.add("Boleto");
        scob.add("Carteira");
        scob.add("Cartão");
        scob.add("Cortesia");

        adaptcob= new ArrayAdapter<String>(getContext(),R.layout.sp_comum,scob);
        adaptcob.setDropDownViewResource(R.layout.sp_comum );
        sptipocob.setAdapter(adaptcob);

        sptipocob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                carregaCobrancas(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        homeViewModel=Home.homeViewModel;

        homeViewModel.getBolgerabanco().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bol) {
                if(bol){
                    homeViewModel.setBolgerabancos(false);
                    util.ChecaStatusTit();
                    itemSpinner();
                }
            }
        });


        adapter_cobrancas = new Adapter_Cobrancas_Tit(getContext(), bcobrancas);
        adapter_cobrancas.notifyDataSetChanged();
        spstat.setAdapter(adapter_cobrancas);
        spstat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                keycob=bcobrancas.get(position).getKey_cobranca();
                spstat.clearFocus();
                itemSpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        adapter = new GroupAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                ListaItem listaItem = (ListaItem) item;

                switch (listaItem.titulo.getStatustit()) {

                    case 1:
                        titAltera = listaItem.titulo;
                        Alerta_Editar_Titulo alerta_editar_titulo = new Alerta_Editar_Titulo(titAltera);
                        alerta_editar_titulo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                    case 2:
                        titAltera = listaItem.titulo;
                        Alerta_Ver_Titulo alerta_ver_titulo = new Alerta_Ver_Titulo(titAltera);
                        alerta_ver_titulo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;
                    case 3:
                        titAltera = listaItem.titulo;
                        Alerta_Ver_Titulo alerta_ver_titulo2 = new Alerta_Ver_Titulo(titAltera);
                        alerta_ver_titulo2.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        break;


                }
            }
        });

        itemSpinner();
    }

    private void carregaCobrancas(int position) {

        bcobrancas.clear();
        Cobrancas cds = new Cobrancas();
        cds.setNome_cobranca("Todas");
        cds.setKey_cobranca("0");
        bcobrancas.add(cds);

        switch (position){

            case 1:
                for(int i=0;i<Home.baseCobrancas.size();i++){
                    if(Home.baseCobrancas.get(i).getType_cob()==1){
                        bcobrancas.add(Home.baseCobrancas.get(i));
                    }
                }

                break;
            case 2:
                for(int i=0;i<Home.baseCobrancas.size();i++){
                    if(Home.baseCobrancas.get(i).getType_cob()==2){
                        bcobrancas.add(Home.baseCobrancas.get(i));
                    }
                }

                break;
            case 3:
                for(int i=0;i<Home.baseCobrancas.size();i++){
                    if(Home.baseCobrancas.get(i).getType_cob()==3){
                        bcobrancas.add(Home.baseCobrancas.get(i));
                    }
                }

                break;

        }

        adapter_cobrancas.notifyDataSetChanged();
        spstat.setSelection(0);
        itemSpinner();
    }

    private void itemSpinner(){
        adapter.clear();
        arrayTemp.clear();
        arrayTitulos.clear();
        for(int i=0;i<Home.baseTitulos.size();i++) {
            if(longmesbase==(Home.baseTitulos.get(i).getComptit())){
                arrayTitulos.add(Home.baseTitulos.get(i));
            }
        }

        Log.i("arrayTit"," arrayTitulos 1 " + arrayTitulos.size());
        arrayTemp.clear();
        arrayTemp.addAll(arrayTitulos);
        arrayTitulos.clear();
        Log.i("arrayTit"," arrayTemp 1 " + arrayTemp.size());
        int tip = sptipocob.getSelectedItemPosition();
        if(tip==4)tip=10;

        if(tip==0){
            for(int i=0;i<arrayTemp.size();i++){
                arrayTitulos.add(arrayTemp.get(i));
            }
        }else{
            for(int i=0;i<arrayTemp.size();i++){
                if(arrayTemp.get(i).getTipocobtit()==tip) {
                    arrayTitulos.add(arrayTemp.get(i));
                }
            }
        }

        Log.i("arrayTit"," arrayTitulos 2 " + arrayTitulos.size());
        arrayTemp.clear();
        arrayTemp.addAll(arrayTitulos);
        arrayTitulos.clear();
        Log.i("arrayTit"," arrayTemp 2 " + arrayTemp.size());
        int cob =spstat.getSelectedItemPosition();
        if(cob==0){
            arrayTitulos.addAll(arrayTemp);
        }else{
            for (int i = 0; i < arrayTemp.size(); i++) {
                if ((arrayTemp.get(i).getKeycobtit().equals(keycob))) {
                    arrayTitulos.add(arrayTemp.get(i));
                }
            }
        }

        Collections.sort(arrayTitulos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Titulos p1 = (Titulos) o1;
                Titulos p2 = (Titulos) o2;
                if(p1.getVencimentotit()<p2.getVencimentotit()) {
                    return -1;
                }else{
                    return 1;
                }
            }
        });
        Log.i("arrayTit"," arrayTitulos 3 " + arrayTitulos.size());
        for (int i=0;i<arrayTitulos.size();i++){
            adapter.add(new ListaItem(arrayTitulos.get(i)));
        }

        if(arrayTitulos.size()>0){
            txttitle.setText("Listagem de Títulos Competencia "+ util.fma(mesbase) +" (" + adapter.getItemCount() +")");
        }else{
            txttitle.setText("Não existem Títulos para esses parâmetros.");
        }

        adapter.notifyDataSetChanged();
        rvtitulos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvtitulos.setAdapter(adapter);

    }


    private void mostracode(String msg){
        if(!msg.equals("Scan Failure")) {
            boolean tem = false;
            for (int i = 0; i < Home.baseTitulos.size(); i++) {
                if (Home.baseTitulos.get(i).getBarrastit().equals(msg)) {
                    if(Home.baseTitulos.get(i).getStatustit()==3){
                        tem=true;
                        util.showSnackAsk(cltit, "Título já baixado.");
                    }else {
                        titAltera = Home.baseTitulos.get(i);
                        Alerta_Baixa_Titulo alerta_baixa_titulo = new Alerta_Baixa_Titulo(titAltera);
                        alerta_baixa_titulo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
                        tem = true;
                    }
                    break;
                }
            }

            if (!tem) {
                util.showSnackAsk(cltit, "Título não encontrado.");
            }
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){

                Log.i("scanview",result.getContents());
                mostracode(result.getContents());
            }else{
                Log.i("scanview","Scan Failure");
                util.showSnackAsk(cltit, "Erro ao scanear cógigo.");
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class ListaItem extends Item<ViewHolder> {

        private final Titulos titulo;

        public ListaItem(Titulos titulo) {
            this.titulo =titulo;
        }

        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtvcto = viewHolder.itemView.findViewById(R.id.txtvcto);
            TextView txtpgto = viewHolder.itemView.findViewById(R.id.txtpgto);
            TextView txtbanco = viewHolder.itemView.findViewById(R.id.txtbanco);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            TextView txtcliente = viewHolder.itemView.findViewById(R.id.txtcliente);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            TextView txtdesc = viewHolder.itemView.findViewById(R.id.txtdesc);
            TextView txtgerar = viewHolder.itemView.findViewById(R.id.txtgerar);
            ImageView imggerar = viewHolder.itemView.findViewById(R.id.imggerar);
            LinearLayout llstat= viewHolder.itemView.findViewById(R.id.llstat);
            TextView ttvcto = viewHolder.itemView.findViewById(R.id.ttvcto);


            switch (titulo.getTipocobtit()){
                case 1:txtbanco.setText("Boleto");break;
                case 2:txtbanco.setText("Carteira");break;
                case 3:txtbanco.setText("Cartão");break;
                case 10:txtbanco.setText("Cortesia");break;
            }

            txtcliente.setText(titulo.getNomeclientetit());
            txtinstall.setText(titulo.getInstalltit());
            txtvalor.setText(titulo.getValortit());
            txtdesc.setText(titulo.getDesctit());

            long dataV = titulo.getVencimentotit();
            long dataP = titulo.getDatapgtotit();

            Instant vt = Instant.ofEpochMilli(dataV);
            Instant pt = Instant.ofEpochMilli(dataP);
            ZonedDateTime zvt = vt.atZone(ZoneId.systemDefault());
            ZonedDateTime zpt = pt.atZone(ZoneId.systemDefault());
            LocalDate datavcto = zvt.toLocalDate();
            LocalDate datapgto = zpt.toLocalDate();

            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            String svcto = datavcto.format(fmt);
            txtvcto.setText(svcto);

            if(dataP==0){
                txtpgto.setText("00-00-0000");
            }else{
                String spgto = datapgto.format(fmt);
                txtpgto.setText(spgto);
            }

            switch (titulo.getStatustit()){

                case 1:
                    txtstat.setText("Em Aberto");
                    llstat.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    txtstat.setText("Baixado");
                    llstat.setBackgroundColor(Color.BLUE);
                    break;
                case 3:
                    txtstat.setText("Cancelado");
                    llstat.setBackgroundColor(Color.GRAY);
                    break;
            }

            if(titulo.getSituacaotit()==1){
                txtstat.setText("Inadimplente");
                llstat.setBackgroundColor(Color.RED);
            }

            if(titulo.getGeradobanco()==0 && titulo.getTipocobtit()==1){
                imggerar.setVisibility(View.VISIBLE);
                txtgerar.setVisibility(View.VISIBLE);
            }else{
                imggerar.setVisibility(View.INVISIBLE);
                txtgerar.setVisibility(View.INVISIBLE);
            }
        }



        @Override
        public int getLayout() {
            return R.layout.rvtitulo_listar_titulo;
        }
    }

    private void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


}