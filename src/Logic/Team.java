package Logic;

import Data.Datalayer;
import Data.Event;
import Data.League;
import Gui.StandingEntry;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
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
import java.util.Optional;

public class Team {

    public static void add(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, Datalayer datalayer) {
        String name = inputDialog("");
        League l = datalayer.createTeam(name);

        int index = tableView.getItems().size();
        //bruger index til standing i stedet for data fra databasen, for at undgå at to hold har samme standing
        data.add(new StandingEntry(
                l.getID(),
                Integer.toString(index),
                name,
                "0",
                "0",
                "0")
        );

        tableView.refresh();
        tableView.scrollTo(index);
    }

    public static void edit(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, Datalayer datalayer) {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();

        String name = inputDialog(entry.getCol2());
        datalayer.editTeam(entry.getID(), name);

        //ændre navnet i gui
        entry.setCol2(name);
        data.set(index, entry);
        tableView.refresh();
    }

    public static void delete(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, Datalayer datalayer) {
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

    private static String inputDialog(String defaultValue) {
        TextInputDialog inputdialog = new TextInputDialog(defaultValue);
        inputdialog.setTitle("Opret Hold");
        inputdialog.setHeaderText("Indtast holdnavn");
        inputdialog.setContentText("Navn:");

        Optional<String> result = inputdialog.showAndWait();

        return result.orElse(defaultValue);
    }
}