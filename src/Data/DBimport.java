package Data;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DBimport {

    private final Connection db = DBconnect.getInstance().getConnection();

    //Imports data into the DB for a given match
    public void importData(List<Event> data, int matchID)  {

        for (Event row : data)
            try {
                PreparedStatement preparedStatement = db.prepareStatement(
                        "INSERT INTO Event (TypeID, MatchID, TeamID, Time, RealTime) VALUES (?,?,?,?,?)");
                preparedStatement.setInt(1, row.getID());
                preparedStatement.setInt(2, matchID);
                preparedStatement.setInt(3, row.getTeamID());
                preparedStatement.setTime(4, toTime(row.getTime()));
                preparedStatement.setTime(5, toTime(row.getRealTime()));

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException | ParseException e) {
                System.out.println(e.getMessage());
            }
    }

    private Time toTime(String time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return new Time(formatter.parse(time).getTime());
    }

}