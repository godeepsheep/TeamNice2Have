import Data.Datalayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	static Datalayer db = new Datalayer();

	@Override
	public void start(Stage primaryStage) throws Exception{

		primaryStage.getIcons().add(new Image("/Gui/images/ball.png"));

		//String fxmlfile = "Interface";
		String fxmlfile = "SidebarNavigation";
		//String fxmlfile = "Stilling";
		//String fxmlfile = "Kamprapport";
		//String fxmlfile = "Events";
		Parent root = FXMLLoader.load(getClass().getResource("Gui/fxml/"+fxmlfile+".fxml"));

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
