import java.util.Random;

public class Brain {

    private Vector2D[] accelerationDirections;
    private int step;

    Brain(int size) {
        this.accelerationDirections = new Vector2D[size];
        this.step = 0;

        populateAccelerationDirections();
    }

    private void populateAccelerationDirections() {
         /*
        Populates accelerationDirections with size random directions.
        */
        for (int i = 0; i < this.accelerationDirections.length; i++) {
            this.accelerationDirections[i] = generateAccelerationDirection();
        }
    }

    private Vector2D generateAccelerationDirection() {
        /*
        Returns a random normalized 2DVector.
        */
        // generate a random angle between 0 and 2pi
        double angle = generateRandomDoubleInRange(0.0, 2 * Math.PI);
        // instantiate and return normalized Vector2D using the generated angle.
        return new Vector2D(Math.cos(angle), Math.sin(angle));
    }
        
    public void mutateAccelerationDirections() {
        /*
        Randomely mutates cirtain directions in accelerationDirections.
        */
        double MUTATION_RATE = 0.01;
        for (int i = 0; i < this.accelerationDirections.length; i++) {
            // generate a random number between 0 and 1
            double random = generateRandomDoubleInRange(0, 1);
            // if random is less then MUTATION_RATE then mutate accelerationDirections[i]
            if(random < MUTATION_RATE) {
                this.accelerationDirections[i] = generateAccelerationDirection();
            }        
        }
    }

    private double generateRandomDoubleInRange(double min, double max) {
        /*
        Generates a random double between min and max.
        */
        Random r = new Random();
        return min + r.nextDouble() * (max - min);
    }

}
