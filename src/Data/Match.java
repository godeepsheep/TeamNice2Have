package Data;

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

    public int getID() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTeam1() {
        return team1;
    }

    public String getGoals1() {
        return Integer.toString(goals1);
    }

    public String getTeam2() {
        return team2;
    }

    public String getGoals2() {
        return Integer.toString(goals2);
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