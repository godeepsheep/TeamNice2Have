package Gui;

import Data.Datalayer;
import Logic.League;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class Stilling implements Initializable  {

    @FXML TableColumn<StandingEntry, String> Stilling, HoldNavn, Kampe, Goals, Pts;
    @FXML TableView<StandingEntry> tableView;
    @FXML Button exportButton;
    Datalayer datalayer = new Datalayer();
    ArrayList<League> list = datalayer.getLeague();
    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry() {

        for (League l : list)
            data.add(new StandingEntry(l.getID(), l.getStanding(), l.getName(), l.getMatches(), l.getGoalsDiff(), l.getPoints()) );
/*
        Stilling.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Standing"));
        HoldNavn.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Name"));
        Kampe.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Matches"));
        Goals.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("GoalDiff"));
        Pts.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>("Points"));
*/
        setCellValue(Stilling, "col1");
        setCellValue(HoldNavn, "col2");
        setCellValue(Kampe, "col3");
        setCellValue(Goals, "col4");
        setCellValue(Pts, "col5");

        tableView.setItems(data);

    }

    private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }

    public void addTeam() {
        String name = inputDialog("");
        League l = datalayer.createTeam(name);

        int index = tableView.getItems().size()-1;
        //bruger index til standing i stedet for data fra databasen, for at undgå at to hold har samme standing
        data.add(new StandingEntry(l.getID(), Integer.toString(index), name, "0", "0", "0"));

        tableView.refresh();
        tableView.scrollTo(index);
    }

    public void editTeam() {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();

        String name = inputDialog(entry.getCol2());
        datalayer.editTeam(entry.getID(), name);

        //ændre navnet i gui
        entry.setCol2(name);
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
        alert.setHeaderText("Slet hold: \""+entry.getCol2()+"\"");
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

    public void exportFile() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Vælg mappe til eksport");

        Stage primaryStage = (Stage) exportButton.getScene().getWindow();
        File dir = directoryChooser.showDialog(primaryStage);

        if(dir != null) {
            StringBuilder data = new StringBuilder();
            data.append("Stilling;Holdnavn;Kampe;Mål;Point\n");

            for (League l : list)
                data.append(l.getStanding() + ";" + l.getName() + ";" + l.getMatches() + ";" + l.getGoalsDiff() + ";" + l.getPoints() + "\n");

            writeToFile(data, dir + "\\Standing_league.csv");
            Desktop.getDesktop().open(new File(dir.toString()));
            alertBox("Export færdig!", "Export");

        }
    }

    private void alertBox(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void writeToFile(StringBuilder data, String filePath) {
        Path path = Paths.get(filePath);

        try {
            Files.write(path, data.toString().getBytes(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            System.out.println(e);
        }
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
