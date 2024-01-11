package Logic;

public class Match {
    private int id, goals1, goals2;
    private String time, team1, team2;

    public Match(int id, String time, String team1, int goals1, String team2, int goals2) {
        this.id = id;
        this.time = time;
        this.team1 = team1;
        this.goals1 = goals1;
        this.team2 = team2;
        this.goals2 = goals2;
    }

    public String getId() {
        return Integer.toString(id);
    }
    public String getTeam1() {
        return team1;
    }
    public int getGoals1() {
        return goals1;
    }
    public String getTeam2() {
        return team2;
    }
    public int getGoals2() {
        return goals2;
    }
    @Override
    public String toString() {
        return "[Match: id=" + id +
                ", Team1=" + team1 +
                ", Mål=" + goals1 +
                ", Team2=" + team2 +
                ", Mål=" + goals2 + "]";
    }
}