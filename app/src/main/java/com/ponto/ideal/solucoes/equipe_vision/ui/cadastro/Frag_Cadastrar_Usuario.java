package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.Login;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.io.ByteArrayOutputStream;


public class Frag_Cadastrar_Usuario extends Fragment {

    private EditText edtuid,edtnome,edtemail;
    private RadioGroup radiogroup;
    private RadioButton rbadmin,rboperador;
    private Button btcad, btcancel,btalterar;
    private ProgressBar mprogressBar;
    private ConstraintLayout clcadusu;
    private TextView textView22;

    public Frag_Cadastrar_Usuario() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frag__cadastrar__usuario, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        radiogroup = v.findViewById(R.id.radiogroup);
        rbadmin = v.findViewById(R.id.rbadmin);
        rboperador = v.findViewById(R.id.rboperador);
        edtemail = v.findViewById(R.id.edtemail);
        edtnome = v.findViewById(R.id.edtnome);
        edtuid = v.findViewById(R.id.edtuid);
        btcad = v.findViewById(R.id.btcad);
        btcancel = v.findViewById(R.id.btcancel);
        btalterar = v.findViewById(R.id.btalterar);
        mprogressBar = v.findViewById(R.id.mprogressBar);
        clcadusu = v.findViewById(R.id.clcadusu);

        textView22 = v.findViewById(R.id.textView22);

        textView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtuid.setText("3adlY48B9SNcZOJsRtn63AlvhbH2");
            }
        });

        clcadusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    edtnome.setText(edtnome.getText().toString().toUpperCase().trim());
                }
            }
        });

        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtemail.setText(edtemail.getText().toString().toLowerCase().trim());
                }
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_frag_Cadastrar_Usuario_to_home);
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edtuid.getText().toString().trim().equals("")){
                    util.showSnackError(clcadusu,"Insira UID do usuário");
                    return;
                }else if(edtemail.getText().toString().equals("")){
                    util.showSnackError(clcadusu,"Insira email do usuário");
                    return;
                }else if(edtnome.getText().toString().equals("")) {
                    util.showSnackError(clcadusu, "Insira um nome para o usuário");
                    return;
                }else if(!rbadmin.isChecked() && !rboperador.isChecked()){
                    util.showSnackError(clcadusu, "Selecione Adinistrador ou Operador");
                    return;
                }else{

                    final String uid = edtuid.getText().toString().trim();
                    final String nome = edtnome.getText().toString().toUpperCase().trim();
                    final String email = edtemail.getText().toString().toLowerCase().trim();

                    showProgressBar(true);
                    bloqueiaTD(true);

                    Usuarios anonimo = new Usuarios();

                    anonimo.setKeyusu(uid);
                    anonimo.setApelidousu("Vision");
                    anonimo.setTimestamp(System.currentTimeMillis());
                    anonimo.setImagemusuario(uid);
                    anonimo.setOnline(0);
                    anonimo.setNomeusu(nome);
                    anonimo.setEmailusu(email);
                    if(rbadmin.isChecked()){
                        anonimo.setStatus(10);
                        anonimo.setUsupermicao("111111111111111");
                    }else {
                        anonimo.setStatus(20);
                        anonimo.setUsupermicao("102201200010100");
                    }


                    FirebaseFirestore.getInstance().collection("usuarios")
                            .document(uid)
                            .set(anonimo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    salvaImagem(uid);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showProgressBar(false);
                                    Log.i("verfire", e.toString());
                                }
                            });

                }
            }
        });

    }

    private void salvaImagem(String uid) {


        StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(uid));

        final Bitmap bitmap = util.drawableToBitmap(getResources().getDrawable(R.drawable.ic_person_azul_24));
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        final byte[] data = byteArray.toByteArray();

        UploadTask uploadTask = montaImagemReferencia.putBytes(data);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                util.showSnackOk(clcadusu,"Usuário cadastardo e liberado para acessar Equipe Vision");
                MainActivity.navController.navigate(R.id.action_frag_Cadastrar_Usuario_to_home);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressBar(false);
                util.showSnackOk(clcadusu, "Problemas ao salvar foto.");
            }
        });
    }


    private void showProgressBar(boolean b) {
        if(b){
            mprogressBar.setVisibility(View.VISIBLE);
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void bloqueiaTD(boolean b) {
        btcancel.setClickable(!b);
        btcancel.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        btalterar.setClickable(!b);
        btalterar.setEnabled(!b);
        edtnome.setClickable(!b);
        edtnome.setEnabled(!b);
        edtemail.setClickable(!b);
        edtemail.setEnabled(!b);
        edtuid.setClickable(!b);
        edtuid.setEnabled(!b);
        MainActivity.blobk=b;
    }

    private void hideSoftKeyboard() {
        edtnome.setText(edtnome.getText().toString().toLowerCase());
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}