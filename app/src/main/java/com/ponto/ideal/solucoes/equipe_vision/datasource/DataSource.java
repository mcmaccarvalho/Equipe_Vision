package com.ponto.ideal.solucoes.equipe_vision.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.ponto.ideal.solucoes.equipe_vision.datamodel.Clientes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Instalacoes_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Setores_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.datamodel.Usuarios_DataModel;
import com.ponto.ideal.solucoes.equipe_vision.model.Clientes;
import com.ponto.ideal.solucoes.equipe_vision.model.Instalacoes;
import com.ponto.ideal.solucoes.equipe_vision.model.Setores;
import com.ponto.ideal.solucoes.equipe_vision.model.Usuarios;

import java.util.ArrayList;

public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "equipe_vision.sqlite";
    private static final int DB_VERSION = 1;

    SQLiteDatabase db;
    Cursor cursor;

    public DataSource(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Usuarios_DataModel.criarTabela());
        } catch (Exception e) {
            Log.e("sqlite error: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insert(String tabela, ContentValues dados) {

        boolean sucesso = true;
        try {
            sucesso = db.insert(tabela, null, dados) > 0;

        } catch (Exception e) {
            sucesso = false;
        }
        return sucesso;
    }

    public boolean deletar(String tabela, int id) {

        boolean sucesso = true;

        sucesso = db.delete(tabela, "id=?",

                new String[]{Integer.toString(id)}) > 0;

        return sucesso;
    }

    public void deletarTabela(String tabela) {

        try {
            db.execSQL("DROP TABLE IF EXISTS " + tabela);
        } catch (Exception e) {

        }

    }

    public void criarTabela(String queryCriarTabela) {

        try {
                db.execSQL(queryCriarTabela);
        } catch (SQLiteCantOpenDatabaseException e) {

        }

    }



    public Usuarios usuariotual(String tabela, String keyusu) {

        String[] columns = {
                "id",
                "keyusu",
                "nomeusu",
                "apelidousu",
                "emailusu",
                "imagemusuario",
                "timestamp",
                "status",
                "token",
                "online"
        };


        String[] args = new String[]{keyusu};
        Cursor cursor = db.query(Usuarios_DataModel.getTABELA(), columns, "keyusu = ? ", args, null, null, null);

        if (cursor.getCount() > 0) {

            Usuarios obj;

            if (cursor.moveToFirst()) {

                obj = new Usuarios();

                obj.setId(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getId())));
                obj.setEmailusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getEmailusu())));
                obj.setImagemusuario(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getImagemusuario())));
                obj.setNomeusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getNomeusu())));
                obj.setKeyusu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getKeyusu())));
                obj.setTimestamp(cursor.getLong(cursor.getColumnIndex(Usuarios_DataModel.getTimestamp())));
                obj.setApelidousu(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getApelidousu())));
                obj.setStatus(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getStatus())));
                obj.setOnline(cursor.getInt(cursor.getColumnIndex(Usuarios_DataModel.getOnline())));
                obj.setToken(cursor.getString(cursor.getColumnIndex(Usuarios_DataModel.getToken())));

                return obj;
            }
            cursor.close();
        }

        return null;
    }

    public boolean updateapelido(String tabela, String uid, ContentValues dados) {
        db.update(tabela, dados, "keyusu=?", new String[]{uid});
        return true;
    }

    public ArrayList<Setores> getAll_Setores() {

        Setores obj;
        ArrayList<Setores> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Setores_DataModel.getTABELA() + " ORDER BY nomesetor";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Setores();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Setores_DataModel.getId           ())));
                obj.setKeysetor    (cursor.getString(cursor.getColumnIndex(Setores_DataModel.getKeysetor    ())));
                obj.setNomesetor  (cursor.getString(cursor.getColumnIndex(Setores_DataModel.getNomesetor  ())));
                obj.setCepinicial (cursor.getString(cursor.getColumnIndex(Setores_DataModel.getCepinicial ())));
                obj.setCepfinal  (cursor.getString(cursor.getColumnIndex(Setores_DataModel.getCepfinal  ())));

                lista.add(obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }


    public ArrayList<Clientes> getAll_Clientes() {

        Clientes obj;
        ArrayList<Clientes> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Clientes_DataModel.getTABELA() + " ORDER BY cpf";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Clientes();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Clientes_DataModel.getId           ())));
                obj.setKeycli    (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getKeycli    ())));
                obj.setNome  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getNome  ())));
                obj.setApelido (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getApelido ())));
                obj.setCpf  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getCpf  ())));
                obj.setEndereco  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getEndereco  ())));
                obj.setNumero  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getNumero  ())));
                obj.setComplemento  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getComplemento  ())));
                obj.setEmailcli  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getEmailcli  ())));
                obj.setTelefone  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getTelefone  ())));
                obj.setCep  (cursor.getString(cursor.getColumnIndex(Clientes_DataModel.getCep  ())));
                obj.setTimestamp  (cursor.getLong(cursor.getColumnIndex(Clientes_DataModel.getTimestamp  ())));
                obj.setStatus  (cursor.getInt(cursor.getColumnIndex(Clientes_DataModel.getStatus  ())));
                lista.add(obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public ArrayList<Instalacoes> getAll_Instalacoes() {

        Instalacoes obj;
        ArrayList<Instalacoes> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + Instalacoes_DataModel.getTABELA() + " ORDER BY numinstal";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                obj = new Instalacoes();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Instalacoes_DataModel.getId           ())));
                obj.setKeyinstalacao    (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getKeyinstalacao    ())));
                obj.setCliente  (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getCliente  ())));
                obj.setTimestamp (cursor.getLong(cursor.getColumnIndex(Instalacoes_DataModel.getTimestamp ())));
                obj.setStatus  (cursor.getInt(cursor.getColumnIndex(Instalacoes_DataModel.getStatus  ())));
                obj.setEndereco       (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getEndereco       ())));
                obj.setCep     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getCep     ())));
                obj.setNumero        (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getNumero        ())));
                obj.setComplemento     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getComplemento     ())));
                obj.setSetor     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getSetor     ())));
                obj.setNuminstal      (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getNuminstal      ())));


                lista.add(obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }



    public ArrayList<Instalacoes> getinstalCli(String idcli) {

        String[] columns = {
                "id",
                "keyinstalacao",
                "cliente",
                "timestamp",
                "status",
                "endereco",
                "cep",
                "numero",
                "complemento",
                "setor",
                "numinstal"
        };

        String[] args = new String[]{idcli};
        Cursor cursor = db.query(Instalacoes_DataModel.getTABELA() , columns, "cliente = ? ", args, null, null, null);

        Instalacoes obj;
        ArrayList<Instalacoes> lista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                obj = new Instalacoes();
                obj.setId           (cursor.getInt   (cursor.getColumnIndex(Instalacoes_DataModel.getId           ())));
                obj.setKeyinstalacao    (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getKeyinstalacao    ())));
                obj.setCliente  (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getCliente  ())));
                obj.setTimestamp (cursor.getLong(cursor.getColumnIndex(Instalacoes_DataModel.getTimestamp ())));
                obj.setStatus  (cursor.getInt(cursor.getColumnIndex(Instalacoes_DataModel.getStatus  ())));
                obj.setEndereco       (cursor.getString(cursor.getColumnIndex(Instalacoes_DataModel.getEndereco       ())));
                obj.setCep     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getCep     ())));
                obj.setNumero        (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getNumero        ())));
                obj.setComplemento     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getComplemento     ())));
                obj.setSetor     (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getSetor     ())));
                obj.setNuminstal      (cursor.getString   (cursor.getColumnIndex(Instalacoes_DataModel.getNuminstal      ())));


                lista.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }



}