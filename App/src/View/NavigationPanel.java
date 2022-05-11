package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel {
    
    JButton startButton;
    JButton restartButton;
    JButton skipButton;

    NavigationPanel() {
        this.setLayout(new GridLayout());

        this.startButton = new JButton("Start");
        this.restartButton = new JButton("Restart");
        this.skipButton = new JButton("Skip 10 Generations");
        this.add(startButton);
        this.add(restartButton);
        this.add(skipButton);

        disableSkipButton();
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

    public void disableSkipButton() {
        this.skipButton.setEnabled(false);
    }

    public void enableSkipButton() {
        this.skipButton.setEnabled(true);
    }

    public void addStartButtonListener(ActionListener listenForStartButton) {
        this.startButton.addActionListener(listenForStartButton);
    }

    public void addRestartButtonListener(ActionListener listenForRestartButton) {
        this.restartButton.addActionListener(listenForRestartButton);
    }

    public void addSkipButtonListener(ActionListener listenForSkipButton) {
        this.skipButton.addActionListener(listenForSkipButton);
    }

}
