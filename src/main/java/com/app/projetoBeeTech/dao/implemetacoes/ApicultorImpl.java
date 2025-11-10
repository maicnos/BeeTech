package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.ApicultorDAO;
import com.app.projetoBeeTech.model.producao.Apicultor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApicultorImpl implements ApicultorDAO {

    @Override
    public Apicultor create(Apicultor obj) {
        String sql = "INSERT INTO apicultor (nome, cpfCnpj, telefone, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpfCnpj());
            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getEndereco());

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
    public List<Apicultor> read() {
        List<Apicultor> listaApicultor = new ArrayList<>();
        String sql = "SELECT * FROM apicultor";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Apicultor apicultor = new Apicultor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpfCnpj"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );
                listaApicultor.add(apicultor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaApicultor;
    }

    @Override
    public void update(Apicultor obj) {
        String sql = "UPDATE apicultor SET nome = ?, cpfCnpj = ?, telefone = ?, endereco = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpfCnpj());
            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getEndereco());
            stmt.setInt(5, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM apicultor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Apicultor readById(int id) {
        String sql = "SELECT * FROM apicultor WHERE id = ?";
        Apicultor apicultor = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    apicultor = new Apicultor();
                    apicultor.setId(rs.getInt("id"));
                    apicultor.setNome(rs.getString("nome"));
                    apicultor.setCpfCnpj(rs.getString("cpfCnpj"));
                    apicultor.setTelefone(rs.getString("telefone"));
                    apicultor.setEndereco(rs.getString("endereco"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apicultor;
    }

}
