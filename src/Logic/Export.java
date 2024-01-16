package Logic;

import Data.Event;
import Data.League;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Export {

    public static void league(Button exportButton, ArrayList<League> list) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Vælg mappe til eksport");

        Stage primaryStage = (Stage) exportButton.getScene().getWindow();
        File dir = directoryChooser.showDialog(primaryStage);

        if(dir != null) {
            StringBuilder data = new StringBuilder();
            data.append("Stilling;Holdnavn;Kampe;Mål;Point\n");

            for (League l : list)
                data.append(
                        l.getStanding() + ";" +
                                l.getName() + ";" +
                                l.getMatches() + ";" +
                                l.getGoalsDiff() + ";" +
                                l.getPoints() + "\n"
                );

            Export.writeToFile(data, dir + "\\Standing_league.csv");
            Desktop.getDesktop().open(new File(dir.toString()));
            Export.alertBox("Export færdig!", "Export");

        }
    }

    public static void events(Button exportButton, ArrayList<Data.Event> list) throws IOException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Vælg mappe til eksport");

        Stage primaryStage = (Stage) exportButton.getScene().getWindow();
        File dir = directoryChooser.showDialog(primaryStage);

        if(dir != null) {
            StringBuilder data = new StringBuilder();
            data.append("Tid;ID;Handling;HoldID;Hold;Realtid\n");

            for (Event e : list)
                data.append(
                        e.getTime() + ";" +
                                e.getID() + ";" +
                                e.getName() + ";" +
                                e.getTeamID() + ";" +
                                e.getTeam() + ";" +
                                e.getRealTime() + "\n"
                );

            Export.writeToFile(data, dir + "\\Match_events.csv");
            Desktop.getDesktop().open(new File(dir.toString()));
            Export.alertBox("Export færdig!", "Export");
        }


    }

    public static void alertBox(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void writeToFile(StringBuilder data, String filePath) {
        Path path = Paths.get(filePath);

        try {
            Files.write(path, data.toString().getBytes(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}