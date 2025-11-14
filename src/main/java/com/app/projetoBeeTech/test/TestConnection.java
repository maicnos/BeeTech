package com.app.projetoBeeTech.test;

import com.app.projetoBeeTech.dao.connection.ConnectionFactory;
import java.sql.Connection;

/*
    Classe (programinha em texto) para testar se a conexão com o banco está ok

*/

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = ConnectionFactory.getInstance().getConnection();

        if (conn != null) {
            System.out.println("Conexão obtida com sucesso!");
        } else {
            System.out.println("Falha ao obter conexão!");
        }

        ConnectionFactory.getInstance().closeConnection();
    }
}


