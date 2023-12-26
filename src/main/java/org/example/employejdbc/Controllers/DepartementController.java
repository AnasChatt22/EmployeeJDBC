package org.example.employejdbc.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.employejdbc.Application;
import org.example.employejdbc.Models.DaoDepartement;
import org.example.employejdbc.Models.DaoEmploye;
import org.example.employejdbc.Models.Departement;
import org.example.employejdbc.Models.Employe;
import org.example.employejdbc.datasource.DsConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;


public class DepartementController implements Initializable {

    @FXML
    private Button back_btn;

    @FXML
    private ComboBox<Departement> dep_select;

    @FXML
    private TableColumn<Employe, Integer> id_column;

    @FXML
    private TableColumn<Employe, String> nom_column;

    @FXML
    private TableColumn<Employe, Double> salaire_column;

    @FXML
    private TableColumn<Employe, Integer> age_column;

    @FXML
    private TableView<Employe> table;

    private final Connection connection = DsConnection.getConnection();
    private final DaoEmploye daoEmploye = new DaoEmploye(connection);
    private final DaoDepartement daoDepartement = new DaoDepartement(connection);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Departement> departmentList = getDepartments();
        dep_select.setItems(departmentList);
        afficherEmployes();
    }

    private ObservableList<Departement> getDepartments() {
        ObservableList<Departement> departments = FXCollections.observableArrayList(daoDepartement.All());
        return departments;
    }

    public void afficherEmployesParDepartement(ActionEvent actionEvent) {
        ObservableList<Employe> employes = FXCollections.observableArrayList(daoDepartement.EmployeesByDepartment(dep_select.getSelectionModel().getSelectedItem()));
        table.setItems(employes);

        id_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Id_emp"));
        nom_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("Nom"));
        age_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Age"));
        salaire_column.setCellValueFactory(new PropertyValueFactory<Employe, Double>("Salaire"));
    }

    public void afficherEmployes() {
        ObservableList<Employe> employes = FXCollections.observableArrayList(daoEmploye.All());
        table.setItems(employes);

        id_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Id_emp"));
        nom_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("Nom"));
        age_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Age"));
        salaire_column.setCellValueFactory(new PropertyValueFactory<Employe, Double>("Salaire"));
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
