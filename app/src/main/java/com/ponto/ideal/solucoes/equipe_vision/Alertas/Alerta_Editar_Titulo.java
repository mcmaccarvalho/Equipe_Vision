package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Baixa_Titulo;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.MaskMoney;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;

public class Alerta_Editar_Titulo extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtcliente, txtinstall,txtvcto,txtpgto,txtconta,txtbanco,ttbanco,txtdesc;
    private EditText txtbarras,edtvalor;
    private ImageView imgrefresh,imgcode;
    Button btcad,btcancel;
    private HomeViewModel homeViewModel;
    private ConstraintLayout cledbol;
    private Titulos titulo;
    private CheckBox cbcancelar,cbbaixar;
    String  VALOR,VCTO,BARRAS;
    int tipocob;
    private long LONGDATAVCTO;
    private long LONGDATAPGTO;
    private LocalDate dataenvio;
    private int dataaltera=0;
    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);


    public Alerta_Editar_Titulo(Titulos titulo) {
        this.titulo = titulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_editar_titulo, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        tipocob=titulo.getTipocobtit();

        homeViewModel = Home.homeViewModel;

        txtinstall=v.findViewById(R.id.txtinstall);
        edtvalor=v.findViewById(R.id.edtvalor);
        txtvcto=v.findViewById(R.id.txtvcto);
        txtpgto=v.findViewById(R.id.txtpgto);
        txtdesc=v.findViewById(R.id.txtdesc);
        ttbanco=v.findViewById(R.id.ttbanco);
        txtbanco=v.findViewById(R.id.txtbco);
        txtconta=v.findViewById(R.id.txtconta);
        txtcliente=v.findViewById(R.id.txtcliente);
        txtbarras=v.findViewById(R.id.txtbarras);
        imgrefresh=v.findViewById(R.id.imgrefresh);
        imgcode=v.findViewById(R.id.imgcode);
        cbbaixar=v.findViewById(R.id.cbbaixar);
        cbcancelar=v.findViewById(R.id.cbcancelar);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        cledbol=v.findViewById(R.id.cledbol);
        edtvalor.addTextChangedListener(  new MaskMoney(edtvalor));

        this.setCancelable(false);

        cbcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                if(cbcancelar.isChecked()){
                    txtpgto.setText("00-00-0000");
                    cbbaixar.setChecked(false);
                }
            }
        });

        cbbaixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                if(cbcancelar.isChecked()) {
                    cbcancelar.setChecked(false);
                }

                if(cbbaixar.isChecked()){
                    Instant hj = Instant.ofEpochMilli(System.currentTimeMillis());
                    ZonedDateTime zdthj = hj.atZone(ZoneId.systemDefault());
                    LocalDate hoje = zdthj.toLocalDate();
                    final String shoje = hoje.format(fmt);
                    txtpgto.setText(shoje);
                }else{
                    txtpgto.setText("00-00-0000");
                }
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
                switch (dataaltera){
                    case 1:
                        txtvcto.setText(date);
                        break;
                    case 2:
                        txtpgto.setText(date);
                        break;
                }
                dataaltera=0;
            }};

        txtvcto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                dataaltera=1;
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();
            }
        });

        txtpgto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                dataaltera=2;
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();
            }
        });


        txtbanco.setText(titulo.getNomecobtit());
        txtconta.setText(titulo.getContacobtit());
        txtcliente.setText(titulo.getNomeclientetit());
        txtinstall.setText(titulo.getInstalltit());
        txtbarras.setText(titulo.getBarrastit());
        txtdesc.setText(titulo.getDesctit());
        switch (titulo.getTipocobtit()){
            case 1:ttbanco.setText("Banco");break;
            case 2:ttbanco.setText("Carteira");break;
            case 3:ttbanco.setText("Cartão");break;
        }

        edtvalor.setText(titulo.getValortit());



        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
                ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
                LocalDate dataenvio = zdt.toLocalDate();
                String scomp = dataenvio.format(fmt);
                txtvcto.setText(scomp);
                txtpgto.setText("00-00-0000");
                edtvalor.setText(titulo.getValortit());
                txtbarras.setText(titulo.getBarrastit());
                cbcancelar.setChecked(false);
                cbbaixar.setChecked(false);

            }
        });

        Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate datavcto = zdt.toLocalDate();

        final String svcto = datavcto.format(fmt);

        txtvcto.setText(svcto);
        txtpgto.setText("00-00-0000");

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);

                String nbarra = txtbarras.getText().toString().trim();
                if(!nbarra.equals("") && nbarra!=null){
                    for(int i=0;i<Home.baseTitulos.size();i++) {
                        if (nbarra.equals(Home.baseTitulos.get(i).getBarrastit())) {
                            if (!Home.baseTitulos.get(i).getKeytit().equals(titulo.getKeytit())) {
                                util.showSnackAsk(cledbol,"Já existe Título com esse código de barras.");
                                txtbarras.setText(titulo.getBarrastit());
                                return;
                            }
                            break;
                        }
                    }
                }

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

                try {
                    String str_date= txtpgto.getText().toString();
                    SimpleDateFormat formatter ;
                    Date date2 ;
                    formatter = new SimpleDateFormat("dd-MM-yyyy");
                    date2 = (Date) formatter.parse(str_date);
                    LONGDATAPGTO=date2.getTime();
                } catch (Exception e) {
                    System.out.println("Exception :"+e);
                }

                edtvalor.clearFocus();
                VALOR=edtvalor.getText().toString().trim();

                if(VALOR.equals("") || VALOR.equals("0,00")){
                    util.showSnackError(cledbol,"informe Valor do Título.");
                    return;
                }else {
                    titulo.setValortit(VALOR);
                    titulo.setVencimentotit(LONGDATAVCTO);
                    if(cbbaixar.isChecked()) {
                        titulo.setDatapgtotit(LONGDATAPGTO);
                        titulo.setStatustit(2);
                        titulo.setGeradobanco(1);
                        titulo.setSituacaotit(0);
                        titulo.setKeyusubaixa(HomeViewModel.getUsuario().getApelidousu());
                    }else if(cbcancelar.isChecked()){
                        titulo.setGeradobanco(1);
                        titulo.setSituacaotit(0);
                        titulo.setStatustit(3);
                        titulo.setKeyusucancel(HomeViewModel.getUsuario().getApelidousu());
                    }
                    titulo.setBarrastit(txtbarras.getText().toString().trim());

                    FirebaseFirestore.getInstance().collection("titulos")
                            .document(titulo.getKeytit())
                            .set(titulo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        util.showSnackOk(cledbol, "Título salvo com sucesso.");
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

        cledbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                edtvalor.clearFocus();
                txtbarras.clearFocus();

            }
        });

        imgcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(txtbarras.getText().toString().equals("")) {
                    IntentIntegrator.forSupportFragment(Alerta_Editar_Titulo.this)
                            .initiateScan();
//                }else{
//                    checacode(txtbarras.getText().toString().trim());
//                }
            }
        });


    }

    private void checacode(String msg){

        if(!msg.equals("Scan Failure")) {

            boolean tem=false;

            if(!msg.equals("Scan Failure")) {

                if(msg!="" && msg!=null) {
                    for (int i = 0; i < Home.baseTitulos.size(); i++) {
                        if (Home.baseTitulos.get(i).getBarrastit().equals(msg)) {
                            if(!Home.baseTitulos.get(i).getKeytit().equals(titulo.getKeytit())) {
                                tem = true;
                            }
                            break;
                        }

                    }
                }
            }
            if(tem) {
                util.showSnackAsk(cledbol, "Já existe um título com esse código de barras.");
                txtbarras.setText("");
                txtbarras.clearFocus();
            }else{
                txtbarras.setText(msg);
                txtbarras.clearFocus();
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
                util.showSnackAsk(cledbol, "Erro ao scanear cógigo.");
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
