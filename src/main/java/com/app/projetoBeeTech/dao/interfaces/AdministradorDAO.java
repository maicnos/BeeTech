package com.app.projetoBeeTech.dao.interfaces;

import com.app.projetoBeeTech.model.usuario.Administrador;

public interface AdministradorDAO extends CRUD<Administrador> {
    Administrador findByCpf(String cpf);
}
