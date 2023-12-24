package org.example.employejdbc.Models;

import java.util.*;

public class Departement {
    private int id_dept ;
    private String Nom_dept ;
    private List<Employe> employes = new ArrayList<Employe>();


    public Departement(int id_dept, String nom_dept) {
        this.id_dept = id_dept;
        this.Nom_dept = nom_dept;
    }

    public void ajouteEmploye(Employe emp) throws Exception {
        if(employes.contains(emp)) throw new Exception("Employe existe deja !");
        employes.add(emp);
    }

    public void retireEmploye(Employe emp) throws Exception {
        if(!employes.contains(emp)) throw new Exception("Employe introuvable !");
        employes.remove(emp);
    }

    public int getId_dept() {
        return id_dept;
    }

    public void setId_dept(int id_dept) {
        this.id_dept = id_dept;
    }

    public String getNom_dept() {
        return Nom_dept;
    }

    public void setNom_dept(String nom_dept) {
        Nom_dept = nom_dept;
    }


    @Override
    public String toString() {
        return Nom_dept;
    }
}
