package com.app.projetoBeeTech.dao.implemetacoes;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import com.app.projetoBeeTech.dao.interfaces.CaixaDAO;
import com.app.projetoBeeTech.model.producao.Apiario;
import com.app.projetoBeeTech.model.producao.Apicultor;
import com.app.projetoBeeTech.model.producao.Caixa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        String sql = """
            SELECT 
                c.id AS caixa_id,
                c.identificacao,
                a.id AS apiario_id,
                a.nome AS apiario_nome,
                a.localizacao,
                p.id AS apicultor_id,
                p.nome AS apicultor_nome,
                p.cpfCnpj,
                p.telefone,
                p.endereco
            FROM caixa c
            LEFT JOIN apiario a ON c.id_apiario = a.id
            LEFT JOIN apicultor p ON a.id_apicultor = p.id;
            """;

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Cria a caixa
                Caixa caixa = new Caixa(
                        rs.getInt("caixa_id"),
                        rs.getString("identificacao")
                );

                int idApiario = rs.getInt("apiario_id");
                if (idApiario > 0) {
                    // Cria o apiário
                    Apiario apiario = new Apiario(
                            idApiario,
                            rs.getString("apiario_nome"),
                            rs.getString("localizacao")
                    );

                    int idApicultor = rs.getInt("apicultor_id");
                    if (idApicultor > 0) {
                        // Cria o apicultor
                        Apicultor apicultor = new Apicultor(
                                idApicultor,
                                rs.getString("apicultor_nome"),
                                rs.getString("cpfCnpj"),
                                rs.getString("telefone"),
                                rs.getString("endereco")
                        );

                        apiario.setApicultor(apicultor);
                    }

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

    public List<Caixa> findByApiarioId(int idApiario) {
        List<Caixa> caixas = new ArrayList<>();

        String sql = "SELECT * FROM caixa WHERE id_apiario = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idApiario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Caixa caixa = new Caixa();
                    caixa.setId(rs.getInt("id"));
                    caixa.setIdentificacao(rs.getString("identificacao"));

                    // Carrega o apiário e o apicultor relacionados
                    ApiarioImpl apiarioDAO = new ApiarioImpl();
                    Apiario apiario = apiarioDAO.readById(rs.getInt("id_apiario"));
                    caixa.setApiario(apiario);

                    caixas.add(caixa);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return caixas;
    }

}
