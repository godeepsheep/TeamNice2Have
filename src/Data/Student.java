package Data;

public class Student { // DBO: DataBærende Objekt (eng. DTO)
  private int id;
  private String lastName, firstName;
  private int semesterNo;
  
  public Student(int id, String lastName, String firstName, int semesterNo) {
    this.id = id;
    this.lastName = lastName;
    this.firstName = firstName;
    this.semesterNo = semesterNo;
  }
  
  @Override
  public String toString() {
    return "[Data.Student: id=" + id +
             ", lastName=" + lastName +
            ", firstName=" + firstName +
           ", semesterNo=" + semesterNo + "]";
  }
}
