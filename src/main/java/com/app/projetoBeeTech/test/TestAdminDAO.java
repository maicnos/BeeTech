package com.app.projetoBeeTech.test;

import com.app.projetoBeeTech.dao.implemetacoes.AdministradorImpl;
import com.app.projetoBeeTech.model.usuario.Administrador;

/*
    Classe (programinha em texto) para testar o acesso do AdminDAO ao banco

*/

public class TestAdminDAO {
    public static void main(String[] args) {
        AdministradorImpl dao = new AdministradorImpl();
        Administrador admin = dao.findByCpf("12345678900");

        if (admin != null) {
            System.out.println("✅ Administrador encontrado:");
            System.out.println(admin);
        } else {
            System.out.println("❌ Nenhum administrador encontrado para o CPF informado.");
        }
    }
}

