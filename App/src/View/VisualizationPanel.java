package View;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {

    List<Circle2D> circles;
    
    VisualizationPanel() {
        circles = new ArrayList<Circle2D>();
    }

    public void paintComponent(Graphics g) {
        /*
        Paints all componenents onto JPanel.
        */
        Graphics2D g2D = (Graphics2D) g; 

        for(Circle2D circle : circles) {
            g2D.setColor(circle.getColor());
            g2D.fill(circle.getCircle());
        }
    }

    public void refreshPanel() {
        /*
        Refreshes the panel.
        */
        this.revalidate();
        this.repaint();
    }

    public void addCircle(Circle2D circle) {
        this.circles.add(circle);
    }
    
    public void clearCircles() {
        this.circles.clear();
    }

    public void addMyMouseListener(MouseAdapter mouseListener) {
        this.addMouseListener(mouseListener);
    }

    public void addMyMouseMoveListener(MouseMotionAdapter mouseMotionListener) {
        this.addMouseMotionListener(mouseMotionListener);
    }

}
