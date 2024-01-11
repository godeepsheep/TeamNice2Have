package Gui;

import javafx.beans.property.SimpleStringProperty;

public class StandingEntry {

 public SimpleStringProperty Standing;
 public SimpleStringProperty Name;
 public SimpleStringProperty GoalDiff;
 public SimpleStringProperty Points;
 public SimpleStringProperty Matches;

    public StandingEntry(String _Standing,String _Name,String _Matches,String _GoalDiff,String _Points)
    {
        Standing = new SimpleStringProperty(_Standing);
        Name = new SimpleStringProperty(_Name);
        Matches = new SimpleStringProperty(_Matches);
        GoalDiff = new SimpleStringProperty(_GoalDiff);
        Points = new SimpleStringProperty(_Points);
    }

    public String getStanding() {
        return Standing.get();
    }

    public String getGoalDiff() {
        return GoalDiff.get();
    }

    public String getMatches() {
        return Matches.get();
    }

    public String getName() {
        return Name.get();
    }

    public String getPoints() {
        return Points.get();
    }
}
