package Logic;

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

    public static <T> void exportFile(Button exportButton, ArrayList<T> list, String fileName, String header) throws IOException {

        File dir = dirInputDialog(exportButton);

        if(dir != null) {
            StringBuilder data = new StringBuilder();
            data.append(header+"\n");

            for (T e : list)
                data.append(e.toString() + "\n");

            writeToFile(data, dir + "\\"+fileName+".csv");
            Desktop.getDesktop().open(dir);
            DialogBox.alert("Export færdig!", "Export");
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