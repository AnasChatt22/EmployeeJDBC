package org.example.employejdbc.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DsConnection {
    private static Connection connection;

    private DsConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            initializeConnection();
        }
        return connection;
    }

    private static void initializeConnection() {
        try {
            MysqlDataSource ds = new MysqlDataSource();
            // Initialization of the source
            ds.setURL("jdbc:mysql://localhost/entreprise");
            ds.setUser("root");
            ds.setPassword("");
            connection = ds.getConnection();
            // Retrieving the connection
            System.out.println("Connexion établie ...");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
