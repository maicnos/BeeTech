package com.app.projetoBeeTech.model.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventario {
    private int id;
    private List<Bem> listaBem;

    public Inventario() {
        this.listaBem = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
