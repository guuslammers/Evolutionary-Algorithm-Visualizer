package View;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {
    
    VisualizationPanel() {
        this.setBackground(Color.blue);
    }

    public void addMyMouseListener(MouseAdapter mouseListener) {
        this.addMouseListener(mouseListener);
    }

    public void check() {
        System.out.println(this.getHeight());
        System.out.println(this.getWidth());
    }

}
