package com.ponto.ideal.solucoes.equipe_vision.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.controller.Usuarios_Controller;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.model.Cobrancas;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;
import com.ponto.ideal.solucoes.equipe_vision.ui.cadastro.Cad_Itens;
import com.ponto.ideal.solucoes.equipe_vision.ui.cadastro.Itens_Lista;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;
import com.ponto.ideal.solucoes.equipe_vision.util.util;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static NavController navController;
    DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce=false;
    public static Toolbar toolbar;
    Context context;
   // Handler handler;
    public static NavigationView navigationView;
   // StorageReference storageReference;
    int destinationId;

    private Usuarios usulog;
    public static FloatingActionButton fab;
    private FirebaseAuth mAuth;

    private Menu mmenu;



    private boolean bolfat =false;
    private boolean bolger =false;
    private boolean bolcad =false;
    private boolean bolconf = false;


    private boolean bolmenu =false;
    private boolean boltit =false;
    private boolean bolset = false;
    public static boolean blobk=false;

    private String n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15;
    private String Sadmim = "111111111111111";
    private String Soperador = "102201200010100";
    private String Scustom = "000000000000000";

    private HomeViewModel homeViewModel;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MAin","passou on create");
        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getCurrentUser().getUid();
        FirebaseFirestore. getInstance () . collection ( "/usuarios" )
                .document ( uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            usulog=task.getResult().toObject(Usuarios.class);
                            homeViewModel.setUsuario(usulog);
                            carregaNav(usulog);
                        }else{
                            carregaNav(null);
                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("retligas", "e " + e.getMessage() + ": " + e );
            }
        });
        HomeViewModel.setUsuario(usulog);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        context = this;



        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);




        fab.setVisibility(View.INVISIBLE);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        mmenu=navigationView.getMenu();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                destinationId = destination.getId();

                switch (destinationId) {
                    case R.id.home:
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setNavigationIcon(R.drawable.ic_wifi_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Vision Internet");
                        break;
                    case R.id.frag_Gerar_Mensalidades:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_mensal_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Gerar Mensalidades");
                        break;
                    case R.id.cad_Itens:
                      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_wifi_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        switch (Home.ctrlItem){
                            case 0:toolbar.setTitle("Links");     toolbar.setNavigationIcon(R.drawable.ic_leak_add_24);break;
                            case 1:toolbar.setTitle("Produtos");  toolbar.setNavigationIcon(R.drawable.ic_baseline_category_24);break;
                            case 2:toolbar.setTitle("Cobranças"); toolbar.setNavigationIcon(R.drawable.ic_attach_money_azul_24);break;
                            case 3:toolbar.setTitle("Adesão");    toolbar.setNavigationIcon(R.drawable.ic_adesao_add_24);break;
                            case 4:toolbar.setTitle("Setores");   toolbar.setNavigationIcon(R.drawable.ic_extension_24);break;
                            case 5:toolbar.setTitle("Despesas");   toolbar.setNavigationIcon(R.drawable.ic_payment_24);break;
                        }
                        break;
                    case R.id.frag_Fat_Link:
                       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_leak_add_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Faturamento Links");
                        break;
                    case R.id.despesas_Operacionais:
                       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_payment_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Despesas");
                        break;
                    case R.id.frag_Listar_Instal:
                       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_baseline_account_box_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Status Clientes");
                        break;
                    case R.id.frag_Cad_Cli:
                      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_azul_add_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Cadastro Clientes");
                        break;

                    case R.id.frag_Titulos:
                     //   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_list_alt_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Gerar Título em Banco");
                        break;
                    case R.id.frag_Listar_Titulos:
                       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_list_alt_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Listar Títulos");
                        break;
                    case R.id.frag_Editar_Cliente:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_edit_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Editar Cliente");
                        break;
                    case R.id.frag_Editar_Install:
                      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_edit_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Editar Instalação");
                        break;
                    case R.id.frag_Add_Install:
                      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_leak_add_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Add Instalação");
                        break;
                    case R.id.frag_Consulta_Cli:
                      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_azul_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Cliente");
                        break;
                    case R.id.frag_Settings:
                       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_baseline_settings_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Configuções");
                        break;
                    case R.id.frag_Cadastrar_Usuario:
                   //     getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_person_add_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Cadastrar Usuário");
                        break;

                    case R.id.frag_Perifl_Usuario:
                        //     getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_baseline_person_outline_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Perfil de Usuário");
                        break;
                    case R.id.frag_Baixa_Titulo:
                    //    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbar.setNavigationIcon(R.drawable.ic_baseline_check_box_24);
                        toolbar.setTitleTextColor(Color.parseColor("#3D77C7"));
                        toolbar.setTitle("Baixar Títulos");
                        break;
                }
            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int mMenu = item.getItemId();
                navigationView.setCheckedItem(mMenu);
                switch (mMenu) {
                    case R.id.gertitulos:
                        bolfat =false;
                        bolcad =false;
                        bolconf = false;
                        bolger =!bolger;
                        if(bolger){
                            montaMenu(usulog.getUsupermicao(),mMenu);
                        }else{
                            resetcheck();

                        }
                        break;
                    case R.id.gcad:
                        bolfat =false;
                        bolger =false;
                        bolconf = false;
                        bolcad =!bolcad;
                        if(bolcad){
                            montaMenu(usulog.getUsupermicao(),mMenu);
                        }else{
                            resetcheck();
                        }
                        break;
                    case R.id.gfat:
                        bolcad =false;
                        bolger =false;
                        bolconf = false;
                        Log.i("bolfat"," fat " + bolfat);
                        bolfat =!bolfat;

                        if(bolfat){
                            montaMenu(usulog.getUsupermicao(),mMenu);
                        }else{
                            resetcheck();
                        }
                        break;
                    case R.id.gcad2:
                        bolcad =false;
                        bolger =false;
                        bolfat = false;
                        bolconf =!bolconf;
                        if(bolconf){
                            montaMenu(usulog.getUsupermicao(),mMenu);
                        }else{
                            resetcheck();
                        }
                        break;

                    case R.id.germen:
                        drawer.closeDrawer(GravityCompat.START);
                        resetcheck();
                        navController.navigate(R.id.action_home_to_frag_Gerar_Mensalidades);
                        break;
                    case R.id.statcli:
                        drawer.closeDrawer(GravityCompat.START);
                        util.ChecaStatusTit();
                        resetcheck();
                        navController.navigate(R.id.action_home_to_frag_Listar_Instal);
                        break;
                    case R.id.fatlink:
                        drawer.closeDrawer(GravityCompat.START);
                        resetcheck();
                        navController.navigate(R.id.action_home_to_frag_Fat_Link);
                        break;
                    case R.id.despesas:
                        drawer.closeDrawer(GravityCompat.START);
                        resetcheck();
                        Home.ctrlItem=5;
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;
                    case R.id.cadadesao:
                        drawer.closeDrawer(GravityCompat.START);
                        resetcheck();
                        Home.ctrlItem=3;
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;
                    case R.id.setores:
                        drawer.closeDrawer(GravityCompat.START);
                        resetcheck();
                        Home.ctrlItem=4;
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;
                    case R.id.cadlink:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        Home.ctrlItem=0;
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;
                    case R.id.cadcob:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        Home.ctrlItem=2;
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;
                    case R.id.cadprod:
                        Home.ctrlItem=1;
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_cad_Itens);
                        break;

                    case R.id.titulos:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Titulos);
                        break;
                    case R.id.baixatit:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Baixa_Titulo);
                        break;

                    case R.id.listartitulos:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Listar_Titulos);
                        break;


                    case R.id.setting:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Settings);
                        break;

                    case R.id.perfil:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(MainActivity.this, Perfil.class);
                        startActivity(intent2);
                        finish();
                        break;

                    case R.id.cadusu:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Cadastrar_Usuario);
                        break;

                    case R.id.perfil_usu:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        navController.navigate(R.id.action_home_to_frag_Perifl_Usuario);
                        break;

                    case R.id.deslogar:
                        resetcheck();
                        drawer.closeDrawer(GravityCompat.START);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent intent3 = new Intent(MainActivity.this,Login.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.sair:
                        if (doubleBackToExitPressedOnce) {
                            finish();
                        }
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                        break;

                }

                return false;
            }
        });




    }

    private void resetcheck() {
        MenuItem item1 = mmenu.findItem(R.id.gfat);
        MenuItem item2 = mmenu.findItem(R.id.gcad);
        MenuItem item3 = mmenu.findItem(R.id.gcad2);
        MenuItem item4 = mmenu.findItem(R.id.gertitulos);
        bolcad =false;
        bolger =false;
        bolfat = false;
        bolconf = false;
        item1.setChecked(false);
        item2.setChecked(false);
        item3.setChecked(false);
        item4.setChecked(false);
        montaMenu(usulog.getUsupermicao(),0);
    }

    private void montaMenu(String permission, int id){

        MenuItem item1 = mmenu.findItem(R.id.gfat);
        MenuItem item2 = mmenu.findItem(R.id.germen);
        MenuItem item3 = mmenu.findItem(R.id.statcli);
        MenuItem item4 = mmenu.findItem(R.id.fatlink);
        MenuItem item5 = mmenu.findItem(R.id.despesas);
        MenuItem item6 = mmenu.findItem(R.id.gertitulos);
        MenuItem item7 = mmenu.findItem(R.id.listartitulos);
        MenuItem item8 = mmenu.findItem(R.id.titulos);
        MenuItem item9 = mmenu.findItem(R.id.baixatit);
        MenuItem item10 = mmenu.findItem(R.id.gcad);
        MenuItem item11 = mmenu.findItem(R.id.gcad2);
        MenuItem item12 = mmenu.findItem(R.id.setting);
        MenuItem item13 = mmenu.findItem(R.id.perfil);
        MenuItem item14 = mmenu.findItem(R.id.cadusu);
        MenuItem item15 = mmenu.findItem(R.id.perfil_usu);
        MenuItem item16 = mmenu.findItem(R.id.cadadesao);
        MenuItem item17 = mmenu.findItem(R.id.cadprod);
        MenuItem item18 = mmenu.findItem(R.id.cadcob);
        MenuItem item19 = mmenu.findItem(R.id.cadlink);
        MenuItem item20 = mmenu.findItem(R.id.setores);

        n1  = permission.substring(0,1);
        n2  = permission.substring(1,2);
        n3  = permission.substring(2,3);
        n4  = permission.substring(3,4);
        n5  = permission.substring(4,5);
        n6  = permission.substring(5,6);
        n7  = permission.substring(6,7);
        n8  = permission.substring(7,8);
        n9  = permission.substring(8,9);
        n10 = permission.substring(9,10);
        n11 = permission.substring(10,11);
        n12 = permission.substring(11,12);
        n13 = permission.substring(12,13);
        n14 = permission.substring(13,14);
        n15 = permission.substring(14,15);

        Log.i("sannap"," n1 " +n1 );

        item1 .setVisible(!n1.equals("0"));
        item2 .setVisible(false);
        item3 .setVisible(false);
        item4 .setVisible(false);
        item5 .setVisible(false);
        item6 .setVisible(!n6.equals("0"));
        item7 .setVisible(false);
        item8 .setVisible(false);
        item9 .setVisible(false);
        item10.setVisible(!n10.equals("0"));
        item11.setVisible(!n11.equals("0"));
        item12.setVisible(false);
        item13.setVisible(false);
        item14.setVisible(false);
        item15.setVisible(false);

        boolean itcad = (!n10.equals("0"));

        item16.setVisible(false);
        item17.setVisible(false);
        item18.setVisible(false);
        item19.setVisible(false);
        item20.setVisible(false);


        item1.setIcon(getDrawable(R.drawable.ic_close_mm_24));
        item6.setIcon(getDrawable(R.drawable.ic_close_mm_24));
        item10.setIcon(getDrawable(R.drawable.ic_close_mm_24));
        item11.setIcon(getDrawable(R.drawable.ic_close_mm_24));

        switch (id){

            case R.id.gfat:
                item1.setIcon(getDrawable(R.drawable.ic_open_mm_24));
                item1.setChecked(true);
                item2 .setVisible(!n2.equals("0"));
                item3 .setVisible(!n3.equals("0"));
                item4 .setVisible(!n4.equals("0"));
                item5 .setVisible(!n5.equals("0"));
                if(item2.isVisible())item2.setTitle(sapanable(item2.getTitle().toString()));
                if(item3.isVisible())item3.setTitle(sapanable(item3.getTitle().toString()));
                if(item4.isVisible())item4.setTitle(sapanable(item4.getTitle().toString()));
                if(item5.isVisible())item5.setTitle(sapanable(item5.getTitle().toString()));
                break;

            case R.id.gertitulos:
                item6.setIcon(getDrawable(R.drawable.ic_open_mm_24));
                item6.setChecked(true);
                item7 .setVisible(!n7.equals("0"));
                item8 .setVisible(!n8.equals("0"));
                item9 .setVisible(!n9.equals("0"));
                if(item7.isVisible())item7.setTitle(sapanable(item7.getTitle().toString()));
                if(item8.isVisible())item8.setTitle(sapanable(item8.getTitle().toString()));
                if(item9.isVisible())item9.setTitle(sapanable(item9.getTitle().toString()));
                break;

            case R.id.gcad:
                item10.setIcon(getDrawable(R.drawable.ic_open_mm_24));
                item10.setChecked(true);
                item16 .setVisible(!n10.equals("0"));
                item17 .setVisible(!n10.equals("0"));
                item18 .setVisible(!n10.equals("0"));
                item19 .setVisible(!n10.equals("0"));
                item20 .setVisible(!n10.equals("0"));
                if(item16.isVisible())item16.setTitle(sapanable(item16.getTitle().toString()));
                if(item17.isVisible())item17.setTitle(sapanable(item17.getTitle().toString()));
                if(item18.isVisible())item18.setTitle(sapanable(item18.getTitle().toString()));
                if(item19.isVisible())item19.setTitle(sapanable(item19.getTitle().toString()));
                if(item20.isVisible())item20.setTitle(sapanable(item20.getTitle().toString()));
                break;
            case R.id.gcad2:
                item11.setIcon(getDrawable(R.drawable.ic_open_mm_24));
                item11.setChecked(true);
                item12 .setVisible(!n12.equals("0"));
                item13 .setVisible(!n13.equals("0"));
                item14 .setVisible(!n14.equals("0"));
                item15 .setVisible(!n15.equals("0"));
                if(item12.isVisible())item12.setTitle(sapanable(item12.getTitle().toString()));
                if(item13.isVisible())item13.setTitle(sapanable(item13.getTitle().toString()));
                if(item14.isVisible())item14.setTitle(sapanable(item14.getTitle().toString()));
                if(item15.isVisible())item15.setTitle(sapanable(item15.getTitle().toString()));
                break;

        }


    }


    private SpannableString sapanable(String title){
        SpannableString spanString = new SpannableString(title);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.mitem)), 0, spanString.length(), 0);
        return spanString;
    }

    private void carregaNav(final Usuarios newusulog){


        FirebaseFirestore.getInstance().collection("/usuarios")
                .whereEqualTo("keyusu",newusulog.getKeyusu())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                            Log.i("sannap"," doc: " +doc.getDocument().get("usupermicao").toString() );
                            switch (doc.getType()){
                                case ADDED:
                                    montaMenu(doc.getDocument().get("usupermicao").toString(),0);
                                    usulog=doc.getDocument().toObject(Usuarios.class);
                                    recarregnav();
                                    break;
                                case MODIFIED:
                                    montaMenu(doc.getDocument().get("usupermicao").toString(),0);
                                    usulog=doc.getDocument().toObject(Usuarios.class);
                                    recarregnav();
                                    break;
                            }

                        }
                    }
                });

        final ImageView imageViewNav = navigationView.getHeaderView(0).findViewById(R.id.imageViewnav);

        TextView txtnomenav = navigationView.getHeaderView(0).findViewById(R.id.txtnomenav);
        TextView txttipousu = navigationView.getHeaderView(0).findViewById(R.id.txttipousu);

        if(usulog!=null) {
            txtnomenav.setText(usulog.getApelidousu());

            if(usulog.getUsupermicao().equals("111111111111111")){
                txttipousu.setText("Admnistrador");
            }else{
                txttipousu.setText("Operador");
            }


            FirebaseStorage storage = FirebaseStorage.getInstance();

            final StorageReference storageReference = storage.getReferenceFromUrl("gs://equipe-vision-5cb37.appspot.com/fotoPerfilUsuario/"+mAuth.getUid());
            final int heigth = 300;
            final int width = 300;

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(200, 200).centerCrop().into(imageViewNav);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }

    private void recarregnav() {

        final ImageView imageViewNav = navigationView.getHeaderView(0).findViewById(R.id.imageViewnav);

        TextView txtnomenav = navigationView.getHeaderView(0).findViewById(R.id.txtnomenav);
        TextView txttipousu = navigationView.getHeaderView(0).findViewById(R.id.txttipousu);

        if(usulog!=null) {
            txtnomenav.setText(usulog.getApelidousu());
            if(usulog.getUsupermicao().equals("111111111111111")){
                txttipousu.setText("Admnistrador");
            }else{
                txttipousu.setText("Operador");
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();

            final StorageReference storageReference = storage.getReferenceFromUrl("gs://equipe-vision-5cb37.appspot.com/fotoPerfilUsuario/" + mAuth.getUid());
            final int heigth = 300;
            final int width = 300;

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).resize(200, 200).centerCrop().into(imageViewNav);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.stat_cli:
                util.ChecaStatusTit();
                util.showmessage(getBaseContext(),"Atualizando Status de Clientes");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onBackPressed(){

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switch (destinationId) {
                case R.id.home:
                    if (doubleBackToExitPressedOnce) {
                        finish();
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    break;
                case R.id.frag_Cad_Cli:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Cad_Cli_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.despesas_Operacionais:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_despesas_Operacionais_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Listar_Instal:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Listar_Instal_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Gerar_Mensalidades:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Gerar_Mensalidades_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;
                case R.id.frag_Consulta_Cli:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Consulta_Cli_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Add_Install:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Add_Install_to_frag_Consulta_Cli);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;



                case R.id.cad_Itens:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        Itens_Lista.bedit=false;
                        Cad_Itens.vpclass.setCurrentItem(0);
                        navController.navigate(R.id.action_cad_Itens_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Editar_Cliente:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Editar_Cliente_to_frag_Consulta_Cli);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;
                case R.id.frag_Baixa_Titulo:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Baixa_Titulo_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;
                case R.id.frag_Listar_Titulos:
                if (doubleBackToExitPressedOnce) {
                    navController.navigate(R.id.action_frag_Listar_Titulos_to_home);
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

                break;


                case R.id.frag_Titulos:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Titulos_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Fat_Link:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Fat_Link_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Settings:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        Itens_Lista.bedit=false;
                        navController.navigate(R.id.action_frag_Settings_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Cadastrar_Usuario:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        Itens_Lista.bedit=false;
                        navController.navigate(R.id.action_frag_Cadastrar_Usuario_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;

                case R.id.frag_Perifl_Usuario:
                    if(blobk) {
                        Toast.makeText(MainActivity.this, "Aguarde final do processo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleBackToExitPressedOnce) {
                        Itens_Lista.bedit=false;
                        navController.navigate(R.id.action_frag_Perifl_Usuario_to_home);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;
                case R.id.frag_Editar_Install:
                    if (doubleBackToExitPressedOnce) {
                        navController.navigate(R.id.action_frag_Editar_Install_to_frag_Consulta_Cli);
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(MainActivity.this, "Pressione novamente para sair.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    break;
                default:
                    getSupportFragmentManager().popBackStack();
            }
        }

    }


}