package com.app.projetoBeeTech.dao.interfaces;

import com.app.projetoBeeTech.model.producao.Apiario;

import java.util.List;

public interface ApiarioDAO extends CRUD<Apiario> {

    List<Apiario> findByApicultorId(int idApicultor);
}
