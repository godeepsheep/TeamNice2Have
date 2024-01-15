package Gui;

import Data.Datalayer;
import Logic.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
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


public class EventsController implements Initializable  {

    @FXML TableColumn<StandingEntry, String> time, event, team, realtime;
    @FXML TableView<StandingEntry> tableView;
    @FXML Button exportButton, importButton;

    private int matchID = 1;
    private Datalayer datalayer = new Datalayer();
    private ArrayList<Event> list;
    final ObservableList<StandingEntry> data = FXCollections.observableArrayList();

    public void AddEntry() {

        list = datalayer.getEvents(matchID);

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



    public void exportFile() throws IOException {
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
            List<Charset> charsets = List.of(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);

            for (Charset charset : charsets) {
                try {
                    List<String> lines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
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
