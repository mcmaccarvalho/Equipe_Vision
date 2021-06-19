package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Consulta_Cli;
import com.ponto.ideal.solucoes.equipe_vision.ui.cadastro.Frag_Mostra_Install;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class InstallPagerAdapter extends FragmentStatePagerAdapter {


    public int cont;
    Context context;

    public InstallPagerAdapter(FragmentManager fm, Integer cont, Context context) {
        super(fm);
        this.cont =cont;
        this.context=context;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new Frag_Mostra_Install();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(Frag_Mostra_Install.ARG_OBJECT, i );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return cont;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
