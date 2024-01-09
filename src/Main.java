import Data.DBconnection;
import Data.Student;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;


public class Main extends Application {



	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("Gui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		DBconnection db = new DBconnection();

		ArrayList<Student> list = db.getStudentsBySemesterNo(1);

		for (Student student : list)
			System.out.println(student);

		Student s = db.getStudentById(1000);

		System.out.println(s);

		ArrayList<Student> all = db.getAllStudents();

		for (Student student : all)
			System.out.println(student);


		launch(args);
	}
}
