package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_tipo_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Instal;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Link;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Produto;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Dia_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Tipo_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.model.Type_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Frag_Editar_Install extends Fragment {

    private HomeViewModel homeViewModel;

    private EditText edtendereco,edtcompl,edtnumero,edtcep,edtppoe,edtmega;
    private TextView txtcontend,txtcontcompl,txtcontppoe,txtnome,txtdata ,txtvcto,txtinstal;
    private TextView txfplano,txftv,txfinstal,txfvplano,txfvtv,txfvinstal,txfvtotal,txtreccli,txttipomensal;

    private Spinner spsetor,sptipocob,spnomecob,spplano,splink,sptv,spstat;
    private ConstraintLayout clcad;
    private ProgressBar mprogressBar;
    private Button btcad,btcancel;
    private LinearLayout llstat;
    private ImageView imgrecar;



    private String ENDERECO,NUMERO,CEP,COMPL,INSTAL,SETOR,LINK,PLANO,TV,COBRANCA,VCTO,PPOE,ADESAO,KEYINSTALL,KEYCLI,NUMINSTALL,USUMAKER,
            VALORPLANO, VALORTV,VALORADESAO,MEGA;
    private int STATUS,STATUSADESAO;
    private long TIMESTAMP;

    private Adapter_Setor adapter_setor;
    private Adapter_Instal adapter_instal;
    private Adapter_Link adapter_link;
    private Adapter_tipo_Cobranca adapter_tipo_cobranca;
    private Adapter_Produto adapter_plano;
    private Adapter_Produto adapter_tv;
    private Adapter_Produto adapter_adesao;
    private Adapter_Cobrancas adapter_cobrancas;
    private ArrayList<Setores> bsetores = new ArrayList<>();
    private ArrayList<Links> blinks = new ArrayList<>();
    private ArrayList<Cobrancas> bcobrancas = new ArrayList<>();
    private ArrayList<Tabela_Produtos> bplanos = new ArrayList<>();
    private ArrayList<Tabela_Produtos> btv = new ArrayList<>();
    private ArrayList<Tabela_Produtos> badesao = new ArrayList<>();

    private BigDecimal zero;
    private BigDecimal biginstal;
    private BigDecimal bigtv;
    private BigDecimal bigplano;
    private BigDecimal bigtotal;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    private ArrayAdapter<String> adaptcob;
    private ArrayList<Type_Cobranca> btypecob = new ArrayList<>();

    private Clientes cliente;
    private Instalacoes installOld;
    private int origemInstall;
    private ArrayList<String> sstat;

    private boolean oldinst = false;

    public Frag_Editar_Install() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_frag__editar__install, container, false);
       initViews(v);
        return v;
    }

    private void initViews(View v) {


        homeViewModel= Home.homeViewModel;

        ENDERECO="";NUMERO="";CEP="";COMPL="";
        INSTAL="";SETOR="";LINK="";PLANO="";TV="";COBRANCA="";VCTO="";PPOE="";ADESAO="";KEYINSTALL="";KEYCLI="";NUMINSTALL="";USUMAKER="";
        VALORPLANO="";VALORTV="";VALORADESAO="";MEGA="";
        STATUSADESAO=0;
        STATUS=0;

        zero =new BigDecimal("0.00");
        biginstal =new BigDecimal("0.00");
        bigtv =new BigDecimal("0.00");
        bigplano =new BigDecimal("0.00");
        bigtotal =new BigDecimal("0.00");

        cliente= HomeViewModel.getClientes();
        installOld=HomeViewModel.getInstall();


        txtnome                =v.findViewById(R.id.txtnome         );
        txtdata                =v.findViewById(R.id.txtdata         );
        edtppoe                =v.findViewById(R.id.edtppoe         );
        edtmega                 =v.findViewById(R.id.edtmega        );
        txtcontppoe             =v.findViewById(R.id.txtcontppoe    );
        txtreccli=v.findViewById(R.id.txtreccli    );
        txttipomensal             =v.findViewById(R.id.txttipomensal    );


        txfplano             =v.findViewById(R.id.txfplano    );
        txftv                =v.findViewById(R.id.txftv       );
        txfinstal             =v.findViewById(R.id.txfinstal     );
        txfvplano             =v.findViewById(R.id.txfvplano     );
        txfvtv                =v.findViewById(R.id.txfvtv        );
        txfvinstal            =v.findViewById(R.id.txfvinstal    );
        txfvtotal            =v.findViewById(R.id.txfvtotal    );
        txtvcto=v.findViewById(R.id.txtvcto    );
        txtinstal=v.findViewById(R.id.txtinstal    );
        llstat = v.findViewById(R.id.llstat);
       // llfin = v.findViewById(R.id.llfin);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        spsetor=v.findViewById(R.id.spsetor);
      //  spinstal=v.findViewById(R.id.spinstal);
        spnomecob=v.findViewById(R.id.spnomecob);
        spplano=v.findViewById(R.id.spplano);
        splink=v.findViewById(R.id.splink);
        spstat=v.findViewById(R.id.spstat);
        sptv=v.findViewById(R.id.sptv);
    //    sptipocob=v.findViewById(R.id.sptipocob);


        btypecob=Home.baseTypeCobranca;
        clcad=v.findViewById(R.id.clcad);
        mprogressBar=v.findViewById(R.id.mprogressBar);
        imgrecar=v.findViewById(R.id.imgrecar);

        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                limpaFoco();
            }
        });
        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_frag_Editar_Install_to_frag_Consulta_Cli);
            }
        });

        txtreccli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                if(origemInstall==1)
                    txtreccli.setText("Refresh Install");
                    carregaInstall();
            }
        });
        imgrecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtreccli.callOnClick();
            }
        });

        homeViewModel.getVctobol().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bol) {
                if(bol){
                    VCTO=homeViewModel.getDiavcto().toString();
                    txtvcto.setText(VCTO);
                }
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

      ;

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
        badesao.clear();
        btv.clear();
        bplanos.add(tp);
        badesao.add(tp);
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
                util.vibratePhone(getContext(), (short) 20);
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
                util.vibratePhone(getContext(), (short) 2);
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






        final ArrayAdapter<CharSequence> adaptervcto = ArrayAdapter.createFromResource(getContext(),
                R.array.vcto, R.layout.sp_prod);
        adaptervcto.setDropDownViewResource(R.layout.sp_prod );

        sstat = new ArrayList<>();
        sstat.clear();
        sstat.add("Ativa");
        sstat.add("Inativa");

        final ArrayAdapter<String> adaptstat= new ArrayAdapter<String>(getContext(),R.layout.sp_prod,sstat);
        adaptstat.setDropDownViewResource(R.layout.sp_prod );
        spstat.setAdapter(adaptstat);

        spstat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);
                hideSoftKeyboard();
                limpaFoco();
                if(STATUS==3){
                    util.showSnackError(clcad,"Instalação inadimplente não pode ser Inativada.");
                      llstat.setBackgroundColor(Color.RED);
                    return;
                }else{
                    STATUS = position + 1;
                    switch (STATUS) {
                        case 1:
                            llstat.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            llstat.setBackgroundColor(Color.GRAY);
                            break;
                        default:
                            llstat.setBackgroundColor(Color.GRAY);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


            btcad.setText("Alterar");
            carregaInstall();

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

        edtppoe.addTextChangedListener(textWatcher);

    }

    private void carregaInstall() {

        KEYINSTALL= installOld.getKeyinstalacao();
        CEP=installOld.getCep();
        KEYCLI=installOld.getCliente();
        COMPL=installOld.getComplemento();
        ENDERECO=installOld.getEndereco();
        NUMERO=installOld.getNumero();
        NUMINSTALL=installOld.getNuminstal();
        SETOR=installOld.getSetor();
        TIMESTAMP=installOld.getTimestamp();
        STATUS=installOld.getStatus();
        LINK=installOld.getLink();
        PLANO=installOld.getPlano();
        TV=installOld.getTv();
        COBRANCA=installOld.getCobranca();
        VCTO=installOld.getVcto();
        PPOE=installOld.getPpoe();
        INSTAL=installOld.getNuminstal();
        USUMAKER=installOld.getUsumaker();
        VALORPLANO=installOld.getPlano();
        VALORTV=installOld.getTv();
        VALORADESAO=installOld.getValorAdesao();
        MEGA=String.valueOf(installOld.getMegainstal());

        txtinstal.setText(INSTAL);

        for(int i=0;i<bcobrancas.size();i++){
            if(bcobrancas.get(i).getKey_cobranca().equals(installOld.getCobranca())){
                spnomecob.setSelection(i);
                for(int j=0;j<btypecob.size();j++){
                    if(btypecob.get(j).getTipo_type_cob()==bcobrancas.get(i).getType_cob()){
                        txttipomensal.setText(btypecob.get(j).getNome_type_cob());
                    }
                }
            }
        }

        biginstal=new BigDecimal(util.S_to_Big(VALORADESAO));
        txfvinstal.setText(df.format(biginstal));
        totalprodutos();

        Instant it = Instant.ofEpochMilli(TIMESTAMP);
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        String scomp = dataenvio.format(fmt);
        txtdata.setText(scomp);
        txtnome.setText(cliente.getNome());
        edtppoe.setText(PPOE);
        edtmega.setText(MEGA);
        for(int i=0;i<bsetores.size();i++){
            if(bsetores.get(i).getKeysetor().equals(installOld.getSetor())){
                spsetor.setSelection(i);
                break;
            }
        }

        for(int i=0;i<blinks.size();i++){
            if(blinks.get(i).getKeylink().equals(installOld.getLink())){
                splink.setSelection(i);
                break;
            }
        }

        for(int i=0;i<bplanos.size();i++){
            if(bplanos.get(i).getKeyproduto().equals(installOld.getPlano())){
                spplano.setSelection(i);
                break;
            }
        }
        for(int i=0;i<btv.size();i++){
            if(btv.get(i).getKeyproduto().equals(installOld.getTv())){
                sptv.setSelection(i);
                break;
            }
        }
        if(installOld.getStatus()==3){
            sstat.clear();
            sstat.add("Inadimplente");
        }else{
            sstat.clear();
            sstat.add("Ativa");
            sstat.add("Inativa");
        }

        switch (installOld.getStatus()){
            case 1:spstat.setSelection(0);
                spstat.setClickable(true);
                spstat.setEnabled(true);
                break;
            case 2:spstat.setSelection(1);
                spstat.setClickable(true);
                spstat.setEnabled(true);
                break;
            case 3:spstat.setSelection(0);
                llstat.setBackgroundColor(Color.RED);
                spstat.setClickable(false);
                spstat.setEnabled(false);
                break;
        }

        txtvcto.setText(installOld.getVcto());

    }

    private void totalprodutos() {
        bigtotal =zero;
        bigtotal=bigtotal.add(bigplano).add(bigtv);
        txfvtotal.setText(df.format(bigtotal));

    }


    private void salvar() {

        limpaFoco();

        if(ENDERECO.equals("")){
            util.showSnackError(clcad,"Digite um Endereço para Instalação.");
            return;
        }else if(NUMERO.equals("")){
            util.showSnackError(clcad,"Digite um Número para o endereço de Instalação.");
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
        }else if(PPOE.equals("")) {
            util.showSnackError(clcad, "Digite um Ppoe para a Instalção.");
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

        if (!PPOE.equals(installOld.getPpoe())) {
            for(int i=0;i<Home.baseInstall.size();i++){
                if(Home.baseInstall.get(i).getPpoe()!=null && Home.baseInstall.get(i).getPpoe().equals(PPOE)){
                    temppoe=true;
                    break;
                }
            }
        }

        if(temppoe){
            util.showSnackError(clcad, "Já existe um Ppoe com esse nome.");
            return;
        }else{
            cadastraInstall();
        }
    }

    private void cadastraInstall() {

        final Instalacoes iins = installOld;
        int stt = 0;
        if(installOld.getStatus()==3){
            stt=3;
        }else {
            stt = spstat.getSelectedItemPosition() + 1;
        }
        iins.setSetor(SETOR);
        iins.setStatus(stt);
        iins.setLink(LINK);
        iins.setPlano(PLANO);
        iins.setTv(TV);
        iins.setCobranca(COBRANCA);
        iins.setVcto(VCTO);
        iins.setPpoe(PPOE);
        iins.setUsumaker(HomeViewModel.getUsuario().getKeyusu());
        iins.setStatusMensal(0);
        iins.setMegainstal(Integer.parseInt(MEGA));

        for(int i=0;i<Home.instalcli.size();i++){
            if(Home.instalcli.get(i).getNuminstal().equals(iins.getNuminstal())){
                Home.instalcli.set(i,iins);
            }
        }

        FirebaseFirestore.getInstance().collection("instalacoes")
                .document(INSTAL)
                .set(iins)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showSnackAsk(clcad, "Títuos gerados anteriormente não são alterados.");
                        HomeViewModel.setInstall(iins);

                        MainActivity.navController.navigate(R.id.action_frag_Editar_Install_to_frag_Consulta_Cli);
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