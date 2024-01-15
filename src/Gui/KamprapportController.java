package Gui;

import Data.Datalayer;
import Logic.Event;
import Logic.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
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
import java.util.List;
import java.util.ResourceBundle;


public class KamprapportController implements Initializable {

    @FXML TableColumn<StandingEntry, String> time, team1, goal1, team2, goal2; //kampe
    @FXML TableColumn<StandingEntry, String> timeEvent, event, team, realtime; //events
    @FXML TableView<StandingEntry> tableView, tableViewEvents;
    @FXML Button exportButton, importButton;
    @FXML Tab tabReport;

    int matchID = -1;
    Datalayer datalayer = new Datalayer();
    ArrayList<Match> list = datalayer.getMatches();

    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();
    final ObservableList<StandingEntry> dataEvent = FXCollections.observableArrayList();

    public void AddEntry() {



        for (Match m : list)
           data.add(new StandingEntry(m.getID(), m.getTime(), m.getTeam1(), m.getGoals1(), m.getTeam2(), m.getGoals2()));

        setCellValue(time, "col1");
        setCellValue(team1, "col2");
        setCellValue(goal1, "col3");
        setCellValue(team2, "col4");
        setCellValue(goal2, "col5");

        tableView.setItems(data);
        tableView.getSelectionModel().selectFirst();

    }


    public void setEventTable() {
        ArrayList<Event> list = datalayer.getEvents(matchID);
        dataEvent.clear();

        for (Event e : list)
            dataEvent.add(new StandingEntry(0, e.getTime(), e.getName(), e.getTeam(), e.getRealTime()));

        setCellValue(timeEvent, "col1");
        setCellValue(event, "col2");
        setCellValue(team, "col3");
        setCellValue(realtime, "col4");

        tableViewEvents.setItems(dataEvent);;
    }

    private void setCellValue(TableColumn<StandingEntry, String> column, String name) {
        column.setCellValueFactory(new PropertyValueFactory<StandingEntry,String>(name));
    }


    public EventHandler<javafx.event.Event> tabReport() {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        matchID = entry.getID();
        setEventTable();
        return null;
    }

    public void exportFile() throws IOException {
        /*
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Vælg mappe til eksport");

        Stage primaryStage = (Stage) exportButton.getScene().getWindow();
        File dir = directoryChooser.showDialog(primaryStage);

        if(dir != null) {
            StringBuilder data = new StringBuilder();
            data.append("Tid;ID;Handling;HoldID;Hold;Realtid\n");

            for (Event e : list)
                data.append(e.getTime() + ";" + e.getID() + ";" + e.getName() + ";" + e.getTeamID() + ";" + e.getTeam() + ";" + e.getRealTime() + "\n");

            writeToFile(data, dir + "\\Match_events.csv");
            Desktop.getDesktop().open(new File(dir.toString()));
            alertBox("Export færdig!", "Export");
        }
        */

    }

    public void writeToFile(StringBuilder data, String filePath) {
        Path path = Paths.get(filePath);

        try {
            Files.write(path, data.toString().getBytes(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void importFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vælg import CSV fil!");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage primaryStage = (Stage) importButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);

        if(file != null) {
            Path filePath = Paths.get(file.toString());
            java.util.List<Charset> charsets = java.util.List.of(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);

            for (Charset charset : charsets) {
                try {
                    java.util.List<String> lines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
                    List<String[]> data = new ArrayList<>();

                    for (int i=1; i<lines.size(); i++)
                        data.add(lines.get(i).split(";"));

                    datalayer.importData(data, matchID);

                    alertBox("Import færdig!", "Import");
                    AddEntry();
                    int index = tableView.getItems().size();
                    tableView.scrollTo(index);

                    break;

                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }


    private void alertBox(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddEntry();
    }

}
