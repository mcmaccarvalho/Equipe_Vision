package com.ponto.ideal.solucoes.equipe_vision.ui.home;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Setor_Chart;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.model.Work_Rec;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Fat_Link;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;

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


public class Frag_Chart_Setor extends Fragment {


    private Spinner spsetor;
    private TextView txtbase,txtchart,txtclitot,txtinsset,txtcliline;
    private ImageView imgleft,imgright;
    private RadioButton rbmesbase,rbfattime,rbclitime;
    private RadioGroup radioGroup3;
    private CombinedChart chart;
    private ConstraintLayout clchart;

    ArrayList<Work_Rec> rrWork = new ArrayList<>();
    ArrayList<Work_Rec> work_fatu = new ArrayList<>();
    ArrayList<Work_Rec> work_mensal = new ArrayList<>();
    ArrayList<Work_Rec> work_ades = new ArrayList<>();
    ArrayList<Work_Rec> work_inad = new ArrayList<>();
    ArrayList<Work_Rec> work_result = new ArrayList<>();
    ArrayList<Work_Rec> work_ins = new ArrayList<>();

    private LocalDate hoje;
    private LocalDate database;
    private long longdatahoje;
    private long longdatabase;
    private ArrayAdapter<String> adaptvisao;

    ArrayList<BarEntry> barEntryArrayListRec;
    ArrayList<String> labelsnameRec;

    private Adapter_Setor_Chart adapter_setor;
    private ArrayList<Setores> bsetor = new ArrayList<>();

    private ArrayList<Clientes> arrayBaseClientes = new ArrayList<>();

    private ArrayList<Instalacoes> arrayBaseInstal = new ArrayList<>();
    private  ArrayList<Titulos> arrayBaseTit = new ArrayList<>();

   // private ArrayList<String> Scliset = new ArrayList<>();
    private ArrayList<String> Sinsmes = new ArrayList<>();
    private ArrayList<String> Sinstot = new ArrayList<>();

    private ArrayList<Instalacoes> arrayDataInstal = new ArrayList<>();
    private  ArrayList<Titulos> arrayDataTit = new ArrayList<>();

    private String SETOR;
    private int ctrlGraph;

    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("MM-yy")
            .withResolverStyle(ResolverStyle.STRICT);

    private BigDecimal titbaixado= new BigDecimal("0");
    private BigDecimal titberto= new BigDecimal("0");
    private BigDecimal titinad= new BigDecimal("0");

    ArrayList<String> dataline = new ArrayList<>();

    public Frag_Chart_Setor() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__chart__setor, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        txtbase = v.findViewById(R.id.txtbase);
        txtchart= v.findViewById(R.id.txtchart);
        rbclitime= v.findViewById(R.id.rbclitime);
        rbmesbase= v.findViewById(R.id.rbmesbase);
        rbfattime= v.findViewById(R.id.rbfattime);
        radioGroup3= v.findViewById(R.id.radioGroup3);
        txtclitot= v.findViewById(R.id.txtclitot);
        txtinsset= v.findViewById(R.id.txtinsset);
        imgright = v.findViewById(R.id.imgright);
        imgleft = v.findViewById(R.id.imgleft);
        spsetor = v.findViewById(R.id.spsetor);
        clchart = v.findViewById(R.id.clchart);
        chart = v.findViewById(R.id.chart);

        txtinsset.setText("0");
        txtclitot.setText("0");
        chart.setNoDataText("Não existem dados para exibir.");
        chart.setNoDataTextColor(Color.BLACK);

        arrayBaseClientes=Home.baseClientes;


        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                util.vibratePhone(getContext(),(short) 20);
                switch (checkedId) {
                    case R.id.rbmesbase:
                        ctrlGraph=0;
                        txtchart.setText("Faturamento Mês Base");
                        break;
                    case R.id.rbfattime:
                        ctrlGraph=1;
                        txtchart.setText("Faturamento Time Line");
                        break;
                    case R.id.rbclitime:
                        ctrlGraph=2;
                        txtchart.setText("Clientes Time Line");
                        break;
                    default:
                        ctrlGraph=0;
                        txtchart.setText("Faturamento Mês Base");
                }
                carregaInfos(SETOR);
            }
        });


        longdatabase=util.long_X_ld(util.Dia_01_Mes(System.currentTimeMillis()));
        database=util.ld_X_long(longdatabase);
        txtbase.setText(util.fma(database));

        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=database.minusMonths(1);
                longdatabase=util.long_X_ld(database);
                txtbase.setText(util.fma(database));
                carregaInfos(SETOR);
            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database=database.plusMonths(1);
                longdatabase=util.long_X_ld(database);
                txtbase.setText(util.fma(database));
                carregaInfos(SETOR);
            }
        });


        Setores ss = new Setores();
        ss.setKeysetor("0");
        ss.setNomesetor("Todos");

        bsetor.clear();
        bsetor.add(ss);
        for (int i = 0; i< Home.baseSetores.size(); i++){
            bsetor.add(Home.baseSetores.get(i));
        }
        adapter_setor = new Adapter_Setor_Chart(getContext(), bsetor);
        adapter_setor.notifyDataSetChanged();
        spsetor.setAdapter(adapter_setor);
        spsetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short)  20);
                if(position==0){
                    SETOR="none";

                }else {
                    SETOR=bsetor.get(position).getKeysetor();
                }
                spsetor.clearFocus();
                carregaInfos(SETOR);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        radioGroup3.callOnClick();
        SETOR="none";
    }

    private void carregaInfos(String setor) {

        arrayBaseTit.clear();
        arrayBaseInstal.clear();
        Sinstot.clear();
        Sinsmes.clear();

        switch (setor) {
            case "none":
                for (int k = 0; k < Home.baseTitulos.size(); k++) {
                    if (Home.baseTitulos.get(k).getComptit() == longdatabase) {
                        if(!Sinsmes.contains(Home.baseTitulos.get(k).getKeyclientetit()))Sinsmes.add(Home.baseTitulos.get(k).getKeyclientetit());
                        arrayBaseTit.add(Home.baseTitulos.get(k));
                    }
                    if(!Sinstot.contains(Home.baseTitulos.get(k).getKeyclientetit()))Sinstot.add(Home.baseTitulos.get(k).getKeyclientetit());
                }
                break;

            default:

                for (int k = 0; k < Home.baseTitulos.size(); k++) {

                    if (Home.baseTitulos.get(k).getKeysetortit().equals(SETOR)) {
                        if(!Sinstot.contains(Home.baseTitulos.get(k).getKeyclientetit()))Sinstot.add(Home.baseTitulos.get(k).getKeyclientetit());
                    }

                    if (Home.baseTitulos.get(k).getComptit() == longdatabase
                            && Home.baseTitulos.get(k).getKeysetortit().equals(SETOR)) {
                        if (Home.baseTitulos.get(k).getComptit() == longdatabase) {
                            arrayBaseTit.add(Home.baseTitulos.get(k));
                            if(!Sinsmes.contains(Home.baseTitulos.get(k).getKeyclientetit()))Sinsmes.add(Home.baseTitulos.get(k).getKeyclientetit());

                        }
                    }
                }
        }

        txtinsset.setText(String.valueOf(Sinsmes.size()));
        txtclitot.setText(String.valueOf(Sinstot.size()));

        switch (radioGroup3.getCheckedRadioButtonId()) {

            case R.id.rbmesbase:
                ctrlGraph=0;
                txtchart.setText("Faturamento Mês Base");
                generateDados();
                break;
            case R.id.rbfattime:
                ctrlGraph=1;
                txtchart.setText("Faturamento Time Line");
                generateLine();
                break;
            case R.id.rbclitime:
                ctrlGraph=2;
                txtchart.setText("Clientes Time Line");
                generateLineCli();
                break;
            default:
                ctrlGraph=0;
                txtchart.setText("Faturamento Mês Base");
                generateDados();
        }
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

        for (int i=0;i<arrayBaseTit.size();i++){
            BigDecimal vltit = new BigDecimal(util.S_to_Big(arrayBaseTit.get(i).getValortit()));
            bfat=bfat.add(vltit);
            if(arrayBaseTit.get(i).getContacobtit().equals("Adesão")){
                badesao=badesao.add(vltit);
            }else {
                bmensal = bmensal.add(vltit);
            }
            if(arrayBaseTit.get(i).getSituacaotit()==1){
                bind=bind.add(vltit);
            }
        }


        float  ffat =converte(util.Big_to_S(bfat ));
        float  fmensal =converte(util.Big_to_S(bmensal ));
        float  fadesao =converte(util.Big_to_S(badesao ));
        float  find    =converte(util.Big_to_S(bind    ));

        int corfat = getResources().getColor(R.color.corfat);
        int cormensal = getResources().getColor(R.color.cormensal);
        int coradesao = getResources().getColor(R.color.coradesao);
        int corinad = getResources().getColor(R.color.corinad);

        provrec.add(new Work_Rec("FTot.",ffat   ,"#FF00B0ff",getResources().getColor(R.color.corfat)));
        provrec.add(new Work_Rec("Mens.",fmensal ,"#76FF03",getResources().getColor(R.color.cormensal)));
        provrec.add(new Work_Rec("Ades.",fadesao,"#FFAB00",getResources().getColor(R.color.coradesao)));
        provrec.add(new Work_Rec("Inad.",find ,"#FF0000",getResources().getColor(R.color.corinad)));


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
        chart.setHighlightPerTapEnabled(false);
        data.setHighlightEnabled(false);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                String sdd = String.valueOf(e.getX());
                float fdd = Float.parseFloat(sdd);
                int ndd = (int)fdd;
                if(ndd>0 && ndd<5) {

                    String tt = rrWork.get(ndd-1).getRectitle();

                    switch (ndd){
//                        case 2:
//                            generateFatPlan();
//                            break;
//                        case 5:
//                            generateDespesa();
//                            break;
//                        default:
//                            generateFat();
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
        barEntryArrayListRec.add(new BarEntry(5, new float[]{0}));
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
        ctrlGraph=1;
        chart.clear();

        ArrayList<Long> longmeses = new ArrayList<>();
        work_fatu.clear();
        work_mensal.clear();
        work_ades.clear();
        work_inad.clear();
        work_result.clear();
        LocalDate dtmes ;
        dataline.clear();

        for(int i=5;i>-1;i--){
            dtmes=database.minusMonths(i);
            dataline.add(dtmes.format(fmt));
            long lmes=util.long_X_ld(dtmes);
            longmeses.add(lmes);
        }

        BigDecimal fat = new BigDecimal("0");
        BigDecimal adesão = new BigDecimal("0");
        BigDecimal inad = new BigDecimal("0");
        BigDecimal mensal = new BigDecimal("0");

        for(int k=0;k<longmeses.size();k++){

            fat = new BigDecimal("0");
            adesão = new BigDecimal("0");
            inad = new BigDecimal("0");
            mensal = new BigDecimal("0");

            switch (SETOR){
                case "none":
                    for (int i=0;i<Home.baseTitulos.size();i++){
                        BigDecimal fatmes = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(i).getValortit()));

                        if(Home.baseTitulos.get(i).getComptit()==longmeses.get(k)) {
                            fat = fat.add(fatmes);

                            if (Home.baseTitulos.get(i).getContacobtit().equals("Adesão")) {
                                adesão = adesão.add(fatmes);
                            }else{
                                mensal=mensal.add(fatmes);
                            }
                            if (Home.baseTitulos.get(i).getSituacaotit() == 1) {
                                inad = inad.add(fatmes);
                            }
                        }

                    }

                    break;
                default:

                    for (int i=0;i<Home.baseTitulos.size();i++){
                        BigDecimal fatmes = new BigDecimal(util.S_to_Big(Home.baseTitulos.get(i).getValortit()));

                        if(Home.baseTitulos.get(i).getComptit()==longmeses.get(k)
                                && Home.baseTitulos.get(i).getKeysetortit().equals(SETOR)) {
                            fat = fat.add(fatmes);

                            if (Home.baseTitulos.get(i).getContacobtit().equals("Adesão")) {
                                adesão = adesão.add(fatmes);
                            }else{
                                mensal=mensal.add(fatmes);
                            }
                            if (Home.baseTitulos.get(i).getSituacaotit() == 1){
                                inad = inad.add(fatmes);
                            }
                        }

                    }


            }

            work_fatu.add(new Work_Rec("Faturamento",converte(util.Big_to_S(fat)),"#ff0000",1));
            work_ades.add(new Work_Rec("Adesão",converte(util.Big_to_S(adesão)),"#ff0000",1));
            work_inad.add(new Work_Rec("Inadimplente",converte(util.Big_to_S(inad)),"#ff0000",1));
            work_mensal.add(new Work_Rec("Mensalidade",converte(util.Big_to_S(mensal)),"#ff0000",1));
            BigDecimal fatres=new BigDecimal("0");
            float rres = converte(util.Big_to_S(fatres));
            work_result.add(new Work_Rec("Resultado",rres,"#FFAB00",1));
        }
        criaGrapfLine();
    }

    private LineData generateLineGeral(){


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        ArrayList<Entry> Efat = new ArrayList<>();
        ArrayList<Entry> Eads = new ArrayList<>();
        ArrayList<Entry> Eind = new ArrayList<>();
        ArrayList<Entry> Emes = new ArrayList<>();

        for (int index = 1; index < work_fatu.size()+1; index++) {
            Efat.add(new Entry(index, work_fatu.get(index-1 ).getRecvrr()));
        }
        for (int index = 1; index < work_ades.size() + 1; index++) {
            Eads.add(new Entry(index, work_ades.get(index - 1).getRecvrr()));
        }
        for (int index = 1; index < work_inad.size() + 1; index++) {
            Eind.add(new Entry(index, work_inad.get(index - 1).getRecvrr()));
        }
        for (int index = 1; index < work_mensal.size() + 1; index++) {
            Emes.add(new Entry(index, work_mensal.get(index - 1).getRecvrr()));
        }

        LineDataSet fat = new LineDataSet(Efat, "Faturamento");
        fat.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });

        LineDataSet ads = new LineDataSet(Eads, "Adesão");
        fat.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });
        LineDataSet ind = new LineDataSet(Eind, "Inadimplente");
        fat.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });
        LineDataSet mes = new LineDataSet(Emes, "Mensalidade");
        fat.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return util.formataTextoValor(value);
            }
        });



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

        ads.setValueTextColor(getResources().getColor(R.color.coradesao));
        ads.setColor(getResources().getColor(R.color.coradesao));
        ads.setLineWidth(2.5f);
        ads.setFillColor(getResources().getColor(R.color.coradesao));
        ads.setDrawValues(true);
        ads.setValueTextSize(10f);
        ads.setDrawFilled(true);
        ads.setDrawCircleHole(false);
        ads.setDrawCircles(false);
        ads.setFillAlpha(50);
        ads.setLineWidth(2.5f);
        ads.setColor(getResources().getColor(R.color.coradesao));

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

        mes.setValueTextColor(getResources().getColor(R.color.preto));
        mes.setColor(getResources().getColor(R.color.preto));
        mes.setLineWidth(2.5f);
        mes.setFillColor(getResources().getColor(R.color.preto));
        mes.setDrawValues(true);
        mes.setValueTextSize(10f);
        mes.setDrawFilled(true);
        mes.setDrawCircleHole(false);
        mes.setDrawCircles(false);
        mes.setFillAlpha(50);
        mes.setLineWidth(2.5f);
        mes.setColor(getResources().getColor(R.color.preto));

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

        dataSets.add(fat);
        dataSets.add(ads);
        dataSets.add(ind);
        dataSets.add(mes);

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

        l.setTextSize(10f);
        legendEntryA.label = "Tot.";
        legendEntryA.formColor = getResources().getColor(R.color.corfat);
        legendEntryB.label = "Mens.";
        legendEntryB.formColor = getResources().getColor(R.color.preto);
        legendEntryC.label = "Ades.";
        legendEntryC.formColor = getResources().getColor(R.color.coradesao);
        legendEntryD.label = "Inad.";
        legendEntryD.formColor = getResources().getColor(R.color.corinad);

        l.setCustom(Arrays.asList(legendEntryA, legendEntryB,  legendEntryC, legendEntryD));
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

        data.setData(generateBarRes());
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
        chart.setScaleEnabled(true);
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


    private void generateLineCli(){
        ctrlGraph=1;
        chart.clear();

        Sinsmes.clear();
        ArrayList<Long> longmeses = new ArrayList<>();
        work_ins.clear();
        work_result.clear();
        LocalDate dtmes ;
        dataline.clear();

        for(int i=5;i>-1;i--){
            dtmes=database.minusMonths(i);
            dataline.add(dtmes.format(fmt));
            long lmes=util.long_X_ld(dtmes);
            longmeses.add(lmes);
        }

        BigDecimal ins = new BigDecimal("0");

        for(int k=0;k<longmeses.size();k++){

            switch (SETOR){
                case "none":
                    for (int i=0;i<Home.baseTitulos.size();i++){
                        if(Home.baseTitulos.get(i).getComptit()==longmeses.get(k)) {
                           if(!Sinsmes.contains(Home.baseTitulos.get(i).getInstalltit())){
                               Sinsmes.add(Home.baseTitulos.get(i).getInstalltit().toString());
                           }
                        }
                    }

                    break;
                default:

                    for (int i=0;i<Home.baseTitulos.size();i++){
                        if(Home.baseTitulos.get(i).getComptit()==longmeses.get(k)
                        && Home.baseTitulos.get(i).getKeysetortit().equals(SETOR)) {
                            if(!Sinsmes.contains(Home.baseTitulos.get(i).getInstalltit())){
                                Sinsmes.add(Home.baseTitulos.get(i).getInstalltit().toString());
                            }
                        }
                    }

            }

            work_ins.add(new Work_Rec("Install",converte(String.valueOf(Sinsmes.size())),"#ff0000",1));
            BigDecimal fatres=new BigDecimal("0");
            float rres = converte(util.Big_to_S(fatres));
            work_result.add(new Work_Rec("Resultado",rres,"#FFAB00",1));
        }
        criaGrapfCli();
    }

    private LineData generateCli(){


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        ArrayList<Entry> Eins = new ArrayList<>();


        for (int index = 1; index < work_ins.size()+1; index++) {
            Eins.add(new Entry(index, work_ins.get(index-1 ).getRecvrr()));
        }

        LineDataSet ins = new LineDataSet(Eins, "Clientes");
        ins.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                return String.format ("%.0f",value);
                //return util.formataTextoValor(value);
            }
        });





        ins.setValueTextColor(getResources().getColor(R.color.corfat));
        ins.setColor(getResources().getColor(R.color.corfat));
        ins.setLineWidth(2.5f);
        ins.setFillColor(getResources().getColor(R.color.corfat));
        ins.setDrawValues(true);
        ins.setValueTextSize(10f);
        ins.setDrawFilled(true);
        ins.setDrawCircleHole(false);
        ins.setDrawCircles(false);
        ins.setFillAlpha(50);
        ins.setLineWidth(2.5f);
        ins.setColor(getResources().getColor(R.color.corfat));



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

        dataSets.add(ins);
        LineData data = new LineData(dataSets);
        return data;

    }

    private void criaGrapfCli() {

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


        l.setTextSize(10f);
        legendEntryA.label = "Clientes";
        legendEntryA.formColor = getResources().getColor(R.color.corfat);


        l.setCustom(Arrays.asList(legendEntryA));
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

        data.setData(generateBarRes());
        data.setData(generateCli());


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
        chart.setScaleEnabled(true);
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

    private BarData generateBarRes() {

        barEntryArrayListRec = new ArrayList<>();

        for (int i = 1; i < work_result.size() + 1; i++) {
            float vrr =(float) work_result.get(i - 1).getRecvrr();
            barEntryArrayListRec.add(new BarEntry(i, new float[]{vrr}));
            Log.i("work_result", i +" work_result" +work_result.get(i-1).getRecvrr());
        }
        barEntryArrayListRec.add(new BarEntry(7, new float[]{0}));
        Log.i("vrrfloat", " barEntryArrayListRec" +" : " +  barEntryArrayListRec.size());
        BarDataSet barDataSet = new BarDataSet(barEntryArrayListRec, "Receitas");



        int coresRec[]= new int[work_result.size()];
        for (int i=0;i<work_result.size();i++){
            coresRec[i]=Color.parseColor(work_result.get(i).getReccor());
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