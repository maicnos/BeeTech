package com.app.projetoBeeTech.test;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

/*
    Classe (programinha em texto) para gerar hashes a partir de senhas - para usar no banco

*/

public class GeradorHashBCrypt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== GERADOR DE HASH BCRYPT ===");
        System.out.print("Digite a senha para gerar o hash: ");
        String senha = scanner.nextLine();

        String hash = BCrypt.hashpw(senha, BCrypt.gensalt(10));

        System.out.println("\nSenha original: " + senha);
        System.out.println("Hash gerado: " + hash);


        System.out.print("\nDigite uma senha para verificar: ");
        String tentativa = scanner.nextLine();

        if (BCrypt.checkpw(tentativa, hash)) {
            System.out.println("✅ Senha correta!");
        } else {
            System.out.println("❌ Senha incorreta!");
        }

        scanner.close();
    }
}

