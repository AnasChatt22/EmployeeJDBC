package org.example.employejdbc.Models;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Departement {
    private int id_dept ;
    private String Nom_dept ;


    public Departement(int id_dept, String nom_dept) {
        this.id_dept = id_dept;
        this.Nom_dept = nom_dept;
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
