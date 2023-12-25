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
    private TableColumn<Departement, String> dep_column;

    @FXML
    private Label dep_max_emp_label;

    @FXML
    private Label nbr_emp_label;

    @FXML
    private TableColumn<Departement, Integer> nbr_employe_column;

    @FXML
    private TableView<Departement> table_nbr_emp_dep;

    private final Connection connection = DsConnection.getConnection();
    private final DaoEmploye daoEmploye = new DaoEmploye(connection);
    private final DaoDepartement daoDepartement = new DaoDepartement(connection);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Nombre_employe();
        Dep_with_max_employe();
    }

    public void Nombre_employe() {
        nbr_emp_label.setText(String.valueOf(daoEmploye.Count()));
    }

    public void Dep_with_max_employe() {
        if (daoDepartement.Dep_with_max_employees() != null) {
            System.out.println("Département avec max employe trouvé !");
            dep_max_emp_label.setText(daoDepartement.Dep_with_max_employees().getNom_dept());
        }
    }



    @FXML
    public void Back_Home(ActionEvent actionEvent) {
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
