package Logic;

public class TeamScore {
    private int id;
    private String Name;

    public TeamScore(int id, String Name) {
        this.id = id;
        this.Name = Name;
    }

    @Override
    public String toString() {
        return "[Data.TeamScore: id=" + id +
                ", Name=" + Name + "]";
    }
}