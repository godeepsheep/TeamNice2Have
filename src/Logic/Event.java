package Logic;

public class Event {

    private String event, team, time, realtime;
    private int id, teamID;

    public Event(int id, String event, int teamID, String team, String time, String realtime) {
        this.id = id;
        this.teamID = teamID;
        this.event = event;
        this.team = team;
        this.time = time;
        this.realtime = realtime;
    }
    public int getID() {
        return id;
    }
    public int getTeamID() {
        return teamID;
    }
    public String getName() {
        return event;
    }
    public String getTeam() {
        return team;
    }
    public String getTime() {
        return time;
    }
    public String getRealTime() {
        return realtime;
    }

    @Override
    public String toString() {
        return "[Match: Event=" + event +
                ", Team=" + team +
                ", Time=" + time +
                ", RealTime=" + realtime + "]";
    }
}