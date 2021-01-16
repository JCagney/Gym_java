public class GymUtility {


    /*This method calculates member BMI based on their height and the weight from their latest assessment. BMI = weight/ height squared*/
    public static float calculateBMI(Member member, Assessment assessment){
        float BMI = (assessment.getWeight() / ((member.getHeight()) * (member.getHeight())));
        return BMI;
    }

    /*This method determines the member's BMI category based on the BMI passed into the method. It uses if statements to determine which category
    * the BMI falls into and then returns a string describing the category  */
    public static String determineBMICategory(float bmi){
        String bmiCategory = "";
        if (bmi < 16){
            bmiCategory = "SEVERELY UNDERWEIGHT"; }
        else if ((bmi >= 16)&&(bmi< 18.5)){
            bmiCategory = "UNDERWEIGHT"; }
        else if((bmi >= 18.5) && (bmi < 25)){
            bmiCategory = "NORMAL";}
        else if((bmi >=25)&&(bmi<30)){
            bmiCategory = "OVERWEIGHT";}
        else if((bmi >=30)&&(bmi<35)){
            bmiCategory = "MODERATELY OBESE";}
        else if(bmi>=35){
            bmiCategory = "SEVERELY OBESE";}

        return bmiCategory;
    }

    /* This method firstly declares a variable to determine the ideal weight of a member, and then a boolean which will indicate whether or not the member is at
    this weight. The ideal weight is based on the Devine formula. The first calculation is performed for members with M gender, and then for members who do
    not have M gender. If members are less than 5 feet (1.524m) they are assigned the default ideal weight. Otherwise the calculation is performed by determining
    their inches above 5 feet and multiplying that by 2.3, then adding 50. If this figure matches the members actual weight (+/-0.2) the boolean returns
    true, otherwise false*/
    public static boolean isIdealBodyWeight(Member member, Assessment assessment){
        double idealWeight;
        boolean isIdealBodyWeight = false;
        if (member.getGender().equals("M")){
            if (member.getHeight() <= 1.524){
                idealWeight = 50;}
            else idealWeight = ((((member.getHeight() - 1.524)*39.37)*2.3)+50);
            }
        else{
            if (member.getHeight() <= 1.524){
                idealWeight = 45.5;}
            else idealWeight = ((((member.getHeight() - 1.524)*39.37)*2.3)+45.5);
        }
        if (((assessment.getWeight())>=(idealWeight-0.2)) && ((assessment.getWeight())<=(idealWeight+0.2))){
            isIdealBodyWeight = true;
        }
        return isIdealBodyWeight;

    }
}
