package Gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {
    public void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }
}

