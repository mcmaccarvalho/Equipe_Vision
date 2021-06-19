package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter_Search extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Clientes> arraylist;
    private ArrayList<Clientes> arrayrecebe;

    public Adapter_Search(Context context,ArrayList<Clientes> arrayrecebe) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Clientes>();
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
    public Clientes getItem(int position) {
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
        if(Home.bnome) {
            holder.name.setText(arrayrecebe.get(position).getNome());
        }else{
            holder.name.setText(arrayrecebe.get(position).getApelido());
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayrecebe.clear();
        if (charText.length() == 0) {
            arrayrecebe.addAll(arraylist);
        } else {
            for (Clientes wp : arraylist) {
                if(Home.bnome) {
                    if (wp.getNome().toLowerCase(Locale.getDefault()).contains(charText)) {
                        arrayrecebe.add(wp);
                    }
                }else{
                    if (wp.getApelido().toLowerCase(Locale.getDefault()).contains(charText)) {
                        arrayrecebe.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}