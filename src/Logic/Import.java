package Logic;

import Data.Datalayer;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Import {

    public static void importFile(Button importButton, Datalayer datalayer, int matchID) {

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

                    Export.alertBox("Import færdig!", "Import");
                    break;

                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }
}