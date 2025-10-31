package com.app.projetoBeeTech.model.producao;

import java.util.ArrayList;
import java.util.List;

public class Apiario {
    private int id;
    private String localizacao;
    private String nome;
    private List<Caixa> listaCaixas;
    private List<Producao> listaProducao;

    private Apicultor apicultor;

    public Apiario(int id, String nome, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.listaCaixas = new ArrayList<>();
        this.listaProducao = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Caixa> getListaCaixas() {
        return listaCaixas;
    }

    public void setListaCaixas(List<Caixa> listaCaixas) {
        this.listaCaixas = listaCaixas;
    }

    public List<Producao> getListaProducao() {
        return listaProducao;
    }

    public void setListaProducao(List<Producao> listaProducao) {
        this.listaProducao = listaProducao;
    }

    public Apicultor getApicultor() {
        return apicultor;
    }

    public void setApicultor(Apicultor apicultor) {
        this.apicultor = apicultor;
    }

    @Override
    public String toString() {
        return "Apiario{" +
                "id=" + id +
                ", localizacao='" + localizacao + '\'' +
                ", nome='" + nome + '\'' +
                ", listaCaixas=" + listaCaixas +
                ", listaProducao=" + listaProducao +
                '}';
    }
}
