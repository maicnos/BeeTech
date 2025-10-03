package com.app.projetoBeeTech.model.usuario;

import org.mindrot.jbcrypt.BCrypt;

public abstract class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String senhaHash;

    public Usuario() {
    }

    public Usuario(int id, String nome, String cpf, String telefone, String endereco, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        setSenha(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public void setSenha(String senha) {
        this.senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public boolean validarSenha(String senha) {
        return BCrypt.checkpw(senha, this.senhaHash);
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
