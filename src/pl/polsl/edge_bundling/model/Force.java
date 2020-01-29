package pl.polsl.edge_bundling.model;

class Force {

    private double x;

    private double y;

    Force(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void combine(Force force) {
        this.x += force.getX();
        this.y += force.getY();
    }

    void scale(double scale) {
        this.x *= scale;
        this.y *= scale;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }
}
