

public class Person {
    private String name;
    private String email;
    private String address;
    private String gender;

    //constructor
    public Person(String email, String name, String address, String gender) {
        setName(name);
        this.email = email;
        this.address = address;
        setGender(gender);
    }

    // the name setter contains the necessary field validation
    public void setName(String name) {
        int length = name.length();
        if (length > 30) {
            String nameOut = name.substring(0,30);
            this.name = nameOut;
        } else {
            this.name = name;
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // the gender setter contains the necessary field validation
    public void setGender(String gender) {
        gender = gender.toUpperCase();
        if (gender.charAt(0) == ('F')) {
            this.gender = gender;
        } else if
        (gender.charAt(0) == ('M')) {
            this.gender = gender;
        } else {
            this.gender = "Unspecified";
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Name= " + name + '\n' +
                "Email= " + email + '\n' +
                "Address= " + address + '\n' +
                "Gender= " + gender + '\n';
    }


}
