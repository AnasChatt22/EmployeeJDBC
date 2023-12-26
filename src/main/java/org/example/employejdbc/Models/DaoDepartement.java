package org.example.employejdbc.Models;


import java.sql.*;
import java.util.*;

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
        System.out.println("Département n'est pas trouvé.");
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

    public List<Employe> EmployesparDepartment(Departement department) {
        List<Employe> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE RefDept = ?";

        try {
            PreparedStatement Pst = connection.prepareStatement(query);
            Pst.setInt(1, department.getId_dept());

            ResultSet rs = Pst.executeQuery();

            while (rs.next()) {
                int idEmp = rs.getInt("IdEmp");
                String nomEmp = rs.getString("NomEmp");
                double salaire = rs.getDouble("Salaire");
                int age = rs.getInt("Age");

                employees.add(new Employe(idEmp, nomEmp, salaire, age, department));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employees.sort(Comparator.comparingInt(Employe::getId_emp));

        return employees;
    }

    public Map<Departement, Integer> CountEmployesparDepartment() {
        Map<Departement, Integer> employeemap = new HashMap<>();

        String query = "SELECT d.IdDept, d.NomDept, COUNT(e.IdEmp) AS EmployeeCount " +
                "FROM departement d " +
                "LEFT JOIN employee e ON d.IdDept = e.RefDept " +
                "GROUP BY d.IdDept";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int idDept = rs.getInt(1);
                String nom = rs.getString(2);
                int employeeCount = rs.getInt(3);

                Departement department = new Departement(idDept, nom);
                employeemap.put(department, employeeCount);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return employeemap;
    }

    public double MasseSalarialeDepartement(Departement dep) {
        String requete = "SELECT SUM(Salaire) FROM employee WHERE RefDept = ?";
        try {
            PreparedStatement Pst = connection.prepareStatement(requete);
            Pst.setInt(1, dep.getId_dept());
            ResultSet rs = Pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur de calcul de la masse salariale du departement " + dep.getNom_dept() + " : " + ex.getMessage());
        }
        return 0.0;
    }

}
