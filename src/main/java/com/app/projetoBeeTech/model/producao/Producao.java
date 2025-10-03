package com.app.projetoBeeTech.model.producao;

import java.util.Date;

public class Producao {
    private int id;
    private enum tipo { MEL, CERA, PROPOLIS};
    private double quantidade;
    private Date data;

    public Producao(int id, double quantidade, Date data) {
        this.id = id;
        this.quantidade = quantidade;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Producao{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", data=" + data +
                '}';
    }
}
