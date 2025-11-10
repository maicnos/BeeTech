package com.app.projetoBeeTech.model.financeiro;

import com.app.projetoBeeTech.model.producao.Apicultor;
import java.util.Date;

public class Gasto {
    private int id;
    private String tipo;
    private double valor;
    private Date data;
    private Apicultor apicultor;

    public Gasto(int id, double valor, Date data) {
        this.id = id;
        this.valor = valor;
        this.data = data;
    }

    public Gasto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Apicultor getApicultor() { return apicultor; }
    public void setApicultor(Apicultor apicultor) { this.apicultor = apicultor; }

    @Override
    public String toString() {
        return tipo + " - R$ " + valor;
    }
}
