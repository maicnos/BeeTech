package com.app.projetoBeeTech.util;


import org.mindrot.jbcrypt.BCrypt;


public final class HashSenha {

    private HashSenha() {}


    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }


    public static boolean verificarSenha(String senha, String senhaHash) {
        if (senhaHash.startsWith("$2b$")) {
            senhaHash = "$2a$" + senhaHash.substring(4);
        }
        return BCrypt.checkpw(senha, senhaHash);
    }
}
