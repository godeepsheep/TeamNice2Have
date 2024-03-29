package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    private static DBconnect instance; //Singleton instance
    private Connection connection;

    //Constructor to initialize the DB connection
    public DBconnect() {
        openConnection("eamvstudie23_dk_db_project", true);
    }

    private boolean openConnection(String databaseName, boolean online) {
        String connectionString;
        try {
            if(online) {
                connectionString =
                        "jdbc:sqlserver://"+
                        "mssql9.unoeuro.com:1433;" +
                        "databaseName=" + databaseName + ";" +
                        "user=eamvstudie23_dk;" +
                        "password=24dcD9FRBtwhkrpeHnay;" +
                        "encrypt=true;" +
                        "trustServerCertificate=true;";
            } else {
                connectionString =
                        "jdbc:sqlserver://"+
                        "localhost:1433;" +
                        "instanceName=SQLEXPRESS;" +
                        "databaseName=" + databaseName + ";" +
                        "integratedSecurity=true;" +
                        "trustServerCertificate=true;";
            }

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

    public Connection getConnection() {
        return connection;
    }

    public static DBconnect getInstance() {
        if (instance == null)
            instance = new DBconnect();

        return instance;
    }

}


