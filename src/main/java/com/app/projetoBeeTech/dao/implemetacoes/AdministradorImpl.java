package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.AdministradorDAO;
import com.app.projetoBeeTech.model.usuario.Administrador;

import java.sql.*;
import java.util.List;

public class AdministradorImpl implements AdministradorDAO {
    @Override
    public Administrador create(Administrador obj) {
        return null;
    }

    @Override
    public List<Administrador> read() {
        return List.of();
    }

    @Override
    public void update(Administrador obj) {

    }

    @Override
    public void delete(int id) {

    }



    @Override
    public Administrador findByCpf(String cpf) {
        String sql = "SELECT * FROM administrador WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Administrador admin = new Administrador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        "" // senha original não é recuperada
                );
                // Setar manualmente o hash (sem recriptografar)
                java.lang.reflect.Field senhaHashField = admin.getClass().getSuperclass().getDeclaredField("senhaHash");
                senhaHashField.setAccessible(true);
                senhaHashField.set(admin, rs.getString("senha_hash"));

                return admin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
