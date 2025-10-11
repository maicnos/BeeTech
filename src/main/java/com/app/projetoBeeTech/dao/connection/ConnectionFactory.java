package com.app.projetoBeeTech.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/beetechdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private ConnectionFactory() {}

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}

