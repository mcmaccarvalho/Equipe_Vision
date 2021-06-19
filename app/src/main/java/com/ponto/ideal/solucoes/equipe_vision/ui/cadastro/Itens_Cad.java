package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Link;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Type_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Altera_Mega_Install;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Inativar;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Adesao;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Type_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.Mask;
import com.ponto.ideal.solucoes.equipe_vision.util.MaskMoney;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Itens_Cad extends Fragment {


    private TextView ttvalor,tttipo,ttdesc;
    private static EditText edtnome,edtvalor,edtdesc,edtconta,edtcapmega,edtcepini,edtcepfim;
    private LinearLayout llnome,llspin,llconta,llvalor,llstatus,lldesc,llcep,llmega,llgeral,lldata,lllinkdesp;
    private static CheckBox cbativo;
    private static Spinner sptipo,splink;
    private static Button btcad,btcancel;
    private ConstraintLayout clcadedit;
    private static ImageView imgrefresh;
    private ProgressBar mprogressBar;
    private static TextView txtdata;

    private HomeViewModel homeViewModel;
//    public static ArrayList<String> nomeitem = new ArrayList<>();
//    public static ArrayList<String> keyitem = new ArrayList<>();

    private static ArrayList<Links> arrayLink = new ArrayList<>();
    private ArrayList<Tabela_Produtos> arrayProd = new ArrayList<>();
    private ArrayList<Type_Cobranca> arrayCob = new ArrayList<>();
    private ArrayList<Adesao> arrayAdesao = new ArrayList<>();
    private ArrayList<Setores> arraySetor = new ArrayList<>();

    public static Links oldlink;
    public static Tabela_Produtos oldprod;
    public static Cobrancas oldCob;
    public static Adesao oldAdesao;
    public static Setores oldsetor;
    public static Despesas olddesp;
    public static  String[] rprod;
    public static  String[] rdesp;
    private static boolean blockAdapt=false;
    private Adapter_Type_Cobranca adapter_cobranca;
    private Adapter_Link adapter_link;

    private String newnome;
    private String newdesc;
    private String newvalor;
    private String newconta;
    private String newcepini;
    private String newcepfin;
    private String newmega;
    private String keyitem;
    private long newdata=0;

    private String COBRANCA;
    private int TYPECOB;
    private int ctrlAlteraMega=0;
    public static int megaalt=0;

    private boolean bedit=false;

    private int retprod;
    public static String TAG = "caditem";

    private String LINK;

    static Resources res;

    ArrayList<String> stNitem = new ArrayList<>();
    private long tdata;

    public Itens_Cad() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_itens_cad, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        ttvalor     =v.findViewById(R.id.ttvalor);
        tttipo     =v.findViewById(R.id.tttipo);
        ttdesc     =v.findViewById(R.id.ttdesc);
        txtdata =v.findViewById(R.id.txtdata);
        edtcepini     =v.findViewById(R.id.edtcepini);
        edtcepfim     =v.findViewById(R.id.edtcepfim);
        edtnome     =v.findViewById(R.id.edtnome);
        edtvalor     =v.findViewById(R.id.edtvalor);
        edtdesc     =v.findViewById(R.id.edtdesc);
        edtconta     =v.findViewById(R.id.edtconta);
        edtcapmega     =v.findViewById(R.id.edtcapmega);
        llgeral     =v.findViewById(R.id.llgeral);
        llnome     =v.findViewById(R.id.llnome);
        llspin     =v.findViewById(R.id.llspin);
        llconta     =v.findViewById(R.id.llconta);
        llvalor     =v.findViewById(R.id.llvalor);
        llstatus     =v.findViewById(R.id.llstatus);
        lldesc     =v.findViewById(R.id.lldesc);
        llcep     =v.findViewById(R.id.llcep);
        llmega     =v.findViewById(R.id.llmega);
        lllinkdesp     =v.findViewById(R.id.lllinkdesp);
        lldata     =v.findViewById(R.id.lldata);
        cbativo     =v.findViewById(R.id.cbativo);
        sptipo     =v.findViewById(R.id.sptipo);
        splink     =v.findViewById(R.id.splink);
        btcad     =v.findViewById(R.id.btcad);
        btcancel     =v.findViewById(R.id.btcancel);
        clcadedit     =v.findViewById(R.id.clcadedit);
        imgrefresh     =v.findViewById(R.id.imgrefresh);
        mprogressBar   =v.findViewById(R.id.mprogressBar);

        edtvalor.addTextChangedListener(new MaskMoney(edtvalor));
        edtcepini.addTextChangedListener(Mask.insert(Mask.CEP_MASK, edtcepini));
        edtcepfim.addTextChangedListener(Mask.insert(Mask.CEP_MASK, edtcepfim));


        bedit=Itens_Lista.bedit;
        COBRANCA="";TYPECOB=0;

        homeViewModel = Home.homeViewModel;

        homeViewModel.getrRetinativar().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer ret) {
                if (ret == 0) {
                    bloqueiaBT(false);
                    carregaItem();
                    util.showSnackOk(clcadedit,"Alteração Cancelada");
                } else {

                    switch (Home.ctrlItem){
                        case 0:
                            util.showSnackOk(clcadedit,"Migrar para Link " + stNitem.get(ret));
                            migraLink(ret);
                            break;
                        case 1:
                            switch (oldprod.getTipoproduto()){
                                case "Planos Net":
                                    retprod=ret;
                                    megaPlano(ret);
                                    break;
                                case "TV":
                                    retprod=ret;
                                    migraTV(ret);
                                    break;
                                default:
                                    salvaProd();
                            }
                            break;
                        case 2:migraCob(ret);break;
                        case 4:migraSetor(ret);break;
                    }
                }
            }
        });

        homeViewModel.getAlteracap().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                migraPlano(retprod);
            }
        });

        Resources  res = getResources();
        rprod = res.getStringArray(R.array.tipo_prod);

        Resources  resd = getResources();
        rdesp = resd.getStringArray(R.array.tipo_desp);

        switch (Home.ctrlItem){
            case 0:
                llgeral.removeView(llspin);
                llgeral.removeView(llconta);
                llgeral.removeView(llcep);
                llgeral.removeView(lldesc);
                llstatus.removeView(lldata);
                llvalor.removeView(lllinkdesp);

                break;
            case 1:

                llgeral.removeView(llconta);
                llgeral.removeView(llcep);
                llgeral.removeView(llmega);
                llstatus.removeView(lldata);
                llvalor.removeView(lllinkdesp);
                break;
            case 2:
                llgeral.removeView(lldesc);
                llgeral.removeView(llcep);
                llgeral.removeView(llmega);
                llgeral.removeView(llvalor);
                llstatus.removeView(lldata);
                llvalor.removeView(lllinkdesp);
                break;
            case 3:
                llgeral.removeView(llconta);
                llgeral.removeView(llcep);
                llgeral.removeView(llmega);
                llstatus.removeView(lldata);
                llvalor.removeView(lllinkdesp);
                break;
            case 4:
                llgeral.removeView(llconta);
                llgeral.removeView(lldesc);
                llgeral.removeView(llmega);
                llgeral.removeView(llvalor);
                llgeral.removeView(llspin);
                llstatus.removeView(lldata);

                break;
            case 5:
                llgeral.removeView(llnome);
                llgeral.removeView(llcep);
                llgeral.removeView(llconta);
                llgeral.removeView(llmega);

                break;
        }

        splink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short)  20);
                if(position==0){
                    LINK="";
                }else if(position==1) {
                    LINK = "Geral";
                }else{
                    LINK=arrayLink.get(position).getKeylink();
                }
                splink.clearFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sptipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);

                   if(Home.ctrlItem==2 || Home.ctrlItem==3) {
                       if (position == 0) {
                           TYPECOB = position;
                           COBRANCA = "";
                       } else {
                           TYPECOB = arrayCob.get(position).getTipo_type_cob();
                           COBRANCA = arrayCob.get(position).getKey_type_cob();
                       }
                   }

                sptipo.clearFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


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
                String date1 =  DAY+ "-" +MONTH+ "-" +YEAR;
                txtdata.setText(date1);

            }};

        txtdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();

            }
        });


        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                bloqueiaBT(false);
                hideSoftKeyboard();
                Itens_Lista.bedit = false;
                edtnome.setText   ("");
                edtcepini.setText ("");
                edtcepfim.setText ("");
                edtvalor.setText  ("");
                edtcapmega.setText("");
                edtdesc.setText   ("");
                edtconta.setText  ("");
                cbativo.setChecked(true);
                cbativo.setText("Ativo/Inativo");
                sptipo.setSelection(0);
                sptipo.setClickable(true);
                sptipo.setEnabled(true);
                imgrefresh.setVisibility(View.INVISIBLE);
                imgrefresh.setClickable(false);
                imgrefresh.setEnabled(false);
                splink.setSelection(0);
                splink.setClickable(true);
                splink.setEnabled(true);
                txtdata.setClickable(true);
                txtdata.setEnabled(true);
                txtdata.setText("00-00-0000");
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        });

        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.showSnackOk(clcadedit,"Alerações descartadas\nDados originais");
                carregaItem();
            }
        });


        cbativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbativo.isChecked()){
                    cbativo.setText("Ativo");
                }else{
                    cbativo.setText("Inativo");
                }
            }
        });

        clcadedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                edtnome.requestFocus();
                hideSoftKeyboard();
                limpaFoco();
            }
        });


        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtnome.setText(edtnome.getText().toString().toUpperCase());
                } else {
                    util.vibratePhone(getContext(), (short) 20);
                }
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bedit=Itens_Lista.bedit;
                hideSoftKeyboard();
                util.vibratePhone(getContext(), (short) 20);
                edtnome.clearFocus();
                edtvalor.clearFocus();
                edtdesc.clearFocus();
                edtconta.clearFocus();
                edtcapmega.clearFocus();
                edtcepini.clearFocus();
                edtcepfim.clearFocus();

                newnome = edtnome.getText().toString().toUpperCase().trim();
                newdesc = edtdesc.getText().toString().toUpperCase().trim();
                newvalor = edtvalor.getText().toString();
                newconta = edtconta.getText().toString();
                newcepini = edtcepini.getText().toString();
                newcepfin = edtcepfim.getText().toString();
                newmega = edtcapmega.getText().toString();
                newdata=util.longdiamesano(txtdata.getText().toString());

                switch (Home.ctrlItem){
                    case 0:checaLink();break;
                    case 1:checaProd();break;
                    case 2:checaCob();break;
                    case 3:checaAdesao();break;
                    case 4:checaSetor();break;
                    case 5:checaDesp();break;
                }
            }
        });

        carregatipo();
    }

    private void limpaFoco() {
        hideSoftKeyboard();
        edtnome.clearFocus();
        edtvalor.clearFocus();
        edtdesc.clearFocus();
        edtconta.clearFocus();
        edtcapmega.clearFocus();
        edtcepini.clearFocus();
        edtcepfim.clearFocus();
    }


    public void checaLink(){



        Log.i(TAG,"Link + bedit " + bedit);
        if (bedit) {
            keyitem = oldlink.getKeylink();
            tdata = oldlink.getTimestamp();
        } else {
            tdata = System.currentTimeMillis();
            keyitem = UUID.randomUUID().toString();
        }

        boolean temnome = false;

        for (int i = 0; i < Home.baseLinks.size(); i++) {
            if (Home.baseLinks.get(i).getNomelink().equals(newnome)) {
                temnome = true;
                if (bedit == true && keyitem.equals(Home.baseLinks.get(i).getKeylink())) {
                    temnome = false;
                }
            }
        }


        if (newnome.equals("")) {
            util.showSnackError(clcadedit, "Digite um Nome para o Linnk.");
            return;
        } else if (temnome) {
            util.showSnackError(clcadedit, "Já existe um Link com este nome.");
            return;
        } else if (newmega.equals("")) {
            util.showSnackError(clcadedit, "informe Capacidade do Link.");
            return;
        } else if (newvalor.equals("0,00") || newvalor.equals("")) {
            util.showSnackError(clcadedit, "informe Valor do Link.");
            return;
        }else {
            bloqueiaBT(true);
            if(oldlink!=null && oldlink.getStatus()==1 && !cbativo.isChecked() && bedit){
                Log.i(TAG,"Link dados ini ok  editar inativar " + bedit);
                verificarUsoLink();
            }else {
                Log.i(TAG,"Link dados ini ok só editar " + bedit);
                salvaLink();
            }
        }

    }

    private void verificarUsoLink() {

        boolean tem = false;
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getLink().equals(oldlink.getKeylink())) {
                tem = true;
                break;
            }
        }

        if (tem) {
            Log.i(TAG, "verificarUsoLink tem uso " + tem);
            stNitem.clear();
            stNitem.add("Selecione");
            for (int i = 0; i < Home.baseLinks.size(); i++) {
                if (Home.baseLinks.get(i).getStatus() == 1
                        && !Home.baseLinks.get(i).getKeylink().equals(oldlink.getKeylink())) {
                    stNitem.add(Home.baseLinks.get(i).getNomelink());
                }
            }
            if (oldlink.getStatus() == 1) {
                Alerta_Inativar alerta_inativar = new Alerta_Inativar("Link", oldlink.getNomelink(), stNitem);
                alerta_inativar.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
            return;
        } else {
            Log.i(TAG, "verificarUsoLink  não tem uso " + tem);
            salvaLink();
        }


    }

    private void migraLink(Integer ret) {

        mprogressBar.setVisibility(View.VISIBLE);

        String kitem = "";

        for (int i = 0; i < Home.baseLinks.size(); i++) {
            if (Home.baseLinks.get(i).getNomelink().equals(stNitem.get(ret))) {
                kitem = Home.baseLinks.get(i).getKeylink();
            }
        }

        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getLink().equals(oldlink.getKeylink())) {
                Log.i(TAG, "Alterar istall  " + Home.baseInstall.get(i).getNuminstal());
            }
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getLink().equals(oldlink.getKeylink())) {
                DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                batch.update(dr, "link", kitem);
            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                salvaLink();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcadedit, "Problemas ao atualizar Instalações. Informe Administrador");
            }
        });
    }

    private void salvaLink() {

        mprogressBar.setVisibility(View.VISIBLE);

        Links ttt = new Links();
        ttt.setNomelink(newnome);
        ttt.setValorlink(newvalor);
        ttt.setKeylink(keyitem);
        ttt.setTimestamp(tdata);
        ttt.setCapacidadelink(newmega);
        if (cbativo.isChecked()) {
            ttt.setStatus(1);
        } else {
            ttt.setStatus(0);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("links").document(keyitem);
        batch.set(dr, ttt);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Link salvo com sucesso.");
                limpatela();
                Itens_Lista.bedit = false;
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Link. Informe administrador.");
                bloqueiaBT(false);
            }
        });

    }



    public void checaProd(){
        Log.i(TAG,"prod");
        if (bedit) {
            keyitem = oldprod.getKeyproduto();
        } else {
            keyitem = UUID.randomUUID().toString();
        }

        boolean temnome = false;

        for (int i = 0; i < Home.baseProdutos.size(); i++) {
            if (Home.baseProdutos.get(i).getNomeproduto().equals(newnome)) {
                temnome = true;
                if (bedit == true && keyitem.equals(Home.baseProdutos.get(i).getKeyproduto())) {
                    temnome = false;
                }
            }
        }

        Log.i(TAG,"Prod tem nome: " + temnome);
        if(newnome.equals("")){
            util.showSnackError(clcadedit,"Digite um Nome para o Produto.");
            return;
        }else if(temnome){
            util.showSnackError(clcadedit, "Já existe um Produto com este nome.");
            return;
        } else if(newvalor.equals("")){
            util.showSnackError(clcadedit,"informe Valor do Produto.");
            return;
        } else if(sptipo.getSelectedItemPosition()==0){
            util.showSnackError(clcadedit,"Selecione Tipo de Produto.");
            return;
        }else {

            bloqueiaBT(true);


            if(bedit) {
                switch (oldprod.getTipoproduto()) {
                    case "Planos Net":
                        if (oldprod.getStatusproduto() == 1 && !cbativo.isChecked() && bedit) {
                            Log.i(TAG, "Prod dados ini ok  editar inativar " + bedit);
                            verificarUsoPlano();
                        } else {
                            Log.i(TAG, "Prod dados ini ok só editar " + bedit);
                            salvaProd();
                        }
                        break;
                    case "TV":
                        if (oldprod.getStatusproduto() == 1 && !cbativo.isChecked() && bedit) {
                            Log.i(TAG, "Prod dados ini ok  editar inativar " + bedit);
                            verificarUsoTv();
                        } else {
                            Log.i(TAG, "Prod dados ini ok só editar " + bedit);
                            salvaProd();
                        }
                        break;
                    default:
                        salvaProd();
                }
            }else{
                salvaProd();
            }
        }

    }

    private void verificarUsoPlano() {

        boolean tem = false;
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getPlano().equals(oldprod.getKeyproduto())) {
                tem = true;
                break;
            }
        }
        if (tem) {
            Log.i(TAG, "verificarUsoPlano tem uso " + tem);
            stNitem.clear();
            stNitem.add("Selecione");
            for (int i = 0; i < Home.baseProdutos.size(); i++) {
                if (Home.baseProdutos.get(i).getStatusproduto() == 1){
                    if(Home.baseProdutos.get(i).getTipoproduto().equals(oldprod.getTipoproduto())
                    && !Home.baseProdutos.get(i).getKeyproduto().equals(oldprod.getKeyproduto())) {
                        stNitem.add(Home.baseProdutos.get(i).getNomeproduto());
                    }
                }
            }

            if (oldprod.getStatusproduto() == 1) {
                Alerta_Inativar alerta_inativar = new Alerta_Inativar("Produto", oldprod.getNomeproduto(), stNitem);
                alerta_inativar.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
            return;
        }else{
            Log.i(TAG, "verificarUsoPlano nao tem uso " + tem);
            salvaProd();
        }
    }

    private void megaPlano(Integer ret) {
        Alerta_Altera_Mega_Install alerta_altera_mega_install = new Alerta_Altera_Mega_Install(stNitem.get(ret));
        alerta_altera_mega_install.show(getActivity().getSupportFragmentManager(), "Dialogalt");
    }

    private void migraPlano(Integer ret) {


        mprogressBar.setVisibility(View.VISIBLE);
        String kitem = "";
        for (int i = 0; i < Home.baseProdutos.size(); i++) {
            if (Home.baseProdutos.get(i).getNomeproduto().equals(stNitem.get(ret).toString())) {
                Log.i(TAG, "nome " + Home.baseProdutos.get(i).getNomeproduto() + " key: " +Home.baseProdutos.get(i).getKeyproduto()
                );
                        kitem = Home.baseProdutos.get(i).getKeyproduto();
            }
        }
        Log.i(TAG, "migrarplano ret " + ret + " retprodp: " + retprod +
                " prod: " + stNitem.get(ret) + " kitem: " + kitem);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getPlano().equals(oldprod.getKeyproduto())) {

                if(megaalt>0) {
                    DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                    batch.update(dr, "plano", kitem,"megainstal",megaalt);
                }else{
                    DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                    batch.update(dr, "plano", kitem);
                }
            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                salvaProd();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcadedit, "Problemas ao atualizar Instalações. Informe Administrador");
            }
        });




    }

    private void verificarUsoTv() {

        boolean tem = false;
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getTv().equals(oldprod.getKeyproduto())) {
                tem = true;
                break;
            }
        }
        if (tem) {
            Log.i(TAG, "verificarUsotv tem uso " + tem);
            stNitem.clear();
            stNitem.add("Selecione");
            for (int i = 0; i < Home.baseProdutos.size(); i++) {
                if (Home.baseProdutos.get(i).getStatusproduto() == 1){
                    if(Home.baseProdutos.get(i).getTipoproduto().equals(oldprod.getTipoproduto())
                            && !Home.baseProdutos.get(i).getKeyproduto().equals(oldprod.getKeyproduto())) {
                        stNitem.add(Home.baseProdutos.get(i).getNomeproduto());
                    }
                }
            }

            if (oldprod.getStatusproduto() == 1) {
                Alerta_Inativar alerta_inativar = new Alerta_Inativar("Produto", oldprod.getNomeproduto(), stNitem);
                alerta_inativar.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
            return;
        }else{
            Log.i(TAG, "verificarUsoPlano nao tem uso " + tem);
            salvaProd();
        }
    }

    private void migraTV(Integer ret) {

        mprogressBar.setVisibility(View.VISIBLE);
        String kitem = "";
        for (int i = 0; i < Home.baseProdutos.size(); i++) {
            if (Home.baseProdutos.get(i).getNomeproduto().equals(stNitem.get(ret))) {
                kitem = Home.baseProdutos.get(i).getKeyproduto();
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getTv().equals(oldprod.getKeyproduto())) {

                    DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                    batch.update(dr, "tv", kitem);

            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                salvaProd();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcadedit, "Problemas ao atualizar Instalações. Informe Administrador");
            }
        });
    }

    private void salvaProd() {

        mprogressBar.setVisibility(View.VISIBLE);

        String newtipo = sptipo.getSelectedItem().toString();
        Tabela_Produtos ttt = new Tabela_Produtos();

        ttt.setNomeproduto(newnome);
        ttt.setValorproduto(newvalor);
        ttt.setKeyproduto(keyitem);
        ttt.setTipoproduto(newtipo);
        ttt.setDescricaoproduto(newdesc);

        if (cbativo.isChecked()) {
            ttt.setStatusproduto(1);
        } else {
            ttt.setStatusproduto(0);
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("produtos").document(keyitem);
        batch.set(dr, ttt);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Produto salvo com sucesso.");
                bloqueiaBT(false);
                limpatela();
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Produto. Informe administrador.");
                bloqueiaBT(false);
            }
        });


    }



    public void checaCob(){

        if (bedit) {
            keyitem = oldCob.getKey_cobranca();
        } else {
            keyitem = UUID.randomUUID().toString();
        }


        boolean temnome = false;

        for (int i = 0; i < Home.baseCobrancas.size(); i++) {
            if (Home.baseCobrancas.get(i).getNome_cobranca().equals(newnome)) {
                temnome = true;
                if (bedit == true && keyitem.equals(Home.baseCobrancas.get(i).getKey_cobranca())) {
                    temnome = false;
                }
            }
        }


        if(newnome.equals("")){
            util.showSnackError(clcadedit, "Digite um Nome para a Cobrança.");
            return;
        }else if(temnome) {
            util.showSnackError(clcadedit, "Já existe um Cobrança com este nome.");
            return;
        }else if(newconta.equals("")) {
            util.showSnackError(clcadedit, "Informe uma Conta para a Cobrança.");
            return;
        }else if(sptipo.getSelectedItemPosition()==0){
            util.showSnackError(clcadedit, "Selecione Tipo Cobrança.");
            return;
        }else {
            bloqueiaBT(true);
            if (bedit && oldCob!=null && oldCob.getStatu_cobranca() == 1 && !cbativo.isChecked() ) {
                Log.i(TAG, "Prod dados ini ok  editar inativar " + bedit);
                verificarUsoCob();
            } else {
                Log.i(TAG, "Prod dados ini ok só editar " + bedit);
                salvaCob();
            }

        }


    }

    private void verificarUsoCob() {

        boolean tem = false;
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getCobranca().equals(oldCob.getKey_cobranca())) {
                tem = true;
                break;
            }
        }
        if (tem) {
            stNitem.clear();
            stNitem.add("Selecione");
            for (int i = 0; i < Home.baseCobrancas.size(); i++) {
                if (Home.baseCobrancas.get(i).getStatu_cobranca() == 1
                        && !Home.baseCobrancas.get(i).getKey_cobranca().equals(oldCob.getKey_cobranca())) {
                    stNitem.add(Home.baseCobrancas.get(i).getNome_cobranca());
                }
            }

            if (oldCob.getStatu_cobranca() == 1) {
                Alerta_Inativar alerta_inativar = new Alerta_Inativar("Cobrança", oldCob.getNome_cobranca(), stNitem);
                alerta_inativar.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
            return;
        }else{
            salvaCob();
        }

    }

    private void migraCob(Integer ret) {

        mprogressBar.setVisibility(View.VISIBLE);

        String kitem = "";

        for (int i = 0; i < Home.baseCobrancas.size(); i++) {
            if (Home.baseCobrancas.get(i).getNome_cobranca().equals(stNitem.get(ret))) {
                kitem = Home.baseCobrancas.get(i).getKey_cobranca();
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getCobranca().equals(oldCob.getKey_cobranca())) {
                DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                batch.update(dr, "cobranca", kitem);
            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                salvaCob();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcadedit, "Problemas ao atualizar Instalações. Informe Administrador");
            }
        });
    }

    private void salvaCob() {

        Cobrancas tc = new Cobrancas();
        tc.setKey_cobranca(keyitem);
        tc.setNome_cobranca(newnome);
        tc.setConta_cobranca(newconta);
        tc.setTypecob_cobranca(COBRANCA);
        tc.setType_cob(TYPECOB);
        if (cbativo.isChecked()) {
            tc.setStatu_cobranca(1);
        } else {
            tc.setStatu_cobranca(0);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("cobrancas").document(keyitem);
        batch.set(dr, tc);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Cobrança salvo com sucesso.");
                limpatela();
                Itens_Lista.bedit = false;
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Cobrança. Informe administrador.");
                bloqueiaBT(false);
            }
        });

    }



    public void checaAdesao(){

        if (bedit) {
            keyitem = oldAdesao.getKey_adesao();
        } else {
            keyitem = UUID.randomUUID().toString();
        }


        boolean temnome = false;

        for (int i = 0; i < Home.baseAdesao.size(); i++) {
            if (Home.baseAdesao.get(i).getNome_adesao().equals(newnome)) {
                temnome = true;
                if (bedit == true && keyitem.equals(Home.baseAdesao.get(i).getKey_adesao())) {
                    temnome = false;
                }
            }
        }


        if(newnome.equals("")){
            util.showSnackError(clcadedit,"Digite um Nome para a Adesão.");
            return;
        }else if(temnome){
            util.showSnackError(clcadedit, "Já existe uma Adesão com este nome.");
            return;
        } else if(newvalor.equals("")){
            util.showSnackError(clcadedit,"informe Valor da Adesão.");
            return;
        } else if(sptipo.getSelectedItemPosition()==0){
            util.showSnackError(clcadedit,"Selecione Tipo de Cobrança.");
            return;
        }else {
            bloqueiaBT(true);
            salvaAdesao();
        }
    }

    private void salvaAdesao() {


        Adesao ttt = new Adesao();

        ttt.setNome_adesao(newnome);
        ttt.setValor_adesao(newvalor);
        ttt.setKey_adesao(keyitem);

        ttt.setDescricao_adesao(newdesc);
        if (cbativo.isChecked()) {
            ttt.setStatus_adesao(1);
        } else {
            ttt.setStatus_adesao(0);
        }

        ttt.setTipo_cob_adesao(COBRANCA);
        ttt.setType_cob(TYPECOB);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("adesao").document(keyitem);
        batch.set(dr, ttt);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Adesão salva com sucesso.");
                limpatela();
                Itens_Lista.bedit = false;
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Adesão. Informe administrador.");
                bloqueiaBT(false);
            }
        });

    }



    public void checaSetor(){

        if (bedit) {
            keyitem = oldsetor.getKeysetor();
        } else {
            keyitem = UUID.randomUUID().toString();
        }

        boolean temnome = false;

        for (int i = 0; i < Home.baseSetores.size(); i++) {
            if (Home.baseSetores.get(i).getNomesetor().equals(newnome)) {
                temnome = true;
                if (bedit == true && keyitem.equals(Home.baseSetores.get(i).getKeysetor())) {
                    temnome = false;
                }
            }
        }

        if(newnome.equals("")){
            util.showSnackError(clcadedit,"Digite um Nome para o Setor.");
            return;
        }else if(temnome){
            util.showSnackError(clcadedit, "Já existe um Setor com este nome.");
            return;
        }else{

            bloqueiaBT(true);
            if (oldsetor!= null && oldsetor.getStatus() == 1 && !cbativo.isChecked() && bedit) {
                Log.i(TAG, "Prod dados ini ok  editar inativar " + bedit);
                verificarUsoSetor();
            } else {
                Log.i(TAG, "Prod dados ini ok só editar " + bedit);
                salvaSetor();
            }
        }


    }

    private void verificarUsoSetor() {

        boolean tem = false;
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getSetor().equals(oldsetor.getKeysetor())) {
                tem = true;
                break;
            }
        }
        if (tem) {
            stNitem.clear();
            stNitem.add("Selecione");
            for (int i = 0; i < Home.baseSetores.size(); i++) {
                if (Home.baseSetores.get(i).getStatus() == 1
                        && !Home.baseSetores.get(i).getKeysetor().equals(oldsetor.getKeysetor())) {
                    stNitem.add(Home.baseSetores.get(i).getNomesetor());
                }
            }

            if (oldsetor.getStatus() == 1) {
                Alerta_Inativar alerta_inativar = new Alerta_Inativar("Setor", oldsetor.getNomesetor(), stNitem);
                alerta_inativar.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
            return;
        }else{
            salvaSetor();
        }

    }

    private void migraSetor(Integer ret) {
        mprogressBar.setVisibility(View.VISIBLE);

        String kitem = "";

        for (int i = 0; i < Home.baseSetores.size(); i++) {
            if (Home.baseSetores.get(i).getNomesetor().equals(stNitem.get(ret))) {
                kitem = Home.baseSetores.get(i).getKeysetor();
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        for (int i = 0; i < Home.baseInstall.size(); i++) {
            if (Home.baseInstall.get(i).getSetor().equals(oldsetor.getKeysetor())) {
                DocumentReference dr = db.collection("instalacoes").document(Home.baseInstall.get(i).getNuminstal());
                batch.update(dr, "setor", kitem);
            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                salvaSetor();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcadedit, "Problemas ao atualizar Instalações. Informe Administrador");
            }
        });
    }

    private void salvaSetor() {

        Setores ttt = new Setores();

        ttt.setNomesetor(newnome);
        ttt.setCepinicial(newcepini);
        ttt.setCepfinal(newcepfin);
        ttt.setKeysetor(keyitem);
        if(cbativo.isChecked()){
            ttt.setStatus(1);
        }else{
            ttt.setStatus(0);
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("setores").document(keyitem);
        batch.set(dr, ttt);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Setor salvo com sucesso.");
                limpatela();
                Itens_Lista.bedit = false;
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Setor. Informe administrador.");
                bloqueiaBT(false);
            }
        });

    }



    public void checaDesp(){

        if (bedit) {
            keyitem = olddesp.getKeydespesa();
        } else {
            keyitem = UUID.randomUUID().toString();
        }

        if(newvalor.equals("")||newvalor.equals("0,00")){
            util.showSnackError(clcadedit,"informe Valor da Despesa.");
            return;
        } else if(sptipo.getSelectedItemPosition()==0){
            util.showSnackError(clcadedit,"Selecione Tipo de Despesa.");
            return;
        } else if(splink.getSelectedItemPosition()==0){
            util.showSnackError(clcadedit,"Selecione Link de Despesa.");
            return;
        } else if(txtdata.getText().toString().equals("00-00-0000")){
            util.showSnackError(clcadedit,"Selecione Data da Despesa.");
            return;
        }else {
            bloqueiaBT(true);
                salvaDesp();
        }


    }

    private void salvaDesp() {

        mprogressBar.setVisibility(View.VISIBLE);
        Despesas desp = new Despesas();

        desp.setTipodespesa(sptipo.getSelectedItem().toString());
        desp.setLinkdespesa(LINK);
        desp.setDescricaodespesa(newdesc);

        desp.setValordespesa(newvalor);
        desp.setKeydespesa(keyitem);

        long dtdesp =util.longdiamesano(txtdata.getText().toString());
        LocalDate ddd = util.Dia_01_Mes(dtdesp);
        desp.setDatadesp(dtdesp);

        DateTimeFormatter fmtc = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);

        desp.setCompdesp(util.longdiamesano(ddd.format(fmtc)));


        if(cbativo.isChecked()){
            desp.setStatusdespesa(1);
        }else{
            desp.setStatusdespesa(0);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        DocumentReference dr = db.collection("despesas").document(keyitem);
        batch.set(dr, desp);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Despesas salvo com sucesso.");
                limpatela();
                Itens_Lista.bedit = false;
                homeViewModel.setApdint(1);
                Cad_Itens.vpclass.setCurrentItem(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mprogressBar.setVisibility(View.INVISIBLE);
                util.showSnackOk(clcadedit, "Problemas ao salvar Despesa. Informe administrador.");
                bloqueiaBT(false);
            }
        });

    }



    public static void carregaItem(){
        bloqueiaBT(false);


        if(!Itens_Lista.bedit) {
            edtnome.setText   ("");
            edtcepini.setText ("");
            edtcepfim.setText ("");
            edtvalor.setText  ("");
            edtcapmega.setText("");
            edtdesc.setText   ("");
            edtconta.setText  ("");
            cbativo.setChecked(true);
            cbativo.setText("Ativo");
            btcad.setText("Add");
            sptipo.setSelection(0);
            imgrefresh.setVisibility(View.INVISIBLE);
            imgrefresh.setClickable(false);
            imgrefresh.setEnabled(false);
            splink.setSelection(0);
            txtdata.setText("00-00-0000");
            bloqueiaBT(false);

            switch (Home.ctrlItem) {
                case 0:
                    btcad.setText("Add Link");
                    break;
                case 1:
                    btcad.setText("Add Produto");
                    break;
                case 2:
                    btcad.setText("Add Cobrança");
                    break;
                case 3:
                    btcad.setText("Add Adesão");
                    break;
                case 4:
                    btcad.setText("Add Setor");
                    break;
                case 5:
                    btcad.setText("Add Despesa");
                    break;
            }

        }else {

            btcad.setText("Alterar");
            imgrefresh.setVisibility(View.VISIBLE);
            imgrefresh.setClickable(true);
            imgrefresh.setEnabled(true);

            switch (Home.ctrlItem) {

                case 0:
                        edtnome.setText(oldlink.getNomelink());
                        edtvalor.setText(oldlink.getValorlink());
                        edtcapmega.setText(oldlink.getCapacidadelink());
                        if (oldlink.getStatus() == 0) {
                            cbativo.setChecked(false);
                            cbativo.setText("Inativo");
                        } else {
                            cbativo.setChecked(true);
                            cbativo.setText("Ativo");
                        }
                    break;
                case 1:
                        edtnome.setText(oldprod.getNomeproduto());
                        edtvalor.setText(oldprod.getValorproduto());
                        edtdesc.setText(oldprod.getDescricaoproduto());
                        if (oldprod.getStatusproduto() == 0) {
                            cbativo.setChecked(false);
                            cbativo.setText("Inativo");
                        } else {
                            cbativo.setChecked(true);
                            cbativo.setText("Ativo");
                        }
                        for (int i = 0; i < rprod.length; i++) {
                            if (oldprod.getTipoproduto().equals(rprod[i])) {
                                sptipo.setSelection(i);
                            }
                        }
                        if (Itens_Lista.bedit) {
                            sptipo.setClickable(false);
                            sptipo.setEnabled(false);
                        }
                    break;
                case 2:
                        edtnome.setText(oldCob.getNome_cobranca());
                        edtconta.setText(oldCob.getConta_cobranca());
                        if (oldCob.getStatu_cobranca() == 0) {
                            cbativo.setChecked(false);
                            cbativo.setText("Inativo");
                        } else {
                            cbativo.setChecked(true);
                            cbativo.setText("Ativo");
                        }
                        for (int i = 0; i < Home.baseTypeCobranca.size(); i++) {
                            if (oldCob.getTypecob_cobranca().equals(Home.baseTypeCobranca.get(i).getKey_type_cob())) {
                                sptipo.setSelection(i);
                            }
                        }
                        if (Itens_Lista.bedit) {
                            sptipo.setClickable(false);
                            sptipo.setEnabled(false);
                        }
                    break;
                case 3:
                        edtnome.setText(oldAdesao.getNome_adesao());
                        edtdesc.setText(oldAdesao.getDescricao_adesao());
                        edtvalor.setText(oldAdesao.getValor_adesao());
                        if (oldAdesao.getStatus_adesao() == 0) {
                            cbativo.setChecked(false);
                            cbativo.setText("Inativo");
                        } else {
                            cbativo.setChecked(true);
                            cbativo.setText("Ativo");
                        }
                        for (int i = 0; i < Home.baseTypeCobranca.size(); i++) {
                            if (oldAdesao.getTipo_cob_adesao().equals(Home.baseTypeCobranca.get(i).getKey_type_cob())) {
                                sptipo.setSelection(i);
                            }
                        }
                        if (Itens_Lista.bedit) {
                            sptipo.setClickable(false);
                            sptipo.setEnabled(false);
                        }
                    break;
                case 4:
                        edtnome.setText(oldsetor.getNomesetor());
                        edtcepini.setText(oldsetor.getCepinicial());
                        edtcepfim.setText(oldsetor.getCepfinal());
                        if (oldsetor.getStatus() == 0) {
                            cbativo.setChecked(false);
                            cbativo.setText("Inativo");
                        } else {
                            cbativo.setChecked(true);
                            cbativo.setText("Ativo");
                        }
                    break;
                case 5:
                    edtdesc.setText(olddesp.getDescricaodespesa());
                    edtvalor.setText(olddesp.getValordespesa());


                    for(int i=0;i<rdesp.length;i++){
                        if(olddesp.getTipodespesa().equals(rdesp[i])){
                            sptipo.setSelection(i);
                        }
                    }

                    if(olddesp.getLinkdespesa().equals("Geral")){
                        splink.setSelection(1);
                    }else {
                        for (int i = 0; i < arrayLink.size(); i++) {
                            if (arrayLink.get(i).getKeylink().equals(olddesp.getLinkdespesa())) {
                                splink.setSelection(i);
                            }
                        }
                    }
                    if (olddesp.getStatusdespesa() == 0) {
                        cbativo.setChecked(false);
                        cbativo.setText("Cancelada");
                    } else {
                        cbativo.setChecked(true);
                        cbativo.setText("Ativa");
                    }

                    Instant it = Instant.ofEpochMilli(olddesp.getDatadesp());
                    ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
                    LocalDate datadesp = zdt.toLocalDate();

                    DateTimeFormatter fmtc = DateTimeFormatter
                            .ofPattern("dd-MM-yyyy")
                            .withResolverStyle(ResolverStyle.STRICT);


                    txtdata.setText(datadesp.format(fmtc));
                    break;
            }
        }
    }

    private void carregatipo() {
        switch (Home.ctrlItem){
            case 1:
                final ArrayAdapter<CharSequence> adaptersp = ArrayAdapter.createFromResource(getContext(),
                        R.array.tipo_prod, R.layout.sp_prod);
                adaptersp.setDropDownViewResource(R.layout.sp_prod);
                sptipo.setAdapter(adaptersp);
                break;
            case 2:
                arrayCob.clear();

                for (int i=0;i<Home.baseTypeCobranca.size();i++){
                    if(Home.baseTypeCobranca.get(i).getTipo_type_cob()!=10) {
                        arrayCob.add(Home.baseTypeCobranca.get(i));
                    }

                }
                adapter_cobranca = new Adapter_Type_Cobranca(getContext(), arrayCob);
                adapter_cobranca.notifyDataSetChanged();
                sptipo.setAdapter(adapter_cobranca);
                break;
            case 3:
                arrayCob.clear();

                for (int i=0;i<Home.baseTypeCobranca.size();i++){
                        arrayCob.add(Home.baseTypeCobranca.get(i));
                }

                adapter_cobranca = new Adapter_Type_Cobranca(getContext(), arrayCob);
                adapter_cobranca.notifyDataSetChanged();
                sptipo.setAdapter(adapter_cobranca);
                break;
            case 5:

                final ArrayAdapter<CharSequence> adapterdesp = ArrayAdapter.createFromResource(getContext(),
                        R.array.tipo_desp, R.layout.sp_prod);
                adapterdesp.setDropDownViewResource(R.layout.sp_prod);
                sptipo.setAdapter(adapterdesp);

                arrayLink.clear();
                for (int i = 0; i< Home.baseLinks.size(); i++){
                        if(Home.baseLinks.get(i).getStatus()==1)arrayLink.add(Home.baseLinks.get(i));
                }
                if(arrayLink.size()==0) {
                    Links ll = new Links();
                    ll.setKeylink("0");
                    ll.setNomelink("Sem Links");
                    ll.setValorlink("0,00");
                    arrayLink.add(ll);
                }else{
                    Links ll = new Links();
                    ll.setKeylink("0");
                    ll.setNomelink("Selecione");
                    ll.setValorlink("0,00");
                    arrayLink.add(0,ll);
                    Links ll2 = new Links();
                    ll2.setKeylink("1");
                    ll2.setNomelink("Geral");
                    ll2.setValorlink("0,00");
                    arrayLink.add(1,ll2);
                }

                adapter_link = new Adapter_Link(getContext(), arrayLink);
                adapter_link.notifyDataSetChanged();
                splink.setAdapter(adapter_link);

                break;

        }
    }

    private void limpatela() {
        Itens_Lista.bedit=false;
        edtnome.setText   ("");
        edtcepini.setText ("");
        edtcepfim.setText ("");
        edtvalor.setText  ("");
        edtcapmega.setText("");
        edtdesc.setText   ("");
        edtconta.setText  ("");
        cbativo.setChecked(true);
        cbativo.setText("Ativo");
        btcad.setText("Add");
        sptipo.setSelection(0);
        imgrefresh.setVisibility(View.INVISIBLE);
        imgrefresh.setClickable(false);
        imgrefresh.setEnabled(false);
        splink.setSelection(0);
        splink.setClickable(true);
        splink.setEnabled(true);
        txtdata.setClickable(true);
        txtdata.setEnabled(true);
        txtdata.setText("00-00-0000");
        bloqueiaBT(false);

        switch (Home.ctrlItem) {
            case 0:
                btcad.setText("Add Link");
                break;
            case 1:
                btcad.setText("Add Produto");
                break;
            case 2:
                btcad.setText("Add Cobrança");
                break;
            case 3:
                btcad.setText("Add Adesão");
                break;
            case 4:
                btcad.setText("Add Setor");
                break;
            case 5:
                btcad.setText("Add Despesa");
                break;
        }

    }

    private static void bloqueiaBT ( boolean b){

        btcancel.setClickable(!b);
        btcancel.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        edtdesc.setEnabled(!b);
        edtdesc.setClickable(!b);
        edtnome.setEnabled(!b);
        edtnome.setClickable(!b);
        edtcepini.setEnabled(!b);
        edtcepini.setClickable(!b);
        edtcepfim.setEnabled(!b);
        edtcepfim.setClickable(!b);
        edtvalor.setEnabled(!b);
        edtvalor.setClickable(!b);
        edtcapmega.setEnabled(!b);
        edtcapmega.setClickable(!b);
        edtconta.setEnabled(!b);
        edtconta.setClickable(!b);
        blockAdapt = b;
        cbativo.setClickable(!b);
        cbativo.setEnabled(!b);
        sptipo.setEnabled(!b);
        sptipo.setClickable(!b);
        splink.setClickable(!b);
        splink.setEnabled(!b);
        txtdata.setClickable(!b);
        txtdata.setEnabled(!b);
        MainActivity.blobk = b;
    }


    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


}