package Data;

public class EventType {
    //Attributes
    private int TypeID;
    private String name;

    //Constructor
    private EventType() {
        name = "";
    }

    //Methods for ensuring the name is below 50 chars.
    private void setName(String newName) {
        if (newName.length() <=50) {
            name = newName;
        } else {
            //What should we do if its too long?
            System.out.println("Name exceeds 50 chars");
        }
    }
    private String getName() {
        return name;
    }
}
