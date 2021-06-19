package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter_Search_Instal extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Instalacoes> arraylist;
    private ArrayList<Instalacoes> arrayrecebe;

    public Adapter_Search_Instal(Context context,ArrayList<Instalacoes> arrayrecebe) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Instalacoes>();
        this.arrayrecebe=arrayrecebe;
        this.arraylist.addAll(arrayrecebe);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return arrayrecebe.size();
    }

    @Override
    public Instalacoes getItem(int position) {
        return arrayrecebe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adp_cli, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.txtnome);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

            holder.name.setText(arrayrecebe.get(position).getNuminstal());


        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayrecebe.clear();
        if (charText.length() == 0) {
            arrayrecebe.addAll(arraylist);
        } else {
            for (Instalacoes wp : arraylist) {

                    if (wp.getNuminstal().toLowerCase(Locale.getDefault()).contains(charText)) {
                        arrayrecebe.add(wp);
                    }

            }
        }
        notifyDataSetChanged();
    }
}