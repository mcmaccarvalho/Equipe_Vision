package com.ponto.ideal.solucoes.equipe_vision.ui.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Search;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Despesas;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Frag_Busca_Nome extends Fragment {

    private SearchView svnome;
    private ListView lvnome;
    private CardView cvlvnome;
    private ConstraintLayout clbnome;
    private RadioButton rbnome,rbapelido;

    private Adapter_Search adapter_search;

    public static ArrayList<Clientes> baseClientes= new ArrayList<>();
    public static ArrayList<Instalacoes> baseInstall = new ArrayList<>();
    public static ArrayList<Instalacoes> instalcli=new ArrayList<>();

    public Frag_Busca_Nome() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frag__busca__nome, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {

        svnome=v.findViewById(R.id.svnome);
        cvlvnome=v.findViewById(R.id.cvlvnome);
        clbnome=v.findViewById(R.id.clbnome);
        lvnome=v.findViewById(R.id.lvnome);
        rbapelido=v.findViewById(R.id.rbapelido);
        rbnome=v.findViewById(R.id.rbnome);

        baseClientes=Home.baseClientes;
        baseInstall=Home.baseInstall;
        setupSearchView();

        svnome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;

                 adapter_search.filter(text);

                return false;
            }
        });

        rbnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbnome.isChecked()) {
                    svnome.setQueryHint("Filtrar por Nome");
                    Home.bnome = true;
                    Collections.sort(baseClientes, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            Clientes p1 = (Clientes) o1;
                            Clientes p2 = (Clientes) o2;
                            if(p1.getNome().compareToIgnoreCase(p2.getNome())<0){
                                return -1;
                            }else if(p1.getNome().compareToIgnoreCase(p2.getNome())>0){
                                return 1;
                            }
                            return 1;

                        }
                    });
                }else{
                    svnome.setQueryHint("Filtrar por Apelido");
                    Home.bnome = false;
                    Collections.sort(baseClientes, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            Clientes p1 = (Clientes) o1;
                            Clientes p2 = (Clientes) o2;
                            if(p1.getApelido().compareToIgnoreCase(p2.getApelido())<0){
                                return -1;
                            }else if(p1.getApelido().compareToIgnoreCase(p2.getApelido())>0){
                                return 1;
                            }
                            return 1;

                        }
                    });

                }
                adapter_search.notifyDataSetChanged();
            }
        });
        rbapelido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbnome.isChecked()) {
                    svnome.setQueryHint("Filtrar por Nome");
                    Home.bnome = true;
                    Collections.sort(baseClientes, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            Clientes p1 = (Clientes) o1;
                            Clientes p2 = (Clientes) o2;
                            if(p1.getNome().compareToIgnoreCase(p2.getNome())<0){
                                return -1;
                            }else if(p1.getNome().compareToIgnoreCase(p2.getNome())>0){
                                return 1;
                            }
                            return 1;

                        }
                    });
                }else{
                    svnome.setQueryHint("Filtrar por Apelido");
                    Home.bnome = false; Collections.sort(baseClientes, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            Clientes p1 = (Clientes) o1;
                            Clientes p2 = (Clientes) o2;
                            if(p1.getApelido().compareToIgnoreCase(p2.getApelido())<0){
                                return -1;
                            }else if(p1.getApelido().compareToIgnoreCase(p2.getApelido())>0){
                                return 1;
                            }
                            return 1;

                        }
                    });

                }
                adapter_search.notifyDataSetChanged();
            }
        });

        lvnome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);

                    HomeViewModel.setClientes(baseClientes.get(position));
                    HomeViewModel.setCpfcod(baseClientes.get(position).getCpf());
                    instalcli.clear();
                    for(int i=0;i<baseInstall.size();i++){
                        if(baseInstall.get(i).getCliente().equals(baseClientes.get(position).getKeycli())){
                            HomeViewModel.setInstall(baseInstall.get(i));
                            instalcli.add(baseInstall.get(i));
                        }
                    }
                    HomeViewModel.setArrayinstall(instalcli);
                    MainActivity.navController.navigate(R.id.action_home_to_frag_Consulta_Cli);
            }
        });

        if(rbnome.isChecked()) {
            svnome.setQueryHint("Filtrar por Nome");
            Home.bnome = true;
            Collections.sort(baseClientes, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Clientes p1 = (Clientes) o1;
                    Clientes p2 = (Clientes) o2;
                    if(p1.getNome().compareToIgnoreCase(p2.getNome())<0){
                        return -1;
                    }else if(p1.getNome().compareToIgnoreCase(p2.getNome())>0){
                        return 1;
                    }
                    return 1;

                }
            });
        }else{
            svnome.setQueryHint("Filtrar por Apelido");
            Home.bnome = false;
            Collections.sort(baseClientes, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Clientes p1 = (Clientes) o1;
                    Clientes p2 = (Clientes) o2;
                    if(p1.getApelido().compareToIgnoreCase(p2.getApelido())<0){
                        return -1;
                    }else if(p1.getApelido().compareToIgnoreCase(p2.getApelido())>0){
                        return 1;
                    }
                    return 1;

                }
            });

        }
        adapter_search=new Adapter_Search(getContext(),baseClientes);
        lvnome.setAdapter( adapter_search);

    }

    private void setupSearchView() {
        svnome.setIconifiedByDefault(false);
        svnome.setSubmitButtonEnabled(false);
        svnome.setQueryHint("Filtrar");
    }
}