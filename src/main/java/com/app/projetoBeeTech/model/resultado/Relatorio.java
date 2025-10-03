package com.app.projetoBeeTech.model.resultado;

import com.app.projetoBeeTech.model.financeiro.Gasto;
import com.app.projetoBeeTech.model.producao.Producao;

import java.util.Date;
import java.util.List;

public class Relatorio {
    private int id;
    private Date dataGeracao;
    private String periodo;
    private List<Producao> producoes;
    private List<Gasto> gastos;
    private double totalProducao;
    private double totalGastos;
    private double lucroEstimado;

    public Relatorio(String periodo, List<Producao> producoes, List<Gasto> gastos) {
        this.dataGeracao = new Date();
        this.periodo = periodo;
        this.producoes = producoes;
        this.gastos = gastos;
        this.totalProducao = calcularTotalProducao();
        this.totalGastos = calcularTotalGastos();
        this.lucroEstimado = totalProducao - totalGastos;
    }

    private double calcularTotalProducao() {
        return producoes.stream().mapToDouble(Producao::getQuantidade).sum();
    }

    private double calcularTotalGastos() {
        return gastos.stream().mapToDouble(Gasto::getValor).sum();
    }
}
