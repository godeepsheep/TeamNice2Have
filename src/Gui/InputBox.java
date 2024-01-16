package Gui;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class InputBox {
    public static String inputDialog(String defaultValue) {
        TextInputDialog inputdialog = new TextInputDialog(defaultValue);
        inputdialog.setTitle("Opret Hold");
        inputdialog.setHeaderText("Indtast holdnavn");
        inputdialog.setContentText("Navn:");

        Optional<String> result = inputdialog.showAndWait();

        return result.orElse(defaultValue);
    }
}