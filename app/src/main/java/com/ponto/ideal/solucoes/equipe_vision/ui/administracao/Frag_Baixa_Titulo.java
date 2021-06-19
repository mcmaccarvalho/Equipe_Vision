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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Baixa_Titulo;
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


public class Frag_Baixa_Titulo extends Fragment {

    private ConstraintLayout cltit;
    private RecyclerView rvtitulos;
    private TextView tttitle,txtdata,txttitle,txttodos,txtlimpar;
    private GroupAdapter adapter;
    private ImageView imgcode;
    private EditText edtbarras;

    private LinearLayout lltodos,llmes,lldata;
    private Spinner spmes;

    private HomeViewModel homeViewModel;

    private ArrayList<String> bmes = new ArrayList<>();

    private ArrayAdapter<String> adaptmes;

    private LocalDate datahoje;
    private long longdataenvio;
    private String YEAR,MONTH,DAY;
    private String TIPOTIME;
    public static ArrayList<Titulos> arrayTitulo = new ArrayList<>();
    private ArrayList<Titulos> arrayBaseTitulo = new ArrayList<>();
    private ArrayList<Titulos> todosTitulos = new ArrayList<>();
    private Titulos titAltera = new Titulos();
    private Spinner sptipocob;
    private ArrayAdapter<String> adaptcob;

    DateTimeFormatter fmesano = DateTimeFormatter
            .ofPattern("MM/yy")
            .withResolverStyle(ResolverStyle.STRICT);

    public Frag_Baixa_Titulo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__baixa__titulo, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        tttitle=v.findViewById(R.id.tttitle);
        txtdata=v.findViewById(R.id.txtdata);
        txttodos=v.findViewById(R.id.txttodos);
        txttitle=v.findViewById(R.id.txttitle);
        txtlimpar=v.findViewById(R.id.txtlimpar);
        cltit=v.findViewById(R.id.cltit);
        rvtitulos=v.findViewById(R.id.rvtitulos);
        sptipocob=v.findViewById(R.id.sptipocob);
        imgcode=v.findViewById(R.id.imgcode);
        edtbarras=v.findViewById(R.id.edtbarras);
        spmes=v.findViewById(R.id.spmes);
        lltodos=v.findViewById(R.id.lltodos);
        llmes=v.findViewById(R.id.llmes);
        lldata=v.findViewById(R.id.lldata);


        todosTitulos = Home.baseTitulos;

        homeViewModel=Home.homeViewModel;

        homeViewModel.getBolgerabanco().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bol) {
                if(bol){
                    homeViewModel.setBolgerabancos(false);
                    itemSpinner();
                }
            }
        });


        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        datahoje = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        YEAR = String.valueOf(datahoje.getYear());
        MONTH =String.format("%02d", new Object[] { datahoje.getMonthValue() });
        DAY = String.format("%02d", new Object[] { datahoje.getDayOfMonth() });
        String scomp = datahoje.format(fmt);

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

        final ArrayList<String> scob = new ArrayList<>();
        scob.clear();
        scob.add("Todos");
        scob.add("Boleto");
        scob.add("Carteira");
        scob.add("Cartão");

        adaptcob= new ArrayAdapter<String>(getContext(),R.layout.sp_prod,scob);
        adaptcob.setDropDownViewResource(R.layout.sp_prod );
        sptipocob.setAdapter(adaptcob);

        sptipocob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                itemSpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        bmes.clear();
        bmes.add("Selecione");

        LocalDate dtt=datahoje.minusMonths(3);

        for (int i=0;i<6;i++){
            dtt=dtt.plusMonths(1);
            String smes = dtt.format(fmesano);
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
                }else {
                    util.vibratePhone(getContext(), (short) 20);
                    TIPOTIME = "mes";
                    llmes.setBackgroundColor(Color.RED);
                    lldata.setBackgroundColor(getResources().getColor(R.color.gray));
                    lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
                    txtdata.setText("Selecione");
                    itemSpinner();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                spmes.setSelection(0);
                llmes.setBackgroundColor(getResources().getColor(R.color.gray));
                lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
                lldata.setBackgroundColor(Color.RED);
                TIPOTIME ="data";
                itemSpinner();

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
                util.vibratePhone(getContext(), (short) 20);
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
                util.vibratePhone(getContext(), (short) 20);
                spmes.setSelection(0);
                llmes.setBackgroundColor(getResources().getColor(R.color.gray));
                lldata.setBackgroundColor(getResources().getColor(R.color.gray));
                lltodos.setBackgroundColor(Color.RED);
                txtdata.setText("Selecione");
                TIPOTIME ="todos";
                itemSpinner();
            }
        });

        txtlimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtbarras.setText("");
                hideSoftKeyboard();
                edtbarras.clearFocus();

            }
        });

        cltit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                edtbarras.clearFocus();
            }
        });

        edtbarras.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        checacode(edtbarras.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });

        imgcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtbarras.getText().toString().equals("")) {
                    IntentIntegrator.forSupportFragment(Frag_Baixa_Titulo.this)
                            .initiateScan();
                }else{
                    checacode(edtbarras.getText().toString().trim());
                }
            }
        });

        TIPOTIME = "mes";
        String smesatual = datahoje.format(fmesano);
        for(int i=0;i<bmes.size();i++){
            if(bmes.get(i).toString().equals(smesatual)){
                spmes.setSelection(i);
            }
        }
        lltodos.setBackgroundColor(getResources().getColor(R.color.gray));
        lldata.setBackgroundColor(getResources().getColor(R.color.gray));
        llmes.setBackgroundColor(Color.RED);

        adapter = new GroupAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                ListaItem listaItem = (ListaItem) item;
                titAltera = listaItem.titulo;
                Alerta_Baixa_Titulo alerta_baixa_titulo = new Alerta_Baixa_Titulo(titAltera);
                alerta_baixa_titulo.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });

    }

    private void itemSpinner(){

        arrayBaseTitulo.clear();
        switch (sptipocob.getSelectedItemPosition()){
            case 0:
                for(int i=0;i<todosTitulos.size();i++){
                    if(todosTitulos.get(i).getStatustit()==1) {
                        arrayBaseTitulo.add(todosTitulos.get(i));
                    }
                }
                break;

            case 1:

                for(int i=0;i<todosTitulos.size();i++){

                        if(todosTitulos.get(i).getTipocobtit()==1 ) {
                            if(todosTitulos.get(i).getStatustit()==1) {
                                arrayBaseTitulo.add(todosTitulos.get(i));
                            }
                        }

                }
                break;
            case 2:
                for(int i=0;i<todosTitulos.size();i++){
                    if(todosTitulos.get(i).getTipocobtit()==2 ) {
                        if(todosTitulos.get(i).getStatustit()==1) {
                            arrayBaseTitulo.add(todosTitulos.get(i));
                        }
                    }
                }
                break;
            case 3:
                for(int i=0;i<todosTitulos.size();i++){
                    if(todosTitulos.get(i).getTipocobtit()==3 ) {
                        if(todosTitulos.get(i).getStatustit()==1) {
                            arrayBaseTitulo.add(todosTitulos.get(i));
                        }
                    }
                }
                break;
        }


        Collections.sort(arrayBaseTitulo, new Comparator() {
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
        carregaAdapter();
    }

    private void carregaAdapter() {

        adapter.clear();
        arrayTitulo.clear();
        Instant it2 = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt2 = it2.atZone(ZoneId.systemDefault());

        switch (TIPOTIME) {
            case "mes":
                for (int i = 0; i < arrayBaseTitulo.size(); i++) {
                    it2 = Instant.ofEpochMilli(arrayBaseTitulo.get(i).getVencimentotit());
                    zdt2 = it2.atZone(ZoneId.systemDefault());
                    LocalDate dtt = zdt2.toLocalDate();
                    String smes = dtt.format(fmesano);
                    if (smes.equals(spmes.getSelectedItem())) {
                        arrayTitulo.add(arrayBaseTitulo.get(i));
                        adapter.add(new ListaItem(arrayBaseTitulo.get(i)));
                    }
                }
                break;

            case "data":
                for (int i = 0; i < arrayBaseTitulo.size(); i++) {
                    it2 = Instant.ofEpochMilli(arrayBaseTitulo.get(i).getVencimentotit());
                    zdt2 = it2.atZone(ZoneId.systemDefault());
                    LocalDate dtt = zdt2.toLocalDate();
                    DateTimeFormatter datacom = DateTimeFormatter
                            .ofPattern("dd-MM-yyyy")
                            .withResolverStyle(ResolverStyle.STRICT);
                    String sdata = dtt.format(datacom);
                    if (sdata.equals(txtdata.getText().toString()) && arrayBaseTitulo.get(i).getStatustit() == 1) {
                        arrayTitulo.add(arrayBaseTitulo.get(i));
                        adapter.add(new ListaItem(arrayBaseTitulo.get(i)));
                    }
                }
                break;

            case "todos":
                for (int i = 0; i < arrayBaseTitulo.size(); i++) {
                    arrayTitulo.add(arrayBaseTitulo.get(i));
                    adapter.add(new ListaItem(arrayBaseTitulo.get(i)));
                }

                break;
        }

        if(arrayTitulo.size()>0){
            txttitle.setText("Títulos a Baixar para o período ("+ adapter.getItemCount()+")");
        }else{
            txttitle.setText("Não existem Títulos a Baixar para o período");
        }

        adapter.notifyDataSetChanged();
        rvtitulos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvtitulos.setAdapter(adapter);
    }

    private void checacode(String msg){

        if(!msg.equals("Scan Failure")) {

            boolean tem = false;

            if(msg!="" && msg!=null) {

                for (int i = 0; i < todosTitulos.size(); i++) {
                    if (todosTitulos.get(i).getBarrastit().equals(msg)) {
                        if (todosTitulos.get(i).getStatustit() == 2) {
                            tem = true;
                            util.showSnackAsk(cltit, "Título já baixado.");
                            edtbarras.setText("");
                            edtbarras.clearFocus();
                        } else {
                            edtbarras.setText("");
                            edtbarras.clearFocus();
                            titAltera = todosTitulos.get(i);
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){

                Log.i("scanview",result.getContents());
                checacode(result.getContents());
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

            TextView txtbanco = viewHolder.itemView.findViewById(R.id.txtbanco);
            TextView txtconta = viewHolder.itemView.findViewById(R.id.txtconta);
            TextView txtcliente = viewHolder.itemView.findViewById(R.id.txtcliente);
            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            TextView txtvcto = viewHolder.itemView.findViewById(R.id.txtvcto);
         //  TextView ttbanco = viewHolder.itemView.findViewById(R.id.ttbanco);
            TextView txtdesc = viewHolder.itemView.findViewById(R.id.txtdesc);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            TextView txtgerar = viewHolder.itemView.findViewById(R.id.txtgerar);
            ImageView imggerar = viewHolder.itemView.findViewById(R.id.imggerar);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);

//            switch (titulo.getTipocobtit()){
//                case 1:txtbanco.setText("Boleto");break;
//                case 2:txtbanco.setText("Carteira");break;
//                case 3:txtbanco.setText("Cartão");break;
//            }
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

    private void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


}