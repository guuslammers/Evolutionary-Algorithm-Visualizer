import Model.Model;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    
    Model model;
    View view;

    boolean running;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.running = false;

        this.view.getNavigationPanel().addStartButtonListener(new StartButtonListener());
        this.view.getNavigationPanel().addRestartButtonListener(new RestartButtonListener());
        
        this.view.getVisualizationPanel().addMouseListener(new MyMouseListener());
        this.view.getVisualizationPanel().addMouseMotionListener(new MyMouseMotionListener());
    }

    class StartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(running) {
                view.getNavigationPanel().enableRestartButton();
                view.getNavigationPanel().changeToStartButton();
                running = false;
            }
            else {
                view.getNavigationPanel().disableRestartButton();
                view.getNavigationPanel().changeToStopButton();
                running = true;
            }
        }

    }

    class RestartButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("Restart");
        }

    }

    class MyMouseListener extends MouseAdapter {
        public void mouseClicked (MouseEvent e)
        {
            System.out.println("Clicked");
        }
        public void mousePressed (MouseEvent e)
        {
            System.out.println("Pressed");
        }
        public void mouseReleased (MouseEvent e)
        {
            System.out.println("Released");
        }
        public void mouseEntered (MouseEvent e)
        {
            System.out.println("Entered");
        }
        public void mouseExited (MouseEvent e)
        {
            System.out.println("Exited");
        }
    }

    class MyMouseMotionListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            System.out.println("Moved");
        }
    
        public void mouseDragged(MouseEvent e) {
            System.out.println("Dragged");
        }
    }

}
