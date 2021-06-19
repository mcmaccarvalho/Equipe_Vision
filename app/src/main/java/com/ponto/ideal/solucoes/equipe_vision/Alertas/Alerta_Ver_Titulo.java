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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ponto.ideal.solucoes.equipe_vision.R;
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

public class Alerta_Ver_Titulo extends DialogFragment {

    private static final String TAG = "Resultado Jogo";

    private TextView txtcliente, txtinstall,txtvcto,txtpgto,txtconta,txtbanco,ttbanco,txtdesc,txtusu,txtvalor;
    Button btcad;
    private HomeViewModel homeViewModel;
    private ConstraintLayout cledbol;
    private Titulos titulo;

    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withResolverStyle(ResolverStyle.STRICT);


    public Alerta_Ver_Titulo(Titulos titulo) {
        this.titulo = titulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alerta_ver_titulo, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        txtinstall=v.findViewById(R.id.txtinstall);
        txtvalor=v.findViewById(R.id.txtvalor);
        txtvcto=v.findViewById(R.id.txtvcto);
        txtpgto=v.findViewById(R.id.txtpgto);
        txtdesc=v.findViewById(R.id.txtdesc);
        ttbanco=v.findViewById(R.id.ttbanco);
        txtbanco=v.findViewById(R.id.txtbco);
        txtconta=v.findViewById(R.id.txtconta);
        txtcliente=v.findViewById(R.id.txtcliente);
        txtusu=v.findViewById(R.id.txtusu);


        btcad=v.findViewById(R.id.btcad);

        cledbol=v.findViewById(R.id.cledbol);

        this.setCancelable(false);

        txtbanco.setText(titulo.getNomecobtit());
        txtconta.setText(titulo.getContacobtit());
        txtcliente.setText(titulo.getNomeclientetit());
        txtinstall.setText(titulo.getInstalltit());
        txtdesc.setText(titulo.getDesctit());
        switch (titulo.getTipocobtit()){
            case 1:ttbanco.setText("Banco");break;
            case 2:ttbanco.setText("Carteira");break;
            case 3:ttbanco.setText("Cartão");break;
        }

        txtvalor.setText(titulo.getValortit());


        Instant it = Instant.ofEpochMilli(titulo.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate datavcto = zdt.toLocalDate();
        txtvcto.setText(datavcto.format(fmt));

        it = Instant.ofEpochMilli(titulo.getDatapgtotit());
        zdt = it.atZone(ZoneId.systemDefault());
        datavcto = zdt.toLocalDate();
        txtpgto.setText(datavcto.format(fmt));

        if(titulo.getStatustit()==3){
            txtpgto.setText("CANCELADO");
            txtusu.setText(titulo.getKeyusucancel());
        }else{
            txtpgto.setText(datavcto.format(fmt));
            txtusu.setText(titulo.getKeyusubaixa());
        }

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                getDialog().dismiss();
            }
        });

    }

}
