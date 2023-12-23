package org.example.employejdbc.Models;


import java.sql.*;
import java.util.*;

public class DaoDepartement implements CRUD<Departement, Integer> {

    private final Connection MyConn ;
    public DaoDepartement(Connection MyConn) {
        this.MyConn = MyConn ;
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
            Statement st = MyConn.createStatement();
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
            PreparedStatement Pst = MyConn.prepareStatement(requete);
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

}
