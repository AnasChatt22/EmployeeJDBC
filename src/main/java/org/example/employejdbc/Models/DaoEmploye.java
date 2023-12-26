package org.example.employejdbc.Models;

import java.net.DatagramPacket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DaoEmploye implements CRUD<Employe, Integer> {

    private final Connection connection;

    public DaoEmploye(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean Add(Employe emp) {
        String requete = "insert into Employee (NomEmp, Salaire, Age, RefDept) values(?,?,?,?);";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setString(1, emp.getNom());
            Pst.setDouble(2, emp.getSalaire());
            Pst.setInt(3, emp.getAge());
            Pst.setInt(4, emp.getDep().getId_dept());

            int nbr_lignes = Pst.executeUpdate();
            if (nbr_lignes == 1) {
                System.out.println("Employé ajouté avec succès.");
                return true;
            } else {
                System.err.println("Employé n'est pas ajouté");
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Erreur dans la requête Add : " + ex.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employe> All() {
        DaoDepartement daoDepartement = new DaoDepartement(connection);
        List<Employe> employees = new ArrayList<Employe>();
        String requete = "SELECT * FROM employee";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Optional<Departement> dep = daoDepartement.Read(rs.getInt(5));
                Departement departement = dep.orElse(null);
                employees.add(new Employe(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), departement));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employees.sort(Comparator.comparingInt(Employe::getId_emp));
        return employees;
    }


    @Override
    public Optional<Employe> Read(Integer ID) {
        DaoDepartement daoDepartement = new DaoDepartement(connection);
        String requete = "select * from employee where IdEmp = ? ";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setInt(1, ID);
            ResultSet rs = Pst.executeQuery();
            if (rs.next()) {
                System.out.println("Employé trouvé.");
                Optional<Departement> departementOptional = daoDepartement.Read(rs.getInt(5));
                Departement departement = departementOptional.orElse(null);
                Employe emp = new Employe(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), departement);
                return Optional.of(emp);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur dans la requête Read : " + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Employé n'est pas trouvé.");
        return Optional.empty();
    }

    @Override
    public boolean Update(Employe emp, Integer ID) {
        String requete = "update employee set NomEmp = ?, Salaire = ?, Age = ?, RefDept = ? where IdEmp = ?";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setString(1, emp.getNom());
            Pst.setDouble(2, emp.getSalaire());
            Pst.setInt(3, emp.getAge());
            Pst.setInt(4, emp.getDep().getId_dept());
            Pst.setInt(5, ID);
            int ligne = Pst.executeUpdate();
            if (ligne != 0) {
                System.out.println("Employé modifié avec succès.");
                return true;
            } else {
                System.out.println("Employé n'est trouvé.");
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur dans la requête Update : " + ex.getMessage());
        }
        System.out.println("Employé n'est pas modifié.");
        return false;
    }

    @Override
    public boolean Delete(Integer ID) {
        String requete = "delete from employee where IdEmp = ?";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setInt(1, ID);
            int ligne = Pst.executeUpdate();
            if (ligne != 0) {
                System.out.println(ligne + " employee deleted with success !");
                return true;
            } else {
                System.out.println("Employé n'est pas trouvé !");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Erreur dans la requête Delete : " + ex.getMessage());
        }
        System.out.println("Employé n'est pas supprimé.");
        return false;
    }

    @Override
    public Long Count() {
        String requete = "select count(*) from employee";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException ex) {
            System.out.println("Erreur dans le requête Count : " + ex.getMessage());
        }
        return 0L; // cast to long
    }

    public double MasseSalarialeEntreprise() {
        String requete = "SELECT SUM(Salaire) FROM employee";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur de calcul de la masse salariale totale : " + ex.getMessage());
        }
        return 0.0;
    }
    

}
