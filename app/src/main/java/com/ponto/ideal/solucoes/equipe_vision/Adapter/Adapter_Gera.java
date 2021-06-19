package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Gerar_Mensalidades;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Gera extends RecyclerView.Adapter<Adapter_Gera.ViewHolder> {

    private List<Instalacoes> instalacoes = new ArrayList();
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private HomeViewModel homeViewModel;

    private Context context;

    public Adapter_Gera(List<Instalacoes> instalacoesI
            , Context ctx,HomeViewModel homeViewModel1) {
        instalacoes = instalacoesI;
        context = ctx;
        homeViewModel=homeViewModel1;
    }

    @Override
    public Adapter_Gera.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvinstal_gerar_mensal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return instalacoes.size();
    }

    void loaditem(List<Instalacoes> titins) {
        this.instalacoes = titins;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtinstall;
        public CheckBox selectionState;
        public TextView txtstat;
        LinearLayout llstat;
        private HomeViewModel vm = homeViewModel;

        public ViewHolder(View view) {
            super(view);
            txtinstall = view.findViewById(R.id.txtinstall);
            txtstat = view.findViewById(R.id.txtstat);
            llstat = view.findViewById(R.id.llstat);
           // selectionState = view.findViewById(R.id.cbgerar);

            selectionState.setClickable(false);
            //item click event listener
            view.setOnClickListener(this);

            //checkbox click event handling
//                selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView,
//                                                 boolean isChecked) {
//                        if (isChecked) {
//
//                        } else {
//
//                        }
//                    }
//                });
        }

        void bind(int position) {


            txtinstall.setText(instalacoes.get(position).getNuminstal()+" ("+instalacoes.get(position).getMegainstal()+")");

            switch (instalacoes.get(position).getStatus()) {

                case 3:
                    txtstat.setText("Inadimplente");
                    llstat.setBackgroundColor(Color.RED);
                    break;
                default:
                    txtstat.setText("Ativa");
                    llstat.setBackgroundColor(Color.GREEN);
            }


            // use the sparse boolean array to check
            if(Frag_Gerar_Mensalidades.geratodos){
                itemStateArray.put(position, true);
            }else{
                itemStateArray.put(position, false);
            }
            if (!itemStateArray.get(position, false)) {
                selectionState.setText("Não Gerar");
                selectionState.setChecked(false);

            } else {
                selectionState.setText("Gerar");
                selectionState.setChecked(true);
            }
            //   selectionState.setText(String.valueOf(joglist.get(position).getPosition()));
        }


        @Override
        public void onClick(View v) {
            //Frag_Gerar_Mensalidades.arrayInstal.clear();
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                selectionState.setChecked(true);
                itemStateArray.put(adapterPosition, true);
                if(!Frag_Gerar_Mensalidades.insagerar.contains(instalacoes.get(adapterPosition))) {
                    Frag_Gerar_Mensalidades.insagerar.add(instalacoes.get(adapterPosition).getNuminstal());
                }
            } else {
                selectionState.setChecked(false);
                itemStateArray.put(adapterPosition, false);
                for(int i=0;i<Frag_Gerar_Mensalidades.insagerar.size();i++) {
                    if (Frag_Gerar_Mensalidades.insagerar.get(i).equals(instalacoes.get(adapterPosition).getNuminstal())) {
                        Frag_Gerar_Mensalidades.insagerar.remove(i);
                    }
                }

            }
            vm.setBolgera(true);
        }
    }
}
