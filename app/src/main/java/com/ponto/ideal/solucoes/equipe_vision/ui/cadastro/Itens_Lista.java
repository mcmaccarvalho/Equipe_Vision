package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Adesao;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Itens_Lista extends Fragment {

    private TextView nomelista,tipolista,valorlista,txttipo,txtcad,txtbase,txtstatlista;
    private ImageView imgleft,imgright;
    private Spinner spfiltro;
    private RecyclerView rvitem;
    private LinearLayout llgeral,lldatadesp;
    private ConstraintLayout clcaditem;
    private CardView cvcad;
    private int ctrlItem;
    public static GroupAdapter adapter;

    private ArrayList<Links> arraylinks=new ArrayList<>();
    private ArrayList<Tabela_Produtos> arrayprod=new ArrayList<>();
    private ArrayList<Cobrancas> arraycob=new ArrayList<>();
    private ArrayList<Adesao> arrayadesao=new ArrayList<>();
    private ArrayList<Setores> arraysetores=new ArrayList<>();
    private ArrayList<Despesas> arraydesp=new ArrayList<>();

    public static Links oldlink;
    public static Tabela_Produtos oldprod;
    public static Cobrancas oldCob;
    public static Adesao oldAdesao;
    public static Setores oldsetor;
    public static Despesas olddesp;

    public static boolean bedit=false;
    public static boolean blockAdapt=false;


    private LocalDate mesbase;
//    private LocalDate database;
    private long longmesbase;

    DateTimeFormatter dma = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("MM-yy")
            .withResolverStyle(ResolverStyle.STRICT);

    private HomeViewModel homeViewModel;

    public Itens_Lista() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_itesn_lista, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {


        nomelista   = v.findViewById(R.id.nomelista   );
        tipolista   = v.findViewById(R.id.tipolista   );
        valorlista  = v.findViewById(R.id.valorlista  );
        txttipo     = v.findViewById(R.id.txttipo     );
        txtcad      = v.findViewById(R.id.txtcad      );
        imgleft=v.findViewById(R.id.imgleft);
        imgright=v.findViewById(R.id.imgright);
        txtbase=v.findViewById(R.id.txtbase);
        spfiltro=v.findViewById(R.id.spfiltro);
        llgeral      = v.findViewById(R.id.llgeral      );
        lldatadesp      = v.findViewById(R.id.lldatadesp      );
        rvitem      = v.findViewById(R.id.rvitem      );
        cvcad       = v.findViewById(R.id.cvcad       );
        clcaditem   = v.findViewById(R.id.clcaditem   );

        homeViewModel = Home.homeViewModel;

        homeViewModel.getApdint().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                if (i == 1) {
                    homeViewModel.setApdint(0);
                    carregaAdapter();
                }
            }
        });



        ctrlItem= Home.ctrlItem;

        switch (ctrlItem){
            case 0:
                nomelista .setText("Nome");
                tipolista .setText("Cap. Mega");
                valorlista.setText("Valor R$");
                txttipo   .setText("Links");
                txtcad    .setText("Add Link");
                llgeral.removeView(lldatadesp);
                break;
            case 1:
                nomelista .setText("Nome");
                tipolista .setText("Tipo");
                valorlista.setText("Valor R$");
                txttipo   .setText("Produtos");
                txtcad    .setText("Add Produto");
                llgeral.removeView(lldatadesp);
                break;
            case 2:
                nomelista .setText("Nome");
                tipolista .setText("Tipo");
                valorlista.setText("Conta");
                txttipo   .setText("Cobranças");
                txtcad    .setText("Add Cobrança");
                llgeral.removeView(lldatadesp);
                break;
            case 3:
                nomelista .setText("Nome");
                tipolista .setText("Tipo Cob");
                valorlista.setText("Valor R$");
                txttipo   .setText("Adesão");
                txtcad    .setText("Add Adesão");
                llgeral.removeView(lldatadesp);
                break;

            case 4:
                nomelista .setText("Nome");
                tipolista .setText("Cep ini");
                valorlista.setText("Cep Fim");
                txttipo   .setText("Setores");
                txtcad    .setText("Add Setor");
                llgeral.removeView(lldatadesp);
                break;
            case 5:
                nomelista .setText("Link");
                tipolista .setText("Tipo");
                valorlista.setText("Data");
                txttipo   .setText("Despesas");
                txtcad    .setText("Add Despesas");
                mesbase=util.Dia_01_Mes(System.currentTimeMillis());
                longmesbase=util.longdiamesano(mesbase.format(dma));
                txtbase.setText(mesbase.format(fmt));
                break;
        }

        imgleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesbase=mesbase.minusMonths(1);
                txtbase.setText(mesbase.format(fmt));
                carregaadapterDesp();
            }
        });

        imgright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesbase=mesbase.plusMonths(1);
                txtbase.setText(mesbase.format(fmt));
                carregaadapterDesp();
            }
        });

        txtcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bedit=false;
                Itens_Cad.carregaItem();
                Cad_Itens.vpclass.setCurrentItem(1);
            }
        });



        final ArrayAdapter<CharSequence> adapterfiltro = ArrayAdapter.createFromResource(getContext(),
                R.array.tipo_desp_filtro, R.layout.sp_prod);
        adapterfiltro.setDropDownViewResource(R.layout.sp_prod);
        spfiltro.setAdapter(adapterfiltro);

        spfiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carregaadapterDesp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        adapter = new GroupAdapter();
        carregaAdapter();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                if (!blockAdapt) {
                    util.vibratePhone(getContext(), (short) 20);
                    bedit = true;
                    switch (Home.ctrlItem){
                        case 0:
                            ListaLink listaLink = (ListaLink) item;
                            Links lp = listaLink.link;
                            Itens_Cad.oldlink = lp;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;
                        case 1:
                            ListaProd listaProd = (ListaProd) item;
                            Tabela_Produtos tp = listaProd.produto;
                            Itens_Cad.oldprod = tp;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;
                        case 2:
                            ListaCob listaCob = (ListaCob) item;
                            Cobrancas tc = listaCob.cobranca;
                            Itens_Cad.oldCob = tc;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;
                        case 3:
                            ListaAdesao listaAdesao = (ListaAdesao) item;
                            Adesao ad = listaAdesao.adesao;
                            Itens_Cad.oldAdesao = ad;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;
                        case 4:
                            ListaSetor listaSetor = (ListaSetor) item;
                            Setores st = listaSetor.setor;
                            Itens_Cad.oldsetor = st;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;
                        case 5:
                            ListaDesp listaDesp = (ListaDesp) item;
                            Despesas dp = listaDesp.despesas;
                            Itens_Cad.olddesp = dp;
                            Itens_Cad.carregaItem();
                            Cad_Itens.vpclass.setCurrentItem(1);
                            break;


                    }




                }
            }
        });
    }

    public static void atualizalista(){
        adapter.notifyDataSetChanged();
    }

    private void carregaadapterDesp(){

        arraydesp.clear();
        adapter.clear();

        longmesbase=util.longdiamesano(mesbase.format(dma));

        if(spfiltro.getSelectedItemPosition()==0){
            for(int i=0;i<Home.baseDepesas.size();i++){
                if(Home.baseDepesas.get(i).getCompdesp()==longmesbase){
                    arraydesp.add(Home.baseDepesas.get(i));
                }
            }
        }else {
            for(int i=0;i<Home.baseDepesas.size();i++){
                if(Home.baseDepesas.get(i).getCompdesp()==longmesbase
                && Home.baseDepesas.get(i).getTipodespesa().equals(spfiltro.getSelectedItem()) ){
                    arraydesp.add(Home.baseDepesas.get(i));
                }
            }
        }


        Collections.sort(arraydesp, new Comparator() {
            public int compare(Object o1, Object o2) {
                Despesas p1 = (Despesas) o1;
                Despesas p2 = (Despesas) o2;
                if(p1.getLinkdespesa().compareToIgnoreCase(p2.getLinkdespesa())<0){
                    return -1;
                }else if(p1.getLinkdespesa().compareToIgnoreCase(p2.getLinkdespesa())>0){
                    return 1;
                }else if(p1.getTipodespesa().compareToIgnoreCase(p2.getTipodespesa())<0){
                    return -1;
                }else if(p1.getTipodespesa().compareToIgnoreCase(p2.getTipodespesa())>0){
                    return 1;
                }return 1;

            }
        });
        for (int i = 0; i < arraydesp.size(); i++) {
            adapter.add(new ListaDesp(arraydesp.get(i)));
        }


    }

    private void carregaAdapter () {
        adapter.clear();
        switch (ctrlItem){
            case 0:
                arraylinks=Home.baseLinks;
                for (int i = 0; i < arraylinks.size(); i++) {
                    adapter.add(new ListaLink(arraylinks.get(i)));
                }
                break;
            case 1:
                arrayprod=Home.baseProdutos;

                Collections.sort(arrayprod, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Tabela_Produtos p1 = (Tabela_Produtos) o1;
                        Tabela_Produtos p2 = (Tabela_Produtos) o2;
                        if(p1.getTipoproduto().compareToIgnoreCase(p2.getTipoproduto())<0){
                            return -1;
                        }else if(p1.getTipoproduto().compareToIgnoreCase(p2.getTipoproduto())>0){
                            return 1;
                        }else if(p1.getNomeproduto().compareToIgnoreCase(p2.getNomeproduto())<0){
                            return -1;
                        }else if(p1.getNomeproduto().compareToIgnoreCase(p2.getNomeproduto())>0){
                            return 1;
                        }return 1;

                    }
                });

                for (int i = 0; i < arrayprod.size(); i++) {
                    adapter.add(new ListaProd(arrayprod.get(i)));
                }
                break;
            case 2:
                arraycob=Home.baseCobrancas;
                Collections.sort(arraycob, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Cobrancas p1 = (Cobrancas) o1;
                        Cobrancas p2 = (Cobrancas) o2;
                        if(p1.getTypecob_cobranca().compareToIgnoreCase(p2.getTypecob_cobranca())<0){
                            return -1;
                        }else if(p1.getTypecob_cobranca().compareToIgnoreCase(p2.getTypecob_cobranca())>0){
                            return 1;
                        }else if(p1.getNome_cobranca().compareToIgnoreCase(p2.getNome_cobranca())<0){
                            return -1;
                        }else if(p1.getNome_cobranca().compareToIgnoreCase(p2.getNome_cobranca())>0){
                            return 1;
                        }return 1;

                    }
                });
                for (int i = 0; i < arraycob.size(); i++) {
                    adapter.add(new ListaCob(arraycob.get(i)));
                }
                break;
            case 3:
                arrayadesao=Home.baseAdesao;
                Collections.sort(arrayadesao, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Adesao p1 = (Adesao) o1;
                        Adesao p2 = (Adesao) o2;
                        if(p1.getTipo_cob_adesao().compareToIgnoreCase(p2.getTipo_cob_adesao())<0){
                            return -1;
                        }else if(p1.getTipo_cob_adesao().compareToIgnoreCase(p2.getTipo_cob_adesao())>0){
                            return 1;
                        }else if(p1.getNome_adesao().compareToIgnoreCase(p2.getNome_adesao())<0){
                            return -1;
                        }else if(p1.getNome_adesao().compareToIgnoreCase(p2.getNome_adesao())>0){
                            return 1;
                        }return 1;

                    }
                });
                for (int i = 0; i < arrayadesao.size(); i++) {
                    adapter.add(new ListaAdesao(arrayadesao.get(i)));
                }
                break;
            case 4:
                arraysetores=Home.baseSetores;

                for (int i = 0; i < arraysetores.size(); i++) {
                    adapter.add(new ListaSetor(arraysetores.get(i)));
                }
                break;
            case 5:
                carregaadapterDesp();
                break;
        }

        rvitem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvitem.setAdapter(adapter);
    }

    private class ListaLink extends Item<ViewHolder> {

        private final Links link;

        public ListaLink(Links link) {
            this.link = link;
        }


        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);
            if (link.getStatus() == 0) {
                llstat.setBackgroundColor(Color.RED);
            } else {
                llstat.setBackgroundColor(Color.GREEN);
            }

            txtnome.setText(link.getNomelink());
            txttipo.setText(link.getCapacidadelink());
            txtvalor.setText(link.getValorlink());

        }

        @Override
        public int getLayout() {
            return R.layout.rvitem;
        }
    }

    private class ListaProd extends Item<ViewHolder> {

        private final Tabela_Produtos produto;

        public ListaProd(Tabela_Produtos produto) {
            this.produto = produto;
        }


        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);
            if (produto.getStatusproduto() == 0) {
                llstat.setBackgroundColor(Color.RED);
            } else {
                llstat.setBackgroundColor(Color.GREEN);
            }

            txtnome.setText(produto.getNomeproduto());
            txttipo.setText(produto.getTipoproduto());
            txtvalor.setText(produto.getValorproduto());

        }

        @Override
        public int getLayout() {
            return R.layout.rvitem;
        }
    }

    private class ListaCob extends Item<ViewHolder> {

        private final Cobrancas cobranca;

        public ListaCob(Cobrancas cobranca) {
            this.cobranca = cobranca;
        }


        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);
            if (cobranca.getStatu_cobranca() == 0) {
                llstat.setBackgroundColor(Color.RED);
            } else {
                llstat.setBackgroundColor(Color.GREEN);
            }

            txtnome.setText(cobranca.getNome_cobranca());

//            for(int i=0;i<Home.baseTypeCobranca.size();i++){
//                if(Home.baseTypeCobranca.get(i).getKey_type_cob().equals(cobranca.getTypecob_cobranca())){
                    txttipo.setText(cobranca.getTypecob_cobranca());
//                    break;
//                }
//            }
            txtvalor.setText(cobranca.getConta_cobranca());

        }

        @Override
        public int getLayout() {
            return R.layout.rvitem;
        }
    }

    private class ListaAdesao extends Item<ViewHolder> {

        private final Adesao adesao;

        public ListaAdesao(Adesao adesao) {
            this.adesao = adesao;
        }


        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);
            if (adesao.getStatus_adesao() == 0) {
                llstat.setBackgroundColor(Color.RED);
            } else {
                llstat.setBackgroundColor(Color.GREEN);
            }

            txtnome.setText(adesao.getNome_adesao());
            for(int i=0;i<Home.baseTypeCobranca.size();i++) {
                if (Home.baseTypeCobranca.get(i).getKey_type_cob().equals(adesao.getTipo_cob_adesao())) {
                    txttipo.setText(Home.baseTypeCobranca.get(i).getNome_type_cob());
                    break;
                }
            }
            txtvalor.setText(adesao.getValor_adesao());
        }

        @Override
        public int getLayout() {
            return R.layout.rvitem;
        }
    }

    private class ListaSetor extends Item<ViewHolder> {

        private final Setores setor;

        public ListaSetor(Setores setor) {
            this.setor = setor;
        }


        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txtnome = viewHolder.itemView.findViewById(R.id.txtnome);
            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);
            if (setor.getStatus() == 0) {
                llstat.setBackgroundColor(Color.RED);
            } else {
                llstat.setBackgroundColor(Color.GREEN);
            }

            txtnome.setText(setor.getNomesetor());
            txttipo.setText(setor.getCepinicial());
            txtvalor.setText(setor.getCepfinal());

        }

        @Override
        public int getLayout() {
            return R.layout.rvitem;
        }
    }

    private class ListaDesp extends Item<ViewHolder> {

        private final Despesas despesas;

        public ListaDesp(Despesas despesa) {
            this.despesas =despesa;
        }



        @Override
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            TextView txttipo = viewHolder.itemView.findViewById(R.id.txttipo);
            TextView txtlink = viewHolder.itemView.findViewById(R.id.txtlink);
            TextView txtvalor = viewHolder.itemView.findViewById(R.id.txtvalor);
            TextView txtdata = viewHolder.itemView.findViewById(R.id.txtdata);
            TextView txtdesc = viewHolder.itemView.findViewById(R.id.txtdesc);
            LinearLayout llstat = viewHolder.itemView.findViewById(R.id.llstat);

            txttipo.setText(despesas.getTipodespesa());
            txtvalor.setText(despesas.getValordespesa());
            txtdesc.setText(despesas.getDescricaodespesa());
            for (int i = 0; i < Home.baseLinks.size(); i++) {
                if (Home.baseLinks.get(i).getKeylink().equals(despesas.getLinkdespesa())) {
                    txtlink.setText(Home.baseLinks.get(i).getNomelink());
                    break;
                }
            }

            if(despesas.getStatusdespesa()==1){
                llstat.setBackgroundColor(Color.GREEN);
            }else{
                llstat.setBackgroundColor(Color.GRAY);
            }

            Instant it = Instant.ofEpochMilli(despesas.getDatadesp());
            ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
            LocalDate datadesp = zdt.toLocalDate();

            DateTimeFormatter fmtc = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy")
                    .withResolverStyle(ResolverStyle.STRICT);


            txtdata.setText(datadesp.format(fmtc));


        }

        @Override
        public int getLayout() {
            return R.layout.rvdespesas;
        }
    }



}