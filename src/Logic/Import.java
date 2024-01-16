package Logic;

import Data.DBimport;
import Gui.DialogBox;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Import extends DialogBox {

    public static void importFile(Button importButton, DBimport db, int matchID) {

        File file = fileInputDialog(importButton);

        if(file != null) {
            Path filePath = Paths.get(file.toString());
            java.util.List<Charset> charsets = java.util.List.of(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);

            for (Charset charset : charsets) {
                try {
                    java.util.List<String> lines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
                    List<String[]> data = new ArrayList<>();

                    for (int i=1; i<lines.size(); i++)
                        data.add(lines.get(i).split(";"));

                    db.importData(data, matchID);

                    alert("Import færdig!", "Import");
                    break;

                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }
}