package Gui;

import Data.Datalayer;
import Logic.Export;
import Data.League;
import Logic.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Stilling extends controller implements Initializable {

    @FXML private TableColumn<StandingEntry, String> Stilling, HoldNavn, Kampe, Goals, Pts;
    @FXML private TableView<StandingEntry> tableView;
    @FXML private Button exportButton;
    Datalayer datalayer = new Datalayer();
    private final ArrayList<League> list = datalayer.getLeague();
    private final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry() {

        for (League l : list)
            data.add(new StandingEntry(
                    l.getID(),
                    l.getStanding(),
                    l.getName(),
                    l.getMatches(),
                    l.getGoalsDiff(),
                    l.getPoints())
            );

        setCellValue(Stilling, "col1");
        setCellValue(HoldNavn, "col2");
        setCellValue(Kampe, "col3");
        setCellValue(Goals, "col4");
        setCellValue(Pts, "col5");

        tableView.setItems(data);

    }

    /*private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }
*/

    public void addTeam() {
        Team.add(tableView, data, datalayer);
    }

    public void editTeam() {
        Team.edit(tableView, data, datalayer);
    }

    public void deleteTeam() {
        Team.delete(tableView, data, datalayer);
    }

    public void exportFile() throws IOException {
        Export.league(exportButton,list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }

}
