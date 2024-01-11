package Data;

import Logic.League;
import Logic.Match;
import java.sql.Connection;
import java.sql.DriverManager;
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


    public ArrayList<League> getLeague() {
        try {
            ArrayList<League> leagueList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("EXEC getLeague");

            while (resultSet.next()) {
                int standing = resultSet.getInt("Standing");
                String name = resultSet.getString("Name");
                int matches = resultSet.getInt("Matches");
                int goalsDiff = resultSet.getInt("GoalsDiff");
                int points = resultSet.getInt("Points");

                League league = new League(standing, name, matches, goalsDiff, points);
                leagueList.add(league);
            }

            statement.close();
            return leagueList;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Match> getMatches() {
        try {
            ArrayList<Match> matchList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("EXEC getMatches");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String time = resultSet.getString("Time");
                String team1 = resultSet.getString("Team1");
                int goals1 = resultSet.getInt("Goals1");
                String team2 = resultSet.getString("Team2");
                int goals2 = resultSet.getInt("Goals2");

                Match match = new Match(id, time, team1, goals1, team2, goals2);
                matchList.add(match);
            }

            statement.close();
            return matchList;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void setEvent(int eventType, int matchID, int teamID, String time)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                    "@matchID="+matchID+","+
                    "@teamID="+teamID+","+
                    "@time='"+time+"'";

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("setEvent "+parameters);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEvent(int eventType, int matchID, int teamID)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                    "@matchID="+matchID+","+
                    "@teamID="+teamID;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("deleteEvent "+parameters);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //følgende mangler at blive lavet:
    public void createTeam()  {
        /*
        try {
            String sql = "INSERT INTO Team VALUES ()";

            //Statement statement = connection.createStatement();
            //statement.executeUpdate("setEvent "+parameters);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
         */
    }

    public void deleteTeam()  {

    }

    public void renameTeam()  {

    }

    public void startMatch()  {

    }

    public void endMatch()  {

    }




    public void PrintAction(int ID, int Time, String Name){

        String sql = "INSERT INTO Event VALUES ("+ ID +"," + Time + "," + Name + ")";
        System.out.println(sql);

    }




}
