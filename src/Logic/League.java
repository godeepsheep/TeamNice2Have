package Logic;

public class League {
    private int standing, matches, goalsDiff, points;
    private String name;

    public League(int standing, String Name, int matches, int goalsDiff, int points) {
        this.standing = standing;
        this.name = Name;
        this.matches = matches;
        this.goalsDiff = goalsDiff;
        this.points = points;
    }

    public String getStanding() {
        return Integer.toString(standing);
    }
    public String getName() {
        return name;
    }

    public String getMatches() {
        return Integer.toString(matches);
    }

    public String getGoalsDiff() {
        return Integer.toString(goalsDiff);
    }

    public String getPoints() {
        return Integer.toString(points);
    }

    @Override
    public String toString() {
        return "[League: standing=" + standing +
                ", Name=" + name +
                ", Matches=" + matches +
                ", GoalsDiff=" + goalsDiff +
                ", Points=" + points + "]";
    }
}