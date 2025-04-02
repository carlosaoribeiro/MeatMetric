package com.carlosribeiro.meatmetric.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessaoManager {

    private static final String PREF_NOME = "meatmetric_sessao";
    private static final String CHAVE_EMAIL = "email_usuario";

    // Salva o e-mail do usuário logado
    public static void salvarUsuarioLogado(Context context, String email) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        prefs.edit().putString(CHAVE_EMAIL, email).apply();
    }

    // Recupera o e-mail do usuário logado
    public static String getUsuarioLogado(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        return prefs.getString(CHAVE_EMAIL, null);
    }

    // Verifica se há usuário logado
    public static boolean isUsuarioLogado(Context context) {
        return getUsuarioLogado(context) != null;
    }

    // Faz logout
    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NOME, Context.MODE_PRIVATE);
        prefs.edit().remove(CHAVE_EMAIL).apply();
    }
}
