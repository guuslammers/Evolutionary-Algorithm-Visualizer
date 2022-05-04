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

public class Controller {
    
    Model model;
    View view;

    boolean running;

    Vector2D temporaryObstacleCenter;
    Color temporaryObstacleColor = Color.orange;
    Circle2D temporaryObstacle;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.running = false;

        this.view.getNavigationPanel().addStartButtonListener(new StartButtonListener());
        this.view.getNavigationPanel().addRestartButtonListener(new RestartButtonListener());
        
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
                this.view.getVisualizationPanel().addCircle(new Circle2D(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), Color.pink));
            }
        }
        // redraw window
        this.view.getVisualizationPanel().refreshPanel();
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
                running = false;
            }
            else {
                if(model.getStartPosition() != null && model.getGoal() != null) {
                    view.getNavigationPanel().disableRestartButton();
                    view.getNavigationPanel().changeToStopButton();
                    running = true;
                    if(model.getPopulation() == null) {
                        model.createNewPopulation();
                    }
                    updateVisualization();
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
            updateVisualization();
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
