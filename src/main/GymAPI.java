import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class GymAPI {
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;

    //Constructor
    public GymAPI() {
        this.members = new ArrayList<>();
        this.trainers = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    public int numberOfMembers() {
        return members.size();
    }

    public int numberOfTrainers() {
        return trainers.size();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Trainer> getTrainers() { return trainers; }


    public boolean isValidMemberIndex(int index) {
        //declare size of member array as a variable
        int size = members.size();
        //check if the members array, if so return false
        if (size == 0) {
            return false;
         //check if the index passed in is between 0 and the last index of the array, if so return true, else return false
        } else if
        ((size >= (index + 1)) && (index >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidTrainerIndex(int index) {
        //declare size of trainer array as a variable
        int size = trainers.size();
        //if the array is empty, return false
        if (size == 0) {
            return false;
            //check if the index passed in is between 0 and the last index of the array, if so return true, else return false
        } else if
        ((size >= (index + 1)) && (index >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    public Member searchMembersByEmail(String emailEntered) {
        //declare a variable as null
        Member returnedMember = null;
        //If there are members in the array, use a for loop to check each member email for a match to the email Entered. Return the match
        if (getMembers().size() > 0) {
            for (Member member : getMembers()) {
                if (emailEntered.equals(member.getEmail())) {
                    returnedMember = member;
                }
            }
        }
        return returnedMember;
    }

    public Trainer searchTrainersByEmail(String emailEntered) {
        //declare a variable as null
        Trainer returnedTrainer = null;
        //If there are trainers in the array, use a for loop to check each trainer email for a match to the email Entered. Return the match
        if (getTrainers().size() > 0) {
            for (Trainer trainer : getTrainers()) {
                if (emailEntered.equals(trainer.getEmail())) {
                    returnedTrainer = trainer;
                }
            }
        }
        return returnedTrainer;
    }

    /*This method declares an ArrayList of members to return from the search, then uses a for loop to iterate through all the members and check
    if any of them contain the string passed into the method. If they do it adds them to the array to return, thus returning an array of partial
    or full matches */
    public ArrayList<String> searchMembersByName(String nameEntered) {
        ArrayList<String> membersReturn = new ArrayList<>();
        for (Member member : getMembers()) {
            if (member.getName().contains(nameEntered)){
                membersReturn.add(member.getName());
            }
        }
        return membersReturn;
    }

    // Same as above
    public ArrayList<String> searchTrainersByName(String nameEntered){
        ArrayList<String> trainersReturn = new ArrayList<>();
        for (Trainer trainer : getTrainers()) {
            if (trainer.getName().contains(nameEntered)){
                trainersReturn.add(trainer.getName());
            }
        }
        return trainersReturn;
    }

    public ArrayList<Member> listMembers() {
        return members;
    }

    /* This method declares an ArrayList of members with ideal weight. It then iterates through all the arraylist of all members ,
    firstly checking that the member has any assessments, if so it passes the member the latest assessment into the isIdealBodyWeight method
     and if this returns true it adds the member to the declared array list of ideal bosy weight members and returns that arraylist */
    public ArrayList<Member> listMembersWithIdealWeight() {
        ArrayList<Member> idealMembers = new ArrayList<>();
        for(Member member: members) {
            if (member.getAssessments().size()>0) {
                if (GymUtility.isIdealBodyWeight(member, member.latestAssessment())) {
                    idealMembers.add(member);
                }
            }
        }
        return idealMembers;
    }

    /* This method declares an ArrayList of members whose BMI category matches the String passed into the method.
    * It iterates through the arraylist of all members using a for loop. It uses the determineBMICategory to return a string of the members BMI category.
    * To use this method it must pass in the members BMI, which is itself calculated using the calculateBMI category, passing in the member and their latest
    * assessment. It then checks if that String (category) contains the string passed into the method. If so, the member is added to the arraylist.
    * An arraylist of members matching or partially matching the specific category is then returned. */
    public ArrayList<Member> listMembersBySpecificBMICategory(String category) {
        ArrayList<Member> categoryMembers = new ArrayList<>();
        for(Member member: members) {
            if (GymUtility.determineBMICategory(GymUtility.calculateBMI(member, member.latestAssessment())) .contains(category.toUpperCase())){
                categoryMembers.add(member);
            }
        }
    return categoryMembers;
    }


    /*This method initiates a string which will contain the data required to be returned. It checks that there are members in the members arraylist.
    * If so it iterates through the members, adding to the string on each visit. I used the round method from the Math class to return the floats to the nearest integer.
    *  */
    public String listMemberDetailsImperialAndMetric() {
        String details = "";
        if (members.size()>0) {
            for (Member member : members) {
                Assessment assessment = member.latestAssessment();
                details = details + member.getName() + ": " + assessment.getWeight() + " kg (" + Math.round(assessment.getWeight()*2.205) + " lbs) " + member.getHeight() + " metres (" + Math.round(member.getHeight()*39.37) + " inches). \n";
            }
        }
        else details = "No registered members";
        return details;
    }



    @SuppressWarnings("unchecked")
    public void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());



        Class<?>[] classes = new Class[] { Member.class, Trainer.class, Assessment.class };

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("people.xml"));
        members = (ArrayList<Member>) is.readObject();
        trainers = (ArrayList<Trainer>) is.readObject();
        is.close();
    }

    public void save() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Product class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated

        Class<?>[] classes = new Class[] { Member.class, Trainer.class, Assessment.class };

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("people.xml"));
        out.writeObject(members);
        out.writeObject(trainers);
        out.close();
    }

}
