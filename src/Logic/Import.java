package Logic;

import Data.DBimport;
import Data.Event;
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
            List<Charset> charsets = List.of(StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);

            for (Charset charset : charsets) {
                try {
                    List<String> lines = Files.readAllLines(filePath, charset);
                    List<Event> data = new ArrayList<>();

                    for (int i=1; i<lines.size(); i++) {
                        String[] col = lines.get(i).split(";");
                        data.add(new Event(
                                Integer.parseInt(col[1]), //id
                                col[2], //event
                                Integer.parseInt(col[3]), //teamID
                                col[4], //Team
                                "00:"+col[0], //time
                                col[5]) //real time
                        );
                     }

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