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

    //todo int?
    public double getLength() {
        return startingVertex.distanceTo(endingVertex);
    }
}
