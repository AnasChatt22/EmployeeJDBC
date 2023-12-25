package org.example.employejdbc.Models;

import org.example.employejdbc.datasource.DsConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
    public static void dropTableIfExists(Connection MyConn, String tableName) {
        try {
            Statement statement = MyConn.createStatement();
            String requete = "DROP TABLE IF EXISTS " + tableName;
            statement.execute(requete);
            //System.out.println("Table " + tableName + " dropped successfully (if it existed).");
        } catch (SQLException ex) {
            System.out.println("Error dropping table " + tableName + ": " + ex);
        }
    }

    public static void createEmployeeTable(Connection MyConn) {
        try {
            Statement statement = MyConn.createStatement();
            String requete = "CREATE TABLE IF NOT EXISTS Employee (" +
                    "IdEmp INT PRIMARY KEY AUTO_INCREMENT ," +
                    "NomEmp VARCHAR(255) NOT NULL," +
                    "Salaire DOUBLE NOT NULL," +
                    "Age INT NOT NULL," +
                    "RefDept INT NOT NULL," +
                    "FOREIGN KEY (RefDept) REFERENCES Departement(IdDept)" +
                    ")";
            statement.execute(requete);
            System.out.println("Table Employee created successfully.");
        } catch (SQLException ex) {
            System.out.println("Error creating table Employee : " + ex);
        }
    }


    public static void createDepartmentTable(Connection MyConn) {
        try {
            Statement statement = MyConn.createStatement();
            String requete = "CREATE TABLE IF NOT EXISTS Departement (" +
                    "IdDept INT PRIMARY KEY AUTO_INCREMENT," +
                    "NomDept VARCHAR(255) NOT NULL" +
                    ")";
            statement.execute(requete);
            System.out.println("Table Departement created successfully.");
        } catch (SQLException ex) {
            System.out.println("Error creating table Departement : " + ex);
        }
    }

    public static void insertTestData(Connection MyConn) {
        try {
            Statement statement = MyConn.createStatement();
            String requete1 = "INSERT INTO Departement (IdDept, NomDept) VALUES " +
                    "(1, 'IT'), " +
                    "(2, 'RH'), " +
                    "(3, 'Finance')";
            statement.execute(requete1);

            String requete2 = "INSERT INTO Employee VALUES " +
                    "(1, 'Anas Chatt', 9000.0, 30, 1), " +
                    "(2,'Oussama Bentoufile', 8500.0, 35, 2), " +
                    "(3,'Aymane Chanaa', 9500.0, 28, 1)";
            statement.execute(requete2);

            System.out.println("Test data inserted successfully.");
        } catch (SQLException ex) {
            System.out.println("Error inserting test data : " + ex);
        }
    }

    public static void main(String[] args) {
        Connection MyConn = DsConnection.getConnection();
        dropTableIfExists(MyConn, "Employee");
        dropTableIfExists(MyConn, "Departement");
        createDepartmentTable(MyConn);
        createEmployeeTable(MyConn);
        insertTestData(MyConn);
    }

}




