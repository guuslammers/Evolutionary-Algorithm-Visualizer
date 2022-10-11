import Model.Model;
import View.View;

public class App {
    
    final static int WINDOW_WIDTH = 1000;
    final static int WINDOW_HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        Model model = new Model(WINDOW_WIDTH, WINDOW_HEIGHT);    
        View view = new View(WINDOW_WIDTH, WINDOW_HEIGHT);
        new Controller(model, view);

        view.setVisible(true);
    }
}
