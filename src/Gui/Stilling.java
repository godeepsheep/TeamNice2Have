package Gui;

import javafx.fxml.FXML;

import javax.swing.table.TableColumn;

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

    public void AddEntry(String _Stilling, String _HoldNavn, String _Kampe, String _Pts, String _Goals){
    Stilling.setHeaderValue(_Stilling);
    HoldNavn.setHeaderValue(_HoldNavn);
    Kampe.setHeaderValue(_Kampe);
    Pts.setHeaderValue(_Pts);
    Goals.setHeaderValue(_Goals);
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
