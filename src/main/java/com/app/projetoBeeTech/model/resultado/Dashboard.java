package com.app.projetoBeeTech.model.resultado;

import com.app.projetoBeeTech.model.financeiro.Gasto;
import com.app.projetoBeeTech.model.producao.Producao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dashboard {
    private int id;
    private Date dataGeracao;

    // Exemplo de indicadores
    private Map<String, Double> producaoPorTipo;   // {"mel": 100.0, "cera": 20.0, ...}
    private Map<String, Double> gastosPorTipo;     // {"transporte": 500.0, "insumos": 300.0, ...}
    private double totalProducao;
    private double totalGastos;
    private double margemLucro;

    public Dashboard(List<Producao> producoes, List<Gasto> gastos) {
        this.dataGeracao = new Date();
        this.producaoPorTipo = agruparProducao(producoes);
        this.gastosPorTipo = agruparGastos(gastos);
        this.totalProducao = producoes.stream().mapToDouble(Producao::getQuantidade).sum();
        this.totalGastos = gastos.stream().mapToDouble(Gasto::getValor).sum();
        this.margemLucro = totalProducao - totalGastos;
    }

    private Map<String, Double> agruparProducao(List<Producao> producoes) {
        return producoes.stream().collect(
                Collectors.groupingBy(Producao::getTipo, Collectors.summingDouble(Producao::getQuantidade))
        );
    }

    private Map<String, Double> agruparGastos(List<Gasto> gastos) {
        return gastos.stream().collect(
                Collectors.groupingBy(Gasto::getTipo, Collectors.summingDouble(Gasto::getValor))
        );
    }
}

