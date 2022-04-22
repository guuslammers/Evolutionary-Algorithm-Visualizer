import java.util.Random;
import java.util.Vector;

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

    public void incrementStep() {
        /*
        Increments step.
        */
        this.step++;
    }

    public boolean accelerationDirectionsAvailable() {
        /*
        Returns true if there are accelerationDirections left in the brain.
        */
        if(this.accelerationDirections.length > this.step) {
            return true;
        }

        return false;
    }

    public Vector2D getNextAccelerationDirection() {
        /*
        Returns the next accelerationDirection.
        */
        return this.accelerationDirections[this.step];
    }

    public void setBrainAccelerationDirection(Vector2D[] accelerationDirections) {
        /*
        Overwrites this brains acceleration directions with the passed in acceleration directions.
        */
        this.accelerationDirections = accelerationDirections;
    }

    public Vector2D[] deepCopyAccelerationDirections() {
        /*
        Deep copy this brains accelerationDirections and return the deep copy.
        */
        Vector2D[] deepCopy = new Vector2D[this.accelerationDirections.length];
        for(int i = 0; i < this.accelerationDirections.length; i++) {
            deepCopy[i] = new Vector2D(this.accelerationDirections[i].getX(), this.accelerationDirections[i].getY());
        }
        return deepCopy;
    }

    private double generateRandomDoubleInRange(double min, double max) {
        /*
        Generates a random double between min and max.
        */
        Random r = new Random();
        return min + r.nextDouble() * (max - min);
    }

}
