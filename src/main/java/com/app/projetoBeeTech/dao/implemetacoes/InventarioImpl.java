package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.InventarioDAO;
import com.app.projetoBeeTech.model.financeiro.Inventario;
import com.app.projetoBeeTech.model.producao.Apicultor;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class InventarioImpl implements InventarioDAO {
    @Override
    public Inventario create(Inventario obj) {
        String sql = "INSERT INTO inventario (id_apicultor) VALUES (?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (obj.getApicultor() != null) {
                stmt.setInt(1, obj.getApicultor().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inventario> read() {

        List<Inventario> inventarios = new ArrayList<>();
        String sql = """
                SELECT i.id AS id_inventario, 
                       a.id AS id_apicultor, a.nome, a.cpfCnpj, a.telefone, a.endereco
                FROM inventario i
                LEFT JOIN apicultor a ON i.id_apicultor = a.id
                """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inventario inventario = new Inventario();
                inventario.setId(rs.getInt("id_inventario"));

                int idApicultor = rs.getInt("id_apicultor");
                if (idApicultor > 0) {
                    Apicultor apicultor = new Apicultor(
                            idApicultor,
                            rs.getString("nome"),
                            rs.getString("cpfCnpj"),
                            rs.getString("telefone"),
                            rs.getString("endereco")
                    );
                    inventario.setApicultor(apicultor);
                }

                inventarios.add(inventario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventarios;
    }

    @Override
    public void update(Inventario obj) {
        String sql = "UPDATE inventario SET id_apicultor = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (obj.getApicultor() != null) {
                stmt.setInt(1, obj.getApicultor().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            stmt.setInt(2, obj.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM inventario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
