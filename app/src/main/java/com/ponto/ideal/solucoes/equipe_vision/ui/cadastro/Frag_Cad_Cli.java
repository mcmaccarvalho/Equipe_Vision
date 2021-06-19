package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class Frag_Cad_Cli extends Fragment {

    public static ViewPager vpclass;
    public static TabLayout tab_vp;
    ConstraintLayout clcontainer;
    FragmentManager fragmentManager;

    public Frag_Cad_Cli() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag__cad__cli, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        fragmentManager = getChildFragmentManager();
        vpclass = v.findViewById(R.id.vpclass);
        tab_vp = v.findViewById(R.id.tab_vp);
        setupViewPager(vpclass);
        tab_vp.setupWithViewPager (vpclass);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Cad_Cli(),"Cliente");
        adapter.addFragment(new Cad_Install(), "Instalação");
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

}