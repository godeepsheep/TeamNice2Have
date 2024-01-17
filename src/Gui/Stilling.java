package Gui;

import Data.DBleague;
import Data.DBteam;
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


public class Stilling extends Controller implements Initializable {

    @FXML private TableColumn<StandingEntry, String> Stilling, HoldNavn, Kampe, Goals, Pts;
    @FXML private TableView<StandingEntry> tableView;
    @FXML private Button exportButton;

    private final DBleague leagueDB = new DBleague();
    private final DBteam teamDB = new DBteam();

    private final ArrayList<League> list = leagueDB.getLeague();
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

    public void addTeam() {
        Team.add(tableView, data, teamDB);
    }

    public void editTeam() {
        Team.edit(tableView, data, teamDB);
    }

    public void deleteTeam() {
        Team.delete(tableView, data, teamDB);
    }

    public void exportFile() throws IOException {
        Export.exportFile(exportButton,list,"Standing_league", "Stilling; Hold; Kampe; Mål; Point");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }

}
