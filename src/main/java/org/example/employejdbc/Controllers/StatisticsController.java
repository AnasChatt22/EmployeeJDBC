package org.example.employejdbc.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.employejdbc.Application;
import org.example.employejdbc.Models.DaoDepartement;
import org.example.employejdbc.Models.DaoEmploye;
import org.example.employejdbc.Models.Departement;
import org.example.employejdbc.datasource.DsConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private Button back_btn;

    @FXML
    private Label masse_totale_label;

    @FXML
    private TableColumn<Departement, String> dep_column;

    @FXML
    private Label dep_max_emp_label;

    @FXML
    private Label nbr_emp_label;

    @FXML
    private TableColumn<Departement, Integer> nbr_employe_column;

    @FXML
    private TableColumn<Departement, Double> masse_column;

    @FXML
    private TableView<Departement> table_nbr_emp_dep;

    private final Connection connection = DsConnection.getConnection();
    private final DaoEmploye daoEmploye = new DaoEmploye(connection);
    private final DaoDepartement daoDepartement = new DaoDepartement(connection);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherNombreEmployes();
        departementAvecPlusEmployes();
        afficherNbrEmpMasseParDep();
        afficherMasseTotale();
    }

    public void afficherNombreEmployes() {
        nbr_emp_label.setText(String.valueOf(daoEmploye.Count()));
    }

    public void departementAvecPlusEmployes() {
        if (daoDepartement.Dep_with_max_employees() != null) {
            dep_max_emp_label.setText(daoDepartement.Dep_with_max_employees().getNom_dept());
        }
    }

    public void afficherMasseTotale() {
        masse_totale_label.setText(String.valueOf(daoEmploye.MasseSalarialeEntreprise()));
    }

    public void afficherNbrEmpMasseParDep() {
        Map<Departement, Integer> employeemap = daoDepartement.CountEmployesparDepartment();
        ObservableList<Departement> departements = FXCollections.observableArrayList();

        for (Map.Entry<Departement, Integer> entry : employeemap.entrySet()) {
            Departement departement = entry.getKey();
            int employeeCount = entry.getValue();
            double masse = daoDepartement.MasseSalarialeDepartement(departement);
            departement.setNbr_emp(employeeCount);
            departement.setMasse(masse);
            departements.add(departement);
        }
        dep_column.setCellValueFactory(new PropertyValueFactory<>("nom_dept"));
        nbr_employe_column.setCellValueFactory(new PropertyValueFactory<>("nbr_emp"));
        masse_column.setCellValueFactory(new PropertyValueFactory<>("masse"));
        table_nbr_emp_dep.setItems(departements);
    }


    @FXML
    public void RetourAccueil(ActionEvent actionEvent) {
        try {
            back_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
