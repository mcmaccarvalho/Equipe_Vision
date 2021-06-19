package com.ponto.ideal.solucoes.equipe_vision.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Links;
import com.ponto.ideal.solucoes.equipe_vision.model.Tabela_Produtos;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {


    public HomeViewModel(){

    }


    private MutableLiveData<Boolean> Bolaltins = new MutableLiveData<>();
    public void setBolaltins(Boolean boll) {
        Bolaltins.setValue(boll);
    }
    public LiveData<Boolean> getBolaltins() {
        return Bolaltins;
    }

    private MutableLiveData<Boolean> Bolgerabanco = new MutableLiveData<>();
    public void setBolgerabancos(Boolean boll) {
        Bolgerabanco.setValue(boll);
    }
    public LiveData<Boolean> getBolgerabanco() {
        return Bolgerabanco;
    }

    private MutableLiveData<Boolean> bolinstal = new MutableLiveData<>();
    public void setBolinstal(Boolean boll) {
        bolinstal.setValue(boll);
    }
    public LiveData<Boolean> getBolinstals() {
        return bolinstal;
    }


    private MutableLiveData<Integer> resultbol = new MutableLiveData<>();
    public void setResultbol(Integer bol) {
        resultbol.setValue(bol);
    }
    public LiveData<Integer> getResultbol() {
        return resultbol;
    }

    private MutableLiveData<Integer> retinativar = new MutableLiveData<>();
    public void setRetinativar(Integer bol) {
        retinativar.setValue(bol);
    }
    public LiveData<Integer> getrRetinativar() {
        return retinativar;
    }

    private MutableLiveData<Integer> apdint = new MutableLiveData<>();
    public void setApdint(Integer i) {
        apdint.setValue(i);
    }
    public LiveData<Integer> getApdint() {
        return apdint;
    }

    private MutableLiveData<Integer> alteracap = new MutableLiveData<>();
    public void setAlteracap(Integer i) {
        alteracap.setValue(i);
    }
    public LiveData<Integer> getAlteracap() {
        return alteracap;
    }

    private MutableLiveData<Integer> numerobol = new MutableLiveData<>();
    public void setNumerobol(Integer bol) {
        numerobol.setValue(bol);
    }
    public LiveData<Integer> getNumerobol() {
        return numerobol;
    }

    private static String diavcto;
    public static void setDiavcto(String bol) {
        HomeViewModel.diavcto = bol;
    }
    public String getDiavcto() {
        return diavcto;
    }

    private MutableLiveData<Boolean> vctobol = new MutableLiveData<>();
    public void setVctobol(Boolean bol) {
        vctobol.setValue(bol);
    }
    public LiveData<Boolean> getVctobol() {
        return vctobol;
    }

    private MutableLiveData<Boolean> listarmes = new MutableLiveData<>();
    public void setListarmes(Boolean bol) {
        listarmes.setValue(bol);
    }
    public LiveData<Boolean> getListarmes() {
        return listarmes;
    }

    private static String meslistar;
    public static void setMeslistar(String mes) {
        HomeViewModel.meslistar = mes;
    }
    public String getMeslistar() {
        return meslistar;
    }


//    private MutableLiveData<Boolean> listarano = new MutableLiveData<>();
//    public void setListarano(Boolean bol) {
//        listarano.setValue(bol);
//    }
//    public LiveData<Boolean> getListarno() {
//        return listarano;
//    }

    private MutableLiveData<Boolean> bolgera = new MutableLiveData<>();
    public void setBolgera(Boolean bol) {
        bolgera.setValue(bol);
    }
    public LiveData<Boolean> getBolgera() {
        return bolgera;
    }

//    private static String anolistar;
//    public static void setAnolistar(String ano) {
//        HomeViewModel.anolistar = ano;
//    }
//    public String getAnolistar() {
//        return anolistar;
//    }


    private static Clientes clientes  ;
    public static Clientes getClientes(){
        return clientes;
    }
    public static void setClientes(Clientes cli) {
        HomeViewModel.clientes = cli;
    }


    private static Usuarios usuario  ;
    public static Usuarios getUsuario(){
        return usuario;
    }
    public static void setUsuario(Usuarios usu) {
        HomeViewModel.usuario = usu;
    }


    private static Instalacoes install  ;
    public static Instalacoes getInstall(){
        return install;
    }
    public static void setInstall(Instalacoes inst) {
        HomeViewModel.install = inst;
    }

    private static ArrayList<Instalacoes> arrayinstall  ;
    public static ArrayList<Instalacoes> getArrayinstall(){
        return arrayinstall;
    }
    public static void setArrayinstall(ArrayList<Instalacoes> inst) {
        HomeViewModel.arrayinstall = inst;
    }

    private static Links link  ;
    public static Links getLink(){
        return link;
    }
    public static void setLink(Links ll) {
        HomeViewModel.link = ll;
    }

//    private static Tabela_Produtos tabela_produtos  ;
//    public static Tabela_Produtos getTabela_produtos(){
//        return tabela_produtos;
//    }
//    public static void setTabela_produtos(Tabela_Produtos tp) {
//        HomeViewModel.tabela_produtos = tp;
//    }



    private static String cpfcod  ;

    public static String getCpfcod(){
        return cpfcod;
    }
    public static void setCpfcod(String AS) { HomeViewModel.cpfcod = AS;
    }
}