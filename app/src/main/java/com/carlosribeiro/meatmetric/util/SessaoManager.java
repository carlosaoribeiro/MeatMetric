package com.carlosribeiro.meatmetric.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlosribeiro.meatmetric.ui.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SessaoManager {

    private static final String PREF_NOME = "meatmetric_sessao";
    private static final String CHAVE_EMAIL = "email_usuario";
    private static final String CHAVE_NOME = "nome_usuario";
    private static final String CHAVE_DATA_CRIACAO = "data_criacao_conta";

    public static boolean isUsuarioLogado(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.contains(CHAVE_EMAIL);
    }


    public static void salvarUsuarioLogado(Context context, String nome, String email, long dataCriacaoMillis) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_EMAIL, email);
        editor.putLong(CHAVE_DATA_CRIACAO, dataCriacaoMillis);
        editor.apply();
    }

    public static String getUsuarioLogado(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_EMAIL, null);
    }

    public static String getNomeUsuario(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_NOME, null);
    }

    public static String getDataCriacaoFormatada(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        long dataMillis = prefs.getLong(CHAVE_DATA_CRIACAO, 0);

        if (dataMillis == 0) return null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(dataMillis));
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
