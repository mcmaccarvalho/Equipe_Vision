package com.ponto.ideal.solucoes.equipe_vision.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.Valida_CPF;
import com.ponto.ideal.solucoes.equipe_vision.model.Adesao;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Settings;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.model.Type_Cobranca;
import com.ponto.ideal.solucoes.equipe_vision.util.Mask;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends Fragment {

    public static HomeViewModel homeViewModel;
    private EditText edtcpf;


    private ConstraintLayout clcad;
    boolean bvalida =true;
    private ImageView imgcpf;
    private ProgressBar mprogressBar;


    public static ViewPager vpclass;
    FragmentManager fragmentManager;



    public static int ctrlItem=0;
    public static boolean bnome=true;

    public static int origemCli = 0;
    public static int numinstal=0;
    public static ArrayList<Clientes> baseClientes= new ArrayList<>();
    public static ArrayList<Instalacoes> baseInstall = new ArrayList<>();
 //   public static ArrayList<Instalacoes> installarray;
    public static ArrayList<Instalacoes> instalcli=new ArrayList<>();
    public static ArrayList<Setores> baseSetores= new ArrayList<>();
    public static ArrayList<Tabela_Produtos> baseProdutos= new ArrayList<>();
    public static ArrayList<Links> baseLinks= new ArrayList<>();
    public static ArrayList<Adesao> baseAdesao= new ArrayList<>();
    public static ArrayList<Despesas> baseDepesas= new ArrayList<>();

    public static ArrayList<Type_Cobranca> baseTypeCobranca= new ArrayList<>();
    public static ArrayList<Cobrancas> baseCobrancas= new ArrayList<>();

    public static ArrayList<Titulos> baseTitulos= new ArrayList<>();

    public static ArrayList<Settings> baseSettings= new ArrayList<>();
    public static Settings Settings;



    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {


        fragmentManager = getChildFragmentManager();
        vpclass = v.findViewById(R.id.vpclass);

        

        //todo acertar titulo de Gerenciar titulos


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        HomeViewModel.setClientes(null);
        HomeViewModel.setCpfcod(null);
        origemCli = 0;

        imgcpf = v.findViewById(R.id.imgcpf);
        mprogressBar = v.findViewById(R.id.mprogressBar);
        clcad= v.findViewById(R.id.clcad);
        edtcpf = v.findViewById(R.id.edtcpf);
        edtcpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, edtcpf));

        mprogressBar.setVisibility(View.INVISIBLE);

        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(), (short) 20);
                hideSoftKeyboard();
                edtcpf.clearFocus();

            }
        });


        FirebaseFirestore.getInstance().collection("/despesas")
                .orderBy("tipodespesa", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseDepesas.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Despesas idesp = doc.toObject(Despesas.class);
                            baseDepesas.add(idesp);

                        }
                    }
                });


        FirebaseFirestore.getInstance().collection("/cobrancas")
                .orderBy("typecob_cobranca", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseCobrancas.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Cobrancas itype = doc.toObject(Cobrancas.class);
                            baseCobrancas.add(itype);

                        }
                    }
                });



            FirebaseFirestore.getInstance().collection("/type_cobranca")
                .orderBy("tipo_type_cob", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Type_Cobranca>  cc = new ArrayList<>();
                        baseTypeCobranca.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Type_Cobranca nss = doc.toObject(Type_Cobranca.class);
                                cc.add(nss);
                            }
                            if (cc.size() == 0) {
                                geraTypeCobranca();
                            }else{
                                baseTypeCobranca.addAll(cc);
                            }
                        }
                    }
                });


        FirebaseFirestore.getInstance().collection("/settings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Settings> ast = new ArrayList<>();
                        Settings  ss = new Settings();
                        baseSettings.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Settings nss = doc.toObject(Settings.class);
                                ast.add(nss);
                                Log.i("sethome","doc " + doc.getData());
                            }
                            if (ast.size()==0) {
                                geraSettings();
                            }else{
                                    Settings=ast.get(0);
                            }
                        }
                    }
                });


        FirebaseFirestore.getInstance().collection("/adesao")
                .orderBy("nome_adesao", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseAdesao.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Adesao iadesao = doc.toObject(Adesao.class);
                            baseAdesao.add(iadesao);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("/titulos")
                .orderBy("numerotit", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseTitulos.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Titulos ititi= doc.toObject(Titulos.class);
                            baseTitulos.add(ititi);

                        }
                    }
                });





        FirebaseFirestore.getInstance().collection("/links")
                .orderBy("nomelink", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseLinks.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Links itabprod = doc.toObject(Links.class);
                            baseLinks.add(itabprod);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("/produtos")
                .orderBy("nomeproduto", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseProdutos.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Tabela_Produtos itabprod = doc.toObject(Tabela_Produtos.class);
                            baseProdutos.add(itabprod);
                        }
                    }
                });


        FirebaseFirestore.getInstance().collection("/instalacoes")
                .orderBy("numinstal", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseInstall.clear();
                        if(queryDocumentSnapshots!=null) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Instalacoes iinst = doc.toObject(Instalacoes.class);
                                baseInstall.add(iinst);
                            }
                            numinstal = baseInstall.size();
                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("/clientes")
                .orderBy("nome", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseClientes.clear();
                        if(queryDocumentSnapshots!=null) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Clientes icli = doc.toObject(Clientes.class);
                                baseClientes.add(icli);
                            }
                        }
                    }
                });


        FirebaseFirestore.getInstance().collection("/setores")
                .orderBy("nomesetor", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        baseSetores.clear();
                        if(queryDocumentSnapshots!=null) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Setores iset = doc.toObject(Setores.class);
                                baseSetores.add(iset);
                            }
                            setupViewPager(vpclass);
                        }
                    }
                });



        imgcpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.vibratePhone(getContext(), (short) 20);
                hideSoftKeyboard();

                String ccc = edtcpf.getText().toString();

                if (ccc == null || ccc.equals("")) {
                    util.showSnackError(clcad, "Digite CPF para Busca ou Cadastro");
                    return;
                }

                switch (ccc) {
                    case "999.999.999-99":
                        mprogressBar.setVisibility(View.VISIBLE);
                        Long ll = System.currentTimeMillis();
                        String sll = String.valueOf(ll);
                        Log.i("sll", sll);
                        sll = sll.substring(sll.length() - 4);
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DATE);
                        String dsll = String.format("%02d", new Object[]{day});
                        int month = cal.get(Calendar.MONTH) + 1;
                        String msll = String.format("%02d", new Object[]{month});
                        Log.i("sll", "day " + day + " : " + month);
                        edtcpf.setText("000" + sll + dsll + msll);
                        ccc = edtcpf.getText().toString();
                        HomeViewModel.setClientes(null);
                        HomeViewModel.setCpfcod(ccc);
                        HomeViewModel.setInstall(null);
                        origemCli = 1;
                        edtcpf.setText("");
                        MainActivity.navController.navigate(R.id.action_home_to_frag_Cad_Cli);
                        break;
                    default:
                        mprogressBar.setVisibility(View.VISIBLE);
                        String ccc2 =ccc.replace(".","");
                        ccc2 = ccc2.replace("-","");
                        Log.i("bvalida",bvalida + " ccc2: " + ccc2);
                        if(!Valida_CPF.isCPF(ccc2) ) {
                            mprogressBar.setVisibility(View.INVISIBLE);
                            util.showSnackError(clcad, "Digite um CPF válido");
                            return;
                        }else {
                            if (baseClientes.size() == 0) {
                                HomeViewModel.setClientes(null);
                                HomeViewModel.setCpfcod(ccc);
                                HomeViewModel.setInstall(null);
                                origemCli = 1;
                                edtcpf.setText("");
                                MainActivity.navController.navigate(R.id.action_home_to_frag_Cad_Cli);
                            } else {
                                boolean tem = false;
                                Clientes cvai = new Clientes();
                                for (int i = 0; i < baseClientes.size(); i++) {
                                    if (baseClientes.get(i).getCpf().equals(ccc)) {
                                        tem = true;
                                        cvai = baseClientes.get(i);
                                        break;
                                    }
                                }

                                if (tem) {
                                    HomeViewModel.setClientes(cvai);
                                    HomeViewModel.setCpfcod(cvai.getCpf());
                                    boolean teminst = false;
                                    for (int i = 0; i < baseInstall.size(); i++) {
                                        if (baseInstall.get(i).getCliente().equals(cvai.getKeycli())) {
                                            HomeViewModel.setInstall(baseInstall.get(i));
                                            teminst = true;
                                            break;
                                        }
                                    }
                                    instalcli.clear();
                                    for(int i=0;i<baseInstall.size();i++){
                                        if(baseInstall.get(i).getCliente().equals(cvai.getKeycli())){
                                            HomeViewModel.setInstall(baseInstall.get(i));
                                            instalcli.add(baseInstall.get(i));
                                        }
                                    }
                                    HomeViewModel.setArrayinstall(instalcli);

                                    origemCli = 2;
                                    edtcpf.setText("");
                                    MainActivity.navController.navigate(R.id.action_home_to_frag_Consulta_Cli);
                                } else {
                                    HomeViewModel.setClientes(null);
                                    HomeViewModel.setCpfcod(ccc);
                                    // HomeViewModel.setInstall(null);
                                    origemCli = 1;
                                    edtcpf.setText("");
                                    MainActivity.navController.navigate(R.id.action_home_to_frag_Cad_Cli);
                                }
                            }
                        }

                }

                }


        });


    }

    private void geraSettings(){


        Log.i("sethome","Entrou geraset");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        Settings s1 = new Settings();
        s1.setMax_dias_atraso("1");
        s1.setValor_min_boleto("10,00");
        s1.setPzo_vcto_adesao("1");
        DocumentReference dr = db.collection("settings").document("settings");
        batch.set(dr, s1);
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcad, "Problemas ao atualizar Settings. Informe Administrador");
            }
        });

        Settings=s1;
    }

    private void geraTypeCobranca() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        Type_Cobranca tc = new Type_Cobranca();
        tc.setId_cobranca(0);
        tc.setKey_type_cob("Selecione");
        tc.setNome_type_cob("Selecione");
        tc.setTipo_type_cob(0);
        DocumentReference dr = db.collection("type_cobranca")
                .document(tc.getKey_type_cob());
        batch.set(dr, tc);

        tc.setId_cobranca(1);
        tc.setKey_type_cob("Boleto");
        tc.setNome_type_cob("Boleto");
        tc.setTipo_type_cob(1);
        dr = db.collection("type_cobranca")
                .document(tc.getKey_type_cob());
        batch.set(dr, tc);

        tc.setId_cobranca(2);
        tc.setKey_type_cob("Carteira");
        tc.setNome_type_cob("Carteira");
        tc.setTipo_type_cob(2);
        dr = db.collection("type_cobranca")
                .document(tc.getKey_type_cob());
        batch.set(dr, tc);

        tc.setId_cobranca(3);
        tc.setKey_type_cob("Cartão");
        tc.setNome_type_cob("Cartão");
        tc.setTipo_type_cob(3);
        dr = db.collection("type_cobranca")
                .document(tc.getKey_type_cob());
        batch.set(dr, tc);

        tc.setId_cobranca(4);
        tc.setKey_type_cob("Cortesia");
        tc.setNome_type_cob("Cortesia");
        tc.setTipo_type_cob(10);
        dr = db.collection("type_cobranca")
                .document(tc.getKey_type_cob());
        batch.set(dr, tc);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                util.showSnackOk(clcad, "Problemas ao atualizar Tipo Cobrança. Informe Administrador");
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Frag_Chart_Setor(),"Chart");
        adapter.addFragment(new Frag_Busca_Nome(),"Busca");
        adapter.addFragment(new Frag_Busca_Install(),"Instal");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}