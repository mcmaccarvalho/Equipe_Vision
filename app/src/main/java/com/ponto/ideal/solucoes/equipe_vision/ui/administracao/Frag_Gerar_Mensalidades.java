package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Conex_Gera;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Gera_Individual;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Mensalidades_Instal;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Frag_Gerar_Mensalidades extends Fragment {

    private ConstraintLayout cltit;
    private RecyclerView rvtitulos;
    private TextView txtbase,txtgerar;
    private RadioGroup rgsel;
    private RadioButton rbsel,rbtodos;
    private ImageView imgleft, imgright;
    private GroupAdapter adapter;
    private Button btcad;
    private ProgressBar mprogressBar;

    private HomeViewModel homeViewModel;

    private Instant it;
    private ZonedDateTime zdt;

    private LocalDate ld_mes_a_gerar;
    private LocalDate ld_hojemax;


    public static boolean geratodos=true;
    private static boolean blockAdapt=false;

    public static ArrayList<Instalacoes> Instal_a_Gerar = new ArrayList<>();
    public static ArrayList<Instalacoes> arrayInstal = new ArrayList<>();
    public static ArrayList<String> insagerar = new ArrayList<>();

    private  ArrayList<Cobrancas> arraycobrancas;
    private  ArrayList<Clientes> clientes;
    private  ArrayList<Tabela_Produtos> produtos;

    private Instalacoes instalsalva = new Instalacoes();
    private Titulos titsalva = new Titulos();

    DateTimeFormatter mesano = DateTimeFormatter
            .ofPattern("MM-yyyy")
            .withResolverStyle(ResolverStyle.STRICT);

    DateTimeFormatter diamesano = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withResolverStyle(ResolverStyle.STRICT);


    private int num_a_gerar;
    private int ctrlgerar;

    private BigDecimal bigtv;
    private BigDecimal bigplano;
    private BigDecimal bigtotal;
    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    public Frag_Gerar_Mensalidades() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_frag__gerar__mensalidades, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        mprogressBar=v.findViewById(R.id.mprogressBar);
        txtbase=v.findViewById(R.id.txtbase);
        txtgerar=v.findViewById(R.id.txtgerar);
        imgleft=v.findViewById(R.id.imgleft);
        imgright=v.findViewById(R.id.imgright);
        rgsel=v.findViewById(R.id.rgsel);
        rbsel=v.findViewById(R.id.rbsel);
        rbtodos=v.findViewById(R.id.rbtodos);
        cltit=v.findViewById(R.id.cltit);
        rvtitulos=v.findViewById(R.id.rvtitulos);
        btcad=v.findViewById(R.id.btcad);


        homeViewModel=Home.homeViewModel;

        ld_mes_a_gerar = util.Dia_01_Mes(System.currentTimeMillis());
        ld_hojemax = ld_mes_a_gerar;
        ld_hojemax=ld_hojemax.plusMonths(1);

        homeViewModel.getBolgera().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if(b){
                  //  atualizaIns();
                    homeViewModel.setBolaltins(false);
                }
            }
        });
        txtbase.setText(ld_mes_a_gerar.format(mesano));

        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ld_mes_a_gerar=ld_mes_a_gerar.minusMonths(1);
                txtbase.setText(ld_mes_a_gerar.format(mesano));
                mprogressBar.setVisibility(View.VISIBLE);
                Log.i("datasgerar","ld_mes_a_gerar lef"+ ld_mes_a_gerar.format(diamesano));
                lista_ja_gerada();
            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ld_hojemax.compareTo(ld_mes_a_gerar)<=0){
                    util.showSnackError(cltit,"Não é permitido gerar mensalidades além dessa data.");
                    return;
                }
                bloqqueiaTD(false);
                ld_mes_a_gerar=ld_mes_a_gerar.plusMonths(1);
                txtbase.setText(ld_mes_a_gerar.format(mesano));
                mprogressBar.setVisibility(View.VISIBLE);
                Log.i("datasgerar","ld_mes_a_gerar rig"+ ld_mes_a_gerar.format(diamesano));
                lista_ja_gerada();
            }
        });

        rbtodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(geratodos)return;
                if(rbtodos.isChecked()){
                    geratodos=true;

                }else{
                    geratodos=false;

                }
                btcad.setEnabled(true);
                btcad.setClickable(true);
                if(arrayInstal.size()==0){
                    txtgerar.setText("Não há mensalidades a gerar para esta data.");
                }else if(arrayInstal.size()==1){
                    txtgerar.setText("Será gerada "+ arrayInstal.size() +" mensalidade.");
                }else{
                    txtgerar.setText("Serão geradas "+ arrayInstal.size() +" mensalidades.");
                }
                Log.i("geratodos","geraT " + geratodos);
            }
        });

        rbsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!geratodos)return;
                if(rbsel.isChecked()){
                    geratodos=false;

                }else{
                    geratodos=true;

                }
                btcad.setEnabled(false);
                btcad.setClickable(false);
                txtgerar.setText("Selecione instalação p/ gerar a Mensalidade.");

                Log.i("geratodos","geraT " + geratodos);
            }
        });


        arraycobrancas = Home.baseCobrancas;
        clientes=Home.baseClientes;
        produtos=Home.baseProdutos;
        homeViewModel=Home.homeViewModel;

        adapter = new GroupAdapter();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if(rbsel.isChecked() && !blockAdapt) {
                    util.vibratePhone(getContext(), (short) 20);
                    ListaItem listaItem = (ListaItem) item;
                    Instalacoes it = listaItem.install;
                    instalsalva = it;
                    bloqqueiaTD(true);
                    Alerta_Gera_Individual alerta_gera_individual = new Alerta_Gera_Individual((it.getNuminstal()+"("+it.getMegainstal()+")"),ld_mes_a_gerar.format(mesano));
                    alerta_gera_individual.show(getActivity().getSupportFragmentManager(), "DialogTeclado");

                }
            }
        });


        homeViewModel.getNumerobol().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer bol) {

                switch (bol) {

                    case 15:
                       util.showSnackOk(cltit,"Gera");
                       // bloqqueiaTD(false);
                        mprogressBar.setVisibility(View.VISIBLE);
                        ctrlgerar = 0;
                        geraTodas();
                        break;
                    case 25:
                        bloqqueiaTD(false);
                       // util.showSnackOk(cltit,"Cancela");
                        break;
                    case 45:
                        bloqqueiaTD(false);
                       // util.showSnackOk(cltit,"Cancela");
                        break;
                    case 55:
                        mprogressBar.setVisibility(View.VISIBLE);
                        geraIndividual();
                        break;

                }
            }
        });

        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloqqueiaTD(true);

                    util.vibratePhone(getContext(),(short)20);
                    num_a_gerar=arrayInstal.size();
                    ctrlgerar=0;
                    if (num_a_gerar == 0) {
                        util.showSnackAsk(cltit, "Não existem mensalidades a gerar.");
                        bloqqueiaTD(false);
                        return;
                    }
                    Alerta_Conex_Gera alerta_conex_gera = new Alerta_Conex_Gera();
                    alerta_conex_gera.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });

        mprogressBar.setVisibility(View.VISIBLE);
        lista_ja_gerada();

    }

    private void bloqqueiaTD(boolean b) {

        imgright.setClickable(!b);
        imgright.setEnabled(!b);
        imgleft.setClickable(!b);
        imgleft.setEnabled(!b);
        rbsel.setClickable(!b);
        rbsel.setEnabled(!b);
        rbtodos.setClickable(!b);
        rbtodos.setEnabled(!b);
        btcad.setClickable(!b);
        btcad.setEnabled(!b);
        blockAdapt = b;
        MainActivity.blobk = b;


    }

    private void lista_ja_gerada() {

        final ArrayList<String> install_gerada = new ArrayList<>();
        install_gerada.clear();
        String agerar =ld_mes_a_gerar.format(diamesano);

        FirebaseFirestore.getInstance().collection(agerar)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                install_gerada.add(document.getString("install"));
                            }
                            Log.i("campgera", "install_gerada " + install_gerada.size() );
                            carregaAdapter(install_gerada);

                        } else {
                            Log.i("listmen", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void carregaAdapter(ArrayList<String> insgerada) {

        long longdatagerar = util.longdiamesano(ld_mes_a_gerar.format(diamesano));

        Instal_a_Gerar.clear();
        arrayInstal.clear();
        adapter.clear();

        for(int i=0;i<Home.baseInstall.size();i++){

            LocalDate ldcad = util.Dia_01_Mes(Home.baseInstall.get(i).getTimestamp());
            long longdatacad=util.longdiamesano(ldcad.format(diamesano));

            Log.i("campgera", "install: "+Home.baseInstall.get(i).getNuminstal()+"ldcad01 " + ldcad.format(diamesano));

            if (!insgerada.contains(Home.baseInstall.get(i).getNuminstal().toString())){
                if (Home.baseInstall.get(i).getStatus()!=2){
                    if(Home.baseInstall.get(i).getTimestamp()<=longdatagerar) {
                        arrayInstal.add(Home.baseInstall.get(i));
                        adapter.add(new ListaItem(Home.baseInstall.get(i)));
                    }
                }
            }
        }

        if(arrayInstal.size()==0){
            txtgerar.setText("Não há mensalidades a gerar para esta data.");
        }else if(arrayInstal.size()==1){
            txtgerar.setText("Será gerada "+ arrayInstal.size() +" mensalidade.");
        }else{
            txtgerar.setText("Serão geradas "+ arrayInstal.size() +" mensalidades.");
        }
        mprogressBar.setVisibility(View.INVISIBLE);

        adapter.notifyDataSetChanged();
        rvtitulos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvtitulos.setAdapter(adapter);
    }

    private void listaTit(Titulos tit,Instalacoes iins) {

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);

        Log.i("listatit", "getKeytit: "+tit.getKeytit());
        Log.i("listatit", "getNomeclientetit: "+tit.getNomeclientetit());
        Log.i("listatit", "getKeyclientetit: "+tit.getKeyclientetit());
        Log.i("listatit", "getInstalltit: "+tit.getInstalltit());
        Log.i("listatit", " ");
        Log.i("listatit", "getTipocobtit: "+tit.getTipocobtit());
        Log.i("listatit", "getKeycobtit: "+tit.getKeycobtit());
        Log.i("listatit", "getNomecobtit: "+tit.getNomecobtit());
        Log.i("listatit", "getContacobtit: "+tit.getContacobtit());
        Log.i("listatit", "getBarrastit: "+tit.getBarrastit());
        Log.i("listatit", " ");
        Log.i("listatit", "getNumerotit: "+tit.getNumerotit());
        Log.i("listatit", "getDesctit: "+tit.getDesctit());
        Log.i("listatit", "getProdutotit: "+tit.getProdutostit());
        Log.i("listatit", "getKeyplanotit: "+tit.getKeyplanotit());
        Log.i("listatit", "getKeytvtit: "+tit.getKeytvtit());
        Log.i("listatit", "getKeygeraltit: "+tit.getKeygeraltit());
        Log.i("listatit", "getSituacaotit: "+tit.getSituacaotit());
        Log.i("listatit", "getGeradobanco: "+tit.getGeradobanco());
        Log.i("listatit", "getKeyusubaixa: "+tit.getKeyusubaixa());
        Log.i("listatit", "getKeyusucancel: "+tit.getKeyusucancel());
        Log.i("listatit", "getStatustit: "+tit.getStatustit());
        Log.i("listatit", "getKeylinktit: "+tit.getKeylinktit());
        Log.i("listatit", "getKeysetortit: "+tit.getKeysetortit());
        Log.i("listatit", " ");
        Instant it = Instant.ofEpochMilli(tit.getVencimentotit());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate ll = zdt.toLocalDate();
        Log.i("listatit", "getVencimentotit: "+ll.format(fmt));
        Log.i("listatit", "getDatapgtotit: "+tit.getDatapgtotit());
        it = Instant.ofEpochMilli(tit.getComptit());
        zdt = it.atZone(ZoneId.systemDefault());
        ll = zdt.toLocalDate();
        Log.i("listatit", "getComptit: "+ll.format(fmt));
        Log.i("listatit", " ");
        Log.i("listatit", "getValorgeral: "+tit.getValorgeral());
        Log.i("listatit", "getValortv: "+tit.getValortv());
        Log.i("listatit", "getValorplano: "+tit.getValorplano());
        Log.i("listatit", "getValortv: "+tit.getValortv());
        Log.i("listatit", "getValortit: "+tit.getValortit());


    }

    private void geraIndividual() {
        titsalva=geraTitulo(instalsalva);
        listaTit(titsalva,instalsalva);
        salvaTítulo(titsalva,instalsalva);
    }

    private void geraTodas() {

        if(ctrlgerar>=num_a_gerar){
            util.showSnackOk(cltit,"Mensalidades geradas com sucesso!!");
            bloqqueiaTD(false);
            lista_ja_gerada();
        }else{
            instalsalva=arrayInstal.get(ctrlgerar);
            titsalva=geraTitulo(instalsalva);
            listaTit(titsalva,instalsalva);
            salvaTítulo(titsalva,instalsalva);
        }

    }

    private Titulos geraTitulo(Instalacoes instalacao){

        Instalacoes iins = instalacao;

        Clientes cli = new Clientes();
        for(int i=0;i<clientes.size();i++){
            if(iins.getCliente().equals(clientes.get(i).getKeycli())){
                cli=clientes.get(i);
                break;
            }
        }

        int tcob=0;
        String ncob="";
        String kcob="";
        String contacob="";
        for(int i = 0; i<arraycobrancas.size(); i++){
            if(arraycobrancas.get(i).getKey_cobranca().equals(iins.getCobranca())){
               tcob= arraycobrancas.get(i).getType_cob();
               ncob= arraycobrancas.get(i).getNome_cobranca();
               kcob= arraycobrancas.get(i).getKey_cobranca();
               contacob= arraycobrancas.get(i).getConta_cobranca();
            }

        }

        Titulos tt = new Titulos();

        tt.setKeytit(UUID.randomUUID().toString());
        tt.setKeyclientetit(cli.getKeycli());
        tt.setNomeclientetit(cli.getNome());
        tt.setInstalltit(iins.getNuminstal());
        tt.setTipocobtit(tcob);
        tt.setNomecobtit(ncob);
        tt.setKeycobtit(kcob);
        tt.setContacobtit(contacob);
        tt.setBarrastit("");

        tt.setKeyplanotit(iins.getPlano());
        tt.setKeytvtit(iins.getTv());
        tt.setKeygeraltit("");

        String VALORPLANOTIT="";
        String VALORTVTIT="";

        for (int i=0;i<produtos.size();i++){
            if(produtos.get(i).getKeyproduto().equals(iins.getPlano())){
                VALORPLANOTIT=produtos.get(i).getValorproduto();
                bigplano = new BigDecimal(util.S_to_Big(produtos.get(i).getValorproduto()));
            }
            if(produtos.get(i).getKeyproduto().equals(iins.getTv())){
                VALORTVTIT=produtos.get(i).getValorproduto();
                bigtv = new BigDecimal(util.S_to_Big(produtos.get(i).getValorproduto()));
            }

        }

        tt.setValorplano(VALORPLANOTIT);
        tt.setValortv(VALORTVTIT);
        tt.setValorgeral("0,00");

        StringBuilder str = null;
        str = new StringBuilder();
        str.append(iins.getPlano());
        str.append("#"+iins.getTv());
        String result = str.toString();
        tt.setProdutostit(result);
        tt.setKeyusubaixa("");
        tt.setKeyusucancel("");
        tt.setKeylinktit(iins.getLink());
        tt.setKeysetortit(iins.getSetor());

        String vctofinal = iins.getVcto()+"-"+ld_mes_a_gerar.format(mesano);

        long time=0;
        try {
            String str_date= vctofinal;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            time=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }


        tt.setVencimentotit(time);
        bigtotal = new BigDecimal("0");
        bigtotal=bigtotal.add(bigplano).add(bigtv);

        String valorfinal=util.Big_to_S(bigtotal);

        tt.setValortit(valorfinal);

        tt.setDatapgtotit(0);
        tt.setStatustit(1);
        tt.setSituacaotit(0);
        tt.setGeradobanco(1);
        if(tcob==1) tt.setGeradobanco(0);


        long longdatagerar = util.longdiamesano(ld_mes_a_gerar.format(diamesano));
        tt.setComptit(longdatagerar);

        String numt = "";
        String digtit = "";
        numt=instalacao.getNuminstal();
        numt = new StringBuilder(numt).reverse().toString();
        digtit = String.format("%03d", new Object[] { iins.getNum_tit_gerado()+1 });
        digtit = new StringBuilder(digtit).reverse().toString();
        tt.setNumerotit(numt+digtit);
        tt.setDesctit("Pagamento Mensalidade");

        iins.setMes_gerado_d1(longdatagerar);
        iins.setNum_tit_gerado(iins.getNum_tit_gerado()+1);

        return tt;
    }

    private class ListaItem extends Item<ViewHolder> {

        private final Instalacoes install;

        public ListaItem(Instalacoes install) {
            this.install =install;
        }

        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtinstall = viewHolder.itemView.findViewById(R.id.txtinstall);
            TextView txtstat = viewHolder.itemView.findViewById(R.id.txtstat);
            LinearLayout llstat= viewHolder.itemView.findViewById(R.id.llstat);




            switch (install.getStatus()){
                case 1:
                    txtstat.setText("Ativa");
                    llstat.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    txtstat.setText("Inadiplente");
                    llstat.setBackgroundColor(Color.RED);
                    break;
            }


            txtinstall.setText(install.getNuminstal()+" (" + install.getMegainstal() + ")");

        }
        @Override
        public int getLayout() {
            return R.layout.rvinstal_gerar_mensal;
        }
    }

    public void salvaTítulo(final Titulos tit, final Instalacoes iins){


        FirebaseFirestore db =FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference dr =db.collection("titulos").document(tit.getKeytit());

        batch.set(dr, tit);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                atualizaInstal(instalsalva);
            }
        });


    }

    private void atualizaInstal(Instalacoes iins) {

        int numtit = (iins.getNum_tit_gerado()+1);
        long dttit = util.longdiamesano(ld_mes_a_gerar.format(diamesano));

        FirebaseFirestore db =FirebaseFirestore.getInstance();

        WriteBatch batch = db.batch();
        DocumentReference dr =db.collection("instalacoes").document(iins.getNuminstal());

        batch.update(dr, "mes_gerado_d1",dttit,"num_tit_gerado",numtit);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

               atualizaMensalidades();

            }
        });
    }

    private void atualizaMensalidades() {

        Mensalidades_Instal mi = new Mensalidades_Instal();
        mi.setInstall(instalsalva.getNuminstal());

        FirebaseFirestore.getInstance().collection(ld_mes_a_gerar.format(diamesano))
                .document(instalsalva.getNuminstal())
                .set(mi)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(rbtodos.isChecked()){
                            ctrlgerar++;
                            geraTodas();
                        }else {
                            util.showSnackOk(cltit,"Mensalidade gerada com sucesso!!");
                            mprogressBar.setVisibility(View.INVISIBLE);
                            lista_ja_gerada();
                            bloqqueiaTD(false);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("failure", " e: " + e);
                    }
                });
    }

}