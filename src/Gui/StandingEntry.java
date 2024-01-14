package Gui;

import javafx.beans.property.SimpleStringProperty;

public class StandingEntry {

    private int Id;
    private SimpleStringProperty col1;
    private SimpleStringProperty col2;
    private SimpleStringProperty col3;
    private SimpleStringProperty col4;
    private SimpleStringProperty col5;

    public StandingEntry(int id, String c1,String c2,String c3,String c4,String c5)
    {
        setEntry(id,c1,c2,c3,c4,c5);
    }

    public StandingEntry(int id, String c1,String c2,String c3,String c4)
    {
        setEntry(id,c1,c2,c3,c4,"");
    }

    public void setEntry(int id, String c1,String c2,String c3,String c4,String c5) {
        this.Id = id;
        this.col1 = new SimpleStringProperty(c1);
        this.col2 = new SimpleStringProperty(c2);
        this.col3 = new SimpleStringProperty(c3);
        this.col4 = new SimpleStringProperty(c4);
        this.col5 = new SimpleStringProperty(c5);
    }

    public void setCol2(String value) {
        this.col2 = new SimpleStringProperty(value);
    }

    public int getID() {
        return Id;
    }

    public String getCol1() {
        return col1.get();
    }

    public String getCol2() {
        return col2.get();
    }

    public String getCol3() {
        return col3.get();
    }

    public String getCol4() {
        return col4.get();
    }

    public String getCol5() {
        return col5.get();
    }

}
