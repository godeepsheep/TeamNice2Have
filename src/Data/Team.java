package Data;

public class Team {
    private int id;
    private String name;

    //Constructs a Team object
    public Team(int id, String Name) {
        this.id = id;
        this.name = Name;
    }

    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}