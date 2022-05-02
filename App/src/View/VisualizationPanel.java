package View;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {
    
    VisualizationPanel() {
        this.setBackground(Color.white);
    }

    public void addMyMouseListener(MouseAdapter mouseListener) {
        this.addMouseListener(mouseListener);
    }

    public void addMyMouseMoveListener(MouseMotionAdapter mouseMotionListener) {
        this.addMouseMotionListener(mouseMotionListener);
    }

    public void check() {
        System.out.println(this.getHeight());
        System.out.println(this.getWidth());
    }

}
