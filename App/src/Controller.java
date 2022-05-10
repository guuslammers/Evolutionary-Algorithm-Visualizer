import Model.Model;
import Model.Obstacle;
import Model.StartPosition;
import Model.Vector2D;
import Model.Entity;
import Model.Goal;
import Model.ICircle;

import View.View;
import View.Circle2D;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    
    int SIMULATION_SPEED = 50;

    Model model;
    View view;

    boolean running;
    Timer timer;

    Vector2D temporaryObstacleCenter;
    Color temporaryObstacleColor = Color.orange;
    Circle2D temporaryObstacle;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.running = false;

        this.view.getNavigationPanel().addStartButtonListener(new StartButtonListener());
        this.view.getNavigationPanel().addRestartButtonListener(new RestartButtonListener());
        this.view.getNavigationPanel().addSkipButtonListener(new SkipButtonListener());
        
        this.view.getVisualizationPanel().addMouseListener(new MyMouseListener());
        this.view.getVisualizationPanel().addMouseMotionListener(new MyMouseMotionListener());
    }

    private void updateVisualization() {
        // reset visualization
        this.view.getVisualizationPanel().clearCircles();
        // check if there is a temorary obstacle that needs to be drawn
        if(this.temporaryObstacle != null) {
            this.view.getVisualizationPanel().addCircle(this.temporaryObstacle);
        }
        // get all visualization elements from the model
        List<ICircle> elements = this.model.getAllElements();
        // create visualization elements for the view
        for(ICircle circle : elements) {
            // check the type of the circle and change color accordingly
            if(circle instanceof Obstacle) {
                this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.red));
            }
            else if(circle instanceof StartPosition) {
                this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.blue));
            }
            else if(circle instanceof Goal) {
                this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.green));
            }
            else if(circle instanceof Entity) {
                Entity entity = (Entity)circle;
                if(entity.isBestEntity()) {
                    this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.orange));
                } else {
                    this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.gray));
                }
            }
        }
        // update generation label

        // redraw window
        this.view.getVisualizationPanel().refreshPanel();
    }

    private void createSimulationTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    model.updateSimulation();
                    updateVisualization();
                    String generationLabel = "Generation: " + Integer.toString(model.getPopulation().getGeneration());
                    view.getVisualizationPanel().setGenerationLabel(generationLabel);
                    if(model.getPopulation().getBestEntity() != null) {
                        String stepsLabel = "Steps Taken: " + Integer.toString(model.getPopulation().getBestEntity().getBrain().getStep());
                        view.getVisualizationPanel().setBestEntityStepsTakenLabel(stepsLabel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
          }, this.SIMULATION_SPEED, this.SIMULATION_SPEED);
    }

    class StartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            /*
            Starts and stops the simulation depending on if it is already running or not.
            Does not allow the simulation to start unless both startPosition and goal are not null.
            */
            if(running) {
                view.getNavigationPanel().enableRestartButton();
                view.getNavigationPanel().changeToStartButton();
                view.getNavigationPanel().disableSkipButton();
                timer.cancel();
                running = false;
            }
            else {
                if(model.getStartPosition() != null && model.getGoal() != null) {
                    view.getNavigationPanel().disableRestartButton();
                    view.getNavigationPanel().changeToStopButton();
                    view.getNavigationPanel().enableSkipButton();
                    // create population if it doesn't exist
                    if(model.getPopulation() == null) {
                        model.createNewPopulation();
                        view.getVisualizationPanel().createGenerationLabel("Generation: " + model.getPopulation().getGeneration());
                        view.getVisualizationPanel().createBestEntityStepsTakenLabel();
                    }
                    updateVisualization();
                    createSimulationTimer();
                    running = true;
                }
            }
        }

    }

    class RestartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            /*
            Clears the entire visualization and rerenders.
            */
            model.clearPopulation();
            model.clearStartPosition();
            model.clearGoal();
            model.clearObstacles();
            view.getVisualizationPanel().clearGenerationLabel();
            view.getVisualizationPanel().clearBestEntityStepsTakenLabel();
            updateVisualization();
        }

    }

    class SkipButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            /*
            Skips 10 generations in the simulation.
            */
            view.getNavigationPanel().disableSkipButton();
            timer.cancel();
            int currentGeneration = model.getPopulation().getGeneration();
            while(true) {
                model.updateSimulation();
                if(model.getPopulation().getGeneration() - currentGeneration >= 10) {
                    model.updateSimulation();
                    break;
                }
            }
            createSimulationTimer();
            view.getNavigationPanel().enableSkipButton();
        }

    }

    class MyMouseListener extends MouseAdapter {

        public void mouseClicked (MouseEvent e)
        {
            /*
            Sets start position and goal position if they havent been set yet.
            */
            if(!running) {
                if(model.getStartPosition() == null) {
                    model.setStartPosition(e.getX(), e.getY());
                    updateVisualization();
                }
                else if(model.getGoal() == null) {
                    model.setGoal(e.getX(), e.getY());
                    updateVisualization();
                }
            }
        }

        public void mousePressed (MouseEvent e)
        {
            /*
            Sets temporaryObstacle center if start position and goal position have been set.
            */
            if(!running) {  
                if(model.getStartPosition() != null && model.getGoal() != null) {
                    temporaryObstacleCenter = new Vector2D(e.getX(), e.getY());
                }
            }
        }

        public void mouseReleased (MouseEvent e)
        {
            /*
            create obstacle in model, clears temporary obstacle variables, and updates visualization.
            */
            if(!running) {
                if(temporaryObstacleCenter != null) {
                    Vector2D mousePosition = new Vector2D(e.getX(), e.getY());
                    double radius = temporaryObstacleCenter.getDistanceTo(mousePosition);
                    model.addObstacle(temporaryObstacleCenter.getX(), temporaryObstacleCenter.getY(), radius);         
                    temporaryObstacle = null;
                    temporaryObstacleCenter = null;
                    updateVisualization();
                }
            }
        }

    }

    class MyMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            /*
            Updates temporary obstacle and updates the visualization.
            */
            if(!running) {
                if(temporaryObstacleCenter != null) {
                    Vector2D mousePosition = new Vector2D(e.getX(), e.getY());
                    double radius = temporaryObstacleCenter.getDistanceTo(mousePosition);
                    temporaryObstacle = new Circle2D(temporaryObstacleCenter.getX(), temporaryObstacleCenter.getY(), radius, temporaryObstacleColor);
                    updateVisualization();
                }
            }
        }
        
    }

}
