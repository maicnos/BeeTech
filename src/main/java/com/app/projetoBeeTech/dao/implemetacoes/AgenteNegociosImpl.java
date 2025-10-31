package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.AgenteNegociosDAO;
import com.app.projetoBeeTech.model.usuario.AgenteNegocios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteNegociosImpl implements AgenteNegociosDAO {
    @Override
    public AgenteNegocios create(AgenteNegocios obj) {
        String sql = "INSERT INTO agenteNegocios (nome, cpf, telefone, senha_hash) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getTelefone());


            java.lang.reflect.Field senhaField = obj.getClass().getSuperclass().getDeclaredField("senhaHash");
            senhaField.setAccessible(true);
            String senhaHash = (String) senhaField.get(obj);
            stmt.setString(4, senhaHash);

            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AgenteNegocios> read() {
        List<AgenteNegocios> listaAgentes = new ArrayList<>();
        String sql = "SELECT * FROM agenteNegocios";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AgenteNegocios agente = new AgenteNegocios(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        ""
                );

                java.lang.reflect.Field senhaField = agente.getClass().getSuperclass().getDeclaredField("senhaHash");
                senhaField.setAccessible(true);
                senhaField.set(agente, rs.getString("senha_hash"));

                listaAgentes.add(agente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaAgentes;
    }

    @Override
    public void update(AgenteNegocios obj) {
        String sql = "UPDATE agenteNegocios SET nome = ?, cpf = ?, telefone = ?, senha_hash = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getTelefone());

            java.lang.reflect.Field senhaField = obj.getClass().getSuperclass().getDeclaredField("senhaHash");
            senhaField.setAccessible(true);
            String senhaHash = (String) senhaField.get(obj);
            stmt.setString(4, senhaHash);

            stmt.setInt(5, obj.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM agenteNegocios WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
