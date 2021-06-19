package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Settings;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.util.MaskMoney;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;

public class Frag_Settings extends Fragment {

    private CheckBox cbatraso,cbvlbol,cbadesao;
    private EditText edtadesao,edtvalor,edtatraso;
    private Button btcad,btcancel;
    private ScrollView svset;
    private ConstraintLayout clset;
    private ArrayList<Settings> arraySet = new ArrayList<>();
    private Settings settings;
    private static ImageView imgrefresh;
    private ProgressBar mprogressBar;

    public Frag_Settings() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__settings, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        cbatraso = v.findViewById(R.id.cbatraso);
        cbvlbol = v.findViewById(R.id.cbvlbol);
        cbadesao = v.findViewById(R.id.cbadesao);
        edtadesao = v.findViewById(R.id.edtadesao);
        edtvalor = v.findViewById(R.id.edtvalor);
        edtatraso = v.findViewById(R.id.edtatraso);
        btcad = v.findViewById(R.id.btcad);
        btcancel = v.findViewById(R.id.btcancel);
        svset = v.findViewById(R.id.svset);
        clset = v.findViewById(R.id.clset);
        imgrefresh = v.findViewById(R.id.imgrefresh);
        mprogressBar = v.findViewById(R.id.mprogressBar);
        arraySet= Home.baseSettings;

        edtvalor.addTextChangedListener(new MaskMoney(edtvalor));
        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregadefautlt();
            }
        });
        settings=Home.Settings;

        clset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                edtadesao.requestFocus();
                hideSoftKeyboard();
                limpaFoco();
            }
        });



         btcancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MainActivity.navController.navigate(R.id.action_frag_Settings_to_home);
             }
         });

         btcad.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(!cbatraso.isChecked() && !cbvlbol.isChecked() && !cbadesao.isChecked() ){
                     util.showSnackError(svset,"Selecione item para salvar.");
                     return;
                 }


                 String vladesao = edtadesao.getText().toString();
                 String vlminbol = edtvalor.getText().toString();
                 String vlatraso = edtatraso.getText().toString();

                 if(cbadesao.isChecked()){
                     if(vladesao.equals("") || vladesao==null){
                         util.showSnackError(svset,"Informe prazo de vcto Adesão.");
                         return;
                     }
                 }
                 if(cbvlbol.isChecked()){
                     if(vlminbol.equals("0,00") || vlminbol==null){
                         util.showSnackError(svset,"Informe Valor mínimo para gerar Boleto.");
                         return;
                     }
                 }
                 if(cbatraso.isChecked()){
                     if(vlatraso.equals("") || vlatraso==null){
                         util.showSnackError(svset,"Informe Máximo dias de Atraso.");
                         return;
                     }
                 }

                 mprogressBar.setVisibility(View.VISIBLE);
                 bloqueiaBT(true);

                 FirebaseFirestore db = FirebaseFirestore.getInstance();
                 WriteBatch batch = db.batch();
                 DocumentReference dr = db.collection("settings").document("settings");
                 if(cbadesao.isChecked()){
                     batch.update(dr, "pzo_vcto_adesao", vladesao);
                 }
                 if(cbvlbol.isChecked()){
                    batch.update(dr, "valor_min_boleto", vlminbol);
                 }
                 if(cbatraso.isChecked()){
                    batch.update(dr, "max_dias_atraso", vlatraso);
                 }

                 batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         mprogressBar.setVisibility(View.INVISIBLE);
                         util.showSnackOk(clset, "Cofiguração salva com sucesso.");
                         bloqueiaBT(false);
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                         util.showSnackOk(svset, "Problemas ao salvar Produto. Informe administrador.");
                         bloqueiaBT(false);
                     }
                 });

             }
         });

        carregadefautlt();
    }

    private void carregadefautlt(){

        edtatraso.setText(settings.getMax_dias_atraso());
        edtvalor.setText (settings.getValor_min_boleto());
        edtadesao.setText(settings.getPzo_vcto_adesao());
        cbvlbol.setChecked(false);
        cbatraso.setChecked(false);
        cbadesao.setChecked(false);
    }

    private  void bloqueiaBT ( boolean b){

        btcancel.setClickable(!b);
        btcancel.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        edtadesao.setEnabled(!b);
        edtadesao.setClickable(!b);
        edtatraso.setEnabled(!b);
        edtatraso.setClickable(!b);
        edtvalor.setEnabled(!b);
        edtvalor.setClickable(!b);
        cbadesao.setEnabled(!b);
        cbadesao.setClickable(!b);
        cbatraso.setEnabled(!b);
        cbatraso.setClickable(!b);
        cbvlbol.setClickable(!b);
        cbvlbol.setEnabled(!b);
        imgrefresh.setClickable(!b);
        imgrefresh.setEnabled(!b);
        MainActivity.blobk = b;
    }

    private void limpaFoco() {
        hideSoftKeyboard();
        edtadesao.clearFocus();
        edtvalor.clearFocus();
        edtatraso.clearFocus();
    }

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}