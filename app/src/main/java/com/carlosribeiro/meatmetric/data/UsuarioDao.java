package com.carlosribeiro.meatmetric.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.carlosribeiro.meatmetric.model.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    void inserirUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario buscarUsuario(String email, String senha);

    @Query("SELECT COUNT(*) FROM usuarios WHERE email = :email")
    int emailJaCadastrado(String email);

    @Query("UPDATE usuarios SET senha = :novaSenha WHERE email = :email")
    void atualizarSenha(String email, String novaSenha);
}
