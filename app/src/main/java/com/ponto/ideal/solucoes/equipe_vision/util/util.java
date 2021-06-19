package com.ponto.ideal.solucoes.equipe_vision.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.StorageReference;
import com.ponto.ideal.solucoes.equipe_vision.R;
import com.ponto.ideal.solucoes.equipe_vision.helper.ConfiguracaoFirebase;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Dias_Vcto;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Titulos;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.Home;
import com.ponto.ideal.solucoes.equipe_vision.ui.home.HomeViewModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class util {


    public static final String URL_WEB_SERVICE = "http://192.168.15.10:8085/";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private static Snackbar snackbar;


        public static String fdma(LocalDate ld){
            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy")
                    .withResolverStyle(ResolverStyle.STRICT);
            return ld.format(fmt);
        }

    public static String fma(LocalDate ld){
        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("MM-yy")
                .withResolverStyle(ResolverStyle.STRICT);
        return ld.format(fmt);
    }

    public static LocalDate Dia_01_Mes(long longdata){
        Instant it = Instant.ofEpochMilli(longdata);
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate dia01 = zdt.toLocalDate();
        DateTimeFormatter mes = DateTimeFormatter
                .ofPattern("MM");
        DateTimeFormatter ano = DateTimeFormatter
                .ofPattern("yyyy");
        String stttemp = "01/"+dia01.format(mes)+"/"+dia01.format(ano);
        DateTimeFormatter barras = DateTimeFormatter
                .ofPattern("dd/MM/yyyy");
        LocalDate ret = LocalDate.parse(stttemp,barras);

        return ret;
    }

    public static long longdiamesano(String dma){
        long longdiamesano=0;
        try {
            String str_date= dma;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            longdiamesano=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }
        return longdiamesano;
    }

    public static long longmesbase(LocalDate ld){
        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);
        long longmesbase=0;
        String st=ld.format(fmt);
        try {
            String str_date= st;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            longmesbase=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }
        return longmesbase;
    }

    public static String formataTextoValor(double ndd){


        String vld = String.valueOf(ndd);
        Double dd = Double.parseDouble(vld);
        vld = util.formatarValorDecimal(dd);

        String nvld="";

        switch (vld.length()){

            case 4:
                nvld =  vld.substring(vld.length()-4,vld.length()-3)+
                        "," +
                        vld.substring(vld.length()-2,vld.length()-1)+
                        vld.substring(vld.length()-1,vld.length());
                break;
            case 5:
                nvld =
                        vld.substring(vld.length()-5,vld.length()-4)+
                                vld.substring(vld.length()-4,vld.length()-3)+
                                "," +
                                vld.substring(vld.length()-2,vld.length()-1)+
                                vld.substring(vld.length()-1,vld.length());
                break;
            case 6:
                nvld =
                        vld.substring(vld.length()-6,vld.length()-5)+
                                vld.substring(vld.length()-5,vld.length()-4)+
                                vld.substring(vld.length()-4,vld.length()-3)+
                                "," +
                                vld.substring(vld.length()-2,vld.length()-1)+
                                vld.substring(vld.length()-1,vld.length());
                break;
            case 8:
                nvld =
                        vld.substring(vld.length()-8,vld.length()-7)+
                                "." +
                                vld.substring(vld.length()-6,vld.length()-5)+
                                vld.substring(vld.length()-5,vld.length()-4)+
                                vld.substring(vld.length()-4,vld.length()-3)+
                                "," +
                                vld.substring(vld.length()-2,vld.length()-1)+
                                vld.substring(vld.length()-1,vld.length());
                break;
            case 9:
                nvld =
                        vld.substring(vld.length()-9,vld.length()-8)+
                                vld.substring(vld.length()-8,vld.length()-7)+
                                "." +
                                vld.substring(vld.length()-6,vld.length()-5)+
                                vld.substring(vld.length()-5,vld.length()-4)+
                                vld.substring(vld.length()-4,vld.length()-3)+
                                "," +
                                vld.substring(vld.length()-2,vld.length()-1)+
                                vld.substring(vld.length()-1,vld.length());
                break;

        }

        return nvld;
    }

    public static String formatarValorDecimal(Double valor) {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return df.format(valor);
    }

    public static String Big_to_S(BigDecimal valor){
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        Log.i("locale valor",": " + valor);
        df.format(valor);
        String nval = df.format(valor);
        Log.i("locale valor","format: " + valor);
        Log.i("locale nval","DEPformat: " + nval);
//        if(!Locale.getDefault().toString().equals("pt_BR")) {
//            Log.i("locale nval", "Big_to_S pt_BR: " + nval);
//            nval = nval.replace(".", " ");
//            Log.i("locale nval", "A: " + nval);
//            nval = nval.replace(",", ".");
//            Log.i("locale nval", "B: " + nval);
//            nval = nval.replace(" ", ",");
//            Log.i("locale nval", "C: " + nval);
//        }

//            Log.i("locale nval","Big_to_S outro: " + nval);
//            nval = nval.replace(",", " ");
//            Log.i("locale nval","D: " + nval);
//            nval = nval.replace(".", ",");
//            Log.i("locale nval","E: " + nval);
//            nval = nval.replace(" ", ".");
//            Log.i("locale nval","F: " + nval);

        Log.i("locale",":Big_to_S return :" + nval);
        return nval;
    }

    public static String S_to_Big(String  ss){

        String nval = ss;
        nval = nval.replace(".", "");
        nval = nval.replace(",", ".");

        return nval;
    }

    public static Double formatarValorDouble(Double valor){
        double dd = valor%.0f;
        return  dd;

    }

    public static void showmessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showSnackOk(View view, String message){


        Snackbar snackbar= Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View vsnac = snackbar.getView();
        vsnac.setBackgroundColor(Color.parseColor("#3F51B5"));
        TextView textView;
        textView = (TextView) vsnac.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(18);
        snackbar.show();


    }

    public static void salvarperfs(Context context , String tipoPrefs, String tipoId){

        Log.i("testeprefs", ": " + tipoPrefs + " : " + tipoId);

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("Vision", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(tipoPrefs,tipoId);
        editor.apply();

    }

    public static String lerperfs(Context context , String tipoPrefs){

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("Vision",Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(tipoPrefs, null);
        Log.i("testeprefs", ": " + tipoPrefs + " : " + result);

        return result;

    }

    public static String loadurl(String uuid){
        StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorageReference();
        String url = storageReference+ "fotoPerfilUsuario/"
                + uuid;

        return url;
    }

    public static void showSnackError(View view, String message){

        Snackbar snackbar= Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View vsnac = snackbar.getView();
        vsnac.setBackgroundColor(Color.parseColor("#FD0303"));
        TextView textView;
        textView = (TextView) vsnac.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(18);
        snackbar.show();

    }

    public static void showSnackAsk(View view, String message){

        final Snackbar snackbar= Snackbar.make(view, message,
                Snackbar.LENGTH_INDEFINITE);
        View vsnac = snackbar.getView();
        vsnac.setBackgroundColor(Color.parseColor("#000000"));
        TextView textView;
        textView = (TextView) vsnac.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(18);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

    }

    public static ArrayList<Dias_Vcto> dias_vctos(){

        ArrayList<Dias_Vcto> dvcto= new ArrayList<>();

        Dias_Vcto dd = new Dias_Vcto();
        dd.setDiaA("01");
        dd.setDiaB("02");
        dd.setDiaC("03");
        dd.setDiaD("04");
        dd.setDiaE("05");
        dd.setDiaF("06");
        dvcto.add(dd);
        dd = new Dias_Vcto();
        dd.setDiaA("07");
        dd.setDiaB("08");
        dd.setDiaC("09");
        dd.setDiaD("10");
        dd.setDiaE("11");
        dd.setDiaF("12");
        dvcto.add(dd);
        dd = new Dias_Vcto();
        dd.setDiaA("13");
        dd.setDiaB("14");
        dd.setDiaC("15");
        dd.setDiaD("16");
        dd.setDiaE("17");
        dd.setDiaF("18");
        dvcto.add(dd);
        dd = new Dias_Vcto();
        dd.setDiaA("19");
        dd.setDiaB("20");
        dd.setDiaC("21");
        dd.setDiaD("22");
        dd.setDiaE("23");
        dd.setDiaF("24");
        dvcto.add(dd);
        dd = new Dias_Vcto();
        dd.setDiaA("25");
        dd.setDiaB("26");
        dd.setDiaC("27");
        dd.setDiaD("28");
        dd.setDiaE("29");
        dd.setDiaF("30");
        dvcto.add(dd);

        return dvcto;
    }

    public static void showSnackCampo(View view, String message){

        Snackbar snackbar= Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View vsnac = snackbar.getView();
        vsnac.setBackgroundColor(Color.parseColor("#86C964"));
        TextView textView;
        textView = (TextView) vsnac.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(18);
        snackbar.show();

    }

    public static File LoadImageFile(String nomearquivo){
        File file = null;
//        "Gruber/"+
        file = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),nomearquivo);
        file.getAbsoluteFile();
        BufferedReader input = null;
        try {
            input = new BufferedReader (new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer StringBuffer = new StringBuffer ();
            while ((line = input.readLine ())!= null) {
                StringBuffer.append (line);
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }
    }

    public static boolean SaveImageFile(String nomearquivo, Bitmap bitmap){

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] data = byteArray.toByteArray();

        FileOutputStream salvar = null;
        File arquivo = new File(Environment.DIRECTORY_PICTURES,nomearquivo);
        if(arquivo.exists()){
            arquivo.delete();
        }
        try {
            salvar = new FileOutputStream(arquivo);
            salvar.write(data);
            salvar.close();
            Log.e("salvaimg","salvo com sucesso");
            return  true;
        }catch (FileNotFoundException e){
            Log.e("salvaimg","Arquivo não encontrdo " + e.getMessage());
            return  false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("salvaimg","Erro ao salvar arquivo " + e.getMessage());
            return  false;
        }


    }

    public  static Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();

        } catch (Exception e) {
            Log.e("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String [] listafile(Context context) {


        try {
            String[] llfile = context.fileList();


            return llfile;
        } catch (Exception e) {
            Log.e("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();

        }

        return null;

    }

    public static boolean saveImage(Context context, Bitmap b, String imageName) {

        FileOutputStream foStream;
        try
        {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();

            return true;
        }
        catch (Exception e)
        {
            Log.e("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
            return false;
        }
    }

    public static String saveurl(String uuid){

        String url =
                "/fotoPerfilUsuario/"
                        + uuid;

        return url;
    }

    public static boolean Bisessexto(int year){
        if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
            return true;
        }else{
            return false;
        }
    }

    public static void ChecaStatusTit(){

        HomeViewModel homeViewModel = Home.homeViewModel;

        ArrayList<Clientes> bcli = new ArrayList<>();
        ArrayList<Instalacoes> binstal =  new ArrayList<>();
        ArrayList<Titulos> btit =  new ArrayList<>();

        //pega somente ativos e inadimplentes
        for(int i=0;i<Home.baseClientes.size();i++){
            if(Home.baseClientes.get(i).getStatus()==1 || Home.baseClientes.get(i).getStatus()==3){
                bcli.add(Home.baseClientes.get(i));
            }

        }
        for(int i=0;i<Home.baseInstall.size();i++){
            if(Home.baseInstall.get(i).getStatus()==1 || Home.baseInstall.get(i).getStatus()==3){
                binstal.add(Home.baseInstall.get(i));
            }

        }
        for(int i=0;i<Home.baseTitulos.size();i++){
            if(Home.baseTitulos.get(i).getStatustit()==1){
                btit.add(Home.baseTitulos.get(i));
            }
        }

        // positiva todos
        for(int i=0;i<bcli.size();i++){
            bcli.get(i).setStatus(1);
        }
        for(int i=0;i<binstal.size();i++){
            binstal.get(i).setStatus(1);
        }
        for(int i=0;i<btit.size();i++){
            btit.get(i).setSituacaotit(0);
        }

        int podeatrasar =Integer.parseInt( Home.Settings.getMax_dias_atraso());

        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .withResolverStyle(ResolverStyle.STRICT);

        Instant it = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate hoje = zdt.toLocalDate();


        //verifica e certa status dos titulos
        for (int i=0;i<btit.size();i++){
            it=Instant.ofEpochMilli(btit.get(i).getVencimentotit());
            zdt = it.atZone(ZoneId.systemDefault());
            LocalDate vcto = zdt.toLocalDate();

            long diasAtrasado = ChronoUnit.DAYS.between(vcto,hoje);

            int dd=Integer.valueOf(podeatrasar);

            if(diasAtrasado>=podeatrasar) {
                btit.get(i).setSituacaotit(1);
            }
        }

        //verifica e certa status install e clientes
        for(int i=0;i<btit.size();i++){
            Log.i("stattit","btit dep: " + btit.get(i).getNumerotit() + " titcli:" + btit.get(i).getNomeclientetit() + " sit: " + btit.get(i).getSituacaotit());
            for(int j=0;j<bcli.size();j++){
                if(btit.get(i).getKeyclientetit().equals(bcli.get(j).getKeycli())){
                    if(btit.get(i).getSituacaotit()==1){
                        bcli.get(j).setStatus(3);
                    }
                }
            }

            for(int j=0;j<binstal.size();j++){
                if(btit.get(i).getKeyclientetit().equals(binstal.get(j).getCliente())){
                    if(btit.get(i).getSituacaotit()==1){
                        binstal.get(j).setStatus(3);
                    }
                }
            }
        }

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();

        for(int i=0;i<btit.size();i++){
            DocumentReference dr =db.collection("titulos").document(btit.get(i).getKeytit());
            batch.update(dr,"situacaotit",btit.get(i).getSituacaotit());
        }

        for(int i=0;i<bcli.size();i++) {
            DocumentReference dr = db.collection("clientes").document(bcli.get(i).getKeycli());
            batch.update(dr, "status", bcli.get(i).getStatus());
        }
        for(int i=0;i<binstal.size();i++) {
                DocumentReference dr = db.collection("instalacoes").document(binstal.get(i).getNuminstal());
                batch.update(dr, "status", binstal.get(i).getStatus());
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }

        });
    }


    public static final void vibratePhone(Context context, short vibrateMilliSeconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrateMilliSeconds);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable)
        { return ((BitmapDrawable) drawable).getBitmap(); }

        final int width = !drawable.getBounds().isEmpty() ? drawable .getBounds().width() : drawable.getIntrinsicWidth();
        final int height = !drawable.getBounds().isEmpty() ? drawable .getBounds().height() : drawable.getIntrinsicHeight();
        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width, height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);
        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight()); drawable.draw(canvas);
        return bitmap;
    }


    public static long long_X_st_dma(String st){

        long longmesbase=0;
        try {
            String str_date= st;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            longmesbase=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }
        return longmesbase;
    }

    public static LocalDate ld_X_long(long ll){

        Instant it = Instant.ofEpochMilli(ll);
        ZonedDateTime zdt = it.atZone(ZoneId.systemDefault());
        LocalDate ld = zdt.toLocalDate();

        return ld;
    }

    public static long long_X_ld(LocalDate ld){
        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("dd-MM-yyyy")
                .withResolverStyle(ResolverStyle.STRICT);
        long longmesbase=0;
        String st=ld.format(fmt);
        try {
            String str_date= st;
            SimpleDateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            Log.i("test",""+date);
            longmesbase=date.getTime();
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }
        return longmesbase;
    }




}
