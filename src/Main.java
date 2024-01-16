import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{

		primaryStage.getIcons().add(new Image("/Gui/images/ball.png"));

		Parent root = FXMLLoader.load(getClass().getResource("Gui/fxml/SidebarNavigation.fxml"));

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.getStylesheets().add(getClass().getResource("Gui/css/application.css").toExternalForm());

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
