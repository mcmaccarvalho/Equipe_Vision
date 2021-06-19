package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Link;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta_Dia_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.Alertas.Alerta__Info;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.model.Work_Desp;
import com.ponto.ideal.solucoes.equipe_vision.model.Work_Rec;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


public class Frag_Fat_Link extends Fragment {

    private Spinner splink;
    private TextView txtrec,txtdesp,txtres,txtinstativa,txtinstinad,txtcapmegas,
            txtresmes,txtutilmegas,txtbase,vlrec,vlarec,vlinad,txttimeline;
    private ImageView imgleft,imgright,imginfo;
    private CombinedChart chart;
    private HorizontalBarChart chart1;

    private ConstraintLayout clfatlink;
    private LinearLayout llstatlink;


    private Adapter_Link adapter_link;
    private ArrayList<Links> blinks = new ArrayList<>();

    private ArrayList<Clientes> arrayBaseClientes = new ArrayList<>();

    private ArrayList<Instalacoes> arrayBaseInstal = new ArrayList<>();
    private  ArrayList<Titulos> arrayBaseTit = new ArrayList<>();
    private  ArrayList<Despesas> arrayBaseDesp = new ArrayList<>();

    private ArrayList<Instalacoes> arrayDataInstal = new ArrayList<>();
    private  ArrayList<Titulos> arrayDataTit = new ArrayList<>();
    private  ArrayList<Despesas> arrayDtaDesp = new ArrayList<>();

    private boolean aptinst;
    private boolean filtroLink=false;

    private String LINK;

    private int capMega=0;
    private int utilMega=0;

    private BigDecimal custolink= new BigDecimal("0");
    private BigDecimal titbaixado= new BigDecimal("0");
    private BigDecimal titberto= new BigDecimal("0");
    private BigDecimal titinad= new BigDecimal("0");
    private LocalDate hoje;
    private LocalDate database;
    private long longdatahoje;
    private long longdatabase;
    private ArrayAdapter<String> adaptvisao;

    ArrayList<BarEntry> barEntryArrayListRec;
    ArrayList<String> labelsnameRec;
    ArrayList<String> dataline = new ArrayList<>();
    ArrayList<Work_Rec> rrWork = new ArrayList<>();
    ArrayList<Work_Rec> work_fat = new ArrayList<>();
    ArrayList<Work_Rec> work_desp = new ArrayList<>();
    ArrayList<Work_Rec> work_inad = new ArrayList<>();
    ArrayList<Work_Rec> work_res = new ArrayList<>();
    ArrayList<Work_Rec> work_fake = new ArrayList<>();
    private int ctrlGraph=0;





    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("MM-yy")
            .withResolverStyle(ResolverStyle.STRICT);

    public Frag_Fat_Link() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__fat__link, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        txtrec = v.findViewById(R.id.txtrec);
        txtdesp = v.findViewById(R.id.txtdesp);
        txtres = v.findViewById(R.id.txtres);
        txtbase = v.findViewById(R.id.txtbase);
        txtresmes= v.findViewById(R.id.txtresmes);
        txttimeline= v.findViewById(R.id.txttimeline);
        vlinad = v.findViewById(R.id.vlinad);
        vlrec = v.findViewById(R.id.vlrec);
        vlarec = v.findViewById(R.id.vlarec);
        llstatlink = v.findViewById(R.id.llstatlink);
        imgright = v.findViewById(R.id.imgright);
        imgleft = v.findViewById(R.id.imgleft);
        imginfo = v.findViewById(R.id.imginfo);
        txtinstativa = v.findViewById(R.id.txtinstativa);
        txtinstinad = v.findViewById(R.id.txtinstinad);
        txtcapmegas = v.findViewById(R.id.txtcapmegas);
        txtutilmegas = v.findViewById(R.id.txtutilmegas);

        splink = v.findViewById(R.id.splink);
        clfatlink = v.findViewById(R.id.clfatlink);

        arrayBaseClientes=Home.baseClientes;

        chart = v.findViewById(R.id.chart1);
        chart.setNoDataText("Não existem dados para exibir.");
        chart.setNoDataTextColor(Color.BLACK);

//        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
//        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
//        LocalDate agora = zdt.toLocalDate();
//        agora=util.Dia_01_Mes(System.currentTimeMillis());

        longdatabase=util.long_X_ld(util.Dia_01_Mes(System.currentTimeMillis()));
        longdatahoje=util.long_X_ld(util.Dia_01_Mes(System.currentTimeMillis()));
        database = util.ld_X_long(longdatabase);


//        try {
//            String str_date= "01-"+agora.getMonthValue()+"-"+agora.getYear();
//            Log.i("titlink","str_date: " + str_date);
//
//            SimpleDateFormat formatter ;
//            Date date ;
//            formatter = new SimpleDateFormat("dd-MM-yyyy");
//            date = (Date) formatter.parse(str_date);
//            Log.i("test",""+date);
//            longdatahoje=date.getTime();
//            longdatabase=date.getTime();
//        } catch (Exception e) {
//            System.out.println("Exception :"+e);
//        }
//        it = Instant.ofEpochMilli(longdatahoje);
//        zdt = it.atZone(ZoneId.systemDefault());
//        hoje = zdt.toLocalDate();


        txtbase.setText(util.fma(database));
        //database=hoje;

        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database=database.minusMonths(1);
                longdatabase=util.long_X_ld(database);
                txtbase.setText(util.fma(database));
                carregaInfos(LINK);
            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(longdatahoje==longdatabase){
//                util.showSnackError(clfatlink,"Não existem dados para data solicitada.");
//                return;
//                }
                database=database.plusMonths(1);
                longdatabase=util.long_X_ld(database);
                txtbase.setText(util.fma(database));
                carregaInfos(LINK);
            }
        });

        imginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerta__Info alerta__info = new Alerta__Info();
                alerta__info.show(getActivity().getSupportFragmentManager(), "DialogTeclado");
            }
        });

        txttimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctrlGraph==5){
                    ctrlGraph=0;
                    carregaInfos(LINK);
                    txttimeline.setText(" Ver Time Line");
                }else {
                    ctrlGraph = 5;
                    carregaInfos(LINK);
                    txttimeline.setText("Ver Mês Base");
                }
            }
        });


        Links ll = new Links();
        ll.setKeylink("0");
        ll.setNomelink("Todos");
        ll.setValorlink("0,00");
        blinks.clear();
        blinks.add(ll);
        for (int i = 0; i< Home.baseLinks.size(); i++){
        blinks.add(Home.baseLinks.get(i));
    }
    adapter_link = new Adapter_Link(getContext(), blinks);
        adapter_link.notifyDataSetChanged();
        splink.setAdapter(adapter_link);
        splink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            util.vibratePhone(getContext(), (short)  20);


            if(position==0){
                LINK="none";

                llstatlink.setBackgroundColor(Color.BLUE);
            }else {
                LINK=blinks.get(position).getKeylink();
                switch (blinks.get(position).getStatus()){
                    case 0:
                        llstatlink.setBackgroundColor(Color.RED);
                        break;
                    case 1:
                        llstatlink.setBackgroundColor(Color.GREEN);
                        break;
                }

            }
            splink.clearFocus();
//                if(filtroLink)
            carregaInfos(LINK);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    });

    LINK="none";
    //carregaInfos(LINK);

}

    private void carregaInfos(String link)  {

        filtroLink=true;

        arrayBaseTit.clear();
        arrayBaseInstal.clear();
        arrayBaseDesp.clear();
        arrayDataInstal.clear();
        arrayDataTit.clear();
        arrayDtaDesp.clear();


        capMega=0;
        utilMega=0;
        titbaixado=new BigDecimal("0");
        titberto=new BigDecimal("0");
        titinad=new BigDecimal("0");
        custolink=new BigDecimal("0");
        int insativa=0;
        int insinad=0;


        switch (LINK){
            case "none":
                for(int i=0;i<Home.baseInstall.size();i++){
                    if(Home.baseInstall.get(i).getStatus()!=2) {
                        arrayDataInstal.add(Home.baseInstall.get(i));
                        utilMega = utilMega + Home.baseInstall.get(i).getMegainstal();
                        if(Home.baseInstall.get(i).getStatus()==1){
                            insativa++;
                        }
                        if(Home.baseInstall.get(i).getStatus()==3){
                            insinad++;
                        }
                    }
                }

                for(int k=0;k<Home.baseDepesas.size();k++){
                    if(Home.baseDepesas.get(k).getCompdesp()==longdatabase){
                        arrayDtaDesp.add(Home.baseDepesas.get(k));
                        BigDecimal ldesp = new BigDecimal(util.S_to_Big(Home.baseDepesas.get(k).getValordespesa()));
                        custolink=custolink.add(ldesp);
                    }
                }

                for(int i=0;i<Home.baseLinks.size();i++){
                    if(Home.baseLinks.get(i).getStatus()==1){
                        int vmeg =Integer.parseInt(Home.baseLinks.get(i).getCapacidadelink());
                        capMega=capMega+vmeg;
                    }
                }

                for(int k=0;k<Home.baseTitulos.size();k++){
                    if(Home.baseTitulos.get(k).getComptit()==longdatabase && Home.baseTitulos.get(k).getStatustit()!=3){
                        arrayDataTit.add(Home.baseTitulos.get(k));
                        BigDecimal vtit = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(k).getValortit()));

                        if(Home.baseTitulos.get(k).getStatustit()==1){
                            titberto=titberto.add(vtit);
                        }
                        if(Home.baseTitulos.get(k).getStatustit()==2){
                            titbaixado=titbaixado.add(vtit);
                        }

                        if(Home.baseTitulos.get(k).getSituacaotit()==1) {
                            titinad = titinad.add(vtit);
                        }
                    }
                }

                break;

            default:

                for(int i=0;i<Home.baseInstall.size();i++){
                    if(Home.baseInstall.get(i).getStatus()!=2
                            && Home.baseInstall.get(i).getLink().equals(LINK)) {
                        arrayDataInstal.add(Home.baseInstall.get(i));
                        utilMega = utilMega + Home.baseInstall.get(i).getMegainstal();
                        if(Home.baseInstall.get(i).getStatus()==1){
                            insativa++;
                        }
                        if(Home.baseInstall.get(i).getStatus()==3){
                            insinad++;
                        }
                    }
                }

                for(int k=0;k<Home.baseDepesas.size();k++){
                    if(Home.baseDepesas.get(k).getCompdesp()==longdatabase
                            && Home.baseDepesas.get(k).getLinkdespesa().equals(LINK)){
                        arrayDtaDesp.add(Home.baseDepesas.get(k));
                        BigDecimal ldesp = new BigDecimal(util.S_to_Big(Home.baseDepesas.get(k).getValordespesa()));
                        custolink=custolink.add(ldesp);
                    }
                }

                for(int i=0;i<Home.baseLinks.size();i++){
                    if(Home.baseLinks.get(i).getStatus()==1
                            && Home.baseLinks.get(i).getKeylink().equals(LINK)){
                        int vmeg =Integer.parseInt(Home.baseLinks.get(i).getCapacidadelink());
                        capMega=capMega+vmeg;
                    }
                }
                for(int k=0;k<Home.baseTitulos.size();k++){
                    if(Home.baseTitulos.get(k).getComptit()==longdatabase && Home.baseTitulos.get(k).getStatustit()!=3
                    && Home.baseTitulos.get(k).getKeylinktit().equals(LINK))
                    {
                        arrayDataTit.add(Home.baseTitulos.get(k));
                        BigDecimal vtit = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(k).getValortit()));

                        if(Home.baseTitulos.get(k).getStatustit()==1){
                            titberto=titberto.add(vtit);
                        }
                        if(Home.baseTitulos.get(k).getStatustit()==2){
                            titbaixado=titbaixado.add(vtit);
                        }

                        if(Home.baseTitulos.get(k).getSituacaotit()==1) {
                            titinad = titinad.add(vtit);
                        }
                    }
                }
        }

        txtdesp.setText(util.Big_to_S(custolink));
        txtinstinad.setText(String.valueOf(insinad));
        txtinstativa.setText(String.valueOf(insativa));
        txtutilmegas.setText(String.valueOf(utilMega));
        txtcapmegas.setText(String.valueOf(capMega));
        vlrec.setText(util.Big_to_S(titbaixado));
        vlarec.setText(util.Big_to_S(titberto));
        vlinad.setText(util.Big_to_S(titinad));
        txtrec.setText(util.Big_to_S(titbaixado));
        txtresmes.setText(txtbase.getText().toString());

        BigDecimal resultado = titbaixado;
        resultado=resultado.subtract(custolink);
        txtres.setText(util.Big_to_S(resultado));

        switch (ctrlGraph){
            case 0:
                generateDados();
                break;
            case 1:
                generateDespesa();
                break;
            case 2:
                generateFat();
                break;
            case 3:
                generateFatPlan();
                break;
            case 5:
                generateLine();
                break;
        }

    }




    private BarData generateBarFatLine() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < work_fat.size() + 1; i++) {
            float vrr =(float) work_fat.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
        }
        barEntryArrayListRec.add(new BarEntry(7, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[work_fat.size()];
        for (int i=0;i<work_fat.size();i++){
            coresRec[i]=work_fat.get(i).getIntcor();
        }
        barDataSet.setColors(coresRec);

        barDataSet.setValueTextSize(10);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);

        return barData;

    }




    private void generateDados()  {
        ctrlGraph=0;
        chart.clear();
        ArrayList<Work_Rec> provrec = new ArrayList<>();
        rrWork.clear();
        provrec.clear();

        BigDecimal bfat = new BigDecimal("0");
        BigDecimal bmensal = new BigDecimal("0");
        BigDecimal badesao = new BigDecimal("0");
        BigDecimal bind = new BigDecimal("0");
        BigDecimal bdesp = new BigDecimal("0");
        BigDecimal bresult = new BigDecimal("0");



        for (int i=0;i<arrayDataTit.size();i++){
            BigDecimal vltit = new BigDecimal(util.S_to_Big(arrayDataTit.get(i).getValortit()));
            bfat=bfat.add(vltit);
            if(arrayDataTit.get(i).getContacobtit().equals("Adesão")){
                badesao=badesao.add(vltit);
            }else {
                bmensal = bmensal.add(vltit);
            }
            if(arrayDataTit.get(i).getSituacaotit()==1){
                bind=bind.add(vltit);
            }
        }

        for(int i=0;i<arrayDtaDesp.size();i++) {
            BigDecimal vldesp = new BigDecimal(util.S_to_Big(arrayDtaDesp.get(i).getValordespesa()));
            bdesp = bdesp.add(vldesp);
        }

        bresult=bfat.subtract(bdesp);

        float  ffat =converte(util.Big_to_S(bfat ));
        float  fmensal =converte(util.Big_to_S(bmensal ));
        float  fadesao =converte(util.Big_to_S(badesao ));
        float  find    =converte(util.Big_to_S(bind    ));
        float  fdesp    =converte(util.Big_to_S(bdesp    ));
        float  fresult   =converte(util.Big_to_S(bresult    ));

// if(foutro>0f )//
// if(fservi>0f )//
// if(fmanut>0f )//
// if(fmater>0f )//
// if(fequip>0f )//
// if(flinks>0f )//
// if(fadesao>0f)//
// if(find>0f   )//
// if(fabr>0f   )//
// if(frec>0f   )//
        provrec.add(new Work_Rec("Fatu.",ffat   ,"#FF00B0ff",getResources().getColor(R.color.corfat)));
        provrec.add(new Work_Rec("Mens.",fmensal ,"#76FF03",getResources().getColor(R.color.cormensal)));
        provrec.add(new Work_Rec("Ades.",fadesao,"#FFAB00",getResources().getColor(R.color.coradesao)));
        provrec.add(new Work_Rec("Inad.",find ,"#FF0000",getResources().getColor(R.color.corinad)));
        provrec.add(new Work_Rec("Desp.",fdesp ,"#FFEB3B",getResources().getColor(R.color.cordesp)));
        provrec.add(new Work_Rec("Res.",fresult ,"#FFEB3B",getResources().getColor(R.color.corresult)));

        rrWork.clear();
        rrWork.addAll(provrec);

//            ddWork.clear();
//            ddWork.addAll(provdesp);
        Log.i("worktes"," rr: " + rrWork.size() + " rr: " + rrWork.size());

        criaGrapf();
    }

    private void criaGrapf(){

        chart.clear();
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR});
        Legend l = chart.getLegend();
        l.setEnabled(false);

        labelsnameRec = new ArrayList<>();
        labelsnameRec.add(" ");
        for (int i = 1; i < rrWork.size()+1; i++) {
            String appRec = rrWork.get(i - 1).getRectitle();
            labelsnameRec.add(appRec);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsnameRec));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yr = chart.getAxisRight();
        yr.setEnabled(false);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setDrawTopYLabelEntry(true);
        yl.setAxisMinimum(0.1f);
        yl.setSpaceBottom(10f);
        yl.setSpaceTop(20f);
        yl.setDrawZeroLine(true);
        yl.setDrawLabels(true);
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setGranularity(1f);

        final CombinedData data = new CombinedData();

        data.setData(generateBarData());
//        data.setData(generateLineMeta());
//        data.setData(generateScatterData());

        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.argb(0, 1, 1, 1));
        chart.setBorderColor(Color.BLACK);
        chart.setBorderWidth(0.5f);
        chart.setDrawBorders(true);
        chart.setDrawValueAboveBar(true);
        chart.animateXY(500, 500);
        chart.setScaleEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setData(data);
        chart.invalidate();



        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        data.setHighlightEnabled(true);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                String sdd = String.valueOf(e.getX());
                float fdd = Float.parseFloat(sdd);
                int ndd = (int)fdd;
                if(ndd>0 && ndd<6) {

                  String tt = rrWork.get(ndd-1).getRectitle();

                  switch (ndd){
                      case 1:
                          generateFat();
                          break;
                      case 2:
                          generateFatPlan();
                          break;
                      case 5:
                          generateDespesa();
                          break;

                  }

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private BarData generateBarData() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < rrWork.size() + 1; i++) {
            float vrr =(float) rrWork.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
        }
        barEntryArrayListRec.add(new BarEntry(7, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[rrWork.size()];
        for (int i=0;i<rrWork.size();i++){
            coresRec[i]=rrWork.get(i).getIntcor();
        }
        barDataSet.setColors(coresRec);

        barDataSet.setValueTextSize(10);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);

        return barData;

    }




    private void generateDespesa() {
        ctrlGraph=1;
        chart.clear();
        ArrayList<Work_Rec> provrec = new ArrayList<>();
        rrWork.clear();
        provrec.clear();

        BigDecimal bpaglink = new BigDecimal("0");
        BigDecimal bequip = new BigDecimal("0");
        BigDecimal bmater = new BigDecimal("0");
        BigDecimal bmanu = new BigDecimal("0");
        BigDecimal bserv = new BigDecimal("0");
        BigDecimal boutra = new BigDecimal("0");


        for(int i=0;i<arrayDtaDesp.size();i++) {
            BigDecimal vldesp = new BigDecimal(util.S_to_Big(arrayDtaDesp.get(i).getValordespesa()));
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Pgto Link")) bpaglink = bpaglink.add(vldesp);
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Equip/tos")) bequip  = bequip  .add(vldesp);
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Material"))  bmater  = bmater  .add(vldesp);
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Manutenção"))bmanu   = bmanu   .add(vldesp);
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Serviços"))  bserv   = bserv   .add(vldesp);
            if(arrayDtaDesp.get(i).getTipodespesa().equals("Outros"))    boutra  = boutra  .add(vldesp);
        }

        float  fpaglink =converte(util.Big_to_S    (bpaglink  ));
        float  fequip   =converte(util.Big_to_S (bequip    ));
        float  fmater   =converte(util.Big_to_S (bmater    ));
        float  fmanu    =converte(util.Big_to_S (bmanu     ));
        float  fserv     =converte(util.Big_to_S(bserv     ));
        float  foutra    =converte(util.Big_to_S(boutra    ));

        provrec.add(new Work_Rec("Link.",fpaglink ,"#76FF03",getResources().getColor(R.color.cordlink)));
        provrec.add(new Work_Rec("Equi.",fequip   ,"#FF00B0ff",getResources().getColor(R.color.cordequip)));
        provrec.add(new Work_Rec("Mate.",fmater   ,"#FFAB00",getResources().getColor(R.color.cordmat)));
        provrec.add(new Work_Rec("Manu.",fmanu    ,"#FF0000",getResources().getColor(R.color.cordmanu)));
        provrec.add(new Work_Rec("Serv.",fserv    ,"#FFEB3B",getResources().getColor(R.color.cordserv)));
        provrec.add(new Work_Rec("Outr.",foutra   ,"#1F6612",getResources().getColor(R.color.cordout)));

        rrWork.clear();
        rrWork.addAll(provrec);

        criaGrapfDesp();

    }

    private void criaGrapfDesp() {

        chart.clear();
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR});
        Legend l = chart.getLegend();
        l.setEnabled(false);

        labelsnameRec = new ArrayList<>();
        labelsnameRec.add(" ");
        for (int i = 1; i < rrWork.size()+1; i++) {
            String appRec = rrWork.get(i - 1).getRectitle();
            Log.i("rrwork",": "+rrWork.get(i-1).getRectitle()+ " vl:" + rrWork.get(i-1).getRecvrr());
            labelsnameRec.add(appRec);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsnameRec));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yr = chart.getAxisRight();
        yr.setEnabled(false);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setDrawTopYLabelEntry(true);
        yl.setAxisMinimum(0.1f);
        yl.setSpaceBottom(10f);
        yl.setSpaceTop(20f);
        yl.setDrawZeroLine(true);
        yl.setDrawLabels(true);
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setGranularity(1f);

        final CombinedData data = new CombinedData();

        data.setData(generateBarDesp());


        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.argb(0, 1, 1, 1));
        chart.setBorderColor(Color.BLACK);
        chart.setBorderWidth(0.5f);
        chart.setDrawBorders(true);
        chart.setDrawValueAboveBar(true);
        chart.animateXY(500, 500);
        chart.setScaleEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setData(data);
        chart.invalidate();



        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        data.setHighlightEnabled(true);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                            generateDados();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private BarData generateBarDesp() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < rrWork.size() + 1; i++) {
            float vrr =(float) rrWork.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
        }
        barEntryArrayListRec.add(new BarEntry(7, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[rrWork.size()];
        for (int i=0;i<rrWork.size();i++){
            coresRec[i]=rrWork.get(i).getIntcor();
        }
        barDataSet.setColors(coresRec);

        barDataSet.setValueTextSize(10);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);

        return barData;
    }




    private BarData generateBarFat() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < rrWork.size() + 1; i++) {
            float vrr =(float) rrWork.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
        }
        barEntryArrayListRec.add(new BarEntry(4, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[rrWork.size()];
        for (int i=0;i<rrWork.size();i++){
            coresRec[i]=rrWork.get(i).getIntcor();
        }
        barDataSet.setColors(coresRec);



        barDataSet.setValueTextSize(10);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);



        return barData;
    }

    private void criaGrapfFat() {

        chart.clear();
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR});
        Legend l = chart.getLegend();
        l.setEnabled(false);

        labelsnameRec = new ArrayList<>();
        labelsnameRec.add(" ");
        for (int i = 1; i < rrWork.size()+1; i++) {
            String appRec = rrWork.get(i - 1).getRectitle();
            Log.i("rrwork",": "+rrWork.get(i-1).getRectitle()+ " vl:" + rrWork.get(i-1).getRecvrr());
            labelsnameRec.add(appRec);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsnameRec));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yr = chart.getAxisRight();
        yr.setEnabled(false);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setDrawTopYLabelEntry(true);
        yl.setAxisMinimum(0.1f);
        yl.setSpaceBottom(10f);
        yl.setSpaceTop(20f);
        yl.setDrawZeroLine(true);
        yl.setDrawLabels(true);
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setGranularity(1f);

        final CombinedData data = new CombinedData();

        data.setData(generateBarFat());


        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.argb(0, 1, 1, 1));
        chart.setBorderColor(Color.BLACK);
        chart.setBorderWidth(0.5f);
        chart.setDrawBorders(true);
        chart.setDrawValueAboveBar(true);
        chart.animateXY(500, 500);
        chart.setScaleEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setData(data);
        chart.invalidate();



        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        data.setHighlightEnabled(true);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                generateDados();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private void generateFat() {
        ctrlGraph=2;
        chart.clear();
        ArrayList<Work_Rec> provrec = new ArrayList<>();
        rrWork.clear();
        provrec.clear();

        BigDecimal bbaix = new BigDecimal("0");
        BigDecimal baber = new BigDecimal("0");
        BigDecimal binad = new BigDecimal("0");

        for (int i=0;i<arrayDataTit.size();i++){
            BigDecimal vltit = new BigDecimal(util.S_to_Big(arrayDataTit.get(i).getValortit()));
            if(arrayDataTit.get(i).getStatustit()==2) bbaix = bbaix.add(vltit);
            if(arrayDataTit.get(i).getStatustit()==1) baber = baber.add(vltit);
            if(arrayDataTit.get(i).getSituacaotit()==1) binad = binad.add(vltit);
        }

        float  fbaix =converte(util.Big_to_S (bbaix  ));
        float  faber =converte(util.Big_to_S (baber  ));
        float  finad =converte(util.Big_to_S (binad  ));

        provrec.add(new Work_Rec("Baixados",fbaix ,"#FF00B0ff",getResources().getColor(R.color.baixado)));
        provrec.add(new Work_Rec("Abertos",faber ,"#76FF03",getResources().getColor(R.color.aberto)));
        provrec.add(new Work_Rec("Inadimp.",finad ,"#FF0000",getResources().getColor(R.color.corinad)));

        rrWork.clear();
        rrWork.addAll(provrec);

        criaGrapfFat();

    }




    private void generateFatPlan() {
        ctrlGraph=3;
        chart.clear();
        ArrayList<Work_Rec> provrec = new ArrayList<>();
        rrWork.clear();
        provrec.clear();

        BigDecimal bplan = new BigDecimal("0");
        BigDecimal btv = new BigDecimal("0");
        BigDecimal binad = new BigDecimal("0");

        for (int i=0;i<arrayDataTit.size();i++){
            BigDecimal vlplan = new BigDecimal(util.S_to_Big(arrayDataTit.get(i).getValorplano()));
            BigDecimal vltv = new BigDecimal(util.S_to_Big(arrayDataTit.get(i).getValortv()));
            bplan = bplan.add(vlplan);
            btv = btv.add(vltv);
            if(arrayDataTit.get(i).getSituacaotit()==1) {
                binad = binad.add(vlplan);
                binad = binad.add(vltv);
            }
        }

        float  fplan =converte(util.Big_to_S (bplan  ));
        float  ftv =converte(util.Big_to_S (btv  ));
        float  finad =converte(util.Big_to_S (binad  ));

        provrec.add(new Work_Rec("Plano",fplan ,"#FFAA66",getResources().getColor(R.color.corplano)));
        provrec.add(new Work_Rec("Tv",ftv ,"#FFFF88",getResources().getColor(R.color.cortv)));
        provrec.add(new Work_Rec("Inadimp.",finad ,"#FF0000",getResources().getColor(R.color.corinad)));

        rrWork.clear();
        rrWork.addAll(provrec);

        criaGrapfFatPlan();

    }

    private void criaGrapfFatPlan() {

        chart.clear();
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR});
        Legend l = chart.getLegend();
        l.setEnabled(false);

        labelsnameRec = new ArrayList<>();
        labelsnameRec.add(" ");
        for (int i = 1; i < rrWork.size()+1; i++) {
            String appRec = rrWork.get(i - 1).getRectitle();
            Log.i("rrwork",": "+rrWork.get(i-1).getRectitle()+ " vl:" + rrWork.get(i-1).getRecvrr());
            labelsnameRec.add(appRec);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsnameRec));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yr = chart.getAxisRight();
        yr.setEnabled(false);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setDrawTopYLabelEntry(true);
        yl.setAxisMinimum(0.1f);
        yl.setSpaceBottom(10f);
        yl.setSpaceTop(20f);
        yl.setDrawZeroLine(true);
        yl.setDrawLabels(true);
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setGranularity(1f);

        final CombinedData data = new CombinedData();

        data.setData(generateBarFatPlan());


        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.argb(0, 1, 1, 1));
        chart.setBorderColor(Color.BLACK);
        chart.setBorderWidth(0.5f);
        chart.setDrawBorders(true);
        chart.setDrawValueAboveBar(true);
        chart.animateXY(500, 500);
        chart.setScaleEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setData(data);
        chart.invalidate();



        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        data.setHighlightEnabled(true);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                generateDados();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private BarData generateBarFatPlan() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < rrWork.size() + 1; i++) {
            float vrr =(float) rrWork.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
        }
        barEntryArrayListRec.add(new BarEntry(4, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[rrWork.size()];
        for (int i=0;i<rrWork.size();i++){
            coresRec[i]=rrWork.get(i).getIntcor();
        }
        barDataSet.setColors(coresRec);



        barDataSet.setValueTextSize(10);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);



        return barData;
    }




    private void generateLine(){
        ctrlGraph=5;
        chart.clear();
//        ArrayList<Work_Rec> fatres = new ArrayList<>();
//        fatres.clear();
        ArrayList<Long> longmeses = new ArrayList<>();
        work_fat.clear();
        work_desp.clear();
        work_inad.clear();
        work_res.clear();
        LocalDate dtmes ;






        for(int i=5;i>-1;i--){
            dtmes=database.minusMonths(i);
            long lmes=0;
            try {
                String str_date= "01-"+dtmes.getMonthValue()+"-"+dtmes.getYear();
                SimpleDateFormat formatter ;
                Date date ;
                formatter = new SimpleDateFormat("dd-MM-yyyy");
                date = (Date) formatter.parse(str_date);
                lmes=date.getTime();
            } catch (Exception e) {
                System.out.println("Exception :"+e);
            }
            longmeses.add(lmes);
        }

        BigDecimal desp;
        BigDecimal fat;
        BigDecimal inad;
        BigDecimal res;
        DateTimeFormatter fmtd = DateTimeFormatter
                .ofPattern("MM-yy")
                .withResolverStyle(ResolverStyle.STRICT);
        dataline.clear();
        for(int k=0;k<longmeses.size();k++) {
            Instant it = Instant.ofEpochMilli(longmeses.get(k));
            ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
            LocalDate dmes = zdt.toLocalDate();
            dataline.add(dmes.format(fmtd));
        }





        for(int k=0;k<longmeses.size();k++){

            desp = new BigDecimal("0");
            fat = new BigDecimal("0");
            inad = new BigDecimal("0");
            res = new BigDecimal("0");



            switch (LINK){
                case "none":

                    for (int i=0;i<Home.baseTitulos.size();i++){

                        Instant it = Instant.ofEpochMilli(Home.baseTitulos.get(i).getComptit());
                        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
                        LocalDate dmes = zdt.toLocalDate();
                        it = Instant.ofEpochMilli(longmeses.get(k));
                        zdt =  it.atZone(ZoneId.systemDefault());
                        LocalDate longmes = zdt.toLocalDate();
                        DateTimeFormatter fmtc = DateTimeFormatter
                                .ofPattern("dd-MM-yyyy")
                                .withResolverStyle(ResolverStyle.STRICT);

                        Log.i("linetit","install " + Home.baseTitulos.get(i).getInstalltit()+
                                " comptit " + dmes.format(fmtc) + " longmes " + longmes.format(fmtc));
                        BigDecimal fatmes = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(i).getValortit()));

                        if(Home.baseTitulos.get(i).getComptit()==longmeses.get(k)) {

                            fat = fat.add(fatmes);

                            if (Home.baseTitulos.get(i).getSituacaotit() == 1) {
                                inad = inad.add(fatmes);
                            }
                        }
                    }

                    for(int i=0;i<Home.baseDepesas.size();i++){
                        if(Home.baseDepesas.get(i).getCompdesp()==longmeses.get(k)){
                            BigDecimal despmes = new BigDecimal(util.S_to_Big(Home.baseDepesas.get(i).getValordespesa()));
                            desp=desp.add(despmes);
                        }
                    }
                    break;
                default:

                    for (int i=0;i<Home.baseTitulos.size();i++){
                        BigDecimal fatmes = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(i).getValortit()));

                            if (Home.baseTitulos.get(i).getComptit() == longmeses.get(k)
                                    && Home.baseTitulos.get(i).getKeylinktit().equals(LINK)) {
                                fat = fat.add(fatmes);
                                if (Home.baseTitulos.get(i).getSituacaotit() == 1) {
                                    inad = inad.add(fatmes);
                                }
                            }
                    }
                    for(int i=0;i<Home.baseDepesas.size();i++){
                    if(Home.baseDepesas.get(i).getCompdesp()==longmeses.get(k)
                    && Home.baseDepesas.get(i).getLinkdespesa().equals(LINK)){
                        BigDecimal despmes = new BigDecimal(util.S_to_Big(Home.baseDepesas.get(i).getValordespesa()));
                        desp=desp.add(despmes);
                    }
                }

            }

            work_fat.add(new Work_Rec("Faturamento",converte(util.Big_to_S(fat)),"#ff0000",getResources().getColor(R.color.corfat)));
            work_desp.add(new Work_Rec("Despesa",converte(util.Big_to_S(desp)),"#ff0000",getResources().getColor(R.color.cordesp)));
            work_inad.add(new Work_Rec("Inadimplente",converte(util.Big_to_S(inad)),"#ff0000",getResources().getColor(R.color.corinad)));

            res=fat.subtract(desp);

            float rres = converte(util.Big_to_S(res));
            int ires = Float.compare(rres,0f);
            work_res.add(new Work_Rec("Adesão",converte(util.Big_to_S(res)),"#ff0000",getResources().getColor(R.color.corresult)));
        }

        criaGrapfLine();
    }

    private LineData generateLineGeral(){


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        ArrayList<Entry> Efat = new ArrayList<>();
        ArrayList<Entry> Edep = new ArrayList<>();
        ArrayList<Entry> Eind = new ArrayList<>();
        ArrayList<Entry> Eres = new ArrayList<>();

        for (int index = 1; index < work_fat.size()+1; index++) {
            Efat.add(new Entry(index, work_fat.get(index-1 ).getRecvrr()));
        }
        for (int index = 1; index < work_desp.size() + 1; index++) {
            Edep.add(new Entry(index, work_desp.get(index - 1).getRecvrr()));
        }
        for (int index = 1; index < work_inad.size() + 1; index++) {
            Eind.add(new Entry(index, work_inad.get(index - 1).getRecvrr()));
        }
        for (int index = 1; index < work_res.size() + 1; index++) {
            Eres.add(new Entry(index, work_res.get(index - 1).getRecvrr()));
        }

        LineDataSet fat = new LineDataSet(Efat, "Faturamento");
//        fat.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return util.formataTextoValor(value);
//            }
//        });
        LineDataSet des = new LineDataSet(Edep, "Despesas");
//        fat.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return util.formataTextoValor(value);
//            }
//        });
        LineDataSet ind = new LineDataSet(Eind, "Inadimplente");
//        fat.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return util.formataTextoValor(value);
//            }
//        });
        LineDataSet res = new LineDataSet(Eres, "Resultado");
//        fat.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return util.formataTextoValor(value);
//            }
//        });




        fat.setValueTextColor(getResources().getColor(R.color.corfat));
        fat.setColor(getResources().getColor(R.color.corfat));
        fat.setLineWidth(2.5f);
        fat.setFillColor(getResources().getColor(R.color.corfat));
        fat.setDrawValues(true);
        fat.setValueTextSize(10f);
        fat.setDrawFilled(true);
        fat.setDrawCircleHole(false);
        fat.setDrawCircles(false);
        fat.setFillAlpha(50);
        fat.setLineWidth(2.5f);
        fat.setColor(getResources().getColor(R.color.corfat));

        des.setValueTextColor(getResources().getColor(R.color.cordesp));
        des.setColor(getResources().getColor(R.color.cordesp));
        des.setLineWidth(2.5f);
        des.setFillColor(getResources().getColor(R.color.cordesp));
        des.setDrawValues(true);
        des.setValueTextSize(10f);
        des.setDrawFilled(true);
        des.setDrawCircleHole(false);
        des.setDrawCircles(false);
        des.setFillAlpha(50);
        des.setLineWidth(2.5f);
        des.setColor(getResources().getColor(R.color.cordesp));

        ind.setValueTextColor(getResources().getColor(R.color.corinad));
        ind.setColor(getResources().getColor(R.color.corinad));
        ind.setLineWidth(2.5f);
        ind.setFillColor(getResources().getColor(R.color.corinad));
        ind.setDrawValues(true);
        ind.setValueTextSize(10f);
        ind.setDrawFilled(true);
        ind.setDrawCircleHole(false);
        ind.setDrawCircles(false);
        ind.setFillAlpha(50);
        ind.setLineWidth(2.5f);
        ind.setColor(getResources().getColor(R.color.corinad));

        res.setValueTextColor(getResources().getColor(R.color.corresult));
        res.setColor(getResources().getColor(R.color.corresult));
        res.setLineWidth(2.5f);
        res.setFillColor(getResources().getColor(R.color.corresult));
        res.setDrawValues(true);
        res.setValueTextSize(10f);
        res.setDrawFilled(true);
        res.setDrawCircleHole(false);
        res.setDrawCircles(false);
        res.setFillAlpha(50);
        res.setLineWidth(2.5f);
        res.setColor(getResources().getColor(R.color.corresult));



        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, (float) 0));
        values.add(new Entry(1, (float) 0));
        values.add(new Entry(2, (float) 0));
        values.add(new Entry(3, (float) 0));
        values.add(new Entry(4, (float) 0));
        values.add(new Entry(5, (float) 0));
        values.add(new Entry(6, (float) 0));
        values.add(new Entry(7, (float) 0));

        LineDataSet d = new LineDataSet(values, "Meta");
        d.setColor(Color.argb(0,1,1,1));
        d.setLineWidth(1f);
        d.setDrawValues(false);
        d.setDrawCircleHole(false);
        d.setDrawCircles(false);
        dataSets.add(d);

       // dataSets.add(fat);
        dataSets.add(des);
        //dataSets.add(ads);
        dataSets.add(ind);
        //dataSets.add(men);
      //  dataSets.add(res);

        LineData data = new LineData(dataSets);
        return data;

    }

    private void criaGrapfLine() {

        chart.clear();
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE});


        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        LegendEntry legendEntryA = new LegendEntry();
        LegendEntry legendEntryB = new LegendEntry();
        LegendEntry legendEntryC = new LegendEntry();
        LegendEntry legendEntryD = new LegendEntry();
        LegendEntry legendEntryE = new LegendEntry();
        LegendEntry legendEntryF = new LegendEntry();

        l.setTextSize(10f);
        legendEntryA.label = "FTot.";
        legendEntryA.formColor = getResources().getColor(R.color.corfat);
        legendEntryD.label = "Inad.";
        legendEntryD.formColor = getResources().getColor(R.color.corinad);
        legendEntryE.label = "Desp.";
        legendEntryE.formColor = getResources().getColor(R.color.cordesp);
        legendEntryF.label = "Resul.";
        legendEntryF.formColor = getResources().getColor(R.color.corresult);
        l.setCustom(Arrays.asList(legendEntryA, legendEntryD, legendEntryE));
        l.setEnabled(true);

        labelsnameRec = new ArrayList<>();
        labelsnameRec.add("mês");
        for (int i = 1; i < dataline.size()+1; i++) {
            String appRec = dataline.get(i - 1);
            labelsnameRec.add(appRec);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsnameRec));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yr = chart.getAxisRight();
        yr.setEnabled(false);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setDrawTopYLabelEntry(true);
        yl.setAxisMinimum(0.1f);
        yl.setSpaceBottom(10f);
        yl.setSpaceTop(20f);
        yl.setDrawZeroLine(true);
        yl.setDrawLabels(true);
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setGranularity(1f);

        final CombinedData data = new CombinedData();

        data.setData(generateBarFatLine());
        data.setData(generateLineGeral());


        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.argb(0, 1, 1, 1));
        chart.setBorderColor(Color.BLACK);
        chart.setBorderWidth(0.5f);
        chart.setDrawBorders(true);
        chart.setDrawValueAboveBar(true);
        chart.animateXY(500, 500);
        chart.setScaleEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.setData(data);
        chart.invalidate();



        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        data.setHighlightEnabled(true);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
           //     generateDados();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    public static float converte(String arg) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        float number = 0;
        try {
            number = nf.parse(arg).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return number;
    }
}