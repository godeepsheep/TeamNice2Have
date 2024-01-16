package Logic;

import Data.Event;
import Data.League;
import Gui.DialogBox;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Export extends DialogBox {

    public static void league(Button exportButton, ArrayList<League> list) throws IOException {

        File dir = dirInputDialog(exportButton);

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

            writeFile(data, dir, "Standing_league");
        }
    }

    private static void writeFile(StringBuilder data, File dir, String name) throws IOException {
        Export.writeToFile(data, dir + "\\"+name+".csv");
        Desktop.getDesktop().open(dir);
        DialogBox.alert("Export færdig!", "Export");
    }

    public static void events(Button exportButton, ArrayList<Data.Event> list) throws IOException {

        File dir = dirInputDialog(exportButton);

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

            writeFile(data, dir, "Match_events");
        }
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