package com.app.projetoBeeTech.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    Central de conexão com o banco de dados
    Singleton aplicado
    DAOs já utilizando esta central

*/

public class ConnectionFactory {


    private static ConnectionFactory instance;

    // Dados da conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/beeTechDB";
    private static final String USER = "root";
    private static final String PASSWORD = "vcG?ZXd!F5wM";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Conexão persistente
    private Connection connection;

    // Construtor privado (impede "new ConnectionFactory()")
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER); // Testa se o driver está conectado
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado ao banco de dados com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        }
    }

    // Método estático para obter a única instância (Singleton)
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    // Método para obter conexão com o banco
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Nova conexão aberta com o banco.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão: " + e.getMessage());
        }
        return connection;
    }

    // Método para fechar conexão
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão encerrada com sucesso.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

