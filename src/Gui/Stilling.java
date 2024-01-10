package Gui;

import javafx.fxml.FXML;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Stilling {


    @FXML
    TableColumn Stilling;
    @FXML
    TableColumn HoldNavn;
    @FXML
    TableColumn Kampe;
    @FXML
    TableColumn Pts;
    @FXML
    TableColumn Goals;
    @FXML
    TableView tableView;
    public void AddEntry(String _Stilling, String _HoldNavn, String _Kampe, String _Pts, String _Goals){


    tableView.setItems();

    }
    public void AddEntry(String _HoldNavn) {
        Stilling.setHeaderValue("");
        HoldNavn.setHeaderValue(_HoldNavn);
        Kampe.setHeaderValue(0);
        Pts.setHeaderValue(0);
        Goals.setHeaderValue(0);
    }

    public void getKampe(String _HoldNavn){


    }

}
