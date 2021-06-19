package com.ponto.ideal.solucoes.equipe_vision.ui.logar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.ponto.ideal.solucoes.equipe_vision.view.Login;
import com.ponto.ideal.solucoes.equipe_vision.view.MainActivity;


public class Frag_Usu extends Fragment {
    boolean olho1 = true;
    boolean olho2 = true;
    private EditText edtnome, edtemail, edtsenha, edtrepsenha;
    private ImageView eyesenhablack, eyerepsenhablack;
    private Button btcad, btcancel;
    private TextView txtcontnome, txtcontemail;
    private  static ConstraintLayout clcad;
    private  static ProgressBar mprogressBar;
    private boolean travabotao;
    public static String CAD_EMAIL, CAD_NOME, CAD_SENHA, CAD_KEYUSU;

    private FirebaseAuth autenticacao;
    int origem = 0;
    int contateste = 0;

    public Frag_Usu(int origem) {
        this.origem=origem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag__usu, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {

        if (origem == 3) {
//            Login.ctrl = 3;
        }else{
//            Login.ctrl = 2;
        }

        mprogressBar = view.findViewById(R.id.mprogressBar);
        edtnome = view.findViewById(R.id.edtnome);
        edtemail = view.findViewById(R.id.edtemail);
        edtsenha = view.findViewById(R.id.edtsenha);
        edtrepsenha = view.findViewById(R.id.edtrepsenha);

        eyesenhablack = view.findViewById(R.id.eyesenhablack);
        eyerepsenhablack = view.findViewById(R.id.eyerepsenhablack);

        eyesenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (olho1) {
                    edtsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    edtsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                }

                olho1 = !olho1;

            }
        });

        eyerepsenhablack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (olho2) {
                    edtrepsenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyerepsenhablack.setImageResource(R.drawable.ic_eye_red);
                } else {
                    edtrepsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyerepsenhablack.setImageResource(R.drawable.ic_noeye_black);
                }

                olho2 = !olho2;

            }
        });



        btcad = view.findViewById(R.id.btcad);
        btcad.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                if (travabotao){ return; }
                travabotao=true;
                travabotao();

                mprogressBar.setVisibility(View.VISIBLE);
                edtnome.setText(edtnome.getText().toString().toUpperCase());
                cadastrarUsuario();

            }
        });

        txtcontnome = view.findViewById(R.id.txtcontnome);
        txtcontemail = view.findViewById(R.id.txtcontemail);

        clcad = view.findViewById(R.id.clcad);
        clcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });



        edtnome.addTextChangedListener(textWatcher);
        edtnome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtnome.setSelection(0);

                } else {

                }
            }
        });

        edtemail.addTextChangedListener(textWatcher);
        edtemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                edtemail.setText(edtemail.getText().toString().toLowerCase());
                if (!hasFocus) {
                    edtemail.setSelection(0);

                } else {

                }
            }
        });

        edtsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyesenhablack.setImageResource(R.drawable.ic_noeye_black);
                if (!hasFocus) {

                } else {

                }

                letranum();
            }
        });

        edtrepsenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtrepsenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyerepsenhablack.setImageResource(R.drawable.ic_noeye_black);
                if (!hasFocus) {

                } else {

                }
            }
        });


        btcancel = view.findViewById(R.id.btcancel);

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (travabotao){ return; }
                travabotao=true;travabotao();
                switch (origem){

                    case 3:
                        Intent intent2 = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                        break;
                    case 2:
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Frag_Logar()).commit();
                        break;
                    default:
                        //                        AlertaAsk();

                }

            }
        });

        clcad.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.entra_right_alerta));

    }

    public void cadastrarUsuario() {

        Boolean ok = false;
        hideSoftKeyboard();

        if (edtnome.getText().toString().trim().isEmpty() || edtnome.getText().toString().length() < 3) {
            util.showSnackError(clcad, "Preencha campo Nome. (min. 3)");
        } else if (!edtemail.getText().toString().contains("@") || !edtemail.getText().toString().contains(".")) {
            util.showSnackError(clcad, "Insira um e_mail válido.");
        } else if (!letranum()) {
            util.showSnackError(clcad, "Digite uma senha mais forte, contendo no mínimo 8 caracteres e que contenha letras e números!");
            edtsenha.requestFocus();
        } else if (!edtsenha.getText().toString().trim().equals(edtrepsenha.getText().toString().trim())
                || edtsenha.getText().toString().trim().isEmpty()) {
            util.showSnackError(clcad, "Os campos de Senha não são iguais.");
            edtsenha.requestFocus();
        } else {
            ok = true;
        }

        if (ok == true) {
            CAD_EMAIL = edtemail.getText().toString().toLowerCase().trim();
            CAD_NOME = edtnome.getText().toString().trim();
            CAD_SENHA = edtsenha.getText().toString().trim();
                criaCredenciais();
        }else{
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void criaCredenciais(){

        String senha = CAD_SENHA;
        final String email = CAD_EMAIL;


        autenticacao = FirebaseAuth.getInstance();
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                email,
                senha
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    CAD_KEYUSU = autenticacao.getUid();

                    contateste=0;
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llcad, new Frag_Perfil(1,CAD_NOME)).commit();


                } else {

                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Log.e("login 1",": " + e.getMessage());
                        erroExcecao = "Esse e-mail já está cadastrado!";
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Log.e("login 2",": " + e.getMessage());
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e que contenha letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Log.e("login 3",": " + e.getMessage());
                        erroExcecao = "O e-mail digitado é invalido, digite um novo e-mail";
                    } catch (Exception e) {
                        Log.e("login 4",": " + e.getMessage());
                        erroExcecao = "Erro ao efetuar o cadastro!\nVerfique sua conexão.";
                        e.printStackTrace();
                    }
                    mprogressBar.setVisibility(View.INVISIBLE);
                    util.showSnackOk(clcad,erroExcecao);

                }

            }
        });

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence c, int start, int before, int count) {
            if (edtnome.hasFocus()) {
                txtcontnome.setText(c.length() + "/30");
            } else if (edtemail.hasFocus()) {
                txtcontemail.setText(c.length() + "/60");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    };

    private void hideSoftKeyboard() {
        edtnome.setText(edtnome.getText().toString().toUpperCase());
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public boolean letranum() {
        boolean letranum = false;
        boolean letra = false;
        boolean num = false;

        String letras = edtsenha.getText().toString();

        for (int i = 0; i < letras.length(); i++) {
            if (Character.isDigit(letras.charAt(i)) == true) {
                num = true;
                break;
            }
        }
        for (int i = 0; i < letras.length(); i++) {
            if (!Character.isDigit(letras.charAt(i)) == true) {
                letra = true;
                break;
            }
        }
        if (letra && num && letras.length() > 7) {
            letranum = true;
        }
        return letranum;
    }

    public void travabotao(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                travabotao = false;
                Log.i("logando",  "liberado");
            }
        }, 1500);
    }

}