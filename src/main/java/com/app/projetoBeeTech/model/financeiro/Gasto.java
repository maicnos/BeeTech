package com.app.projetoBeeTech.model.financeiro;

import java.util.Date;

public class Gasto {
    private int id;



    private enum tipo { TRANSPORTE, INSUMOS, MANUTENCAO };
    private double valor;
    private Date data;

    public Gasto(int id, double valor, Date data) {
        this.id = id;
        this.valor = valor;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return "";
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}
