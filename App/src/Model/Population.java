package Model;
import java.util.Random;

public class Population {
    
    Vector2D startPosition;
    Entity[] population;
    Entity bestEntity;
    int generation;
    double fitnessSum;

    Population(Vector2D startPosition, int populationSize) {
        this.startPosition = startPosition;
        this.population = new Entity[populationSize];
        this.generation = 1;

        createPopulation();
    }

    private void createPopulation() {
        /*
        Creates a new population.
        */
        for(int i = 0; i < this.population.length; i++) {
            this.population[i] = new Entity(startPosition.copy(), false);
        }
    }

    public void updatePopulation() {
        /*
        Updates the entire population by moving all the alive Entities that have not reached the goal.
        */
        for(Entity entity : population) {
            if(entity.isAlive()) {
                entity.move();
            }
        }
    }

    public Entity[] getPopulation() {
        return this.population;
    }

    public boolean isPopulationDead() {
        /*
        Returns true if the entire population is dead, false otherwise.
        */
        for (int i = 0; i < this.population.length; i++) {
            if(this.population[i].isAlive()) {
                return false;
            }
        }

        return true;
    }

    public void calculateEntityFitnesses(Vector2D goalPosition) {
        /*
        Calculates the fitness for every Entity in the population.
        */
        for(int i = 0; i < this.population.length; i++) {
            this.population[i].calculateFitness(goalPosition);
        }   
    }

    public void naturalSelection() {
        /*

        */
        Entity[] newPopulation = new Entity[this.population.length]; 
        this.calculateFitnessSum();
        for(int i = 0; i < this.population.length - 1; i++) {
            Entity parent = this.selectParent();
            newPopulation[i] = getChildEntity(parent, false);
        }
        // Find and add best entity.
        this.bestEntity = null;
        this.setBestEntity();
        newPopulation[newPopulation.length - 1] = getChildEntity(this.bestEntity, true);
        // overwrite population with new generation.
        this.population = newPopulation;
        // increment generation
        this.generation++;
    }

    public void mutate() {
        /*
        Mutates all of the entities in the poopulation except the best entity. 
        */
        for(Entity entity : population) {
            if(!entity.isBestEntity()) {
                entity.getBrain().mutateAccelerationDirections();;
            }
        }
    }

    private void calculateFitnessSum() {
        /*
        Sums the fitnesses of every Entity in the population.
        */
        this.fitnessSum = 0;
        for(int i = 0; i < this.population.length; i++) {
            this.fitnessSum += this.population[i].getFitness();
        }
    }

    private Entity selectParent() {
        /*
        Selects a parent Entity from the population.
        */
        // Get a random number between 0 and fitnessSum. 
        double random = generateRandomDoubleInRange(0, this.fitnessSum);
        double sum = 0;
        int index = 0;
        for(int i = 0; i < this.population.length; i++) {
            sum += this.population[i].getFitness();
            if(sum > random) {
                index = i;
                break;
            }
        }

        return this.population[index];
    }

    private Entity getChildEntity(Entity parent, boolean bestEntity) {
        /*
        Creates a new Entity with a cloned copy of the parents brain.
        */
        Entity child = new Entity(this.startPosition.copy(), bestEntity);
        child.cloneBrainDirectionsFrom(parent.getBrain());
        return child;
    }

    private void setBestEntity() {
        /*
        Sets the best Entity in the population.
        */
        int bestIndex = 0;
        double bestFitness = 0;
        for(int i = 0; i < this.population.length; i++) {
            if(this.population[i].getFitness() > bestFitness) {
                bestIndex = i;
                bestFitness = this.population[i].getFitness();
            }
        }

        this.bestEntity = this.population[bestIndex];
    }

    public int getGeneration() {
        return this.generation;
    }

    private double generateRandomDoubleInRange(double min, double max) {
        /*
        Generates a random double between min and max.
        */
        Random r = new Random();
        return min + r.nextDouble() * (max - min);
    }

}
