package com.app.projetoBeeTech.util;

import com.app.projetoBeeTech.dao.interfaces.AdministradorDAO;
import com.app.projetoBeeTech.dao.interfaces.AgenteNegociosDAO;
import com.app.projetoBeeTech.model.usuario.Administrador;
import com.app.projetoBeeTech.model.usuario.AgenteNegocios;

public class Auth {

    private final AdministradorDAO adminDao;
    private final AgenteNegociosDAO agenteDao;

    public Auth(AdministradorDAO adminDao, AgenteNegociosDAO agenteDao) {
        this.adminDao = adminDao;
        this.agenteDao = agenteDao;
    }

    public Administrador registrarAdmin(String nome, String cpf, String telefone, String senha) {
        String hash = HashSenha.gerarHash(senha);
        Administrador admin = new Administrador();
        admin.setNome(nome);
        admin.setCpf(cpf);
        admin.setTelefone(telefone);
        admin.setSenhaHash(hash);
        return adminDao.create(admin);
    }

    public Administrador autenticarAdmin(String cpf, String senha) {
        Administrador admin = adminDao.findByCpf(cpf);
        if (admin != null && HashSenha.verificarSenha(senha, admin.getSenhaHash())) {
            return admin;
        }
        return null;
    }


    public AgenteNegocios registrarAgente(String nome, String cpf, String telefone, String senha) {
        String hash = HashSenha.gerarHash(senha);
        AgenteNegocios agente = new AgenteNegocios();
        agente.setNome(nome);
        agente.setCpf(cpf);
        agente.setTelefone(telefone);
        agente.setSenhaHash(hash);
        return agenteDao.create(agente);
    }

    public AgenteNegocios autenticarAgente(String cpf, String senha) {
        AgenteNegocios agente = agenteDao.findByCpf(cpf);
        if (agente != null && HashSenha.verificarSenha(senha, agente.getSenhaHash())) {
            return agente;
        }
        return null;
    }
}
