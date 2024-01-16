package Data;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DBimport {

    private final DBconnect database = DBconnect.getInstance();
    private final Connection db = database.getConnection();

    public void importData(List<String[]> data, int matchID)  {

        for (String[] row : data)
            try {
                PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO Event (TypeID, MatchID, TeamID, Time, RealTime) VALUES (?,?,?,?,?)");
                preparedStatement.setInt(1, Integer.parseInt(row[1]));
                preparedStatement.setInt(2, matchID);
                preparedStatement.setInt(3, Integer.parseInt(row[3]));
                preparedStatement.setTime(4, toTime("00:"+row[0]));
                preparedStatement.setTime(5, toTime(row[5]));

                preparedStatement.executeUpdate();
                preparedStatement.close();

            }
            catch (SQLException | ParseException e) {
                System.out.println(e.getMessage());
            }
    }

    private Time toTime(String time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return new Time(formatter.parse(time).getTime());
    }

}