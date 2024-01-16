package Gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    public void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }
}

