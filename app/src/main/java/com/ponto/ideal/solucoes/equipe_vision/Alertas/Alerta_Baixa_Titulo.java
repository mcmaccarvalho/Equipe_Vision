package com.ponto.ideal.solucoes.equipe_vision.Alertas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Alerta_Baixa_Titulo extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtcliente, txtinstall,txtvcto,txtpgto,txtbarras,txtconta,txtbanco,ttbanco,txtdesc;
    private EditText edtvalor;
    private ImageView imgrefresh;
    Button btcad,btcancel;
    private HomeViewModel homeViewModel;
    private ConstraintLayout clboleto;
    private Titulos titulo;
    String  VALOR,VCTO,BARRAS;
    int tipocob;
    private long LONGDATAPGTO,LONGDATAVCTO;
    private LocalDate dataenvio;
    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private int dataaltera =0;

    public Alerta_Baixa_Titulo(Titulos titulo) {
        this.titulo = titulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_baixa_titulo, container, false);
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

        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        clboleto=v.findViewById(R.id.clboleto);
        edtvalor.addTextChangedListener(  new MaskMoney(edtvalor));
        this.setCancelable(false);

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
        if(titulo.getBarrastit().equals("") || titulo.getBarrastit()==null ){
            txtbarras.setText("Não informado");
        }else{
            txtbarras.setText(titulo.getBarrastit());
        }
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

                Instant hj = Instant.ofEpochMilli(System.currentTimeMillis());
                ZonedDateTime zhj= hj.atZone(ZoneId.systemDefault());
                LocalDate datahj = zhj.toLocalDate();
                String shoje = datahj.format(fmt);
                txtpgto.setText(shoje);

                edtvalor.setText(titulo.getValortit());

            }
        });

        Instant vt = Instant.ofEpochMilli(titulo.getVencimentotit());
        Instant pt = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zvt = vt.atZone(ZoneId.systemDefault());
        ZonedDateTime zpt = pt.atZone(ZoneId.systemDefault());
        LocalDate datavcto = zvt.toLocalDate();
        LocalDate datapgto = zpt.toLocalDate();

        String svcto = datavcto.format(fmt);
        String spgto = datapgto.format(fmt);
        txtvcto.setText(svcto);
        txtpgto.setText(spgto);

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(),(short) 20);
                edtvalor.clearFocus();
                VALOR=edtvalor.getText().toString().trim();



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



                if(VALOR.equals("")|| VALOR.equals("0,00")) {
                    util.showSnackError(clboleto, "Informe Valor do Título.");
                    return;
                }else {
                    titulo.setValortit(VALOR);
                    titulo.setVencimentotit(LONGDATAVCTO);
                    titulo.setDatapgtotit(LONGDATAPGTO);
                    titulo.setStatustit(2);
                    titulo.setGeradobanco(1);
                    titulo.setSituacaotit(0);
                    titulo.setKeyusubaixa(HomeViewModel.getUsuario().getApelidousu());

                    FirebaseFirestore.getInstance().collection("titulos")
                            .document(titulo.getKeytit())
                            .set(titulo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        util.showSnackOk(clboleto, "Título salvo com sucesso.");
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

        clboleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(),(short) 20);
                edtvalor.clearFocus();
                hideSoftKeyboard();

            }
        });
    }


    private void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            edtvalor.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
