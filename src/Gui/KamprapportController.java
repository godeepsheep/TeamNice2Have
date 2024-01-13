package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class KamprapportController {

    @FXML
    private Label KamprapportLabel;

    // You can add more UI elements and logic here

    @FXML
    public void initialize() {
        KamprapportLabel.setText("Kamprapport"); // Set initial text or perform other initialization
    }
}
