package com.ponto.ideal.solucoes.equipe_vision.ui.cadastro;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.Mask;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;

public class Cad_Cli extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText edtnome,edtemail,edtapelido,edtfone,edtendereco,edtcompl,edtnumero, edtcep;
    private TextView edtcpf,txtcontnome,txtcontapelido,txtcontemail,txtcontend,txtcontcompl,txtdata;
    private ConstraintLayout clcad;
    private Button btcad,btcancel;

    public static String CPF,NOME,APELIDO,FONE,EMAIL,ENDERECO,NUMERO,CEP,COMPL;
    private String YEAR,MONTH,DAY;
    private LocalDate dataenvio;
    public static long longdataenvio;


    public Cad_Cli() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad__cli, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        homeViewModel= Home.homeViewModel;
        CPF="";NOME="";APELIDO="";FONE="";EMAIL="";ENDERECO="";NUMERO="";CEP="";COMPL="";

        edtcpf                 =v.findViewById(R.id.edtcpf          );
        edtnome                =v.findViewById(R.id.edtnome         );
        edtapelido             =v.findViewById(R.id.edtapelido      );
        edtemail               =v.findViewById(R.id.edtemail        );
        edtfone                =v.findViewById(R.id.edtfone         );
        edtendereco            =v.findViewById(R.id.edtendereco     );
        edtcompl               =v.findViewById(R.id.edtcompl        );
        edtnumero              =v.findViewById(R.id.edtnumero       );
        edtcep                 =v.findViewById(R.id.edtcep          );
        txtcontnome            =v.findViewById(R.id.txtcontnome     );
        txtcontapelido         =v.findViewById(R.id.txtcontapelido  );
        txtcontemail           =v.findViewById(R.id.txtcontemail    );
        txtcontend             =v.findViewById(R.id.txtcontend      );
        txtcontcompl           =v.findViewById(R.id.txtcontcompl    );
        txtdata                =v.findViewById(R.id.txtdata         );
        clcad                  =v.findViewById(R.id.clcad           );

        edtnome.addTextChangedListener(textWatcher);
        edtapelido.addTextChangedListener(textWatcher);
        edtemail.addTextChangedListener(textWatcher);
        edtendereco.addTextChangedListener(textWatcher);
        edtcompl.addTextChangedListener(textWatcher);
        edtcep.addTextChangedListener(Mask.insert(Mask.CEP_MASK, edtcep));
        edtfone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, edtfone));

        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        dataenvio = zdt.toLocalDate();

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        YEAR = String.valueOf(dataenvio.getYear());
        MONTH =String.format("%02d", new Object[] { dataenvio.getMonthValue() });
        DAY = String.format("%02d", new Object[] { dataenvio.getDayOfMonth() });
        String scomp = dataenvio.format(fmt);

        try {
            String str_date= scomp;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            longdataenvio=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }


        txtdata.setText(scomp);


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                monthOfYear = monthOfYear + 1;
                YEAR = String.valueOf(year);
                MONTH =String.format("%02d", new Object[] { monthOfYear });
                DAY = String.format("%02d", new Object[] { dayOfMonth });
                String date =  DAY+ "-" +MONTH+ "-" +YEAR;
                txtdata.setText(date);
                try {
                    String str_date= date;
                    SimpleDateFormat formatter ;
                    Date date1 ;
                    formatter = new SimpleDateFormat("dd-MM-yyyy");
                    date1 = (Date) formatter.parse(str_date);
                    Log.i("test",""+date);
                    longdataenvio=date1.getTime();
                } catch (Exception e) {
                    System.out.println("Exception :"+e);
                }
            }};

        txtdata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
                dialog.show();

                return false;
            }
        });

        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard();
                limpaFoco();
            }
        });


        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtnome.setText(edtnome.getText().toString().trim().toUpperCase());
                    NOME=edtnome.getText().toString().trim().toUpperCase();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontnome.setText(NOME.length()+"/50");
                }
            }
        });
        edtapelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtapelido.setText(edtapelido.getText().toString().trim().toUpperCase());
                    APELIDO=edtapelido.getText().toString().trim().toUpperCase();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontapelido.setText(APELIDO.length()+"/15");
                }
            }
        });
        edtfone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FONE=edtfone.getText().toString();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                }
            }
        });
        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtemail.setText(edtemail.getText().toString().trim().toLowerCase());
                    EMAIL=edtemail.getText().toString().trim().toLowerCase();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontemail.setText(EMAIL.length()+"/60");
                }
            }
        });

        edtendereco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtendereco.setText(edtendereco.getText().toString().trim().toUpperCase());
                    ENDERECO=edtendereco.getText().toString().trim().toUpperCase();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontend.setText(ENDERECO.length()+"/80");
                }
            }
        });
        edtcompl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtcompl.setText(edtcompl.getText().toString().trim().toUpperCase());
                    COMPL=edtcompl.getText().toString().trim().toUpperCase();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                    txtcontcompl.setText(COMPL.length()+"/30");
                }
            }
        });

        edtnumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    NUMERO=edtnumero.getText().toString();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                }
            }
        });

        edtcep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    CEP=edtcep.getText().toString();
                }else{
                    util.vibratePhone(getContext(), (short) 20);
                }
            }
        });

        CPF=HomeViewModel.getCpfcod();
        edtcpf.setText(CPF);

    }


    public void limpaFoco(){
        hideSoftKeyboard();
        edtnome.clearFocus();
        edtfone.clearFocus();
        edtapelido.clearFocus();
        edtemail.clearFocus();
        edtendereco.clearFocus();
        edtcompl.clearFocus();
        edtcep.clearFocus();
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