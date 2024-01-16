package Gui;

import javafx.scene.control.Alert;

public class AlertBox {
    public static void showDialog(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}