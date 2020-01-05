package pl.polsl.edge_bundling.model;

public class Force {

    private double x;

    private double y;

    public Force(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Force combine(Force force) {
        this.x += force.getX();
        this.y += force.getY();
        return this;
    }

    public Force scale(double scale) {
        this.x *= scale;
        this.y *= scale;
        return this;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
