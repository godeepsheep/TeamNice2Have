package Data;

import Logic.TeamScore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Datalayer {
    private Connection connection;

    public Datalayer() {
        openConnection("eamvstudie23_dk_db_project", true);
    }

    private boolean openConnection(String databaseName, boolean online) {
        String connectionString = "jdbc:sqlserver://";
        try {
            if (online) {
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
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + databaseName);
            System.out.println(e.getMessage());

            return false;
        }

    }

    public ArrayList<TeamScore> getTeamScore(int ligaID) {
        try {
            ArrayList<TeamScore> teamscores = new ArrayList<>();

            String sql = "SELECT * FROM Team";
            System.out.println(sql);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");

                TeamScore teamscore = new TeamScore(id, name);
                teamscores.add(teamscore);
            }

            statement.close();

            return teamscores;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

            return null;
        }
    }

}
