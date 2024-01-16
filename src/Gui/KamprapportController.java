package Gui;

import Data.*;
import Logic.Export;
import Logic.Import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class KamprapportController extends Controller implements Initializable {

    @FXML private TableColumn<StandingEntry, String> time, team1, goal1, team2, goal2, timeEvent, event, team, realtime;
    @FXML private TableView<StandingEntry> tableView, tableViewEvents;
    @FXML private Button exportButton, importButton;
    @FXML private Tab tabReport;

    private int matchID = -1;
    DBimport importDB = new DBimport();
    DBmatch matchDB = new DBmatch();
    DBevent eventDB = new DBevent();

    private final ObservableList<StandingEntry> data = FXCollections.observableArrayList();
    private final ArrayList<Match> list = matchDB.getMatches();

    private final ObservableList<StandingEntry> dataEvent = FXCollections.observableArrayList();
    private ArrayList<Event> eventlist;

    public void AddEntry() {

        for (Match m : list)
           data.add(new StandingEntry(
                   m.getID(),
                   m.getTime(),
                   m.getTeam1(),
                   m.getGoals1(),
                   m.getTeam2(),
                   m.getGoals2())
           );

        setCellValue(time, "col1");
        setCellValue(team1, "col2");
        setCellValue(goal1, "col3");
        setCellValue(team2, "col4");
        setCellValue(goal2, "col5");

        tableView.setItems(data);
        tableView.getSelectionModel().selectFirst();
        showhideButton();
    }

    public void showhideButton() {
        boolean show = !exportButton.isVisible();
        exportButton.setVisible(show);
        importButton.setVisible(show);
    }

    public void setEventTable() {
        eventlist = eventDB.getEvents(matchID);
        dataEvent.clear();

        for (Event e : eventlist)
            dataEvent.add(new StandingEntry(
                    0,
                    e.getTime(),
                    e.getName(),
                    e.getTeam(),
                    e.getRealTime())
            );

        setCellValue(timeEvent, "col1");
        setCellValue(event, "col2");
        setCellValue(team, "col3");
        setCellValue(realtime, "col4");

        tableViewEvents.setItems(dataEvent);;
        showhideButton();
    }

    public EventHandler<javafx.event.Event> tabReport() {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        matchID = entry.getID();
        setEventTable();
        return null;
    }

    public void exportFile() throws IOException {
        Export.events(exportButton, eventlist);
    }

    public void importFile() {
        Import.importFile(importButton, importDB, matchID);
        setEventTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }

}
