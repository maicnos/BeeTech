package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.CaixaDAO;
import com.app.projetoBeeTech.model.producao.Apiario;
import com.app.projetoBeeTech.model.producao.Caixa;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CaixaImpl implements CaixaDAO {
    @Override
    public Caixa create(Caixa obj) {
        String sql = "INSERT INTO caixa (identificacao, id_apiario) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getIdentificacao());

            if (obj.getApiario() != null) {
                stmt.setInt(2, obj.getApiario().getId());
            } else {
                stmt.setNull(2, Types.INTEGER);
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
    public List<Caixa> read() {
        List<Caixa> listaCaixas = new ArrayList<>();
        String sql = "SELECT c.id, c.identificacao, a.id AS id_apiario, a.nome AS nome_apiario, a.localizacao " +
                "FROM caixa c LEFT JOIN apiario a ON c.id_apiario = a.id";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Caixa caixa = new Caixa(rs.getInt("id"), rs.getString("identificacao"));

                int idApiario = rs.getInt("id_apiario");
                if (idApiario > 0) {
                    Apiario apiario = new Apiario(idApiario, rs.getString("nome_apiario"), rs.getString("localizacao"));
                    caixa.setApiario(apiario);
                }

                listaCaixas.add(caixa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaCaixas;
    }

    @Override
    public void update(Caixa obj) {
        String sql = "UPDATE caixa SET identificacao = ?, id_apiario = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getIdentificacao());

            if (obj.getApiario() != null) {
                stmt.setInt(2, obj.getApiario().getId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setInt(3, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM caixa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
