package Data;

import java.sql.*;
import java.util.ArrayList;

public class DBleague {

    private final DBconnect database = DBconnect.getInstance();
    private final Connection db = database.getConnection();

    public ArrayList<League> getLeague() {
        try {
            ArrayList<League> leagueList = new ArrayList<>();

            Statement statement = db.createStatement();
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

}