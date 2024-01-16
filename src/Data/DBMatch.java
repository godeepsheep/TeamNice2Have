package Data;

import java.sql.*;
import java.util.ArrayList;

public class DBmatch {

    private final DBconnect database = DBconnect.getInstance();
    private final Connection db = database.getConnection();


    public ArrayList<Match> getMatches() {
        try {
            ArrayList<Match> matchList = new ArrayList<>();

            Statement statement = db.createStatement();
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

    public int startMatch(int teamID1, int teamID2)  {
        try {
            String parameters = "@t1="+teamID1+", @t2="+teamID2;

            Statement statement = db.createStatement();
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

            Statement statement = db.createStatement();
            statement.executeUpdate(sql);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}