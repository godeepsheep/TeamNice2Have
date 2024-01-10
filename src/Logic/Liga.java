package Logic;

import Data.DBconnection;
import Data.Datalayer;
import Data.Student;
import Logic.TeamScore;

import java.util.ArrayList;

public class Liga {

    public void show() {
    Datalayer data = new Datalayer();
    ArrayList<TeamScore> list = data.getTeamScore(1);

		for (TeamScore teamscore : list)
            System.out.println(teamscore);
    }
}
