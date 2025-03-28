package com.carlosribeiro.meatmetric.data;

import com.carlosribeiro.meatmetric.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    // Lista que simula o banco de dados de usuários
    private static final List<Usuario> usuarios = new ArrayList<>();

    static {
        // Usuário de teste padrão
        usuarios.add(new Usuario("admin@meatmetric.com", "123456"));
    }

    // Método para buscar usuário pelo email e senha (login)
    public static Usuario buscarUsuario(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    // Método para adicionar novo usuário (cadastro)
    public static void adicionarUsuario(Usuario novoUsuario) {
        usuarios.add(novoUsuario);
    }

    // (Opcional) verificar se email já existe
    public static boolean emailJaCadastrado(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
