package Gui;

import Data.Datalayer;
import Logic.League;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Stilling implements Initializable {


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

        final ObservableList<StandingEntry> data = FXCollections.observableArrayList();
        //new StandingEntry("1", "Hello World!", "1", "1", "1")

        Datalayer datalayer = new Datalayer();
        ArrayList<League> list = datalayer.getLeague();

        for (League l : list)
            data.add(new StandingEntry(l.getStanding(), l.getName(), l.getMatches(), l.getGoalsDiff(), l.getPoints()) );

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }
}
