package Model;
public class Goal implements ICircle {
    
    private Vector2D position;
    private double radius = 5.0;

    Goal(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Double getRadius() {
        return this.radius;
    }

}
