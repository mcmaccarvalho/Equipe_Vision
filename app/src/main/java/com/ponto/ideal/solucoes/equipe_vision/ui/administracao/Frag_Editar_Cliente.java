package com.ponto.ideal.solucoes.equipe_vision.ui.administracao;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.Valida_CPF;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.Mask;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;


public class Frag_Editar_Cliente extends Fragment {


    private EditText edtnome,edtemail,edtapelido,edtfone,edtendereco,edtcompl,edtnumero,
            edtcep,edtcpf;
    private TextView txtcontnome,txtcontapelido,txtcontemail,txtcontend,txtcontcompl,txtdata,txtfin,txtreccli;
    private ImageView imgrecar;

    private Clientes clienteOld;
    private LinearLayout llstat,llfin;

    private Spinner spstat;
    private ConstraintLayout clcad;
    private ProgressBar mprogressBar;
    private Button btcad,btcancel;

    private String CPF,NOME,APELIDO,FONE,EMAIL,ENDERECO,NUMERO,CEP,COMPL,KEYCLI;
    private int STATUS;
    private long TIMESTAMP;
    public Frag_Editar_Cliente() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_editar_cliente, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        CPF="";NOME="";APELIDO="";FONE="";EMAIL="";ENDERECO="";NUMERO="";CEP="";COMPL="";
        KEYCLI="";STATUS=0;TIMESTAMP=0;

        edtcpf                 =v.findViewById(R.id.edtcpf          );
        edtnome                =v.findViewById(R.id.edtnome         );
        edtapelido             =v.findViewById(R.id.edtapelido      );
        edtemail               =v.findViewById(R.id.edtemail        );
        edtfone                =v.findViewById(R.id.edtfone         );
        edtendereco            =v.findViewById(R.id.edtendereco     );
        edtcompl               =v.findViewById(R.id.edtcompl        );
        edtnumero              =v.findViewById(R.id.edtnumero       );
        edtcep                 =v.findViewById(R.id.edtcep          );

        txtcontnome             =v.findViewById(R.id.txtcontnome    );
        txtcontapelido          =v.findViewById(R.id.txtcontapelido );
        txtcontemail            =v.findViewById(R.id.txtcontemail   );
        txtcontend              =v.findViewById(R.id.txtcontend     );
        txtcontcompl            =v.findViewById(R.id.txtcontcompl   );
        txtdata            =v.findViewById(R.id.txtdata   );
        txtreccli   =v.findViewById(R.id.txtreccli   );
        txtfin =v.findViewById(R.id.txtfin   );
        imgrecar =v.findViewById(R.id.imgrecar   );


        llfin = v.findViewById(R.id.llfin);
        llstat = v.findViewById(R.id.llstat);
        btcad=v.findViewById(R.id.btcad);
        btcancel=v.findViewById(R.id.btcancel);
        spstat=v.findViewById(R.id.spstat);

        clcad=v.findViewById(R.id.clcad);
        mprogressBar=v.findViewById(R.id.mprogressBar);

        clienteOld = HomeViewModel.getClientes();

        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtnome.setText(edtnome.getText().toString().toUpperCase());
                    NOME=edtnome.getText().toString().toUpperCase();
                }else{
                    txtcontnome.setText(edtnome.getText().toString().trim().length()+"/50");
                }
            }
        });
        edtapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtapelido.setText(edtapelido.getText().toString().toUpperCase());
                    APELIDO=edtapelido.getText().toString().toUpperCase();
                }else{
                    txtcontapelido.setText(edtapelido.getText().toString().trim().length()+"/15");
                }
            }
        });
        edtfone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    FONE=edtfone.getText().toString();
                }
            }
        });
        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtemail.setText(edtemail.getText().toString().toLowerCase());
                    EMAIL=edtemail.getText().toString().toLowerCase();
                }else{
                    txtcontemail.setText(edtemail.getText().toString().trim().length()+"/60");
                }
            }
        });
        edtendereco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtendereco.setText(edtendereco.getText().toString().toUpperCase());
                    ENDERECO=edtendereco.getText().toString().toUpperCase();
                }else{
                    txtcontend.setText(edtendereco.getText().toString().trim().length()+"/80");
                }
            }
        });
        edtcompl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtcompl.setText(edtcompl.getText().toString().toUpperCase());
                    COMPL=edtcompl.getText().toString().toUpperCase();
                }else{
                    txtcontcompl.setText(edtcompl.getText().toString().trim().length()+"/30");
                }
            }
        });

        edtnumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    NUMERO=edtnumero.getText().toString();

                }
            }
        });

        edtcep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    CEP=edtcep.getText().toString();
                }
            }
        });

        edtcpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    String ccc = edtcpf.getText().toString();

                    String ccc2 = ccc.replace(".", "");
                    ccc2 = ccc2.replace("-", "");
                    if (!Valida_CPF.isCPF(ccc2)) {
                        mprogressBar.setVisibility(View.INVISIBLE);
                        util.showSnackError(clcad, "Digite um CPF válido");
                        CPF=clienteOld.getCpf();
                        edtcpf.setText(CPF);
                    } else {
                        boolean tem = false;
                        Clientes cvai = new Clientes();
                        for (int i = 0; i < Home.baseClientes.size(); i++) {
                            if (Home.baseClientes.get(i).getCpf().equals(ccc)) {
                                tem = true;
                                break;
                            }
                        }

                        if (tem) {
                            util.showSnackError(clcad, "CPF já cadastrado");
                            CPF=clienteOld.getCpf();
                            edtcpf.setText(CPF);
                        } else {
                            CPF = edtcpf.getText().toString();
                        }
                    }
                }


            }
        });

        edtnome.addTextChangedListener(textWatcher);
        edtapelido.addTextChangedListener(textWatcher);
        edtemail.addTextChangedListener(textWatcher);
        edtendereco.addTextChangedListener(textWatcher);
        edtcompl.addTextChangedListener(textWatcher);
        edtcpf.addTextChangedListener(textWatcher);

        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                limpaFoco();
            }
        });
        btcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
                
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_frag_Editar_Cliente_to_frag_Consulta_Cli);
            }
        });

        txtreccli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.vibratePhone(getContext(), (short) 20);
                hideSoftKeyboard();
                limpaFoco();
                carregaCliente();

            }
        });
        imgrecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtreccli.callOnClick();
            }
        });


        final ArrayList<String> sstat = new ArrayList<>();
        sstat.clear();
        sstat.add("Ativo");
        sstat.add("Inativo");

        final ArrayAdapter<String> adaptstat= new ArrayAdapter<String>(getContext(),R.layout.sp_prod,sstat);
        adaptstat.setDropDownViewResource(R.layout.sp_prod );
        spstat.setAdapter(adaptstat);

        spstat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideSoftKeyboard();
                limpaFoco();

                if(STATUS==3){
                    util.showSnackError(clcad,"Cliente inadimplente não pode ser Inativado.");
                  //  llstat.setBackgroundColor(Color.RED);
                    return;
                }else{
                        STATUS = position + 1;
                        switch (STATUS) {
                            case 1:
                                llstat.setBackgroundColor(Color.GREEN);
                                llfin.setBackgroundColor(Color.GREEN);
                                txtfin.setText("Normal");
                                break;
                            case 2:
                                llfin.setBackgroundColor(Color.GRAY);
                                txtfin.setText("Inativo");
                                llstat.setBackgroundColor(Color.GRAY);
                                break;
                            default:
                                llstat.setBackgroundColor(Color.GRAY);
                        }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for(int i=0;i<sstat.size();i++){
            Log.i("clienteOld.getStatus()",i+" sstat: "+sstat.get(i) );
        }


        if(clienteOld !=null){

            carregaCliente();

        }






        edtcep.addTextChangedListener(Mask.insert(Mask.CEP_MASK, edtcep));
        edtfone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, edtfone));
        edtcpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, edtcpf));


    }

    private void carregaCliente() {


        KEYCLI= clienteOld.getKeycli();
        CPF= clienteOld.getCpf();
        NOME= clienteOld.getNome();
        APELIDO= clienteOld.getApelido();
        FONE= clienteOld.getTelefone();
        EMAIL= clienteOld.getEmailcli();
        ENDERECO= clienteOld.getEndereco();
        NUMERO= clienteOld.getNumero();
        CEP= clienteOld.getCep();
        COMPL= clienteOld.getComplemento();
        STATUS= clienteOld.getStatus();
        TIMESTAMP= clienteOld.getTimestamp();

        edtcpf.setText(CPF);
        String sll = CPF;
        sll = sll.substring(0,3);
        if(!sll.equals("000")){
            edtcpf.setClickable(false);
            edtcpf.setEnabled(false);

        }


        Instant it = Instant.ofEpochMilli(TIMESTAMP);
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        String scomp = dataenvio.format(fmt);
        txtdata.setText(scomp);
        edtnome.setText(clienteOld.getNome());
        txtcontnome.setText(edtnome.getText().toString().trim().length()+"/50");
        edtapelido.setText(clienteOld.getApelido());
        txtcontapelido.setText(edtapelido.getText().toString().trim().length()+"/15");
        edtfone.setText(FONE);
        edtemail.setText(clienteOld.getEmailcli());
        txtcontemail.setText(edtemail.getText().toString().trim().length()+"/60");
        edtendereco.setText(clienteOld.getEndereco());
        txtcontend.setText(edtendereco.getText().toString().trim().length()+"/80");
        edtcompl.setText(clienteOld.getComplemento());
        txtcontcompl.setText(edtcompl.getText().toString().trim().length()+"/30");
        edtnumero.setText(clienteOld.getNumero());
        edtcep.setText(clienteOld.getCep());

             /*    Status:
            1 = Ativo;
            2 = Inativo;
            3 = Inadimplente;
            5 = Bloqueado;
        */
        switch (clienteOld.getStatus()){
            case 1:spstat.setSelection(0);
                llfin.setBackgroundColor(Color.GREEN);
                txtfin.setText("Normal");
                spstat.setClickable(true);
                spstat.setEnabled(true);
                break;
            case 2:spstat.setSelection(1);
                llfin.setBackgroundColor(Color.GRAY);
                txtfin.setText("Inativo");
                spstat.setClickable(true);
                spstat.setEnabled(true);
                break;
            case 3:spstat.setSelection(0);
                llfin.setBackgroundColor(Color.RED);
                llstat.setBackgroundColor(Color.GREEN);
                txtfin.setText("Inadimplente");
                spstat.setClickable(false);
                spstat.setEnabled(false);
                break;
        }
    }

    private void salvar() {

        limpaFoco();

        if(FONE.equals("") || FONE==null)FONE="(00)0000 0000)";
        if(EMAIL.equals("") || EMAIL==null)EMAIL="Não Informado";
        if(COMPL.equals("") || COMPL==null)COMPL="-";
        if(CEP.equals("") || CEP==null)CEP="00000-000";

        if(NOME.equals("")){
            util.showSnackError(clcad,"Digite um Nome para o Cliente.");
            return;
        }else if(APELIDO.equals("")){
            util.showSnackError(clcad,"Digite um Apelido para o Cliente.");
            return;
        }else if(ENDERECO.equals("")){
            util.showSnackError(clcad,"Digite um Endereço para Instalção.");
            return;
        }else if(NUMERO.equals("")){
            util.showSnackError(clcad,"Digite um Número para o endereço de Instalção.");
            return;
        }else {

            final Clientes cli = new Clientes();
            cli.setId(clienteOld.getId());
            cli.setKeycli(KEYCLI);
            cli.setNome(NOME);
            cli.setApelido(APELIDO);
            cli.setCpf(CPF);
            cli.setEndereco(ENDERECO);
            cli.setCep(CEP);
            cli.setNumero(NUMERO);
            cli.setComplemento(COMPL);
            cli.setEmailcli(EMAIL);
            cli.setTimestamp(TIMESTAMP);
            cli.setStatus(STATUS);
            cli.setTelefone(FONE);


            FirebaseFirestore.getInstance().collection("clientes")
                    .document(KEYCLI)
                    .set(cli)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mprogressBar.setVisibility(View.INVISIBLE);
                            Log.i("verfire", e.toString());
                        }
                    });
            util.showSnackOk(clcad, "Alterações salvas com sucesso.");
            HomeViewModel.setClientes(cli);
            MainActivity.navController.navigate(R.id.action_frag_Editar_Cliente_to_frag_Consulta_Cli);
        }
    }

    public void limpaFoco(){
        edtnome.clearFocus();
        edtapelido.clearFocus();
        edtemail.clearFocus();
        edtendereco.clearFocus();
        edtcompl.clearFocus();
        edtcpf.clearFocus();
        edtcep.clearFocus();
        edtfone.clearFocus();
        edtnumero.clearFocus();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence c, int start, int before, int count) {

            if(edtnome.hasFocus()){
                txtcontnome.setText(c.length() + "/50");
            } else if (edtapelido.hasFocus()) {
                txtcontapelido.setText(c.length() + "/15");
            } else if (edtemail.hasFocus()) {
                txtcontemail.setText(c.length() + "/60");
            } else if (edtendereco.hasFocus()) {
                txtcontend.setText(c.length() + "/80");
            } else if (edtcompl.hasFocus()) {
                txtcontcompl.setText(c.length() + "/30");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

    private void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}