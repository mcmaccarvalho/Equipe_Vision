package com.ponto.ideal.solucoes.equipe_vision.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private StorageReference storageReference;

    EditText etapelido;
    Button btsalvar,btcancelar;
    ConstraintLayout clperfil;
    ImageView imgperfil,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9,avat10,avat11,avat12,avat13,avat14,avat15;
    TextView txtselavatxgal;
    private ProgressBar mprogressBar;
    private int idavatar=-1;
    private int avat[] = new int[15];

    private Usuarios usulog;
    private String CAD_KEYUSU;

    private int origem;
    private String CAD_NOME;

    boolean doubleBackToExitPressedOnce=false;
    boolean blok=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        initViews();
    }

    private void initViews() {
        clperfil = findViewById(R.id.clperfil);
        btsalvar = findViewById(R.id.btsalvar);
        btcancelar = findViewById(R.id.btcancelar);
        etapelido = findViewById(R.id.etapelido);
        imgperfil = findViewById(R.id.imgperfil);
        txtselavatxgal = findViewById(R.id.txtselavatxgal);
        mprogressBar = findViewById(R.id.mprogressBar);


        mAuth= ConfiguracaoFirebase.getFirebaseAuth();
        CAD_KEYUSU = mAuth.getUid();


        avat1        = findViewById(R.id.avat1  );
        avat2        = findViewById(R.id.avat2  );
        avat3        = findViewById(R.id.avat3  );
        avat4        = findViewById(R.id.avat4  );
        avat5        = findViewById(R.id.avat5   );
        avat6        = findViewById(R.id.avat6    );
        avat7        = findViewById(R.id.avat7    );
        avat8        = findViewById(R.id.avat8    );
        avat9        = findViewById(R.id.avat9    );
        avat10        =findViewById(R.id.avat10     );
        avat11        =findViewById(R.id.avat11    );
        avat12       = findViewById(R.id.avat12  );
        avat13        =findViewById(R.id.avat13     );
        avat14        =findViewById(R.id.avat14    );
        avat15        =findViewById(R.id.avat15   );


        avat1  .setOnClickListener(this);
        avat2  .setOnClickListener(this);
        avat3  .setOnClickListener(this);
        avat4  .setOnClickListener(this);
        avat5  .setOnClickListener(this);
        avat6    .setOnClickListener(this);
        avat7    .setOnClickListener(this);
        avat8    .setOnClickListener(this);
        avat9    .setOnClickListener(this);
        avat10   .setOnClickListener(this);
        avat11   .setOnClickListener(this);
        avat12   .setOnClickListener(this);
        avat13   .setOnClickListener(this);
        avat14   .setOnClickListener(this);
        avat15   .setOnClickListener(this);


        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usulog.getStatus()==10 || usulog.getStatus()==20){
                    util.showmessage(Perfil.this,"Não é possível cancelar o primeiro Login. Efetue as alteraçoes e clique em salvar");
                    return;
                }

                btcancelar.setClickable(true);
                btcancelar.setEnabled(true);
                btsalvar.setClickable(true);
                Intent intent = new Intent(Perfil.this, MainActivity.class);
                startActivity(intent);
                finish();;

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
        avat[5] =   R.drawable.avatar_15;
        avat[6] =   R.drawable.avatar_20;
        avat[7] =   R.drawable.avatar_1;
        avat[8] =   R.drawable.avatar_8;
        avat[9] =   R.drawable.avatar_14;
        avat[10] =  R.drawable.ic_baseline_account_box_24;
        avat[11] =   R.drawable.ic_wifi_24;
        avat[12] =   R.drawable.ic_build_24;
        avat[13] =   R.drawable.ic_contactless_24;
        avat[14] =  R.drawable.ic_menu_gallery;


        clperfil.setFocusable(true);

        clperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        final String uid = mAuth.getCurrentUser().getUid();
        FirebaseFirestore. getInstance () . collection ( "/usuarios" )
                .document ( uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            usulog=task.getResult().toObject(Usuarios.class);
                            carregaDados(usulog);
                        }else{
                            carregaDados(null);
                        }
                    }
                });

    }

    private void carregaDados(Usuarios usulog) {


        if(usulog!=null) {

            etapelido.setText(usulog.getApelidousu());

            FirebaseStorage storage = FirebaseStorage.getInstance();

            final StorageReference storageReference = storage.getReferenceFromUrl("gs://equipe-vision-5cb37.appspot.com/fotoPerfilUsuario/"+mAuth.getUid());
            final int heigth = 300;
            final int width = 300;

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(200, 200).centerCrop().into(imgperfil);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }




    }


    public void salvanovovisitante(){

        util.vibratePhone(Perfil.this, (short) 20);

        etapelido.setText(etapelido.getText().toString().toUpperCase().trim());
        final String napel = etapelido.getText().toString().toUpperCase().trim();

        if(napel.equals(null)|| napel.equals("") || napel.equals("VISION")){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clperfil,"Insira um Apelido para o usuário.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }else if(idavatar==-1 && (usulog.getStatus()==10 || usulog.getStatus()==20)){
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showSnackError(clperfil,"Selecione um Avatar.");
            btcancelar.setClickable(true);
            btcancelar.setEnabled(true);
            btsalvar.setClickable(true);
            btsalvar.setEnabled(true);
            return;
        }

        storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        StorageReference montaImagemReferencia = storageReference.child(util.saveurl(CAD_KEYUSU));

        String url = storageReference+ "fotoPerfilUsuario/"
                + FirebaseAuth.getInstance().getUid();

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

                salvaUsu(napel);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
          //      util.showSnackOk(clcad_visitante, "Problemas ao salvar foto.");
            }
        });

        //util.saveImage(getContext(), bitmap, CAD_KEYUSU);



    }

    private void salvaUsu(String napel){

        final Usuarios anonimo = new Usuarios();
        anonimo.setKeyusu(CAD_KEYUSU);
        anonimo.setApelidousu(napel);
        anonimo.setTimestamp(usulog.getTimestamp());
        anonimo.setImagemusuario(CAD_KEYUSU);
        anonimo.setOnline(0);
        anonimo.setNomeusu(usulog.getNomeusu());
        anonimo.setEmailusu(mAuth.getCurrentUser().getEmail());
        anonimo.setUsupermicao(usulog.getUsupermicao());

        switch (usulog.getStatus()){
            case 10:anonimo.setStatus(100);break;
            case 20:anonimo.setStatus(200);break;
            default:anonimo.setStatus(usulog.getStatus());
        }

        FirebaseFirestore.getInstance().collection("usuarios")
                .document(mAuth.getUid())
                .set(anonimo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        iniciar();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showmessage(Perfil.this,"Problemas ao salvar usuário. Informe Administrador");
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
        util.showmessage(Perfil.this,"Usuário salvo com sucesso.");
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void hideSoftKeyboard() {
        etapelido.setText(etapelido.getText().toString().toUpperCase());
        etapelido.clearFocus();
        try {
            View windowToken = Perfil.this.getWindow().getDecorView().getRootView();
            InputMethodManager imm = (InputMethodManager) Perfil.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {

        }

    }

    @Override
    public void onBackPressed() {

        if (!blok) {
            if (doubleBackToExitPressedOnce) {

                if(usulog.getStatus()==10 || usulog.getStatus()==20){
                    util.showmessage(Perfil.this,"Obrigado por utilizar O Equipe Vision.");
                    mAuth.signOut();
                    finish();
                }else {

                    Intent intent = new Intent(Perfil.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(Perfil.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else{
            Toast.makeText(Perfil.this, "Aguarde o final do processo.", Toast.LENGTH_SHORT).show();
        }

    }

}