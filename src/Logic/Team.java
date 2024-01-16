package Logic;

import Data.*;
import Gui.DialogBox;
import Gui.StandingEntry;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Objects;
import java.util.Optional;

public class Team extends DialogBox {

    public static void add(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, DBteam teamDB) {
        League l;
        String name = textInputDialog("");

        if(!name.isEmpty()) {
            l = teamDB.createTeam(name);

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
    }

    public static void edit(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, DBteam datalayer) {
        StandingEntry entry = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();

        String name = textInputDialog(entry.getCol2());

        if(!name.isEmpty()) {
            datalayer.editTeam(entry.getID(), name);

            //ændre navnet i gui
            entry.setCol2(name);
            data.set(index, entry);
            tableView.refresh();
        }
    }

    public static void delete(TableView<StandingEntry> tableView, ObservableList<StandingEntry> data, DBteam datalayer) {
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

}