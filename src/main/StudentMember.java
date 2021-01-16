public class StudentMember extends Member  {

    public StudentMember(String email, String name, String address, String gender, float height, float startWeight, String chosenPackage, String studentid, String collegeName) {
        super(email, name, address, gender, height, startWeight, chosenPackage);
        this.studentid = studentid;
        this.collegeName = collegeName;
    }

    private String studentid;
    private String collegeName;
}
