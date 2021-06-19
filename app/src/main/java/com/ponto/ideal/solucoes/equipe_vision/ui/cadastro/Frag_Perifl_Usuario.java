package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Half;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Usuários;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;


public class Frag_Perifl_Usuario extends Fragment {


    private CheckBox cbfat,cbgermen,cbstatcli,cblink,cbdesp,cbgertit,cblisttit,cbgerartit,
          cbbxtit,cbcad,cbconf,cbset,cbperf,cbcadusu,cbperfusu;
    private RadioGroup rgtipo,rgstatcli,rglink,rglisttit,rggerartit,rgbxtit;
    private RadioButton rbadmin,rboperador,rbcustom,rbstatcliall,rbstatcliown,rblinkall,rblinkown,rblisttitall,rblisttitown,
            rbgerartitall,rbgerartitown,rbbxtitall,rbbxtitown;
    private Button btcad,btcancel;
    private Spinner spusu;
    private ConstraintLayout clperm;
    private ProgressBar mprogressBar;

    private String n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15;
    private String Sadmim = "111111111111111";
    private String Soperador = "102201200010100";
    private String Scustom = "000000000000000";

    private FirebaseAuth mAuth;

    private ArrayList<Usuarios> usuarios = new ArrayList<>();
    private Adapter_Usuários adapter_usuários ;
    private Usuarios usuAlt = new Usuarios();


    public Frag_Perifl_Usuario() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_frag__perifl__usuario, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {

        mAuth=FirebaseAuth.getInstance();

        cbfat     = v.findViewById(R.id.cbfat           ) ;
        cbgermen  = v.findViewById(R.id.cbgermen        )    ;
        cbstatcli = v.findViewById(R.id.cbstatcli       )     ;
        cblink    = v.findViewById(R.id.cblink          )  ;
        cbdesp    = v.findViewById(R.id.cbdesp          )  ;
        cbgertit  = v.findViewById(R.id.cbgertit        )    ;
        cblisttit = v.findViewById(R.id.cblisttit       )     ;
        cbgerartit= v.findViewById(R.id.cbgerartit      )      ;
        cbbxtit   = v.findViewById(R.id.cbbxtit         )   ;
        cbcad     = v.findViewById(R.id.cbcad           ) ;
        cbconf    = v.findViewById(R.id.cbconf          )  ;
        cbset     = v.findViewById(R.id.cbset           ) ;
        cbperf    = v.findViewById(R.id.cbperf          )  ;
        cbcadusu  = v.findViewById(R.id.cbcadusu        )    ;
        cbperfusu = v.findViewById(R.id.cbperfusu       )     ;

        rgtipo     = v.findViewById(R.id.rgtipo           ) ;
        rgstatcli    = v.findViewById(R.id.rgstatcli          )  ;
        rglink     = v.findViewById(R.id.rglink           ) ;
        rglisttit    = v.findViewById(R.id.rglisttit          )  ;
        rggerartit  = v.findViewById(R.id.rggerartit        )    ;
        rgbxtit = v.findViewById(R.id.rgbxtit       )     ;

        rbadmin  = v.findViewById(R.id.rbadmin        )    ;
        rboperador = v.findViewById(R.id.rboperador       )     ;
        rbcustom    = v.findViewById(R.id.rbcustom          )  ;
        rbstatcliall    = v.findViewById(R.id.rbstatcliall          )  ;
        rbstatcliown  = v.findViewById(R.id.rbstatcliown        )    ;
        rblinkall = v.findViewById(R.id.rblinkall       )     ;
        rblinkown= v.findViewById(R.id.rblinkown      )      ;
        rblisttitall   = v.findViewById(R.id.rblisttitall         )   ;
        rblisttitown     = v.findViewById(R.id.rblisttitown           ) ;
        rbgerartitall    = v.findViewById(R.id.rbgerartitall          )  ;
        rbgerartitown     = v.findViewById(R.id.rbgerartitown           ) ;
        rbbxtitall    = v.findViewById(R.id.rbbxtitall          )  ;
        rbbxtitown  = v.findViewById(R.id.rbbxtitown        )    ;

        btcad    = v.findViewById(R.id.btcad          )  ;
        btcancel  = v.findViewById(R.id.btcancel        )    ;
        spusu  = v.findViewById(R.id.spusu        )    ;
        clperm  = v.findViewById(R.id.clperm        )    ;
        mprogressBar = v.findViewById(R.id.mprogressBar);

        Usuarios uss = new Usuarios();
        uss.setNomeusu("Selecione");
        uss.setEmailusu("");
        uss.setUsupermicao("000000000000000");
        usuarios.add(uss);
        FirebaseFirestore. getInstance () . collection ( "/usuarios" )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Usuarios nusu = new Usuarios();
                                nusu=document.toObject(Usuarios.class);
                                if(!nusu.getKeyusu().equals(mAuth.getUid()))usuarios.add(nusu);
                            }
                            carregaSpinner();
                        } else {

                        }
                    }
                });





        spusu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);

                if(position==0){
                    usuAlt=null;
                    marcaChecks("000000000000000");
                }else {
                    usuAlt=usuarios.get(position);
                    marcaChecks(usuarios.get(position).getUsupermicao());
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaPermicao();
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_frag_Perifl_Usuario_to_home);
            }
        });

        rbadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcaChecks(Sadmim);
            }
        });
        rboperador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcaChecks(Soperador);
            }
        });
        rbcustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcaChecks(Scustom);
            }
        });

        rbstatcliall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbstatcli.setChecked(true);
                checatd();
            }
        });

        rbstatcliown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbstatcli.setChecked(true);
                checatd();
            }
        });

        rblinkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cblink.setChecked(true);
                checatd();
            }
        });
        rblinkown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cblink.setChecked(true);
                checatd();
            }
        });

        rblisttitall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cblisttit.setChecked(true);
                checatd();
            }
        });

        rblisttitown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cblisttit.setChecked(true);
                checatd();
            }
        });

        rbgerartitall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbgerartit.setChecked(true);
                checatd();
            }
        });


        rbgerartitown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbgerartit.setChecked(true);
                checatd();
            }
        });



        rbbxtitall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbbxtit.setChecked(true);
                checatd();
            }
        });


        rbbxtitown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbbxtit.setChecked(true);
                checatd();
            }
        });

        cbcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checatd();
            }
        });

        cbfat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbfat.isChecked()){
                    cbgermen.setChecked(false);
                    cbstatcli.setChecked(false);
                    cblink.setChecked(false);
                    cbdesp.setChecked(false);
                    rgstatcli.clearCheck();
                    rglink.clearCheck();
                    checatd();
                }
            }
        });
        cbstatcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbstatcli.isChecked()){
                    rgstatcli.clearCheck();
                }
                checacbfat();
            }
        });

        cblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cblink.isChecked()){
                    rglink.clearCheck();
                }
                checacbfat();
            }
        });
        cbdesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbfat();
            }
        });
        cbgermen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbfat();
            }
        });





        cbgertit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbgertit.isChecked()){
                    cblisttit.setChecked(false);
                    cbgerartit.setChecked(false);
                    cbbxtit.setChecked(false);
                    rglisttit.clearCheck();
                    rggerartit.clearCheck();
                    rgbxtit.clearCheck();
                    checatd();
                }
            }
        });
        cblisttit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cblisttit.isChecked()){
                    rglisttit.clearCheck();
                }
                checacbgertit();
            }
        });

        cbgerartit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbgerartit.isChecked()){
                    rggerartit.clearCheck();
                }
                checacbgertit();
            }
        });
        cbbxtit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbbxtit.isChecked()){
                    rgbxtit.clearCheck();
                }
                checacbgertit();
            }
        });

        cbconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbconf.isChecked()){
                    cbset.setChecked(false);
                    cbperf.setChecked(false);
                    cbcadusu.setChecked(false);
                    cbperfusu.setChecked(false);
                    checatd();
                }
            }
        });

        cbset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbconf();
            }
        });

        cbperf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbconf();
            }
        });
        cbcadusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbconf();
            }
        });
        cbperfusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checacbconf();
            }
        });
    }

    private void carregaSpinner() {
        adapter_usuários = new Adapter_Usuários(getContext(), usuarios);
        spusu.setAdapter(adapter_usuários);

    }

    private void checatd() {
        if(cbfat.isChecked()){ n1="1"; }else{ n1="0";}
        if(cbgermen.isChecked()){ n2="1"; }else{ n2="0";}
        if(rbstatcliall.isChecked()){ n3="1";}else if(rbstatcliown.isChecked()){ n3="2";}else{n3="0";}
        if(rblinkall.isChecked()){ n4="1"; }else if(rblinkown.isChecked()){ n4="2";}else{n4="0";}
        if(cbdesp.isChecked()){ n5="1"; }else{ n5="0";}
        if(cbgertit.isChecked()){ n6="1"; }else{ n6="0";}
        if(rblisttitall.isChecked()){ n7="1";}else if(rblisttitown.isChecked()){ n7="2";}else{n7="0";}
        if(rbgerartitall.isChecked()){ n8="1";}else if(rbgerartitown.isChecked()){ n8="2";}else{n8="0";}
        if(rbbxtitall.isChecked()){ n9="1";}else if(rbbxtitown.isChecked()){ n9="2";}else{n9="0";}
        if(cbcad.isChecked()){ n10="1"; }else{ n10="0";}
        if(cbconf.isChecked()){ n11="1"; }else{ n11="0";}
        if(cbset.isChecked()){ n12="1"; }else{ n12="0";}
        if(cbperf.isChecked()){ n13="1"; }else{ n13="0";}
        if(cbcadusu.isChecked()){ n14="1"; }else{ n14="0";}
        if(cbperfusu.isChecked()){ n15="1"; }else{ n15="0";}

        if(cbstatcli.isChecked() && !rbstatcliown.isChecked() && !rbstatcliall.isChecked())n3="4";
        if(cblink.isChecked() && !rblinkown.isChecked() && !rblinkall.isChecked())n4="4";
        if(cblisttit.isChecked() && !rblisttitown.isChecked() && !rblisttitall.isChecked())n5="4";
        if(cbgerartit.isChecked() && !rbgerartitown.isChecked() && !rbgerartitall.isChecked())n6="4";
        if(cbbxtit.isChecked() && !rbbxtitown.isChecked() && !rbbxtitall.isChecked())n7="4";

        String snew = n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15;
        if(snew.equals(Soperador))rboperador.setChecked(true);
        if(snew.equals(Sadmim))rbadmin.setChecked(true);
        if(!snew.equals(Soperador) && !snew.equals(Sadmim))rbcustom.setChecked(true);

    }

    private void atualizaPermicao(){

        if(usuAlt==null || usuAlt.getNomeusu().equals("Selecione")) {
            util.showSnackError(clperm, "Selecione usuario para alterar");
            return;
        }else if(!cbfat.isChecked()) {
            util.showSnackError(clperm, "Ao menos o item Faturamento/Status Cliente/Owner é obrigatório");
            return;
        }else if(cbstatcli.isChecked() && !rbstatcliall.isChecked() && !rbstatcliown.isChecked()) {
            util.showSnackError(clperm, "Selecione Todos/Owner em Status Cliente");
            return;
        }else if(cblink.isChecked() && !rblinkall.isChecked() && !rblinkown.isChecked()) {
            util.showSnackError(clperm, "Selecione Todos/Owner em Faturamento Link");
            return;
        }else if(cblisttit.isChecked() && !rblisttitall.isChecked() && !rblisttitown.isChecked()) {
            util.showSnackError(clperm, "Selecione Todos/Owner em Listar Títulos");
            return;
        }else if(cbgerartit.isChecked() && !rbgerartitall.isChecked() && !rbgerartitown.isChecked()) {
            util.showSnackError(clperm, "Selecione Todos/Owner em Gerar Título Banco");
            return;
        }else if(cbbxtit.isChecked() && !rbbxtitall.isChecked() && !rbbxtitown.isChecked()) {
            util.showSnackError(clperm, "Selecione Todos/Owner em Baixar Títulos");
            return;
        }else{
            if(cbfat.isChecked()){ n1="1"; }else{ n1="0";}
            if(cbgermen.isChecked()){ n2="1"; }else{ n2="0";}
            if(rbstatcliall.isChecked()){ n3="1";}else if(rbstatcliown.isChecked()){ n3="2";}else{n3="0";}
            if(rblinkall.isChecked()){ n4="1"; }else if(rblinkown.isChecked()){ n4="2";}else{n4="0";}
            if(cbdesp.isChecked()){ n5="1"; }else{ n5="0";}
            if(cbgertit.isChecked()){ n6="1"; }else{ n6="0";}
            if(rblisttitall.isChecked()){ n7="1";}else if(rblisttitown.isChecked()){ n7="2";}else{n7="0";}
            if(rbgerartitall.isChecked()){ n8="1";}else if(rbgerartitown.isChecked()){ n8="2";}else{n8="0";}
            if(rbbxtitall.isChecked()){ n9="1";}else if(rbbxtitown.isChecked()){ n9="2";}else{n9="0";}
            if(cbcad.isChecked()){ n10="1"; }else{ n10="0";}
            if(cbconf.isChecked()){ n11="1"; }else{ n11="0";}
            if(cbset.isChecked()){ n12="1"; }else{ n12="0";}
            if(cbperf.isChecked()){ n13="1"; }else{ n13="0";}
            if(cbcadusu.isChecked()){ n14="1"; }else{ n14="0";}
            if(cbperfusu.isChecked()){ n15="1"; }else{ n15="0";}

            String snew = n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15;
            String tt = "";
            if(snew.equals(Soperador))tt="Operador";
            if(snew.equals(Sadmim))tt="Admnistrador";
            if(!snew.equals(Soperador) && !snew.equals(Sadmim))tt="Operador(custom)";

            showProgressBar(true);
            salvaPermicao(snew,tt);
        }
    }

    private void salvaPermicao(String snew,String tipo) {

        if(usuAlt!=null){

            usuAlt.setUsupermicao(snew);


            switch (usuAlt.getStatus()){

                case 10:
                    if(!tipo.equals("Administrador")){
                        usuAlt.setStatus(20);
                    }
                    break;
                case 20:
                    if(!tipo.equals("Operador")){
                        usuAlt.setStatus(10);
                    }
                    break;
                case 100:
                    if(!tipo.equals("Administrador")){
                        usuAlt.setStatus(200);
                    }
                    break;
                case 200:
                    if(!tipo.equals("Operador")){
                        usuAlt.setStatus(100);
                    }
                    break;
            }

            FirebaseFirestore.getInstance().collection("usuarios")
                    .document(usuAlt.getKeyusu())
                    .set(usuAlt)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            MainActivity.navController.navigate(R.id.action_frag_Perifl_Usuario_to_home);

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

        util.showSnackOk(clperm, "Cadastro atualizado com permição de " + tipo);

    }

    private void checacbfat() {
        if(!cbgermen.isChecked() && !cbstatcli.isChecked() && !cblink.isChecked() && !cbdesp.isChecked()){
            cbfat.setChecked(false);
        }else {
            cbfat.setChecked(true);
        }

        checatd();
    }

    private void checacbgertit() {
        if(!cblisttit.isChecked() && !cbgerartit.isChecked() && !cbbxtit.isChecked() ){
            cbgertit.setChecked(false);
        }else{
            cbgertit.setChecked(true);
        }
        checatd();
    }
    private void checacbconf() {
        if(!cbset.isChecked() && !cbperf.isChecked() && !cbperfusu.isChecked() && !cbcadusu.isChecked()){
            cbconf.setChecked(false);
        }else{
            cbconf.setChecked(true);
        }
        checatd();
    }
    /*
    Lista de Códigos:
    1- Faturamento                   valores = 0 - 1
    11 - Gerar Mensalidades          valores = 0 - 1
    12 - Status Cliente              valores = 0 - 1 - 2
    13 - Faturamento Link            valores = 0 - 1 - 2
    14 - Despesas                    valores = 0 - 1
    2 - Gerenciar Titulos            valores = 0 - 1
    21 - listar Títulos              valores = 0 - 1 - 2
    22 - Gerar Boleto Banco          valores = 0 - 1 - 2
    23 - Baixar Títulos              valores = 0 - 1 - 2
    3 - Cadastros                    valores = 0 - 1
    4 - Configurar                   valores = 0 - 1
    41 - Settings                    valores = 0 - 1
    42 - Perfil                      valores = 0 - 1
    43 - Cadastro Usuário            valores = 0 - 1
    44 - Perfil Usuario              valores = 0 - 1
    Administrador = 111111111111111
    Operador = 102201200010100
     */

    private void marcaChecks(String tipousu){

        n1  = tipousu.substring(0,1);
        n2  = tipousu.substring(1,2);
        n3  = tipousu.substring(2,3);
        n4  = tipousu.substring(3,4);
        n5  = tipousu.substring(4,5);
        n6  = tipousu.substring(5,6);
        n7  = tipousu.substring(6,7);
        n8  = tipousu.substring(7,8);
        n9  = tipousu.substring(8,9);
        n10 = tipousu.substring(9,10);
        n11 = tipousu.substring(10,11);
        n12 = tipousu.substring(11,12);
        n13 = tipousu.substring(12,13);
        n14 = tipousu.substring(13,14);
        n15 = tipousu.substring(14,15);

        if(n1.equals("") || n1.equals("0")){
            cbfat.setChecked(false);
            cbgermen.setChecked(false);
            cbstatcli.setChecked(false);
            cblink.setChecked(false);
            cbdesp.setChecked(false);
            rgstatcli.clearCheck();
            rglink.clearCheck();
        }else{
            cbfat.setChecked(true);
            if(n2.equals("") || n2.equals("0")) {
                cbgermen.setChecked(false);
            }else{
                cbgermen.setChecked(true);
            }

            switch (n3){
                case "1":
                    cbstatcli.setChecked(true);
                    rbstatcliall.setChecked(true);
                    break;
                case "2":
                    cbstatcli.setChecked(true);
                    rbstatcliown.setChecked(true);
                    break;
                default:
                    cbstatcli.setChecked(false);
                    rgstatcli.clearCheck();
            }

            switch (n4){
                case "1":
                    cblink.setChecked(true);
                    rblinkall.setChecked(true);
                    break;
                case "2":
                    cblink.setChecked(true);
                    rblinkown.setChecked(true);
                    break;
                default:
                    cblink.setChecked(false);
                    rglink.clearCheck();
            }

            if(n5.equals("") || n5.equals("0")) {
                cbdesp.setChecked(false);
            }else{
                cbdesp.setChecked(true);
            }
        }


        if(n6.equals("") || n6.equals("0")){
            cbgertit.setChecked(false);
            cblisttit.setChecked(false);
            cbgerartit.setChecked(false);
            cbbxtit.setChecked(false);
            rglisttit.clearCheck();
            rggerartit.clearCheck();
            rgbxtit.clearCheck();
        }else{
            cbgertit.setChecked(true);

            switch (n7){
                case "1":
                    cblisttit.setChecked(true);
                    rblisttitall.setChecked(true);
                    break;
                case "2":
                    cblisttit.setChecked(true);
                    rblisttitown.setChecked(true);
                    break;
                default:
                    cblisttit.setChecked(false);
                    rglisttit.clearCheck();
            }

            switch (n8){
                case "1":
                    cbgerartit.setChecked(true);
                    rbgerartitall.setChecked(true);
                    break;
                case "2":
                    cbgerartit.setChecked(true);
                    rbgerartitown.setChecked(true);
                    break;
                default:
                    cbgerartit.setChecked(false);
                    rggerartit.clearCheck();
            }

            switch (n9){
                case "1":
                    cbbxtit.setChecked(true);
                    rbbxtitall.setChecked(true);
                    break;
                case "2":
                    cbbxtit.setChecked(true);
                    rbbxtitown.setChecked(true);
                    break;
                default:
                    cbbxtit.setChecked(false);
                    rgbxtit.clearCheck();
            }


        }

        if(n10.equals("") || n10.equals("0")) {
            cbcad.setChecked(false);
        }else{
            cbcad.setChecked(true);
        }

        if(n11.equals("") || n11.equals("0")) {
            cbconf.setChecked(false);
        }else{
            cbconf.setChecked(true);
        }

        if(n12.equals("") || n12.equals("0")) {
            cbset.setChecked(false);
        }else{
            cbset.setChecked(true);
        }

        if(n13.equals("") || n13.equals("0")) {
            cbperf.setChecked(false);
        }else{
            cbperf.setChecked(true);
        }

        if(n14.equals("") || n14.equals("0")) {
            cbcadusu.setChecked(false);
        }else{
            cbcadusu.setChecked(true);
        }

        if(n15.equals("") || n15.equals("0")) {
            cbperfusu.setChecked(false);
        }else{
            cbperfusu.setChecked(true);
        }
        if(tipousu.equals(Soperador))rboperador.setChecked(true);
        if(tipousu.equals(Sadmim))rbadmin.setChecked(true);
        if(!tipousu.equals(Soperador) && !tipousu.equals(Sadmim))rbcustom.setChecked(true);

    }
    private void showProgressBar(boolean b) {
        if(b){
            mprogressBar.setVisibility(View.VISIBLE);
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }
}