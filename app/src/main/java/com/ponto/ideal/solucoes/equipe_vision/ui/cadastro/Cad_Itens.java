package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;

import java.util.ArrayList;
import java.util.List;

public class Cad_Itens extends Fragment {
    public static ViewPager vpclass;
    public static TabLayout tab_vp;
    ConstraintLayout clcontainer;
    FragmentManager fragmentManager;

    private String titleTab;
    private int ctrlItem;

    public Cad_Itens() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad__itens, container, false);
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
        switch (Home.ctrlItem){
            case 0:titleTab="Links";break;
            case 1:titleTab="Produtos";break;
            case 2:titleTab="Cobranças";break;
            case 3:titleTab="Adesão";break;
            case 4:titleTab="Setor";break;
            case 5:titleTab="Despesas";break;
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Itens_Lista(),"Lista");
        adapter.addFragment(new Itens_Cad(), "AAD/EDIT");
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
