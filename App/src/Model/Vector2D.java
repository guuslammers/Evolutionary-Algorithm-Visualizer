package Model;
public class Vector2D {
    
    private double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private void normalize() {
        /*
        Normalizes this Vector2D.
        */
        double magnitude = this.getMagnitude();
        this.x = this.x / magnitude;
        this.y = this.y / magnitude;
    }

    public void addToVector(Vector2D vector) {
        /*
        Adds passed in Vector2D to this Vector2D.
        */
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public void scaleVector(double scale) {
        /*
        Scales this Vector2D by scale.
        */ 
        this.x = this.x * scale;
        this.y = this.y * scale;
    }

    public double getMagnitude() {
        /*
        Returns the magnitude of this Vector2D.
        */
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public void setMagnitude(double magnitude) {
        /*
        Sets the values of x, and y, in this Vector2D so that the vectors magnitude matches the passed in magnitude.
        */
        this.normalize();
        this.scaleVector(magnitude);
    }

    public double getDistanceTo(Vector2D vector) {
        /*
        Gets the distance between this Vector2D andd the passed in Vector2D.
        */
        return Math.sqrt(Math.pow((this.x - vector.getX()), 2) + Math.pow((this.y - vector.getY()), 2));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
