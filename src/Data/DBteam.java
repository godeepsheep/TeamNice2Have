package Data;

import java.sql.*;
import java.util.ArrayList;

public class DBteam {

    private final DBconnect database = DBconnect.getInstance();
    private final Connection db = database.getConnection();

    public ArrayList<Team> getTeams() {
        try {
            ArrayList<Team> teamList = new ArrayList<>();

            Statement statement = db.createStatement();
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

    public League createTeam(String name)  {
        try {
            CallableStatement callableStatement  = db.prepareCall("addTeam ?, 1");
            callableStatement.setString(1, name);
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

            Statement statement = db.createStatement();
            statement.executeUpdate(sql);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editTeam(int teamID, String name)  {
        try {
            PreparedStatement preparedStatement = db.prepareStatement("UPDATE Team SET Name = ? WHERE ID = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, teamID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}