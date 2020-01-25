package pl.polsl.edge_bundling.model;


import java.util.Date;

public class Vertex implements Comparable<Vertex> {

    private Date timestamp;

    private double x;

    private double y;

    private Date getTimestamp() {
        return timestamp;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    void applyForce(Force force) {
        x += force.getX();
        y += force.getY();
    }

    Vertex(Vertex template) {
        this.timestamp = template.getTimestamp();
        this.x = template.getX();
        this.y = template.getY();
    }

    Vertex(double x, double y) {
        this.timestamp = null;
        this.x = x;
        this.y = y;
    }

    Vertex(String[] entry) {
        this.timestamp = new Date(Long.parseLong(entry[0]));
        this.x = Integer.parseInt(entry[1]);
        this.y = Integer.parseInt(entry[2]);
    }

    @Override
    public int compareTo(Vertex vertex) {
        return getTimestamp().compareTo(vertex.getTimestamp());
    }

    double distanceTo(Vertex vertex) {
        return Math.sqrt((vertex.getY() - this.getY()) * (vertex.getY() - this.getY()) +
                (vertex.getX() - this.getX()) * (vertex.getX() - this.getX()));
    }

    double xDistanceTo(Vertex vertex) {
        return vertex.getX() - this.getX();
    }

    double yDistanceTo(Vertex vertex) {
        return vertex.getY() - this.getY();
    }
}
