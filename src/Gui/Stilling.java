package Gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Stilling {


    @FXML
    TableColumn Stilling;
    @FXML
    TableColumn HoldNavn;
    @FXML
    TableColumn Kampe;
    @FXML
    TableColumn Goals;
    @FXML
    TableColumn Pts;
    @FXML
    TableView tableView;
    public void AddEntry(){

        final ObservableList<StandingEntry> data = FXCollections.observableArrayList(
                //Get data from database and enter it here.
                new StandingEntry("1", "Hello World!", "1", "1", "1"),
                new StandingEntry("2", "Hello World!", "1", "1", "1")
        );


        Stilling.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Standing"));
        HoldNavn.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Name"));
        Kampe.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Matches"));
        Goals.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("GoalDiff"));
        Pts.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Points"));

        tableView.setItems(data);

    }
    public void AddEntry(String _HoldNavn) {

    }

    public void getKampe(String _HoldNavn){


    }

}
