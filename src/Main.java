import Data.DBconnection;
import Data.Datalayer;
import Gui.SidebarNavigationController;
import Logic.League;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

import java.util.ArrayList;


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
		Parent root = FXMLLoader.load(getClass().getResource("Gui/SidebarNavigation.fxml"));
		primaryStage.initStyle(StageStyle.UNDECORATED);

		root.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});

		root.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX() - x);
			primaryStage.setY(event.getScreenY() - y);
		});

		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}


	public static void main(String[] args) {

		Datalayer data = new Datalayer();
		ArrayList<League> list = data.getLeague();

		for (League league : list)
			System.out.println(league);

		launch(args);
	}


	public static void WriteActionsToDB(int id, int time, String Name){
		db.PrintAction(id, time, Name);
	}

}
