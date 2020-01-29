package pl.polsl.edge_bundling.model;

public class Edge {

    private Vertex startingVertex;

    private Vertex endingVertex;

    public Edge() {
    }

    public Edge(Vertex startingVertex, Vertex endingVertex) {
        this.startingVertex = startingVertex;
        this.endingVertex = endingVertex;
    }

    public Vertex getMidpoint() {
        return new Vertex((startingVertex.getX() + endingVertex.getX()) / 2.0,
                (startingVertex.getY() + endingVertex.getY()) / 2.0);
    }

    public Vertex getStartingVertex() {
        return startingVertex;
    }

    public void setStartingVertex(Vertex startingVertex) {
        this.startingVertex = startingVertex;
    }

    public Vertex getEndingVertex() {
        return endingVertex;
    }

    public void setEndingVertex(Vertex endingVertex) {
        this.endingVertex = endingVertex;
    }

    public double getLength() {
        return startingVertex.distanceTo(endingVertex);
    }

    public double getYLength() {
        return startingVertex.yDistanceTo(endingVertex);
    }

    public double getXLength() {
        return startingVertex.xDistanceTo(endingVertex);
    }
}
