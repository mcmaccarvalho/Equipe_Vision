package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Baixa_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.MaskMoney;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Alerta_Boleto_Banco extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtcliente, txtinstall,txtvcto,txtend,txtnum,txtcep,txtcompl,ttbanco,txtconta,txtbanco,txtcpf;
    private ImageView imgrefresh,imgcode;
    private EditText edtvalor,txtbarras;
    Button btcad,btcancel;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clgerabol;
    private Titulos titulo;
    String  VALOR,VCTO,BARRAS;

    private ArrayList<Titulos> todostitulos = new ArrayList<>();


    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withResolverStyle(ResolverStyle.STRICT);

    public Alerta_Boleto_Banco(Titulos titulo) {
        this.titulo = titulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_boleto_banco, container, false);

        initViews(v);
        return v;
    }

    private void initViews(View v) {

        txtcliente=v.findViewById(R.id.txtcliente);
        txtend=v.findViewById(R.id.txtend);
        txtnum=v.findViewById(R.id.txtnum);
        txtcep=v.findViewById(R.id.txtcep);
        txtcompl=v.findViewById(R.id.txtcompl);
        txtbarras=v.findViewById(R.id.txtbarras);
        txtinstall=v.findViewById(R.id.txtinstall);
        edtvalor=v.findViewById(R.id.edtvalor);
        txtvcto=v.findViewById(R.id.txtvcto);
        txtcpf=v.findViewById(R.id.txtcpf);
        imgcode=v.findViewById(R.id.imgcode);
        imgrefresh=v.findViewById(R.id.imgrefresh);
        ttbanco=v.findViewById(R.id.ttbanco);
        txtbanco=v.findViewById(R.id.txtbco);
        txtconta=v.findViewById(R.id.txtconta);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        clgerabol=v.findViewById(R.id.clgerabol);
        edtvalor.addTextChangedListener(  new MaskMoney(edtvalor));

        this.setCancelable(false);

        homeViewModel = Home.homeViewModel;
        todostitulos= Home.baseTitulos;


        txtbanco.setText(titulo.getNomecobtit());
        txtconta.setText(titulo.getContacobtit());
        txtcliente.setText(titulo.getNomeclientetit());
        txtinstall.setText(titulo.getInstalltit());

        Clientes cli = new Clientes();
        for(int i=0;i<Home.baseClientes.size();i++){
            if(Home.baseClientes.get(i).getKeycli().equals(titulo.getKeyclientetit())){
                cli=Home.baseClientes.get(i);
            }
        }

        txtend.setText(cli.getEndereco());
        txtnum.setText(cli.getNumero());
        txtcep.setText(cli.getCep());
        txtcompl.setText(cli.getComplemento());
        txtcpf.setText(cli.getCpf());

        edtvalor.setText(titulo.getValortit());

        Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dataenvio = zdt.toLocalDate();

        String scomp = dataenvio.format(fmt);
        txtvcto.setText(scomp);

        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
                ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
                LocalDate dataenvio = zdt.toLocalDate();
                String scomp = dataenvio.format(fmt);
                txtvcto.setText(scomp);
                edtvalor.setText(titulo.getValortit());
                txtbarras.setText("");
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
                String YEAR = String.valueOf(year);
                String MONTH =String.format("%02d", new Object[] { monthOfYear });
                String DAY = String.format("%02d", new Object[] { dayOfMonth });
                String date =  DAY+ "-" +MONTH+ "-" +YEAR;
                txtvcto.setText(date);
            }};

        txtvcto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();
            }
        });





        imgcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
//                if(txtbarras.getText().toString().equals("")) {
                    IntentIntegrator.forSupportFragment(Alerta_Boleto_Banco.this)
                            .initiateScan();
//                }else{
//                    checacode(txtbarras.getText().toString().trim());
//                }
            }
        });








        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(),(short) 20);

                String nbarra = txtbarras.getText().toString().trim();
                if(!nbarra.equals("") && nbarra!=null){
                    for(int i=0;i<Home.baseTitulos.size();i++) {
                        if (nbarra.equals(Home.baseTitulos.get(i).getBarrastit())) {
                                util.showSnackAsk(clgerabol,"Já existe Título com esse código de barras.");
                                txtbarras.setText(titulo.getBarrastit());
                                return;
                        }
                    }
                }

                long LONGDATAVCTO=0;
                try {
                    String str_date= txtvcto.getText().toString();
                    SimpleDateFormat formatter ;
                    Date date2 ;
                    formatter = new SimpleDateFormat("dd-MM-yyyy");
                    date2 = (Date) formatter.parse(str_date);
                    LONGDATAVCTO=date2.getTime();
                } catch (Exception e) {
                    System.out.println("Exception :"+e);
                }
                edtvalor.clearFocus();
                txtbarras.clearFocus();
                VALOR=edtvalor.getText().toString().trim();
                VCTO=txtvcto.getText().toString();
                BARRAS=txtbarras.getText().toString();

                if(VALOR.equals("") || VALOR.equals("0,00")){
                    util.showSnackError(clgerabol,"Informe Valor do Título.");
                    return;
                }else if(LONGDATAVCTO==0){
                    util.showSnackError(clgerabol,"Verifique data de vencimento.");
                    return;
                }else{
                    titulo.setValortit(VALOR);
                    titulo.setBarrastit(BARRAS);
                    titulo.setVencimentotit(LONGDATAVCTO);
                    titulo.setGeradobanco(1);

                    FirebaseFirestore.getInstance().collection("titulos")
                            .document(titulo.getKeytit())
                            .set(titulo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        util.showSnackOk(clgerabol, "Título salvo com sucesso.");
                                        homeViewModel.setBolgerabancos(true);
                                        getDialog().dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("verfire", e.toString());
                                }
                            });

                }

            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                getDialog().dismiss();
            }
        });

        clgerabol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                edtvalor.clearFocus();
                txtbarras.clearFocus();
                hideSoftKeyboard();
            }
        });
    }


    private void checacode(String msg){

        boolean tem=false;

        if(!msg.equals("Scan Failure")) {

            if(msg!="" && msg!=null) {
                Log.i("temtit","tem 1 " + tem + "tds " + todostitulos.size());
                for (int i = 0; i < todostitulos.size(); i++) {
                    if (todostitulos.get(i).getBarrastit().equals(msg)) {
                        Log.i("temtit",i + " tem i " + tem);
                            tem=true;
                        break;
                    }

                }
            }
        }
        Log.i("temtit","tem 2 " + tem);
        if(tem) {
            util.showSnackAsk(clgerabol, "Já existe um título com esse código de barras.");
            txtbarras.setText("");
            txtbarras.clearFocus();
        }else{
            txtbarras.setText(msg);
            txtbarras.clearFocus();
        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            if(result!=null){
                if(result.getContents()!=null){
                    checacode(result.getContents());
                }else{
                    Log.i("scanview","Scan Failure");
                    util.showSnackAsk(clgerabol, "Erro ao scanear cógigo.");
                }
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
    }

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
