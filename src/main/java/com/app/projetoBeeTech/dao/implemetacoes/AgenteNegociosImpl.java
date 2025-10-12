package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.AgenteNegociosDAO;
import com.app.projetoBeeTech.model.usuario.AgenteNegocios;

import java.sql.*;
import java.util.List;

public class AgenteNegociosImpl implements AgenteNegociosDAO {
    @Override
    public AgenteNegocios create(AgenteNegocios obj) {
        return null;
    }

    @Override
    public List<AgenteNegocios> read() {
        return List.of();
    }

    @Override
    public void update(AgenteNegocios obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public AgenteNegocios findByCpf(String cpf) {
        String sql = "SELECT * FROM agenteNegocios WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AgenteNegocios agente = new AgenteNegocios(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        ""
                );
                java.lang.reflect.Field senhaHashField = agente.getClass().getSuperclass().getDeclaredField("senhaHash");
                senhaHashField.setAccessible(true);
                senhaHashField.set(agente, rs.getString("senha_hash"));

                return agente;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
