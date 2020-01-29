package pl.polsl.edge_bundling.model;

public class Edge {

    private Vertex startingVertex;

    private Vertex endingVertex;

    Edge() {
    }

    Edge(Vertex startingVertex, Vertex endingVertex) {
        this.startingVertex = startingVertex;
        this.endingVertex = endingVertex;
    }

    Vertex getMidpoint() {
        return new Vertex((startingVertex.getX() + endingVertex.getX()) / 2.0,
                (startingVertex.getY() + endingVertex.getY()) / 2.0);
    }

    public Vertex getStartingVertex() {
        return startingVertex;
    }

    void setStartingVertex(Vertex startingVertex) {
        this.startingVertex = startingVertex;
    }

    public Vertex getEndingVertex() {
        return endingVertex;
    }

    void setEndingVertex(Vertex endingVertex) {
        this.endingVertex = endingVertex;
    }

    public double getLength() {
        return startingVertex.distanceTo(endingVertex);
    }

    double getYLength() {
        return startingVertex.yDistanceTo(endingVertex);
    }

    double getXLength() {
        return startingVertex.xDistanceTo(endingVertex);
    }
}
