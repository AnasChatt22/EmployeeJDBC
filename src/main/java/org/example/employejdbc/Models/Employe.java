package org.example.employejdbc.Models;


import com.mysql.cj.conf.EnumProperty;

import java.util.Objects;

public class Employe {
    private int Id_emp ;
    private String Nom_emp;
    private double salaire;
    private int age;
    private Departement dep;

    public Employe(int Id, String nom, double salaire, int age, Departement dep) throws Exception {
        this.Id_emp = Id ;
        this.Nom_emp = nom;
        this.salaire = salaire;
        this.age = age;
        if (dep == null) throw new Exception("Impossible de créer un employé sans departement !");
        this.dep = dep;
    }

    public int getId_emp() {
        return Id_emp;
    }

    public void setId_emp(int id_emp) {
        Id_emp = id_emp;
    }

    public String getNom() {
        return Nom_emp;
    }

    public void setNom(String nom) {
        Nom_emp = nom;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Departement getDep() {
        return dep;
    }

    public void setDep(Departement dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "Nom_emp='" + Nom_emp + '\'' +
                ", salaire=" + salaire +
                ", age=" + age +
                ", dep=" + dep.getNom_dept() +
                '}';
    }

    public boolean equals(Employe emp) {
        if (this == emp)
            return true;
        if (emp == null || getClass() != emp.getClass())
            return false;
        return Objects.equals(Nom_emp,emp.Nom_emp);
    }
}
