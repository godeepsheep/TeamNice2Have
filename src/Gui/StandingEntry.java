package Gui;

import javafx.beans.property.SimpleStringProperty;

public class StandingEntry {

    private int Id;
    private SimpleStringProperty Standing;
    private SimpleStringProperty Name;
    private SimpleStringProperty GoalDiff;
    private SimpleStringProperty Points;
    private SimpleStringProperty Matches;

    public StandingEntry(int id, String Standing,String Name,String Matches,String GoalDiff,String Points)
    {
        this.Id = id;
        this.Standing = new SimpleStringProperty(Standing);
        this.Name = new SimpleStringProperty(Name);
        this.Matches = new SimpleStringProperty(Matches);
        this.GoalDiff = new SimpleStringProperty(GoalDiff);
        this.Points = new SimpleStringProperty(Points);
    }

    public String getStanding() {
        return Standing.get();
    }

    public int getID() {
        return Id;
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

    public void setName(String Name) {
        this.Name = new SimpleStringProperty(Name);
    }
}
