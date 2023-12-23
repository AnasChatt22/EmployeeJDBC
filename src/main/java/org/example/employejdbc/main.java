package org.example.employejdbc;

import org.example.employejdbc.Models.DaoEmploye;
import org.example.employejdbc.Models.Departement;
import org.example.employejdbc.Models.Employe;
import org.example.employejdbc.datasource.DsConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class main {
    public static void main(String[] args) throws Exception {
        Connection MyConn = DsConnection.getConnection();
        DaoEmploye daoEmploye = new DaoEmploye(MyConn);

        List<Employe> employes = daoEmploye.All();
        employes.forEach(emp -> System.out.println(emp));

        Optional<Employe> employe = daoEmploye.Read(1);
        employe.ifPresentOrElse(
                emp -> System.out.println(emp),
                () -> System.out.println("Article not found !")
        );
    }
}
