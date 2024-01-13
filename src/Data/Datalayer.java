package Data;

import Logic.League;
import Logic.Match;
import Logic.Event;
import Logic.Team;

import java.sql.*;
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
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int matches = resultSet.getInt("Matches");
                int goalsDiff = resultSet.getInt("GoalsDiff");
                int points = resultSet.getInt("Points");

                League league = new League(standing, id, name, matches, goalsDiff, points);
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
                    "@time=\"00:"+time+"\"";

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

    public League createTeam(String name)  {
        try {
            //PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Team (Name, LeagueID) VALUES (?, 1)");
            CallableStatement callableStatement  = connection.prepareCall("addTeam ?, 1");
            callableStatement.setString(1, name);
            //preparedStatement.executeUpdate();
            callableStatement.executeQuery();
            ResultSet resultSet = callableStatement.getResultSet();
            League league = null;

            while (resultSet.next()) {
                int standing = resultSet.getInt("Standing");
                int id = resultSet.getInt("ID");
                league = new League(standing, id, name, 0, 0, 0);
            }

            callableStatement.close();
            return league;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteTeam(int teamID)  {
        try {
            String sql = "DELETE FROM Team WHERE ID = "+teamID;

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editTeam(int teamID, String name)  {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Team SET Name = ? WHERE ID = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, teamID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public void PrintAction(int ID, int Time, String Name){

        String sql = "INSERT INTO Event VALUES ("+ ID +"," + Time + "," + Name + ")";
        System.out.println(sql);

    }




}
