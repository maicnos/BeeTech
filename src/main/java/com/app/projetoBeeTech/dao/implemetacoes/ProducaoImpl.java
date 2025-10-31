package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.ProducaoDAO;
import com.app.projetoBeeTech.model.producao.Caixa;
import com.app.projetoBeeTech.model.producao.Producao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducaoImpl implements ProducaoDAO {

    @Override
    public Producao create(Producao obj) {
        String sql = "INSERT INTO producao (tipo, quantidade, data, id_caixa) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getTipo().name());
            stmt.setDouble(2, obj.getQuantidade());
            stmt.setDate(3, new java.sql.Date(obj.getData().getTime()));

            if (obj.getCaixa() != null) {
                stmt.setInt(4, obj.getCaixa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
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
            return null;
        }
    }

    @Override
    public List<Producao> read() {
        List<Producao> listaProducao = new ArrayList<>();
        String sql = """
                SELECT p.id, p.tipo, p.quantidade, p.data,
                       c.id AS id_caixa, c.identificacao
                FROM producao p
                LEFT JOIN caixa c ON p.id_caixa = c.id
                """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producao.Tipo tipo = Producao.Tipo.valueOf(rs.getString("tipo"));
                Producao producao = new Producao(
                        rs.getInt("id"),
                        tipo,
                        rs.getDouble("quantidade"),
                        rs.getDate("data")
                );

                int idCaixa = rs.getInt("id_caixa");
                if (idCaixa > 0) {
                    Caixa caixa = new Caixa(idCaixa, rs.getString("identificacao"));
                    producao.setCaixa(caixa);
                }

                listaProducao.add(producao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProducao;
    }

    @Override
    public void update(Producao obj) {
        String sql = "UPDATE producao SET tipo = ?, quantidade = ?, data = ?, id_caixa = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getTipo().name());
            stmt.setDouble(2, obj.getQuantidade());
            stmt.setDate(3, new java.sql.Date(obj.getData().getTime()));

            if (obj.getCaixa() != null) {
                stmt.setInt(4, obj.getCaixa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM producao WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
