package Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBevent {

    private final DBconnect database = DBconnect.getInstance();
    private final Connection db = database.getConnection();


    //Gets the list of events for a given match from DB
    public ArrayList<Event> getEvents(int matchID) {
        try {
            ArrayList<Event> eventList = new ArrayList<>();

            Statement statement = db.createStatement();
            ResultSet resultSet = statement.executeQuery("EXEC getEvents "+ matchID);

            while (resultSet.next()) {
                int id = resultSet.getInt("Event_ID");
                int teamID = resultSet.getInt("Team_ID");
                String name = resultSet.getString("Event");
                String team = resultSet.getString("Team");
                String time = resultSet.getString("Time");
                String realtime = resultSet.getString("RealTime");

                Event event = new Event(id, name, teamID, team, time, realtime);
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

    //Create the event and if there is a goal
    public void setEvent(int eventType, int matchID, int teamID, String time)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                            "@matchID="+matchID+","+
                            "@teamID="+teamID+","+
                            "@time=\"00:"+time+"\"";

            Statement statement = db.createStatement();
            statement.executeUpdate("setEvent "+parameters);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Delete the event and the goal
    public void deleteEvent(int eventType, int matchID, int teamID)  {
        try {
            String parameters =
                    "@type="+eventType+","+
                            "@matchID="+matchID+","+
                            "@teamID="+teamID;

            Statement statement = db.createStatement();
            statement.executeUpdate("deleteEvent "+parameters);

            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}