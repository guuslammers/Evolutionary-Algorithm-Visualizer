public class Entity {
    
    int TERMINAL_VELOCITY = 5;
    int ENTITY_RADIUS = 5;

    Brain brain;
    Vector2D position, velocity, acceleration;
    boolean bestEntity;
    boolean alive;
    double fitness;
    boolean goalReached;

    Entity(Vector2D startPosition, boolean bestEntity, int radius) {
        this.brain = new Brain(1000);
        this.bestEntity = bestEntity;
        this.alive = true;
        this.fitness = 0;
        this.goalReached = false;
    }

    public void killEntity() {
        /*
        Kills entity by setting alive to false.
        */
        this.alive = false;
    }

    private void move() {
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

    public void update() {
        /*
        Controls the updating of the entity's state. This includes moving the entity, chec
        */
    }

}
