package com.ponto.ideal.solucoes.equipe_vision.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Search;
import com.ponto.ideal.solucoes.equipe_vision.Adapter.Adapter_Search_Instal;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Frag_Busca_Install extends Fragment {

    private SearchView svnome;
    private ListView lvnome;
    private CardView cvlvnome;
    private ConstraintLayout clbnome;


    private Adapter_Search_Instal adapter_search;

    public static ArrayList<Clientes> baseClientes= new ArrayList<>();
    public static ArrayList<Instalacoes> baseInstall = new ArrayList<>();
    public static ArrayList<Instalacoes> instalcli=new ArrayList<>();

    public Frag_Busca_Install() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frag__busca_install, container, false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {

        svnome=v.findViewById(R.id.svnome);
        cvlvnome=v.findViewById(R.id.cvlvnome);
        clbnome=v.findViewById(R.id.clbnome);
        lvnome=v.findViewById(R.id.lvnome);


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


        lvnome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                util.vibratePhone(getContext(), (short) 20);

                Log.i("clibusca","Cliente Size: "+baseClientes.size() +
                        "install: " + baseInstall.get(position).getNuminstal());
                Clientes cc = new Clientes();
                for(int i=0;i<baseClientes.size();i++){
                    if(baseClientes.get(i).getKeycli().equals(baseInstall.get(position).getCliente())){
                        cc=baseClientes.get(i);
                    }
                }
                if(cc!=null){
                    Log.i("clibusca","Cliente: "+cc.getNome());
                }else{
                    Log.i("clibusca","Cliente: null");
                }

                HomeViewModel.setClientes(cc);
                HomeViewModel.setCpfcod(cc.getCpf());
                instalcli.clear();
                for(int i=0;i<baseInstall.size();i++){
                    if(baseInstall.get(i).getCliente().equals(cc.getKeycli())){
                        instalcli.add(baseInstall.get(i));
                    }
                }
                HomeViewModel.setArrayinstall(instalcli);
                HomeViewModel.setInstall(baseInstall.get(position));
                MainActivity.navController.navigate(R.id.action_home_to_frag_Consulta_Cli);
            }
        });

            svnome.setQueryHint("Filtrar por Instalação");
            Collections.sort(baseInstall, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Instalacoes p1 = (Instalacoes) o1;
                    Instalacoes p2 = (Instalacoes) o2;
                    if(p1.getNuminstal().compareToIgnoreCase(p2.getNuminstal())<0){
                        return -1;
                    }else if(p1.getNuminstal().compareToIgnoreCase(p2.getNuminstal())>0){
                        return 1;
                    }
                    return 1;

                }
            });

        adapter_search=new Adapter_Search_Instal(getContext(),baseInstall);
        lvnome.setAdapter( adapter_search);

    }

    private void setupSearchView() {
        svnome.setIconifiedByDefault(false);
        svnome.setSubmitButtonEnabled(false);
        svnome.setQueryHint("Filtrar");
    }
}