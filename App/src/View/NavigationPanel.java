package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel {
    
    JButton startButton;
    JButton restartButton;

    NavigationPanel() {
        this.setLayout(new GridLayout());

        this.startButton = new JButton("Start");
        this.restartButton = new JButton("Restart");
        this.add(startButton);
        this.add(restartButton);
    }

    public void disableRestartButton() {
        this.restartButton.setEnabled(false);
    }

    public void enableRestartButton() {
        this.restartButton.setEnabled(true);
    }

    public void changeToStopButton() {
        this.startButton.setText("Stop");
    }
    
    public void changeToStartButton() {
        this.startButton.setText("Start");
    }

    public void addStartButtonListener(ActionListener listenForStartButton) {
        this.startButton.addActionListener(listenForStartButton);
    }

    public void addRestartButtonListener(ActionListener listenForRestartButton) {
        this.restartButton.addActionListener(listenForRestartButton);
    }

}
