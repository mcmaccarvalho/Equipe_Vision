package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Dias_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Vcto_Dia extends RecyclerView.Adapter<Adapter_Vcto_Dia.ViewHolder> {



    private List<Dias_Vcto> dias = new ArrayList();
    private Context context;
    private HomeViewModel homeViewModel;

    public Adapter_Vcto_Dia(List<Dias_Vcto> dias_vcto,Context ctx,HomeViewModel homeViewModel) {
        context = ctx;
        dias=dias_vcto;
        homeViewModel=homeViewModel;
    }

    @Override
    public Adapter_Vcto_Dia.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_dia, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dias.size();
    }

    void loaditem(List<Dias_Vcto> vctodia) {
        this.dias = vctodia;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private HomeViewModel homeViewModel = Home.homeViewModel;

        public TextView txtdia1;
        public TextView txtdia2;
        public TextView txtdia3;
        public TextView txtdia4;
        public TextView txtdia5;
        public TextView txtdia6;


        public ViewHolder(View view) {
            super(view);
            txtdia1 = view.findViewById(R.id.txtdia1);
            txtdia2 = view.findViewById(R.id.txtdia2);
            txtdia3 = view.findViewById(R.id.txtdia3);
            txtdia4 = view.findViewById(R.id.txtdia4);
            txtdia5 = view.findViewById(R.id.txtdia5);
            txtdia6 = view.findViewById(R.id.txtdia6);


            view.setOnClickListener(this);
            txtdia1.setOnClickListener(this);
            txtdia2.setOnClickListener(this);
            txtdia3.setOnClickListener(this);
            txtdia4.setOnClickListener(this);
            txtdia5.setOnClickListener(this);
            txtdia6.setOnClickListener(this);
            //checkbox click event handling
//                selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView,
//                                                 boolean isChecked) {
//                        if (isChecked) {
//
//
//                            Inscrever_Jogadores.jog1=txtnomeliga.getText().toString();
//                            util.showmessage(context,"esta marcado");
//                        } else {
//                            util.showmessage(context,"nao esta marcado");
//                        }
//                    }
//                });
        }

        void bind(int position) {

//            txtbanco.setText(listTitulos.get(position).getNomebancotit());
//            txtconta.setText(listTitulos.get(position).getContatit());
            txtdia1.setText(dias.get(position).getDiaA());
            txtdia2.setText(dias.get(position).getDiaB());
            txtdia3.setText(dias.get(position).getDiaC());
            txtdia4.setText(dias.get(position).getDiaD());
            txtdia5.setText(dias.get(position).getDiaE());
            txtdia6.setText(dias.get(position).getDiaF());

        }


        @Override
        public void onClick(View v) {

            int iddia = v.getId();

            String sdd = "";
           switch (iddia){
               case R.id.txtdia1:
                    sdd = txtdia1.getText().toString();
                   break;
               case R.id.txtdia2:
                   sdd = txtdia2.getText().toString();
                   break;
               case R.id.txtdia3:
                   sdd = txtdia3.getText().toString();
                   break;
               case R.id.txtdia4:
                   sdd = txtdia4.getText().toString();
                   break;
               case R.id.txtdia5:
                   sdd = txtdia5.getText().toString();
                   break;
               case R.id.txtdia6:
                   sdd = txtdia6.getText().toString();
                   break;
           }

            homeViewModel.setDiavcto(sdd);
            homeViewModel.setVctobol(true);

        }
    }
}
