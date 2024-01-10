package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBconnection {
  private Connection connection;

  public DBconnection() {
    //loading af jdbc er ikke nødvendigt mere: https://docs.oracle.com/javase/8/docs/api/java/sql/DriverManager.html
    //openConnection("skoleDB", false);
    openConnection("eamvstudie23_dk_db_project", true);
  }

  private boolean openConnection(String databaseName, boolean online) {
      String connectionString = "jdbc:sqlserver://";
    try {
        if(online) {
            connectionString +=
                    "mssql9.unoeuro.com:1433;" +
                    "databaseName=" + databaseName + ";" +
                    "user=eamvstudie23_dk;" +
                    "password=24dcD9FRBtwhkrpeHnay;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true;";
        } else {
            connectionString +=
                    "localhost:1433;" +
                    "instanceName=SQLEXPRESS;" +
                    "databaseName=" + databaseName + ";" +
                    "integratedSecurity=true;" + // bruger Windows credentials
                    "trustServerCertificate=true;"; // afht. SSL forbindelse
        }
      System.out.println(connectionString);
      System.out.println("Connecting to database...");
      
      connection = DriverManager.getConnection(connectionString);
      System.out.println("Connected to database");
      
      return true;
    }

    catch (SQLException e) {
      System.out.println("Could not connect to database: " + databaseName);
      System.out.println(e.getMessage());
      
      return false;
    }

  }





/*
  public Student getStudentById(int id) {
    ArrayList<Student> list = getStudentsByWhereClause("StudentID=" + id);
    
    if (list.size() > 0)
      return list.get(0);
    else
      return null;
  }

  public ArrayList<Student> getStudentsBySemesterNo(int semesterNo) {
    return getStudentsByWhereClause("Grade=" + semesterNo);
  }
  
  public ArrayList<Student> getAllStudents() {
    return getStudentsByWhereClause("0=0");
  }
  
  private ArrayList<Student> getStudentsByWhereClause(String whereClause) {
    try {
      ArrayList<Student> students = new ArrayList<>();
      
      String sql = "SELECT * FROM Student WHERE " + whereClause;
      
      System.out.println(sql);
      
      Statement statement = connection.createStatement();
      
      ResultSet resultSet = statement.executeQuery(sql);
      
      // iteration starter 'before first'
      while (resultSet.next()) {
        int id = resultSet.getInt("StudentID");
        String lastName = resultSet.getString("LastName");
        String firstName = resultSet.getString("FirstName");
        int semesterNo = resultSet.getInt("Grade");

        Student student = new Student(id, lastName, firstName, semesterNo);
        
        students.add(student);
      }
      
      statement.close();
      
      return students;
    }
    catch (SQLException e) {
      System.out.println("Error: Cold not get students");
      System.out.println(e.getMessage());
      
      return null;
    }
  }
  */
 
}


