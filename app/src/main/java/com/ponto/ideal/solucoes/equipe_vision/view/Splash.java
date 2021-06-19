package com.ponto.ideal.solucoes.equipe_vision.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.CheckApp;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    public static final int APP_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE = 1;

    private ProgressBar mprogressBar;

    private ConstraintLayout clsplash;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);


        mprogressBar  = findViewById(R.id.mprogressBar);
        mprogressBar.setVisibility(View.VISIBLE);

        if (CheckApp.verificarGooglePlayServices(Splash.this)) {
            checar_permissoes();
        } else {
            mprogressBar.setVisibility(View.INVISIBLE);
            util.showmessage(getApplicationContext(), "Google Play Service não instalado");
        }
    }

    private void checar_permissoes() {

        if (Build.VERSION.SDK_INT < 23) {

            Log.i("permissoes <23", "premissões ok");
            carregausu();

        } else if (checkAndRequestPermissions()) {

            Log.i("permissoes 1>23", "premissões dadas");

            carregausu();

        } else {


            Log.i("permissoes 2>23", "sem premissões");
        }

    }

    public boolean checkAndRequestPermissions() {

        boolean retorno = true;

        List<String> permissoesnecessarias = new ArrayList<>();

        int permissaoWrite =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permissaoRead =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissaoCam = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissaoWrite != PackageManager.PERMISSION_GRANTED) {
            permissoesnecessarias.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissaoRead != PackageManager.PERMISSION_GRANTED) {
            permissoesnecessarias.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissaoCam != PackageManager.PERMISSION_GRANTED) {
            permissoesnecessarias.add(Manifest.permission.CAMERA);
        }



        if (!permissoesnecessarias.isEmpty()) {

            ActivityCompat.requestPermissions(this,
                    permissoesnecessarias.toArray(new String[permissoesnecessarias.size()]),
                    APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE);
            retorno = false;



        }

        return retorno;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case APP_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGEE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    carregausu();
                    Log.i("permissoes 1", "premissões liberadas");
                } else {
                    mprogressBar.setVisibility(View.INVISIBLE);

                }
            }

            case APP_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    carregausu();
                    Log.i("permissoes 2", "premissões liberadas");
                } else {
                    Log.i("permissoes 2", "premissões parciais");
                }
            }
        }
    }

    public void carregausu() {


        final int SPLASH_TIME_OUT = 5000;

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {



                    if (temusuarioLogado()) {

                        mprogressBar.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        mprogressBar.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                        finish();

                    }



            }
        }, SPLASH_TIME_OUT);


    }

    public Boolean temusuarioLogado() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        autenticacao = FirebaseAuth.getInstance();
        Log.i("autenticacao.getUid()",": " + autenticacao.getUid());
        if (user != null) {
            return true;
        } else {
            return false;
        }

    }
}