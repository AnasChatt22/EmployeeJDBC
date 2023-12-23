package org.example.employejdbc.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    public Button clear_btn;
    public TextField search_input;
    @FXML
    private Button login_btn;
    @FXML
    private TextField pwd_input;
    @FXML
    private TextField usr_input;
    @FXML
    private Button add_btn;

    @FXML
    private TableColumn<Employe, Integer> age_column;

    @FXML
    private TextField age_input;

    @FXML
    private Button delete_btn;

    @FXML
    private TableColumn<Employe, Departement> dep_column;

    @FXML
    private ComboBox<Departement> dep_select;

    @FXML
    private TableColumn<Employe, Integer> id_column;

    @FXML
    private TableColumn<Employe, String> nom_column;

    @FXML
    private TextField nom_input;

    @FXML
    private TableColumn<Employe, Double> salaire_column;

    @FXML
    private TextField salaire_input;

    @FXML
    private Button search_btn;

    @FXML
    private TableView<Employe> table;

    @FXML
    private Button update_btn;

    @FXML
    private Label dep_maxemp_label;

    @FXML
    private Label nbr_emp_label;

    private final Connection Myconn = DsConnection.getConnection();

    private final DaoEmploye daoEmploye = new DaoEmploye(Myconn);
    private final DaoDepartement daoDepartement = new DaoDepartement(Myconn);
    private int id = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Departement> departmentList = getDepartments();
        dep_select.setItems(departmentList);
        showEmployee();
        nbr_total_employee();
    }

    private ObservableList<Departement> getDepartments() {
        ObservableList<Departement> departments = FXCollections.observableArrayList(daoDepartement.All());
        return departments;
    }


    @FXML
    void Add(ActionEvent event) throws Exception {
        String nom = nom_input.getText();
        String salaireText = salaire_input.getText();
        String ageText = age_input.getText();
        Departement selectedDepartement = dep_select.getValue();

        // Check if any of the required fields are empty
        if (nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please fill in all required fields", "");
            return;  // Exit the method if validation fails
        }

        // Validate numeric inputs
        try {
            double salaire = Double.parseDouble(salaireText);
            int age = Integer.parseInt(ageText);

            // Proceed with adding the employee
            Employe emp = new Employe(0, nom, salaire, age, selectedDepartement);
            boolean rs = daoEmploye.Add(emp);

            if (rs) {
                showEmployee();
                nbr_total_employee();
                nom_input.clear();
                age_input.clear();
                salaire_input.clear();
                dep_select.getSelectionModel().clearSelection();
                showAlert(Alert.AlertType.INFORMATION, "Employee Registration", "Employee Registration", "Employee Added successfully !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Employee Registration", "Employee Registration", "Employee not Added !");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please enter valid numeric values for Salary and Age", "");
        }
    }


    public void showEmployee() {

        ObservableList<Employe> employes = FXCollections.observableArrayList(daoEmploye.All());
        table.setItems(employes);
        id_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Id_emp"));
        nom_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("Nom"));
        age_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Age"));
        salaire_column.setCellValueFactory(new PropertyValueFactory<Employe, Double>("Salaire"));
        dep_column.setCellValueFactory(new PropertyValueFactory<Employe, Departement>("dep"));

    }

    @FXML
    public void Delete(ActionEvent event) {
        if (daoEmploye.Delete(id)) {
            showEmployee();
            nbr_total_employee();
            nom_input.clear();
            age_input.clear();
            salaire_input.clear();
            dep_select.getSelectionModel().clearSelection();
            add_btn.setDisable(false);
            showAlert(Alert.AlertType.INFORMATION, "Employee Delete", "Employee Delete", "Employee Deleted successfully !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Employee Delete", "Employee Delete", "Employee not Deleted!");
        }
    }

    @FXML
    public void Update(ActionEvent event) {
        String nom = nom_input.getText();
        String salaireText = salaire_input.getText();
        String ageText = age_input.getText();
        Departement selectedDepartement = dep_select.getValue();

        // Check if any of the required fields are empty
        if (nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            showAlert(Alert.AlertType.ERROR, "Input Validation Error", "Please fill in all required fields", "");
            return;  // Exit the method if validation fails
        }

        // Validate numeric inputs
        try {
            double salaire = Double.parseDouble(salaireText);
            int age = Integer.parseInt(ageText);
            Employe emp = new Employe(id, nom, salaire, age, selectedDepartement);

            if (daoEmploye.Update(emp, id)) {
                showEmployee();
                nbr_total_employee();
                nom_input.clear();
                age_input.clear();
                salaire_input.clear();
                dep_select.getSelectionModel().clearSelection();
                add_btn.setDisable(false);

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
    public void clearFields(ActionEvent event) {
        id = 0;
        nom_input.clear();
        salaire_input.clear();
        age_input.clear();
        search_input.clear();
        dep_select.getSelectionModel().clearSelection();
        add_btn.setDisable(false);

    }

    @FXML
    void getData(MouseEvent event) {
        Employe employe = table.getSelectionModel().getSelectedItem();
        id = employe.getId_emp();
        nom_input.setText(employe.getNom());
        salaire_input.setText(String.valueOf(employe.getSalaire()));
        age_input.setText(String.valueOf(employe.getAge()));
        dep_select.getSelectionModel().select(employe.getDep());
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void nbr_total_employee() {
        nbr_emp_label.setText(String.valueOf(daoEmploye.Count()));
    }


}