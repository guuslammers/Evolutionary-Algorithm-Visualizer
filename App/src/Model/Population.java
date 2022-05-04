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
        this.generation = 0;

        createPopulation();
    }

    private void createPopulation() {
        /*
        Creates a new population.
        */
        for(int i = 0; i < this.population.length; i++) {
            this.population[i] = new Entity(startPosition, false);
        }
    }

    public void updatePopulation() {
        /*
        Updates the entire population by moving all the alive Entities.
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
    }

    public void mutate() {
        /*

        */
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
        Entity child = new Entity(this.startPosition, bestEntity);
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

    private double generateRandomDoubleInRange(double min, double max) {
        /*
        Generates a random double between min and max.
        */
        Random r = new Random();
        return min + r.nextDouble() * (max - min);
    }

}
