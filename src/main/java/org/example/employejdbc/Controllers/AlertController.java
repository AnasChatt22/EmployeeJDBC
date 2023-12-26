package org.example.employejdbc.Controllers;

import javafx.scene.control.Alert;

public class AlertController {

    public static void AfficherAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
