

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Member extends Person {

    private float height;
    private float startWeight;
    private String chosenPackage;

    private HashMap <String, Assessment> assessmentMap;

    public HashMap<String, Assessment> getAssessments() {
        return assessmentMap;
    }

    //Constructor, which inherits the super fields from the superclass Person and adds extra fields
    public Member(String email, String name, String address, String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startWeight);
        setChosenPackage(chosenPackage);
        assessmentMap = new HashMap<String, Assessment>();
    }


    public void addAssessment(Assessment assessment, String AssessmentDate){
        assessmentMap.put(AssessmentDate, assessment);
    }

    /* this uses the get method from the HashMap class to return the value of the corresponding key passed in. In the case the value is an assessment
    paired to the key which a date */
    public Assessment getAssessment(String key){
        Assessment assessment = assessmentMap.get(key);
        return assessment;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if (height>=1 && height<=3) {
            this.height = height;
        }
        else{this.height = 2;}
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        if (startWeight >= 35 && startWeight <= 250) {
            this.startWeight = startWeight;
        } else {
            startWeight = 0;
        }
    }

    public String getChosenPackage() {
        return chosenPackage;
    }

    public void setChosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }

    /*This method declares an assessment variable as null. If the user's assessment hashmap contains pairs, then the sortedAssessmentDates is called
    * using the last() method of the treeset class to get the latest assessment. This returns the latest assessment date which is the key to
    * finding the latest assessment in the assessment hashmap using the get() method of the hashmap class    */
    public Assessment latestAssessment(){
        Assessment assessment = null;
        if(assessmentMap.size()>0) {
            String date = sortedAssessmentDates().last();
            assessment = assessmentMap.get(date);
        }
        return assessment;
    }

    /* this method declares and initiates a new TreeSet variable, an implementation of the SortedSet interface. The method then
    * uses a for string to which uses the keySet() hashmap method to create a set of all the keys (dates) in the loop and iterate through that loop,
    * adding each key/ date to the treeset. These are then automatically sorted within the treeset as part of the function of that class.   */
    public SortedSet<String> sortedAssessmentDates(){
        TreeSet set = new TreeSet();
        for (String date : assessmentMap.keySet()) {
            set.add(date);
        }
        return set;
    }

    public void chosenPackage(String chosenPackage){
        this.chosenPackage = chosenPackage;

    }
    //This method calls on the toString method of the superclass Person and adds the fields of the subclass member to the string
    @Override
    public String toString() {
        return super.toString() +
                "Height= " + height + '\n'+
                "StartWeight= " + startWeight + '\n'+
                "ChosenPackage= " + chosenPackage;
    }
}

