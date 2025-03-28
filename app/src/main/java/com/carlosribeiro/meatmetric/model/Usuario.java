package com.carlosribeiro.meatmetric.model;

public class Usuario {
    private String email;
    private String senha;

    public Usuario() {
        // Construtor vazio (caso precise usar frameworks como Firebase, Room etc.)
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
