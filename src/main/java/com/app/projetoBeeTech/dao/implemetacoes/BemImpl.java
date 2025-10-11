package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.interfaces.BemDAO;
import com.app.projetoBeeTech.model.financeiro.Bem;

import java.util.List;

public class BemImpl implements BemDAO {
    @Override
    public Bem create(Bem obj) {
        return null;
    }

    @Override
    public List<Bem> read() {
        return List.of();
    }

    @Override
    public void update(Bem obj) {

    }

    @Override
    public void delete(int id) {

    }
}
