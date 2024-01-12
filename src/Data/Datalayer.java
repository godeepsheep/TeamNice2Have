package Data;

import Logic.League;
import Logic.Match;
import Logic.Event;
import Logic.Team;
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
                        "integratedSecurity=true;" +
                        "trustServerCertificate=true;";
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

    public ArrayList<Event> getEvents(int matchID) {
        try {
            ArrayList<Event> eventList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("EXEC getEvents "+ matchID);

            while (resultSet.next()) {
                String name = resultSet.getString("Event");
                String team = resultSet.getString("Team");
                String time = resultSet.getString("Time");
                String realtime = resultSet.getString("RealTime");

                Event event = new Event(name, team, time, realtime);
                eventList.add(event);
            }

            statement.close();
            return eventList;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public ArrayList<Team> getTeams() {
        try {
            ArrayList<Team> teamList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Team");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");

                Team team = new Team(id, name);
                teamList.add(team);
            }

            statement.close();
            return teamList;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*create the event and if there is a goal*/
    public void setEvent(int eventType, int matchID, int teamID, String time)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                    "@matchID="+matchID+","+
                    "@teamID="+teamID+","+
                    "@time='"+time+"'";

            Statement statement = connection.createStatement();
            statement.executeUpdate("setEvent "+parameters);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*delete the event and the goal*/
    public void deleteEvent(int eventType, int matchID, int teamID)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                    "@matchID="+matchID+","+
                    "@teamID="+teamID;

            Statement statement = connection.createStatement();
            statement.executeUpdate("deleteEvent "+parameters);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int startMatch(int teamID1, int teamID2)  {
        try {
            String parameters = "@t1="+teamID1+", @t2="+teamID2;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("createMatch "+parameters);

            int matchID = -1;
            while (resultSet.next())
                matchID = resultSet.getInt(1);

            statement.close();
            return matchID;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public void endMatch(int matchID)  {
        try {
            String sql = "UPDATE [Match] SET TimeEnd = GETDATE() WHERE ID = "+matchID;

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*
    //følgende mangler at blive lavet:
    public void createTeam()  {
        try {
            String sql = "INSERT INTO Team VALUES ()";

            Statement statement = connection.createStatement();
            statement.executeQuery("setEvent "+parameters);
            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
*/
    public void deleteTeam()  {

    }

    public void renameTeam()  {

    }





    public void PrintAction(int ID, int Time, String Name){

        String sql = "INSERT INTO Event VALUES ("+ ID +"," + Time + "," + Name + ")";
        System.out.println(sql);

    }




}
