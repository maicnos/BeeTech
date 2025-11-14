package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.ApiarioDAO;
import com.app.projetoBeeTech.model.producao.Apiario;
import com.app.projetoBeeTech.model.producao.Apicultor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApiarioImpl implements ApiarioDAO {

    @Override
    public Apiario create(Apiario obj) {

        if (obj.getApicultor() == null || obj.getApicultor().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Erro: O Apiário deve estar vinculado a um Apicultor existente antes de ser criado."
            );
        }

        String sql = "INSERT INTO apiario (nome, localizacao, id_apicultor) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getLocalizacao());
            stmt.setInt(3, obj.getApicultor().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

            return obj;

        } catch (SQLException e) {
            System.err.println("Erro ao criar Apiário: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Apiario> read() {
        List<Apiario> lista = new ArrayList<>();

        String sql = """
            SELECT 
                a.id AS apiario_id,
                a.nome AS apiario_nome,
                a.localizacao,
                p.id AS apicultor_id,
                p.nome AS apicultor_nome,
                p.cpfCnpj,
                p.telefone,
                p.endereco
            FROM apiario a
            INNER JOIN apicultor p ON a.id_apicultor = p.id;
            """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Apicultor apicultor = new Apicultor(
                        rs.getInt("apicultor_id"),
                        rs.getString("apicultor_nome"),
                        rs.getString("cpfCnpj"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );

                Apiario apiario = new Apiario(
                        rs.getInt("apiario_id"),
                        rs.getString("apiario_nome"),
                        rs.getString("localizacao")
                );
                apiario.setApicultor(apicultor);

                lista.add(apiario);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao ler Apiários: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Apiario obj) {
        if (obj.getApicultor() == null || obj.getApicultor().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Erro: Não é possível atualizar um Apiário sem Apicultor vinculado."
            );
        }

        String sql = "UPDATE apiario SET nome = ?, localizacao = ?, id_apicultor = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getLocalizacao());
            stmt.setInt(3, obj.getApicultor().getId());
            stmt.setInt(4, obj.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar Apiário: " + e.getMessage());
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
            System.err.println("Erro ao excluir Apiário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Apiario> findByApicultorId(int idApicultor) {
        List<Apiario> lista = new ArrayList<>();

        String sql = """
            SELECT 
                a.id AS apiario_id,
                a.nome AS apiario_nome,
                a.localizacao,
                p.id AS apicultor_id,
                p.nome AS apicultor_nome,
                p.cpfCnpj,
                p.telefone,
                p.endereco
            FROM apiario a
            INNER JOIN apicultor p ON a.id_apicultor = p.id
            WHERE a.id_apicultor = ?;
            """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idApicultor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Apicultor apicultor = new Apicultor(
                        rs.getInt("apicultor_id"),
                        rs.getString("apicultor_nome"),
                        rs.getString("cpfCnpj"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );

                Apiario apiario = new Apiario(
                        rs.getInt("apiario_id"),
                        rs.getString("apiario_nome"),
                        rs.getString("localizacao")
                );
                apiario.setApicultor(apicultor);

                lista.add(apiario);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar Apiários por Apicultor: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public Apiario readById(int id) {
        String sql = "SELECT * FROM apiario WHERE id = ?";
        Apiario apiario = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    apiario = new Apiario();
                    apiario.setId(rs.getInt("id"));
                    apiario.setNome(rs.getString("nome"));
                    apiario.setLocalizacao(rs.getString("localizacao"));

                    // Carrega o apicultor dono
                    ApicultorImpl apicultorDAO = new ApicultorImpl();
                    Apicultor apicultor = apicultorDAO.readById(rs.getInt("id_apicultor"));
                    apiario.setApicultor(apicultor);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apiario;
    }

}
