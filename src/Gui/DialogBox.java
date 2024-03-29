package Gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class DialogBox {
    public static String textInputDialog(String defaultValue) {
        String StringResult = "";

        for(int i=0;i<2;i++) {
            TextInputDialog inputdialog = new TextInputDialog(defaultValue);
            inputdialog.setTitle("Opret Hold");
            inputdialog.setHeaderText("Indtast holdnavn");
            inputdialog.setContentText("Navn:");

            Optional<String> result = inputdialog.showAndWait();
            StringResult = result.orElse(defaultValue);

            if (StringResult.length() > 25) {
                alert("Holdnavn er for langt. Navnet må ikke være mere end 25 tegn", "For mange tegn");
            } else {
                return StringResult;
            }
        }
        return defaultValue;
    }

    public static File dirInputDialog(Button exportButton) {
        Stage primaryStage = (Stage) exportButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Vælg mappe til eksport");

        return directoryChooser.showDialog(primaryStage);
    }

    public static File fileInputDialog(Button importButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vælg import CSV fil!");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage primaryStage = (Stage) importButton.getScene().getWindow();
        return fileChooser.showOpenDialog(primaryStage);
    }

    public static void alert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}