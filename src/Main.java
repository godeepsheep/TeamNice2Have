import Data.DBconnection;
import Data.Datalayer;
import Data.Student;
import Logic.Liga;
import Logic.TeamScore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;


public class Main extends Application {

	static Datalayer db = new Datalayer();

	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Gui/Interface.fxml"));
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
	
	public static void main(String[] args) {


		Liga liga = new Liga();
		liga.show();


		launch(args);
	}


	public static void WriteActionsToDB(int id, int time, String Name){
		db.PrintAction(id, time, Name);
	}

}
