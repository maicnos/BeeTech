package com.app.projetoBeeTech.model.producao;

import java.util.ArrayList;
import java.util.List;

public class Caixa {
    private int id;
    private String identificacao;

    private Apiario apiario;

    private List<Producao> listaProducao;

    public Caixa(int id, String identificacao) {
        this.id = id;
        this.identificacao = identificacao;
        this.listaProducao = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public List<Producao> getListaProducao() {
        return listaProducao;
    }

    public void setListaProducao(List<Producao> listaProducao) {
        this.listaProducao = listaProducao;
    }

    public Apiario getApiario() {
        return apiario;
    }

    public void setApiario(Apiario apiario) {
        this.apiario = apiario;
    }

    @Override
    public String toString() {
        return "Caixa{" +
                "id=" + id +
                ", identificacao='" + identificacao + '\'' +
                ", listaProducao=" + listaProducao +
                '}';
    }
}
