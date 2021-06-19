package com.ponto.ideal.solucoes.equipe_vision.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.administracao.Frag_Titulos;
import com.ponto.ideal.solucoes.equipe_vision.util.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Titulos extends RecyclerView.Adapter<Adapter_Titulos.ViewHolder> {

    private List<Titulos> listTitulos = new ArrayList();
    SparseBooleanArray itemStateArray = new SparseBooleanArray();

    private Context context;

    public Adapter_Titulos(List<Titulos> listTitulosT
            , Context ctx) {
        listTitulos = listTitulosT;
        context = ctx;
    }

    @Override
    public Adapter_Titulos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvtitulo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listTitulos.size();
    }

    void loaditem(List<Titulos> titins) {
        this.listTitulos = titins;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtnomeliga;
        public CheckBox selectionState;

        public TextView txtbanco;
        public TextView txtconta;
        public TextView txtcliente;
        public TextView txtinstall;
        public TextView txtvalor;
        public TextView txtvcto;
        public TextView txtstat;
        LinearLayout llstat;

        public ViewHolder(View view) {
            super(view);
            txtbanco = view.findViewById(R.id.txtbanco);
            txtconta = view.findViewById(R.id.txtconta);
            txtcliente = view.findViewById(R.id.txtcliente);
            txtinstall = view.findViewById(R.id.txtinstall);
            txtvalor = view.findViewById(R.id.txtvalor);
            txtvcto = view.findViewById(R.id.txtvcto);
            txtstat = view.findViewById(R.id.txtstat);
            llstat = view.findViewById(R.id.llstat);
            selectionState = view.findViewById(R.id.selectionState);

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
            txtcliente.setText(listTitulos.get(position).getNomeclientetit());
            txtinstall.setText(listTitulos.get(position).getInstalltit());

            txtvalor.setText(listTitulos.get(position).getValortit());

            Instant it = Instant.ofEpochMilli(listTitulos.get(position).getVencimentotit());
            ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
            LocalDate dataenvio = zdt.toLocalDate();

            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            String scomp = dataenvio.format(fmt);

            txtvcto.setText(scomp);

//
//            0 = aberto
//            1 = baixado
//            2 cancelado
            switch (listTitulos.get(position).getStatustit()) {

                case 0:
                    txtstat.setText("em aberto");
                    llstat.setBackgroundColor(Color.GREEN);
                    break;
                case 1:
                    txtstat.setText("baixado");
                    llstat.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    txtstat.setText("cancelado");
                    llstat.setBackgroundColor(Color.YELLOW);
                    break;
            }


            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {

                selectionState.setChecked(false);
            } else {

                selectionState.setChecked(true);
            }
            //   selectionState.setText(String.valueOf(joglist.get(position).getPosition()));
        }


        @Override
        public void onClick(View v) {

            int sizeins = Frag_Titulos.arrayTitulo.size();

            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                llstat.setBackgroundColor(Color.YELLOW);
                selectionState.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            } else {
                llstat.setBackgroundColor(Color.CYAN);
                selectionState.setChecked(false);
                itemStateArray.put(adapterPosition, false);

            }


        }
    }
}
