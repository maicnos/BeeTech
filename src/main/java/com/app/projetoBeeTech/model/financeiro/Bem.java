package com.app.projetoBeeTech.model.financeiro;

import java.text.DateFormat;
import java.time.LocalDate;

public class Bem {
    private int id;
    private String item;
    private int quantidade;
    private String descricao;
    private double valor;
    private LocalDate data;

    private Inventario inventario;

    public Bem(int id, String item, int quantidade, String descricao, double valor, LocalDate data) {
        this.id = id;
        this.item = item;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Bem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        return "Bem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}
