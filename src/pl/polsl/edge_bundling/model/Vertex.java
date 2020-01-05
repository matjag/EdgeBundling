package pl.polsl.edge_bundling.model;


import java.util.Date;

public class Vertex implements Comparable<Vertex> {

    private Date timestamp;

    private int x;

    private int y;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void applyForce(Force force) {
        x+= force.getX();
        y+= force.getY();
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vertex(Date timestamp, int x, int y) {
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
    }

    public Vertex() {
    }

    public Vertex(int x, int y) {
        this.timestamp = null;
        this.x = x;
        this.y = y;
    }

    public Vertex(String[] entry) {
        this.timestamp = new Date(Long.parseLong(entry[0]));
        this.x = Integer.parseInt(entry[1]);
        this.y = Integer.parseInt(entry[2]);
    }

    @Override
    public int compareTo(Vertex vertex) {
        return getTimestamp().compareTo(vertex.getTimestamp());
    }

    public double distanceTo(Vertex vertex) {
        return Math.sqrt((vertex.getY() - this.getY()) * (vertex.getY() - this.getY()) +
                (vertex.getX() - this.getX()) * (vertex.getX() - this.getX()));
    }

    public int xDistanceTo(Vertex vertex) {
        return this.getX() - vertex.getX();
    }

    public int yDistanceTo(Vertex vertex) {
        return this.getY() - vertex.getY();
    }
}
