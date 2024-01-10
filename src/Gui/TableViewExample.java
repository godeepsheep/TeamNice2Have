package Gui;


    import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.value.ObservableValue;
    import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

    public class TableViewExample extends Application {

        public static class Person {
            private final SimpleStringProperty name;

            public Person(String name) {
                this.name = new SimpleStringProperty(name);
            }

            public String getName() {
                return name.get();
            }

            public void setName(String name) {
                this.name.set(name);
            }

            public ObservableValue<String> nameProperty() {

                return null;
            }
        }

        @Override
        public void start(Stage primaryStage) {
            // Create a TableView
            TableView<Person> tableView = new TableView<>();

            // Create a TableColumn for names
            TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

            // Add the column to the TableView
            tableView.getColumns().add(nameColumn);

            // Create some sample data
            ObservableList<Person> data = FXCollections.observableArrayList(
                    new Person("John"),
                    new Person("Jane"),
                    new Person("Bob")
            );

            // Add data to the TableView
            tableView.setItems(data);

            // Create the scene and show the stage
            Scene scene = new Scene(tableView, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX TableView Example");
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

