import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MenuController {


    private Scanner input = new Scanner(System.in);
    private GymAPI gym;
    HashMap<String, String> packages = new HashMap<>();


    //the main method initiates an new instance of the MenuController class
    public static void main(String[] args) {
        MenuController c = new MenuController();
    }

    /*The constructor initiates a new instance of the GymAPI class as a variable "gym". It uses the load method from that class to load the people.xml
    data file, within a try catch loop for exception handling. It also calls the fillPackages method which puts the keys and values into the HashMap of
      available packages. It then runs the welcome() menu which begins the menu for the user */
    public MenuController() {
        gym = new GymAPI();
        try {
            gym.load();
        }
        catch(Exception e){
            System.err.println("Error writing to file: "+e);
        }
        fillPackages();
        welcome();
    }

    /* The welcome method begins the user console interface. It asks the user to select login or register. It then calls the checkRole()
    * bespoke method which returns a boolean, true for a member and false for a trainer. Based on this data it then calls the
    * memberLogin(), trainerLogin(), memberRegister() or trainerRegister()  methods    */
    private void welcome() {
        System.out.println();
        System.out.print("Welcome to Cagney's Gym!\n\n");

        /* Create a while loop to repeat login/register options until user enters a valid option
        When they do we call the checkRole() method to see if they are a trainer or member and then call
        the appropriate method based on the boolean return of that method  */
        boolean goodInput = false;
        while (!goodInput) {
            System.out.print("Please select login(l) or register(r)\n ==>>");
            char enterChar = input.next().charAt(0);

            if ((enterChar == 'l') || (enterChar == 'L')) {
                goodInput = true;
                boolean role = checkRole();
                if (role==true){memberLogin();}
                else{trainerLogin();}

            } else if ((enterChar == 'r') || (enterChar == 'R')) {
                goodInput = true;
                boolean role = checkRole();
                if (role==true){memberRegister();}
                else{trainerRegister();}
            }
            else System.out.print("Sorry, incorrect character(s) entered.\n");

        }

    }

    /* The memberLogin method takes in an email address from the user. It then uses the searchMembersByEmail which returns a member
    * whose email matches the input, if there is one. If the return is null(no match) then the user is returned to welcome(). If there is a match
    * the matching member is passed into the runMemberMenu which is then called  */
    private void memberLogin() {
        input.nextLine();
        System.out.print("You have selected to login as a member. \n Please enter your email address: \n ==>>");
        String emailEntered = input.nextLine();

        Member memberLoggedIn = gym.searchMembersByEmail(emailEntered.toLowerCase());
        if (memberLoggedIn == null){
            System.out.println("Sorry, this email is not registered. \n Press any key to return to the main menu");
            input.nextLine();
            welcome();
        }
        else {


            System.out.println("Welcome, "+ memberLoggedIn.getName());
            runMemberMenu(memberLoggedIn);


        }
    }

    //Same as above method only for trainer
    private void trainerLogin() {
        input.nextLine();
        System.out.print("You have selected to login as a trainer. \n Please enter your email address: \n ==>>");
        String emailEntered = input.nextLine();

        Trainer trainerLoggedIn = gym.searchTrainersByEmail(emailEntered.toLowerCase());
        if (trainerLoggedIn == null){
            System.out.println("Sorry, this email is not registered. \n Press any key to return to the main menu");
            input.nextLine();
            welcome();
        }
        else {


            System.out.println("Welcome, "+ trainerLoggedIn.getName());
            runTrainerMenu(trainerLoggedIn);
        }
    }

    /* This is the memberMenu method which is called from the runMemberMenu method. It returns an int indicating which option the user has chosen.
    * It uses a try catch loop nested in a while loop to repeat the user input request until the user enters a valid int and also to prevent input exceptions  */
    private int memberMenu() {
        System.out.println("Gym Member Menu");
        System.out.println("---------");
        System.out.println("  1) View Profile");
        System.out.println("  2) Update Profile");
        System.out.println("  0) Exit");
        System.out.print("==>> ");
        boolean goodInput = false;
        int option = 0;
        while (!goodInput) {
            try {
                option = input.nextInt();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Num expected - you entered text");
            }
        }
        return option;
    }

    //Same as above, only for trainer
    private int trainerMenu(){
        System.out.println("Gym Trainer Menu");
        System.out.println("---------");
        System.out.println("  1) Add a New Member");
        System.out.println("  2) List All Members");
        System.out.println("  3) Search for a member by email and see assessment options");
        System.out.println("  0) Exit");
        System.out.print("==>> ");
        boolean goodInput = false;
        int option = 0;
        while (!goodInput) {
            try {
                option = input.nextInt();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Num expected - you entered text");
            }
        }
        return option;

    }

    /*This method calls the memberMenu method which returns the users option choice as an int. It then uses a switch statement to act based on that
    option. The switch statement is within a while loop. The loop breaks out to exit if the option is 0. The View Profile option calls the to String
     of the member logged in, while the update option calls the bespoke updateMemberProfile method. */
    private void runMemberMenu(Member member) {
        int option = memberMenu();
        while (option != 0) {

            switch (option) {
                case 1:
                    System.out.println(member.toString());
                    break;
                case 2:
                    updateMemberProfile(member);
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            input.nextLine();  //this second read is required - bug in Scanner class; a String read is ignored straight after reading an int.

            //display the main menu again
            option = memberMenu();
        }

        //the user chose option 0, so exit the program
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /* Same as above, only the options are different for a trainer. The trainerAddMember() method is called for that option.
     the list members option calls the listMembers method. The third option, to search for a member by email and see assessment options,
      runs a search of the email entered by calling gym.searchMembersByEmail(). If null is returned, the trainer ias advised that the email does
      not exist. If null is NOT returned the runAssessmentMenu() method is called, passing in the trainer and the member returned by the search  */
    private void runTrainerMenu(Trainer trainer){

        int option = trainerMenu();
        while (option != 0) {

            switch (option) {
                case 1:
                    trainerAddMember(trainer);
                    break;
                case 2:
                    System.out.println("Members: ");
                    System.out.println(listMembers());
                    input.nextLine();
                    break;
                case 3:
                    System.out.println("Please enter member email: ");
                    input.nextLine();
                    String email = input.nextLine();
                    if (gym.searchMembersByEmail(email.toLowerCase()) != null) {
                        System.out.print("The member's name is " + gym.searchMembersByEmail(email.toLowerCase()).getName()+". \n\n");
                        runAssessmentMenu(trainer, gym.searchMembersByEmail(email.toLowerCase()));
                    }
                    else{System.out.print("That email does not exist in the member database.");}
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }
            System.out.println("\nPress any key to continue...");
            input.nextLine();


            //display the welcome menu again
            option = trainerMenu();
        }

        //the user chose option 0, so exit the program
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /* This is the assessment submenu which is presented when the trainer runs a successful search member by email.
    * It is called from the runAssessmentMenu() method and functions the same as the member and trainer menu methods   */
    private int assessmentMenu(){
        System.out.println("Please choose from below options for this member:");
        System.out.println("---------");
        System.out.println("  1) Add a new assessment");
        System.out.println("  2) Update a comment on an assessment for this member");
        System.out.println("  0) Exit");
        System.out.print("==>> ");
        boolean goodInput = false;
        int option = 0;
        while (!goodInput) {
            try {
                option = input.nextInt();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Num expected - you entered text");
            }
        }
        return option;

    }

    /* This method calls the assessmentMenu sub menu which returns an int indicating the trainer's choice.
    * The first option is to add an assessment for the relevant member passed in. This build up the input variables and then
    * passes them into the Assessment constructor which is passed into the member.AddAssessment() method.
    *
    *  This then runs the gym.save method to save the assessment to the xml file, within a try/catch loop to handle exceptions.
    *
    *
    * The second option is to Update a comment on an assessment. This takes in a date for the assessment and then uses this as the ket to get()
    * the assessment from the assessment HashMap. It then takes in a string and sets this as the comment using assessment.setComment() method.
    *  */
    private void runAssessmentMenu(Trainer trainer, Member member){

        int option = assessmentMenu();
        while (option != 0) {

            switch (option) {
                case 1:
                    /* This option to add an assessment takes in a date first and does two data validations on it:
                    *  - to make sure the length is 8 charachers long
                    *  - make sure there is not already an assessment for that date which would cause an exception when adding the key
                    *    to the hashmap*/
                    boolean validDate;
                    String assessmentDate;
                    input.nextLine();
                    do{ validDate = true;
                        System.out.println("Enter the assessment date (format YY/MM/DD): ");
                        assessmentDate = input.nextLine();
                        if (assessmentDate.length()!=8){
                            System.out.println("Invalid format.");
                            validDate = false;
                        }
                        for(String string : member.getAssessments().keySet()){
                            if (assessmentDate.equals(string)){
                                System.out.println("Sorry there is already an assessment for that date.");
                                validDate = false;
                                }
                            }
                    }while(!validDate);
                    System.out.print("Enter the member's weight:  ");
                    float weight = input.nextFloat();
                    System.out.print("Enter the member's thigh size:  ");
                    float thigh= input.nextFloat();
                    System.out.print("Enter the member's waist size:  ");
                    float waist = input.nextFloat();
                    System.out.print("Add a comment for this assessment:  ");
                    input.nextLine();
                    String comment = input.nextLine();
                    member.addAssessment(new Assessment(weight, thigh, waist, comment, trainer), assessmentDate);
                    try {
                        gym.save();
                    }
                    catch(Exception e){
                        System.err.println("Error writing to file: "+e);
                    }
                    System.out.println("You have successfully added an assessment for "+member.getName()+". ");

                    break;
                case 2:
                    System.out.print("Enter date of the assessment you wish to update comment on (format YY/MM/DD)  ");
                    input.nextLine();
                    String date = input.nextLine();
                    Assessment assessment = member.getAssessment(date);
                    if (assessment == null){
                        System.out.println("There is no assessment for that date");
                    }
                    else {
                        System.out.println("Here is the assessment from that date: \n" + assessment + "Please enter updated comment for this assessment: ");
                        String newComment = input.nextLine();
                        assessment.setComment(newComment);
                        try {
                            gym.save();
                        } catch (Exception e) {
                            System.err.println("Error writing to file: " + e);
                        }
                        System.out.println("Assessment updated: \n" + assessment);
                    }
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }
            System.out.println("\nPress any key to continue...");
            input.nextLine();


            //display the assessment menu again
            option = assessmentMenu();
        }

        //the user chose option 0, so return to the trainer menu
        runTrainerMenu(trainer);
    }



    /* the updateMemberProfile method is called from the runMemberMenu() which passes the relevant member into the method.
    It uses the bespoke requestAddress(), requestGender() & choosePackage() methods to take in new fields for the member.
         It then saves the updated fields to the people.xml file within a try/catch loop. It then prints out the member's updated
         profile using the toString() method. The member is then returned to the memberMenu using the runMemberMenu method */
    private void updateMemberProfile(Member member){
        input.nextLine();
        String address = requestAddress();
        member.setAddress(address);
        String gender = requestGender();
        member.setGender(gender);
        String chosenPackage=choosePackage();
        member.setChosenPackage(chosenPackage);
        try {
            gym.save();
        }
        catch(Exception e){
            System.err.println("Error writing to file: "+e);}
        input.nextLine();
        System.out.println("You have successfully updated your profile "+member.getName()+". \nHere is your updated information: ");
        System.out.println(member.toString());
        System.out.println("\nPress any key to continue...");
        input.nextLine();
        runMemberMenu(member);
    }

    /*The memberRegister method is called from the welcome() menu if the user chooses to register as a member.
    It uses the bespoke methods to take in email, name, address, gender and package fields. It takes in height and starting
     weight using the necessary field validation. These parameters are then passed into the Member constructor which is passed into the
      gym.addMember() method to create the new member. The gym is then saved with a try/ catch loop to update the data file. The user is returned
       to the welcome method */
    private void memberRegister() {
        System.out.println("You have selected to register as a member.");
        input.nextLine();
        String email = requestnewEmail().toLowerCase();
        String name = requestName();
        String address = requestAddress();
        String gender = requestGender();
        System.out.print("Please enter your height:");
        Float height = input.nextFloat();
        while(height<1 || height>3){
            System.out.print("Height must be between 1 & 3. Please enter your height:");
            height = input.nextFloat();
        }
        System.out.print("Please enter your current weight:");
        Float startWeight = input.nextFloat();
        while(startWeight<35 || startWeight>250){
            System.out.print("Weight must be between 35 & 250. Please enter your weight:");
            startWeight = input.nextFloat();
        }
        String chosenPackage = choosePackage();

        gym.addMember(new Member(email,name, address, gender, height, startWeight, chosenPackage));
        try {
            gym.save();
        }
        catch(Exception e){
            System.err.println("Error writing to file: "+e);
        }

        input.nextLine();
        System.out.print("Congratulation "+name+", you have registered as a member. \n Press any key to return to the main menu.");
        input.nextLine();
        welcome();
    }

    //Same thing as above only this is for trainer register
    private void trainerRegister(){
        System.out.println("You have selected to register as a trainer.");
        input.nextLine();
        String email = requestnewEmail().toLowerCase();
        String name = requestName();
        String address = requestAddress();
        String gender = requestGender();
        System.out.print("Please enter your speciality:");
        String speciality = input.nextLine();
        gym.addTrainer(new Trainer(email,name, address, gender, speciality));
        try {
            gym.save();
        }
        catch(Exception e){
            System.err.println("Error writing to file: "+e);
        }
        System.out.print("Congratulation "+name+", you have registered as a trainer. \n Press any key to return to the main menu.");
        input.nextLine();
        welcome();
    }


    //This bespoke method called in the welcome method to check whether user is a member or trainer and return
    // a boolean (true for member, false for trainer)
    private boolean checkRole() {
        boolean role = true;
        boolean goodInput = false;
        while (!goodInput) {
            System.out.print("Please input if you are a member(m) or a trainer(t):\n ==>>");
            char enterChar = input.next().charAt(0);

            if ((enterChar == 'm') || (enterChar == 'M')) {
                goodInput = true;
                role = true;
            } else if ((enterChar == 't') || (enterChar == 'T')) {
                goodInput = true;
                role = false;
            }
            else System.out.print("Sorry, incorrect character(s) entered.\n");

        }
    return role;
    }

    /* This method is called from the runTrainerMenu to create a string list of all members. It iterates through the members using a
    * for loop, adding each member's name and email to the string   */
    private String listMembers(){
        String members = "";
        for (Member member : gym.getMembers()) {
            members = members + member.getName() +" "+ member.getEmail() + "\n";
        }
        return members;
    }

    /*This bespoke method request an email address for new people who are registering.
    The method iterates through both member and trainer arrays to see if the email is in use and if it is
    asks the user to enter a different valid email*/
    private String requestnewEmail() {
        String emailInput ="";
        boolean validEmail = false;

        System.out.print("Please enter your email address:");

        while (validEmail == false) {

            emailInput = input.nextLine();
            validEmail = true; //this boolean is to determine if an email enteres is unique or not

            //iterate through member array to check for matches
            for (Member member : gym.getMembers()) {
                if (member.getEmail().equals(emailInput)) {
                    validEmail = false;
                }
            }
            //iterate through trainer array to check for matches
            for (Trainer trainer : gym.getTrainers()) {
                if (trainer.getEmail().equals(emailInput)) {
                    validEmail = false;
                }
            }
            if (validEmail == false) {
                System.out.println("Sorry this email is in use. \n Please a valid email address:");
        }

        }


        //if there is no match the current emailInput should be returned
        return emailInput;
    }

    /* This method is called from the trainer member to add a member. It takes in all the relevant fields. Unfortunately I could not use
    * some of my bespoke methods for this as the printed lines needed to be different, which is why this method is quite long.
    * It returns the trainer to the trainer menu after the new member is added */
    private void trainerAddMember(Trainer trainer) {
        System.out.println("You have selected to add a member.");
        input.nextLine();
        String email ="";
        boolean validEmail = false;
        System.out.print("Please enter member's email address:");
        while (validEmail == false) {
            email = input.nextLine().toLowerCase();
            validEmail = true;
            //iterate through member array to check for matches
            for (Member member : gym.getMembers()) {
                if (member.getEmail().equals(email)) {
                    validEmail = false;
                }
            }
            //iterate through trainer array to check for matches
            for (Trainer trainer1 : gym.getTrainers()) {
                if (trainer1.getEmail().equals(email)) {
                    validEmail = false;
                }
            }
            if (validEmail == false) {
                System.out.println("Sorry this email is in use. \n Please a valid email address:");
            }
        }
        System.out.print("Please enter member's name:");
        String name = input.nextLine();
        System.out.print("Please enter member's address:");
        String address = input.nextLine();
        System.out.print("Please enter member's gender (M or F):");
        String gender;
        boolean genderValid = false;

        do {
            gender = input.nextLine().toUpperCase();
            if (gender.charAt(0) == 'M') {
                genderValid = true;
            } else if (gender.charAt(0) == 'F') {
                genderValid = true;
            } else {
                System.out.println("Incorrect selection.\n Please enter M or F:");
            }
        }
        while (!genderValid) ;
        System.out.print("Please enter member's height:");
        Float height = input.nextFloat();
        while(height<1 || height>3){
            System.out.print("Height must be between 1 & 3. Please enter member's height:");
            height = input.nextFloat();
        }
        System.out.print("Please enter member's current weight:");
        Float startWeight = input.nextFloat();
        while(startWeight<35 || startWeight>250){
            System.out.print("Weight must be between 35 & 250. Please enter member's weight:");
            startWeight = input.nextFloat();
        }

        String chosenPackage=choosePackage();
        input.nextLine();
        gym.addMember(new Member(email.toLowerCase(), name, address, gender, height, startWeight, chosenPackage));
        try {
            gym.save();
        }
        catch(Exception e){
            System.err.println("Error writing to file: "+e);
        }
        System.out.print("Congratulation "+ trainer.getName()+ ", you have registered "+name+" as a member. \n Press any key to return to the trainer menu.");
        input.nextLine();
        runTrainerMenu(trainer);
    }

    //a bespoke method to take in a name. Created to avoid repetition in the longer methods
    private String requestName(){
        System.out.print("Please enter your name:");
        String name = input.nextLine();
        return name;
    }

    //a bespoke method to take in an address. Created to avoid repetition in the longer methods
    private String requestAddress(){
        System.out.print("Please enter your address:");
        String address = input.nextLine();
        return address;
    }

    //a bespoke method to take in a gender. Created to avoid repetition in the longer methods
    private String requestGender() {
        System.out.print("Please enter your gender (M or F):");
        String gender;
        boolean genderValid = false;

        do {
            gender = input.nextLine().toUpperCase();
            if (gender.charAt(0) == 'M') {
                genderValid = true;
            } else if (gender.charAt(0) == 'F') {
                genderValid = true;
            } else {
                System.out.println("Incorrect selection.\n Please enter M or F:");
            }
        }
            while (!genderValid) ;

     return gender;
    }

    /*a bespoke method to take in the chosen package. Created to avoid repetition in the longer methods
    * The method uses a try/catch loop to take in the relevant number in order to avoid exceptions. That is within a while loop to repeat
    * until there is a valid input. The loop is nested within another while loop to set the chosen package string or else repeat the loop if
    * the input is not valid. */
    private String choosePackage() {
        System.out.println("\nHere are the packages available to you:\n");
        System.out.println("Package 1: " + packages.get("Package 1") + "\n\n"+"Package 2: " + packages.get("Package 2") + "\n\n"+
                "Package 3: " + packages.get("Package 3") + "\n\n"+" Package 4 (WIT): " + packages.get("WIT")+"\n");
        boolean goodInput = false;
        int choice = 0;
        String chosenPackage = "";
        while (!goodInput) {
            while (!goodInput) {
                try {
                    System.out.print("Please enter 1, 2, 3 or 4 to chose a package. \n==> ");
                    choice = input.nextInt();
                    goodInput = true;
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Bad input. Please enter 1, 2 or 3 or 4 to chose a package. \n==>");
                }
            }
            if (choice == 1) {
                chosenPackage = "Package 1";
            } else if (choice == 2) {
                chosenPackage = "Package 2";
            } else if (choice == 3) {
                chosenPackage = "Package 3";
            } else if (choice == 4) {
                chosenPackage = "WIT";
            } else {
                goodInput = false;
            }
        }
        System.out.println("You have selected "+chosenPackage+". \nPress any key to complete registration!");
        input.nextLine();
        return chosenPackage;
    }

    //This method fills the packages hashmap with the pairs of keys and values
    private void fillPackages(){
        packages.put("Package 1", "Allowed access anytime to gym.\nFree access to all classes.\nAccess to all changing areas including deluxe changing rooms.");

        packages.put("Package 2", "Allowed access anytime to gym.\n€3 fee for all classes.\nAccess to all changing areas including deluxe changing rooms.");

        packages.put("Package 3", "Allowed access to gym at off-peak times.\n€5 fee for all classes. \nNo access to deluxe changing rooms.");

        packages.put("WIT", "Allowed access to gym during term time.\n€4 fee for all classes.  \nNo access to deluxe changing rooms.");

    }




}
