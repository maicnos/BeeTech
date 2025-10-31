package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.BemDAO;
import com.app.projetoBeeTech.model.financeiro.Bem;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BemImpl implements BemDAO {
    @Override
    public Bem create(Bem obj) {
        String sql = "INSERT INTO bem (item, quantidade, descricao, valor, data, id_inventario) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = ConnectionFactory.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getItem());
            stmt.setInt(2, obj.getQuantidade());
            stmt.setString(3, obj.getDescricao());
            stmt.setDouble(4, obj.getValor());
            stmt.setDate(5, Date.valueOf(obj.getData()));
            stmt.setInt(6, 1);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public List<Bem> read() {
        List<Bem> listaBem = new ArrayList<>();
        String sql = "SELECT * FROM bem";

        try (   Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bem bem = new Bem(
                        rs.getInt("id"),
                        rs.getString("item"),
                        rs.getInt("quantidade"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getDate("data").toLocalDate()
                );
                listaBem.add(bem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaBem;
    }

    @Override
    public void update(Bem obj) {
        String sql = "UPDATE bem SET item = ?, quantidade = ?, descricao = ?, valor = ?, data = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, obj.getItem());
            stmt.setInt(2, obj.getQuantidade());
            stmt.setString(3, obj.getDescricao());
            stmt.setDouble(4, obj.getValor());
            stmt.setDate(5, Date.valueOf(obj.getData()));
            stmt.setInt(6, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM bem WHERE id = ?";
        try (   Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
