package com.ponto.ideal.solucoes.equipe_vision.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.ui.logar.Frag_Logar;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

public class Login extends AppCompatActivity {


    private EditText edtnome,edtsenha;
    private TextView txtesquecisenha;
    private Button btlogar;
    private ImageView eyesenhablack;
    private ConstraintLayout cllogin;
    private ProgressBar mprogressBar;
    boolean doubleBackToExitPressedOnce=false;
    private FirebaseAuth mAuth;
    Context context;
    private HomeViewModel homeViewModel;

    private String TAG = "LoginVision";
    private boolean olho1=true;
    public static boolean blok=false;
    private Usuarios usulog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle(Html.fromHtml("<font color=\"#3D77C7\">" +"Login"+ "</font>"));

        initViews();

    }

    private void initViews() {

        edtnome = findViewById(R.id.edtnome);
        edtsenha = findViewById(R.id.edtsenha);
        txtesquecisenha = findViewById(R.id.txtesquecisenha);
        btlogar = findViewById(R.id.btlogar);
        eyesenhablack = findViewById(R.id.eyesenhablack);
        cllogin = findViewById(R.id.cllogin);
        mprogressBar = findViewById(R.id.mprogressBar);

        eyesenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (olho1) {
                    edtsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    edtsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                }
                olho1 = !olho1;
            }
        });

        cllogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtnome.setSelection(0);
                }else{
                    edtnome.setText(edtnome.getText().toString().toLowerCase());
                }
            }
        });

        edtsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                } else {

                }
            }
        });
        txtesquecisenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String emailAddress = edtnome.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent." + emailAddress);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Exception. "+e+" " + emailAddress);
                    }
                });
            }
        });

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uemail = edtnome.getText().toString();
                String usenha = edtsenha.getText().toString();

                if(uemail.equals("") || uemail.isEmpty() || uemail.equals(null)){
                    util.showSnackError(cllogin,"Informe email para Logim");
                    return;
                }else if(usenha.equals("") || usenha.isEmpty() || usenha.equals(null)){
                    util.showSnackError(cllogin,"Informe senha para Logim");
                    return;
                }else {
                    bloqueiaTD(true);
                    showProgressBar(true);
                    edtnome.setText(edtnome.getText().toString().toLowerCase());
                    validarLogin();
                }

            }
        });
    }

    private void validarLogin() {
        mAuth = FirebaseAuth.getInstance();
        final String uemail = edtnome.getText().toString();
        final String usenha = edtsenha.getText().toString();
        mAuth = ConfiguracaoFirebase.getFirebaseAuth();
        mAuth.signInWithEmailAndPassword(uemail, usenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    get_usuario();
                }else{
                    util.showSnackError(cllogin,"Usuario ou Senha Invalido");
                    bloqueiaTD(false);
                    showProgressBar(false);
                }

            }
        });
    }

    public void get_usuario(){

        final String uid = mAuth.getCurrentUser().getUid();
        FirebaseFirestore. getInstance () . collection ( "/usuarios" )
                .document ( uid)
                .get()
                .addOnSuccessListener ( new  OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public  void  onSuccess ( DocumentSnapshot  documentSnapshot ) {
                        if(documentSnapshot!=null) {
                            Log.i("retligas", "usulog " + documentSnapshot.getData() );
                            usulog = documentSnapshot.toObject(Usuarios.class);
                        }else {
                            usulog=null;
                            Log.i("retligas", "usulog null" );
                        }
                        checaStatusUsu(usulog);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("retligas", "e " + e.getMessage() + ": " + e );
            }
        });
    }

    private void checaStatusUsu(Usuarios usulog) {

        if(usulog==null){
            util.showmessage(Login.this,"Usuário não cadastrado. Entre em contato com Administrador.");
            mAuth.signOut();
            finish();
        }else{
            homeViewModel.setUsuario(usulog);
            if(usulog.getStatus()==10 || usulog.getStatus()==20){
                Intent intent2 = new Intent(Login.this, Perfil.class);
                startActivity(intent2);
                finish();
            }else{
                Intent intent2 = new Intent(Login.this, MainActivity.class);
                startActivity(intent2);
                finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        Log.i("bloqueia"," passou onBackPressed: " + blok );
        if (!blok) {
            if (doubleBackToExitPressedOnce) {
                util.showmessage(Login.this, "Obrigado por utilizar o Equipe Vision.");
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(Login.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    private void showProgressBar(boolean b) {
        if(b){
            mprogressBar.setVisibility(View.VISIBLE);
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void bloqueiaTD(boolean b) {
        Log.i("bloqueia"," passou bloqeeia: " + blok + " b: " + b);
        btlogar.setClickable(!b);
        btlogar.setEnabled(!b);
        edtnome.setClickable(!b);
        edtnome.setEnabled(!b);
        edtsenha.setClickable(!b);
        edtsenha.setEnabled(!b);
        txtesquecisenha.setClickable(!b);
        txtesquecisenha.setEnabled(!b);
        blok=b;
    }

    private void hideSoftKeyboard() {
        edtnome.setText(edtnome.getText().toString().toLowerCase());
        if (Login.this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Login.this.getSystemService(Login.this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), 0);
        }
    }



}