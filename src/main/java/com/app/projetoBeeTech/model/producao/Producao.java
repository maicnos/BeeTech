package com.app.projetoBeeTech.model.producao;

import java.util.Date;

public class Producao {
    private int id;
    private Tipo tipo;
    private double quantidade;
    private Date data;

    private Caixa caixa;

    public enum Tipo {
        MEL, CERA, PROPOLIS
    }

    public Producao(int id, Tipo tipo, double quantidade, Date data) {
        this.id = id;
        this.quantidade = quantidade;
        this.tipo = tipo;
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


    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
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
