package com.app.projetoBeeTech.model.producao;

import com.app.projetoBeeTech.model.financeiro.Gasto;
import com.app.projetoBeeTech.model.financeiro.Inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Apicultor {
    private int id;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String endereco;
    private Inventario inventario;
    private List<Apiario> listaApiarios;
    private List<Gasto> listaGastos;

    public Apicultor(int id, String nome, String cpfCnpj, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.inventario = new Inventario();
        this.listaApiarios = new ArrayList<>();
        this.listaGastos = new ArrayList<>();
    }

    public Apicultor() {
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
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

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public List<Apiario> getListaApiarios() {
        return listaApiarios;
    }

    public void setListaApiarios(List<Apiario> listaApiarios) {
        this.listaApiarios = listaApiarios;
    }

    public List<Gasto> getListaGastos() {
        return listaGastos;
    }

    public void setListaGastos(List<Gasto> listaGastos) {
        this.listaGastos = listaGastos;
    }

    @Override
    public String toString() {
        return "Apicultor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", inventario=" + inventario +
                ", listaApiarios=" + listaApiarios +
                ", listaGastos=" + listaGastos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Apicultor apicultor)) return false;
        return id == apicultor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
