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

	
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("Gui/css/application.css").toExternalForm());

		primaryStage.show();
	}


	public static void main(String[] args) {

/*

		ArrayList<League> list1 = db.getLeague();
		System.out.println("\n\nGetLeague");
		for (League l : list1)
			System.out.println(l);


		ArrayList<Match> list2 = db.getMatches();
		System.out.println("\n\nGetMatches");
		for (Match m : list2)
			System.out.println(m);


		ArrayList<Event> list3 = db.getEvents(1);
		System.out.println("\n\nGetEvents");
		for (Event e : list3)
			System.out.println(e);

		ArrayList<Team> list4 = db.getTeams();
		System.out.println("\n\nGetTeams");
		for (Team t : list4)
			System.out.println(t);
*/

		/*Starter en kamp med teamid 4 og teamid 8*/
		//int matchID = db.startMatch(4, 8);
		//System.out.println(matchID);

		/*slutter en kamp med id 30*/
		//db.endMatch(30);


		/*sætter event. eventType : 1 er mål 2 er udvisning*/
		//db.setEvent(2,1,11, "00:00:11");

		/*sletter event. eventType : 1 er mål 2 er udvisning*/
		//db.deleteEvent(1,1,11);


		//db.createTeam("bingoholdet2");
		//db.editTeam(17, "bingoholdet_test");

		//db.deleteTeam(17);

		launch(args);
	}


	public static void WriteActionsToDB(int id, int time, String Name){
		db.PrintAction(id, time, Name);
	}

}
