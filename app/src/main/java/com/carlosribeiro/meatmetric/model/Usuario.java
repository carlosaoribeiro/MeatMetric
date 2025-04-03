package com.carlosribeiro.meatmetric.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey
    @NonNull
    private String email;

    private String senha;
    private String nome;
    private long createdAt;

    public Usuario() {
        // Construtor vazio necessário para Room
    }

    @Ignore
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.createdAt = System.currentTimeMillis(); // Marca o momento do cadastro
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    // 🔹 Retorna data de criação formatada como String legível
    public String getDataCriacaoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(createdAt));
    }
}
