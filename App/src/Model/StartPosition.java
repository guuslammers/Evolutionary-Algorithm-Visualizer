package Model;

public class StartPosition implements ICircle {
    
    private Vector2D position;
    private double radius;

    StartPosition(Vector2D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Double getRadius() {
        return this.radius;
    }
}
