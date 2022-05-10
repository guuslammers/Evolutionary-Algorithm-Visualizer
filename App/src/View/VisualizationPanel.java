package View;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {

    List<Circle2D> circles;

    JLabel generationLabel;
    JLabel bestEntityStepsTakenLabel;
    
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

    public void createGenerationLabel(String generation) {
        /*
        Instantiates generationLabel with a generation of 0 and centers it in the middle of the screen.
        */
        this.generationLabel = new JLabel(generation);
        this.generationLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 25));
        this.generationLabel.setHorizontalAlignment(JLabel.CENTER);
        this.generationLabel.setVerticalAlignment(JLabel.CENTER);
        this.add(this.generationLabel, BorderLayout.CENTER);
    }

    public void setGenerationLabel(String currentGeneration) {
        this.generationLabel.setText(currentGeneration);
    }

    public void clearGenerationLabel() {
        this.remove(this.generationLabel);
    }

    public void createBestEntityStepsTakenLabel() {
        this.bestEntityStepsTakenLabel = new JLabel("Steps Taken: N/A");
        this.bestEntityStepsTakenLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 25));
        this.bestEntityStepsTakenLabel.setHorizontalAlignment(JLabel.CENTER);
        this.bestEntityStepsTakenLabel.setVerticalAlignment(JLabel.CENTER);
        this.add(this.bestEntityStepsTakenLabel, BorderLayout.SOUTH);
    }

    public void setBestEntityStepsTakenLabel(String currentSteps) {
        this.bestEntityStepsTakenLabel.setText(currentSteps);
    }

    public void clearBestEntityStepsTakenLabel() {
        this.remove(this.bestEntityStepsTakenLabel);
    }

    public void addMyMouseListener(MouseAdapter mouseListener) {
        this.addMouseListener(mouseListener);
    }

    public void addMyMouseMoveListener(MouseMotionAdapter mouseMotionListener) {
        this.addMouseMotionListener(mouseMotionListener);
    }

}
