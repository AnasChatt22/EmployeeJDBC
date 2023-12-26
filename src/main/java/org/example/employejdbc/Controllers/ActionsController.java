package org.example.employejdbc.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static org.example.employejdbc.Controllers.AlertController.AfficherAlert;

public class ActionsController implements Initializable {

    @FXML
    private TextField id_input_delete;

    @FXML
    private TextField id_input_update;

    @FXML
    private Button back_btn;

    @FXML
    private TableColumn<Employe, Integer> age_column;

    @FXML
    private TextField age_input_add;

    @FXML
    private TextField age_input_update;

    @FXML
    private TableColumn<Employe, String> dep_column;

    @FXML
    private ComboBox<Departement> dep_select_add;

    @FXML
    private ComboBox<Departement> dep_select_update;

    @FXML
    private TableColumn<Employe, Integer> id_column;

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
    private TableView<Employe> table;

    private final Connection connection = DsConnection.getConnection();

    private final DaoEmploye daoEmploye = new DaoEmploye(connection);
    private final DaoDepartement daoDepartement = new DaoDepartement(connection);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Departement> departmentList = getDepartments();
        dep_select_add.setItems(departmentList);
        dep_select_update.setItems(departmentList);
        afficherEmployes();
    }

    private ObservableList<Departement> getDepartments() {
        ObservableList<Departement> departments = FXCollections.observableArrayList(daoDepartement.All());
        return departments;
    }

    @FXML
    void Ajouter(ActionEvent event) {
        String nom = nom_input_add.getText();
        String salaireText = salaire_input_add.getText();
        String ageText = age_input_add.getText();
        Departement selectedDepartement = dep_select_add.getValue();

        if (nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires", "");
            return;
        }

        try {
            double salaire = Double.parseDouble(salaireText);
            int age = Integer.parseInt(ageText);

            if (age < 18 || age > 63) {
                AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "L'âge doit être entre 18 et 63", "");
                return;
            }

            Employe emp = new Employe(0, nom, salaire, age, selectedDepartement);
            if (daoEmploye.Add(emp)) {
                afficherEmployes();
                nom_input_add.clear();
                age_input_add.clear();
                salaire_input_add.clear();
                dep_select_add.getSelectionModel().clearSelection();
                dep_select_add.setValue(null);

                AfficherAlert(Alert.AlertType.INFORMATION, "Enregistrement de l'employé", "Enregistrement de l'employé", "Employé ajouté avec succès !");
            } else {
                AfficherAlert(Alert.AlertType.ERROR, "Enregistrement de l'employé", "Enregistrement de l'employé", "Employé non ajouté !");
            }
        } catch (NumberFormatException e) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez saisir des valeurs numériques valides pour le salaire et l'âge", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void Chercher_emp_parID(ActionEvent actionEvent) {
        String input_id = id_input_update.getText();

        if (input_id.isEmpty()) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires", "");
            return;
        }

        Optional<Employe> employeOptional = daoEmploye.Read(Integer.parseInt(input_id));

        employeOptional.ifPresent(employe -> {
            nom_input_update.setText(employe.getNom());
            age_input_update.setText(String.valueOf(employe.getAge()));
            salaire_input_update.setText(String.valueOf(employe.getSalaire()));
            dep_select_update.getSelectionModel().select(employe.getDep());

        });
        if (employeOptional.isEmpty()) {
            AfficherAlert(Alert.AlertType.WARNING, "Employé Non Trouvé", "Aucun employé trouvé avec l'ID : " + input_id, "");
        }
    }


    @FXML
    void Modifier(ActionEvent event) {
        String input_id = id_input_update.getText();
        String nom = nom_input_update.getText();
        String salaireText = salaire_input_update.getText();
        String ageText = age_input_update.getText();
        Departement selectedDepartement = dep_select_update.getSelectionModel().getSelectedItem();

        if (input_id.isEmpty() || nom.isEmpty() || salaireText.isEmpty() || ageText.isEmpty() || selectedDepartement == null) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires", "");
            return;
        }

        try {
            int id = Integer.parseInt(id_input_update.getText());
            double salaire = Double.parseDouble(salaireText);

            // Age validation
            int age = Integer.parseInt(ageText);
            if (age < 18 || age > 63) {
                AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "L'âge doit être entre 18 et 63", "");
                return;
            }

            Employe emp = new Employe(id, nom, salaire, age, selectedDepartement);

            if (daoEmploye.Update(emp, id)) {
                afficherEmployes();
                id_input_update.clear();
                nom_input_update.clear();
                age_input_update.clear();
                salaire_input_update.clear();
                dep_select_update.getSelectionModel().clearSelection();
                dep_select_update.setValue(null);
                AfficherAlert(Alert.AlertType.INFORMATION, "Mise à jour de l'employé", "Mise à jour de l'employé", "Employé mis à jour avec succès !");
            } else {
                AfficherAlert(Alert.AlertType.ERROR, "Mise à jour de l'employé", "Mise à jour de l'employé", "Employé non mis à jour !");
            }
        } catch (NumberFormatException e) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez saisir des valeurs numériques valides pour le salaire et l'âge et l'ID", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void Supprimer(ActionEvent event) {
        String input_id = id_input_delete.getText();

        // Check if input_id contains only numbers
        if (!input_id.matches("\\d+")) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "L'ID doit être un nombre entier positif", "");
            return;
        }

        if (input_id.isEmpty()) {
            AfficherAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires", "");
            return;
        }

        if (daoEmploye.Delete(Integer.parseInt(input_id))) {
            afficherEmployes();
            id_input_delete.clear();
            AfficherAlert(Alert.AlertType.INFORMATION, "Suppression de l'employé", "Suppression de l'employé", "Employé supprimé avec succès !");
        } else {
            AfficherAlert(Alert.AlertType.ERROR, "Suppression de l'employé", "Suppression de l'employé", "Employé non trouvé ou non supprimé !");
        }
    }


    @FXML
    void EffacerChampsAjout(ActionEvent event) {
        nom_input_add.clear();
        salaire_input_add.clear();
        age_input_add.clear();
        dep_select_add.getSelectionModel().clearSelection();
        dep_select_update.setValue(null);
    }

    @FXML
    void EffacerChampsSuppression(ActionEvent event) {
        id_input_delete.clear();
    }

    @FXML
    void EffacerChampsModification(ActionEvent event) {
        id_input_update.clear();
        nom_input_update.clear();
        salaire_input_update.clear();
        age_input_update.clear();
        dep_select_update.getSelectionModel().clearSelection();
        dep_select_update.setValue(null);
    }


    public void afficherEmployes() {

        ObservableList<Employe> employes = FXCollections.observableArrayList(daoEmploye.All());
        table.setItems(employes);

        id_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Id_emp"));
        nom_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("Nom"));
        age_column.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("Age"));
        salaire_column.setCellValueFactory(new PropertyValueFactory<Employe, Double>("Salaire"));
        dep_column.setCellValueFactory(new PropertyValueFactory<Employe, String>("dep"));
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
