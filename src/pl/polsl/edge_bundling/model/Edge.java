package pl.polsl.edge_bundling.model;

public class Edge {

    private Vertex startingVertex;

    private Vertex endingVertex;


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
}
