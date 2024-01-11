package Logic;

public class Event {

    private String event, team, time, realtime;

    public Event(String event, String team, String time, String realtime) {
        this.event = event;
        this.team = team;
        this.time = time;
        this.realtime = realtime;
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