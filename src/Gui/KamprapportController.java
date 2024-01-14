package Gui;

import Data.Datalayer;
import Logic.League;
import Logic.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class KamprapportController implements Initializable  {

    @FXML TableColumn<StandingEntry, String> time, team1, goal1, team2, goal2;
    @FXML TableView<StandingEntry> tableView;

    Datalayer datalayer = new Datalayer();
    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry() {

        ArrayList<Match> list = datalayer.getMatches();

        for (Match m : list)
           data.add(new StandingEntry(m.getID(), m.getTime(), m.getTeam1(), m.getGoals1(), m.getTeam2(), m.getGoals2()));

        setCellValue(time, "col1");
        setCellValue(team1, "col2");
        setCellValue(goal1, "col3");
        setCellValue(team2, "col4");
        setCellValue(goal2, "col5");

        tableView.setItems(data);
    }

    private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }

    public void showEventList() {
        /*
        String name = inputDialog("");
        Match m = datalayer.createTeam(name);

        int index = tableView.getItems().size()-1;
        //bruger index til standing i stedet for data fra databasen, for at undgå at to hold har samme standing
        //data.add(new StandingEntry(l.getID(), Integer.toString(index), name, "0", "0", "0"));

        tableView.refresh();
        tableView.scrollTo(index);
        */

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }

}
