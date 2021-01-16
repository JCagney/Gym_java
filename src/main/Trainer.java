public class Trainer extends Person  {
    private String speciality;

    public Trainer(String email, String name, String address, String gender, String speciality){
        super(email, name, address, gender);
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void addAssessment() {
        System.out.println("Please enter member email:");

    }


}
