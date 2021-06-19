package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Baixa_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class Frag_Listar_Instal extends Fragment {

    private RadioGroup rgativas,rgordem;
    private RadioButton rbativas,rbtodas,rbcliente,rbinstal;
    private RecyclerView rvinstal;
    private GroupAdapter adapter,adapterTitulos;
    private ImageView setacli,setainst;

    private Button btcad;
    private TextView txtcliente,txtinstal,txtatrasom,txttotcli,txttotins;
    private CardView cvnome;
    private ObjectAnimator animator1;


    private  ArrayList<Titulos> titcli = new ArrayList<>();
    private ArrayList<Instalacoes> installAdapt  = new ArrayList<>();
    private ArrayList<Clientes>    clientesAdapt = new ArrayList<>();


    private boolean bcli=false;
    private boolean binst=true;
    private boolean adptit=false;
    private boolean reverse = false;

    private long longhoje;

    public Frag_Listar_Instal() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_frag__listar__instal, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        rvinstal=v.findViewById(R.id.rvinstal);

        rgordem=v.findViewById(R.id.rgordem);
        rbcliente=v.findViewById(R.id.rbcliente);
        rbinstal=v.findViewById(R.id.rbinstal);
        setacli=v.findViewById(R.id.setacli);
        setainst=v.findViewById(R.id.setainst);
        txttotcli=v.findViewById(R.id.txttotcli);
        txttotins=v.findViewById(R.id.txttotins);
        txtcliente=v.findViewById(R.id.txtcliente);
        txtatrasom=v.findViewById(R.id.txtatrasom);
        txtinstal=v.findViewById(R.id.txtinstal);
        btcad=v.findViewById(R.id.btcad);
        cvnome=v.findViewById(R.id.cvnome);

        animator1 = ObjectAnimator.ofFloat(cvnome, "translationY", -1000);
        animator1.setDuration(1);
        animator1.start();


        longhoje= util.long_X_ld(util.ld_X_long(System.currentTimeMillis()));

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adptit)return;
                adptit=false;
                animator1.setDuration(800);
                animator1.start();
                carregaLista();
            }
        });

        rbcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adptit)return;

                if(rbcliente.isChecked()) {
                    setainst.setVisibility(View.GONE);
                    setacli.setVisibility(View.VISIBLE);
                    bcli = !bcli;
                    if (bcli) {
                        setacli.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_upward_24));
                    } else {
                        setacli.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_downward_24));
                    }
                    binst=true;
                }

                carregaLista();
            }
        });
        rbinstal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adptit)return;
                if(rbinstal.isChecked()){
                    setacli.setVisibility(View.GONE);
                    setainst.setVisibility(View.VISIBLE);
                    binst=!binst;
                    if(binst){
                        setainst.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_upward_24));
                    }else{
                        setainst.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_downward_24));
                    }
                    bcli=true;
                }
                carregaLista();
            }
        });

        int contcli =0;
        int contins =0;
        for(int i=0;i<Home.baseClientes.size();i++){
            if(Home.baseClientes.get(i).getStatus()!=2)contcli++;
        }
        for(int i=0;i<Home.baseInstall.size();i++){
            if(Home.baseInstall.get(i).getStatus()==1 ||
                    Home.baseInstall.get(i).getStatus()==3)contins++;
        }
        txttotcli.setText(String.valueOf(contcli));
        txttotins.setText(String.valueOf(contins));


//        Collections.sort(arrayBaseClientes, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                Clientes p1 = (Clientes) o1;
//                Clientes p2 = (Clientes) o2;
//                return p1.getNome().compareToIgnoreCase(p2.getNome());
//            }
//        });

        adapterTitulos = new GroupAdapter();
        adapter = new GroupAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if (adptit) return;
                if (rbinstal.isChecked()) {
                    ListaItem listaItem = (ListaItem) item;
                    adptit = true;
                    String keycli = "";
                    for (int i = 0; i < Home.baseClientes.size(); i++) {
                        if (Home.baseClientes.get(i).getKeycli().equals(listaItem.instalacao.getCliente())) {
                            txtcliente.setText(Home.baseClientes.get(i).getNome());
                            keycli = Home.baseClientes.get(i).getKeycli();
                            break;
                        }
                    }
                    ArrayList<String> stins = new ArrayList<>();
                    for (int i = 0; i < Home.baseInstall.size(); i++) {
                        if (Home.baseInstall.get(i).getCliente().equals(keycli)) {
                            stins.add(Home.baseInstall.get(i).getNuminstal());
                        }
                    }
                    if (stins.size() > 1) {
                        txtinstal.setText(stins.size() + " instalações");
                    } else {
                        txtinstal.setText(listaItem.instalacao.getNuminstal() + " (" + listaItem.instalacao.getMegainstal() + ")");
                    }
                    animator1.setDuration(800);
                    animator1.reverse();
                    carregaTitulos(listaItem.instalacao.getCliente());
                }

                if (rbcliente.isChecked()) {
                    ListaClientes listaClientes = (ListaClientes) item;
                    adptit = true;
                    txtcliente.setText(listaClientes.cliente.getNome());
                    ArrayList<Instalacoes> stins = new ArrayList<>();
                    for (int i = 0; i < Home.baseInstall.size(); i++) {
                        if (Home.baseInstall.get(i).getCliente().equals(listaClientes.cliente.getKeycli())) {
                            stins.add(Home.baseInstall.get(i));
                        }
                    }
                    if (stins.size() > 1) {
                        txtinstal.setText(stins.size() + " instalações");
                    } else {
                          txtinstal.setText(stins.get(0).getNuminstal() + " (" + stins.get(0).getMegainstal() + ")");
                    }
                    animator1.setDuration(800);
                    animator1.reverse();
                     carregaTitulos(listaClientes.cliente.getKeycli());
                }
            }

        });


        carregaLista();

    }

    private void carregaLista() {

        installAdapt .clear();
        clientesAdapt.clear();
        adapter.clear();

        if(rbcliente.isChecked()){

            for (int i=0;i<Home.baseClientes.size();i++){
                if(Home.baseClientes.get(i).getStatus()==1 || Home.baseClientes.get(i).getStatus()==3){
                    clientesAdapt.add(Home.baseClientes.get(i));
                }
            }

            Collections.sort(clientesAdapt, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Clientes p1 = (Clientes) o1;
                    Clientes p2 = (Clientes) o2;
                    return p1.getNome().compareToIgnoreCase(p2.getNome());
                }
            });
            if(bcli)Collections.reverse(clientesAdapt);

            for(int i=0;i<clientesAdapt.size();i++){
                adapter.add(new ListaClientes(clientesAdapt.get(i)));
            }

        }

        if(rbinstal.isChecked()){


            for (int i=0;i<Home.baseInstall.size();i++){
                if(Home.baseInstall.get(i).getStatus()==1 || Home.baseInstall.get(i).getStatus()==3){
                    installAdapt.add(Home.baseInstall.get(i));
                }
            }

            Collections.sort(installAdapt, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Instalacoes p1 = (Instalacoes) o1;
                    Instalacoes p2 = (Instalacoes) o2;
                    return p1.getNuminstal().compareToIgnoreCase(p2.getNuminstal());
                }
            });
            if(binst)Collections.reverse(installAdapt);

            for(int i=0;i<installAdapt.size();i++){
                adapter.add(new ListaItem(installAdapt.get(i)));
            }

        }

        adapter.notifyDataSetChanged();
        rvinstal.setLayoutManager(new LinearLayoutManager(getContext()));
        rvinstal.setAdapter(adapter);
    }

    private class ListaClientes extends Item<ViewHolder> {

        private final Clientes cliente;

        public ListaClientes(Clientes cliente) {
            this.cliente = cliente;
        }

        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtcliente = viewHolder.itemView.findViewById(R.id.txtcliente);
            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            LinearLayout llstat= viewHolder.itemView.findViewById(R.id.llstat);

            txtcliente.setText(cliente.getNome());

            switch (cliente.getStatus()){
                case 1:
                    txtstat.setText("Ativo");
                    llstat.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    txtstat.setText("Inativo");
                    llstat.setBackgroundColor(Color.GRAY);
                    break;
                case 3:
                    txtstat.setText("Inadimplente");
                    llstat.setBackgroundColor(Color.RED);
                    break;

            }

           String ins = "";
            for (int i=0;i<Home.baseInstall.size();i++){
                if(Home.baseInstall.get(i).getCliente().equals(cliente.getKeycli())
                && Home.baseInstall.get(i).getStatus()!=2){
                    ins=ins+(Home.baseInstall.get(i).getNuminstal()+
                            " (" + Home.baseInstall.get(i).getMegainstal()+
                            ")\n");
                }
            }

            txtinstall.setText(ins);


        }
        @Override
        public int getLayout() {
            return R.layout.rv_listar_cli_stat;
        }
    }

    private class ListaItem extends Item<ViewHolder> {


        private final Instalacoes instalacao;

        public ListaItem(Instalacoes instalacoes) {
            this.instalacao =instalacoes;
        }

        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {


            TextView txtcliente = viewHolder.itemView.findViewById(R.id.txtcliente);
            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);

            for (int i=0;i<Home.baseClientes.size();i++){
                if(Home.baseClientes.get(i).getKeycli().equals(instalacao.getCliente())){
                    txtcliente.setText(Home.baseClientes.get(i).getNome());
                    break;
                }
            }
            txtinstall.setText(instalacao.getNuminstal() +" (" + instalacao.getMegainstal() +")");
            switch (instalacao.getStatus()){
                case 1:
                    txtstat.setText(("Ativa"));
                    llstat.setBackgroundColor(Color.GREEN);
                    break;

                case 2:
                    txtstat.setText(("Inativa"));
                    llstat.setBackgroundColor(Color.GRAY);
                    break;
                case 3:
                    txtstat.setText(("Inadimplente"));
                    llstat.setBackgroundColor(Color.RED);
                    break;
            }


        }
        @Override
        public int getLayout() {
            return R.layout.rv_listar_instal;
        }
    }




    private void carregaTitulos(String cliente) {

        adapterTitulos.clear();
        titcli.clear();
        titcli=new ArrayList<>();
        double totdd =0;
        double qtdetit=0;
        for(int i=0;i<Home.baseTitulos.size();i++){
            double ddatraso = 0;
            if(Home.baseTitulos.get(i).getKeyclientetit().equals(cliente)){
                titcli.add(Home.baseTitulos.get(i));
                qtdetit=qtdetit+1;
                switch (Home.baseTitulos.get(i).getStatustit()){
                    case 1:
                        ddatraso = diasatraso(Home.baseTitulos.get(i).getVencimentotit(),longhoje);
                        break;
                    case 2:
                        ddatraso = diasatraso(Home.baseTitulos.get(i).getVencimentotit(),Home.baseTitulos.get(i).getDatapgtotit());
                        break;
                    case 3:
                        ddatraso=0;
                        break;
                }
            }

            Log.i("ddatraso"," : " + ddatraso + " vl: " + Home.baseTitulos.get(i).getValortit());
            if(ddatraso<0)ddatraso=0;
            totdd=totdd+ddatraso;
            Log.i("ddatraso"," dep: " + ddatraso + " vl: " + Home.baseTitulos.get(i).getValortit());
        }
        Log.i("ddatraso","  " );
        Log.i("ddatraso"," totdd: " + totdd );
        Log.i("ddatraso"," qtdet: " + qtdetit );
        Log.i("ddatraso","  " );

        double atrr = totdd/qtdetit;
        String sttes = String.valueOf(atrr);
        BigDecimal btes = new BigDecimal(sttes);
        btes = btes.divide(new BigDecimal("1"),1, RoundingMode.HALF_EVEN);
        txtatrasom.setText((String.valueOf(btes)));

        double tes = 3;
        tes = tes/7;

        sttes = String.valueOf(tes);
        Log.i("ddatraso"," sttes: " + sttes );
        btes = new BigDecimal(sttes);
        btes = btes.divide(new BigDecimal("1"),2, RoundingMode.HALF_EVEN);
        Log.i("ddatraso","btes: " + btes );

        Collections.sort(titcli, new Comparator() {
            public int compare(Object o1, Object o2) {
                Titulos p1 = (Titulos) o1;
                Titulos p2 = (Titulos) o2;

                if(p1.getVencimentotit()>p2.getVencimentotit()) {
                    return -1;
                }else {
                    return 1;
                }

            }
        });




        for(int i=0;i<titcli.size();i++){
            adapterTitulos.add(new ListaTitulos(titcli.get(i)));
        }


        adapterTitulos.notifyDataSetChanged();
                rvinstal.setLayoutManager(new LinearLayoutManager(getContext()));
                rvinstal.setAdapter(adapterTitulos);


    }

    private class ListaTitulos extends Item<ViewHolder> {

        private final Titulos titulo;

        public ListaTitulos(Titulos titulo) {
            this.titulo =titulo;
        }

        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtvcto = viewHolder.itemView.findViewById(R.id.txtvcto);
            TextView txtpgto = viewHolder.itemView.findViewById(R.id.txtpgto);
            TextView txtbanco = viewHolder.itemView.findViewById(R.id.txtbanco);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            TextView txtdesc = viewHolder.itemView.findViewById(R.id.txtdesc);
            TextView txtdiasatraso = viewHolder.itemView.findViewById(R.id.txtdiasatraso);
            LinearLayout llstat= viewHolder.itemView.findViewById(R.id.llstat);

            switch (titulo.getTipocobtit()){
                case 1:txtbanco.setText("Boleto");break;
                case 2:txtbanco.setText("Carteira");break;
                case 3:txtbanco.setText("Cartão");break;
                case 10:txtbanco.setText("Cortesia");break;
            }

            txtvalor.setText(titulo.getValortit());
            txtdesc.setText(titulo.getDesctit());

            double ddatraso=0;

            long dataV = titulo.getVencimentotit();
            long dataP = titulo.getDatapgtotit();

            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy")
                    .withResolverStyle(ResolverStyle.STRICT);

            Instant vt = Instant.ofEpochMilli(dataV);
            ZonedDateTime zvt = vt.atZone(ZoneId.systemDefault());
            LocalDate datavcto = zvt.toLocalDate();
            String svcto = datavcto.format(fmt);
            txtvcto.setText(svcto);

            vt = Instant.ofEpochMilli(dataP);
            zvt = vt.atZone(ZoneId.systemDefault());
            datavcto = zvt.toLocalDate();

            if(dataP==0){
                txtpgto.setText("00-00-0000");
            }else{
                String spgto = datavcto.format(fmt);
                txtpgto.setText(spgto);
            }

            switch (titulo.getStatustit()){
                case 1:
                    txtstat.setText("Em Aberto");
                    llstat.setBackgroundColor(Color.GREEN);
                    ddatraso = diasatraso(titulo.getVencimentotit(),longhoje);

                    break;
                case 2:
                    txtstat.setText("Baixado");
                    llstat.setBackgroundColor(Color.BLUE);
                    ddatraso = diasatraso(titulo.getVencimentotit(),titulo.getDatapgtotit());
                    break;
                case 3:
                    txtstat.setText("Cancelado");
                    llstat.setBackgroundColor(Color.GRAY);
                    ddatraso=0;
                    break;
            }

            if(titulo.getSituacaotit()==1){
                txtstat.setText("Inadimplente");
                llstat.setBackgroundColor(Color.RED);
            }

            if(ddatraso<0)ddatraso=0;

            String sttes = String.valueOf(ddatraso);
            BigDecimal btes = new BigDecimal(sttes);
            btes = btes.divide(new BigDecimal("1"),0, RoundingMode.UNNECESSARY);

            txtdiasatraso.setText(String.valueOf(btes));


        }
        @Override
        public int getLayout() {
            return R.layout.rv_listar_tit_cli;
        }
    }




    public static double diasatraso(long datavcto, long datapagto){
        double result = 0;
        long diferenca = datapagto - datavcto;
        double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias
        long horasRestantes = (diferenca /1000) / 60 / 60;// %24; //calcula as horas restantes
        result = diferencaEmDias;// + (horasRestantes /24d); //transforma as horas restantes em fração de dias
        return result;
    }


}