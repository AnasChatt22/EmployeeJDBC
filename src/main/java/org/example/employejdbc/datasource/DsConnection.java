package org.example.employejdbc.datasource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class DsConnection {
    public static Connection getConnection()
    {
        try
        {
            MysqlDataSource ds = new MysqlDataSource();
            // Initialisation de la source
            ds.setURL("jdbc:mysql://localhost/entreprise");
            ds.setUser("root");
            ds.setPassword("");
            Connection MyCon = ds.getConnection();
            // Récupération de la connexion
            System.out.println(" Connexion établie ....");
            return MyCon;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return null ;
        }
    }
}
