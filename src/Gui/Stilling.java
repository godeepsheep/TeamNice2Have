package Gui;

import Data.Datalayer;
import Logic.League;
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


public class Stilling implements Initializable  {

    @FXML TableColumn<StandingEntry, String> Stilling, HoldNavn, Kampe, Goals, Pts;
    @FXML TableView<StandingEntry> tableView;
    @FXML Button addTeamButton;

    Datalayer datalayer = new Datalayer();
    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry(){

        ArrayList<League> list = datalayer.getLeague();

        for (League l : list)
            data.add(new StandingEntry(l.getID(), l.getStanding(), l.getName(), l.getMatches(), l.getGoalsDiff(), l.getPoints()) );
/*
        Stilling.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Standing"));
        HoldNavn.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Name"));
        Kampe.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Matches"));
        Goals.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("GoalDiff"));
        Pts.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Points"));
*/
        setCellValue(Stilling, "Standing");
        setCellValue(HoldNavn, "Name");
        setCellValue(Kampe, "Matches");
        setCellValue(Goals, "GoalDiff");
        setCellValue(Pts, "Points");

        tableView.setItems(data);

    }

    private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }

    public void addTeam() {
        String name = inputDialog("");
        League l = datalayer.createTeam(name);

        data.add(new StandingEntry(l.getID(), l.getStanding(), name, "0", "0", "0"));
        tableView.refresh();
        int index = tableView.getItems().size()-1;
        tableView.scrollTo(index);
    }

    public void editTeam() {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();

        String name = inputDialog(entry.getName());
        datalayer.editTeam(entry.getID(), name);

        entry.setName(name);
        data.set(index, entry);
        tableView.refresh();
    }

    public void deleteTeam() {
        ButtonType yesBut = new ButtonType("Ok");
        ButtonType noBut = new ButtonType("Annullere");

        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advarsel");
        alert.setHeaderText("Slet hold: \""+entry.getName()+"\"");
        alert.setContentText("Er du sikker på at du vil slette dette hold?");
        alert.getButtonTypes().setAll(yesBut, noBut);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesBut) {
            datalayer.deleteTeam(entry.getID());
            data.remove(index);
            tableView.refresh();
        }
    }

    private String inputDialog(String defaultValue) {
        TextInputDialog inputdialog = new TextInputDialog(defaultValue);
        inputdialog.setTitle("Opret Hold");
        inputdialog.setHeaderText("Indtast holdnavn");
        inputdialog.setContentText("Navn:");

        Optional<String> result = inputdialog.showAndWait();

        return result.orElse(defaultValue);
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
