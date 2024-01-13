package Logic;

public class League {
    private int id, standing, matches, goalsDiff, points;
    private String name;

    public League(int standing, int id, String name, int matches, int goalsDiff, int points) {
        this.standing = standing;
        this.id = id;
        this.name = name;
        this.matches = matches;
        this.goalsDiff = goalsDiff;
        this.points = points;
    }

    public String getStanding() {
        return Integer.toString(standing);
    }

    public int getID() {
        return id;
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