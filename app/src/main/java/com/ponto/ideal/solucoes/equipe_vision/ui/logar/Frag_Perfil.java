package com.ponto.ideal.solucoes.equipe_vision.ui.logar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


public class Frag_Perfil extends Fragment implements View.OnClickListener {
    private FirebaseAuth autenticacao;
    private StorageReference storageReference;

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clcad_visitante;
    ImageView imgperfil,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,avat10,avat11,avat12,avat13,avat14,avat15;
    TextView txtcontnome,txtselavatxgal,txtgallery;
    private ProgressBar mprogressBar;
    private int idavatar=-1;
    private int avat[] = new int[15];

    private Usuarios usu;
    private String CAD_KEYUSU;

    private int origem;
    private String CAD_NOME;

    public Frag_Perfil(int origem,String nnome) {
        this.origem=origem;
        this.CAD_NOME=nnome;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag__perfil, container, false);

        clcad_visitante = view.findViewById(R.id.clcad_visitante);
        btsalvar = view.findViewById(R.id.btsalvar);
        btcancelar = view.findViewById(R.id.btcancelar);
        etapelido = view.findViewById(R.id.etapelido);
        imgperfil = view.findViewById(R.id.imgperfil);
        txtcontnome = view.findViewById(R.id.txtcontnome);
        txtselavatxgal = view.findViewById(R.id.txtselavatxgal);

        mprogressBar = view.findViewById(R.id.mprogressBar);


        autenticacao= ConfiguracaoFirebase.getFirebaseAuth();
        CAD_KEYUSU = autenticacao.getUid();

//        Login.ctrl=2;

        String apelido ="";

        for (int i = 0; i < CAD_NOME.length(); i++) {
            if (!CAD_NOME.substring(i,i+1).equals(" ")){
                apelido += CAD_NOME.substring(i,i+1);
            }else{
                break;
            }
        }

        etapelido.setText(apelido);

        etapelido.addTextChangedListener(textWatcher);
        etapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etapelido.setSelection(0);

                }
            }
        });

        avat1        = view.findViewById(R.id.avat1  );
        avat2        = view.findViewById(R.id.avat2  );
        avat3        = view.findViewById(R.id.avat3  );
        avat4        = view.findViewById(R.id.avat4  );
        avat5        = view.findViewById(R.id.avat5   );
        avat6        = view.findViewById(R.id.avat6    );
        avat7        = view.findViewById(R.id.avat7    );
        avat8        = view.findViewById(R.id.avat8    );
        avat9        = view.findViewById(R.id.avat9    );
        avat10        = view.findViewById(R.id.avat10     );
        avat11        = view.findViewById(R.id.avat11    );
        avat12       = view.findViewById(R.id.avat12  );
        avat13        = view.findViewById(R.id.avat13     );
        avat14        = view.findViewById(R.id.avat14    );
        avat15        = view.findViewById(R.id.avat15   );


        avat1  .setOnClickListener(this);
        avat2  .setOnClickListener(this);
        avat3  .setOnClickListener(this);
        avat4  .setOnClickListener(this);
        avat5  .setOnClickListener(this);
//        avat6    .setOnClickListener(this);
//        avat7    .setOnClickListener(this);
//        avat8    .setOnClickListener(this);
//        avat9    .setOnClickListener(this);
//        avat10   .setOnClickListener(this);
//        avat11   .setOnClickListener(this);
//        avat12   .setOnClickListener(this);
//        avat13   .setOnClickListener(this);
//        avat14   .setOnClickListener(this);
//        avat15   .setOnClickListener(this);


        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                btsalvar.setEnabled(true);

                AlertaAsk();

            }
        });

        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressBar.setVisibility(View.VISIBLE);
                btcancelar.setClickable(false);
                btcancelar.setEnabled(false);
                btsalvar.setClickable(false);
                btsalvar.setEnabled(false);
                salvanovovisitante();
            }
        });

        avat[0] =  R.drawable.admin1;
        avat[1] =  R.drawable.admin2;
        avat[2] =  R.drawable.fotoblank;
        avat[3] =   R.drawable.avatar_13;
        avat[4] =   R.drawable.avatar_6;
//        avat[5] =   R.drawable.avatar_15;
//        avat[6] =   R.drawable.avatar_20;
//        avat[7] =   R.drawable.avatar_1;
//        avat[8] =   R.drawable.avatar_8;
//        avat[9] =   R.drawable.avatar_14;
//        avat[10] =  R.drawable.ic_baseline_account_box_24;
//        avat[11] =   R.drawable.ic_wifi_24;
//        avat[12] =   R.drawable.ic_build_24;
//        avat[13] =   R.drawable.ic_contactless_24;
//        avat[14] =  R.drawable.ic_menu_gallery;


        clcad_visitante.setFocusable(true);

        clcad_visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        return view;
    }


    public void salvanovovisitante(){

        util.vibratePhone(getContext(), (short) 20);

        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
        String napel = etapelido.getText().toString().toUpperCase().trim();

        if(napel.equals(null)|| napel.equals("")){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clcad_visitante,"Insira um Nick para o usuário.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }else if(idavatar==-1){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clcad_visitante,"Selecione um Avatar.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }

        storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(CAD_KEYUSU));


        imgperfil.setDrawingCacheEnabled(true);
        imgperfil.buildDrawingCache();

        final Bitmap bitmap = imgperfil.getDrawingCache();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        final byte[] data = byteArray.toByteArray();

        UploadTask uploadTask = montaImagemReferencia.putBytes(data);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
            }
        });

        util.saveImage(getContext(), bitmap, CAD_KEYUSU);


        final Usuarios anonimo = new Usuarios();
        anonimo.setKeyusu(CAD_KEYUSU);
        anonimo.setApelidousu(napel);
        anonimo.setTimestamp(System.currentTimeMillis());
        anonimo.setImagemusuario(CAD_KEYUSU);
        anonimo.setOnline(0);

        if(origem==1){
            anonimo.setNomeusu(napel);
            anonimo.setEmailusu(autenticacao.getCurrentUser().getEmail());
            anonimo.setStatus(1);
        }else if(origem==2){
            anonimo.setNomeusu(napel);
            anonimo.setEmailusu(autenticacao.getCurrentUser().getEmail());
            anonimo.setStatus(1);
        }



        FirebaseFirestore.getInstance().collection("usuarios")
                .document(autenticacao.getUid())
                .set(anonimo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Usuarios_Controller uc = new Usuarios_Controller(getActivity().getBaseContext());
                        boolean dd = uc.salvarusuario(anonimo);
                        Log.i("cadastrousu"," dd: " + dd);
                        iniciar();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        Log.i("verfire", e.toString());
                    }
                });

    }


    @Override
    public void onClick(View v) {

        int iditem = v.getId();

        hideSoftKeyboard();
        switch (iditem){
            case R.id.avat1:imgperfil.setImageResource (avat [0])  ; idavatar =(avat [0])  ;       break;
            case R.id.avat2:imgperfil.setImageResource (avat [1])  ; idavatar =(avat [1])  ;       break;
            case R.id.avat3:imgperfil.setImageResource (avat [2])  ; idavatar =(avat [2])  ;       break;
            case R.id.avat4:imgperfil.setImageResource (avat [3])  ; idavatar =(avat [3])  ;       break;
            case R.id.avat5:imgperfil.setImageResource (avat [4])  ; idavatar =(avat [4])  ;       break;
            case R.id.avat6:imgperfil.setImageResource (avat [5])  ; idavatar =(avat [5])  ;       break;
            case R.id.avat7:imgperfil.setImageResource (avat [6])  ; idavatar =(avat [6])  ;       break;
            case R.id.avat8:imgperfil.setImageResource (avat [7])  ; idavatar =(avat [7])  ;       break;
            case R.id.avat9:imgperfil.setImageResource (avat [8])  ; idavatar =(avat [8])  ;       break;
            case R.id.avat10:imgperfil.setImageResource (avat[9])  ; idavatar =(avat[9])   ;       break;
            case R.id.avat11:imgperfil.setImageResource (avat[10 ])  ; idavatar =(avat[10]) ;       break;
            case R.id.avat12:imgperfil.setImageResource (avat[11 ])  ; idavatar =(avat[11]) ;       break;
            case R.id.avat13:imgperfil.setImageResource (avat[12 ])  ; idavatar =(avat[12]) ;       break;
            case R.id.avat14:imgperfil.setImageResource (avat[13 ])  ; idavatar =(avat[13]) ;       break;
            case R.id.avat15:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
                    break;


        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        final int heigth = 300;
        final int width = 300;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                Uri imagemSelecionada = data.getData();
                Picasso.get().load(imagemSelecionada.toString()).resize(width, heigth).centerCrop().into(imgperfil);
                idavatar=0;
            }
        }
    }

    public void iniciar(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void hideSoftKeyboard() {
        etapelido.setText(etapelido.getText().toString().toUpperCase());
        etapelido.clearFocus();
        try {
            View windowToken = getActivity().getWindow().getDecorView().getRootView();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {

        }

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence c, int start, int before, int count) {
            if (etapelido.hasFocus()) {
                txtcontnome.setText(c.length() + "/15");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

    public void AlertaAsk() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_ask);

        ImageView imgdialog = dialog.findViewById(R.id.imgdialog);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        TextView txtdbody = dialog.findViewById(R.id.txtbody);
        Button btsim = dialog.findViewById(R.id.btsim);
        Button btnao = dialog.findViewById(R.id.btnao);

        ConstraintLayout cldialog = dialog.findViewById(R.id.cldialog);

        imgdialog.setImageResource(R.drawable.ic_wifi_24);
        txttitle.setText("Tabela Campeonato");
        txtdbody.setText("Deseja cancelar a instalação do\nEquipe Vision/Internet ?");
        txtdbody.setGravity(Gravity.CENTER_HORIZONTAL);
        btsim.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_sobe_alerta));
        btnao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                btsalvar.setEnabled(true);
                dialog.dismiss();
            }
        });
        btsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.showmessage(getContext(), "Obrigado por utilizar o\nEquipe Vision/Internet.");
                FirebaseAuth auth = FirebaseAuth.getInstance();

                final String uuid = auth.getCurrentUser().getUid();
                FirebaseFirestore.getInstance().collection("/usuarios")
                        .document(uuid)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser().delete();
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        dialog.dismiss();
                        getActivity().finish();
                    }
                }, 2000);

            }
        });

        dialog.show();
    }
}
