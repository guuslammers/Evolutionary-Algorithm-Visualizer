package Model;
public class Entity implements ICircle {
    
    int TERMINAL_VELOCITY = 5;
    double ENTITY_RADIUS = 5;

    Brain brain;
    Vector2D position, velocity, acceleration;
    boolean bestEntity;
    boolean alive;
    double fitness;
    boolean goalReached;

    public Entity(Vector2D startPosition, boolean bestEntity) {
        this.brain = new Brain(1000);
        this.position = startPosition;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.bestEntity = bestEntity;
        this.alive = true;
        this.fitness = 0;
        this.goalReached = false;
    }

    public void move() {
        /*
        Moves the entitity using the brains next accelerationDirection vector. If no
        direction are available kill the entity.
        */
        if(this.brain.accelerationDirectionsAvailable()) {
            this.acceleration = this.brain.getNextAccelerationDirection();
            this.brain.incrementStep();
        }
        else {
            this.killEntity();
        }
        // Update velocity.
        this.velocity.addToVector(this.acceleration);
        // Limit velocity.
        if(this.velocity.getMagnitude() > this.TERMINAL_VELOCITY) {
            this.velocity.setMagnitude(this.TERMINAL_VELOCITY);
        }
        // Update position.
        this.position.addToVector(this.velocity);
    }

    public void calculateFitness(Vector2D goalPosition) {
        /*
        Calculates the fitness of the entity.
        */
        this.fitness = 1 / (this.position.getDistanceTo(goalPosition));
    }

    public void killEntity() {
        /*
        Kills entity by setting alive to false.
        */
        this.alive = false;
    }

    public boolean isAlive() {
        /*
        Returns true if the entity is alive, false otherwise.
        */
        if(this.alive) {
            return true;
        }

        return false;
    }

    public boolean isBestEntity() {
        /*
        Returns true if the bestEntity is true.
        */
        return this.bestEntity;
    }

    public void cloneBrainDirectionsFrom(Brain brain) {
        /*
        Clones the passed in brain's accelerationDirections into this entities brain's accelerationDirections.
        */
        this.brain.setBrainAccelerationDirection(brain.deepCopyAccelerationDirections());
    }

    public double getFitness() {
        return this.fitness;
    }

    public Brain getBrain() {
        return this.brain;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Double getRadius() {
        return this.ENTITY_RADIUS;
    }
    
    public void setGoalReached() {
        this.goalReached = true;
    }

}
