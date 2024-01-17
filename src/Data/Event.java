package Data;

public class Event {

    private String event, team, time, realtime;
    private int id, teamID;

    //Constructs Event object
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
    public String getName() {
        return event;
    }
    public String getTeam() {
        return team;
    }
    public int getTeamID() {
        return teamID;
    }
    public String getTime() {
        return time;
    }
    public String getRealTime() {
        return realtime;
    }

    @Override
    public String toString() {
        return time+";"+id+";"+event+";"+teamID+";"+team+";"+realtime;
    }
}