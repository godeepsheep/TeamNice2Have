package Logic;

public class Team {
    private int id;
    private String name;

    public Team(int id, String Name) {
        this.id = id;
        this.name = Name;
    }

    public String getID() {
        return Integer.toString(id);
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[League: id=" + id +
                ", Name=" + name + "]";
    }
}