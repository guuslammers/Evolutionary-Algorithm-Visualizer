public class Entity {
    
    Brain brain;
    Vector2D position, velocity, acceleration;
    boolean bestEntity;
    int radius;
    boolean alive;
    double fitness;
    boolean goalReached;

    Entity(Vector2D startPosition, boolean bestEntity, int radius) {
        this.brain = new Brain(1000);
        this.bestEntity = bestEntity;
        this.radius = radius;
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

}
