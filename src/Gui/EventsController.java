package Gui;

import Data.Datalayer;
import Logic.Event;
import Logic.Match;
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


public class EventsController implements Initializable  {

    @FXML TableColumn<StandingEntry, String> time, event, team, realtime;
    @FXML TableView<StandingEntry> tableView;

    Datalayer datalayer = new Datalayer();
    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry(int matchID) {

        ArrayList<Event> list = datalayer.getEvents(matchID);

        for (Event e : list)
           data.add(new StandingEntry(0, e.getTime(), e.getName(), e.getTeam(), e.getRealTime()));

        setCellValue(time, "col1");
        setCellValue(event, "col2");
        setCellValue(team, "col3");
        setCellValue(realtime, "col4");

        tableView.setItems(data);
    }

    private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }

    public void exportFile() {

    }

    public void importFile() {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry(1);
    }

}
