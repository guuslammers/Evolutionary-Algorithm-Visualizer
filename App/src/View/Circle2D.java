package View;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Circle2D {
    
    Ellipse2D circle;
    Color color;

    public Circle2D(double x, double y, double radius, Color color) {
        this.circle = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
        this.color = color;
    }

    public Ellipse2D getCircle() {
        return this.circle;
    }

    public Color getColor() {
        return this.color;
    }

}
