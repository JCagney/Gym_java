public class Assessment {
    private float weight;
    private float thigh;
    private float waist;
    private String comment;
    private Trainer trainer;

//2 x overloaded constructors
    public Assessment(float weight, float thigh, float waist, String comment, Trainer trainer) {
            this.weight = weight;
            this.thigh = thigh;
            this.waist = waist;
            this.comment = comment;
            this.trainer = trainer;
    }

    public Assessment(float weight, float thigh, float waist, String comment) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;
    }


    //Getters & Setters
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getThigh() {
        return thigh;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    //ToString Method
    @Override
    public String toString() {
        return "Assessment: \n" +
                "weight: " + weight +
                "\n thigh: " + thigh +
                "\n waist: " + waist +
                "\n comment: " + comment +
                "\n trainer: " + trainer.getName() +
                "\n";
    }
}
