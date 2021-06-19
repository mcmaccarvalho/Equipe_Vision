package com.ponto.ideal.solucoes.equipe_vision.ui.logar;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.Login;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.io.InputStream;
import java.net.URL;

public class Frag_Logar extends Fragment {

    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private ObjectAnimator animator1;
    private Usuarios usuLog = new Usuarios();
    private String uidLog;



    private Button  btabrelog, btlogin, btesquecisenha, btcad,btvoltar;
    private ConstraintLayout cllogin;
    private EditText etemail, etsenha;
    private ImageView eyesenhablack;
    private ProgressBar mprogressBar;
    private CardView cvaviso,cvdados;
    private TextView txtaviso;


    private boolean olho1 = true;
    private boolean blokclick = false;

    public Frag_Logar() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__logar, container, false);
        initViews(v);
        return v;

    }

    private void initViews(View v) {

//        Login.ctrl =0;
//

        btcad = v.findViewById(R.id.btcad);
        btabrelog = v.findViewById(R.id.btabrelog);
        btlogin = v.findViewById(R.id.btlogin);
        btesquecisenha = v.findViewById(R.id.btesquecisenha);
        btvoltar = v.findViewById(R.id.btvoltar);
        etemail = v.findViewById(R.id.etemail);
        etsenha = v.findViewById(R.id.etsenha);
        txtaviso = v.findViewById(R.id.txtaviso);
        cvaviso = v.findViewById(R.id.cvaviso);
        cvdados = v.findViewById(R.id.cvdados);
        cllogin = v.findViewById(R.id.cllogin);

        eyesenhablack = v.findViewById(R.id.eyesenhablack);

        mprogressBar  = v.findViewById(R.id.mprogressBar);

        cllogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        animator1 = ObjectAnimator.ofFloat(cvdados, "translationX", 1500);
        animator1.setDuration(1);
        animator1.start();


        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blokclick) return;
                blokclick=true;
                limpacell(2);
            }
        });



        btabrelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blokclick) return;
                animator1.setDuration(250);
                animator1.reverse();
                blokclick=true;
            }
        });



        btvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blokclick)return;
                animator1 = ObjectAnimator.ofFloat(cvdados, "translationX", 1500);
                animator1.setDuration(250);
                animator1.start();
                blokclick=false;
            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blokclick=true;
                hideSoftKeyboard();
                Log.i("btclick","loga " + blokclick);
                limpacell(3);
            }
        });

        eyesenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (olho1) {
                    etsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    etsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                }
                olho1 = !olho1;
            }
        });

        etemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etemail.setSelection(0);
                }

            }
        });

        etsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                etsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
            }
        });
        btesquecisenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String emailAddress = etemail.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    util.showmessage(getContext(),"Acesse email enviado para o endereço " + emailAddress);
                                    Log.d("teste", "Email sent." + emailAddress);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("teste", "Exception. "+e+" " + emailAddress);
                    }
                });
            }
        });


    }
    public void limpacell(int tipo){

        mprogressBar.setVisibility(View.VISIBLE);

        String [] arrquivos = util.listafile(getContext());

        for (int i=0;i<arrquivos.length;i++){
            getContext().deleteFile(arrquivos[i]);
        }

        switch (tipo){
            case 2:
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Frag_Usu(2)).commit();
                break;
            case 3: Logar();break;

        }

    }

    public void Logar() {

        if (!etemail.getText().toString().equals("") && !etsenha.getText().toString().equals("")) {
            animator1 = ObjectAnimator.ofFloat(cvdados, "translationX", 1000);
            animator1.setDuration(250);
            animator1.start();
            validarLogin();
        } else {
            util.showSnackError(cllogin, "Preencha todos os campos");
        }
    }

    private void validarLogin() {
        Log.i("logando",  "entrou validar");
        mprogressBar.setVisibility(View.VISIBLE);
        autenticacao = FirebaseAuth.getInstance();
        final String uemail = etemail.getText().toString();
        final String usenha = etsenha.getText().toString();
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(uemail, usenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("retligas", "usuario logado " + autenticacao.getCurrentUser().getEmail() );
                    get_usuario();
                }else{
                    mprogressBar.setVisibility(View.INVISIBLE);
                    blokclick=false;
                    util.showSnackError(cllogin,"Usuario ou Senha Invalido");
                }

            }
        });
    }

    public void get_usuario(){
        txtaviso.setText("Carregando Usuario ...");
        cvaviso.setVisibility(View.VISIBLE);
        final String uid = autenticacao.getCurrentUser().getUid();
        FirebaseFirestore. getInstance () . collection ( "/usuarios" )
                .document ( uid)
                .get()
                .addOnSuccessListener ( new  OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public  void  onSuccess ( DocumentSnapshot  documentSnapshot ) {
                        usuLog = documentSnapshot . toObject ( Usuarios. class);
                        Log.i("retligas", ":perfil carregado " + usuLog.getNomeusu() );
                        checa_status_usuario();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("retligas", "e " + e.getMessage() + ": " + e );
            }
        });
    }

    public void checa_status_usuario() {

        Log.i("retligas", ":checa_status_usuario " );
        if(usuLog !=null){

            if(usuLog.getStatus()!=0){

                Usuarios_Controller uc=new Usuarios_Controller(getContext());
                uc.salvarusuario(usuLog);
                    util.showSnackError(cllogin,"Usuario ok");
                    Log.i("retligas", ":usuarios status ok " + usuLog.getStatus() );
                     fotousuario();

            }else{
                mprogressBar.setVisibility(View.INVISIBLE);
                cvaviso.setVisibility(View.INVISIBLE);
                util.showSnackError(cllogin,"Usuario não autorizado");
                mprogressBar.setVisibility(View.INVISIBLE);
                autenticacao = FirebaseAuth.getInstance();
                autenticacao.signOut();
            }
        }

    }

    public void fotousuario(){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl(util.loadurl(usuLog.getKeyusu()));

        final int heigth = 300;
        final int width = 300;

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("retligas", ":DownloadImage " + uri.toString());
                new DownloadImage().execute(uri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Drawable drawable = getResources().getDrawable(R.drawable.fotoblank);
                final Bitmap bitmap = util.drawableToBitmap(drawable);
                boolean foto;
                foto = util.saveImage(getContext(), bitmap, usuLog.getKeyusu());
                Log.i("retligas", ":DownloadImage Fotoblanc");
                if(foto)
                    inicia();
            }
        });
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {

            boolean sf = util.saveImage(getContext(),result, usuLog.getKeyusu());
            if(sf){
                inicia();

            }else{
                util.showSnackError(cllogin,"Problemas ao salvar perfil.\nEntre em contato com administrador");
            }
        }
    }

    private void inicia(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }


    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}