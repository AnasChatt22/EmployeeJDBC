package org.example.employejdbc.Models;


import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DaoDepartement implements CRUD<Departement, Integer> {

    private final Connection connection;

    public DaoDepartement(Connection MyConn) {
        this.connection = MyConn;
    }

    @Override
    public boolean Add(Departement object) {
        return false;
    }

    @Override
    public List<Departement> All() {
        List<Departement> deps = new ArrayList<Departement>();
        String requete = "SELECT * FROM departement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                deps.add(new Departement(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Sort the list by department ID
        deps.sort(Comparator.comparingInt(Departement::getId_dept));
        return deps;
    }

    @Override
    public Optional<Departement> Read(Integer ID) {
        String requete = "SELECT * FROM departement WHERE IdDept = ?";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setInt(1, ID);
            ResultSet rs = Pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new Departement(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean Update(Departement object, Integer ID) {
        return false;
    }

    @Override
    public boolean Delete(Integer ID) {
        return false;
    }

    @Override
    public Long Count() {
        return null;
    }

    public Departement Dep_with_max_employees() {
        String requete = "SELECT d.IdDept, d.NomDept, COUNT(e.IdEmp) AS EmployeeCount " +
                "FROM departement d " +
                "LEFT JOIN employee e ON d.IdDept = e.RefDept " +
                "GROUP BY d.IdDept " +
                "ORDER BY EmployeeCount DESC " +
                "LIMIT 1";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                int idDept = rs.getInt(1);
                String name = rs.getString(2);
                return new Departement(idDept, name);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


}
