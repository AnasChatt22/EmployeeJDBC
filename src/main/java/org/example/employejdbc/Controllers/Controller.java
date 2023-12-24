package org.example.employejdbc.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.employejdbc.Models.DaoDepartement;
import org.example.employejdbc.Models.DaoEmploye;
import org.example.employejdbc.Models.Departement;
import org.example.employejdbc.Models.Employe;
import org.example.employejdbc.datasource.DsConnection;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField id_input_delete;
    @FXML
    private TextField id_input_update;
    @FXML
    private Button add_btn;

    @FXML
    private Button clear_btn_add;

    @FXML
    private Button clear_btn_delete;

    @FXML
    private Button clear_btn_update;

    @FXML
    private Button delete_btn;

    @FXML
    private TableColumn<Employe, Integer> age_column;

    @FXML
    private TextField age_input_add;

    @FXML
    private TextField age_input_update;

    @FXML
    private TableColumn<Employe, String> dep_column;

    @FXML
    private Label dep_maxemp_label;

    @FXML
    private ComboBox<Departement> dep_select_add;

    @FXML
    private ComboBox<Departement> dep_select_update;

    @FXML
    private TableColumn<Employe, Integer> id_column;

    @FXML
    private Label nbr_emp_label;

    @FXML
    private TableColumn<Employe, String> nom_column;

    @FXML
    private TextField nom_input_add;

    @FXML
    private TextField nom_input_update;

    @FXML
    private TableColumn<Employe, Double> salaire_column;

    @FXML
    private TextField salaire_input_add;

    @FXML
    private TextField salaire_input_update;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_input;

    @FXML
    private TableView<Employe> table;

    @FXML
    private Button update_btn;

    private final Connection Myconn = DsConnection.getConnection();

    private final DaoEmploye daoEmploye = new DaoEmploye(Myconn);
    private final DaoDepartement daoDepartement = new DaoDepartement(Myconn);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Departement> departmentList = getDepartments();
        dep_select_add.setItems(departmentList);
        dep_select_update.setItems(departmentList);
        showEmployee();
        nbr_total_employee();
    }

    private ObservableList<Departement> getDepartments() {
        ObservableList<Departement> departments = FXCollections.observableArrayList(daoDepartement.All());
        return departments;
    }

    @FXML
    void Add(ActionEvent event) {
        String nom = nom_input_add.getText();
        String salaireText = salaire_input_add.getText();
        String ageText = age_input_add.getText();
        Departement selectedDepartement = dep_select_add.getValue();

        if (nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please fill in all required fields", "");
            return;
        }

        try {
            double salaire = Double.parseDouble(salaireText);
            int age = Integer.parseInt(ageText);

            // Proceed with adding the employee
            Employe emp = new Employe(0, nom, salaire, age, selectedDepartement);
            if (daoEmploye.Add(emp)) {
                selectedDepartement.ajouteEmploye(emp);
                showEmployee();
                nbr_total_employee();
                nom_input_add.clear();
                age_input_add.clear();
                salaire_input_add.clear();
                dep_select_add.getSelectionModel().clearSelection();
                showAlert(Alert.AlertType.INFORMATION, "Employee Registration", "Employee Registration", "Employee Added successfully !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Employee Registration", "Employee Registration", "Employee not Added !");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please enter valid numeric values for Salary and Age", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Update(ActionEvent event) {
        String input_id = id_input_update.getText();
        String nom = nom_input_update.getText();
        String salaireText = salaire_input_update.getText();
        String ageText = age_input_update.getText();
        Departement selectedDepartement = dep_select_update.getValue();

        if (input_id.isEmpty() || nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please fill in all required fields", "");
            return;
        }

        try {
            int id = Integer.parseInt(id_input_update.getText());
            double salaire = Double.parseDouble(salaireText);
            int age = Integer.parseInt(ageText);
            Employe emp = new Employe(id, nom, salaire, age, selectedDepartement);

            if (daoEmploye.Update(emp, id)) {
                showEmployee();
                nbr_total_employee();
                id_input_update.clear();
                nom_input_update.clear();
                age_input_update.clear();
                salaire_input_update.clear();
                dep_select_update.getSelectionModel().clearSelection();
                showAlert(Alert.AlertType.INFORMATION, "Employee Update", "Employee Update", "Employee Updated successfully !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Employee Update", "Employee Update", "Employee not Updated!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please enter valid numeric values for Salary and Age", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        String input_id = id_input_delete.getText();

        if (input_id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please fill in all required fields", "");
            return;
        }
        if (daoEmploye.Delete(Integer.parseInt(input_id))) {
            showEmployee();
            nbr_total_employee();
            id_input_delete.clear();
            showAlert(Alert.AlertType.INFORMATION, "Employee Delete", "Employee Delete", "Employee Deleted successfully !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Employee Delete", "Employee Delete", "Employee not Deleted!");
        }
    }

    @FXML
    void Search_by_dep(ActionEvent event) {

    }

    @FXML
    void Clear_fields_add(ActionEvent event) {
        nom_input_add.clear();
        salaire_input_add.clear();
        age_input_add.clear();
        dep_select_add.getSelectionModel().clearSelection();
    }

    @FXML
    void Clear_fields_delete(ActionEvent event) {
        id_input_delete.clear();
    }

    @FXML
    void Clear_fields_update(ActionEvent event) {
        id_input_update.clear();
        nom_input_update.clear();
        salaire_input_update.clear();
        age_input_update.clear();
        dep_select_update.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showEmployee() {

        ObservableList<Employe> employes = FXCollections.observableArrayList(daoEmploye.All());
        table.setItems(employes);

        id_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Id_emp"));
        nom_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("Nom"));
        age_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Age"));
        salaire_column.setCellValueFactory(new PropertyValueFactory<Employe, Double>("Salaire"));
        dep_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("dep"));

    }

    public void nbr_total_employee() {
        nbr_emp_label.setText(String.valueOf(daoEmploye.Count()));
    }

}
