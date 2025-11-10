package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.GastoDAO;
import com.app.projetoBeeTech.model.financeiro.Gasto;
import com.app.projetoBeeTech.model.producao.Apicultor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GastoImpl implements GastoDAO {

    @Override
    public Gasto create(Gasto obj) {
        String sql = "INSERT INTO gasto (tipo, valor, data, id_apicultor) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getTipo());
            stmt.setDouble(2, obj.getValor());
            stmt.setDate(3, new java.sql.Date(obj.getData().getTime()));
            stmt.setInt(4, obj.getApicultor().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

            return obj;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Gasto> readByApicultor(int idApicultor) {
        List<Gasto> listaGastos = new ArrayList<>();
        String sql = """
            SELECT g.id, g.tipo, g.valor, g.data,
                   a.id AS apicultor_id, a.nome, a.cpfCnpj, a.telefone, a.endereco
            FROM gasto g
            INNER JOIN apicultor a ON g.id_apicultor = a.id
            WHERE a.id = ?
            ORDER BY g.data DESC;
        """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idApicultor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Apicultor apicultor = new Apicultor(
                            rs.getInt("apicultor_id"),
                            rs.getString("nome"),
                            rs.getString("cpfCnpj"),
                            rs.getString("telefone"),
                            rs.getString("endereco")
                    );

                    Gasto gasto = new Gasto(
                            rs.getInt("id"),
                            rs.getDouble("valor"),
                            rs.getDate("data")
                    );
                    gasto.setTipo(rs.getString("tipo"));
                    gasto.setApicultor(apicultor);

                    listaGastos.add(gasto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaGastos;
    }

    @Override
    public List<Gasto> read() {
        List<Gasto> listaGastos = new ArrayList<>();
        String sql = """
            SELECT g.id, g.tipo, g.valor, g.data,
                   a.id AS apicultor_id, a.nome, a.cpfCnpj, a.telefone, a.endereco
            FROM gasto g
            INNER JOIN apicultor a ON g.id_apicultor = a.id;
        """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Apicultor apicultor = new Apicultor(
                        rs.getInt("apicultor_id"),
                        rs.getString("nome"),
                        rs.getString("cpfCnpj"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );

                Gasto gasto = new Gasto(
                        rs.getInt("id"),
                        rs.getDouble("valor"),
                        rs.getDate("data")
                );
                gasto.setTipo(rs.getString("tipo"));
                gasto.setApicultor(apicultor);

                listaGastos.add(gasto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaGastos;
    }

    @Override
    public void update(Gasto obj) {
        String sql = "UPDATE gasto SET tipo = ?, valor = ?, data = ?, id_apicultor = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getTipo());
            stmt.setDouble(2, obj.getValor());
            stmt.setDate(3, new java.sql.Date(obj.getData().getTime()));
            stmt.setInt(4, obj.getApicultor().getId());
            stmt.setInt(5, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM gasto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
