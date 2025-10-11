package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.interfaces.InventarioDAO;
import com.app.projetoBeeTech.model.financeiro.Inventario;

import java.util.List;

public class InventarioImpl implements InventarioDAO {
    @Override
    public Inventario create(Inventario obj) {
        return null;
    }

    @Override
    public List<Inventario> read() {
        return List.of();
    }

    @Override
    public void update(Inventario obj) {

    }

    @Override
    public void delete(int id) {

    }
}
