package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Adesao;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Link;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Produto;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Boleto;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Dia_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Gera_Boleto;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Adesao;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Mensalidades_Instal;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Tipo_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.model.Type_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;


public class Cad_Install extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText edtppoe,edtmega;
    private TextView txtcontppoe,txtvcto,txtpzad,txttipoadesao,txttipomensal;
    private TextView txfplano,txftv,txfinstal,txfvplano,txfvtv,txfvinstal,txfvtotal;

    private Spinner spsetor,spnomecob,spplano,splink,sptv,spadesao;
    private ConstraintLayout clcad;
    private ProgressBar mprogressBar;
    private Button btcad,btcancel;

    private String CPF,NOME,APELIDO,FONE,EMAIL,ENDERECO,NUMERO,CEP,COMPL,INSTAL,SETOR,LINK,PLANO,TV,COBRANCA,VCTO,PPOE,ADESAO,
            VALORPLANO, VALORTV,VALORADESAO,KEYCLI,KEYADESAO,MEGA,VALORTVTIT,VALORPLANOTIT;
    private int STATUSADESAO;

    private Adapter_Cobrancas adapter_cobrancas;
    private Adapter_Adesao adapter_adesao;
    private Adapter_Setor adapter_setor;
    private Adapter_Link adapter_link;
    private Adapter_Produto adapter_plano;
    private Adapter_Produto adapter_tv;

    private ArrayList<Setores> bsetores = new ArrayList<>();
    private ArrayList<Links> blinks = new ArrayList<>();
    private ArrayList<Tabela_Produtos> bplanos = new ArrayList<>();
    private ArrayList<Tabela_Produtos> btv = new ArrayList<>();
    private ArrayList<Type_Cobranca> btypecob = new ArrayList<>();
    private ArrayList<Cobrancas> bcobrancas = new ArrayList<>();
    private ArrayList<Adesao> badesao = new ArrayList<>();

    private Adesao adesaosel= new Adesao();

    private BigDecimal zero;
    private BigDecimal biginstal;
    private BigDecimal bigtv;
    private BigDecimal bigplano;
    private BigDecimal bigtotal;
    private BigDecimal bigintegral;
    private BigDecimal bigpropplano;
    private BigDecimal bigprotv;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    private String YEAR,MONTH,DAY;
    long daysBetween = 0;
    BigDecimal dias_a_cobrar ;
    BigDecimal valor_plano;
    BigDecimal valor_plano_prop;
    BigDecimal valor_plano_acrescido;
    BigDecimal valor_dia ;

    private String valorfinal;
    private String vctofinal;

    private String valorfinalplano;
    private String valorfinaltv;

    String diavcto="";
    LocalDate dataInstall;
    LocalDate dataVcto;
    LocalDate dataVctoprox;
    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dmt = DateTimeFormatter
            .ofPattern("dd")
            .withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter mmt = DateTimeFormatter
            .ofPattern("MM")
            .withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter ymt = DateTimeFormatter
            .ofPattern("yyyy")
            .withResolverStyle(ResolverStyle.STRICT);
    String nyear= "";
    String nmonth="";
    String nday = "";
    String strDate="";
    String pnyear="";
    String pnmonth="";
    String pnday = "";
    String pstrDate="";
    int iyear=0;
    int imonth=0;
    int iday=0;
    int proxyear=0;
    int proxmonth=0;
    int proxday=0;
    String sii = "";
    String svv = "";
    String pvv = "";

    LocalDate dataenvio;
    long LONGDATAENVIO;


    private int numeroBol;
    private Clientes cliente;
    private Instalacoes instalacao;
    private Titulos ntit,tit1,tit2,titad;

    private String compcomp;
    private String mescompdia1;

    private int contatit=0;

    private int ctrltit=0;

    private int geravalores=0;

    private Mensalidades_Instal mm1,mm2;
    String menAtual = "";
    String menTit1 = "";
    String menTit2 = "";
    public Cad_Install() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad__install, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {

        homeViewModel= Home.homeViewModel;

        INSTAL="";SETOR="";LINK="";PLANO="";TV="";COBRANCA="";VCTO="";PPOE="";ADESAO="";KEYADESAO="";
        VALORPLANO="";VALORTV="";VALORADESAO="";KEYCLI="";MEGA="";
        STATUSADESAO=0;

        zero =new BigDecimal("0.00");
        biginstal =new BigDecimal("0.00");
        bigtv =new BigDecimal("0.00");
        bigplano =new BigDecimal("0.00");
        bigtotal =new BigDecimal("0.00");

        edtppoe                 =v.findViewById(R.id.edtppoe        );
        edtmega                 =v.findViewById(R.id.edtmega        );
        txtcontppoe             =v.findViewById(R.id.txtcontppoe    );
        txtvcto                 =v.findViewById(R.id.txtvcto        );
        txtpzad                 =v.findViewById(R.id.txtpzad        );
        txttipoadesao                 =v.findViewById(R.id. txttipoadesao         );
        txttipomensal                 =v.findViewById(R.id. txttipomensal         );
        txfplano                =v.findViewById(R.id.txfplano       );
        txftv                   =v.findViewById(R.id.txftv          );
        txfinstal               =v.findViewById(R.id.txfinstal      );
        txfvplano               =v.findViewById(R.id.txfvplano      );
        txfvtv                  =v.findViewById(R.id.txfvtv         );
        txfvinstal              =v.findViewById(R.id.txfvinstal     );
        txfvtotal               =v.findViewById(R.id.txfvtotal      );

        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        spsetor=v.findViewById(R.id.spsetor);
        spnomecob=v.findViewById(R.id.spnomecob);
        spplano=v.findViewById(R.id.spplano);
        splink=v.findViewById(R.id.splink);
        sptv=v.findViewById(R.id.sptv);
        spadesao=v.findViewById(R.id.spadesao);
        clcad=v.findViewById(R.id.clcad);
        mprogressBar=v.findViewById(R.id.mprogressBar);

        btypecob=Home.baseTypeCobranca;
        txtpzad.setText(String.valueOf(Home.Settings.getPzo_vcto_adesao()));


        homeViewModel.getVctobol().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bol) {
                if(bol){
                    VCTO=homeViewModel.getDiavcto().toString();
                    txtvcto.setText(VCTO);
                }
            }
        });

        homeViewModel.getResultbol().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer bol) {

                switch (bol) {
                    case 1:
                        geraTitulo_Tipo1();
                        break;
                    case 2:
                        geraDoisTitulo_Tipo2();
                        break;
                    case 3:
                        geraTitulos_Tipo3();
                        break;
                    default:
                }
            }
        });

        homeViewModel.getNumerobol().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer bol) {

                switch (bol) {

                    case 1:
                        switch (ctrltit){
                            case 1:
                                numeroBol=0;
                                geraAdesao();
                                homeViewModel.setNumerobol(0);
                                break;
                            case 2:
                               geraSegundoTitulo();
                                break;
                            case 3:
                                util.showSnackOk(clcad,"salvatudo");

                                geraMensInstall();

                                //salvaTudo();
                                break;
                        }

                        break;
                    case 5:
                        tit1=null;
                        tit2 =null;
                        titad=null;
                        numeroBol=0;
                        break;


                }
            }
        });



        edtppoe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    PPOE=edtppoe.getText().toString().trim();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontppoe.setText(PPOE.length()+"/15");
                }
            }
        });

        edtppoe.addTextChangedListener(textWatcher);

        edtmega.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    MEGA=edtmega.getText().toString().trim();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                }
            }
        });

        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtppoe.requestFocus();
                hideSoftKeyboard();
                limpaFoco();
            }
        });
        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tit1=null;
                tit2=null;
                numeroBol=0;
                ctrltit=0;
                salvar();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_frag_Cad_Cli_to_home);
            }
        });

        txtvcto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(),(short)20);
                limpaFoco();
                Alerta_Dia_Vcto alerta_dia_vcto = new Alerta_Dia_Vcto();
                alerta_dia_vcto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });





        Setores ss = new Setores();
        ss.setKeysetor("0");
        ss.setNomesetor("Selecione");
        ss.setCepinicial("");
        ss.setCepfinal("");
        bsetores.clear();
        bsetores.add(ss);
        for (int i=0;i<Home.baseSetores.size();i++){
            if(Home.baseSetores.get(i).getStatus()==1) {
                bsetores.add(Home.baseSetores.get(i));
            }
        }
        adapter_setor = new Adapter_Setor(getContext(), bsetores);
        adapter_setor.notifyDataSetChanged();
        spsetor.setAdapter(null);
        spsetor.setAdapter(adapter_setor);
        spsetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                limpaFoco();
                if(position==0){
                    SETOR="";
                }else {
                    SETOR=bsetores.get(position).getKeysetor();
                }
                spsetor.clearFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        Tabela_Produtos tp = new Tabela_Produtos();
        tp.setKeyproduto("0");
        tp.setNomeproduto("Selecione");
        tp.setValorproduto("0,00");
        tp.setTipoproduto("");
        tp.setDescricaoproduto("");
        bplanos.clear();

        btv.clear();
        bplanos.add(tp);
        btv.add(tp);
        for (int i=0;i<Home.baseProdutos.size();i++){
            switch (Home.baseProdutos.get(i).getTipoproduto()){
                case"Planos Net":
                    if(Home.baseProdutos.get(i).getStatusproduto()==1) {
                        bplanos.add(Home.baseProdutos.get(i));
                    }
                    break;

                case"TV":
                    if(Home.baseProdutos.get(i).getStatusproduto()==1) {
                        btv.add(Home.baseProdutos.get(i));
                    }
                    break;
            }

        }
        adapter_plano = new Adapter_Produto(getContext(), bplanos);
        adapter_plano.notifyDataSetChanged();
        spplano.setAdapter(adapter_plano);
        spplano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                limpaFoco();
                if(position==0){
                    PLANO="";
                    VALORPLANO="";
                    bigplano=zero;
                    txfplano.setText("Selecione");
                    txfvplano.setText(df.format(bigplano));
                }else {
                    PLANO=bplanos.get(position).getKeyproduto();
                    VALORPLANO=bplanos.get(position).getValorproduto();
                    bigplano=new BigDecimal(util.S_to_Big(bplanos.get(position).getValorproduto()));
                    txfplano.setText(bplanos.get(position).getNomeproduto());
                    txfvplano.setText(df.format(bigplano));
                }
                spplano.clearFocus();
                totalprodutos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        adapter_tv = new Adapter_Produto(getContext(), btv);
        adapter_tv.notifyDataSetChanged();
        sptv.setAdapter(adapter_tv);
        sptv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 30);
                limpaFoco();
                if(position==0){
                    TV="";
                    VALORTV="";
                    bigtv=zero;
                    txftv.setText("Selecione");
                    txfvtv.setText(df.format(bigtv));
                }else {
                    TV=btv.get(position).getKeyproduto();
                    VALORTV=btv.get(position).getValorproduto();
                    bigtv=new BigDecimal(util.S_to_Big(btv.get(position).getValorproduto()));
                    txftv.setText(btv.get(position).getNomeproduto());
                    txfvtv.setText(df.format(bigtv));
                }
                sptv.clearFocus();
                totalprodutos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        Links ll = new Links();
        ll.setKeylink("0");
        ll.setNomelink("Selecione");
        ll.setValorlink("0,00");
        blinks.clear();
        blinks.add(ll);
        for (int i=0;i<Home.baseLinks.size();i++){
            if(Home.baseLinks.get(i).getStatus()==1) {
                blinks.add(Home.baseLinks.get(i));
            }
        }
        adapter_link = new Adapter_Link(getContext(), blinks);
        adapter_link.notifyDataSetChanged();
        splink.setAdapter(adapter_link);
        splink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                limpaFoco();
                if(position==0){
                    LINK="";
                }else {
                    LINK=blinks.get(position).getKeylink();
                }
                splink.clearFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        badesao.clear();
        Adesao ads = new Adesao();
        ads.setNome_adesao("Selecione");
        ads.setKey_adesao("0");
        badesao.add(ads);
        for(int i=0;i<Home.baseAdesao.size();i++){
            if(Home.baseAdesao.get(i).getStatus_adesao()==1){
                badesao.add(Home.baseAdesao.get(i));
            }
        }


        adapter_adesao = new Adapter_Adesao(getContext(), badesao);
        adapter_adesao.notifyDataSetChanged();
        spadesao.setAdapter(adapter_adesao);
        spadesao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                limpaFoco();

                if(position==0){
                    VALORADESAO="";
                    STATUSADESAO=0;
                    biginstal=zero;
                    txfinstal.setText("Selecione");
                    txfvinstal.setText(df.format(biginstal));
                    ADESAO="";
                    adesaosel=null;
                    txttipoadesao.setText("Selecione");
                }else {
                    adesaosel=badesao.get(position);
                  //  if(badesao.get(position).getNome_adesao().equals("AD. CORTESIA"))adesaosel.setType_cob(10);
                    ADESAO=badesao.get(position).getKey_adesao();
                    VALORADESAO=badesao.get(position).getValor_adesao();
                    STATUSADESAO=0;
                    biginstal=new BigDecimal(util.S_to_Big(badesao.get(position).getValor_adesao()));
                    txfinstal.setText(badesao.get(position).getNome_adesao());
                    txfvinstal.setText(df.format(biginstal));

                    Log.i("adesaotypecob",": "+adesaosel.getType_cob());
                    for (int i=0;i<btypecob.size();i++){
                        if(btypecob.get(i).getTipo_type_cob()==(badesao.get(position).getType_cob())){
                            txttipoadesao.setText(btypecob.get(i).getNome_type_cob());
                        }

                    }
                }
//
                spadesao.clearFocus();
                totalprodutos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        bcobrancas.clear();
        Cobrancas cds = new Cobrancas();
        cds.setNome_cobranca("Selecione");
        cds.setKey_cobranca("0");
        bcobrancas.add(cds);
        for(int i=0;i<Home.baseCobrancas.size();i++){
            if(Home.baseCobrancas.get(i).getStatu_cobranca()==1){
                bcobrancas.add(Home.baseCobrancas.get(i));
            }
        }


        adapter_cobrancas = new Adapter_Cobrancas(getContext(), bcobrancas);
        adapter_cobrancas.notifyDataSetChanged();
        spnomecob.setAdapter(adapter_cobrancas);
        spnomecob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                limpaFoco();
                if(position==0) {
                    COBRANCA = "";
                    txttipomensal.setText("Selecione");
                }else{
                    COBRANCA=bcobrancas.get(position).getKey_cobranca();
                    for (int i=0;i<btypecob.size();i++){
                        if(btypecob.get(i).getKey_type_cob().equals(bcobrancas.get(position).getTypecob_cobranca())){
                            txttipomensal.setText(btypecob.get(i).getNome_type_cob());
                        }
                    }
                }
                spnomecob.clearFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void geraTitulo_Tipo1() {
        valorfinal= util.Big_to_S(valor_plano);

        VALORPLANOTIT = util.Big_to_S(bigplano);
        VALORTVTIT  = util.Big_to_S(bigtv);

        vctofinal=dataVctoprox.format(fmt);
        Log.i("geratit","geraTitulo_Tipo1 valorfinal: " + valorfinal + " vctofinal: " + vctofinal);
        mescompdia1=vctofinal;
        mescompdia1=mescompdia1.substring(2,8);
        mescompdia1="01"+mescompdia1;
        contatit=1;
        Log.i("geratit","mescompdia1: " + mescompdia1 );
        instalacao.setNum_tit_gerado(contatit);
        ctrltit=1;
        tit1=geraTitulos();
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tit1);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        listaTit(tit1);
    }

    private void geraDoisTitulo_Tipo2() {
        valorfinal=util.Big_to_S(valor_plano_prop);
        Log.i("calculopl","valor_plano_prop " + valor_plano_prop);
        Log.i("calculopl","bigtotal " + bigtotal);
        BigDecimal perc = new BigDecimal("0");
        perc=valor_plano_prop .divide(bigtotal,10,BigDecimal.ROUND_HALF_EVEN);
        Log.i("calculopl","perc " + perc);
        Log.i("calculopl","bigplano " + bigplano);
        bigpropplano = bigplano.multiply(perc);
        Log.i("calculopl","bigpropplano " + bigpropplano);
        bigpropplano = bigpropplano.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
        Log.i("calculopl","bigpropplanoX " + bigpropplano);
        VALORPLANOTIT = util.Big_to_S(bigpropplano);
        bigprotv = valor_plano_prop.subtract(bigpropplano);
        Log.i("calculopl","bigprotv " + bigprotv);
       // bigprotv = bigprotv.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
        Log.i("calculopl","bigprotvX " + bigprotv);
        VALORTVTIT = util.Big_to_S(bigprotv);



        vctofinal=dataVcto.format(fmt);
        Log.i("geratit","geraTitulo_Tipo2 valorfinal: " + valorfinal + " vctofinal: " + vctofinal);
        mescompdia1=vctofinal;
        mescompdia1=mescompdia1.substring(2,8);
        mescompdia1="01"+mescompdia1;
        Log.i("geratit"," pri mescompdia1: " + mescompdia1 );
        contatit=1;
        instalacao.setNum_tit_gerado(contatit);
        tit1=geraTitulos();
        ctrltit=2;
        listaTit(tit1);
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tit1);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
    }

    private void geraTitulos_Tipo3() {
        valorfinal=util.Big_to_S(valor_plano_acrescido);

        BigDecimal perc = new BigDecimal("0");
        perc=valor_plano_acrescido  .divide(bigtotal,BigDecimal.ROUND_HALF_UP);
        bigpropplano = bigplano.multiply(perc);
        bigpropplano = bigpropplano.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
        VALORPLANOTIT = util.Big_to_S(bigpropplano);
        bigprotv = valor_plano_acrescido.subtract(bigpropplano);
       // bigprotv = bigprotv.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
        VALORTVTIT = util.Big_to_S(bigprotv);

        vctofinal=dataVctoprox.format(fmt);
        Log.i("geratit","geraTitulo_Tipo3 valorfinal: " + valorfinal + " vctofinal: " + vctofinal);
        mescompdia1=vctofinal;
        mescompdia1=mescompdia1.substring(2,8);
        mescompdia1="01"+mescompdia1;
        Log.i("geratit","mescompdia1: " + mescompdia1 );
        contatit=1;
        instalacao.setNum_tit_gerado(contatit);
        tit1=geraTitulos();
        ctrltit=1;
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tit1);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        listaTit(tit1);
    }

    private void geraSegundoTitulo() {
        valorfinal= util.Big_to_S(valor_plano);
        VALORPLANOTIT = util.Big_to_S(bigplano);
        VALORTVTIT  = util.Big_to_S(bigtv);
        vctofinal = dataVctoprox.format(fmt);
        Log.i("geratit","geraSegundoTitulo valorfinal: " + valorfinal + " vctofinal: " + vctofinal);
        mescompdia1=vctofinal;
        mescompdia1=mescompdia1.substring(2,8);
        mescompdia1="01"+mescompdia1;
        Log.i("geratit","mescompdia1: " + mescompdia1 );
        contatit=2;
        instalacao.setNum_tit_gerado(contatit);
        tit2=geraTitulos();
        ctrltit=1;
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tit2);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        listaTit(tit2);
    }

    private void geraTitulo_1_Titulo() {


        switch (geravalores){
            case 0:
                VALORPLANOTIT = util.Big_to_S(bigplano);
                VALORTVTIT  = util.Big_to_S(bigtv);
                break;

            default:
                valorfinal=util.Big_to_S(valor_plano_prop);
                Log.i("calculopl","valor_plano_prop " + valor_plano_prop);
                Log.i("calculopl","bigtotal " + bigtotal);
                BigDecimal perc = new BigDecimal("0");
                perc=valor_plano_prop .divide(bigtotal,10,BigDecimal.ROUND_HALF_EVEN);
                Log.i("calculopl","perc " + perc);
                Log.i("calculopl","bigplano " + bigplano);
                bigpropplano = bigplano.multiply(perc);
                Log.i("calculopl","bigpropplano " + bigpropplano);
                bigpropplano = bigpropplano.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
                Log.i("calculopl","bigpropplanoX " + bigpropplano);
                VALORPLANOTIT = util.Big_to_S(bigpropplano);
                bigprotv = valor_plano_prop.subtract(bigpropplano);
                Log.i("calculopl","bigprotv " + bigprotv);
                // bigprotv = bigprotv.divide(new BigDecimal("1"),2,BigDecimal.ROUND_UP);
                Log.i("calculopl","bigprotvX " + bigprotv);
                VALORTVTIT = util.Big_to_S(bigprotv);
        }


        Log.i("geratit","geraTitulo_1_Titulo valorfinal: " + valorfinal + " vctofinal: " + vctofinal);
        mescompdia1=vctofinal;
        mescompdia1=mescompdia1.substring(2,8);
        mescompdia1="01"+mescompdia1;
        Log.i("geratit","mescompdia1: " + mescompdia1 );
        contatit=1;
        instalacao.setNum_tit_gerado(contatit);
        tit1=geraTitulos();
        ctrltit=1;
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tit1);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        listaTit(tit1);
    }

    private void listaTit(Titulos tit) {


        Log.i("listatit", "getNumerotit: "+tit.getNumerotit());
        Log.i("listatit", "getValortit: "+tit.getValortit());


        Instant it = Instant.ofEpochMilli(tit.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate ll = zdt.toLocalDate();
        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);
        Log.i("listatit", "getVencimentotit: "+ll.format(fmt));

        Instant it2 = Instant.ofEpochMilli(tit.getComptit());
        ZonedDateTime zdt2 = it2.atZone(ZoneId.systemDefault());
        LocalDate ll2 = zdt2.toLocalDate();
        DateTimeFormatter fmt2 = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);
        Log.i("listatit", "getComptit: "+ll2.format(fmt2));

        Log.i("listatit", "getStatustit: "+tit.getStatustit());
        Log.i("listatit", "getInstalltit: "+tit.getInstalltit());
        Log.i("listatit", "getNomeclientetit: "+tit.getNomeclientetit());
        Log.i("listatit", "getDesctit: "+tit.getDesctit());
        Log.i("listatit", "getTipocobtit: "+tit.getTipocobtit());
        Log.i("listatit", "getNomecobtit: "+tit.getNomecobtit());
        Log.i("listatit", "getContacobtit: "+tit.getContacobtit());
        Log.i("listatit", "getProdutotit: "+tit.getProdutostit());
        Log.i("listatit"," ");

        Log.i("listatit", "getKeyplanotit: "+tit.getKeyplanotit());
        Log.i("listatit", "getKeytvtit: "+tit.getKeytvtit());
        Log.i("listatit", "getKeygeraltit: "+tit.getKeygeraltit());
        Log.i("listatit", "getValorplano: "+tit.getValorplano());
        Log.i("listatit", "getValortv: "+tit.getValortv());
        Log.i("listatit", "getValorgeral: "+tit.getValorgeral());
        Log.i("listatit"," ");



        Log.i("listatit", "intall getNum_tit_gerado : "+instalacao.getNum_tit_gerado());

        it2 = Instant.ofEpochMilli(instalacao.getMes_gerado_d1());
        zdt2 = it2.atZone(ZoneId.systemDefault());
        ll2 = zdt2.toLocalDate();
        Log.i("listatit", "intall getMes_gerado_d1: "+ll2.format(fmt));
        Log.i("listatit"," ");

    }

    private void salvar() {


        limpaFoco();
        CPF=Cad_Cli.CPF;
        NOME=Cad_Cli.NOME;
        APELIDO=Cad_Cli.APELIDO;
        FONE=Cad_Cli.FONE;
        EMAIL=Cad_Cli.EMAIL;
        ENDERECO=Cad_Cli.ENDERECO;
        NUMERO=Cad_Cli.NUMERO;
        CEP=Cad_Cli.CEP;
        COMPL=Cad_Cli.COMPL;
        LONGDATAENVIO=Cad_Cli.longdataenvio;
        if(FONE.equals("") || FONE==null)FONE="(00)0000 0000)";
        if(EMAIL.equals("") || EMAIL==null)EMAIL="Não Informado";
        if(COMPL.equals("") || COMPL==null)COMPL="-";
        if(CEP.equals("") || CEP==null)CEP="00000-000";


        Instant it = Instant.ofEpochMilli(LONGDATAENVIO);
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);

        YEAR = String.valueOf(dataenvio.getYear());
        MONTH =String.format("%02d", new Object[] { dataenvio.getMonthValue() });
        DAY = String.format("%02d", new Object[] { dataenvio.getDayOfMonth() });

        Log.i("datasvcto","dataenvio: "+ dataenvio.format(fmt));

        if(NOME.equals("")){
            util.showSnackError(clcad,"Digite um Nome em Cliente.");
            return;
        }else if(APELIDO.equals("")){
            util.showSnackError(clcad,"Digite um Apelido em Cliente.");
            return;
        }else if(ENDERECO.equals("")){
            util.showSnackError(clcad,"Digite um Endereço em Cliente..");
            return;
        }else if(NUMERO.equals("")){
            util.showSnackError(clcad,"Digite um Número para o endereço em Cliente..");
            return;
        }else if(SETOR.equals("")){
            util.showSnackError(clcad,"Selecione um Setor para a Instalação.");
            return;
        }else if(LINK.equals("")){
            util.showSnackError(clcad,"Selecione o Link da Instalação.");
            return;
        }else if(PLANO.equals("")){
            util.showSnackError(clcad,"Selecione o Plano da Instalação.");
            return;
        }else if(TV.equals("")){
            util.showSnackError(clcad,"Selecione o Plano de TV da Instalação.");
            return;
        }else if(COBRANCA.equals("")){
            util.showSnackError(clcad,"Selecione o Tipo de Cobrança da Instalação.");
            return;
        }else if(VCTO.equals("")){
            util.showSnackError(clcad,"Selecione a data de Vencimento da Instalação.");
            return;
        }else if(ADESAO.equals("")){
            util.showSnackError(clcad,"Selecione o Nome da Adesão.");
            return;
        }else if(PPOE.equals("")) {
            util.showSnackError(clcad, "Digite um Ppoe para a Instalação.");
            return;
        }else if(MEGA.equals("") || MEGA==null) {
            util.showSnackError(clcad, "Informe qtde de Megas para a Instalção.");
            return;
        }else{
              verificar_ppoe();
        }

    }

    private void verificar_ppoe() {

        boolean temppoe=false;
        for(int i=0;i<Home.baseInstall.size();i++){
            if(Home.baseInstall.get(i).getPpoe()!=null && Home.baseInstall.get(i).getPpoe().equals(PPOE)){
                temppoe=true;
                break;
            }
        }

        if(temppoe){
            util.showSnackError(clcad, "Já existe um Ppoe com esse nome.");
            return;
        }else{
            cadastraCli();
        }
    }

    private void cadastraCli() {
        ArrayList<Instalacoes> arrayI = Home.baseInstall;
        Collections.sort(arrayI, new Comparator() {
            public int compare(Object o1, Object o2) {
                Instalacoes p1 = (Instalacoes) o1;
                Instalacoes p2 = (Instalacoes) o2;
                return p1.getNuminstal().compareToIgnoreCase(p2.getNuminstal());
            }
        });
        String ult="";
        if(arrayI.size()>0){
            ult  = arrayI.get(arrayI.size()-1).getNuminstal();
        }else{
            ult="000000";
        }


        int ntam =Integer.parseInt(ult);


        String sinstall = "";
        int iinstall = ntam+8;
        sinstall = String.format("%06d", new Object[] { iinstall });
        INSTAL =  sinstall;

        KEYCLI = UUID.randomUUID().toString();
        Clientes cli = new Clientes();
        cli.setKeycli(KEYCLI);
        cli.setNome(NOME);
        cli.setApelido(APELIDO);
        cli.setCpf(CPF);
        cli.setEndereco(ENDERECO);
        cli.setCep(CEP);
        cli.setNumero(NUMERO);
        cli.setComplemento(COMPL);
        cli.setEmailcli(EMAIL);
        cli.setTimestamp(LONGDATAENVIO);
        cli.setStatus(1);
        cli.setTelefone(FONE);

        cliente=cli;


        String keyinstall = UUID.randomUUID().toString();
        Instalacoes iins = new Instalacoes();
        iins.setCep(CEP);
        iins.setCliente(KEYCLI);
        iins.setComplemento(COMPL);
        iins.setEndereco(ENDERECO);
        iins.setKeyinstalacao(keyinstall);
        iins.setNumero(NUMERO);
        iins.setNuminstal(INSTAL);
        iins.setSetor(SETOR);
        iins.setStatus(1);
        iins.setTimestamp(LONGDATAENVIO);
        iins.setLink(LINK);
        iins.setPlano(PLANO);
        iins.setTv(TV);
        iins.setCobranca(COBRANCA);
        iins.setVcto(VCTO);
        iins.setPpoe(PPOE);
        iins.setAdesao(ADESAO);
        iins.setValorAdesao(VALORADESAO);
        iins.setUsumaker(HomeViewModel.getUsuario().getKeyusu());
        iins.setMes_gerado_d1(0);
        iins.setNum_tit_gerado(0);
        iins.setStatusMensal(0);
        iins.setMegainstal(Integer.parseInt(edtmega.getText().toString()));

        instalacao=iins;
        posicaoVcto();

    }

    private void posicaoVcto() {

        if(VCTO.equals("") || LONGDATAENVIO==0 ){
            util.showSnackError(clcad,"Prenncha data install em Cliente");
            return;
        }

        diavcto = VCTO;
        String dday=DAY;

        if(DAY.equals("31"))dday="30";
        BigDecimal bdata = new BigDecimal(dday);
        BigDecimal bvcto = new BigDecimal(diavcto);
        Log.i("datasvcto","DAY: " + DAY + " bdata: " + bdata + " diavcto: " + diavcto + " bvcto: " +bvcto);
        int compara = bvcto.compareTo(bdata);

        switch (compara){
            case -1:
                vctoMenorInstall();
                break;
            case 0:
               vctoIgualInstall();
                break;
            case 1:
                vctoMaiorInstall();
                break;
        }
    }

    public Titulos geraTitulos(){

        Cobrancas cc = new Cobrancas();
        for (int i=0;i<bcobrancas.size();i++){
            if(COBRANCA.equals(bcobrancas.get(i).getKey_cobranca())){
                cc=bcobrancas.get(i);
                break;
            }
        }

        Titulos tt = new Titulos();
        tt.setKeytit(UUID.randomUUID().toString());
        tt.setKeyclientetit(KEYCLI);
        tt.setNomeclientetit(NOME);
        tt.setInstalltit(INSTAL);

        tt.setTipocobtit(cc.getType_cob());
        tt.setNomecobtit(cc.getNome_cobranca());
        tt.setKeycobtit(cc.getKey_cobranca());
        tt.setContacobtit(cc.getConta_cobranca());
        tt.setBarrastit("");

        tt.setKeyplanotit(PLANO);
        tt.setKeytvtit(TV);
        tt.setKeygeraltit("");
        tt.setValorplano(VALORPLANOTIT);
        tt.setValortv(VALORTVTIT);
        tt.setValorgeral("0,00");

        StringBuilder str = null;
        str = new StringBuilder();
        str.append(PLANO);
        str.append("#"+TV);
        String result = str.toString();
        tt.setProdutostit(result);
        tt.setKeyusubaixa("");
        tt.setKeyusucancel("");

        tt.setKeylinktit(LINK);
        tt.setKeysetortit(SETOR);

        tt.setVencimentotit(util.long_X_st_dma(vctofinal));
        tt.setValortit(valorfinal);

        tt.setDatapgtotit(0);
        tt.setStatustit(1);
        tt.setSituacaotit(0);
        tt.setGeradobanco(1);

        if(cc.getType_cob()==1) tt.setGeradobanco(0);


        long timecomp=util.long_X_ld(util.Dia_01_Mes(LONGDATAENVIO));

        tt.setComptit(timecomp);

        instalacao.setMes_gerado_d1(timecomp);

        String numt = "";
        String digtit = "";
        numt=instalacao.getNuminstal();
        numt = new StringBuilder(numt).reverse().toString();
        digtit = String.format("%03d", new Object[] { contatit });
        digtit = new StringBuilder(digtit).reverse().toString();
        tt.setNumerotit(numt+digtit);
        tt.setDesctit("Pagamento Mensalidade");

//        ntit=tt;
//        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tt);
//        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        return tt;

    }

    private void geraAdesao() {

        Titulos tt = new Titulos();

        tt.setKeytit(UUID.randomUUID().toString());
        tt.setKeyclientetit(KEYCLI);
        tt.setNomeclientetit(NOME);
        tt.setInstalltit(INSTAL);

        LocalDate dtt=util.ld_X_long(LONGDATAENVIO);
        int somadias = Integer.parseInt(Home.Settings.getPzo_vcto_adesao());
        dtt=dtt.plusDays(somadias);
        long time=util.long_X_ld(dtt);

        tt.setNomecobtit(adesaosel.getNome_adesao());
        tt.setKeycobtit(adesaosel.getTipo_cob_adesao());
        tt.setContacobtit("Adesão");
        tt.setTipocobtit(adesaosel.getType_cob());
        tt.setSituacaotit(0);

        tt.setKeyplanotit(PLANO);
        tt.setKeytvtit(TV);
        tt.setKeygeraltit("");
        tt.setValorplano("0,00");
        tt.setValortv("0,00");
        tt.setValorgeral("0,00");

        switch (adesaosel.getType_cob()){

            case 1:
                tt.setDatapgtotit(0);
                tt.setStatustit(1);
                tt.setGeradobanco(0);
                tt.setKeyusubaixa("");
                break;

            case 2:
                tt.setDatapgtotit(0);
                tt.setStatustit(1);
                tt.setGeradobanco(1);
                tt.setKeyusubaixa("");
                break;

            case 3:
                tt.setDatapgtotit(0);
                tt.setStatustit(1);
                tt.setGeradobanco(1);
                tt.setKeyusubaixa("");
                break;
            case 10:
                tt.setTipocobtit(10);
                tt.setDatapgtotit(time);
                tt.setStatustit(3);
                tt.setGeradobanco(1);
                tt.setKeyusubaixa(HomeViewModel.getUsuario().getApelidousu());
                break;

        }

        tt.setBarrastit("");
        tt.setKeyusucancel("");
        tt.setVencimentotit(time);
        tt.setValortit(VALORADESAO);

        long timecomp=util.long_X_ld(util.Dia_01_Mes(LONGDATAENVIO));

        tt.setComptit(timecomp);
        tt.setKeylinktit(LINK);
        tt.setKeysetortit(SETOR);

        contatit++;
        String numt = "";
        String digtit = "";
        numt=instalacao.getNuminstal();
        numt = new StringBuilder(numt).reverse().toString();
        digtit = String.format("%03d", new Object[] { contatit });
        digtit = new StringBuilder(digtit).reverse().toString();
        tt.setNumerotit(numt+digtit);
        tt.setDesctit("Pagamento Adesão");

        tt.setProdutostit(adesaosel.getNome_adesao());

        instalacao.setNum_tit_gerado(contatit);
        ctrltit=3;
        listaTit(tt);
        titad=tt;
        Alerta_Boleto alerta_boleto = new Alerta_Boleto(tt);
        alerta_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
    }

    private void geraMensInstall(){

        mm1=new Mensalidades_Instal();
        mm2=new Mensalidades_Instal();

        menAtual = util.fdma(util.Dia_01_Mes(LONGDATAENVIO));
        menTit1 = util.fdma(util.Dia_01_Mes(LONGDATAENVIO));;
        menTit2 = "";

        mm1.setInstall(instalacao.getNuminstal());
        mm2.setInstall(instalacao.getNuminstal());
        if(tit1!=null && !util.fdma(util.Dia_01_Mes(tit1.getComptit())).equals(menAtual)){
            menTit2 = util.fdma(util.Dia_01_Mes(tit1.getComptit()));
        }
        if(tit2!=null && !util.fdma(util.Dia_01_Mes(tit2.getComptit())).equals(menAtual)){
            menTit2 = util.fdma(util.Dia_01_Mes(tit2.getComptit()));
        }

       salvaTudo();
    }

    private void sssint() {


        FirebaseFirestore.getInstance().collection("instalacoes")
                .document(INSTAL)
                .set(instalacao)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        sssMenInt();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    private void ssstitulos() {

        FirebaseFirestore.getInstance().collection("titulos")
                .document(tit1.getKeytit())
                .set(tit1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        FirebaseFirestore.getInstance().collection("titulos")
                                .document(titad.getKeytit())
                                .set(titad)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(tit2==null){

                                            util.showSnackOk(clcad, "Cliente salvo com sucesso.");
                                            MainActivity.navController.navigate(R.id.action_frag_Cad_Cli_to_home);
                                        }else {

                                            FirebaseFirestore.getInstance().collection("titulos")
                                                    .document(tit2.getKeytit())
                                                    .set(tit2)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            util.showSnackOk(clcad, "Cliente salvo com sucesso.");
                                                            MainActivity.navController.navigate(R.id.action_frag_Cad_Cli_to_home);
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


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

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

    private void sssMenInt(){

        if(!menTit1.equals("")){
            Log.i("mmins","salva em "+menTit1 + " - "+mm1.getInstall());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            WriteBatch batch = db.batch();
            DocumentReference dr = db.collection(menTit1).document(instalacao.getNuminstal());
            batch.set(dr, mm1);
            if (!menTit2.equals("")) {
                dr = db.collection(menTit2).document(instalacao.getNuminstal());
                batch.set(dr, mm2);
            }

            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    ssstitulos();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mprogressBar.setVisibility(View.INVISIBLE);
                    util.showSnackOk(clcad, "Problemas ao salvar Mensalidade. Informe administrador.");

                }
            });
        }

    }

    private void salvaTudo() {

        if(cliente==null){
            util.showSnackError(clcad,"Problemas no cadastro, verifique preenchimento");
            return;
        }else if(instalacao==null){
            util.showSnackError(clcad,"Problemas no cadastro, verifique preenchimento");
            return;
        } else if(tit1==null){
            util.showSnackError(clcad,"Problemas no cadastro, verifique preenchimento");
            return;
        } else if(titad==null){
            util.showSnackError(clcad,"Problemas no cadastro, verifique preenchimento");
            return;
        }else{

            FirebaseFirestore.getInstance().collection("clientes")
                    .document(KEYCLI)
                    .set(cliente)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sssint();
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

    }

    public void vctoMaiorInstall(){

        strDate = YEAR+"-"+MONTH+"-"+DAY;
        boolean bissexto = util.Bisessexto(iyear);
        dataInstall = LocalDate.parse(strDate);
        Log.i("datasvcto","ano bissexto: " +bissexto );


        iyear=Integer.parseInt(YEAR) ;
        imonth=Integer.parseInt(MONTH) ;
        iday=Integer.parseInt(diavcto) ;
        proxyear=Integer.parseInt(YEAR);
        proxmonth=Integer.parseInt(MONTH) ;
        proxday=Integer.parseInt(diavcto) ;

        proxmonth++;
        if(proxmonth==13){
            proxmonth=1;
            proxyear++;
        }

        if(proxmonth==2 && proxday>28){

            if (bissexto) {
                if(proxday==29){
                    proxday=1;
                    proxmonth=3;
                }
                if(proxday==30){
                    proxday=1;
                    proxmonth=3;
                }
            }else{
                if(proxday==29){
                    proxday=1;
                    proxmonth=3;
                }
                if(proxday==30){
                    proxday=2;
                    proxmonth=3;
                }
            }
        }


        if(imonth==2 && iday>28){

            if (bissexto) {
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=1;
                    imonth=3;
                }
            }else{
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=2;
                    imonth=3;
                }
            }
        }

        pnyear=String.valueOf(proxyear);
        pnmonth = String.format("%02d", new Object[] { proxmonth });
        pnday = String.format("%02d", new Object[] { proxday });
        pstrDate=(pnyear+"-"+pnmonth+"-"+pnday);
        dataVctoprox = LocalDate.parse(pstrDate);


        nyear=String.valueOf(iyear);
        nmonth = String.format("%02d", new Object[] { imonth });
        nday = String.format("%02d", new Object[] { iday });
        strDate=(nyear+"-"+nmonth+"-"+nday);
        dataVcto = LocalDate.parse(strDate);

        sii = dataInstall.format(fmt);
        svv = dataVcto.format(fmt);
        pvv = dataVctoprox.format(fmt);
        Log.i("datasvcto","install: " + sii + "  -  vcto: " + svv + "  -  proximo vcto: " +pvv );
        Log.i("datasvcto"," ");

        daysBetween = DAYS.between( dataInstall,dataVcto);
        dias_a_cobrar = new BigDecimal(daysBetween);
        valor_plano = bigtotal;// new BigDecimal(util.S_to_Big(valor_mesalidade));
        Log.i("datasvcto","valor_plano:" + valor_plano );
        Log.i("datasvcto","dias_a_cobrar:" + dias_a_cobrar );
        valor_dia = valor_plano.divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valordia: " + valor_dia);
        valor_plano_prop = valor_dia.multiply(dias_a_cobrar);
        valor_plano_prop = valor_plano_prop.divide(new BigDecimal("1"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valor_plano_prop: " + valor_plano_prop);
        valor_plano_acrescido = valor_plano.add(valor_plano_prop);
        Log.i("datasvcto", "valor_plano_acrescido: " + valor_plano_acrescido);
        Log.i("datasvcto", " ");

        if(valor_plano_prop.compareTo(new BigDecimal(util.S_to_Big(Home.Settings.getValor_min_boleto())))==-1){
            Alerta_Gera_Boleto alerta_gera_boleto = new Alerta_Gera_Boleto(
                    String.valueOf(daysBetween),
                    util.Big_to_S(valor_plano),
                    util.Big_to_S(valor_plano_prop),
                    util.Big_to_S(valor_plano_acrescido),
                    dataVcto.format(fmt),
                    dataVctoprox.format(fmt));
            alerta_gera_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        }else{
            valorfinal=util.Big_to_S(valor_plano_prop);
            vctofinal=dataVcto.format(fmt);
            numeroBol=3;
            geravalores=1;
            geraTitulo_1_Titulo();
        }


    }

    public void vctoMenorInstall(){

        strDate = YEAR+"-"+MONTH+"-"+DAY;

//        definir dia de vencimento titulo

        boolean bissexto = util.Bisessexto(iyear);
        dataInstall = LocalDate.parse(strDate);
        Log.i("datasvcto","ano bissexto: " +bissexto );


        iyear=Integer.parseInt(YEAR) ;
        imonth=Integer.parseInt(MONTH) ;
        iday=Integer.parseInt(diavcto) ;
        proxyear=Integer.parseInt(YEAR);
        proxmonth=Integer.parseInt(MONTH) ;
        proxday=Integer.parseInt(diavcto) ;

        proxmonth++;
        if(proxmonth==13){
            proxmonth=2;
            proxyear++;
        }else{
            proxmonth++;
            if(proxmonth==13){
                proxmonth=1;
                proxyear++;
            }
        }

        if(proxmonth==2 && proxday>28){

            if (bissexto) {
                if(proxday==29){
                    proxday=1;
                    proxmonth=3;
                }
                if(proxday==30){
                    proxday=1;
                    proxmonth=3;
                }
            }else{
                if(proxday==29){
                    proxday=1;
                    proxmonth=3;
                }
                if(proxday==30){
                    proxday=2;
                    proxmonth=3;
                }
            }
        }

        imonth++;
        if(imonth==13){
            imonth=1;
            iyear++;
        }


        if(imonth==2 && iday>28){

            if (bissexto) {
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=1;
                    imonth=3;
                }
            }else{
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=2;
                    imonth=3;
                }
            }
        }

        pnyear=String.valueOf(proxyear);
        pnmonth = String.format("%02d", new Object[] { proxmonth });
        pnday = String.format("%02d", new Object[] { proxday });
        pstrDate=(pnyear+"-"+pnmonth+"-"+pnday);
        dataVctoprox = LocalDate.parse(pstrDate);


        nyear=String.valueOf(iyear);
        nmonth = String.format("%02d", new Object[] { imonth });
        nday = String.format("%02d", new Object[] { iday });
        strDate=(nyear+"-"+nmonth+"-"+nday);
        dataVcto = LocalDate.parse(strDate);

        sii = dataInstall.format(fmt);
        svv = dataVcto.format(fmt);
        pvv = dataVctoprox.format(fmt);
        Log.i("datasvcto","install: " + sii + "  -  vcto: " + svv + "  -  proximo vcto: " +pvv );
        Log.i("datasvcto"," ");

        daysBetween = DAYS.between( dataInstall,dataVcto);
        dias_a_cobrar = new BigDecimal(daysBetween);
        valor_plano = bigtotal;//new BigDecimal(util.S_to_Big(valor_mesalidade));
        Log.i("datasvcto","valor_plano:" + valor_plano );
        Log.i("datasvcto","dias_a_cobrar:" + dias_a_cobrar );
        valor_dia = valor_plano.divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valordia: " + valor_dia);
        valor_plano_prop = valor_dia.multiply(dias_a_cobrar);
        valor_plano_prop = valor_plano_prop.divide(new BigDecimal("1"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valor_plano_prop: " + valor_plano_prop);
        valor_plano_acrescido = valor_plano.add(valor_plano_prop);
        Log.i("datasvcto", "valor_plano_acrescido: " + valor_plano_acrescido);
        Log.i("datasvcto", " ");

        if(valor_plano_prop.compareTo(new BigDecimal(util.S_to_Big(Home.Settings.getValor_min_boleto())))==-1){
            Alerta_Gera_Boleto alerta_gera_boleto = new Alerta_Gera_Boleto(
                    String.valueOf(daysBetween),
                    util.Big_to_S(valor_plano),
                    util.Big_to_S(valor_plano_prop),
                    util.Big_to_S(valor_plano_acrescido),
                    dataVcto.format(fmt),
                    dataVctoprox.format(fmt));
            alerta_gera_boleto.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
        }else{
            valorfinal=util.Big_to_S(valor_plano_prop);
            vctofinal=dataVcto.format(fmt);
            numeroBol=3;
            geravalores=2;
            geraTitulo_1_Titulo();
        }

    }

    public void vctoIgualInstall(){
        strDate = YEAR+"-"+MONTH+"-"+DAY;

//        definir dia de vencimento titulo

        boolean bissexto = util.Bisessexto(iyear);
        dataInstall = LocalDate.parse(strDate);
        Log.i("datasvcto","ano bissexto: " +bissexto );


        iyear=Integer.parseInt(YEAR) ;
        imonth=Integer.parseInt(MONTH) ;
        iday=Integer.parseInt(diavcto) ;
        proxyear=Integer.parseInt(YEAR);
        proxmonth=Integer.parseInt(MONTH) ;
        proxday=Integer.parseInt(diavcto) ;

        imonth++;
        if(imonth==13){
            imonth=1;
            iyear++;
        }


        if(imonth==2 && iday>28){

            if (bissexto) {
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=1;
                    imonth=3;
                }
            }else{
                if(iday==29){
                    iday=1;
                    imonth=3;
                }
                if(iday==30){
                    iday=2;
                    imonth=3;
                }
            }
        }

        nyear=String.valueOf(iyear);
        nmonth = String.format("%02d", new Object[] { imonth });
        nday = String.format("%02d", new Object[] { iday });
        strDate=(nyear+"-"+nmonth+"-"+nday);
        dataVcto = LocalDate.parse(strDate);

        sii = dataInstall.format(fmt);
        svv = dataVcto.format(fmt);
        Log.i("datasvcto","install: " + sii + "  -  vcto: " + svv + "  -  proximo vcto: null"  );
        Log.i("datasvcto"," ");

        daysBetween = DAYS.between( dataInstall,dataVcto);
        dias_a_cobrar = new BigDecimal(daysBetween);
        valor_plano = bigtotal;//new BigDecimal(util.S_to_Big(valor_mesalidade));
        Log.i("datasvcto","valor_plano:" + valor_plano );
        Log.i("datasvcto","dias_a_cobrar:" + dias_a_cobrar );
        valor_dia = valor_plano.divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valordia: " + valor_dia);
        valor_plano_prop = valor_dia.multiply(dias_a_cobrar);
        valor_plano_prop = valor_plano_prop.divide(new BigDecimal("1"), 2, RoundingMode.HALF_UP);
        Log.i("datasvcto", "valor_plano_prop: " + valor_plano_prop);
        valor_plano_acrescido = valor_plano.add(valor_plano_prop);
        Log.i("datasvcto", "valor_plano_acrescido: " + valor_plano_acrescido);
        Log.i("datasvcto", " ");

        valorfinal=util.Big_to_S(valor_plano);
        vctofinal=dataVcto.format(fmt);
        numeroBol=3;
        geravalores=0;
        geraTitulo_1_Titulo();
    }

    private void totalprodutos() {
        bigtotal =zero;
        bigtotal=bigtotal.add(bigplano).add(bigtv);
        txfvtotal.setText(df.format(bigtotal));
    }

    public void limpaFoco(){
        hideSoftKeyboard();
        edtppoe.clearFocus();
        edtmega.clearFocus();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence c, int start, int before, int count) {

            if (edtppoe.hasFocus()) {
                txtcontppoe.setText(c.length() + "/15");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}