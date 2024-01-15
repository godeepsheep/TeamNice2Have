import Data.Datalayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {

	static Datalayer db = new Datalayer();

	/* @Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();

			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Gui/SidebarNavigation.fxml"));
			Parent root = fxmlLoader.load();

			// Access the controller from the FXMLLoader
			SidebarNavigationController sidebarController = fxmlLoader.getController();

			// Pass the Datalayer instance to the controller
			sidebarController.setDatalayer(db);
			//FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Gui/SidebarNavigation.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("Gui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
*/
	double x,y = 0;
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




	public static void WriteActionsToDB(int id, int time, String Name){
		db.PrintAction(id, time, Name);
	}

}
