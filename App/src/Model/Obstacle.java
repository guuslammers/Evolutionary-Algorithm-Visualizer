package Model;

public class Obstacle implements ICircle {

    private Vector2D position;
    private double radius;

    Obstacle(Vector2D position, Double radius) {
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
