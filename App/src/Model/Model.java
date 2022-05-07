package Model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    
    Integer WINDOW_WIDTH;
    Integer WINDOW_HEIGHT;

    Integer populationSize = 20;
    Population population;
    
    double startPositionRadius = 5;
    StartPosition startPosition;

    double goalRadius = 10;
    Goal goal;

    List<Obstacle> obstacles = new ArrayList<Obstacle>();

    public Model(int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    public void updateSimulation() {
        /*
        Moves the simulation one tick forward. This includes updating the entire population, checking if any Entities have died,
        and if the generation is dead. If the current generation is dead a new generation will be created.
        */  
        // update entities and check collisions.
        this.population.updatePopulation();
        Entity[] entities = this.population.getPopulation();
        for(Entity entity : entities) {
            // skip entity if it is already dead.
            if(entity.isAlive()) {
                // Check if entity collided with the goal.
                if(circleCollide(entity, this.goal)) {
                    entity.setGoalReached();
                    entity.killEntity();
                    continue;
                }
                // Check if entity collided with a wall.
                else if(wallCollide(entity)) {
                    entity.killEntity();
                    continue;
                }
                // Check if entity collided with an obstacle.
                for(Obstacle obstacle : this.obstacles) {
                    if(circleCollide(entity, obstacle)) {
                        entity.killEntity();
                        break;
                    }
                }
            }
        }

        // Check if population is dead, if it is create a new generation.
        if(this.population.isPopulationDead()) {
            this.population.calculateEntityFitnesses(this.goal.getPosition());
            this.population.naturalSelection();
            this.population.mutate();
        }
    }

    public void createNewPopulation() {
        /*
        Creates a new population at startposition.
        */
        Population newPopulation = new Population(this.startPosition.getPosition(), this.populationSize);
        this.population = newPopulation;
    }

    public void clearPopulation() {
        this.population = null;
    }

    public Population getPopulation() {
        return this.population;
    }

    private boolean circleCollide(ICircle circle1, ICircle circle2) {
        /*
        Returns true if circle1 and circle2 intersect, false otherwise.
        */
        double distanceBetweenCenters = circle1.getPosition().getDistanceTo(circle2.getPosition());
        if(distanceBetweenCenters < (circle1.getRadius() + circle2.getRadius())) {
            return true;
        }

        return false;
    }

    private boolean wallCollide(ICircle circle) {
        /*
        Checks if circle collided with one of the four walls. Returns true if it did and false otherwise.
        */
        if(circle.getPosition().getX() - circle.getRadius() < 0) {
            return true;
        }
        else if(circle.getPosition().getY() - circle.getRadius() < 0) {
            return true;
        }
        else if(circle.getPosition().getX() + circle.getRadius() > this.WINDOW_WIDTH) {
            return true;
        }
        else if(circle.getPosition().getY() + circle.getRadius() > this.WINDOW_HEIGHT) {
            return true;
        }

        return false;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public void setGoal(double x, double y) {
        /*
        Creates new goal if it is not colliding with the walls.
        */
        Goal newGoal = new Goal(new Vector2D(x, y), this.goalRadius);
        if(!wallCollide(newGoal)) {
            this.goal = newGoal;
        }
    }

    public void clearGoal() {
        this.goal = null;
    }

    public StartPosition getStartPosition() {
        return this.startPosition;
    }

    public void setStartPosition(double x, double y) {
        /*
        Creates new start position if it is not colliding with the walls.
        */
        StartPosition newStartPosition = new StartPosition(new Vector2D(x, y), this.startPositionRadius);
        if(!wallCollide(newStartPosition)) {
            this.startPosition = newStartPosition;
        }
    }

    public void clearStartPosition() {
        this.startPosition = null;
    } 

    public void addObstacle(double x, double y, double radius) {
        /*
        Creates new obstacle only if the start position and goal are not inside it.
        */
        Obstacle obstacle = new Obstacle(new Vector2D(x, y), radius);
        if(!circleCollide(obstacle, this.startPosition) && !circleCollide(obstacle, this.goal)) {
            this.obstacles.add(new Obstacle(new Vector2D(x, y), radius));
        }
    }

    public void clearObstacles() {
        this.obstacles.clear();
    }

    public List<ICircle> getAllElements() {
        /*
        Returns a list of all the ICircle objects present in the simulation.
        */
        List<ICircle> elements = new ArrayList<ICircle>();
        if(this.startPosition != null) {
            elements.add(this.startPosition);
        }
        if(this.goal != null) {
            elements.add(this.goal);
        }
        if(this.obstacles.size() > 0) {
            elements.addAll(this.obstacles);
        }
        if(this.population!= null) {
            for(Entity entity: this.population.getPopulation()) {
                elements.add(entity);
            }  
        }

        return elements;
    }

}
