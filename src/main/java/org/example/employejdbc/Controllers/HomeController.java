package org.example.employejdbc.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.employejdbc.Application;
import org.example.employejdbc.datasource.DsConnection;

import java.io.IOException;
import java.sql.Connection;

public class HomeController {
    @FXML
    private Button action_button;

    @FXML
    private Button stat_button;

    @FXML
    private Button aff_emp_dep_btn;

    private final Connection connection = DsConnection.getConnection();

    @FXML
    void open_actions(ActionEvent event) throws IOException {
        action_button.getScene().getWindow().hide();
        loadFXML("Actions.fxml","Actions");

    }

    @FXML
    void open_statistics(ActionEvent event) throws IOException {
        stat_button.getScene().getWindow().hide();
        loadFXML("Statistics.fxml","Statistiques");
    }

    @FXML
    public void open_departement(ActionEvent actionEvent) {
        aff_emp_dep_btn.getScene().getWindow().hide();
        loadFXML("Departement.fxml","Employés d'un département");
    }

    private void loadFXML(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
