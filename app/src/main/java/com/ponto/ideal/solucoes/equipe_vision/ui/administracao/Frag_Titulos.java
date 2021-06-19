package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Baixa_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Boleto_Banco;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Frag_Titulos extends Fragment {

    private ConstraintLayout cltit;
    private RecyclerView rvtitulos;
    private TextView tttitle,txtdata,txttitle,txttodos;
    private GroupAdapter adapter;

    private LinearLayout lltodos,llmes,lldata;
    private Spinner spmes;

    private HomeViewModel homeViewModel;

    private ArrayList<String> bmes = new ArrayList<>();

    private ArrayAdapter<String> adaptmes;

    private LocalDate dataenvio;
    private long longdataenvio;
    private String YEAR,MONTH,DAY;
    private String TIPOFILTRO,TIPOTIME;
    public static ArrayList<Titulos> arrayTitulo = new ArrayList<>();
    private ArrayList<Titulos> arrayBaseTitulo = new ArrayList<>();
    private Titulos titAltera = new Titulos();


    public Frag_Titulos() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__titulos, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        tttitle=v.findViewById(R.id.tttitle);
        txtdata=v.findViewById(R.id.txtdata);
        txttodos=v.findViewById(R.id.txttodos);
        txttitle=v.findViewById(R.id.txttitle);
        cltit=v.findViewById(R.id.cltit);
        rvtitulos=v.findViewById(R.id.rvtitulos);

        for(int i=0;i<Home.baseTitulos.size();i++){
            if(Home.baseTitulos.get(i).getTipocobtit()==1){
                arrayBaseTitulo.add(Home.baseTitulos.get(i));
            }
        }

        spmes=v.findViewById(R.id.spmes);
        homeViewModel=Home.homeViewModel;
        homeViewModel.getBolgerabanco().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bol) {
              if(bol){
                  homeViewModel.setBolgerabancos(false);
                  carregaAdapter();
              }
            }
        });

        lltodos=v.findViewById(R.id.lltodos);
        llmes=v.findViewById(R.id.llmes);
        lldata=v.findViewById(R.id.lldata);

        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        YEAR = String.valueOf(dataenvio.getYear());
        MONTH =String.format("%02d", new Object[] { dataenvio.getMonthValue() });
        DAY = String.format("%02d", new Object[] { dataenvio.getDayOfMonth() });
        String scomp = dataenvio.format(fmt);

        try {
            String str_date= scomp;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            longdataenvio=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }


        txtdata.setText("Selecione");


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                monthOfYear = monthOfYear + 1;
                YEAR = String.valueOf(year);
                MONTH =String.format("%02d", new Object[] { monthOfYear });
                DAY = String.format("%02d", new Object[] { dayOfMonth });
                String date =  DAY+ "-" +MONTH+ "-" +YEAR;
                txtdata.setText(date);
                TIPOTIME ="data";
                spmes.setSelection(0);
                llmes.setBackgroundColor(getResources().getColor(R.color.gray));
                lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
                lldata.setBackgroundColor(Color.RED);
                carregaAdapter();
                try {
                    String str_date= date;
                    SimpleDateFormat formatter ;
                    Date date1 ;
                    formatter = new SimpleDateFormat("dd-MM-yyyy");
                    date1 = (Date) formatter.parse(str_date);
                    Log.i("test",""+date);
                    longdataenvio=date1.getTime();
                } catch (Exception e) {
                    System.out.println("Exception :"+e);
                }
            }};

        txtdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();

            }
        });

        txttodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spmes.setSelection(0);
                llmes.setBackgroundColor(getResources().getColor(R.color.gray));
                lldata.setBackgroundColor(getResources().getColor(R.color.gray));
                lltodos.setBackgroundColor(Color.RED);
                txtdata.setText("Selecione");
                TIPOTIME ="todos";
                carregaAdapter();
            }
        });


        bmes.clear();
        bmes.add("Selecione");
        Instant it2 = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt2 = it2.atZone(ZoneId.systemDefault());
        LocalDate dtt = zdt2.toLocalDate();

        DateTimeFormatter mesano = DateTimeFormatter
                .ofPattern("MM/yy")
                .withResolverStyle(ResolverStyle.STRICT);
        dtt=dtt.minusMonths(3);

        for (int i=0;i<6;i++){
            dtt=dtt.plusMonths(1);
            String smes = dtt.format(mesano);
            bmes.add(smes);
        }

        adaptmes= new ArrayAdapter<String>(getContext(),R.layout.sp_prod,bmes);
        adaptmes.setDropDownViewResource(R.layout.sp_prod );
        spmes.setAdapter(adaptmes);

        spmes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    llmes.setBackgroundColor(getResources().getColor(R.color.gray));
                    carregaAdapter();
                }else {
                    TIPOTIME = "mes";
                    llmes.setBackgroundColor(Color.RED);
                    lldata.setBackgroundColor(getResources().getColor(R.color.gray));
                    lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
                    DateTimeFormatter fmt = DateTimeFormatter
                            .ofPattern("dd-MM-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);
                    String scomp = dataenvio.format(fmt);
                    txtdata.setText("Selecione");

                    carregaAdapter();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TIPOTIME = "mes";
        String smesatual = dataenvio.format(mesano);
        for(int i=0;i<bmes.size();i++){
            if(bmes.get(i).toString().equals(smesatual)){
                spmes.setSelection(i);
            }
        }
        lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
        lldata.setBackgroundColor(getResources().getColor(R.color.gray));
        llmes.setBackgroundColor(Color.RED);
        TIPOFILTRO = "nulo";


        adapter = new GroupAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                ListaItem listaItem = (ListaItem) item;

                titAltera = listaItem.titulo;
                Alerta_Boleto_Banco alerta_boleto_banco = new Alerta_Boleto_Banco(titAltera);
                alerta_boleto_banco.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });
        carregaAdapter();


    }

    private void carregaAdapter() {

        Log.i("Tipotime",": " + TIPOTIME);
        adapter.clear();
        arrayTitulo.clear();

        switch (TIPOTIME){
            case "mes":

                for(int i=0;i<arrayBaseTitulo.size();i++) {
                    Instant it2 = Instant.ofEpochMilli(arrayBaseTitulo.get(i).getVencimentotit());
                    ZonedDateTime zdt2 = it2.atZone(ZoneId.systemDefault());
                    LocalDate dtt = zdt2.toLocalDate();
                    DateTimeFormatter mesano = DateTimeFormatter
                            .ofPattern("MM/yy")
                            .withResolverStyle(ResolverStyle.STRICT);
                    String smes = dtt.format(mesano);
                    if(smes.equals(spmes.getSelectedItem())  && arrayBaseTitulo.get(i).getGeradobanco()==0){
                        arrayTitulo.add(arrayBaseTitulo.get(i));
                    }
                }
                break;

            case "data":

                for(int i=0;i<arrayBaseTitulo.size();i++) {
                    Instant it2 = Instant.ofEpochMilli(arrayBaseTitulo.get(i).getVencimentotit());
                    ZonedDateTime zdt2 = it2.atZone(ZoneId.systemDefault());
                    LocalDate dtt = zdt2.toLocalDate();
                    DateTimeFormatter datacom = DateTimeFormatter
                            .ofPattern("dd-MM-yyyy")
                            .withResolverStyle(ResolverStyle.STRICT);
                    String sdata = dtt.format(datacom);
                    if(sdata.equals(txtdata.getText().toString()) && arrayBaseTitulo.get(i).getGeradobanco()==0){
                        arrayTitulo.add(arrayBaseTitulo.get(i));
                    }
                }

                break;

            case "todos":
                for(int i=0;i<arrayBaseTitulo.size();i++) {
                    if(arrayBaseTitulo.get(i).getGeradobanco()==0){
                        arrayTitulo.add(arrayBaseTitulo.get(i));
                    }
                }

                break;
        }

        if(arrayTitulo.size()>0){

            Collections.sort(arrayTitulo, new Comparator() {
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

            for(int i=0;i<arrayTitulo.size();i++){
                adapter.add(new ListaItem(arrayTitulo.get(i)));
            }
            txttitle.setText("Títulos a Gerar ("+ adapter.getItemCount()+")");
        }else{
            txttitle.setText("Não existem Títulos a Gerar");
        }

        adapter.notifyDataSetChanged();
        rvtitulos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvtitulos.setAdapter(adapter);
    }

    private class ListaItem extends Item<ViewHolder> {

        private final Titulos titulo;

        public ListaItem(Titulos titulo) {
            this.titulo =titulo;
        }
        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtbanco = viewHolder.itemView.findViewById(R.id.txtbanco);
            TextView txtconta = viewHolder.itemView.findViewById(R.id.txtconta);
            TextView txtcliente = viewHolder.itemView.findViewById(R.id.txtcliente);
            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            TextView txtvcto = viewHolder.itemView.findViewById(R.id.txtvcto);
            TextView txtdesc = viewHolder.itemView.findViewById(R.id.txtdesc);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            TextView txtgerar = viewHolder.itemView.findViewById(R.id.txtgerar);
            ImageView imggerar = viewHolder.itemView.findViewById(R.id.imggerar);
            LinearLayout llstat= viewHolder.itemView.findViewById(R.id.llstat);

            txtbanco.setText(titulo.getNomecobtit());
            txtconta.setText(titulo.getContacobtit());
            txtcliente.setText(titulo.getNomeclientetit());
            txtinstall.setText(titulo.getInstalltit());
            txtdesc.setText(titulo.getDesctit());
            txtvalor.setText(titulo.getValortit());

            Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
            ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
            LocalDate dataenvio = zdt.toLocalDate();

            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            String scomp = dataenvio.format(fmt);

            txtvcto.setText(scomp);

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
            return R.layout.rvtitulo_gerado;
        }
    }



}