package View;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class View extends JFrame {
    
    int VISUALIZATION_WIDTH;
    int NAVIGATION_BAR_HEIGHT = 100;
    int VISUALIZATION_HEIGHT;

    private NavigationPanel navigationPanel;
    private VisualizationPanel visualizationPanel;

    public View(int VISUALIZATION_WIDTH, int VISUALIZATION_HEIGHT) {
        this.VISUALIZATION_WIDTH = VISUALIZATION_WIDTH;
        this.VISUALIZATION_HEIGHT = VISUALIZATION_HEIGHT;

        this.setTitle("Evolutionary Algorithm Visualization Tool"); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setResizable(false);

        // create, resize, add, and pack JPanels
        this.navigationPanel = new NavigationPanel();
        this.visualizationPanel = new VisualizationPanel();
        this.navigationPanel.setPreferredSize(new Dimension(this.VISUALIZATION_WIDTH, this.NAVIGATION_BAR_HEIGHT));
        this.visualizationPanel.setPreferredSize(new Dimension(this.VISUALIZATION_WIDTH, this.VISUALIZATION_HEIGHT));
        this.add(navigationPanel, BorderLayout.NORTH);
        this.add(visualizationPanel, BorderLayout.CENTER);
        this.pack();
    }

    public NavigationPanel getNavigationPanel() {
        return this.navigationPanel;
    }

    public VisualizationPanel getVisualizationPanel() {
        return this.visualizationPanel;
    }

}
