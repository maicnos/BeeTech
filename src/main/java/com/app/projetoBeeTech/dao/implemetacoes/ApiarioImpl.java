package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.ApiarioDAO;
import com.app.projetoBeeTech.model.producao.Apiario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApiarioImpl implements ApiarioDAO {

    @Override
    public Apiario create(Apiario obj) {

        String sql = "INSERT INTO apiario (nome, localizacao, id_apicultor) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getLocalizacao());

            // A forma mais simples é armazenar o id_apicultor dentro do objeto Apiario
            // (ex: obj.getApicultor().getId()) — ajuste conforme seu modelo real.
            int idApicultor = obj.getApicultor() != null ? obj.getApicultor().getId() : 0;
            stmt.setInt(3, idApicultor);

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
    public List<Apiario> read() {
        List<Apiario> lista = new ArrayList<>();
        String sql = "SELECT * FROM apiario";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Apiario apiario = new Apiario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("localizacao")
                );
                lista.add(apiario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Apiario obj) {
        String sql = "UPDATE apiario SET nome = ?, localizacao = ?, id_apicultor = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getLocalizacao());
            int idApicultor = obj.getApicultor() != null ? obj.getApicultor().getId() : 0;
            stmt.setInt(3, idApicultor);
            stmt.setInt(4, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM apiario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Apiario> findByApicultorId(int idApicultor) {
        List<Apiario> lista = new ArrayList<>();
        String sql = "SELECT * FROM apiario WHERE id_apicultor = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idApicultor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Apiario apiario = new Apiario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("localizacao")
                );
                lista.add(apiario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
