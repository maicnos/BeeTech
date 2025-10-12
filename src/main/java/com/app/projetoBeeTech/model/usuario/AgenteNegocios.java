package com.app.projetoBeeTech.model.usuario;

public class AgenteNegocios extends Usuario {

    public AgenteNegocios() {
    }

    public AgenteNegocios(int id, String nome, String cpf, String telefone, String senhaHash) {
        super(id, nome, cpf, telefone, senhaHash);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}
