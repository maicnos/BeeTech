package com.app.projetoBeeTech.model.financeiro;

import com.app.projetoBeeTech.model.producao.Apicultor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventario {
    private int id;
    private Apicultor apicultor;
    private List<Bem> listaBem;

    public Inventario() {
        this.listaBem = new ArrayList<>();
    }

    public Inventario(int id, Apicultor apicultor) {
        this.id = id;
        this.apicultor = apicultor;
        this.listaBem = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apicultor getApicultor() {
        return apicultor;
    }

    public void setApicultor(Apicultor apicultor) {
        this.apicultor = apicultor;
    }

    public List<Bem> getListaBem() {
        return listaBem;
    }

    public void setListaBem(List<Bem> listaBem) {
        this.listaBem = listaBem;
    }

    public void addBem(Bem bem) {
        listaBem.add(bem);
    }


    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", listaBem=" + listaBem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inventario that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
