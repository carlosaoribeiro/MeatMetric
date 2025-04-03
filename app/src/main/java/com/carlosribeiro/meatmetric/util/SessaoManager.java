package com.carlosribeiro.meatmetric.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlosribeiro.meatmetric.ui.LoginActivity;

public class SessaoManager {

    private static final String PREF_NOME = "meatmetric_sessao";
    private static final String CHAVE_EMAIL = "email_usuario";
    private static final String CHAVE_NOME = "nome_usuario";
    private static final String CHAVE_DATA_CRIACAO = "data_criacao_conta";

    // 🔹 Salva todos os dados do usuário
    public static void salvarDadosUsuario(Context context, String nome, String email, String dataCriacao) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CHAVE_EMAIL, email);
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_DATA_CRIACAO, dataCriacao);
        editor.apply();
    }

    // 🔹 Retorna apenas o e-mail
    public static String getUsuarioLogado(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_EMAIL, null);
    }

    public static String getNomeUsuario(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_NOME, null);
    }

    public static String getDataCriacao(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_DATA_CRIACAO, null);
    }

    public static boolean isUsuarioLogado(Context context) {
        return getUsuarioLogado(context) != null;
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public static void salvarDadosUsuario(LoginActivity loginActivity, String email) {
    }
}
